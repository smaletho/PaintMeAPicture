package edu.msu.project1.barrelofmonkeys;

import java.io.Serializable;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class GuessingActivity extends Activity {

	private static final String PARAMETERS = "parameters";

	private static class Parameters implements Serializable {

		private static final long serialVersionUID = 1L;

		private String pictureHint = "I'm a hint!";

		private String pictureSolution = "test";

		private DrawingView userDrawing;

		private long startTime = 130000;

		private final long interval = 1000;

		private GuessingCountDownTimer countDownTimer;

		private int player1Score;

		private int player2Score;

		private EditText et1;

		private TextView timerText;

		private TextView hintText;

	}

	public void setHintText(TextView view) {
		params.hintText = view;
	}

	public void setTimerText(TextView view) {
		params.timerText = view;
	}

	public void setEditText(EditText text) {
		params.et1 = text;
	}

	public int getPlayer1Score() {
		return params.player1Score;
	}

	public void setPlayer1Score(int player1Score) {
		params.player1Score = player1Score;
	}

	public int getPlayer2Score() {
		return params.player2Score;
	}

	public void setPlayer2Score(int player2Score) {
		params.player2Score = player2Score;
	}

	public String getPictureHint() {
		return params.pictureHint;
	}

	public String getPictureSolution() {
		return params.pictureSolution;
	}

	public DrawingView getUserDrawing() {
		return params.userDrawing;
	}

	public void setUserDrawing(DrawingView view) {
		params.userDrawing = view;
	}

	public long getTimeRemaining() {
		return params.countDownTimer.timeRemaining;
	}

	public void setTimeRemaining (long time) {
		params.startTime = time;
	}

    /**
     * The current parameters
     */
    private Parameters params = new Parameters();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guessing);

        /*
         * Restore any state
         */
        if(savedInstanceState != null) {
            getFromBundle(PARAMETERS, savedInstanceState);
        }
        else {
    		params.et1 = (EditText)findViewById(R.id.guessBox);
    		params.timerText = (TextView)findViewById(R.id.timerText);
    		setTimeRemaining(params.startTime);
    		
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(R.string.switchToGuessTitle);
			builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	   params.countDownTimer.start();
		           }
		       });

			AlertDialog dialog = builder.create();
			dialog.show();
        }

		params.countDownTimer = new GuessingCountDownTimer(params.startTime, params.interval);
		params.timerText = (TextView)findViewById(R.id.timerText);
		params.hintText = (TextView)findViewById(R.id.hintText);
		params.et1 = (EditText)findViewById(R.id.guessBox);
	}

	public void isCorrect(View view) {

		if(params.et1.getText().toString().equals(params.pictureSolution)){
			params.countDownTimer.cancel();
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(R.string.correctGuess);
			builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
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
		        	   params.et1.selectAll();
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

		public long timeRemaining;

		public GuessingCountDownTimer(long startTime, long interval) {
			super(startTime, interval);
		}

		@Override
		public void onFinish() {
			timeExpired();
		}

		@Override
		public void onTick(long millisUntilFinished) {
			timeRemaining = millisUntilFinished;
			int timeLeft = (int)(millisUntilFinished / 1000);
			params.timerText.setText("Time Remaining: " + timeLeft + " seconds");

			if (millisUntilFinished <= 70000) {
				params.hintText.setText("Hint: " + params.pictureHint);
			}
			else {
				params.hintText.setText(R.string.hintString);
			}
		}

		public long getTimeRemaining() {
			return timeRemaining;
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

    /**
     * Save the view state to a bundle
     * @param key key name to use in the bundle
     * @param bundle bundle to save to
     */
    public void putToBundle(String key, Bundle bundle) {
    	bundle.putSerializable(key, params);   
    }
    
    /**
     * Get the view state from a bundle
     * @param key key name to use in the bundle
     * @param bundle bundle to load from
     */
    public void getFromBundle(String key, Bundle bundle) {
    	params = (Parameters)bundle.getSerializable(key);
    	
        // Ensure the options are all set
    	setTimeRemaining(params.startTime);
    	setPlayer1Score(params.player1Score);
    	setPlayer2Score(params.player2Score);
    	setEditText(params.et1);
    	setTimerText(params.timerText);
    	setHintText(params.hintText);

    }
    
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        
        params.startTime = params.countDownTimer.getTimeRemaining();
        
        putToBundle(PARAMETERS, outState);
    }
    
    @Override
    public void onBackPressed() {
    }
}