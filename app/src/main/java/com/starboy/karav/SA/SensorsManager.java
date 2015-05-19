package com.starboy.karav.SA;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.ActionBarActivity;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.logging.Handler;

public class SensorsManager extends ActionBarActivity implements SensorEventListener {

	private SensorManager senSensorManager;
	private Sensor senAccelerometer;
	private double x;
	private double y;
	private double z;
	public int num = 0;
	private double save_datax = 0,save_datay = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		senSensorManager = (SensorManager) this.getSystemService((SENSOR_SERVICE));
		senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR);
		senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_FASTEST);

	}

	@Override
	public void onSensorChanged(SensorEvent sensorEvent) {
		Sensor mySensor = sensorEvent.sensor;
		if (mySensor.getType() == Sensor.TYPE_GAME_ROTATION_VECTOR) {
			x = sensorEvent.values[0];
			y = sensorEvent.values[1];
			String str1 = String.format("%1.3f", x);
			x = Double.valueOf(str1);
			String str2 = String.format("%1.3f", y);
			y = Double.valueOf(str2);
			Process p=new Process();
			p.setlevel(3);
			p.process(x,y);
		}
		num++;
	}

	public String calibrate(double dx,double dy){
		double cali_x = 0,cali_y = 0;
		if(num == 0){
			save_datax = dx;
			save_datay = dy;
		}
		else{
			cali_x = save_datax-dx;
			cali_y = save_datay-dy;
		}
		return ("cali_x = " + Double.toString(cali_x)+"\n" + " cali_y =" + Double.toString(cali_y)+"\n");
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}

	public void pause(){
		onPause();
		senSensorManager.unregisterListener(this);
	}
	@Override
	protected void onPause() {
		pause();
	}

	public void resume(){
		onResume();
		senSensorManager.registerListener(this, senAccelerometer,  SensorManager.SENSOR_DELAY_FASTEST);
	}
	@Override
	protected void onResume() {
		resume();
	}

}
