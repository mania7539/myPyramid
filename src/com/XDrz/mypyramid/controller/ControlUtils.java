package com.XDrz.mypyramid.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.TextView;

import com.XDrz.mypyramid.PyramidActivity;
import com.XDrz.mypyramid.R;
import com.XDrz.mypyramid.data.DataUtils;
import com.XDrz.mypyramid.data.PyramidData;
import com.XDrz.mypyramid.data.PyramidDbManager;

public class ControlUtils {
	public static final int itemDateTextIds[] = { 
			R.id.itemDateText1, R.id.itemDateText2,
			R.id.itemDateText3, R.id.itemDateText4, R.id.itemDateText5,
			R.id.itemDateText6, R.id.itemDateText7, R.id.itemDateText8 };
	public static final int itemCheckIconIds[] = { 
			R.id.itemCheckIcon1, R.id.itemCheckIcon2,
			R.id.itemCheckIcon3, R.id.itemCheckIcon4, R.id.itemCheckIcon5,
			R.id.itemCheckIcon6, R.id.itemCheckIcon7, R.id.itemCheckIcon8 };
	public static final int itemLayoutIds[] = { 
			R.id.itemLayout1, R.id.itemLayout2,
			R.id.itemLayout3, R.id.itemLayout4, R.id.itemLayout5,
			R.id.itemLayout6, R.id.itemLayout7, R.id.itemLayout8 };
	
//	private static volatile TextView callbackTextView;
	private static int itemId;
	private static int callbackViewId;
	
	private synchronized static void setItemId(int id) {
		itemId = id;
	}
	private static int getItemId() {
		return itemId;
	}
	
	private synchronized static void setCallbackViewId(int id) {
		callbackViewId = id;
	}
	private static int getCallbackViewId() {
		return callbackViewId;
	}
	
	public static PyramidDbManager getPyramidDatabaseManager(Context context) {
		return new PyramidDbManager(context);
	}
	
	public static int getIndexOfViewInEachItem(int callbackViewId, int[] ids) {
		int index = 0;
		for(int id: ids) {
			if(callbackViewId==id) {
				break;
			}
			index++;
		}
		return index;
	}
	
	public static class CustomOnClickListener implements OnClickListener {
		public static final String TAG = "CustomOnClickListener";
		private Activity activity;
		private int cId;
		public CustomOnClickListener(Activity activity, int cId) {
			this.activity = activity;
			this.cId = cId;
			
		}
		@SuppressWarnings("deprecation")
		@Override
		public void onClick(View v) {
			activity.showDialog(PyramidActivity.PRM_DATE_DIALOG);
			setItemId(cId);
			setCallbackViewId(v.getId());
			Log.v(TAG+this.hashCode(), "ONCLICK!!!");
		}
	};	
	
	
	public static class CustomOnDateSetListener implements DatePickerDialog.OnDateSetListener {
		public static final String TAG = "CustomOnDateSetListener";
		private PyramidDbManager pyramidDbManager;
		private Activity activity;
		public CustomOnDateSetListener(Activity activity, PyramidDbManager pyramidDbManager) {
			this.pyramidDbManager = pyramidDbManager;
			this.activity = activity;
		}

		// when dialog box is closed, below method will be called.
		public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
			SimpleDateFormat df = DataUtils.getFullDateFormat();
			PyramidData item = DataUtils.getPyramidDataFromCursor(pyramidDbManager, itemId);
			Log.v(TAG, "ViewID: "+getCallbackViewId());
//			synchronized (ControlUtils.class) {
//				
//			}
			int index = getIndexOfViewInEachItem(callbackViewId, itemDateTextIds);
			
			Calendar calendar = Calendar.getInstance(); 
			calendar.setTime(item.getDateArrayClone()[index]);
			calendar.set(Calendar.YEAR, selectedYear);
			calendar.set(Calendar.MONTH, selectedMonth);
			calendar.set(Calendar.DAY_OF_MONTH, selectedDay);
			
			TextView callbackTextView = (TextView)activity.findViewById(getCallbackViewId());
			callbackTextView.setText(df.format(calendar.getTime()));
			item.setDateArray(index, calendar.getTime());
			Log.v(TAG, "cID: "+getItemId());
			pyramidDbManager.update(getItemId(), item);
			
//			callbackTextView = null;
		}
	};
}
