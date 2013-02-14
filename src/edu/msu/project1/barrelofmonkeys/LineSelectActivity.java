package edu.msu.project1.barrelofmonkeys;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class LineSelectActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_color_select);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_color_select, menu);
		return true;
	}
	
	public void onDone(View view)
	{
//		Intent intent = new Intent(this, DrawingActivity.class); 
//    	startActivity(intent);
		Intent result = new Intent();
		
		setResult(Activity.RESULT_OK, result);
		finish();
	}

}
