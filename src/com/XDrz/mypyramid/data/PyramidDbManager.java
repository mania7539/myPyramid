package com.XDrz.mypyramid.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class PyramidDbManager extends SQLiteOpenHelper {
	public static final String TAG = "PyramidDbManager";
	
	
	public static final String DB_NAME = "pyramid_date.db";	//db file
	public static final int DB_VERSION = 1;					//db version
	public static final String TABLE = "status";	
	public static final String C_ID = "_id";
	public static final String C_SUMMARY = "summary";
	public static final String C_DATE1 = "date1";
	public static final String C_DATE2 = "date2";
	public static final String C_DATE3 = "date3";
	public static final String C_DATE4 = "date4";
	public static final String C_DATE5 = "date5";
	public static final String C_DATE6 = "date6";
	public static final String C_DATE7 = "date7";
	public static final String C_DATE8 = "date8";
	public static final String C_IS_DONE1 = "check1";
	public static final String C_IS_DONE2 = "check2";
	public static final String C_IS_DONE3 = "check3";
	public static final String C_IS_DONE4 = "check4";
	public static final String C_IS_DONE5 = "check5";
	public static final String C_IS_DONE6 = "check6";
	public static final String C_IS_DONE7 = "check7";
	public static final String C_IS_DONE8 = "check8";
	private static int count = -1;
	
	public PyramidDbManager(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.d(TAG, "onUpgrade from "+oldVersion+" to "+newVersion);
		//Usually alter TABLE statement
		db.execSQL("drop table if exists "+TABLE);
		onCreate(db);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
//		db.execSQL("drop table if exists "+TABLE);
		String sql = String.format(
				"create table %s "+
				"(%s int primary key, %s text, " +
				"%s text, %s text, %s text, %s text, %s text, %s text, %s text, %s text, " +
				"%s int, %s int, %s int, %s int, %s int, %s int, %s int, %s int)", 
				TABLE, 
				C_ID, C_SUMMARY, 
				C_DATE1, C_DATE2, C_DATE3, C_DATE4, C_DATE5, C_DATE6, C_DATE7, C_DATE8,
				C_IS_DONE1, C_IS_DONE2, C_IS_DONE3, C_IS_DONE4, C_IS_DONE5, C_IS_DONE6, C_IS_DONE7, C_IS_DONE8);
		
		Log.d(TAG, "onCreate with SQL: "+sql);
		db.execSQL(sql);
		
	}

	private void printCursor(Cursor cursor) {
		String[] columnNames = cursor.getColumnNames();
		String columnNamesInLine = "";
		String columnInLine = "";
		for(int a=0;a<columnNames.length;a++) {
			if(a==0) {
				columnNamesInLine += "id: "+columnNames[a]+"\n";
				continue;
			}
			columnNamesInLine += DataUtils.padRight(columnNames[a], 10);
			//columnNamesInLine+=String.format("%1$20s", columnNames[a]);
		}
		Log.v(TAG, columnNamesInLine);
		columnNamesInLine = "";
		
		cursor.moveToFirst();
		do {
			for(int a=0;a<cursor.getColumnCount();a++) {
				if(a==0) {
					columnInLine += "id: "+cursor.getString(a)+"\n";
					continue;
				}
				columnInLine += DataUtils.padRight(cursor.getString(a), 20);
			}
			Log.v(TAG, columnInLine);
			columnInLine = "";
		} while(cursor.moveToNext());
		
		cursor.moveToFirst();
	}
	
	public Cursor query(String selection, String sortedColumnName) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor;
		if(sortedColumnName!=null && sortedColumnName!="") {
			cursor = db.query(TABLE, null, selection, null, null, null, 
					sortedColumnName+" DESC");
		} else {
			cursor = db.query(TABLE, null, selection, null, null, null, null);
		}
		printCursor(cursor);
		return cursor;
	}
	
	public void update(int id, PyramidData item) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.update(TABLE, DataUtils.getContentValuesFromPyramidData(item), 
				String.format("%s=%d", C_ID, id), null);
//		Log.v(TAG, String.format("%s=%d", C_ID, id));
	}
	
	public void insert(PyramidData item) {
		setCount(getCount()+1);
		SQLiteDatabase db = this.getWritableDatabase();		
		db.insert(TABLE, null, DataUtils.getContentValuesFromPyramidData(item));	//convert our status to value
	}
	
	public static int getCount() {
		return count;
	}
	
	private static synchronized void setCount(int count) {
		PyramidDbManager.count = count; 
	}

}
