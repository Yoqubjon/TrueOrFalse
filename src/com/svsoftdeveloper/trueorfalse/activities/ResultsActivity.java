package com.svsoftdeveloper.trueorfalse.activities;

import com.svsoftdeveloper.trueorfalse.R;
import com.svsoftdeveloper.trueorfalse.activities.db.Statistics;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class ResultsActivity extends Activity {
	
	public static final String EXTRA_STATISTICS = "statistics";
	
	private TextView txtAverageResult;
	
	private TextView txtLevel1percentage;
	private TextView txtLevel2percentage;
	private TextView txtLevel3percentage;
	private TextView txtLevel4percentage;
	private TextView txtLevel5percentage;
	
	private Statistics statistics;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myresults);
        
        txtAverageResult = (TextView) findViewById(R.id.txtAverageResult);
        
        txtLevel1percentage = (TextView) findViewById(R.id.txtLevel1percentage);
        txtLevel2percentage = (TextView) findViewById(R.id.txtLevel2percentage);
        txtLevel3percentage = (TextView) findViewById(R.id.txtLevel3percentage);
        txtLevel4percentage = (TextView) findViewById(R.id.txtLevel4percentage);
        txtLevel5percentage = (TextView) findViewById(R.id.txtLevel5percentage);
        
        statistics = getIntent().getParcelableExtra(EXTRA_STATISTICS);
        
        txtLevel1percentage.setText(Float.toString(statistics.getL1Percents()) + " %");
        txtLevel2percentage.setText(Float.toString(statistics.getL2Percents()) + " %");
        txtLevel3percentage.setText(Float.toString(statistics.getL3Percents()) + " %");
        txtLevel4percentage.setText(Float.toString(statistics.getL4Percents()) + " %");
        txtLevel5percentage.setText(Float.toString(statistics.getL5Percents()) + " %");
        
        txtAverageResult.setText(Float.toString((Math.round(10*((statistics.getL1Percents() + statistics.getL2Percents() + statistics.getL3Percents() + statistics.getL4Percents() + statistics.getL5Percents())/5)))/10.0f) + " %");
        
        
    }

}
