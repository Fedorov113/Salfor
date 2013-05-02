package com.example.testing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

	TextView helloTxt;
	Button createBtn;
	Button backupBtn;
	DB db = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		/* проверяем, сущечтвует ли база с профилем, если существует - запускаем архиглавное активити с тренировкой
		db = new DB(this);
		db.open();
	    if(db.checkDatabase()){
	    	Intent intent = new Intent(this, SuperMainActivity.class);
	    	startActivity(intent);
	    }
		*/
		setContentView(R.layout.activity_main);

		createBtn = (Button) findViewById(R.id.createBtn);
		backupBtn = (Button) findViewById(R.id.backupBtn);
		createBtn.setOnClickListener(this);
		backupBtn.setOnClickListener(this);
	}

	public void onClick(View V) {
		switch (V.getId()) {
		case R.id.createBtn:
			Intent intent = new Intent(this, RegistrationActivityOne.class);
			startActivity(intent);
			break;
		case R.id.backupBtn:
			Intent intent1 = new Intent(this, MyTimer.class);
			startActivity(intent1);
			break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
}
