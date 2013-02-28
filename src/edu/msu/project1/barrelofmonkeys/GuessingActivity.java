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

		private String pictureHint;

		private String pictureSolution;

		private DrawingView userDrawing;

		private long startTime = 130000;

		private final long interval = 1000;

		private GuessingCountDownTimer countDownTimer;

		private EditText et1;

		private TextView timerText;

		private TextView hintText;
		
		private TextView categoryText;
		
		private GameManager gameManager;
		
		private TextView p1score;
		
		private TextView p2score;

	}
	
	private void setPlayer1ScoreText(TextView view) {
		params.p1score = view;
	}
	
	private void setPlayer2ScoreText(TextView view) {
		params.p2score = view;
	}
	
	public GameManager getGameManager() {
		return params.gameManager;
	}

	public void setGameManager(GameManager gameManager) {
		params.gameManager = gameManager;
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
	
	public void setCategoryText(TextView view) {
		params.categoryText = view;
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
    		params.categoryText = (TextView)findViewById(R.id.categoryText);
    		params.p1score = (TextView)findViewById(R.id.textView1);
    		params.p2score = (TextView)findViewById(R.id.textView2);
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
		params.categoryText = (TextView)findViewById(R.id.categoryText);
		params.et1 = (EditText)findViewById(R.id.guessBox);
		params.p1score = (TextView)findViewById(R.id.textView1);
		params.p2score = (TextView)findViewById(R.id.textView2);
		params.gameManager = GameManager.get();
		params.pictureHint = params.gameManager.getGameHint();
		params.pictureSolution = params.gameManager.getGameSolution();
		params.userDrawing = params.gameManager.getDrawingView();
		params.categoryText.setText(params.gameManager.getCategory());
		
		int p1 = params.gameManager.getPlayer1score();
		int p2 = params.gameManager.getPlayer2score();
		
		params.p1score.setText("P1: " + p1);
		params.p2score.setText("P2: " + p2);
	}

	public void isCorrect(View view) {

		if(params.et1.getText().toString().toUpperCase().equals(params.pictureSolution)){
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

			if (millisUntilFinished <= 60000) {
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
		params.gameManager.setGameRound();
		int score = (int)params.countDownTimer.getTimeRemaining() / 1000;
		if(params.gameManager.getCurrentPlayer() == 1) {
			params.gameManager.setPlayer1score(score);
		}
		else {
			params.gameManager.setPlayer2score(score);
		}
		params.gameManager.switchPlayers();
		if(params.gameManager.getGameRound() == 3) {
			Intent intent = new Intent(getBaseContext(), ScoreActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
			startActivity(intent);
		} else {
			Intent intent = new Intent(getBaseContext(), DrawingActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
			startActivity(intent);
		}
	}

	public void timeExpired() {
				
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.timeExpired);
		builder.setMessage("Correct answer was: " + params.gameManager.getGameSolution());
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
    	setEditText(params.et1);
    	setTimerText(params.timerText);
    	setHintText(params.hintText);
    	setCategoryText(params.categoryText);
    	setGameManager(params.gameManager);
    	setPlayer1ScoreText(params.p1score);
    	setPlayer2ScoreText(params.p2score);

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