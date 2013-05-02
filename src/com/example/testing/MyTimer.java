package com.example.testing;

import java.util.Timer;
import java.util.TimerTask;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.TextView;
import android.widget.AbsoluteLayout.LayoutParams;

public class MyTimer extends Activity implements OnClickListener {
	TextView text, text2, text3;
	long starttime = 0, starttime1 = 0;
	TimerCircle myTimerCircle;
	final int howLong = 60000, timeDelta = 20;
	long previous;
	float degreesForOneMilli, angleDelta, newAngle = 0;
	Button b;

	Handler h2 = new Handler();
	
	Runnable run = new Runnable() {
		public void run() {
			long millis = starttime - System.currentTimeMillis();
			
			int seconds = (int) (millis / 1000);
			int minutes = seconds / 60;
			seconds = seconds % 60;
			float angleDelta = (float) (360.0/(howLong/(previous-millis)));
			text2.setText(String.format("%d:%02d", minutes, seconds));
			if (newAngle >= 360) {
				myTimerCircle.setAngle(360);
				b.setText("Œ“ƒ€’");
				b.setEnabled(true);
				h2.removeCallbacks(this);
				return;
			}
			previous = millis;
			newAngle += angleDelta;
			myTimerCircle.setAngle(newAngle);
			
			h2.postDelayed(this, timeDelta);
		}
	};

	//Timer myTimer = new Timer();

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		int[] layout_coordinates = new int[2];
		layout_coordinates[0] = this.getResources().getDisplayMetrics().widthPixels;
		layout_coordinates[1] = this.getResources().getDisplayMetrics().heightPixels;

		text2 = (TextView) findViewById(R.id.textView112);
		AbsoluteLayout.LayoutParams params = (LayoutParams) text2
				.getLayoutParams();
		params.y = layout_coordinates[1] / 2;
		text2.setLayoutParams(params);

		int seconds = (int) (howLong / 1000);
		int minutes = seconds / 60;
		seconds = seconds % 60;
		text2.setText(String.format("%02d:%02d", minutes, seconds));
		myTimerCircle = (TimerCircle) findViewById(R.id.timerCircle);

		b = (Button) findViewById(R.id.button1113);
		b.setText("Œ“ƒ€’");
		b.setOnClickListener(this);
	}

	public void onClick(View v) {
		Button b = (Button) v;
		if (!b.isEnabled()) {
			h2.removeCallbacks(run);
			b.setText("Œ“ƒ€’");
		} else {
			newAngle = 0;
			starttime = System.currentTimeMillis() + howLong + 1000;
			previous = howLong + 1000;
			h2.removeCallbacks(run);
			h2.postDelayed(run, timeDelta);
			b.setEnabled(false);
		}
	}

}
