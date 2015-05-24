package com.starboy.karav.SA.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Karav on 5/23/2015.
 * database access
 */
public class DatabaseHelper extends SQLiteOpenHelper {
	public static final String DATABASE_NAME = "SA_flight.db";
	public static final int DATABASE_VERSION = 1;
	private final String TAG = "DatabaseHelper";
	private SQLiteDatabase sqLiteDatabase;

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);//Context context, String name, SQLiteDatabase.CursorFactory factory, int version
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_FLIGHT_TABLE = "CREATE TABLE" + Flight.TABLE + "(" +
				"INTEGER PRIMARY KEY  AUTOINCREMENT " + Flight.Column.ID +
				"INTEGER " + Flight.Column.TOTAL +
				"INTEGER " + Flight.Column.INBAL +
				"INTEGER " + Flight.Column.LEVEL +
				"INTEGER " + Flight.Column.TAKETTIME +
				"TEXT" + Flight.Column.DATETIME + ")";


		Log.i(TAG, CREATE_FLIGHT_TABLE);

		// create flight table
		db.execSQL(CREATE_FLIGHT_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String DROP_FLIGHT_TABLE = "DROP TABLE IF EXISTS " + Flight.TABLE;

		db.execSQL(DROP_FLIGHT_TABLE);

		Log.i(TAG, "Upgrade Database from " + oldVersion + " to " + newVersion);

		onCreate(db);
	}

	public List<Flight> getFlightList() {
		List<Flight> friends = new ArrayList<>();

		sqLiteDatabase = this.getWritableDatabase();

		Cursor cursor = sqLiteDatabase.query(Flight.TABLE, null, null, null, null, null, null);

		if (cursor != null) {
			cursor.moveToFirst();
		}

		while (!cursor.isAfterLast()) {
			int total = cursor.getInt(1);
			int inbal = cursor.getInt(2);
			int level = cursor.getInt(3);
			int takeTime = cursor.getInt(4);
			String dateTime = cursor.getString(5);
			DateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				Date date = iso8601Format.parse(dateTime);
				friends.add(new Flight(total, inbal, level, takeTime, date));
			} catch (ParseException e) {
				Log.e(TAG, "Parsing ISO8601 datetime failed", e);
			}


			cursor.moveToNext();
		}

		sqLiteDatabase.close();

		return friends;
	}

	public void addFriend(Flight friend) {
		sqLiteDatabase = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		//values.put(Friend.Column.ID, friend.getId());
		values.put(Flight.Column.TOTAL, friend.getTotal());
		values.put(Flight.Column.INBAL, friend.getInbal());
		values.put(Flight.Column.LEVEL, friend.getLevel());
		values.put(Flight.Column.TAKETTIME, friend.getTakeTime());
		values.put(Flight.Column.DATETIME, friend.getFlightDateString());

		sqLiteDatabase.insert(Flight.TABLE, null, values);

		sqLiteDatabase.close();
	}
}
