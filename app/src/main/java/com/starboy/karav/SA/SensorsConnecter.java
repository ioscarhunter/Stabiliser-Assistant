package com.starboy.karav.SA;

import android.app.Fragment;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class SensorsConnecter extends Fragment implements SensorEventListener {

	public int num = 0;
	public int b_num = 0;
	double cali_x = 0, cali_y = 0;
	boolean mIsStarted;
	boolean bEnableLogging;
	private SensorManager senSensorManager;
	private Sensor senAccelerometer;
	private double x;
	private double y;
	private int level;
	private double save_datax = 0, save_datay = 0;

	protected void start(Context context) {
		senSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
		senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR);
		senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_FASTEST);
		mIsStarted = false;
		bEnableLogging = false;
	}

	@Override
	public void onSensorChanged(SensorEvent sensorEvent) {
		if (bEnableLogging) {
			Sensor mySensor = sensorEvent.sensor;
			if (mySensor.getType() == Sensor.TYPE_GAME_ROTATION_VECTOR) {
				x = sensorEvent.values[0];
				y = sensorEvent.values[1];
				calibrate(x, y);
				Process p = new Process();
				p.setlevel(level);
				if (p.process(cali_x, cali_y)) {
					b_num++;
				}
			}
			num++;
		}
	}

	public void calibrate(double dx, double dy) {

		if (num == 0) {
			save_datax = dx;
			save_datay = dy;
		} else {
			cali_x = save_datax - dx;
			cali_y = save_datay - dy;
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}

	public void stop(Context context) {
		mIsStarted = false;
		SensorManager manager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
		manager.unregisterListener(this);
	}

	public void pause() {
		senSensorManager.unregisterListener(this);
		bEnableLogging = false;
	}

	public void resume() {
		senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_FASTEST);
		bEnableLogging = true;
	}

}
