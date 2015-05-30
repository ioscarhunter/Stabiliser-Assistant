package com.starboy.karav.SA.UI.Sender;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.starboy.karav.SA.Bluetooth.BluetoothChatService;
import com.starboy.karav.SA.Database.DatabaseHelper;
import com.starboy.karav.SA.Database.Flight;
import com.starboy.karav.SA.R;
import com.starboy.karav.SA.UI.BluetoothActivity;

import java.util.Date;


public class SenderActivity extends BluetoothActivity implements SensorListenerFragment.OnFragmentInteractionListener {

	private String TAG = "SenderActivity";
	private Button discover;
	private int statusb = 0;
	private int rating = 0;
	private int level;
	private int bal;
	private int total;
	private int time;
	private DatabaseHelper dbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		int currentOrientation = getResources().getConfiguration().orientation;
		if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
		} else {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
		}
		setContentView(R.layout.activity_sender);
		getFragmentManager().beginTransaction().add(R.id.sensor_listener, new SensorListenerFragment()).commit();

		setupBluetooth();
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		rating = 1;
		statusb = 1;

		discover = (Button) findViewById(R.id.discover_rec);
		discover.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ensureDiscoverable();
			}
		});
		dbHelper = new DatabaseHelper(getActivity());
	}

	private void setupBluetooth() {
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		// If BT is not on, request that it be enabled.
		// setupChat() will then be called during onActivityResult
		if (!mBluetoothAdapter.isEnabled()) {
			Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
			// Otherwise, setup the chat session
		} else if (mChatService == null) {
			// Initialize the BluetoothChatService to perform bluetooth connections
			mChatService = new BluetoothChatService(getActivity(), mHandler);
		}
	}


	private void updateData() {
		sendMessage("U:" + statusb + ":" + rating);
	}

	public void updateData(int status, int rating, int bal, int total) {//compact data
		this.statusb = status;
		this.rating = rating;
		this.bal = bal;
		this.total = total;
		updateData();
	}

	private void saveToDB() {
		dbHelper.addFlight(new Flight(total, bal, level, time, new Date()));
	}

	private Activity getActivity() {
		return SenderActivity.this;
	}

	//message receive from bluetooth to be process and sent to fragment
	@Override
	protected void messageReceive(String s) {
		String receive[] = s.split(":");
		Log.d(TAG, s);
		switch (receive[0]) {
			case "T":
				onTimeControlChange(receive);
				break;
		}
	}

	@Override
	public void onTimeControlChange(String[] what) {
		SensorListenerFragment SensorLisFrag = (SensorListenerFragment) getFragmentManager().findFragmentById(R.id.sensor_listener);
		if (what[1].equals("S")) {
			time = Integer.parseInt(what[2]);
		}
		if (what[1].equals("B")) {
			level = Integer.parseInt(what[2]);
			SensorLisFrag.setLevel(level);
		}
		SensorLisFrag.setStatus(what[1]);
	}
}
