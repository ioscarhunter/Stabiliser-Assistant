package com.starboy.karav.SA;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class SenderActivity extends ColourBarActivity {

	private static final int REQUEST_ENABLE_BT = 3;
	/**
	 * Name of the connected device
	 */
	private String mConnectedDeviceName = null;
	/**
	 * Member object for the chat services
	 */
	private BluetoothChatService mChatService = null;
	/**
	 * Local Bluetooth adapter
	 */
	private BluetoothAdapter mBluetoothAdapter = null;

	/**
	 * String buffer for outgoing messages
	 */
	private StringBuffer mOutStringBuffer;

	private TextView display;
	/**
	 * The Handler that gets information back from the BluetoothChatService
	 */
	private final Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			Activity activity = getActivity();
			switch (msg.what) {
				case Constants.MESSAGE_STATE_CHANGE:
					switch (msg.arg1) {
						case BluetoothChatService.STATE_CONNECTED:
//                            (getActivity()).setTitle(getString(R.string.title_connected_to, mConnectedDeviceName));
//                            mConversationArrayAdapter.clear();
							break;
						case BluetoothChatService.STATE_CONNECTING:
//                            setStatus(R.string.title_connecting);
							break;
						case BluetoothChatService.STATE_LISTEN:
							break;
						case BluetoothChatService.STATE_NONE:
//                            setStatus(R.string.title_not_connected);
							break;
					}
					break;

				case Constants.MESSAGE_READ:    //get the message
					byte[] readBuf = (byte[]) msg.obj;
					// construct a string from the valid bytes in the buffer
					displayMessage(new String(readBuf, 0, msg.arg1));
//                    mConversationArrayAdapter.add(mConnectedDeviceName + ":  " + readMessage);
					break;
				case Constants.MESSAGE_DEVICE_NAME:
					// save the connected device's name
					mConnectedDeviceName = msg.getData().getString(Constants.DEVICE_NAME);
					if (null != activity) {
						Toast.makeText(activity, "Connected to " + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
					}
					break;
				case Constants.MESSAGE_TOAST:
					if (null != activity) {
						Toast.makeText(activity, msg.getData().getString(Constants.TOAST), Toast.LENGTH_SHORT).show();
					}
					break;
			}
		}
	};
	private Button discover;
	private Button sb;
	private Button su;
	private Button l1;
	private Button l3;
	private Button l5;
	private Button l2;
	private Button l4;
	private TextView status;

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
				writeMessage("Balance");
			}
		});

		su = (Button) findViewById(R.id.s_ub);
		su.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				writeMessage("UnBalance");
			}
		});

		l1 = (Button) findViewById(R.id.s1);
		l1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				writeMessage("Level1");
			}
		});

		l2 = (Button) findViewById(R.id.s2);
		l2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				writeMessage("Level2");
			}
		});

		l3 = (Button) findViewById(R.id.s3);
		l3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				writeMessage("Level3");
			}
		});

		l4 = (Button) findViewById(R.id.s4);
		l4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				writeMessage("Level4");
			}
		});

		l5 = (Button) findViewById(R.id.s5);
		l5.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				writeMessage("Level5");
			}
		});


	}

	@Override
	public void onStart() {
		super.onStart();
		// Get local Bluetooth adapter
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		// If BT is not on, request that it be enabled.
		// setupChat() will then be called during onActivityResult
		if (!mBluetoothAdapter.isEnabled()) {
			Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
			// Otherwise, setup the chat session
		} else if (mChatService == null) {
			// Initialize the BluetoothChatService to perform bluetooth connections
			mChatService = new BluetoothChatService(getApplicationContext(), mHandler);
		}
		// Initialize the buffer for outgoing messages
		mOutStringBuffer = new StringBuffer("");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (mChatService != null) {
			mChatService.stop();
		}
	}

	@Override
	public void onResume() {
		super.onResume();

		// Performing this check in onResume() covers the case in which BT was
		// not enabled during onStart(), so we were paused to enable it...
		// onResume() will be called when ACTION_REQUEST_ENABLE activity returns.
		if (mChatService != null) {
			// Only if the state is STATE_NONE, do we know that we haven't started already
			if (mChatService.getState() == BluetoothChatService.STATE_NONE) {
				// Start the Bluetooth chat services
				mChatService.start();
			}
		}
	}

	/**
	 * Makes this device discoverable.
	 */
	private void ensureDiscoverable() {
		if (mBluetoothAdapter.getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
			Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
			discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
			startActivity(discoverableIntent);
		}
	}

	private void displayMessage(String message) {
		display.setText(message);
	}

	private void writeMessage(String msg) {
		sendMessage(msg);
	}

	private void processMassage(String msg) {

	}

	private void setStatus(String msg) {

	}


	private Activity getActivity() {
		return SenderActivity.this;
	}

	/**
	 * Sends a message.
	 *
	 * @param message A string of text to send.
	 */
	private void sendMessage(String message) {
		// Check that we're actually connected before trying anything
		if (mChatService.getState() != BluetoothChatService.STATE_CONNECTED) {
			Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT).show();
			return;
		}

		// Check that there's actually something to send
		if (message.length() > 0) {
			// Get the message bytes and tell the BluetoothChatService to write
			byte[] send = message.getBytes();
			mChatService.write(send);

//			// Reset out string buffer to zero and clear the edit text field
			mOutStringBuffer.setLength(0);
//			mOutEditText.setText(mOutStringBuffer);
		}
	}
}
