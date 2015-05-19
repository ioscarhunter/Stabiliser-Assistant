package com.starboy.karav.SA;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class SenderActivity extends BluetoothActivity {


	private TextView display;
	/**
	 * The Handler that gets information back from the BluetoothChatService
	 */

	private Button discover;
	private Button sb;
	private Button su;
	private Button l1;
	private Button l3;
	private Button l5;
	private Button l2;
	private Button l4;
	private TextView status;
	private int statusb = 0;
	private int rating = 0;


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

		discover = (Button) findViewById(R.id.discover_rec);
		discover.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ensureDiscoverable();
			}
		});

		display = (TextView) findViewById(R.id.connect_status);
		sb = (Button) findViewById(R.id.s_bl);
		sb.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				statusb = 0;
				updateData();
			}
		});

		su = (Button) findViewById(R.id.s_ub);
		su.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				statusb = 1;
				updateData();
			}
		});

		l1 = (Button) findViewById(R.id.s1);
		l1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				rating = 1;
				updateData();
			}
		});

		l2 = (Button) findViewById(R.id.s2);
		l2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				rating = 2;
				updateData();
			}
		});

		l3 = (Button) findViewById(R.id.s3);
		l3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				rating = 3;
				updateData();
			}
		});

		l4 = (Button) findViewById(R.id.s4);
		l4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				rating = 4;
				updateData();
			}
		});

		l5 = (Button) findViewById(R.id.s5);
		l5.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				rating = 5;
				updateData();
			}
		});
		rating = 1;
		statusb = 1;

	}

	private void displayMessagerecieve(String message) {
		display.setText(message);
	}

	private void updateData() {
		sendMessage(update + ":" + statusb + ":" + rating);
	}

	private void processMassage(String msg) {

	}

	private void setStatus(String msg) {

	}


	private Activity getActivity() {
		return SenderActivity.this;
	}

	@Override
	protected void messageReceive(String s) {
		displayMessagerecieve(s);
		String receive[] = s.split(":");
//		Log.d(TAG, s);
		switch (receive[0]) {
			case "T":
				switch (receive[1]) {
					case "S":
						break;
					case "R":
						//resume
						break;
					case "P":
						//pause
						break;
					case "B":
						break;
				}
				break;
		}
	}
}
