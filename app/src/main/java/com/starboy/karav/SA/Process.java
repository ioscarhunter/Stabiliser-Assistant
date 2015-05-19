package com.starboy.karav.SA;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import static java.lang.Math.exp;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;


public class Process {
	private double r;
	private double r_level;

	public void setlevel(int l) {
		int num = 3-l;
		double base = 0.3;
		r_level = base+(0.05*num);
	}
	public boolean process(double x, double y) {
		r = sqrt(pow(x,2)+pow(y,2));
		return r>=0.0 && r<=r_level;
	}
}

