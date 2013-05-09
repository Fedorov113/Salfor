package com.example.testing;

import android.os.Parcel;
import android.os.Parcelable;

public class ExerciseData implements Parcelable {
	public String codeName;
	public String name;
	public int id;
	public int currentWeight;
	public int setsNumber;
	public int repeatsNumber;
	public int muscule;
	public int index = 0;
	public boolean completed = false;
	
	ExerciseData(String newCodeName, int newId, int newCurrentWeight, int newSetsNumber, int newRepeatsNumber, int newMuscule){
		codeName = newCodeName;
		id = newId;
		currentWeight = newCurrentWeight;
		setsNumber = newSetsNumber;
		repeatsNumber = newRepeatsNumber;
		muscule = newMuscule;
	}

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(codeName);
		dest.writeString(name);
		dest.writeInt(id);
		dest.writeInt(currentWeight);
		dest.writeInt(setsNumber);
		dest.writeInt(repeatsNumber);
		dest.writeInt(muscule);
		dest.writeInt(index);
	}
	
	public static final Parcelable.Creator<ExerciseData> CREATOR = new Parcelable.Creator<ExerciseData>() {
        public ExerciseData createFromParcel(Parcel in) {
            return new ExerciseData(in);
        }

        public ExerciseData[] newArray(int size) {
            return new ExerciseData[size];
        }
    };
    
    private ExerciseData(Parcel in) {
    	codeName = in.readString();
    	name = in.readString();
    	id = in.readInt();
    	currentWeight = in.readInt();
    	setsNumber = in.readInt();
    	repeatsNumber = in.readInt();
    	muscule = in.readInt();
    	index = in.readInt();
    }
}
