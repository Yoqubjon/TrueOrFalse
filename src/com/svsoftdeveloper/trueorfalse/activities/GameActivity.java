package com.svsoftdeveloper.trueorfalse.activities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.svsoftdeveloper.trueorfalse.R;
import com.svsoftdeveloper.trueorfalse.activities.db.DatabaseHandler;
import com.svsoftdeveloper.trueorfalse.activities.db.Question;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends Activity {
	
	private static final int NUMBER_OF_QUESTIONS_LEVEL_1 = 3;
	private static final int NUMBER_OF_QUESTIONS_LEVEL_2 = 4;
	private static final int NUMBER_OF_QUESTIONS_LEVEL_3 = 5;
	private static final int NUMBER_OF_QUESTIONS_LEVEL_4 = 6;
	private static final int NUMBER_OF_QUESTIONS_LEVEL_5 = 7;
	
	private int numberCorrectAnswers;
	private int numberWrongAnswers;
	private int questionNumber;
	
	private List<Question> questionsList;
	private Question question;
	
	private DatabaseHandler db;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        
        Intent intent = getIntent();      
        int levelnumber = intent.getIntExtra("levelnumber", 1);        
        //Toast.makeText(this, "Level number: " + Integer.toString(levelnumber), Toast.LENGTH_SHORT).show();
        
        numberCorrectAnswers = 0;
        numberCorrectAnswers = 0;
        questionNumber = 0;
        
        db = new DatabaseHandler(this);
        questionsList = db.getNewQuestions();
        question = questionsList.get(0);
        Toast.makeText(this, "Level number: " + Integer.toString(questionsList.size()), Toast.LENGTH_SHORT).show();
        // Min + (int)(Math.random() * ((Max - Min) + 1)) - original
        // (int)(Math.random() * (questionsList.size() + 1))
        Random rand = new Random();
        //rand.nextInt(questionsList.size());
        //Toast.makeText(this, "Level number: " + Integer.toString(rand.nextInt(questionsList.size()+1)), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Level number: " + Integer.toString((int)(Math.random() * (questionsList.size()))), Toast.LENGTH_SHORT).show();
    }

}
