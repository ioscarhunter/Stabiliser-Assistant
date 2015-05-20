package com.starboy.karav.SA;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Karav on 4/26/2015.
 */
public class FlightSetFragment extends Fragment {

	//    private static final int REQUEST_ENABLE_BT = 3;
//    /**
//     * Name of the connected device
//     */
//    private String mConnectedDeviceName = null;
//
//    /**
//     * Member object for the chat services
//     */
//    private BluetoothChatService mChatService = null;
//
//    /**
//     * Local Bluetooth adapter
//     */
//    private BluetoothAdapter mBluetoothAdapter = null;

	private String TAG = "FlightSetF";

	private View view;
	private TextView display;
	private Button discover;
	private TextView status;
	private Button start;
	private Button stop;
	private Button level1;
	private Button level2;
	private Button level3;
	private Button level4;
	private Button level5;
	private Chronometer countdown;
	private LinearLayout levelSelector;
	private Button plus;
	private Button minu;
	private long time;

	private int level;

	private boolean timeOn;

	private int currentColour;
	private ReceiverFragmentListener rListener;
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			//add Listener

			rListener = (ReceiverFragmentListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must implement OnArticleSelectedListener");
		}

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_flight_setting, container, false);
//        display = (TextView) view.findViewById(R.id.waitForConnect);
		status = (TextView) view.findViewById(R.id.status);
		countdown = (Chronometer) view.findViewById(R.id.countdown);
		level = 1;
		timeOn = false;
		currentColour = R.color.c_l1;
		levelSelector = (LinearLayout) view.findViewById(R.id.levelselector);
		time = 3 * 1000 * 60;
		countdown.setBase(SystemClock.elapsedRealtime() - time);

		setButton();
		level1.setBackgroundColor(getResources().getColor(R.color.black_alpha));

		return view;
	}

	private void startTime() {
		Bundle bundle = new Bundle();
		bundle.putLong("time", time);
		bundle.putInt("level", level);
		Fragment monitor = new ReceiverFragment();
		monitor.setArguments(bundle);
		Log.d(TAG, "start other");
		((ReceiverActivity) getActivity()).replaceFragment(monitor, 2);

	}


	private void setButton() {
		start = (Button) view.findViewById(R.id.start_but2);
		start.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startTime();
			}
		});
		level1 = (Button) view.findViewById(R.id.level1);
		level1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				setLevel(1);
				level = 1;
			}
		});
		level2 = (Button) view.findViewById(R.id.level2);
		level2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				setLevel(2);
				level = 2;
			}
		});
		level3 = (Button) view.findViewById(R.id.level3);
		level3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				setLevel(3);
				level = 3;
			}
		});
		level4 = (Button) view.findViewById(R.id.level4);
		level4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				setLevel(4);
				level = 4;
			}
		});
		level5 = (Button) view.findViewById(R.id.level5);
		level5.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				setLevel(5);
				level = 5;
			}
		});
//        start.setBackgroundColor(getResources().getColor(R.color.black));
		plus = (Button) view.findViewById(R.id.plus_but);
		plus.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (time == (1 * 1000 * 60)) {
					minu.setTextColor(getResources().getColor(R.color.deep_orange500));
				}
				time += 1 * 1000 * 60;
				countdown.setBase(SystemClock.elapsedRealtime() - time);
			}
		});
		minu = (Button) view.findViewById(R.id.minus_but);
		minu.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (time > (1 * 1000 * 60)) {
					time -= 1 * 1000 * 60;
					if (time == (1 * 1000 * 60)) {
						minu.setTextColor(getResources().getColor(R.color.blue_grey500));
					}
					countdown.setBase(SystemClock.elapsedRealtime() - time);
				}
			}
		});
	}

	private void resetColour() {
		level1.setBackgroundColor(getResources().getColor(R.color.clear));
		level2.setBackgroundColor(getResources().getColor(R.color.clear));
		level3.setBackgroundColor(getResources().getColor(R.color.clear));
		level4.setBackgroundColor(getResources().getColor(R.color.clear));
		level5.setBackgroundColor(getResources().getColor(R.color.clear));
	}

	private void setLevel(int level) {
		resetColour();
		switch (level) {
			case 1:
				setColourAnimation(levelSelector, currentColour, R.color.c_l1, 300);
				currentColour = R.color.c_l1;
//                status_level.setBackgroundColor(getResources().getColor(R.color.c_l1));
				setColourAnimation(level1, R.color.clear, R.color.c_l1d, 50);
				break;
			case 2:
				setColourAnimation(levelSelector, currentColour, R.color.c_l2, 300);
				currentColour = R.color.c_l2;
				setColourAnimation(level2, R.color.clear, R.color.c_l2d, 50);
				break;
			case 3:
				setColourAnimation(levelSelector, currentColour, R.color.c_l3, 300);
				currentColour = R.color.c_l3;
				setColourAnimation(level3, R.color.clear, R.color.c_l3d, 50);
				break;
			case 4:
				setColourAnimation(levelSelector, currentColour, R.color.c_l4, 300);
				currentColour = R.color.c_l4;
				setColourAnimation(level4, R.color.clear, R.color.c_l4d, 50);
				break;
			case 5:
				setColourAnimation(levelSelector, currentColour, R.color.c_l5, 300);
				currentColour = R.color.c_l5;
				setColourAnimation(level5, R.color.clear, R.color.c_l5d, 50);
				break;
			default:
				break;
		}
	}

	private void setColourAnimation(final ViewGroup textView, int from, int to, int duration) {
		Integer colorFrom = getResources().getColor(from);
		Integer colorTo = getResources().getColor(to);
		ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
		colorAnimation.setDuration(duration);
		colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

			@Override
			public void onAnimationUpdate(ValueAnimator animator) {
				textView.setBackgroundColor((Integer) animator.getAnimatedValue());
			}

		});
		colorAnimation.start();
	}

	private void setColourAnimation(final TextView textView, int from, int to, int duration) {
		Integer colorFrom = getResources().getColor(from);
		Integer colorTo = getResources().getColor(to);
		ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
		colorAnimation.setDuration(duration);
		colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

			@Override
			public void onAnimationUpdate(ValueAnimator animator) {
				textView.setBackgroundColor((Integer) animator.getAnimatedValue());
			}

		});
		colorAnimation.start();
	}


	@Override
	public void onDetach() {
		super.onDetach();
		rListener = null;
	}

}
