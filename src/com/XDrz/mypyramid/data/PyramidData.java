package com.XDrz.mypyramid.data;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class PyramidData {
	private Calendar calendar = new GregorianCalendar(Locale.getDefault());
	public static final int numColumn = 8;
	private Integer id;
	private String summary;
	private Date[] dates = new Date[numColumn];
	private Boolean[] isDones = new Boolean[numColumn];
	
	public PyramidData(int id, String summary, Date date1) {
		this.id = id;
		this.summary = summary;
		this.dates[0] = date1;
		this.dates[1] = getDate(date1, Calendar.MINUTE, 20);
		this.dates[2] = getDate(date1, Calendar.HOUR_OF_DAY, 1);
		this.dates[3] = getDate(date1, Calendar.DAY_OF_MONTH, 1);
		this.dates[4] = getDate(date1, Calendar.DAY_OF_MONTH, 6);
		this.dates[5] = getDate(date1, Calendar.DAY_OF_MONTH, 29);
		this.dates[6] = getDate(date1, Calendar.DAY_OF_MONTH, 179);
		this.dates[7] = getDate(date1, Calendar.DAY_OF_MONTH, 364);
		
		for(int a=0;a<isDones.length;a++) {
			if(a!=0) {
				isDones[a] = Boolean.FALSE;
				continue;
			}
			this.isDones[a] = Boolean.TRUE;
		}
	}
	
	public PyramidData(int id, String summary, Date[] dates, Boolean[] isDones) {
		this.id = id;
		this.summary = summary;
		try {
			if(dates!=null && dates.length==numColumn) {
				this.dates = dates.clone();
			} else {
				throw new IllegalAccessException("Length of Date array is wrong!!!");
			}
			if(isDones!=null && isDones.length==numColumn) {
				this.isDones = isDones.clone();
			} else {
				throw new IllegalAccessException("Length of Boolean array is wrong!!!");
			}
		} catch(IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	public Date getDate(Date date, int field, int value) {
		calendar.setTime(date);
		calendar.add(field, value);
		return calendar.getTime();
	}
	
	public String getSummary() {
		return summary;
	}
	
	public Integer getId() {
		return id;
	}
	public Date[] getDateArrayClone() {
		return dates.clone();
	}
	
	public void setDateArray(int index, Date date) {
		dates[index] = date;
	}
	
	public Boolean[] getIsDoneArrayClone() {
		return isDones.clone();
	}
	
	public void setIsDoneArray(int index, Boolean flag) {
		isDones[index] = flag;
	}
}
