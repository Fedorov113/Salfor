package com.example.testing;

import android.content.Context;
import android.database.Cursor;
import android.widget.SimpleCursorAdapter;

public class CreateProgramOnOneWeek {
	DB profileDB;
	SimpleCursorAdapter scAdapter;
	Cursor cursor;
	Context mCtx;
	String sAvailavleDays;
	
	public CreateProgramOnOneWeek(Context ctx){
		mCtx = ctx;
	}
	
	public void createProgram(){
		profileDB = new DB(mCtx);
		profileDB.open();
		cursor = profileDB.getAvailableDays();
		//startManagingCursor(cursor);
		cursor.moveToFirst();
		sAvailavleDays = cursor.getString(0);
		
	}
}
