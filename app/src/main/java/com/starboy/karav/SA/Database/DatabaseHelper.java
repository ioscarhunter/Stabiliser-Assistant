package com.starboy.karav.SA.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Karav on 5/23/2015.
 * database access
 */
public class DatabaseHelper extends SQLiteOpenHelper {
	public static final String DATABASE_NAME = "SA_flight.db";
	public static final int DATABASE_VERSION = 1;
	private final String TAG = "DatabaseHelper";

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
				"DATETIME" + Flight.Column.DATETIME + ")";


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
}
