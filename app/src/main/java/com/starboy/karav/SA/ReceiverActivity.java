package com.starboy.karav.SA;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class ReceiverActivity extends BluetoothActivity implements ReceiverFragment.ReceiverFragmentListener {
	private static final int REQUEST_ENABLE_BT = 3;
	private static final int REQUEST_GET_DEVICE = 2;
	public static String EXTRA_DEVICE_ADDRESS = "device_address";
	private String TAG = "ReceiverActivity";
	private int currentFragment;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		int currentOrientation = getResources().getConfiguration().orientation;
		if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
		} else {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
		}
		setContentView(R.layout.activity_receiver);
		setStatusBarColour(R.color.status_noconnected);
		setActionBarColour(getResources().getString(R.string.not_connected), R.color.title_noconnected);
		getFragmentManager().beginTransaction().add(R.id.r_fragment, new FlightSetFragment()).commit();
		currentFragment = 1;
	}

	private Activity getActivity() {
		return ReceiverActivity.this;
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_reciever, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		switch (id) {
			case R.id.icon_bluetooth:
				// Launch the DeviceListActivity to see devices and do scan
				Intent serverIntent = new Intent(getActivity(), BluetoothDeviceListActivity.class);
				startActivityForResult(serverIntent, 2);
				return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
			case REQUEST_GET_DEVICE:
				// When DeviceListActivity returns with a device to connect
				if (resultCode == Activity.RESULT_OK) {
					data.getStringExtra("");
					connectDevice(data.getStringExtra(EXTRA_DEVICE_ADDRESS));
				}
				break;
			case REQUEST_ENABLE_BT:
				// When the request to enable Bluetooth returns
				if (resultCode != Activity.RESULT_OK) {
					// User did not enable Bluetooth or an error occurred
					Log.d(TAG, "BT not enabled");
					Toast.makeText(getActivity(), R.string.bt_not_enabled_leaving, Toast.LENGTH_SHORT).show();
					getActivity().finish();
				}
				break;
		}
	}

	public void replaceFragment(Fragment fragment, int fragmentNum) {
		Log.d(TAG, "Start new activity");
		currentFragment = fragmentNum;
		FragmentTransaction transaction = getFragmentManager().beginTransaction();

		if (fragmentNum == 2 && connect) {//connected only can change
			transaction.addToBackStack(null);//save only fragment 1
			transaction.replace(R.id.r_fragment, fragment);
			transaction.commit();
		} else {
			transaction.replace(R.id.r_fragment, fragment);
			transaction.commit();
		}
	}


	@Override
	public void onDataReceive(int status, int level) {
		// Capture the article fragment from the activity layout
		try {
			//in case of fragment not match
			ReceiverFragment ReceiverFrag = (ReceiverFragment) getFragmentManager().findFragmentById(R.id.r_fragment);
//
			if (currentFragment == 2) {
//				Log.d(TAG, "enter");
				// Call a method in the ArticleFragment to update its content
				ReceiverFrag.updateData(status, level);
			}
//			Log.d(TAG, "not enter");
		} catch (ClassCastException e) {
			Log.d(TAG, e.toString());
		}
	}


	//message receive from bluetooth to be process and sent to fragment
	@Override
	protected void messageReceive(String s) {
		String receive[] = s.split(":");
//		Log.d(TAG, s);
		switch (receive[0]) {
			case "U":
				int status = Integer.parseInt(receive[1]);
				int level = Integer.parseInt(receive[2]);
				onDataReceive(status, level);
				break;
		}
	}
}
