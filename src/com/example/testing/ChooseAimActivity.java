package com.example.testing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ChooseAimActivity extends Activity implements OnClickListener {
	
	Button muscule, relief, strength;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registration3);
		
		muscule = (Button) findViewById(R.id.chooseMuscule);
		muscule.setOnClickListener(this);
		
		relief = (Button) findViewById(R.id.chooseRelief);
		relief.setOnClickListener(this);
		
		strength = (Button) findViewById(R.id.chooseStrength);
		strength.setOnClickListener(this);
	}

	public void onClick(View V) {
		switch(V.getId()){
		case R.id.chooseMuscule:
			Intent intent = new Intent(this, ChooseDayActivity.class);
			intent.putExtra("aim", 0);
			startActivity(intent);
			break;
		case R.id.chooseRelief:
			Intent intent1 = new Intent(this, ChooseDayActivity.class);
			intent1.putExtra("aim", 1);
			startActivity(intent1);
			break;
		case R.id.chooseStrength:
			Intent intent2 = new Intent(this, ChooseDayActivity.class);
			intent2.putExtra("aim", 2);
			startActivity(intent2);
			break;
		}
	}
	
}
