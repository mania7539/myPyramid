package com.XDrz.mypyramid.data;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class PyramidPreferenceManager {
	public static final String TAG = "PyramidPreferenceManager";
	private static int count = -1;
	public static final String C_ID = "_id";
	public static final String C_SUMMARY = "summary";
	public static final String C_DATE1 = "date1";
	public static final String C_DATE2 = "date2";
	public static final String C_DATE3 = "date3";
	public static final String C_DATE4 = "date4";
	public static final String C_DATE5 = "date5";
	public static final String C_DATE6 = "date6";
	public static final String C_DATE7 = "date7";
	public static final String C_IS_DONE = "is_done";
	
	
	
//	private Context context;
	private SharedPreferences sharedPreference;
	private Editor editor;
	
	
	public PyramidPreferenceManager(Context context) {
//		this.context = context;
		sharedPreference = PreferenceManager.getDefaultSharedPreferences(context);
		editor = sharedPreference.edit();
	}

	public void insert(String summary, String date1) {
		// TODO put date1+1, date1+6, date1+30, date1+60, date1+180, date1+365 
		editor.putString(C_DATE2+getCount(), getFutureDate(strToDate(date1), 1).toString());
		editor.putString(C_DATE3+getCount(), getFutureDate(strToDate(date1), 6).toString());
		editor.putString(C_DATE4+getCount(), getFutureDate(strToDate(date1), 30).toString());
		editor.putString(C_DATE5+getCount(), getFutureDate(strToDate(date1), 60).toString());
		editor.putString(C_DATE6+getCount(), getFutureDate(strToDate(date1), 180).toString());
		editor.putString(C_DATE7+getCount(), getFutureDate(strToDate(date1), 365).toString());
		
		editor.commit();
	}
	
	private Date strToDate(String date) {
		// TODO regex
		return null;
	}
	
	private Date getFutureDate(Date date, int numFutureDays) {
		Calendar calendar = new GregorianCalendar(Locale.getDefault());///* remember about timezone! */
		calendar.setTime(date);
		calendar.add(Calendar.DATE, numFutureDays);
		return calendar.getTime();
	}
	
	public SharedPreferences getSharedPreferences() {
		return sharedPreference;
	}
	
	public Editor getPreferenceEditor() {
		return editor;
	}
	
	public static int getCount() {
		return count;
	}
	
	public static synchronized void setCount(int count) {
		PyramidPreferenceManager.count = count; 
	}
}
