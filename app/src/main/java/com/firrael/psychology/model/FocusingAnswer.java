package com.firrael.psychology.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Railag on 19.03.2017.
 */

public class FocusingAnswer implements Parcelable {
    private int number;
    private double time;
    private int errorValue;

    public FocusingAnswer() {
    }

    protected FocusingAnswer(Parcel in) {
        number = in.readInt();
        time = in.readDouble();
        errorValue = in.readInt();
    }

    public static final Creator<FocusingAnswer> CREATOR = new Creator<FocusingAnswer>() {
        @Override
        public FocusingAnswer createFromParcel(Parcel in) {
            return new FocusingAnswer(in);
        }

        @Override
        public FocusingAnswer[] newArray(int size) {
            return new FocusingAnswer[size];
        }
    };

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public int getErrorValue() {
        return errorValue;
    }

    public void setErrorValue(int errorValue) {
        this.errorValue = errorValue;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(number);
        parcel.writeDouble(time);
        parcel.writeInt(errorValue);
    }
}
