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
	//	private LinearLayout levelSelector;
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
			rListener = (ReceiverFragmentListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must implement OnArticleSelectedListener");
		}

	}

	//
//	@Override
//	public void onA
	// this method is only called once for this fragment
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// retain this fragment
		setRetainInstance(true);
	}

	@Override
	public void onStart() {
		super.onStart();
//        // Get local Bluetooth adapter
//        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//        // If BT is not on, request that it be enabled.
//        // setupChat() will then be called during onActivityResult
//        if (!mBluetoothAdapter.isEnabled()) {
//            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
//            // Otherwise, setup the chat session
//        } else if (mChatService == null) {
//            // Initialize the BluetoothChatService to perform bluetooth connections
//            mChatService = new BluetoothChatService(getActivity(), mHandler);
//        }
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

		level = 5;

		timeOn = false;
		currentColour = R.color.c_l5;
//		levelSelector = (LinearLayout) view.findViewById(R.id.levelselector);

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

		Bundle bundle = this.getArguments();
		final long setedTime = bundle.getLong("time");
//            Log.d(TAG,"time = "+i);
//		int level = bundle.getInt("level");

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
		setLevel(level);
		setStatus(0);
		startTime();
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


//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        if (mChatService != null) {
//            mChatService.stop();
//        }
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//
//        // Performing this check in onResume() covers the case in which BT was
//        // not enabled during onStart(), so we were paused to enable it...
//        // onResume() will be called when ACTION_REQUEST_ENABLE activity returns.
//        if (mChatService != null) {
//            // Only if the state is STATE_NONE, do we know that we haven't started already
//            if (mChatService.getState() == BluetoothChatService.STATE_NONE) {
//                // Start the Bluetooth chat services
//                mChatService.start();
//            }
//        }
//    }
//
//    /**
//     * Makes this device discoverable.
//     */
//    private void ensureDiscoverable() {
//        if (mBluetoothAdapter.getScanMode() !=
//                BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
//            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
//            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
//            startActivity(discoverableIntent);
//        }
//    }
//
//    private void messageReceive(String message) {
//        display.setText(message);
//    }
//
//    /**
//     * The Handler that gets information back from the BluetoothChatService
//     */
//    private final Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            FragmentActivity activity = getActivity();
//            switch (msg.what) {
//                case Constants.MESSAGE_STATE_CHANGE:
//                    switch (msg.arg1) {
//                        case BluetoothChatService.STATE_CONNECTED:
////                            (getActivity()).setTitle(getString(R.string.title_connected_to, mConnectedDeviceName));
////                            mConversationArrayAdapter.clear();
//                            break;
//                        case BluetoothChatService.STATE_CONNECTING:
////                            setStatus(R.string.title_connecting);
//                            break;
//                        case BluetoothChatService.STATE_LISTEN:
//                            break;
//                        case BluetoothChatService.STATE_NONE:
////                            setStatus(R.string.title_not_connected);
//                            break;
//                    }
//                    break;
////                case Constants.MESSAGE_WRITE:
////                    byte[] writeBuf = (byte[]) msg.obj;
////                    // construct a string from the buffer
////                    String writeMessage = new String(writeBuf);
////                    mConversationArrayAdapter.add("Me:  " + writeMessage);
////                    break;
//                case Constants.MESSAGE_READ:    //get the message
//                    byte[] readBuf = (byte[]) msg.obj;
//                    // construct a string from the valid bytes in the buffer
//                    messageReceive(new String(readBuf, 0, msg.arg1));
////                    mConversationArrayAdapter.add(mConnectedDeviceName + ":  " + readMessage);
//                    break;
//                case Constants.MESSAGE_DEVICE_NAME:
//                    // save the connected device's name
//                    mConnectedDeviceName = msg.getData().getString(Constants.DEVICE_NAME);
//                    if (null != activity) {
//                        Toast.makeText(activity, "Connected to "
//                                + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
//                    }
//                    break;
//                case Constants.MESSAGE_TOAST:
//                    if (null != activity) {
//                        Toast.makeText(activity, msg.getData().getString(Constants.TOAST),
//                                Toast.LENGTH_SHORT).show();
//                    }
//                    break;
//            }
//        }
//    };
}
