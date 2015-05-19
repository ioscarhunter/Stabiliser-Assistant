package com.example.make.logrecorder;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.make.logrecorder.table.DatabaseInfo;
import com.example.make.logrecorder.table.TestResult;

/**
 * Created by Apipol on 19/05/15.
 */
public class DBOperation extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1; // schema version

    public DBOperation(Context context) {
        super(context, DatabaseInfo.DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String create_query = "CREATE TABLE " + DatabaseInfo.TABLE_NAME;
        String not_null = "NOT NULL";
        String columnData = "(" +
                TestResult.id + " INT PRIMARY KEY AUTOINCREMENT, " + not_null +
                TestResult.level + " INT CHECK" + "(" + TestResult.level + " BETWEEN 1 AND 5" + ")" +
                TestResult.duration + " INT, " + not_null +
                TestResult.balance + " INT, " + not_null +
                TestResult.totalBalance + " INT " + not_null
                ;

        create_query += columnData;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
