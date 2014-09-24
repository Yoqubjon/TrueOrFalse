package com.svsoftdeveloper.trueorfalse.activities;

import com.svsoftdeveloper.trueorfalse.R;
import com.svsoftdeveloper.trueorfalse.R.id;
import com.svsoftdeveloper.trueorfalse.R.layout;
import com.svsoftdeveloper.trueorfalse.R.menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class MainActivity extends Activity implements OnClickListener{
	
	Button btnPlay;
	Button btnResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        btnPlay = (Button) findViewById(R.id.btnPlay);
        btnPlay.setOnClickListener(this);
        btnResults = (Button) findViewById(R.id.btnResults);
        btnResults.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
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
	    	startActivity(intent);
	    	break;  
	    default:
	      break;
	    }
	  }
	
}
