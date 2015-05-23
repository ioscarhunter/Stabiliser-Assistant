package com.starboy.karav.SA.Sensor;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import android.util.Log;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;


public class SensorProcess {
	private double r;
	private double r_level;

	public void setlevel(int l) {
		int num = 3 - l;
		double base = 0.1;
		r_level = base + (0.03 * num);
	}

	public boolean process(double x, double y) {
		r = sqrt((pow(x, 2) + pow(y, 2)));
		return (r >= 0.0 && r <= r_level);
	}

	public int rate(int all_num, int balance_num) {
		int grade;
		double percentage = ((double) balance_num / (double) all_num) * 100;
		Log.d("SensorProcess", percentage + " %");
		if (percentage >= 80) {
			grade = 5;
		} else if (percentage >= 60) {
			grade = 4;
		} else if (percentage >= 40) {
			grade = 3;
		} else if (percentage >= 20) {
			grade = 2;
		} else {
			grade = 1;
		}
		return grade;
	}

}

