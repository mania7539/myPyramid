package com.XDrz.mypyramid.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.ContentValues;
import android.database.Cursor;

public class DataUtils {
	private static final SimpleDateFormat dfFullDate = new SimpleDateFormat("k:m MMMdd, yyyy", Locale.getDefault());
//	private SimpleDateFormat dfDate = new SimpleDateFormat("MMMdd, yyyy", Locale.getDefault());
//	private SimpleDateFormat dfTime = new SimpleDateFormat("k:m", Locale.getDefault());
	
	public static final String[] dateColumnName = {
			PyramidDbManager.C_DATE1, PyramidDbManager.C_DATE2,
			PyramidDbManager.C_DATE3, PyramidDbManager.C_DATE4,
			PyramidDbManager.C_DATE5, PyramidDbManager.C_DATE6,
			PyramidDbManager.C_DATE7, PyramidDbManager.C_DATE8,
		};
	public static final String[] isDoneColumnName = {
			PyramidDbManager.C_IS_DONE1, PyramidDbManager.C_IS_DONE2,
			PyramidDbManager.C_IS_DONE3, PyramidDbManager.C_IS_DONE4,
			PyramidDbManager.C_IS_DONE5, PyramidDbManager.C_IS_DONE6,
			PyramidDbManager.C_IS_DONE7, PyramidDbManager.C_IS_DONE8,
		};
	
	public static PyramidData getPyramidDataFromCursor(PyramidDbManager pyramidDbManager, int cId) {
		Cursor cursor = pyramidDbManager.query(
				String.format("%s=%d", PyramidDbManager.C_ID, cId), null);
		cursor.moveToFirst();
//		Integer id = cursor.getInt(cursor.getColumnIndex(PyramidDatabaseManager.C_ID));
		String summary = cursor.getString(cursor.getColumnIndex(PyramidDbManager.C_SUMMARY));
		Date[] dates = {
				stringToDate(cursor.getString(cursor.getColumnIndex(PyramidDbManager.C_DATE1))),
				stringToDate(cursor.getString(cursor.getColumnIndex(PyramidDbManager.C_DATE2))),
				stringToDate(cursor.getString(cursor.getColumnIndex(PyramidDbManager.C_DATE3))),
				stringToDate(cursor.getString(cursor.getColumnIndex(PyramidDbManager.C_DATE4))),
				stringToDate(cursor.getString(cursor.getColumnIndex(PyramidDbManager.C_DATE5))),
				stringToDate(cursor.getString(cursor.getColumnIndex(PyramidDbManager.C_DATE6))),
				stringToDate(cursor.getString(cursor.getColumnIndex(PyramidDbManager.C_DATE7))),
				stringToDate(cursor.getString(cursor.getColumnIndex(PyramidDbManager.C_DATE8)))
		};
		Boolean[] isDones = {
				integerToBoolean(cursor.getInt(cursor.getColumnIndex(PyramidDbManager.C_IS_DONE1))),
				integerToBoolean(cursor.getInt(cursor.getColumnIndex(PyramidDbManager.C_IS_DONE2))),
				integerToBoolean(cursor.getInt(cursor.getColumnIndex(PyramidDbManager.C_IS_DONE3))),
				integerToBoolean(cursor.getInt(cursor.getColumnIndex(PyramidDbManager.C_IS_DONE4))),
				integerToBoolean(cursor.getInt(cursor.getColumnIndex(PyramidDbManager.C_IS_DONE5))),
				integerToBoolean(cursor.getInt(cursor.getColumnIndex(PyramidDbManager.C_IS_DONE6))),
				integerToBoolean(cursor.getInt(cursor.getColumnIndex(PyramidDbManager.C_IS_DONE7))),
				integerToBoolean(cursor.getInt(cursor.getColumnIndex(PyramidDbManager.C_IS_DONE8)))
		};
		
		return new PyramidData(cId, summary, dates, isDones);
	}
	
	public static ContentValues getContentValuesFromPyramidData(PyramidData item) {
		ContentValues values = new ContentValues();
		Date[] dates = item.getDateArrayClone();
		Boolean[] isDones = item.getIsDoneArrayClone();
		values.put(PyramidDbManager.C_ID, item.getId());//status properties was known in API doc
		values.put(PyramidDbManager.C_SUMMARY, item.getSummary());
		values.put(PyramidDbManager.C_DATE1, DataUtils.dateToString(dates[0]));
		
		// TODO put date1+1, date1+6, date1+30, date1+60, date1+180, date1+365 
		values.put(PyramidDbManager.C_DATE2, DataUtils.dateToString(dates[1]));
		values.put(PyramidDbManager.C_DATE3, DataUtils.dateToString(dates[2]));
		values.put(PyramidDbManager.C_DATE4, DataUtils.dateToString(dates[3]));
		values.put(PyramidDbManager.C_DATE5, DataUtils.dateToString(dates[4]));
		values.put(PyramidDbManager.C_DATE6, DataUtils.dateToString(dates[5]));
		values.put(PyramidDbManager.C_DATE7, DataUtils.dateToString(dates[6]));
		values.put(PyramidDbManager.C_DATE8, DataUtils.dateToString(dates[7]));
		
		values.put(PyramidDbManager.C_IS_DONE1, DataUtils.booleanToInteger(isDones[0]));
		values.put(PyramidDbManager.C_IS_DONE2, DataUtils.booleanToInteger(isDones[1]));
		values.put(PyramidDbManager.C_IS_DONE3, DataUtils.booleanToInteger(isDones[2]));
		values.put(PyramidDbManager.C_IS_DONE4, DataUtils.booleanToInteger(isDones[3]));
		values.put(PyramidDbManager.C_IS_DONE5, DataUtils.booleanToInteger(isDones[4]));
		values.put(PyramidDbManager.C_IS_DONE6, DataUtils.booleanToInteger(isDones[5]));
		values.put(PyramidDbManager.C_IS_DONE7, DataUtils.booleanToInteger(isDones[6]));
		values.put(PyramidDbManager.C_IS_DONE8, DataUtils.booleanToInteger(isDones[7]));
		
		return values;
	}
	
	public static Integer booleanToInteger(Boolean flag) {
		//TODO true=integer 1, false=integer 0
		return (flag)? 1: 0;
	}
	
	public static Boolean integerToBoolean(Integer flag) {
		//TODO true=integer 1, false=integer 0
		return (flag==0)? Boolean.FALSE: Boolean.TRUE;
	}
	
	public static Date stringToDate(String dateString) {
		try {
			return dfFullDate.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
		
	public static String dateToString(Date date) {
		return dfFullDate.format(date);
	}
	
	public static String padRight(String s, int n) {
	     return String.format("%1$-" + n + "s", s);  
	}

	public static String padLeft(String s, int n) {
	    return String.format("%1$" + n + "s", s);  
	}
	
	public static SimpleDateFormat getFullDateFormat() {
		return dfFullDate;
	}
}
