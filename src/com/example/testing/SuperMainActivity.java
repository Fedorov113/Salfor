package com.example.testing;

import java.io.IOException;
import java.util.Vector;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.SimpleCursorAdapter;

public class SuperMainActivity extends Activity {
	
	DB profileDB;
	ExercisesDB exercises;
	SimpleCursorAdapter scAdapter;
	Cursor cursor;
	String exerciseName;
	CreateProgramOnOneWeek program;
	
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
		
		Vector <ExerciseView> exerciseVector = profileDB.getVectorOfExercises();
		for (int i = 0; i < exerciseVector.size(); i++){
			String exerciseCodeName = exerciseVector.elementAt(i).getExerciseCodeName();
			exerciseVector.elementAt(i).setContext(this);
			nextWorkoutCard.addView(exerciseVector.elementAt(i));
			exerciseVector.elementAt(i).SetExerciseName(getString(getApplicationContext().getResources()
			         .getIdentifier(exerciseCodeName,"string", "com.example.testing")).toUpperCase());
		}
		
	}
}
