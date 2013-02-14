package edu.msu.project1.barrelofmonkeys;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class DrawingActivity extends Activity {

	
	private static final int GOT_COLOR = 2;
	//Make a button so that when they're done, it passes the drawing,
	//the hint and the answer to the guessing activity
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drawing);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_drawing, menu);
		return true;
	}
	
	public void onSubmit(View view)
	{
		Intent intent = new Intent(this, HintSelectionActivity.class);
		startActivity(intent);
	}
	public void onLineColor(View view)
	{
		Intent intent = new Intent(this, ColorSelectActivity.class); 
    	startActivity(intent);
	}

	public void onLineWidth(View view)
	{
		
	}

}
