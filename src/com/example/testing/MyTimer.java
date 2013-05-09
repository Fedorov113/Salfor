package com.example.testing;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MyTimer extends Activity implements OnClickListener {
	TextView exerciseName, timeLeft, text3, repeatsText, weightText,
			repeatsNumber, weightNumber, setsText, setsNumber;
	TimerCircle myTimerCircle;
	ExerciseData exercise;
	final int howLong = 60000, timeDelta = 20;
	int weightWidth = 0, setsLeft, newWeight = 0;
	long previous, starttime = 0, starttime1 = 0;
	float degreesForOneMilli, angleDelta, newAngle = 0, radius, delta;
	
	Button b;
	Context mCtx;

	Handler h2 = new Handler();

	Runnable run = new Runnable() {
		public void run() {
			long millis = starttime - System.currentTimeMillis();
			int seconds = (int) (millis / 1000);
			int minutes = seconds / 60;
			seconds = seconds % 60;
			float angleDelta = (float) (360.0 / (howLong / (previous - millis)));
			timeLeft.setText(String.format("%02d:%02d", minutes, seconds));
			// if (newAngle >= 360) {
			if (millis - 1000 <= 0) {

				try {
					myTimerCircle.setAngle(360);
					b.setText("ОТДЫХ");
					b.setEnabled(true);
					h2.removeCallbacks(this);
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				myTimerCircle.setAngle(0);
				setsLeft--;
				if (setsLeft == 0) {
					exercise.completed = true;
					Intent intent = new Intent(mCtx, SuperMainActivity.class);
					intent.putExtra("completed", exercise.index);
					setResult(RESULT_OK, intent);
					finish();
					// startActivity(intent);
				}
				setsNumber.setText(Integer.toString(setsLeft) + "/"
						+ Integer.toString(exercise.setsNumber));
				return;
			}
			previous = millis;
			newAngle += angleDelta;
			myTimerCircle.setAngle(newAngle);

			h2.postDelayed(this, timeDelta);
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mCtx = this;
		int heightPx = getResources().getDisplayMetrics().heightPixels / 2;
		int widthPx = getResources().getDisplayMetrics().widthPixels / 2;
		delta = 20 * getResources().getDisplayMetrics().density;

		exercise = (ExerciseData) getIntent().getParcelableExtra("exercise");
		setsLeft = exercise.setsNumber;
		exerciseName = (TextView) findViewById(R.id.exerciseNameInTimer);
		exerciseName.setText(exercise.name);

		int seconds = (int) (howLong / 1000);
		int minutes = seconds / 60;
		// Typeface myTypeface = Typeface.createFromAsset(getAssets(),
		// "Fonts/Helvetica-Condensed-Light-Li.ttf");
		Typeface helvetica = Typeface.createFromAsset(getAssets(),
				"Fonts/Helvetica_Condenced-Normal Regular.ttf");
		timeLeft = (TextView) findViewById(R.id.textView112);
		// timeLeft.setTypeface(myTypeface);
		timeLeft.setText(String.format("%02d:%02d", minutes, seconds % 60));
		timeLeft.setY(heightPx + delta);

		myTimerCircle = (TimerCircle) findViewById(R.id.timerCircle);
		radius = myTimerCircle.getRadius();

		weightText = (TextView) findViewById(R.id.weightTextInTimer);
		weightText.setTypeface(helvetica);

		weightNumber = (TextView) findViewById(R.id.weightNumberInTimer);
		weightNumber.setText(Integer.toString(exercise.currentWeight));

		repeatsText = (TextView) findViewById(R.id.repeatsTextInTimer);
		repeatsText.setTypeface(helvetica);

		repeatsNumber = (TextView) findViewById(R.id.repeatsNumberInTimer);
		repeatsNumber.setText(Integer.toString(exercise.repeatsNumber));

		setsText = (TextView) findViewById(R.id.setsTextInTimer);
		setsText.setTypeface(helvetica);

		setsNumber = (TextView) findViewById(R.id.setsNumberInTimer);
		setsNumber.setText(Integer.toString(exercise.setsNumber) + "/"
				+ Integer.toString(exercise.setsNumber));

		setsText.setY((float) (heightPx - radius * Math.sin(0.43)));
		setsText.setX((float) (widthPx - radius * Math.cos(0.43)));
		setsNumber.setX((float) (widthPx - radius * Math.cos(0.43)));

		repeatsText.setY((float) (heightPx - radius * Math.sin(0.43)));
		repeatsText.setX(widthPx);
		repeatsNumber.setX(widthPx);
		delta = 2 * getResources().getDisplayMetrics().density;

		b = (Button) findViewById(R.id.button1113);
		b.setText("ОТДЫХ");
		b.setOnClickListener(this);

		if (exercise.currentWeight == 0) {
			ChooseOperatingWeight dialog = new ChooseOperatingWeight();
			dialog.show(getFragmentManager(), "dialog");

		}
	}

	public void onClick(View v) {
		Button b = (Button) v;
		if (!b.isEnabled()) {
			h2.removeCallbacks(run);
			b.setText("ОТДЫХ");
		} else {
			newAngle = 0;
			starttime = System.currentTimeMillis() + howLong + 1000;
			previous = howLong + 1000;
			h2.removeCallbacks(run);
			h2.postDelayed(run, timeDelta);
			b.setEnabled(false);
		}
	}

	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		setsText.setWidth((int) (radius * Math.cos(0.43)));
		repeatsText.setWidth((int) (radius * Math.cos(0.43)));

		Typeface myTypeface = Typeface.createFromAsset(getAssets(),
				"Fonts/Helvetica-Condensed-Light-Li.ttf");

		repeatsNumber.setWidth((int) (radius * Math.cos(0.43)));
		repeatsNumber.setY(repeatsText.getY() + repeatsText.getTextSize()
				+ delta);
		repeatsNumber.setTypeface(myTypeface);

		setsNumber.setWidth((int) (radius * Math.cos(0.43)));
		setsNumber.setY(repeatsText.getY() + repeatsText.getTextSize() + delta);
		setsNumber.setTypeface(myTypeface);

		weightText.setY(repeatsNumber.getY() + repeatsText.getTextSize());
		weightNumber.setY(weightText.getY() + weightText.getTextSize() + delta);
		weightNumber.setTypeface(myTypeface);
	}

	public void onUserSelectValue(String selectedValue) {
		weightNumber.setText(selectedValue);
		newWeight = Integer.parseInt(selectedValue);

		ExercisesDB DB = new ExercisesDB(mCtx);
		DB.openDataBase();
		DB.updateCurrentWeight(newWeight, exercise.id);
		DB.close();
	}

	@Override
	public void onBackPressed() {
		Toast toast = Toast.makeText(getApplicationContext(),
				"Пожалйста, выполните упражнение!", Toast.LENGTH_SHORT);
		toast.show();
		return;
	}

}
