package edu.msu.project1.barrelofmonkeys;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class DrawingView extends View {

	private class Line implements Parcelable {
		public ArrayList<Point> mPoints = new ArrayList<Point>();
		
		private int mNumPoints = 0;
		private Point mLastPoint = new Point(-10, -10);
		
		/*
		 * Allocate 60 points at a go, allowing us to draw 360px before
		 * reallocations.  This helps avoid allocations during onDraw()
		 */
		private int ALLOCATION_SIZE = 60;
		private double POINT_SEPARATION = 6.0;
		
		private transient Paint mPaint = new Paint();
		
		Line(int color, float width) {
			mNumPoints = 0;
			for(int i = 0; i < ALLOCATION_SIZE; i++) {
				mPoints.add(new Point(-1, -1));
			}
			
			mPaint.setColor(color);
			mPaint.setStrokeWidth(width);
			mPaint.setStrokeCap(Paint.Cap.ROUND);
		}
		
		public boolean addPoint(int x, int y) {
			double dist = Math.sqrt((x - mLastPoint.x) * (x - mLastPoint.x) + (y - mLastPoint.y) * (y - mLastPoint.y));
			if(dist > POINT_SEPARATION) {
				if(mNumPoints >= mPoints.size()) {
					for(int i = 0; i < ALLOCATION_SIZE; i++) {
						mPoints.add(new Point(-1, -1));
					}
				}
				mLastPoint = mPoints.get(mNumPoints);
				mLastPoint.x = x;
				mLastPoint.y = y;
				mNumPoints += 1;
				
				return true;
			}
			return false;
		}

		@Override
		public int describeContents() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public void writeToParcel(Parcel out, int flags) {
			// TODO Auto-generated method stub
			
		}
		
		public void drawIn(Canvas canvas) {
			if(mNumPoints == 1) {
				canvas.drawPoint(mLastPoint.x, mLastPoint.y, mPaint);
			}
			for(int i = 0; i < mNumPoints - 1; i++) {
				Point p1 = mPoints.get(i);
				Point p2 = mPoints.get(i+1);
				canvas.drawLine(p1.x, p1.y, p2.x, p2.y, mPaint);
			}
		}
	}
	
	private Paint mPaint = new Paint();
	private ArrayList<Line> mLines = new ArrayList<Line>();
	private float mLineWidth;
	private int mLineColor;
	private Line mCurLine;
	
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

	
	/* (non-Javadoc)
	 * @see android.view.View#onDraw(android.graphics.Canvas)
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
//		canvas.drawLine(0, 0, 200, 200, mPaint);
		
		for(Line l : mLines) {
			l.drawIn(canvas);
		}
		
	}

	/* (non-Javadoc)
	 * @see android.view.View#onTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getActionMasked();
		boolean added = false;
		
		switch(action) {
			case MotionEvent.ACTION_DOWN:
				added = mCurLine.addPoint((int)event.getX(), (int)event.getY());
				if(added) {
					invalidate();
				}
				return true;
			
			case MotionEvent.ACTION_MOVE:
				added = mCurLine.addPoint((int)event.getX(), (int)event.getY());
				if(added) {
					invalidate();
				}
				return true;
				
			case MotionEvent.ACTION_CANCEL:
			case MotionEvent.ACTION_UP:
				
				mCurLine = new Line(mLineColor, mLineWidth);
				mLines.add(mCurLine);
				Log.i("LINES", "Currently "+mLines.size()+" lines on the canvas");
				return true;
		}
		
		return super.onTouchEvent(event);
	}

	
}
