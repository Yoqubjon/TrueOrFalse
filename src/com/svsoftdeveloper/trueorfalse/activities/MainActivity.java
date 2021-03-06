package com.svsoftdeveloper.trueorfalse.activities;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.Reader;

import com.svsoftdeveloper.trueorfalse.R;
import com.svsoftdeveloper.trueorfalse.R.id;
import com.svsoftdeveloper.trueorfalse.R.layout;
import com.svsoftdeveloper.trueorfalse.R.menu;
import com.svsoftdeveloper.trueorfalse.activities.db.DatabaseHandler;
import com.svsoftdeveloper.trueorfalse.activities.db.MyDBAdapter;
import com.svsoftdeveloper.trueorfalse.activities.db.Question;
import com.svsoftdeveloper.trueorfalse.activities.db.Statistics;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class MainActivity extends Activity implements OnClickListener{
	
	
	final String TAG = "States";
	
	final int DIALOG_EXIT = 1;
	 
	private Button btnPlay;
	private Button btnResults;
	
	//MyDBAdapter mydatbase;
	//Cursor cursor;
	
	Statistics statistics;
	
	AlertDialog.Builder clearHistoryDialog;
	
	DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        btnPlay = (Button) findViewById(R.id.btnPlay);
        btnPlay.setOnClickListener(this);
        btnResults = (Button) findViewById(R.id.btnResults);
        btnResults.setOnClickListener(this);
        
        /*Log.d(TAG, "Trying to create mydatbase");
        mydatbase = new MyDBAdapter(this);
        Log.d(TAG, "Trying to open mydatbase");
		mydatbase.open();
		Log.d(TAG, "Open mydatbase - successful");*/
        
		
		db = new DatabaseHandler(this);
		
		if(db.getQuestionsCount() < 1){
			db.fillDB();
		}
		Log.d(TAG, Integer.toString(db.getQuestionsCount()));
		
		if(db.getStatisticsCount() < 1){
			db.fillStatisticsTable();
		}
		Log.d(TAG, Integer.toString(db.getStatisticsCount()));
		
		//db.addQuestion(new Question(0, "�������� ��������� � ����", "1", "���������� �����", "0"));
		//db.addQuestion(new Question(1, "�������� ��������� � ���� #2", "0", "���������� ����� #2", "0"));
        
		//Question q = db.getQuestion(1);
		
		//Log.d(TAG, q.getText());
		//Log.d(TAG, q.getAnswer());
		//Log.d(TAG, q.getExplanation());
		//Log.d(TAG, Integer.toString(db.getQuestionsCount()));
		
		statistics = db.getStatistics(1);
		
		Log.d(TAG, String.valueOf(statistics.getL1Percents()));
		Log.d(TAG, String.valueOf(statistics.getL2Percents()));
		Log.d(TAG, String.valueOf(statistics.getL3Percents()));
		Log.d(TAG, String.valueOf(statistics.getL4Percents()));
		Log.d(TAG, String.valueOf(statistics.getL5Percents()));

		Log.d(TAG, String.valueOf(statistics.getL1Done()));
		Log.d(TAG, String.valueOf(statistics.getL2Done()));
		Log.d(TAG, String.valueOf(statistics.getL3Done()));
		Log.d(TAG, String.valueOf(statistics.getL4Done()));
		Log.d(TAG, String.valueOf(statistics.getL5Done()));
		
		clearHistoryDialog = new AlertDialog.Builder(MainActivity.this); 
		 
		clearHistoryDialog.setTitle(R.string.title_clear_history);
		clearHistoryDialog.setMessage(R.string.clear_history_text);
		clearHistoryDialog.setIcon(android.R.drawable.ic_dialog_info);
		 
		clearHistoryDialog.setPositiveButton(R.string.yes_clear_history, 
		        new DialogInterface.OnClickListener() { 
		            public void onClick(DialogInterface dialog, int which) {
		                // Write your code here to execute after dialog 
		            	resetStatistic();
		            	
		            } 
		        }); 
		// Setting Negative "NO" Btn 
		clearHistoryDialog.setNegativeButton(R.string.no_clear_history,
		        new DialogInterface.OnClickListener() {
		            public void onClick(DialogInterface dialog, int which) {
		                // Write your code here to execute after dialog 

		                dialog.cancel();
		            } 
		        }); 
    }
    

    public void resetStatistic(){
    	db.resetStatistics();
    	db.resetQuestions();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
    	getMenuInflater().inflate(R.menu.main_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
    	int id = item.getItemId();
		if (id == R.id.action_clear_history) {
			
			// Showing Alert Dialog 
			clearHistoryDialog.show();	
			
			return true;
		}
		return super.onOptionsItemSelected(item);
    }

    

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent;
		switch (v.getId()) {
	    case R.id.btnPlay:
	      // TODO Call second activity
	    	intent = new Intent(this, LevelSelectionActivity.class);
	        startActivity(intent);
	        break;
	    case R.id.btnResults:
	    	// TODO Call second activity
	    	intent = new Intent(this, ResultsActivity.class);
	    	intent.putExtra(ResultsActivity.EXTRA_STATISTICS, statistics);
	    	startActivity(intent);
	    	break;  
	    default:
	      break;
	    }
	  }
	
}
