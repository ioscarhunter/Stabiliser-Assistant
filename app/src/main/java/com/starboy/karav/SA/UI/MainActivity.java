package com.starboy.karav.SA.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.starboy.karav.SA.R;
import com.starboy.karav.SA.UI.Database.DatabaseActivity;
import com.starboy.karav.SA.UI.Receiver.ReceiverActivity;
import com.starboy.karav.SA.UI.Sender.SenderActivity;


public class MainActivity extends ColourBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button Sender = (Button) findViewById(R.id.senderBut);
		Button Receiver = (Button) findViewById(R.id.receiverBut);
		Button Database = (Button) findViewById(R.id.databaseBut);
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

		Database.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, DatabaseActivity.class));
			}
		});

	}


}
