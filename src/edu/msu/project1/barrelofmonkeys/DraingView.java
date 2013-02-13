package edu.msu.project1.barrelofmonkeys;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class DraingView extends View {

	private class Line implements Parcelable {
		public ArrayList<Point> mPoints = new ArrayList<Point>();
		public int mColor;
		public float mWidth;
		private transient Paint mPaint = new Paint();
		
		Line(int color, float width, int x, int y) {
			mColor = color;
			mWidth = width;
			mPoints.add(new Point(x, y));
			
			mPaint.setStrokeCap(Paint.Cap.ROUND);
			mPaint.setStrokeWidth(width);
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
	}
	
	public DraingView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public DraingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public DraingView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see android.view.View#onDraw(android.graphics.Canvas)
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
	}

	/* (non-Javadoc)
	 * @see android.view.View#onTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return super.onTouchEvent(event);
	}

	
}
