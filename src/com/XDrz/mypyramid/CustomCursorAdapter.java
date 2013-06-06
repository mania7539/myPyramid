package com.XDrz.mypyramid;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.XDrz.mypyramid.controller.ControlUtils;
import com.XDrz.mypyramid.controller.GestureListener;
import com.XDrz.mypyramid.data.PyramidData;

/**
 * Deprecated
 * @author MT02694
 *
 */
public class CustomCursorAdapter extends SimpleCursorAdapter {
	public static final String TAG = "SimpleCursorAdapter";
	private int layoutResourceId = -1;
	static final int num = PyramidData.numColumn;
	private SimpleDateFormat df;
	private EditText summaryEditText;
	private TextView[] dateTexts = new TextView[num];
	private ImageView[] checkImages = new ImageView[num];
//	private View[] itemLayouts = new View[num];
	private TextView callbackTextView;
	
	private CustomOnClickListener adaterItemListener;
	private Activity activity;
	final int itemDateTextIds[] = {
			R.id.itemDateText1, R.id.itemDateText2, R.id.itemDateText3,
			R.id.itemDateText4, R.id.itemDateText5, R.id.itemDateText6,
			R.id.itemDateText7, R.id.itemDateText8
	};
	final int itemCheckIconIds[] = {
			R.id.itemCheckIcon1, R.id.itemCheckIcon2, R.id.itemCheckIcon3, 
			R.id.itemCheckIcon4, R.id.itemCheckIcon5, R.id.itemCheckIcon6, 
			R.id.itemCheckIcon7, R.id.itemCheckIcon8
	};
	final int itemLayoutIds[] = {
			R.id.itemLayout1, R.id.itemLayout2, R.id.itemLayout3, 
			R.id.itemLayout4, R.id.itemLayout5, R.id.itemLayout6,
			R.id.itemLayout7, R.id.itemLayout8
	};
	
	public CustomCursorAdapter(Activity activity, int layout, Cursor c,
			String[] from, int[] to, int flag) {
		super(activity, layout, c, from, to, flag);
		this.activity = activity;
		this.layoutResourceId = layout;
		adaterItemListener = new CustomOnClickListener(activity);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
	        convertView = LayoutInflater.from(activity).inflate(layoutResourceId, null);
	    }
		
		//error
	    PyramidData item = (PyramidData)getItem(position);
	    if (item != null) {
	    	Date[] dates = item.getDateArrayClone();
	    	
	    	summaryEditText = (EditText)convertView.findViewById(R.id.itemSummaryEditText);
	    	summaryEditText.setText(item.getSummary());
	    	
	        
	        for(int a=0;a<num;a++) {
	        	dateTexts[a] = (TextView)convertView.findViewById(itemDateTextIds[a]);
	        	
	        	if(a!=1 && a!=2) {
	        		df = new SimpleDateFormat("M/dd,\nyyyy", Locale.ENGLISH);
		        	dateTexts[a].setText(df.format(dates[a]));
		        	dateTexts[a].setOnClickListener(adaterItemListener);
	        	} else if(a==1) {
	        		df = new SimpleDateFormat("k:m", Locale.ENGLISH);
	        		dateTexts[a].setText(df.format(dates[a]));
	        	} else if(a==2) {
	        		df = new SimpleDateFormat("k:m", Locale.ENGLISH);
	        		dateTexts[a].setText(df.format(dates[a]));
	        	}
	        	
	        	checkImages[a] = (ImageView)convertView.findViewById(itemCheckIconIds[a]);
	        	checkImages[a].setVisibility(View.INVISIBLE);
	        	
	        	dateTexts[a].setOnTouchListener(new GestureListener(checkImages[a], 
	        			0, ControlUtils.getPyramidDatabaseManager(activity)));//TODO hardcode 0
//	        	itemLayouts[a] = convertView.findViewById(itemLayoutIds[a]);
//	        	itemLayouts[a].setOnTouchListener(new GestureListener(checkImages[a]));
	        }
	        
	    }
	    return convertView;
	}
	
	
	public class CustomOnClickListener implements OnClickListener {
		private Activity activity;
		public CustomOnClickListener(Activity activity) {
			this.activity = activity;
		}
		@SuppressWarnings("deprecation")
		@Override
		public void onClick(View v) {
			callbackTextView = (TextView)v;
			activity.showDialog(PyramidActivity.PRM_DATE_DIALOG);
		}
	};	
	
	
	public class CustomOnDateSetListener implements DatePickerDialog.OnDateSetListener {
		
		// when dialog box is closed, below method will be called.
		public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.YEAR, selectedYear);
			calendar.set(Calendar.MONTH, selectedMonth);
			calendar.set(Calendar.DAY_OF_MONTH, selectedDay);
			
			SimpleDateFormat df = new SimpleDateFormat("M/dd,\nyyyy", Locale.ENGLISH);
			callbackTextView.setText(df.format(calendar.getTime()));
			
		}
	};
}
