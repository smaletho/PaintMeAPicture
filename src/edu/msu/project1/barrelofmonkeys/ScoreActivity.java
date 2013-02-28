package edu.msu.project1.barrelofmonkeys;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class ScoreActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_score);
		
		GameManager gameManager = GameManager.get();
		
		TextView pl1 = (TextView)findViewById(R.id.player1);
		TextView pl2 = (TextView)findViewById(R.id.player2);
		
		String name1 = gameManager.getPlayer1name();
		String name2 = gameManager.getPlayer2name();
		
		pl1.setText(name1 + "\n" + gameManager.getPlayer1score());
		pl2.setText(name2 + "\n" + gameManager.getPlayer2score());
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_score, menu);
		return true;
	}
	
	public void onPlayAgain(View view) {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}

}
