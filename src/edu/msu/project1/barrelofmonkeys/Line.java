package edu.msu.project1.barrelofmonkeys;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Parcel;
import android.os.Parcelable;

public class Line implements Parcelable {

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
	private transient Path mPath = new Path();
	
	public static final Parcelable.Creator<Line> CREATOR = new Parcelable.Creator<Line>() {
		@Override
		public Line createFromParcel(Parcel in) {
			return new Line(in);
		}
		@Override
		public Line[] newArray(int size) {
			return new Line[size];
		}
	};
	
	private void init() {
		mNumPoints = 0;
		
		mPaint.setStrokeCap(Paint.Cap.ROUND);
		mPaint.setStrokeJoin(Paint.Join.ROUND);
		mPaint.setStyle(Paint.Style.STROKE);
	}
	
	public Line(int color, float width) {
		init();
		
		for(int i = 0; i < ALLOCATION_SIZE; i++) {
			mPoints.add(new Point(-1, -1));
		}
		
		mPaint.setColor(color);
		mPaint.setStrokeWidth(width);
	}
	
	private Line(Parcel in) {
		init();
		
		mNumPoints = in.readInt();
		
		mPaint.setColor(in.readInt());
		mPaint.setStrokeWidth(in.readFloat());
		
		in.readList(mPoints, null);
		
		if(mNumPoints > 0) {
			Point start = mPoints.get(0);
			mPath.moveTo(start.x, start.y);
			for(int i = 1; i < mNumPoints; i++) {
				Point p = mPoints.get(i);
				mPath.lineTo(p.x, p.y);
			}
		}
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeInt(mNumPoints);
		out.writeInt(mPaint.getColor());
		out.writeFloat(mPaint.getStrokeWidth());
		
		out.writeList(mPoints);
	}

	public void setWidth(float width) {
		mPaint.setStrokeWidth(width);
	}
	
	public void setColor(int color) {
		mPaint.setColor(color);
	}
	
	public boolean addPoint(int x, int y, float zoom) {
		if(mNumPoints == 0) {
			mPath.moveTo(x, y);
		}
		
		double dist = Math.sqrt((x - mLastPoint.x) * (x - mLastPoint.x) + (y - mLastPoint.y) * (y - mLastPoint.y));
		if(dist > POINT_SEPARATION / zoom) {
			if(mNumPoints >= mPoints.size()) {
				for(int i = 0; i < ALLOCATION_SIZE; i++) {
					mPoints.add(new Point(-1, -1));
				}
			}
			mLastPoint = mPoints.get(mNumPoints);
			mLastPoint.x = x;
			mLastPoint.y = y;
			mNumPoints += 1;
			
			mPath.lineTo(x, y);
			return true;
		}
		return false;
	}
	
	public void drawIn(Canvas canvas) {
		if(mNumPoints == 1) {
			canvas.drawPoint(mLastPoint.x, mLastPoint.y, mPaint);
		}
		canvas.drawPath(mPath, mPaint);
	}
}
