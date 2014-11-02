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
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends Activity implements onSomeEventListener, onNextButtonEventListener{
	
	private static final int NUMBER_OF_QUESTIONS_LEVEL_1 = 3;
	private static final int NUMBER_OF_QUESTIONS_LEVEL_2 = 4;
	private static final int NUMBER_OF_QUESTIONS_LEVEL_3 = 5;
	private static final int NUMBER_OF_QUESTIONS_LEVEL_4 = 6;
	private static final int NUMBER_OF_QUESTIONS_LEVEL_5 = 7;
	
	private static final float LEVEL_ACCEPTANCE_PERCENTAGE = 70.0f;
	
	private static final int[] NUMBER_OF_QUESTIONS_IN_LEVEL = {3, 4, 5, 6, 7};
	
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
	
	FragmentAnswer fragmentAnswer;
	FragmentNext fragmentNext;
	FragmentTransaction fTrans;
	
	TextView textViewCorrect;
	TextView textViewWrong;
	TextView textViewAnswerTitle;
	TextView textViewExplanationTitle;
	TextView textViewExplanationText;
	
	
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
        //Toast.makeText(this, "Level number: " + Integer.toString(levelnumber), Toast.LENGTH_SHORT).show();
        
        numberCorrectAnswers = 0;
        numberWrongAnswers = 0;
        currentQuestionNumber = 0;
        
        db = new DatabaseHandler(this);
        question = getRandomQuestion();
        statistics = db.getStatistics(1);
        
        showQuestion();
        
        
        
        
    }
	
	public void showQuestion(){
		textViewCorrect.setText(Integer.toString(numberCorrectAnswers));
        textViewWrong.setText(Integer.toString(numberWrongAnswers));
        textViewAnswerTitle.setText("");
        textViewExplanationTitle.setText("");
        textViewExplanationText.setText(question.getText());
        
        currentQuestionNumber++;
	}
	
	public void showAnswer(boolean answerResult){
		textViewCorrect.setText(Integer.toString(numberCorrectAnswers));
        textViewWrong.setText(Integer.toString(numberWrongAnswers));
        if(answerResult == true){
        	textViewAnswerTitle.setText("Верно");
        }
        else{
        	textViewAnswerTitle.setText("Неверно");
        }
        if((question.getExplanation().length()) > 0){
        	textViewExplanationTitle.setText("Пояснение");
        }
        else{
        	textViewExplanationTitle.setText("");
        }
        textViewExplanationText.setText(question.getExplanation());
	}
	
	
	public Question getRandomQuestion(){
		
		Question question = null;
		
		questionsList = db.getNewQuestions();
		Toast.makeText(this, "Not used questions: " + Integer.toString(questionsList.size()), Toast.LENGTH_SHORT).show();
		
		if(questionsList.size() < 1){
			questionsList = db.getAllQuestions();
			
		}
		// question = questionsList.get((int)(Math.random() * (questionsList.size()))); // rand.nextInt(questionsList.size()+1)
		
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
	    		numberCorrectAnswers++;
	    		showAnswer(true);
	    	}
	    	else{
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
	    		numberCorrectAnswers++;
	    		showAnswer(true);
	    	}
	    	else{
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

}












/*
questionsList = db.getNewQuestions();
question = questionsList.get(0);
Toast.makeText(this, "Level number: " + Integer.toString(questionsList.size()), Toast.LENGTH_SHORT).show();
// Min + (int)(Math.random() * ((Max - Min) + 1)) - original
// (int)(Math.random() * (questionsList.size() + 1))
Random rand = new Random();
//rand.nextInt(questionsList.size());
//Toast.makeText(this, "Level number: " + Integer.toString(rand.nextInt(questionsList.size()+1)), Toast.LENGTH_SHORT).show();
Toast.makeText(this, "Level number: " + Integer.toString((int)(Math.random() * (questionsList.size()))), Toast.LENGTH_SHORT).show();
*/
