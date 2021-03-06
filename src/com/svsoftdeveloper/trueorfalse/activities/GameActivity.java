package com.svsoftdeveloper.trueorfalse.activities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.svsoftdeveloper.trueorfalse.R;
import com.svsoftdeveloper.trueorfalse.activities.FragmentAnswer.onSomeEventListener;
import com.svsoftdeveloper.trueorfalse.activities.FragmentNext.onNextButtonEventListener;
import com.svsoftdeveloper.trueorfalse.activities.db.DatabaseHandler;
import com.svsoftdeveloper.trueorfalse.activities.db.Question;
import com.svsoftdeveloper.trueorfalse.activities.db.Statistics;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class GameActivity extends Activity implements onSomeEventListener, onNextButtonEventListener, OnLoadCompleteListener{
	
	public static final String APP_PREFERENCES = "soundsettings";
	public static final String APP_PREFERENCES_SOUND = "soundonoff";
	
	SharedPreferences mSoundSettings;
	
	private static final float LEVEL_ACCEPTANCE_PERCENTAGE = 70.0f;
	
	private static final int[] NUMBER_OF_QUESTIONS_IN_LEVEL = {5, 6, 7, 8, 9};
	
	private int numberCorrectAnswers;
	private int numberWrongAnswers;
	private int currentQuestionNumber;
	private int levelnumber;
	private float levelPercentage;
	
	private Random rand;
	
	private List<Question> questionsList;
	private Question question;
	private Statistics statistics;
	
	private DatabaseHandler db;
	
	Fragment fragmentAnswer;
	Fragment fragmentNext;
	FragmentTransaction fTrans;
	
	TextView textViewCorrect;
	TextView textViewWrong;
	TextView textViewAnswerTitle;
	TextView textViewExplanationTitle;
	TextView textViewExplanationText;
	
	final int MAX_STREAMS = 5;
	private SoundPool sp;
	private int soundIdCorrectAnswer;
	private int soundIdWrongAnswer;
	
	private MenuItem menuSoundOn;
	private MenuItem menuSoundOff;
	
	private int tmpSoundOnOff;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        
        textViewCorrect = (TextView) findViewById(R.id.textViewCorrect);
        textViewWrong = (TextView) findViewById(R.id.textViewWrong);
        textViewAnswerTitle = (TextView) findViewById(R.id.textViewAnswerTitle);
        textViewExplanationTitle = (TextView) findViewById(R.id.textViewExplanationTitle);
        textViewExplanationText = (TextView) findViewById(R.id.textViewExplanationText);
        
        fragmentAnswer = new FragmentAnswer();
        fragmentNext = new FragmentNext();
        
        rand = new Random();
        
        fTrans = getFragmentManager().beginTransaction();
        fTrans.add(R.id.frgmCont, fragmentAnswer);
        fTrans.commit();
        
        Intent intent = getIntent();      
        levelnumber = (intent.getIntExtra("levelnumber", 1));
        
        numberCorrectAnswers = 0;
        numberWrongAnswers = 0;
        currentQuestionNumber = 0;
        
        db = new DatabaseHandler(this);
        question = getRandomQuestion();
        statistics = db.getStatistics(1);
        
        showQuestion();
        
        sp = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0);
		sp.setOnLoadCompleteListener(this);
		soundIdCorrectAnswer = sp.load(this, R.raw.correct_answer, 1);
		soundIdWrongAnswer = sp.load(this, R.raw.wrong_answer, 1);
        
		mSoundSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
		
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sound_on_off, menu);
		
		menuSoundOn = menu.findItem(R.id.menu_sound_on);	
		menuSoundOff = menu.findItem(R.id.menu_sound_off);
		
		checkMenuSoundOnOff();
		
		return true;
	}
	
	private void checkMenuSoundOnOff(){
		if((menuSoundOn == null)&&(menuSoundOff == null)){
			return;
		}
		if(tmpSoundOnOff == 1){
			menuSoundOn.setEnabled(true);
			menuSoundOn.setVisible(true);
			menuSoundOff.setEnabled(false);
			menuSoundOff.setVisible(false);
		}
		else{
			menuSoundOn.setEnabled(false);
			menuSoundOn.setVisible(false);
			menuSoundOff.setEnabled(true);
			menuSoundOff.setVisible(true);
		}
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.menu_sound_on: {
			
			tmpSoundOnOff = 0;
			checkMenuSoundOnOff();
			return true;
		}
		case R.id.menu_sound_off: {

			tmpSoundOnOff = 1;
			checkMenuSoundOnOff();
			return true;
		}

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		checkMenuSoundOnOff();
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();

		Editor editor = mSoundSettings.edit();
		editor.putInt(APP_PREFERENCES_SOUND, tmpSoundOnOff);
		editor.apply();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	    // ���� �� ������ ��� ����
		if (mSoundSettings.contains(APP_PREFERENCES_SOUND)) {
			// �������� ����� �� ��������
			tmpSoundOnOff = mSoundSettings.getInt(APP_PREFERENCES_SOUND, 1);
			// ������� �� �����
		}
		else{
			tmpSoundOnOff = 1;
		}
	}
	
	public void showQuestion(){
		textViewCorrect.setText(Integer.toString(numberCorrectAnswers));
        textViewWrong.setText(Integer.toString(numberWrongAnswers));
        
        textViewAnswerTitle.setTextColor(getResources().getColor(R.color.light_grey));
        textViewAnswerTitle.setText("?");
        textViewExplanationTitle.setText("");
        textViewExplanationText.setText(question.getText());
        
        currentQuestionNumber++;
	}
	
	public void showAnswer(boolean answerResult){
		textViewCorrect.setText(Integer.toString(numberCorrectAnswers));
        textViewWrong.setText(Integer.toString(numberWrongAnswers));
        if(answerResult == true){
        	textViewAnswerTitle.setTextColor(getResources().getColor(R.color.light_green));
        	textViewAnswerTitle.setText("�����");
        }
        else{
        	textViewAnswerTitle.setTextColor(getResources().getColor(R.color.my_red));
        	textViewAnswerTitle.setText("�������");
        }
        if((question.getExplanation().length()) > 0){
        	textViewExplanationTitle.setText("���������");
        }
        else{
        	textViewExplanationTitle.setText("");
        }
        textViewExplanationText.setText(question.getExplanation());
	}
	
	
	public Question getRandomQuestion(){
		
		Question question = null;
		
		questionsList = db.getNewQuestions();
		
		if(questionsList.size() < 1){
			questionsList = db.getAllQuestions();
			
		}
		
		question = questionsList.get(rand.nextInt(questionsList.size()));
		
		return question;
		
	}


	@Override
	public void someEvent(int s) {
		// TODO Auto-generated method stub
		switch (s) {
	    case FragmentAnswer.ANSWER_FALSE:
	      // TODO Call second activity
	    	if(Integer.parseInt(question.getAnswer()) == 0){
	    		if(tmpSoundOnOff == 1){
	    			sp.play(soundIdCorrectAnswer, 1, 1, 0, 0, 1);
	    		}
	    		numberCorrectAnswers++;
	    		showAnswer(true);
	    	}
	    	else{
	    		if(tmpSoundOnOff == 1){
	    			sp.play(soundIdWrongAnswer, 1, 1, 0, 0, 1);
	    		}
	    		numberWrongAnswers++;
	    		showAnswer(false);
	    	}
	    	fTrans = getFragmentManager().beginTransaction();
	    	fTrans.replace(R.id.frgmCont, fragmentNext);
	    	fTrans.commit();
	    	question.setUsed("1");
	    	db.updateQuestion(question);
	        break;
	    case FragmentAnswer.ANSWER_TRUTH:
	    	// TODO Call second activity
	    	if(Integer.parseInt(question.getAnswer()) == 1){
	    		if(tmpSoundOnOff == 1){
	    			sp.play(soundIdCorrectAnswer, 1, 1, 0, 0, 1);
	    		}
	    		numberCorrectAnswers++;
	    		showAnswer(true);
	    	}
	    	else{
	    		if(tmpSoundOnOff == 1){
	    			sp.play(soundIdWrongAnswer, 1, 1, 0, 0, 1);
	    		}
	    		numberWrongAnswers++;
	    		showAnswer(false);
	    	}
	    	fTrans = getFragmentManager().beginTransaction();
	    	fTrans.replace(R.id.frgmCont, fragmentNext);
	    	fTrans.commit();
	    	question.setUsed("1");
	    	db.updateQuestion(question);
	    	break;  
	    default:
	      break;
	    }
	}
	
	
	@Override
	public void nextButtonEvent(int s) {
		// TODO Auto-generated method stub
		Intent intent;
		switch (s) {
	    case FragmentNext.NEXT_QUESTION:
		      // TODO Call second activity
	    	if(currentQuestionNumber < NUMBER_OF_QUESTIONS_IN_LEVEL[levelnumber]){
	    		fTrans = getFragmentManager().beginTransaction();
	    		fTrans.replace(R.id.frgmCont, fragmentAnswer);
	    		fTrans.commit();
	    		question = getRandomQuestion();
	    		showQuestion();
	    	}
	    	else{
	    		levelPercentage = ((float) numberCorrectAnswers/(numberCorrectAnswers + numberWrongAnswers)) * 100;
	    		
	    		if(levelPercentage >= LEVEL_ACCEPTANCE_PERCENTAGE){
	    			
	    			if(levelnumber == 0){
	    				statistics.setL1Done(1);
	    				if(levelPercentage > statistics.getL1Percents()){
	    					statistics.setL1Percents(levelPercentage);
	    				}
	    			}
	    			else if(levelnumber == 1){
	    				statistics.setL2Done(1);
	    				if(levelPercentage > statistics.getL2Percents()){
	    					statistics.setL2Percents(levelPercentage);
	    				}
	    			}
	    			else if(levelnumber == 2){
	    				statistics.setL3Done(1);
	    				if(levelPercentage > statistics.getL3Percents()){
	    					statistics.setL3Percents(levelPercentage);
	    				}
	    			}
	    			else if(levelnumber == 3){
	    				statistics.setL4Done(1);
	    				if(levelPercentage > statistics.getL4Percents()){
	    					statistics.setL4Percents(levelPercentage);
	    				}
	    			}
	    			else if(levelnumber == 4){
	    				statistics.setL5Done(1);
	    				if(levelPercentage > statistics.getL5Percents()){
	    					statistics.setL5Percents(levelPercentage);
	    				}
	    			}
	    			db.updateStatistics(statistics);
	    		}

	    		intent = new Intent(this, PostGameResultActivity.class);
		    	intent.putExtra(PostGameResultActivity.EXTRA_LEVEL_NUMBER, levelnumber);
		    	intent.putExtra(PostGameResultActivity.EXTRA_RESULT_PERCENTS, levelPercentage);
		    	startActivity(intent);
	    	}
		        break;
	    default:
	      break;
	    }
	}

	@Override
	public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
		// TODO Auto-generated method stub
		
	}

}
