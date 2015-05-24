package com.starboy.karav.SA.UI.Database;

import android.os.Bundle;

import com.starboy.karav.SA.R;
import com.starboy.karav.SA.UI.ColourBarActivity;

public class DatabaseActivity extends ColourBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_database);
		setStatusBarColour(R.color.green_dark_leaf);
		setActionBarColour(getResources().getString(R.string.database), R.color.green_tea);
		getFragmentManager().beginTransaction().add(R.id.db_frag, new DatabaseFragment()).commit();
	}


//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.database_menu, menu);
//		return true;
//	}
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		// Handle action bar item clicks here. The action bar will
//		// automatically handle clicks on the Home/Up button, so long
//		// as you specify a parent activity in AndroidManifest.xml.
//		int id = item.getItemId();
//
//		//noinspection SimplifiableIfStatement
//		if (id == R.id.action_settings) {
//			return true;
//		}
//
//		return super.onOptionsItemSelected(item);
//	}
}
