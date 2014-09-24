package com.svsoftdeveloper.trueorfalse.activities;

import com.svsoftdeveloper.trueorfalse.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class LevelSelectionActivity extends Activity implements OnClickListener{
	
	Button btnLevel1;
	Button btnLevel2;
	Button btnLevel3;
	Button btnLevel4;
	Button btnLevel5;
	
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_levelselection);
	        
	        btnLevel1 = (Button) findViewById(R.id.btnLevel1);
	        btnLevel1.setOnClickListener(this);
	        
	        btnLevel2 = (Button) findViewById(R.id.btnLevel2);
	        btnLevel2.setOnClickListener(this);
	        
	        btnLevel3 = (Button) findViewById(R.id.btnLevel3);
	        btnLevel3.setOnClickListener(this);
	        
	        btnLevel4 = (Button) findViewById(R.id.btnLevel4);
	        btnLevel4.setOnClickListener(this);
	        
	        btnLevel5 = (Button) findViewById(R.id.btnLevel5);
	        btnLevel5.setOnClickListener(this);
	    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent;
		switch (v.getId()) {
	    case R.id.btnLevel1:
	      // TODO Call second activity
	    	intent = new Intent(this, GameActivity.class);
	        startActivity(intent);
	        break;
	    case R.id.btnLevel2:
	    	// TODO Call second activity
	    	intent = new Intent(this, GameActivity.class);
	    	startActivity(intent);
	    	break;  
	    case R.id.btnLevel3:
	    	// TODO Call second activity
	    	intent = new Intent(this, GameActivity.class);
	    	startActivity(intent);
	    	break;
	    case R.id.btnLevel4:
	    	// TODO Call second activity
	    	intent = new Intent(this, GameActivity.class);
	    	startActivity(intent);
	    	break;
	    case R.id.btnLevel5:
	    	// TODO Call second activity
	    	intent = new Intent(this, GameActivity.class);
	    	startActivity(intent);
	    	break;
	    default:
	      break;
	    }
	  }
	
	
}
