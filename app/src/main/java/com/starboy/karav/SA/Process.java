package com.starboy.karav.SA;

public class Process {
	private int level;
	private double x_max =0, x_min=0;
	private double y_max =0, y_min =0;
	private boolean x_status = false;
	private boolean y_status = false;

	public void setlevel(int l) {
		int num = 0;
		while(num == l) {
			x_max = x_max+0.1;
			x_min = x_min-0.1;
			y_max = y_min+0.1;
			y_min = y_min-0.1;
			num++;
		}
	}
	public boolean process(double x, double y) {
		if(x>=x_min && x<=x_max){
			x_status = true;
		}
		if(y>=y_min && y<=y_max){
			y_status = true;
		}
		if(x_status == true && y_status == true){
			return true;
		}
		return false;
	}

}
