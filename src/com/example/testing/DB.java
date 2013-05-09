package com.example.testing;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.SparseBooleanArray;

public class DB {

	private static final String DB_NAME = "user_database.db";
	private static final int DB_VERSION = 1;

	/*-------------------------user_table-----------------------*/
	public static final String TABLE_PROFILE = "profile";
	public static final String UID = "_id";
	public static final String NAME = "name";
	public static final String SURNAME = "surname";
	public static final String GENDER = "gender";
	public static final String DATE_OF_BIRTH = "date_of_birth";
	public static final String AVAILABLE_DAYS = "available_days";
	public static final String AIM = "aim";
	public static final String WEIGHT_TABLE = "weight_table";

	private static final String SQL_CREATE_ENTRIES_PROFILE = "CREATE TABLE "
			+ TABLE_PROFILE + " (" + UID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT," + NAME + " TEXT," + SURNAME
			+ " TEXT," + GENDER + " TEXT," + DATE_OF_BIRTH + " INTEGER,"
			+ WEIGHT_TABLE + " TEXT," + AIM + " INTEGER," + AVAILABLE_DAYS
			+ " TEXT);";

	private static final String SQL_DELETE_ENTRIES_PROFILE = "DROP TABLE IF EXISTS "
			+ TABLE_PROFILE;
	/*-------------------------weight_table---------------------*/
	public static final String TABLE_WEIGHT = "weight_table";
	public static final String DATE = "date";
	public static final String WEIGHT = "weight";
	public static final String HEIGHT = "height";
	public static final String BMI = "bmi";

	private static final String SQL_CREATE_ENTRIES_WEIGHT = "CREATE TABLE "
			+ TABLE_WEIGHT + " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ DATE + " TEXT," + WEIGHT + " INTEGER," + HEIGHT + " REAL," + BMI
			+ " REAL);";

	private static final String SQL_DELETE_ENTRIES_WEIGHT = "DROP TABLE IF EXISTS "
			+ TABLE_WEIGHT;

	/*---------------------------program table-----------------------*/
	public static final String TABLE_PROGRAM = "program_table";
	public static final String WEEK_NUMBER = "week";
	public static final String MONDAY = "monday";
	public static final String TUESDAY = "tuesday";
	public static final String WEDNESDAY = "wednesday";
	public static final String THURSDAY = "thursday";
	public static final String FRIDAY = "friday";
	public static final String SATURDAY = "saturday";
	public static final String SUNDAY = "sunday";
	public static final String[] sAllDays = new String[] { MONDAY, TUESDAY,
			WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY };

	private static final String SQL_CREATE_ENTRIES_PROGRAM = "CREATE TABLE "
			+ TABLE_PROGRAM + " (" + UID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT," + WEEK_NUMBER + " INTEGER,"
			+ MONDAY + " INTEGER," + TUESDAY + " INTEGER," + WEDNESDAY
			+ " INTEGER," + THURSDAY + " INTEGER," + FRIDAY + " INTEGER,"
			+ SATURDAY + " INTEGER," + SUNDAY + " INTEGER);";

	private static final String SQL_DELETE_ENTRIES_PROGRAM = "DROP TABLE IF EXISTS "
			+ TABLE_PROGRAM;

	/*-------------------------exercise_table---------------------*/
	public static final String TABLE_EXERCISE = "exercise_table";
	public static final String EXERCISE_NAME = "weight";
	public static final String MUSCULE_GROUP = "height";
	public static final String MUSCULE = "bmi";

	/*-------------------------------------------------------------*/
	private static final String DB_TABLE = "mytab";

	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_IMG = "img";
	public static final String COLUMN_TXT = "txt";

	private final Context mCtx;

	private DBHelper mDBHelper;
	private SQLiteDatabase mDB = null;
	private Cursor daysForWorkout;
	//private int iDaysForWorkoutOnTheWeek;

	public DB(Context ctx) {
		mCtx = ctx;
	}

	public void destroy() {
		if (mDBHelper != null)
			mDBHelper.onUpgrade(mDB, DB_VERSION, 2);
	}

	public void open() {
		mDBHelper = new DBHelper(mCtx, DB_NAME, null, 2);
		mDB = mDBHelper.getWritableDatabase();
	}

	public void close() {
		if (mDBHelper != null)
			mDBHelper.close();
	}

	public boolean checkDatabase() {
		SQLiteDatabase checkDB = null;

		String DB_PATH = "/data/data/com.example.testing/databases/";
		try {
			String myPath = DB_PATH + DB_NAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.OPEN_READONLY);

		} catch (SQLiteException e) {
		}

		if (checkDB != null)
			checkDB.close();

		return checkDB != null ? true : false;
	}

	public void createProgramOnOneWeek() {
		/*-----------------узнаем номер текущей недели и готовим к записи---------------*/
		int currentWeek = new GregorianCalendar().get(Calendar.WEEK_OF_YEAR);
		ContentValues cv = new ContentValues();
		cv.put(WEEK_NUMBER, currentWeek);

		/*-----------------узнаем номер текущеего дня недели---------------*/
		int dayOfWeek = new GregorianCalendar().get(Calendar.DAY_OF_WEEK) - 1;
		if (dayOfWeek == 0)
			dayOfWeek = 7;
		dayOfWeek--;
		
		/*----------если запись с текущей неделей сделана -выходим----------------*/
		Cursor weekData = mDB.query(TABLE_PROGRAM, new String[]{"week"}, null, null, null,
				null, null);
		weekData.moveToLast();
		if(weekData.getInt(0) == currentWeek)
			return;
		
		
		/*------------------теперь узнаем, есть ли на текущей неделе дни для тнерировки-------------------*/
		daysForWorkout = mDB.query(TABLE_PROGRAM, sAllDays, null, null, null,
				null, null);
		daysForWorkout.moveToFirst();
		boolean isThereSomeGoodDays = false;
		int iDaysForWorkout = 0;
		for (int i = 0; i < 7; i++) {
			if ((daysForWorkout.getInt(i) == 1) && (i >= dayOfWeek)) {
				isThereSomeGoodDays = true;
				iDaysForWorkout++;
			}
		}
		
		
		ExercisesDB exercisesDB;
		exercisesDB = new ExercisesDB(mCtx);
		try {
			exercisesDB.createDataBase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		exercisesDB.openDataBase();
		
		String sExercisesForOneDay;//в этой строке будут записаны номера упражнений на один день
		if (isThereSomeGoodDays) {
			for(int i = 0; i < 7; i++){
				if ((daysForWorkout.getInt(i) == 1) && (i >= dayOfWeek)){
					sExercisesForOneDay = exercisesDB.getExercisesForOneDay(iDaysForWorkout % 3);
					cv.put(findDayByNumber(i), sExercisesForOneDay);
					iDaysForWorkout++;
				}
			}
			mDB.insert(TABLE_PROGRAM, null, cv);
		}
		exercisesDB.close();
	}

	public Vector<ExerciseView> getVectorOfExercises(Calendar cal){
		Vector<ExerciseView> vectorOfExercises = new Vector<ExerciseView>();
		String sExercisesForToday = "";
		int currentWeek = new GregorianCalendar().get(Calendar.WEEK_OF_YEAR);
		int dayOfWeek = new GregorianCalendar().get(Calendar.DAY_OF_WEEK) - 1;
		if (dayOfWeek == 0)
			dayOfWeek = 7;
		dayOfWeek--;
		
		Cursor programForLastWeek = mDB.query(TABLE_PROGRAM, null, null,
				null, null, null, null);
		programForLastWeek.moveToLast();
		if(programForLastWeek.getInt(1) == currentWeek){
			programForLastWeek.moveToFirst();
			for(int i = 2; i < 9; i++){
				String oneDay = programForLastWeek.getString(i);
				if((oneDay != null && !oneDay.isEmpty()) && (dayOfWeek <= (i-2))){
					programForLastWeek.moveToLast();
					sExercisesForToday = programForLastWeek.getString(i);
					int k = i;
					if(k == 8)
						k = 1;
					cal.setFirstDayOfWeek(Calendar.MONDAY);
					cal.set(Calendar.WEEK_OF_YEAR, currentWeek);        
					cal.set(Calendar.DAY_OF_WEEK, k);
					i = 9;
				}
			}
			
			String oneExercise;
			int iOneExercise;
			
			ExercisesDB exercisesDB;
			exercisesDB = new ExercisesDB(mCtx);
			exercisesDB.openDataBase();
			
			int i = 0, lastCharacter = 0;
			while(i < sExercisesForToday.length()){
				lastCharacter = sExercisesForToday.indexOf(",", i);
				oneExercise = sExercisesForToday.substring(i, lastCharacter);
				iOneExercise = Integer.parseInt(oneExercise);
				ExerciseView view = new ExerciseView(mCtx, null, exercisesDB.getExerciseName(iOneExercise));
				vectorOfExercises.add(view);
				i = lastCharacter;
				i++;
			}
		}
		
		return vectorOfExercises;
	}
	
	public void setAvailableDays(SparseBooleanArray availableDays) {
		ContentValues cv = new ContentValues();

		for (int i = 0; i < availableDays.size(); i++) {
			if (availableDays.get(availableDays.keyAt(i))) {
				cv.put(findDayByNumber(availableDays.keyAt(i)), 1);
			}
		}

		mDB.insert(TABLE_PROGRAM, null, cv);
	}

	public Cursor getAllData(String table_name) {
		String[] columns = new String[] { "name" };
		return mDB.query(table_name, columns, null, null, null, null, null);
	}

	public Cursor getWeight() {
		String[] columns = new String[] { "weight", "height", "bmi" };
		return mDB.query(TABLE_WEIGHT, columns, null, null, null, null, null);
	}

	public Cursor getAvailableDays() {
		String[] columns = new String[] { AVAILABLE_DAYS };
		return mDB.query(TABLE_PROFILE, columns, null, null, null, null, null);
	}

	public void addProfile(String name, String surname) {
		ContentValues cv = new ContentValues();
		cv.put(NAME, name);
		cv.put(SURNAME, surname);
		mDB.insert(TABLE_PROFILE, null, cv);
	}

	public void addAvailbleDays(String days) {
		ContentValues cv = new ContentValues();
		//iDaysForWorkoutOnTheWeek = days.length();
		cv.put(AVAILABLE_DAYS, days);
		mDB.update(TABLE_PROFILE, cv, "_id " + "=" + 1, null);
	}

	public void addAim(int aim) {
		ContentValues cv = new ContentValues();
		cv.put(AIM, aim);
		mDB.update(TABLE_PROFILE, cv, "_id " + "=" + 1, null);
	}

	public void addWeight(double weight, double height, double bmi) {
		ContentValues cv = new ContentValues();
		cv.put(WEIGHT, weight);
		cv.put(HEIGHT, height);
		cv.put(BMI, bmi);
		mDB.insert(TABLE_WEIGHT, null, cv);
	}

	public void delRec(long id) {
		mDB.delete(DB_TABLE, COLUMN_ID + " = " + id, null);
	}

	private String findDayByNumber(int dayOfWeek) {
		switch (dayOfWeek) {
		case 0:
			return MONDAY;
		case 1:
			return TUESDAY;
		case 2:
			return WEDNESDAY;
		case 3:
			return THURSDAY;
		case 4:
			return FRIDAY;
		case 5:
			return SATURDAY;
		case 6:
			return SUNDAY;
		}
		return "";
	}

	public class DBHelper extends SQLiteOpenHelper {

		public DBHelper(Context context, String name, CursorFactory factory,
				int version) {
			super(context, name, factory, version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(SQL_CREATE_ENTRIES_PROFILE);
			db.execSQL(SQL_CREATE_ENTRIES_WEIGHT);
			db.execSQL(SQL_CREATE_ENTRIES_PROGRAM);
		}

		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL(SQL_DELETE_ENTRIES_PROFILE);
			db.execSQL(SQL_DELETE_ENTRIES_WEIGHT);
			db.execSQL(SQL_DELETE_ENTRIES_PROGRAM);
			onCreate(db);
		}

	}
}