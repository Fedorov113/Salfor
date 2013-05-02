package com.example.testing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class ChooseDayActivity extends Activity implements OnClickListener {

	Button finishRegestrationButton;
	ListView daysList;
	DB db;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choose_days);
		
		daysList = (ListView) findViewById(R.id.lvDays);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_multiple_choice, getResources().getStringArray(R.array.days_of_week));
		daysList.setAdapter(adapter);
		daysList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

		finishRegestrationButton = (Button) findViewById(R.id.finishRegestrationButton);
		finishRegestrationButton.setOnClickListener(this);
	}

	public void onClick(View V) {
		SparseBooleanArray availableDays = daysList.getCheckedItemPositions();
		db = new DB(this);
		db.open();
		Intent oldIntent = getIntent();
		int aim = oldIntent.getIntExtra("aim", 0);
		db.addAim(aim);
		db.setAvailableDays(availableDays);
		db.close();
		
		Intent intent = new Intent(this, SuperMainActivity.class);
		startActivity(intent);
	}
}