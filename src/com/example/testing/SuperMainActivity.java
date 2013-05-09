package com.example.testing;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class SuperMainActivity extends Activity {

	DB profileDB;
	ExercisesDB exercises;
	SimpleCursorAdapter scAdapter;
	Cursor cursor;
	String exerciseName;
	CreateProgramOnOneWeek program;
	Vector<ExerciseView> exerciseVector;
	TextView date;
	
	@SuppressLint("DefaultLocale")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.super_main);

		profileDB = new DB(this);
		profileDB.open();
		profileDB.createProgramOnOneWeek();

		exercises = new ExercisesDB(this);
		exercises.openDataBase();

		LinearLayout nextWorkoutCard = (LinearLayout) findViewById(R.id.cardsSuper);
		date = (TextView) findViewById(R.id.dateText);
		
		Intent oldIntent = getIntent();
		Calendar mCalendar = Calendar.getInstance();
		exerciseVector = profileDB.getVectorOfExercises(mCalendar);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		date.setText(sdf.format(mCalendar.getTime()));
		 
		if (oldIntent.hasExtra("completed")) {
			exerciseVector.elementAt(oldIntent.getIntExtra("completed", -1)).exercise.completed = true;
			exerciseVector.elementAt(oldIntent.getIntExtra("completed", -1))
					.setCompleted();
		}
		for (int i = 0; i < exerciseVector.size(); i++) {
			String exerciseCodeName = exerciseVector.elementAt(i)
					.getExerciseCodeName();
			exerciseVector.elementAt(i).setContext(this);
			exerciseVector.elementAt(i).exercise.index = i;
			nextWorkoutCard.addView(exerciseVector.elementAt(i));
			exerciseVector.elementAt(i).SetExerciseName(
					getString(
							getApplicationContext().getResources()
									.getIdentifier(exerciseCodeName, "string",
											"com.example.testing"))
							.toUpperCase());
		}

	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		exerciseVector.elementAt(data.getIntExtra("completed", -1)).exercise.completed = true;
		exerciseVector.elementAt(data.getIntExtra("completed", -1))
				.setCompleted();
	}
	
	@Override
	public void onBackPressed() {
	    return;
	}

}
