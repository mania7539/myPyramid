package com.XDrz.mypyramid.controller;

import java.util.ArrayList;

import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.XDrz.mypyramid.data.DataUtils;
import com.XDrz.mypyramid.data.PyramidData;
import com.XDrz.mypyramid.data.PyramidDbManager;

public class GestureListener implements OnTouchListener {
	// implements OnTouchListener{
	public static final String TAG = "GestureListener";
	public static final int DIRECTION_UP 		= 12;
	public static final int DIRECTION_RIGHTUP 	= 1;
	public static final int DIRECTION_RIGHT 	= 3;
	public static final int DIRECTION_RIGHTDOWN = 4;
	public static final int DIRECTION_DOWN 		= 6;
	public static final int DIRECTION_LEFTDOWN 	= 7;
	public static final int DIRECTION_LEFT 		= 9;
	public static final int DIRECTION_LEFTUP 	= 10;
//	private GestureDetectorCompat gestureDetectorCompat;
	private static final ArrayList<Integer> directions = new ArrayList<Integer>();
	private int prevX = -1;
	private int prevY = -1;
	private View targetView;// , sensor
	private PyramidDbManager pyramidDbManager;
	private int cId;
	/**
	 * Those use register function are the sensors, those set to param for
	 * responding are the targets.
	 * 
	 * @param targetView
	 */
	public GestureListener(View targetView, int cId, PyramidDbManager pyramidDbManager) {
		// sensor = sensorView;
		this.targetView = targetView;
		this.pyramidDbManager = pyramidDbManager;
		this.cId = cId;
//		gestureDetectorCompat = new GestureDetectorCompat(activity, this);

	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		final int action = MotionEventCompat.getActionMasked(event);

		switch (action) {
		case MotionEvent.ACTION_DOWN:
			Log.v(TAG, "Gesture down!!!");
			// prevMotionEvent = event;
			prevX = (int) event.getX();
			prevY = (int) event.getY();
			return true;
		case MotionEvent.ACTION_MOVE:
			Log.v(TAG, "Gesture move!!!");
			if (prevX != -1 && prevY != -1) {
				int xDirection = (int) event.getX() - prevX;
				int yDirection = (int) event.getY() - prevY;
				Log.v(TAG, String.format("Gesture: xD=%d, yD=%d",
						(int) xDirection, (int) yDirection));
				if (xDirection > 0 && yDirection == 0) {
					Log.v(TAG, "Gesture RIGHT");
					directions.add(GestureListener.DIRECTION_RIGHT);
				} else if (xDirection > 0 && yDirection < 0) {
					Log.v(TAG, "Gesture RIGHTUP");
					directions.add(GestureListener.DIRECTION_RIGHTUP);
				} else if (xDirection == 0 && yDirection < 0) {
					Log.v(TAG, "Gesture UP");
					directions.add(GestureListener.DIRECTION_UP);
				} else if (xDirection < 0 && yDirection < 0) {
					Log.v(TAG, "Gesture LEFTUP");
					directions.add(GestureListener.DIRECTION_LEFTUP);
				} else if (xDirection <= 0 && yDirection == 0) {
					Log.v(TAG, "Gesture LEFT");
					directions.add(GestureListener.DIRECTION_LEFT);
				} else if (xDirection < 0 && yDirection > 0) {
					Log.v(TAG, "Gesture LEFTDOWN");
					directions.add(GestureListener.DIRECTION_LEFTDOWN);
				} else if (xDirection == 0 && yDirection > 0) {
					Log.v(TAG, "Gesture DOWN");
					directions.add(GestureListener.DIRECTION_DOWN);
				} else if (xDirection > 0 && yDirection > 0) {
					Log.v(TAG, "Gesture RIGHTDOWN");
					directions.add(GestureListener.DIRECTION_RIGHTDOWN);
				}
			}
			prevX = (int) event.getX();
			prevY = (int) event.getY();
			// prevMotionEvent = event;
			return true;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			Log.v(TAG, "Gesture cancel!!!");
			int index = ControlUtils.getIndexOfViewInEachItem(targetView.getId(), ControlUtils.itemCheckIconIds);
//			Log.v(TAG, String.format("id:%d, index: %d", cId, index));
			PyramidData item;
			if(checkIfFlingRight()) {
				Log.v(TAG, "Gesture fling left detection = true");
				item = DataUtils.getPyramidDataFromCursor(pyramidDbManager, cId);
				item.setIsDoneArray(index, Boolean.TRUE);
				pyramidDbManager.update(cId, item);
				
				targetView.setVisibility(View.VISIBLE);
				directions.clear();
				
			} else if(checkIfFlingLeft()) {
				Log.v(TAG, "Gesture fling right detection = true");
				item = DataUtils.getPyramidDataFromCursor(pyramidDbManager, cId);
				item.setIsDoneArray(index, Boolean.FALSE);
				pyramidDbManager.update(cId, item);
				
				targetView.setVisibility(View.INVISIBLE);
				directions.clear();
			}
			
//			if (checkIfGestureTick()) {//hard to control for ListView
//				Log.v(TAG, "Gesture tick detection = true");
//				targetView.setVisibility(View.VISIBLE);
//				directions.clear();
//			}
			return true;
		}
		return false;
	}

	private boolean checkIfGestureTick() {
		int prevDirection = -1;
		if (directions != null && !directions.isEmpty()) {
			prevDirection = directions.get(0);
		} else {
			return false;
		}
		for (int direction : directions) {
			if (prevDirection == GestureListener.DIRECTION_DOWN
					|| prevDirection == GestureListener.DIRECTION_LEFTDOWN) {
				if (direction == GestureListener.DIRECTION_UP
						|| direction == GestureListener.DIRECTION_RIGHTUP) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean checkIfFlingLeft() {
		if (directions != null && !directions.isEmpty()) {
			
		} else {
			return false;
		}
		for (int direction : directions) {
			if (direction == GestureListener.DIRECTION_LEFT
					|| direction == GestureListener.DIRECTION_LEFTDOWN
					|| direction == GestureListener.DIRECTION_LEFTUP) {
				return true;
			}
		}
		return false;
	}

	private boolean checkIfFlingRight() {
		if (directions != null && !directions.isEmpty()) {
			
		} else {
			return false;
		}
		for (int direction : directions) {
			if (direction == GestureListener.DIRECTION_RIGHT
					|| direction == GestureListener.DIRECTION_RIGHTDOWN
					|| direction == GestureListener.DIRECTION_UP) {
				return true;
			}
		}
		return false;
	}
}
