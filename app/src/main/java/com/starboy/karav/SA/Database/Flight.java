package com.starboy.karav.SA.Database;

import android.provider.BaseColumns;
import android.util.Log;

import com.starboy.karav.SA.Sensor.SensorProcess;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Karav on 5/23/2015.
 * object of database
 */
public class Flight {
	public static final String TABLE = "flight";
	private static final String TAG = "Flight";
	private int rating;
	private int total;
	private int bal;
	private int level;
	private int takeTime;
	private Date flightDate;
	private TimeZone tz;

	private DateFormat iso8601Format;

	private Flight() {
		tz = TimeZone.getDefault();
		iso8601Format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
		iso8601Format.setTimeZone(tz);
	}

	public Flight(int total, int bal, int level, int takeTime, Date flightDate) {
		this();
		Log.d(TAG, "new Flight");
		this.total = total;
		this.bal = bal;
		this.level = level;
		this.takeTime = takeTime;
		this.flightDate = flightDate;
		this.rating = SensorProcess.rate(total, bal);
	}


	public Flight(int total, int bal, int level, int takeTime, String flightDate) {
		this();
		Log.d(TAG, "new Flight");
		this.total = total;
		this.bal = bal;
		this.level = level;
		this.takeTime = takeTime;
		this.flightDate = getDateFromString(flightDate);
		this.rating = SensorProcess.rate(total, bal);
	}

	public Flight(int total, int bal, int level, int takeTime) {
		this.total = total;
		this.bal = bal;
		this.level = level;
		this.takeTime = takeTime;
		this.flightDate = new Date();
		this.rating = SensorProcess.rate(total, bal);
	}

	private Date getDateFromString(String date) {
		try {
			return iso8601Format.parse(date);
		} catch (ParseException e) {
			Log.e(TAG, e.getMessage());
		}
		return null;
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
		return iso8601Format.format(new Date());
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

