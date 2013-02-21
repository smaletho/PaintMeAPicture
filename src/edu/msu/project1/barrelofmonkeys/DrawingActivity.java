package edu.msu.project1.barrelofmonkeys;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class DrawingActivity extends Activity {

	/*
	 * The drawing view in this activity's view
	 */
	private DrawingView drawingView;
	
	private TextView categoryChoice;
	
	private boolean isDrawing = true;
	
	private static final int GOT_COLOR = 2;
	
	public enum Category{
		ANIMAL,
		BUILDING,
		OBJECT,
		ACTION,
		MSU;
		
		private static final List<Category> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
		private static final int SIZE = VALUES.size();
		private static final Random RANDOM = new Random();
		
		public static Category randomChoice() {
			return VALUES.get(RANDOM.nextInt(SIZE));
		}
	}
	
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
		
		categoryChoice = (TextView) this.findViewById(R.id.categoryText);
		
		categoryChoice.setText(Category.randomChoice().toString());
		
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
	
	public void onSubmit(final View view) {
		final AlertDialog.Builder alert_builder = new AlertDialog.Builder(this);
		
		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(1); //Vertical
		
		final EditText name = new EditText(this);
		final EditText hint = new EditText(this);
		
		name.setHint("Name of the drawing");
		hint.setHint("Hint for player");
		
		name.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
		hint.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
		
		final TextView nameTitle = new TextView(this);
		final TextView hintTitle = new TextView(this);
		
		nameTitle.setText("Name of Drawing");
		hintTitle.setText("Name of Hint");
		
		layout.addView(nameTitle);
		layout.addView(name);
		layout.addView(hintTitle);
		layout.addView(hint);
		
		alert_builder.setView(layout);
		
		alert_builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				params.nameText = name.getText().toString();
				params.hintText = hint.getText().toString();
				if(params.nameText.equals("") || params.hintText.equals("")){
					Toast.makeText(getApplicationContext(), "You must set a title and a hint", Toast.LENGTH_SHORT).show();
				}
				else{
					Intent intent = new Intent(getBaseContext(), GuessingActivity.class);
					startActivity(intent);
				}
			}
		});
		
		alert_builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		alert_builder.show();
	}
	
	public void onLineWidth(View view) {
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
	
	public void onLineColor(View view) {
		Intent intent = new Intent(this, ColorSelectActivity.class); 
    	startActivityForResult(intent, GOT_COLOR);
	}
	
    /**
     * Function called when we get a result from some external 
     * activity called with startActivityForResult()
     * @param requestCode the request code we sent to the activity 
     * @param resultCode a result of from the activity - ok or cancelled
     * @param data data from the activity
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GOT_COLOR && resultCode == Activity.RESULT_OK) { 
        	// This is a color response
        	int color = data.getIntExtra(ColorSelectActivity.COLOR, Color.BLACK);
        	drawingView.setLineColor(color);
        }
    }
	
	public void onToggleDraw(View view) {
		isDrawing = !isDrawing;
		drawingView.toggleDraw(isDrawing);
		
	}
	private static final String PARAMETERS = "parameters";

	private static class Parameters implements Serializable {

		private static final long serialVersionUID = 1L;

		private String nameText;
		private String hintText;

	}
    /**
     * The current parameters
     */
    private Parameters params = new Parameters();
}
