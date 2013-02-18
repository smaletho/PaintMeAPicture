package edu.msu.project1.barrelofmonkeys;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class DrawingView extends View {
	
	public String USER_DRAWING_KEY = "edu.msu.monkies.drawing.user_data";
	
	private Paint mPaint = new Paint();
	
	private ArrayList<Line> mLines = new ArrayList<Line>();
	private Line mCurLine;
	
	private float mLineWidth;
	private int mLineColor;

	private boolean mIsDrawing = true;
	private float mZoomLevel = 1f;
	private float mOffsetX = 0f;
	private float mOffsetY = 0f;
	
	private float lastX1 = -1;
	private float lastY1 = -1;
	private float lastX2 = -1;
	private float lastY2 = -1;
	
	
	private void init() {
		mLineWidth = 4.0f;
		mLineColor = 0xffdd00dd;
		mCurLine = new Line(mLineColor, mLineWidth);
		mLines.add(mCurLine);
		
		mPaint.setColor(0xdd00dd);
	}
	
	public DrawingView(Context context) {
		super(context);
		init();
	}

	public DrawingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public DrawingView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	
	public void setLineWidth(float width) {
		mLineWidth = width;
		
		if(mCurLine != null) {
			mCurLine.setWidth(width);
		}
	}
	
	public void setLineColor(int color) {
		mLineColor = color;
		
		if(mCurLine != null) {
			mCurLine.setColor(color);
		}
	}
	
	/* (non-Javadoc)
	 * @see android.view.View#onDraw(android.graphics.Canvas)
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
//		canvas.drawLine(0, 0, 200, 200, mPaint);
		Log.i("OFFSET", "Translate: "+mOffsetX+"x"+mOffsetY);
		canvas.translate(mOffsetX, mOffsetY);
		
		for(Line l : mLines) {
			l.drawIn(canvas);
		}
		
	}

	
	private void copyTouches(MotionEvent event) {
		lastX1 = event.getX();
		lastY1 = event.getY();
		
		lastX2 = event.getX();
		lastY2 = event.getY();
	}
	/* (non-Javadoc)
	 * @see android.view.View#onTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getActionMasked();
		
		if(mIsDrawing) {
			boolean added = false;
			switch(action) {
				case MotionEvent.ACTION_DOWN:
					added = mCurLine.addPoint((int)(event.getX() - mOffsetX), (int)(event.getY() - mOffsetY));
					if(added) {
						invalidate();
					}
					return true;
				
				case MotionEvent.ACTION_MOVE:
					added = mCurLine.addPoint((int)(event.getX() - mOffsetX), (int)(event.getY() - mOffsetY));
					if(added) {
						invalidate();
					}
					return true;

				case MotionEvent.ACTION_CANCEL:
				case MotionEvent.ACTION_UP:
					
					mCurLine = new Line(mLineColor, mLineWidth);
					mLines.add(mCurLine);
					return true;
			}
		} else {
			switch(action) {
				case MotionEvent.ACTION_DOWN:
					copyTouches(event);
					return true;
				
				case MotionEvent.ACTION_MOVE:
					if(event.getPointerCount() == 1) {
						mOffsetX -= (lastX1 - event.getX());
						mOffsetY -= (lastY1 - event.getY());
						
						copyTouches(event);
						invalidate();
					}
					return true;
	
				case MotionEvent.ACTION_POINTER_DOWN:
					
					return true;
					
				case MotionEvent.ACTION_CANCEL:
				case MotionEvent.ACTION_UP:
					
					return true;
			}
		}
				
		return super.onTouchEvent(event);
	}

	public void saveDrawing(Bundle b) {
		b.putParcelableArrayList(USER_DRAWING_KEY, mLines);
	}
	
	public void loadDrawing(Bundle b) {
		mLines = b.getParcelableArrayList(USER_DRAWING_KEY);
		mLines.add(mCurLine);
	}
	
	public void clearDrawing() {
		mLines = new ArrayList<Line>();
		mCurLine = new Line(mLineColor, mLineWidth);
	}
	
	public void toggleDraw(boolean drawing) {
		mIsDrawing = drawing;
	}
}
