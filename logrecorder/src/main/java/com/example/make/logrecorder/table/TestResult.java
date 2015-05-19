package com.example.make.logrecorder.table;

import android.provider.BaseColumns;

/**
 * Created by Apipol on 19/05/15.
 * A record of result for each completed test contains
 * - id
 * - level (A, B, C, D, E)
 * - timestamp (mm:ss)
 * - #balance number balance pass
 * - total number of sampling
 */
public class TestResult {
    public static final String id = "ID";
    public static final String level = "LEVEL";
    public static final String duration = "DURATION";
    public static final String balance = "BALANCE";
    public static final String totalBalance = "TOTAL_BALANCE";

}
