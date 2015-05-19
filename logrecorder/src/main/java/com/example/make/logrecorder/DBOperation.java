package com.example.make.logrecorder;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.make.logrecorder.table.DatabaseInfo;
import com.example.make.logrecorder.table.TestResult;

/**
 * Created by Apipol on 19/05/15.
 */
public class DBOperation extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1; // schema version
    public static final String LOGCAT_DB = "Database";
    public DBOperation(Context context) {
        super(context, DatabaseInfo.DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(LOGCAT_DB, "database created");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String create_query = "CREATE TABLE " + DatabaseInfo.TABLE_NAME;
        String columnData = "(" +
                TestResult.id + " INT PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                TestResult.level + " INT CHECK" + "(" + TestResult.level + " BETWEEN 1 AND 5" + "), " +
                TestResult.duration + " INT NOT NULL," +
                TestResult.balance + " INT, NOT NULL, " +
                TestResult.totalBalance + " INT NOT NULL"
                ;

        create_query += columnData;

        sqLiteDatabase.execSQL(create_query);
        Log.d(LOGCAT_DB, DatabaseInfo.TABLE_NAME + " is created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
