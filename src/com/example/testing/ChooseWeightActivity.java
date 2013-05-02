package com.example.testing;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.TextView;

public class ChooseWeightActivity extends Activity implements
		OnValueChangeListener, OnClickListener {

	TextView bmi_number_view;
	Draw bmiBar;
	NumberPicker np;
	int weight, minWeight, maxWeight;
	double bmi = 0, new_bmi, height;
	String bmi_number;
	Button nextButton;

	DB db;
	Cursor cursor;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choose_weight);

		bmi_number_view = (TextView) findViewById(R.id.bmi_number_text_view);
		bmiBar = (Draw) findViewById(R.id.bmi_bar);
		nextButton = (Button) findViewById(R.id.nextBtn2);
		nextButton.setOnClickListener(this);

		NumberPicker np = (NumberPicker) findViewById(R.id.numberPicker1);
		np.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
		np.setOnValueChangedListener(this);

		db = new DB(this);
		db.open();
		cursor = db.getWeight();
		cursor.moveToFirst();
		
		weight = (int) cursor.getDouble(0);;
		height = cursor.getDouble(1);;
		bmi = cursor.getDouble(2);

		minWeight = (int) Math.round(3.5 * (height * height) / 10000);
		maxWeight = (int) Math.round(40 * (height * height) / 10000);

		np.setMaxValue(maxWeight);
		np.setMinValue(minWeight);
		np.setValue(weight);

		
		bmi = Math.round(bmi * 10.0) / 10.0;
		bmiBar.setBmi(bmi);
		bmi_number = Double.toString(bmi);

		bmi_number_view.setText(bmi_number);

	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.nextBtn2:
			Intent intent = new Intent(this, ChooseAimActivity.class);
			startActivity(intent);
			break;
		}
	}

	public void onValueChange(NumberPicker np, int old_weight, int new_weight) {
		double new_weight1 = (double) new_weight;
		bmi = (new_weight1 / ((height * height) / 10000));
		bmiBar.setBmi(bmi);
	}
}
