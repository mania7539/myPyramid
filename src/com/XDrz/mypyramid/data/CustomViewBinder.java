package com.XDrz.mypyramid.data;

import android.app.Activity;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter.ViewBinder;
import android.widget.TextView;

import com.XDrz.mypyramid.R;
import com.XDrz.mypyramid.controller.ControlUtils;
import com.XDrz.mypyramid.controller.ControlUtils.CustomOnClickListener;
import com.XDrz.mypyramid.controller.GestureListener;

public class CustomViewBinder implements ViewBinder {
	public static final String TAG = "CustomViewBinder";
	
	private String tempText;
	private Boolean tempCheck;
	private CustomOnClickListener adaterItemListener;
	private Activity activity;
	static final int num = PyramidData.numColumn;
//	private SimpleDateFormat df;
	private EditText summaryEditText;
	private TextView[] dateTexts = new TextView[num];
	private ImageView[] checkImages = new ImageView[num];
	private PyramidDbManager pyramidDbManager;
	public CustomViewBinder(Activity activity) {
		this.activity = activity;
		this.pyramidDbManager = new PyramidDbManager(activity);
	}

	@Override
	public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
		int a;
		int cId = cursor.getInt(cursor.getColumnIndex(PyramidDbManager.C_ID));
//		Log.v(TAG, "cId: "+cId);
		// can bind the data: return true, else false
		if (view.getId() == R.id.itemSummaryEditText) {
			tempText = cursor.getString(
					cursor.getColumnIndex(PyramidDbManager.C_SUMMARY));
			summaryEditText = ((EditText) view);
			summaryEditText.setText(tempText);
		}
		a=0;
		for(int id: ControlUtils.itemDateTextIds) {
			if(view.getId() == id) {
//				Log.v(TAG, "itemDateTextIds: "+a);
				tempText = cursor.getString(cursor.getColumnIndex(DataUtils.dateColumnName[a]));

				adaterItemListener = new CustomOnClickListener(activity, cId);
				dateTexts[a] = ((TextView) view);
				dateTexts[a].setText(tempText);
				
				//TODO conflict with OnTouchListener
				dateTexts[a].setOnClickListener(adaterItemListener);
				
				//TODO see which one, imageView or textView, goes before the other
//				if(checkImages[a]!=null) {
//					dateTexts[a].setOnTouchListener(new GestureListener(checkImages[a], cId, pyramidDbManager));
//				}
			}
			a++;
		}
		a=0;
		for(int id: ControlUtils.itemCheckIconIds) {
			if(view.getId() == id) {
//				Log.v(TAG, "itemCheckIconIds: "+a);
				//TODO get isVisible
				tempCheck = DataUtils.integerToBoolean(
						cursor.getInt(cursor.getColumnIndex(DataUtils.isDoneColumnName[a])));
				checkImages[a] = ((ImageView) view);
				if(!tempCheck) {
					checkImages[a].setVisibility(View.INVISIBLE);
				} else {
					checkImages[a].setVisibility(View.VISIBLE);
				}
//				if(dateTexts[a]!=null) {
//					dateTexts[a].setOnTouchListener(new GestureListener(checkImages[a], cId, pyramidDbManager));
//				}
			}
			a++;
		}
		return true;
	}
	
}


