package com.starboy.karav.SA.UI.Database;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;

import com.starboy.karav.SA.Database.DatabaseHelper;
import com.starboy.karav.SA.Database.Flight;
import com.starboy.karav.SA.R;

import java.util.ArrayList;
import java.util.List;


public class DetailedDatabaseActivity extends AppCompatActivity implements DatabaseSumFragment.OnFragmentInteractionListener {


	private RecyclerView mRecyclerView;
	private RecyclerView.Adapter mAdapter;
	private RecyclerView.LayoutManager mLayoutManager;
	private DatabaseHelper dbHelper;
	private CollapsingToolbarLayout collapsingToolbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_database);
		getFragmentManager().beginTransaction().add(R.id.backdrop, DatabaseSumFragment.newInstance()).commit();
//		setStatusBarColour(R.color.green_dark_leaf);
//		setActionBarColour(getResources().getString(R.string.database), R.color.green_tea);

		if (getSupportActionBar() == null) {
			final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
			setSupportActionBar(toolbar);

			collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
			collapsingToolbar.setTitle(getResources().getString(R.string.database));
		} else {
			getSupportActionBar().setTitle(R.string.database);
		}
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		dbHelper = new DatabaseHelper(DetailedDatabaseActivity.this);
		mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

		mRecyclerView.setHasFixedSize(true);

		DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
//		float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
		float dpWidth = displayMetrics.widthPixels / displayMetrics.density;

		mLayoutManager = new CustomLayoutManager(DetailedDatabaseActivity.this, Math.round(dpWidth), (int) (getResources().getDimension(R.dimen.max_recycler) / displayMetrics.density));
		mRecyclerView.setLayoutManager(mLayoutManager);

		mAdapter = new CustomAdapter(DetailedDatabaseActivity.this, initPlayer());
		mRecyclerView.setAdapter(mAdapter);

	}

	private List<Flight> initPlayer() {


		List<Flight> flightSet = new ArrayList<>();
//		List<Flight> flightSet = dbHelper.getFlightList();

		flightSet.add(new Flight(300, 100, 1, 80 * 1000));
		flightSet.add(new Flight(400, 30, 2, 480 * 1000));
		flightSet.add(new Flight(100, 44, 3, 321 * 1000));
		flightSet.add(new Flight(200, 200, 4, 210 * 1000));
		flightSet.add(new Flight(300, 200, 2, 623 * 1000));
		flightSet.add(new Flight(1000, 300, 3, 800 * 1000));
		flightSet.add(new Flight(700, 400, 1, 114 * 1000));
		flightSet.add(new Flight(200, 200, 5, 30 * 1000));
		flightSet.add(new Flight(210, 30, 4, 125 * 1000));
		flightSet.add(new Flight(590, 300, 2, 531 * 1000));
		flightSet.add(new Flight(390, 100, 5, 180 * 1000));
		flightSet.add(new Flight(520, 100, 4, 126 * 1000));
		flightSet.add(new Flight(490, 299, 3, 816 * 1000));
		flightSet.add(new Flight(1800, 390, 4, 114 * 1000));
		flightSet.add(new Flight(10, 10, 3, 117 * 1000));
		flightSet.add(new Flight(430, 123, 2, 131 * 1000));
		flightSet.add(new Flight(1200, 123, 1, 195 * 1000));
//

		return flightSet;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.sample_actions, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				finish();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}


	@Override
	public void onFragmentInteraction(Uri uri) {

	}
}
