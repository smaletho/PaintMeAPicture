package edu.msu.project1.barrelofmonkeys;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class GuessingActivity extends Activity {
	
	private String pictureHint;
	
	private String pictureSolution;
	
	private DrawingView userDrawing;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guessing);
		
		new CountDownTimer(130000, 1000) {
			
			@Override
			public void onTick(long millisUntilFinished) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				
			}
		};
	}

	public void isCorrect(View view) {
	
		Intent intent = new Intent(this, ScoreActivity.class);
		startActivity(intent);
		
		/*EditText et1 = (EditText)findViewById(R.id.guessBox);
		
		if(et1.getText().equals(pictureSolution)){
			return true;
		}
		return false;*/
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_guessing, menu);
		return true;
	}

}
