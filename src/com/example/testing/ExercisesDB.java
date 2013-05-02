package com.example.testing;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

@SuppressLint({ "SdCardPath", "UseSparseArrays" })
public class ExercisesDB extends SQLiteOpenHelper {

	@SuppressLint("SdCardPath")
	private static String DB_PATH = "/data/data/com.example.testing/databases/";
	private static String DB_NAME = "exercise_data";
	private static final String TABLE_EXERCISE = "exercises";
	private SQLiteDatabase myDataBase;
	private final Context myContext;
	private static int musculeGroupNumber = 3;
	// private int[] groupOne = new int[] { 1, 3, 4, 5, 6 };
	// private int[] groupTwo = new int[] { 7, 9, 10, 11, 12 };
	// private int[] groupThree = new int[] { 2, 14, 8, 13 };
	// private String[] sGroupOne = new String[] { "1", "3", "4", "5", "6" };
	// private String[] sGroupTwo = new String[] { "7", "9", "10", "11", "12" };
	// private String[] sGroupThree = new String[] { "2", "14", "8", "13" };
	private Map<Integer, Integer> mGroupOne = new HashMap<Integer, Integer>();
	private Map<Integer, Integer> mGroupTwo = new HashMap<Integer, Integer>();
	private Map<Integer, Integer> mGroupThree = new HashMap<Integer, Integer>();

	public ExercisesDB(Context context) {
		super(context, DB_NAME, null, 1);
		this.myContext = context;
	}

	/*-------------создаём базу данных и потом переписываем её своей базой--------*/
	public void createDataBase() throws IOException {
		boolean dbExist = checkDataBase();
		if (dbExist) {
			// do nothing - database already exist
		} else {
			/*----------------тут создается пустая база-----*/
			this.getReadableDatabase();

			try {
				copyDataBase();
			} catch (IOException e) {
				throw new Error("Error copying database");
			}
		}

	}

	/**
	 * Check if the database already exist to avoid re-copying the file each
	 * time you open the application.
	 * 
	 * @return true if it exists, false if it doesn't
	 */
	private boolean checkDataBase() {

		SQLiteDatabase checkDB = null;

		try {
			String myPath = DB_PATH + DB_NAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.OPEN_READONLY);

		} catch (SQLiteException e) {
			// database does't exist yet.
		}

		if (checkDB != null)
			checkDB.close();

		return checkDB != null ? true : false;
	}

	/**
	 * Copies your database from your local assets-folder to the just created
	 * empty database in the system folder, from where it can be accessed and
	 * handled. This is done by transfering bytestream.
	 * */
	private void copyDataBase() throws IOException {

		// Open your local db as the input stream
		InputStream myInput = myContext.getAssets().open(DB_NAME);

		// Path to the just created empty db
		String outFileName = DB_PATH + DB_NAME;

		// Open the empty db as the output stream
		OutputStream myOutput = new FileOutputStream(outFileName);

		// transfer bytes from the inputfile to the outputfile
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}

		// Close the streams
		myOutput.flush();
		myOutput.close();
		myInput.close();

	}

	public void openDataBase() throws SQLException {

		// Open the database
		String myPath = DB_PATH + DB_NAME;
		myDataBase = SQLiteDatabase.openDatabase(myPath, null,
				SQLiteDatabase.OPEN_READONLY);

	}

	public String getExercisesForOneDay(int groupNumber) {
		String sExercisesForOneDay = "";
		initializeMaps();
		Set<?> set;
		Iterator<?> iterator = null;
		switch (groupNumber) {
		case 0:
			set = mGroupOne.entrySet();
			iterator = set.iterator();
			break;
		case 1:
			set = mGroupTwo.entrySet();
			iterator = set.iterator();
			break;
		case 2:
			set = mGroupThree.entrySet();
			iterator = set.iterator();
			break;
		}

		Vector<Integer> exercisesForOneDay = new Vector<Integer>();
		String selection = "muscule=?";
		int currentExerciseNumber = 0;
		while (iterator.hasNext()) {
			@SuppressWarnings("rawtypes")
			Map.Entry m = (Map.Entry) iterator.next();
			int howManyExercises = (Integer) m.getValue();
			String sWhatMuscle = String.valueOf((Integer) m.getKey());
			String[] selectionArgs = new String[] { sWhatMuscle };
			int i = 0;
			while (i < howManyExercises) {
				Cursor data = myDataBase.query(TABLE_EXERCISE, null, selection,
						selectionArgs, null, null, null);
				int min = 0, max = data.getCount();
				int randomExercise = min
						+ (int) (Math.random() * ((max - min) + 1));
				if (data.move(randomExercise)) {
					if (exercisesForOneDay.isEmpty()) {
						exercisesForOneDay.add(data.getInt(0));
						i++;
						currentExerciseNumber++;
					} 
					else {
						if (!(exercisesForOneDay.elementAt(currentExerciseNumber - 1) == data
								.getInt(0))) {
							exercisesForOneDay.add(data.getInt(0));
							i++;
							currentExerciseNumber++;
						}
					}
				} 
				else if (randomExercise == 0) {
					if(howManyExercises == 1){
						data.moveToFirst();
						exercisesForOneDay.add(data.getInt(0));
						i++;
						currentExerciseNumber++;
					}
				}
				data.moveToFirst();
			}
		}

		for(int j = 0; j < exercisesForOneDay.size(); j++){
				sExercisesForOneDay = sExercisesForOneDay + String.valueOf(exercisesForOneDay.elementAt(j)) + ",";
		}
		
		return sExercisesForOneDay;
	}

	@Override
	public synchronized void close() {
		if (myDataBase != null)
			myDataBase.close();
		super.close();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	public String getExerciseName(int exerciseNumber) {
		String[] columns = new String[] { "name" };
		String selection = "_id=?";
		String sWhatExercise = String.valueOf(exerciseNumber);
		String[] selectionArgs = new String[] { sWhatExercise };
		Cursor cursor = myDataBase.query(TABLE_EXERCISE, columns, selection, selectionArgs, null,
				null, null);
		String name;
		cursor.moveToFirst();
		name = cursor.getString(0);
		return name;
	}

	public void initializeMaps() {
		mGroupOne.put(1, 2);
		mGroupOne.put(3, 1);
		mGroupOne.put(4, 2);
		mGroupOne.put(5, 2);
		mGroupOne.put(6, 1);
		mGroupTwo.put(7, 2);
		mGroupTwo.put(8, 2);
		mGroupTwo.put(9, 1);
		mGroupTwo.put(10, 2);
		mGroupTwo.put(11, 2);
		mGroupThree.put(2, 2);
		mGroupThree.put(12, 2);
		mGroupThree.put(13, 2);
		mGroupThree.put(14, 1);
	}

	public Cursor getAllExercisesFromOneGroup(char whatGroup) {
		// String[] columns = new String[] { "_id", "name" };
		String selection = "muscule_group=?";
		String sWhatGroup = String.valueOf(whatGroup);
		String[] selectionArgs = new String[] { sWhatGroup };
		return myDataBase.query(TABLE_EXERCISE, null, selection, selectionArgs,
				null, null, null);
	}
	


	public int getMusculeGroupNumber() {
		return musculeGroupNumber;
	}

}