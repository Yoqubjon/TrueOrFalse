package com.svsoftdeveloper.trueorfalse.activities;

import com.svsoftdeveloper.trueorfalse.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class PostGameResultActivity extends Activity {
	
	public static final String EXTRA_LEVEL_NUMBER = "level_number";
	public static final String EXTRA_RESULT_PERCENTS = "result_percents";
	
	TextView txtLevelMark;
	TextView txtResultPercents;
	Button btnAvailableLevel;
	Button btnGoToResults;
	Button btnGoToMenu;
	
	private float resultPercentage;
	private int levelNumber;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postgameresult);
        
        btnAvailableLevel = (Button) findViewById(R.id.btnAvailableLevel);
        btnGoToResults = (Button) findViewById(R.id.btnGoToResults);
        btnGoToMenu = (Button) findViewById(R.id.btnGoToMenu);
        
        txtLevelMark = (TextView) findViewById(R.id.txtlevelMark);
        txtResultPercents = (TextView) findViewById(R.id.txtResultPercents);
        
        
        levelNumber = getIntent().getIntExtra(EXTRA_LEVEL_NUMBER, 0);
        resultPercentage = getIntent().getFloatExtra(EXTRA_RESULT_PERCENTS, (float) 0.0);
        
        txtResultPercents.setText("Правильных ответов: " +  Float.toString(resultPercentage) + " %");
        

    }
	/*
	@Override
	public void onBackPressed() {
	    onNavigateUp();
	}*/
	
	
	@SuppressLint("NewApi") @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	       //что-то делаем: завершаем Activity, открываем другую и т.д.
	    	onNavigateUp();
	    	//Intent intent;
	    	//intent = new Intent(this, MainActivity.class);
	        //startActivity(intent);
	        
	    }
	    return super.onKeyDown(keyCode, event);
	}

}
