package edu.msu.project1.barrelofmonkeys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class DrawingActivity extends Activity {

	/*
	 * The drawing view in this activity's view
	 */
	private DrawingView drawingView;
	
	/*
	 * The players
	 */
	//private Players players = new Players();
	
	private static final int GOT_LINE = 2;
	
	//Make a button so that when they're done, it passes the drawing,
	//the hint and the answer to the guessing activity
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drawing);
		drawingView = (DrawingView) this.findViewById(R.id.drawingView);
		Log.i("SIZE", "Drawing view is: "+drawingView.getWidth()+"x"+drawingView.getHeight());
	}

	/*@Override
	protected void onSaveInstanceState(Bundle bundle) {
		super.onSaveInstanceState(bundle);
		
		drawingView.saveInstanceState(bundle);
	}*/
	
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
	public void onLineProperties(View view)
	{
		Intent intent = new Intent(this, LineSelectActivity.class); 
    	startActivityForResult(intent, this.GOT_LINE);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == GOT_LINE && resultCode == Activity.RESULT_OK) {
			// This is a color response
//			int color = data.getIntExtra(LineSelectActivity.COLOR, Color.BLACK);
//			drawView.setColor(color);
			
			// This is a line width response
		
		//TODO Set the color somewhere
		
		}
	}
	
}
