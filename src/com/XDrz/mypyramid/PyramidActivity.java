package com.XDrz.mypyramid;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.SimpleCursorAdapter.ViewBinder;

import com.XDrz.mypyramid.controller.ControlUtils;
import com.XDrz.mypyramid.data.CustomViewBinder;
import com.XDrz.mypyramid.data.PyramidData;
import com.XDrz.mypyramid.data.PyramidDbManager;

public class PyramidActivity extends Activity {
	public static final int PRM_DATE_DIALOG = 0;
	private Calendar calendar;
	private int year, month, day;
	private ListView listView;
	private ImageButton plusButton, minusButton;
//	private CustomAdapter listAdapter;
	private PyramidDbManager pyramidDbManager;
	private Cursor cursor;
	private SimpleCursorAdapter simpleCursorAdapter;
	private ViewBinder viewBinder;
	
	private String[] cursorStrFroms = {
			PyramidDbManager.C_SUMMARY, PyramidDbManager.C_DATE1,
			PyramidDbManager.C_DATE2, PyramidDbManager.C_DATE3,
			PyramidDbManager.C_DATE4, PyramidDbManager.C_DATE4,
			PyramidDbManager.C_DATE5, PyramidDbManager.C_DATE6,
			PyramidDbManager.C_DATE7, PyramidDbManager.C_DATE8,
			PyramidDbManager.C_IS_DONE1, PyramidDbManager.C_IS_DONE2, 
			PyramidDbManager.C_IS_DONE3, PyramidDbManager.C_IS_DONE4, 
			PyramidDbManager.C_IS_DONE5, PyramidDbManager.C_IS_DONE6, 
			PyramidDbManager.C_IS_DONE7, PyramidDbManager.C_IS_DONE8
		};//no map no viewBinder view
	private int[] cursorIntTos = {
			R.id.itemSummaryEditText, R.id.itemDateText1,
			R.id.itemDateText1, R.id.itemDateText2, R.id.itemDateText3,
			R.id.itemDateText4, R.id.itemDateText5, R.id.itemDateText6,
			R.id.itemDateText7, R.id.itemDateText8,
			R.id.itemCheckIcon1, R.id.itemCheckIcon2, R.id.itemCheckIcon3, 
			R.id.itemCheckIcon4, R.id.itemCheckIcon5, R.id.itemCheckIcon6, 
			R.id.itemCheckIcon7, R.id.itemCheckIcon8
		};//no map no viewBinder view
	
	private static final ArrayList<PyramidData> items = new ArrayList<PyramidData>();
	static {
		Calendar calendar = new GregorianCalendar(Locale.getDefault());
		items.add(new PyramidData(0, "Multi-thread", calendar.getTime()));
		items.add(new PyramidData(1, "Concurrency", calendar.getTime()));
		items.add(new PyramidData(2, "DatePicker", calendar.getTime()));
		items.add(new PyramidData(3, "Volatile", calendar.getTime()));
		items.add(new PyramidData(4, "Evaluation strategy", calendar.getTime()));
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pyramid);
//		this.deleteDatabase(PyramidDatabaseManager.DB_NAME);
		initUI();
	}

	@Override
	@Deprecated
	protected Dialog onCreateDialog(int id) {
		
		switch (id) {
		case PRM_DATE_DIALOG:
			initTime();
			return new DatePickerDialog(this, 
					new ControlUtils.CustomOnDateSetListener(this, pyramidDbManager), 
					year, month, day); 
			
		default:
			return null;
		}
	}
	
	private void initTime() {
		calendar = Calendar.getInstance();
		year = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH);
		day = calendar.get(Calendar.DAY_OF_MONTH);
	}

	@SuppressWarnings("deprecation")
	private void initUI() {
		listView = (ListView)this.findViewById(R.id.listView1);
		plusButton = (ImageButton)this.findViewById(R.id.PlusButton);
		minusButton = (ImageButton)this.findViewById(R.id.MinusButton);
		
//		listAdapter = new CustomAdapter(this, R.layout.adapter_row_item, items);
//		listView.setAdapter(listAdapter);
		
		plusButton.setOnClickListener(buttonListener);
		minusButton.setOnClickListener(buttonListener);
		
		pyramidDbManager = new PyramidDbManager(this);
//		for(PyramidData item: items) {
//			pyramidDbManager.insert(item);
//		}
		
		cursor = pyramidDbManager.query(null, PyramidDbManager.C_SUMMARY);
		simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.adapter_row_item, cursor, cursorStrFroms, cursorIntTos);

		viewBinder = new CustomViewBinder(this);
		simpleCursorAdapter.setViewBinder(viewBinder);
		
		listView.setAdapter(simpleCursorAdapter);
		
	}
	
	private OnClickListener buttonListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if(v.equals(plusButton)) {
				
			} else if(v.equals(minusButton)) {
				
			}
		}
	};
	
	
}

