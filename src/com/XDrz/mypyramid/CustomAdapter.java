package com.XDrz.mypyramid;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.XDrz.mypyramid.controller.ControlUtils;
import com.XDrz.mypyramid.controller.ControlUtils.CustomOnClickListener;
import com.XDrz.mypyramid.controller.GestureListener;
import com.XDrz.mypyramid.data.PyramidData;

/**
 * Deprecated
 * @author MT02694
 *
 */
class CustomAdapter extends ArrayAdapter<PyramidData> {

	public static final String TAG = "CustomAdapter";
	private int layoutResourceId = -1;
	static final int num = PyramidData.numColumn;
	private SimpleDateFormat df;
	private EditText summaryEditText;
	private TextView[] dateTexts = new TextView[num];
	private ImageView[] checkImages = new ImageView[num];
//	private View[] itemLayouts = new View[num];
	private CustomOnClickListener adaterItemListener;
//	private Activity activity;
	
	public CustomAdapter(Activity activity, int layoutResourceId, ArrayList<PyramidData> objects) {
		super(activity, layoutResourceId, objects);
//		this.activity = activity;
		this.layoutResourceId = layoutResourceId;
		adaterItemListener = new ControlUtils.CustomOnClickListener(activity, 0);//hard code 0
//		Log.v(TAG, ""+this.layoutResourceId);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
	        convertView = LayoutInflater.from(getContext()).inflate(layoutResourceId, null);
	    }
		
	    PyramidData item = getItem(position);
	    if (item != null) {
	    	Date[] dates = item.getDateArrayClone();
	    	
	    	summaryEditText = (EditText)convertView.findViewById(R.id.itemSummaryEditText);
	    	summaryEditText.setText(item.getSummary());
	    	
	        
	        for(int a=0;a<num;a++) {
	        	dateTexts[a] = (TextView)convertView.findViewById(ControlUtils.itemDateTextIds[a]);
	        	
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
	        	
	        	checkImages[a] = (ImageView)convertView.findViewById(ControlUtils.itemCheckIconIds[a]);
	        	checkImages[a].setVisibility(View.INVISIBLE);
	        	
	        	dateTexts[a].setOnTouchListener(new GestureListener(checkImages[a], 0, null));////TODO hardcode 0, pyramidDbManager
//	        	itemLayouts[a] = convertView.findViewById(itemLayoutIds[a]);
//	        	itemLayouts[a].setOnTouchListener(new GestureListener(checkImages[a]));
	        }
	        
	    }
	    return convertView;
	}
}
