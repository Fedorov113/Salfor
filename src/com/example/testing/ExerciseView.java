package com.example.testing;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ExerciseView extends LinearLayout implements OnClickListener{

	private TextView exerciseNameTextView, setsNumberTextView, repNumberTextView, weightTextView;
	private Button startExercise;
	private Context myContext;
	public ExerciseData exercise;
	final public int REQUEST_CODE_EXERCISE = 1;
	
	public ExerciseView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public ExerciseView(Context context, AttributeSet attrs, ExerciseData newExercise) {
		super(context, attrs);
		
		exercise = newExercise;
		
		LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = layoutInflater.inflate(R.layout.exercise_view, this);
		
		startExercise = (Button) view.findViewById(R.id.startExercise);
		Typeface helvetica = Typeface.createFromAsset(context.getAssets(), "Fonts/Helvetica_Condenced-Normal Regular.ttf"); 
		exerciseNameTextView = (TextView) view.findViewById(R.id.exerciseName);
		exerciseNameTextView.setTypeface(helvetica);
		
		setsNumberTextView = (TextView) view.findViewById(R.id.setNumberTextView);
		
		repNumberTextView = (TextView) view.findViewById(R.id.repeatNumberTextView);
		
		weightTextView = (TextView) view.findViewById(R.id.weightNumberTextView);
		
	}
	
	public String getExerciseCodeName(){
		return exercise.codeName;
	}
	
	public void SetExerciseName(String name){
		exerciseNameTextView.setText(name);
		setsNumberTextView.setText(Integer.toString(exercise.setsNumber));
		repNumberTextView.setText(Integer.toString(exercise.repeatsNumber));
		weightTextView.setText(Integer.toString(exercise.currentWeight));
		exercise.name = name;
		invalidate();
		requestLayout();
	}
	
	public void setContext(Context context){
		myContext = context;
		startExercise.setOnClickListener(this);
	}
	
	public boolean isCompleted(){
		return exercise.completed;
	}
	
	public void setCompleted(){
		startExercise.setEnabled(false);
	}

	public void onClick(View v) {
		Intent intent = new Intent(myContext, MyTimer.class);
		intent.putExtra("exercise", exercise);
		((Activity) myContext).startActivityForResult(intent, REQUEST_CODE_EXERCISE);
	}
}
