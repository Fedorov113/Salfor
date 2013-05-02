package com.example.testing;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ExerciseView extends LinearLayout implements OnClickListener{

	private TextView exerciseNameTextView;
	private String exerciseCodeName;
	private Button startExercise;
	private Context myContext;
	
	public ExerciseView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = layoutInflater.inflate(R.layout.exercise_view, this);
		
		exerciseNameTextView = (TextView) view.findViewById(R.id.exerciseName);
	}
	
	public ExerciseView(Context context, AttributeSet attrs, String exercise) {
		super(context, attrs);
		
		exerciseCodeName = exercise;
		LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = layoutInflater.inflate(R.layout.exercise_view, this);
		
		startExercise = (Button) view.findViewById(R.id.startExercise);
		exerciseNameTextView = (TextView) view.findViewById(R.id.exerciseName);
	}
	
	public String getExerciseCodeName(){
		return exerciseCodeName;
	}
	
	public void SetExerciseName(String name){
		exerciseNameTextView.setText(name);
		invalidate();
		requestLayout();
	}
	
	public void setContext(Context context){
		myContext = context;
		startExercise.setOnClickListener(this);
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(myContext, MyTimer.class);
		myContext.startActivity(intent);
	}
}
