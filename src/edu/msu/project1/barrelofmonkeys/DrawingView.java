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
	
	private class Touch {
		public float x = 0;
		public float y = 0;
		
		public float lastX = 0;		
		public float lastY = 0;
		
		public int id = -1;
		
		public void clearTouch() {
			id = -1;
			x = lastX = y = lastY = 0;
		}
		
		public void copyToLast() {
			lastX = x;
			lastY = y;
		}
		
		public float dx() {
			return x - lastX;
		}
		public float dy() {
			return y - lastY;
		}
	}
	
	private Paint mPaint = new Paint();
	
	private ArrayList<Line> mLines = new ArrayList<Line>();
	private Line mCurLine;
	
	private float mLineWidth;
	private int mLineColor;

	private boolean mIsDrawing = true;
	private float mZoomLevel = 1f;
	private float mOffsetX = 0f;
	private float mOffsetY = 0f;
	
	private Touch touch1 = new Touch();
	private Touch touch2 = new Touch();
	
	public void setDrawing(boolean flag) {
		mIsDrawing = flag;
	}
	
	
	private void init() {
		mLineWidth = 4.0f;
		mLineColor = 0xffdd00dd;
		mCurLine = new Line(mLineColor, mLineWidth);
		mLines.add(mCurLine);
		
		mPaint.setColor(0x00000000);
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
		
		canvas.translate(mOffsetX, mOffsetY);
		canvas.scale(mZoomLevel, mZoomLevel);
		
		for(Line l : mLines) {
			l.drawIn(canvas);
		}
		
		Log.i("debug", "dx: "+mOffsetX+"\tdy: "+mOffsetY+"\tscale: "+mZoomLevel);
		canvas.drawText("dx: "+mOffsetX+"\ndy: "+mOffsetY+"\nscale: "+mZoomLevel, 0, 0, mPaint);
	}

	
	private void copyTouches(MotionEvent event) {
		for(int i = 0; i < event.getPointerCount(); i++) {
			int id = event.getPointerId(i);
			
			if(id == touch1.id){
				touch1.copyToLast();
				touch1.x = event.getX(i);
				touch1.y = event.getY(i);
			} else if(id == touch2.id) {
				touch2.copyToLast();
				touch2.x = event.getX(i);
				touch2.y = event.getY(i);
			}
		}
	}
	
	private float distance(float x1, float y1, float x2, float y2) {
    	double dx = Math.pow(x2 - x1, 2);
    	double dy = Math.pow(y2 - y1, 2);
    	return (float)Math.sqrt(dx + dy);
    }
	
	private void move() {
		if(touch1.id < 0) { 
			return ; 
		}
		
		mOffsetX += touch1.dx();
		mOffsetY += touch1.dy();
		
		if(touch2.id >= 0) {
			float dist1 = distance(touch1.lastX, touch1.lastY, touch2.lastX, touch2.lastY);
	        float dist2 = distance(touch1.x, touch1.y, touch2.x, touch2.y);
	        mZoomLevel *= dist2 / dist1;
		}
		

        invalidate();
	}
	
	/* (non-Javadoc)
	 * @see android.view.View#onTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getActionMasked();
		int id = event.getPointerId(event.getActionIndex());
		
		if(mIsDrawing) {
			boolean added = false;
			int convertedX = (int)( (event.getX() / mZoomLevel - mOffsetX) );
			int convertedY = (int)( (event.getY() / mZoomLevel - mOffsetY) );
			
			switch(action) {
				case MotionEvent.ACTION_DOWN:		
					added = mCurLine.addPoint(convertedX, convertedY, mZoomLevel);
					if(added) {
						invalidate();
					}
					return true;
				
				case MotionEvent.ACTION_MOVE:
					added = mCurLine.addPoint(convertedX, convertedY, mZoomLevel);
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
					touch1.id = id;
					copyTouches(event);
					touch1.copyToLast();
					return true;
				
				case MotionEvent.ACTION_MOVE:
					copyTouches(event);
					move();
					return true;
	
				case MotionEvent.ACTION_POINTER_DOWN:
					if(touch2.id < 0) {
						touch2.id = id;
						copyTouches(event);
						touch2.copyToLast();
					}
					return true;
					
				case MotionEvent.ACTION_POINTER_UP:
					if(id == touch2.id) {
		                touch2.id = -1;
		            } else if(id == touch1.id) {
		                // Make what was touch2 now be touch1 by 
		                // swapping the objects.
		                Touch t = touch1;
		                touch1 = touch2;
		                touch2 = t;
		                touch2.clearTouch();
		            }
					return true;
					
				case MotionEvent.ACTION_CANCEL:
				case MotionEvent.ACTION_UP:
					touch1.clearTouch();
					touch2.clearTouch();
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
