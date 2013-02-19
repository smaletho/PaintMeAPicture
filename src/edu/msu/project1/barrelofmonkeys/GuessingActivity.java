package edu.msu.project1.barrelofmonkeys;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Spannable;
import android.text.style.BackgroundColorSpan;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class GuessingActivity extends Activity {
	
	private String pictureHint;
	
	private String pictureSolution = "test";
	
	private DrawingView userDrawing;
	
	private final long startTime = 130000;
	
	private final long interval = 1000;
	
	private GuessingCountDownTimer countDownTimer = new GuessingCountDownTimer(startTime, interval);
	
	private EditText et1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guessing);
		et1 = (EditText)findViewById(R.id.guessBox);
		
		//save all parameters
		/*
		 * drawingView, hint, solution, p1 score, p2 score
		 */
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.switchToGuessTitle);
		builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	        	   countDownTimer.start();
	           }
	       });
		
		AlertDialog dialog = builder.create();
		dialog.show();
	}

	public void isCorrect(View view) {

		if(et1.getText().toString().equals(pictureSolution)){
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(R.string.correctGuess);
			builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	   countDownTimer.cancel();
		       			goToScoreActivity();
		           }
		       });
			
			AlertDialog dialog = builder.create();
			dialog.show();
		}
		else {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(R.string.guessAgain);
			builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	   et1.selectAll();
		           }
		       });
			
			AlertDialog dialog = builder.create();
			dialog.show();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_guessing, menu);
		return true;
	}
	
	// CountDownTimer class
	public class GuessingCountDownTimer extends CountDownTimer {

		public GuessingCountDownTimer(long startTime, long interval) {
			super(startTime, interval);
		}

		@Override
		public void onFinish() {
			timeExpired();
		}

		@Override
		public void onTick(long millisUntilFinished) {
			TextView timerText = (TextView)findViewById(R.id.timerText);
			int timeLeft = (int)(millisUntilFinished / 1000);
			timerText.setText("Time Remaining: " + timeLeft + " seconds");
		}		
	}
	
	public void goToScoreActivity() {
		Intent intent = new Intent(this, ScoreActivity.class);
		startActivity(intent);
	}
	
	public void timeExpired() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.timeExpired);
		builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	       			goToScoreActivity();
	           }
	       });
		
		AlertDialog dialog = builder.create();
		dialog.show();
	}
}
