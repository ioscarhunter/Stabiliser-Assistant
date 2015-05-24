package com.starboy.karav.SA.Database;

import android.provider.BaseColumns;

import java.util.Date;

/**
 * Created by Karav on 5/23/2015.
 * object of database
 */
public class Flight {
	public static final String TABLE = "flight";
	private int rating;
	private int total;
	private int inbal;
	private int level;
	private int takeTime;
	private Date flightDate;

	public Flight() {
	}

	public Flight(int rating, int level, int takeTime) {
		this.rating = rating;
		this.level = level;
		this.takeTime = takeTime;
		this.flightDate = new Date();
	}

	public Flight(int rating, int level, int takeTime, Date flightDate) {
		this.rating = rating;
		this.level = level;
		this.takeTime = takeTime;
		this.flightDate = flightDate;
	}

	public int getRating() {
		return rating;
	}

	public int getTotal() {
		return total;
	}

	public int getInbal() {
		return inbal;
	}

	public int getLevel() {
		return level;
	}

	public int getTakeTime() {
		return takeTime;
	}

	public Date getFlightDate() {
		return flightDate;
	}

	public class Column {
		public static final String ID = BaseColumns._ID;
		public static final String RATING = "rating";
		public static final String LEVEL = "level";
		public static final String TAKETTIME = "take_time";
		public static final String DATETIME = "datetime";
		public static final String INBAL = "inbal";
		public static final String TOTAL = "total";
	}
}

