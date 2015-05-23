package com.starboy.karav.SA.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.starboy.karav.SA.R;


public class MainActivity extends ColourBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button Sender = (Button) findViewById(R.id.senderBut);
		Button Receiver = (Button) findViewById(R.id.receiverBut);
		Sender.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(MainActivity.this, SenderActivity.class));
			}
		});

		Receiver.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(MainActivity.this, ReceiverActivity.class));
			}
		});

	}


}