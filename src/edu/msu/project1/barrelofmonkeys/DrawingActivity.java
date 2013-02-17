package edu.msu.project1.barrelofmonkeys;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
	
	//Make a button so that when they're done, it passes the drawing,
	//the hint and the answer to the guessing activity
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drawing);
		
		drawingView = (DrawingView) this.findViewById(R.id.drawingView);
		if(savedInstanceState != null) {
			drawingView.loadDrawing(savedInstanceState);
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle bundle) {
		super.onSaveInstanceState(bundle);
		drawingView.saveDrawing(bundle);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_drawing, menu);
		return true;
	}
	
	public void onSubmit(View view) {
		Intent intent = new Intent(this, HintSelectionActivity.class);
		startActivity(intent);
	}
	public void onLineProperties(View view) {
		String[] widthNames = {"Pencil", "Thinner", "Thin", "Thick", "Thicker", "Thickest"};
		
		AlertDialog.Builder b = new AlertDialog.Builder(this);
		b.setTitle("Choose Line Width")
		.setItems(widthNames, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int choice) {
				float[] widthSizes = {5f, 8f, 10f, 20f, 25f, 35f};
				drawingView.setLineWidth(widthSizes[choice]);
				dialog.dismiss();
			}
		}).show();
		
	}
	
}
