package com.starboy.karav.SA.UI.Database;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.starboy.karav.SA.Database.DatabaseHelper;
import com.starboy.karav.SA.Database.Flight;
import com.starboy.karav.SA.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class DatabaseFragment extends Fragment {

	private RecyclerView mRecyclerView;
	private RecyclerView.Adapter mAdapter;
	private RecyclerView.LayoutManager mLayoutManager;
	private DatabaseHelper dbHelper;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_database, container, false);

		mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

		mRecyclerView.setHasFixedSize(true);

		mLayoutManager = new LinearLayoutManager(getActivity());
		mRecyclerView.setLayoutManager(mLayoutManager);

		mAdapter = new CustomAdapter(getActivity(), initPlayer());
		mRecyclerView.setAdapter(mAdapter);
		return view;
	}

	private List<Flight> initPlayer() {

		Flight messi = new Flight(5, 4, 100 * 1000);
		Flight ronaldo = new Flight(4, 3, 59 * 1000);
		Flight suarez = new Flight(1, 2, 180 * 1000);

		List<Flight> dataset = new ArrayList<>();

		dataset.add(messi);
		dataset.add(ronaldo);
		dataset.add(suarez);
		dataset.add(new Flight(3, 1, 80 * 1000));
		dataset.add(new Flight(4, 2, 480 * 1000));
		dataset.add(new Flight(1, 3, 321 * 1000));
		dataset.add(new Flight(2, 4, 210 * 1000));
		dataset.add(new Flight(3, 2, 623 * 1000));
		dataset.add(new Flight(1, 3, 800 * 1000));
		dataset.add(new Flight(2, 1, 114 * 1000));
		dataset.add(new Flight(2, 5, 30 * 1000));
		dataset.add(new Flight(2, 4, 125 * 1000));
		dataset.add(new Flight(5, 2, 531 * 1000));
		dataset.add(new Flight(3, 5, 180 * 1000));
		dataset.add(new Flight(5, 4, 126 * 1000));
		dataset.add(new Flight(4, 3, 816 * 1000));
		dataset.add(new Flight(1, 4, 114 * 1000));
		dataset.add(new Flight(1, 3, 117 * 1000));
		dataset.add(new Flight(4, 2, 131 * 1000));
		dataset.add(new Flight(1, 1, 195 * 1000));


		return dataset;
	}
}
