package edu.msu.project1.barrelofmonkeys;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	/*private static class Parameters implements Serializable {

		private static final long serialVersionUID = 1L;
		
		private String player1name;
		private String player2name;
	}*/

    /**
     * The current parameters
     */
    //private Parameters params = new Parameters();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
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
		
		if(pl1.getText().equals("") || pl2.getText().equals("")){
			Toast.makeText(getApplicationContext(), "You must enter names!", Toast.LENGTH_SHORT).show();
		}
		else{
			GameManager manager = new GameManager();
			manager.setPlayer1name(pl1.toString());
			manager.setPlayer2name(pl2.toString());
			
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