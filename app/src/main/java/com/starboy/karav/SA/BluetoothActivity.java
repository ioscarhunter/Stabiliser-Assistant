package com.starboy.karav.SA;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Karav on 5/19/2015.
 */
public class BluetoothActivity extends ColourBarActivity {
	protected static final int REQUEST_ENABLE_BT = 3;
	protected static final int REQUEST_GET_DEVICE = 2;
	public static String EXTRA_DEVICE_ADDRESS = "device_address";
	protected String update = "U";
	protected String result = "R";
	protected String timer = "T";
	protected String summary = "S";
	protected boolean connect;

	/**
	 * Name of the connected device
	 */
	protected String mConnectedDeviceName = null;
	/**
	 * The Handler that gets information back from the BluetoothChatService
	 */
	protected final Handler mHandler = new Handler() {
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
						case BluetoothChatService.STATE_LOST:
							mConnectedDeviceName = null;
							setStatusBarColour(R.color.status_noconnected);
							setActionBarColour(getResources().getString(R.string.not_connected), R.color.title_noconnected);
							connect = false;
							break;
					}
					break;
//                case Constants.MESSAGE_WRITE:
//                    byte[] writeBuf = (byte[]) msg.obj;
//                    // construct a string from the buffer
//                    String writeMessage = new String(writeBuf);
//                    mConversationArrayAdapter.add("Me:  " + writeMessage);
//                    break;
				case Constants.MESSAGE_READ:    //get the message
					byte[] readBuf = (byte[]) msg.obj;
					// construct a string from the valid bytes in the buffer
					messageReceive(new String(readBuf, 0, msg.arg1));
//                    mConversationArrayAdapter.add(mConnectedDeviceName + ":  " + readMessage);
					break;
				case Constants.MESSAGE_DEVICE_NAME:
					// save the connected device's name
					mConnectedDeviceName = msg.getData().getString(Constants.DEVICE_NAME);
					if (null != activity) {
						Toast.makeText(activity, "Connected to " + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
						setStatusBarColour(R.color.title_connected);
						setActionBarColour(getResources().getString(R.string.title_connected_to) + " " + mConnectedDeviceName, R.color.status_connected);
						connect = true;
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
	/**
	 * Member object for the chat services
	 */
	protected BluetoothChatService mChatService = null;
	/**
	 * Local Bluetooth adapter
	 */
	protected BluetoothAdapter mBluetoothAdapter = null;
	private String TAG = "BluetoothActivity";
	/**
	 * String buffer for outgoing messages
	 */
	private StringBuffer mOutStringBuffer;

	protected void messageReceive(String s) {
		Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);

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
			mChatService = new BluetoothChatService(this, mHandler);
		}
		connect = false;
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
	protected void ensureDiscoverable() {
		if (mBluetoothAdapter.getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
			Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
			discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
			startActivity(discoverableIntent);
		}
	}

	/**
	 * Sends a message.
	 *
	 * @param message A string of text to send.
	 */
	public void sendMessage(String message) {
		// Check that we're actually connected before trying anything
		if (mChatService.getState() != BluetoothChatService.STATE_CONNECTED) {
			Toast.makeText(getActivity(), R.string.not_connected, Toast.LENGTH_SHORT).show();
			return;
		}

		// Check that there's actually something to send
		if (message.length() > 0) {
			// Get the message bytes and tell the BluetoothChatService to write
			byte[] send = message.getBytes();
			mChatService.write(send);

		}
	}

	/**
	 * Establish connection with other device
	 */
	protected void connectDevice(String address) {

		Log.d(TAG, address);
		// Get the BluetoothDevice object
		Log.d(TAG, "getRemoteDevice");
		BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
		// Attempt to connect to the device
		mChatService.connect(device, false);
	}

	private Activity getActivity() {
		return BluetoothActivity.this;
	}

}
