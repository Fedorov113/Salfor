package com.example.testing;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;

public class RegistrationActivityOne extends Activity implements
		OnClickListener {

	final int GENDER_DIALOG = 2;
	int male = 0, female = 1;

	EditText pick_name, pick_surname, pick_weight, pick_height;
	Button nextBtn;

	DB db;
	SimpleCursorAdapter scAdapter;
	Cursor cursor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registration1);

		nextBtn = (Button) findViewById(R.id.nextBtn);
		nextBtn.setOnClickListener(this);

		pick_name = (EditText) findViewById(R.id.pick_name);
		pick_surname = (EditText) findViewById(R.id.pick_surname);
		pick_weight = (EditText) findViewById(R.id.pick_weight);
		pick_height = (EditText) findViewById(R.id.pick_height);
	}

	public void onClick(View V) {

		switch (V.getId()) {
		case R.id.nextBtn:
			if (pick_name.getText().length() == 0) {
				pick_name.requestFocus();
				pick_name.setError(getString(R.string.complete_the_form));
				break;
			} else if (pick_surname.getText().length() == 0) {
				pick_surname.requestFocus();
				pick_surname.setError(getString(R.string.complete_the_form));
				break;
			} else if (pick_height.getText().length() == 0) {
				pick_weight.requestFocus();
				pick_weight.setError(getString(R.string.complete_the_form));
				break;
			} else if (pick_height.getText().length() == 0) {
				pick_height.requestFocus();
				pick_height.setError(getString(R.string.complete_the_form));
				break;
			}

			String name = pick_name.getText().toString();
			String surname = pick_surname.getText().toString();
			double weight = (double) Integer.parseInt(pick_weight.getText()
					.toString());
			double height = (double) Integer.parseInt(pick_height.getText()
					.toString());
			double bmi = (double) (weight / ((height * height) / 10000));

			db = new DB(this);
			db.open();
			db.destroy();

			db.addProfile(name, surname);
			db.addWeight(weight, height, bmi);

			Intent intent = new Intent(this, ChooseWeightActivity.class);
			startActivity(intent);

			break;
		}

	}

}
