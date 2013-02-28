package edu.msu.project1.barrelofmonkeys;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private GameManager manager;

	/*private static class Parameters implements Serializable {

		private static final long serialVersionUID = 1L;
		
		private String player1name;
		private String player2name;
	}*/
	
	public Toast roundDisplayToast = null;

    /**
     * The current parameters
     */
    //private Parameters params = new Parameters();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		manager = new GameManager();
		
		roundDisplayToast = Toast.makeText(this, "Number of Rounds: 1", Toast.LENGTH_SHORT);
		roundDisplayToast.show();
		
		manager.setNumRounds(2);
		
		SeekBar seekBar = (SeekBar) findViewById(R.id.roundSlider);
		seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				if (progress > 0) {
					manager.setNumRounds(progress * 2);
					roundDisplayToast.setText("Number of Rounds: " + progress);
				}
				else{
					seekBar.setProgress(1);
				}
				roundDisplayToast.show();
			}
		});
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public void onStart(View view) {
		EditText pl1 = (EditText)findViewById(R.id.player1name);
		EditText pl2 = (EditText)findViewById(R.id.player2name);
		
		if(pl1.getText().toString().equals("") || pl2.getText().toString().equals("")){
			Toast.makeText(getApplicationContext(), "You must enter names!", Toast.LENGTH_SHORT).show();
		}
		else{
			manager.setPlayer1name(pl1.getText().toString());
			manager.setPlayer2name(pl2.getText().toString());
			
			Intent intent = new Intent(this, DrawingActivity.class);
			startActivity(intent);
		}
	}

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        
    }
    
    public void onHowToPlay(View view) {
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.howToPlayTitle);
		builder.setMessage(R.string.howToPlayText);
		builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	        	   //
	           }
	       });

		AlertDialog dialog = builder.create();
		dialog.show();
    }

}