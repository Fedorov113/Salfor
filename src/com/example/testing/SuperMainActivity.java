package com.example.testing;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.EmbossMaskFilter;
import android.graphics.MaskFilter;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
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
		
		TextView nWT = (TextView) findViewById(R.id.nextWorkoutText);
		Typeface helvetica = Typeface.createFromAsset(getAssets(), "Fonts/5140485.ttf"); 
		nWT.setTypeface(helvetica);
		nWT.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		MaskFilter filter = new EmbossMaskFilter(new float[] {0.0f, -1.0f, 0.5f}, 0.8f, 15f, 1f);
		nWT.getPaint().setMaskFilter(filter);
		nWT.invalidate();
		
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
