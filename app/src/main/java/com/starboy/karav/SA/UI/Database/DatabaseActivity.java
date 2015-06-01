package com.starboy.karav.SA.UI.Database;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.starboy.karav.SA.Database.DatabaseHelper;
import com.starboy.karav.SA.Database.Flight;
import com.starboy.karav.SA.R;

import java.util.ArrayList;
import java.util.List;


public class DatabaseActivity extends AppCompatActivity {


	private RecyclerView mRecyclerView;
	private RecyclerView.Adapter mAdapter;
	private RecyclerView.LayoutManager mLayoutManager;
	private DatabaseHelper dbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_database);
//		setStatusBarColour(R.color.green_dark_leaf);
//		setActionBarColour(getResources().getString(R.string.database), R.color.green_tea);
		final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
		collapsingToolbar.setTitle(getResources().getString(R.string.database));

		dbHelper = new DatabaseHelper(DatabaseActivity.this);
		mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

		mRecyclerView.setHasFixedSize(true);

		mLayoutManager = new LinearLayoutManager(DatabaseActivity.this);
		mRecyclerView.setLayoutManager(mLayoutManager);

		mAdapter = new CustomAdapter(DatabaseActivity.this, initPlayer());
		mRecyclerView.setAdapter(mAdapter);

	}

	private List<Flight> initPlayer() {


		List<Flight> dataset = new ArrayList<>();
//		List<Flight> dataset = dbHelper.getFlightList();

		dataset.add(new Flight(300, 100, 1, 80 * 1000));
		dataset.add(new Flight(400, 30, 2, 480 * 1000));
		dataset.add(new Flight(100, 44, 3, 321 * 1000));
		dataset.add(new Flight(200, 200, 4, 210 * 1000));
		dataset.add(new Flight(300, 200, 2, 623 * 1000));
		dataset.add(new Flight(1000, 300, 3, 800 * 1000));
		dataset.add(new Flight(700, 400, 1, 114 * 1000));
		dataset.add(new Flight(200, 200, 5, 30 * 1000));
		dataset.add(new Flight(210, 30, 4, 125 * 1000));
		dataset.add(new Flight(590, 300, 2, 531 * 1000));
		dataset.add(new Flight(390, 100, 5, 180 * 1000));
		dataset.add(new Flight(520, 100, 4, 126 * 1000));
		dataset.add(new Flight(490, 299, 3, 816 * 1000));
		dataset.add(new Flight(1800, 390, 4, 114 * 1000));
		dataset.add(new Flight(10, 10, 3, 117 * 1000));
		dataset.add(new Flight(430, 123, 2, 131 * 1000));
		dataset.add(new Flight(1200, 123, 1, 195 * 1000));
//

		return dataset;
	}
}
