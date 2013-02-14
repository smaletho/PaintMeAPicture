package edu.msu.project1.barrelofmonkeys;

import java.io.Serializable;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

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
		Intent intent = new Intent(this, DrawingActivity.class);
		startActivity(intent);
		
		//params.player1name = pl1.getText().toString();
		//params.player2name = pl2.getText().toString();
	}
	
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        
        
    }

}
