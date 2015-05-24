package com.starboy.karav.SA.UI.Sender;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.starboy.karav.SA.R;
import com.starboy.karav.SA.Sensor.SensorProcess;

public class SensorListenerFragment extends Fragment implements SensorEventListener {

	public int totalCounter = 0;
	public int balanceCounter = 0;
	double cali_x = 0, cali_y = 0;
	private SensorManager senSensorManager;
	private Sensor positionSensor;
	private double x;
	private double y;
	private int level;
	private double save_dataX = 0, save_dataY = 0;
	private SensorProcess p;
	private boolean bEnableLogging;
	private int status;

	private OnFragmentInteractionListener mListener;

	@Override //set listener for activity
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (OnFragmentInteractionListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must implement OnFragmentInteractionListener");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		senSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
		positionSensor = senSensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR);
		bEnableLogging = false;//not run into counter code

		p = new SensorProcess();//make process object
	}

	public void start() {
		senSensorManager.registerListener(this, positionSensor, 200000);
		p.setlevel(level);
		totalCounter = 0;//reset counter
		balanceCounter = 0;
		bEnableLogging = true;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_sensor_listener, container, false);
	}

	@Override
	public void onSensorChanged(SensorEvent sensorEvent) {
		if (bEnableLogging) {
			status = 0;
			Sensor mySensor = sensorEvent.sensor;
			if (mySensor.getType() == Sensor.TYPE_GAME_ROTATION_VECTOR) {
				x = sensorEvent.values[0];//get data from sensor
				y = sensorEvent.values[1];
				calibrate(x, y);

				if (!p.process(cali_x, cali_y)) {
					status = 1; //not balance
				} else {
					balanceCounter++; //balance
				}
			}
			totalCounter++;
			int rate = p.rate(totalCounter, balanceCounter); //calculate rate
//			Log.d("SensorFrag", "d" + rate + status);
			((SenderActivity) getActivity()).updateData(status, rate);
		}
	}

	public void calibrate(double dx, double dy) {

		if (totalCounter == 0) {
			save_dataX = dx;
			save_dataY = dy;
		} else {
			cali_x = save_dataX - dx;
			cali_y = save_dataY - dy;
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}


	public void pause() {
		senSensorManager.unregisterListener(this);
		bEnableLogging = false;
	}

	public void resume() {
		senSensorManager.registerListener(this, positionSensor, 200000);
		bEnableLogging = true;
	}

	@Override
	public void onResume() {
		super.onResume();
//		resume();
	}

	@Override
	public void onPause() {
		super.onPause();
		pause();
	}

	public void stop() {
		pause();
	}


	public void setStatus(String status) {
		switch (status) {
			case "S":
				stop();
				break;
			case "R":
				resume();
				break;
			case "P":
				pause();
				break;
			case "B":
				start();
				break;
		}
	}

	public void setLevel(int level) {
		this.level = level;
	}

	/**
	 * This interface must be implemented by activities that contain this
	 * fragment to allow an interaction in this fragment to be communicated
	 * to the activity and potentially other fragments contained in that
	 * activity.
	 * <p/>
	 * See the Android Training lesson <a href=
	 * "http://developer.android.com/training/basics/fragments/communicating.html"
	 * >Communicating with Other Fragments</a> for more information.
	 */
	public interface OnFragmentInteractionListener {
		// TODO: Update argument type and name
		void onTimeControlChange(String what);
	}


}
