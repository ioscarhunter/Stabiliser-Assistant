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


public class ReceiverActivity extends BluetoothActivity implements RecieverFragmentListener {
	private static final int REQUEST_ENABLE_BT = 3;
	private static final int REQUEST_GET_DEVICE = 2;
	public static String EXTRA_DEVICE_ADDRESS = "device_address";
	private String TAG = "ReceiverActivity";


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
	}



//    private void displayMessage(String message) {
//        display.setText(message);
////    }

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
//            case R.id.discover_rec:
//                ensureDiscoverable();
//                return true;
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
				if (resultCode == Activity.RESULT_OK) {
					// Bluetooth is now enabled, so set up a chat session
//                    setupChat();
				} else {
					// User did not enable Bluetooth or an error occurred
					Log.d(TAG, "BT not enabled");
					Toast.makeText(getActivity(), R.string.bt_not_enabled_leaving, Toast.LENGTH_SHORT).show();
					getActivity().finish();
				}
				break;
		}

	}

	public void startNewActivity(Class<?> nextActivity, Bundle extra) {
		Log.d("ReceiverA", Integer.toString(extra.getInt("Time")));
		Intent intent = new Intent(getApplicationContext(), nextActivity);
		intent.putExtras(extra);
		startActivity(intent);
		this.finish();
	}


	public void replaceFragment(Fragment fragment) {
		Log.d(TAG, "Start new activity");
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.replace(R.id.r_fragment, fragment);
		transaction.addToBackStack(null);
		transaction.commit();
	}


	public void sentdata(String data) {
		sendMessage(data);
	}

	@Override
	public void onDataReceive(int status, int level) {

	}
}
