package com.starboy.karav.SA.UI.Database;

/**
 * Created by Karav on 5/23/2015.
 */

import android.content.Context;
import android.os.SystemClock;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.RatingBar;
import android.widget.TextView;

import com.starboy.karav.SA.Database.Flight;
import com.starboy.karav.SA.R;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

	private List<Flight> mPlayers;
	private Context mContext;
	private View v;

	public CustomAdapter(Context context, List<Flight> dataset) {
		mPlayers = dataset;
		mContext = context;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		v = LayoutInflater.from(mContext).inflate(R.layout.db_display, parent, false);
		return new ViewHolder(v);
	}

	@Override
	public void onBindViewHolder(ViewHolder viewHolder, int position) {
		Flight flight = mPlayers.get(position);
		viewHolder.level.setText(v.getResources().getString(getCharLevel(flight.getLevel())));
		viewHolder.rating.setNumStars(flight.getRating());
		viewHolder.timer.setBase(SystemClock.elapsedRealtime() - flight.getTakeTime());
		viewHolder.colourIndicator.setBackgroundColor(v.getResources().getColor(getIndicator(flight.getRating())));
	}

	private int getCharLevel(int level) {
		if (level == 1) return R.string.level_1;
		if (level == 2) return R.string.level_2;
		if (level == 3) return R.string.level_3;
		if (level == 4) return R.string.level_4;
		if (level == 5) return R.string.level_5;
		return 0;
	}

	private int getIndicator(int colour) {
		if (colour == 1) return R.color.c_l1;
		if (colour == 2) return R.color.c_l2;
		if (colour == 3) return R.color.c_l3;
		if (colour == 4) return R.color.c_l4;
		if (colour == 5) return R.color.c_l5;
		return 0;
	}

	@Override
	public int getItemCount() {
		return mPlayers.size();
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {
		public TextView level;
		public RatingBar rating;
		public Chronometer timer;
		public Button colourIndicator;
		private View v;

		public ViewHolder(View view) {
			super(view);
			v = view;
			level = (TextView) v.findViewById(R.id.DBLevel);
			rating = (RatingBar) v.findViewById(R.id.DBratingBar);
			timer = (Chronometer) v.findViewById(R.id.DBTime);
			colourIndicator = (Button) v.findViewById(R.id.colour_indecator);
		}


	}
}