package com.starboy.karav.SA.Database;

import android.provider.BaseColumns;

import com.starboy.karav.SA.Sensor.SensorProcess;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Karav on 5/23/2015.
 * object of database
 */
public class Flight {
	public static final String TABLE = "flight";
	private int rating;
	private int total;
	private int bal;
	private int level;
	private int takeTime;
	private Date flightDate;

	public Flight() {
	}

	public Flight(int total, int bal, int level, int takeTime, Date flightDate) {
		this.total = total;
		this.bal = bal;
		this.level = level;
		this.takeTime = takeTime;
		this.flightDate = flightDate;
		this.rating = SensorProcess.rate(total, bal);
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getBal() {
		return bal;
	}

	public void setBal(int bal) {
		this.bal = bal;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getTakeTime() {
		return takeTime;
	}

	public void setTakeTime(int takeTime) {
		this.takeTime = takeTime;
	}

	public Date getFlightDate() {
		return flightDate;
	}

	public void setFlightDate(Date flightDate) {
		this.flightDate = flightDate;
	}

	public String getFlightDateString() {
		TimeZone tz = TimeZone.getDefault();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
		df.setTimeZone(tz);
		return df.format(new Date());
	}

	public class Column {
		public static final String ID = BaseColumns._ID;
		public static final String RATING = "rating";
		public static final String LEVEL = "level";
		public static final String TAKETTIME = "take_time";
		public static final String DATETIME = "datetime";
		public static final String INBAL = "bal";
		public static final String TOTAL = "total";
	}
}

