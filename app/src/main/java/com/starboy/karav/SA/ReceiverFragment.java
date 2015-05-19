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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * A placeholder fragment containing a simple view.
 */
public class ReceiverFragment extends Fragment {

	private String TAG = "ReceiverFragment";

	private View view;
	private TextView display;
	private Button discover;
	private TextView status_tv;
	private RelativeLayout status_level;
	private Button start;
	private Button stop;

	private Chronometer countdown;
	private Chronometer timer;
	private TextView minusSign;
	private RatingBar rating;
	private RelativeLayout status_layout;

	private ReceiverActivity receiverActivity;

	private int level;

	private boolean firstTime;
	private boolean timeOn;

	private int currentColour;

	private Animation anim;
	private ReceiverFragmentListener rListener;

	public ReceiverFragment() {
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			rListener = (ReceiverFragmentListener) activity; //add listener for activity
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must implement OnArticleSelectedListener");
		}

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_receiver, container, false);
		receiverActivity = ((ReceiverActivity) getActivity());
		setupUI();
		setupAnimate();
		return view;
	}

	private void setStatus(int status) {
		if (status == 1) {
			status_tv.setText(getResources().getString(R.string.status_unbalance));
			status_layout.setBackgroundColor(getResources().getColor(R.color.c_unbalance));
		} else {
			status_tv.setText(getResources().getString(R.string.status_balance));
			status_layout.setBackgroundColor(getResources().getColor(R.color.c_balance));
		}
	}

	private void startTime() {
		if (timeOn) {
			start.setText(getResources().getString(R.string.resume));
			timeOn = false;
			timer.stop();
			setColourAnimation(start, R.color.clear, R.color.black, 100);//change to black
			timer.startAnimation(anim);
			receiverActivity.sendMessage("T:" + "P");
		} else {
			start.setText(getResources().getString(R.string.pause));
			timeOn = true;
			int stoppedSeconds = timeStopped();
			// Amount of time elapsed since the start button was pressed, minus the time paused
			timer.setBase(SystemClock.elapsedRealtime() - stoppedSeconds * 1000);
			timer.start();
			setColourAnimation(start, R.color.black, R.color.clear, 100);
			timer.clearAnimation();
			if (!firstTime) {
				receiverActivity.sendMessage("T:" + "R");
			} else firstTime = false;
		}
	}

	private int timeStopped() {
		//Holds the number of milliseconds paused
		int stoppedSeconds = 0;
		// Get time from the chronometer
		String chronoText = timer.getText().toString();
		String array[] = chronoText.split(":");
		if (array.length == 2) {
			// Find the seconds
			stoppedSeconds = Integer.parseInt(array[0]) * 60 + Integer.parseInt(array[1]);
		} else if (array.length == 3) {
			//Find the minutes
			stoppedSeconds = Integer.parseInt(array[0]) * 60 * 60 + Integer.parseInt(array[1]) * 60 + Integer.parseInt(array[2]);
		}
		return stoppedSeconds;
	}

	private void stopTime() {

		// stop countdown
		countdown.stop();
		int totalTime = timeStopped();
		Log.d(TAG, totalTime + " ");
		//TODO sent stop message to sender
		receiverActivity.sendMessage("T:S:" + totalTime);
		Fragment summary = new SummaryFragment();
		Bundle extra = new Bundle();
		extra.putInt("Time", totalTime);
		extra.putInt("Level", level);
		summary.setArguments(extra);
		receiverActivity.replaceFragment(summary, 3);
	}

	private void setupUI() {
		firstTime = true;
		status_tv = (TextView) view.findViewById(R.id.status);
		status_level = (RelativeLayout) view.findViewById(R.id.ratingContainer);
		minusSign = (TextView) view.findViewById(R.id.minusSign);
		currentColour = R.color.c_l5;

		start = (Button) view.findViewById(R.id.start_but);
		start.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Log.d(TAG, "start click");
				startTime();
			}
		});
		stop = (Button) view.findViewById(R.id.stop_but);
		stop.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				stopTime();
			}
		});

		status_layout = (RelativeLayout) view.findViewById(R.id.status_layout);

		start.setBackgroundColor(getResources().getColor(R.color.black));
		rating = (RatingBar) view.findViewById(R.id.level_rating);
		timeOn = false;
		level = 5;
		setupTimer();
		setLevel(level);
		setStatus(0);
		startTime();
	}

	private void setupTimer() {
		Bundle bundle = this.getArguments();
		final long setedTime = bundle.getLong("time");

		countdown = (Chronometer) view.findViewById(R.id.countdown);
		countdown.setBase(SystemClock.elapsedRealtime() - setedTime);

		timer = (Chronometer) view.findViewById(R.id.timer);
		timer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
			@Override
			public void onChronometerTick(Chronometer chronometer) {
				long myElapsedMillis = SystemClock.elapsedRealtime() - timer.getBase();
				countdown.setBase(SystemClock.elapsedRealtime() - (setedTime - myElapsedMillis));
				if (myElapsedMillis >= setedTime) {
					if (minusSign.getVisibility() == View.INVISIBLE) {
						minusSign.setVisibility(View.VISIBLE);
						countdown.setTextColor(getResources().getColor(R.color.deep_orange500));
						countdown.startAnimation(anim);
						minusSign.startAnimation(anim);
					}
					//TODO alarm
				}
				countdown.setBase(SystemClock.elapsedRealtime() - Math.abs(myElapsedMillis - setedTime));
			}

		});
		timer.setBase(SystemClock.elapsedRealtime());
	}

	private void setupAnimate() {
		//blink animation
		anim = new AlphaAnimation(0.0f, 1.0f);
		anim.setDuration(100); //You can manage the time of the blink with this parameter
		anim.setStartOffset(250);
		anim.setRepeatMode(Animation.REVERSE);
		anim.setRepeatCount(Animation.INFINITE);
	}

	private void setLevel(int level) {
		rating.setProgress(level);
		switch (level) {
			case 1:
				setColourAnimation(status_level, currentColour, R.color.c_l1, 300);
				currentColour = R.color.c_l1;

//                status_level.setBackgroundColor(getResources().getColor(R.color.c_l1));
//                setColourAnimation(level1, R.color.clear, R.color.c_l1d, 250);
				break;
			case 2:
				setColourAnimation(status_level, currentColour, R.color.c_l2, 300);
				currentColour = R.color.c_l2;
//                setColourAnimation(level2, R.color.clear, R.color.c_l2d, 250);
				break;
			case 3:
				setColourAnimation(status_level, currentColour, R.color.c_l3, 300);
				currentColour = R.color.c_l3;
//                setColourAnimation(level3, R.color.clear, R.color.c_l3d, 250);
				break;
			case 4:
				setColourAnimation(status_level, currentColour, R.color.c_l4, 300);
				currentColour = R.color.c_l4;
//                setColourAnimation(level4, R.color.clear, R.color.c_l4d, 250);
				break;
			case 5:
				setColourAnimation(status_level, currentColour, R.color.c_l5, 300);
				currentColour = R.color.c_l5;
//                setColourAnimation(level5, R.color.clear, R.color.c_l5d, 250);
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

	public void updateData(int status, int level) {
		this.level = level;
		setLevel(level);
		setStatus(status);
	}

}
