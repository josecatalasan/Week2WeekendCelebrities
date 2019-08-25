package com.example.week2weekendcelebrities.model.celebrity;

import android.os.Parcel;
import android.os.Parcelable;

public class Celebrity implements Parcelable {

    private String name, height, born, nationality;
    int favorite;

    public Celebrity() {
    }

    public Celebrity(String name, String height, String born, String nationality) {
        this.name = name;
        this.height = height;
        this.born = born;
        this.nationality = nationality;
        this.favorite = 0;
    }

    protected Celebrity(Parcel in) {
        name = in.readString();
        height = in.readString();
        born = in.readString();
        nationality = in.readString();
        favorite = in.readInt();
    }

    public static final Creator<Celebrity> CREATOR = new Creator<Celebrity>() {
        @Override
        public Celebrity createFromParcel(Parcel in) {
            return new Celebrity(in);
        }

        @Override
        public Celebrity[] newArray(int size) {
            return new Celebrity[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getBorn() {
        return born;
    }

    public void setBorn(String born) {
        this.born = born;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(height);
        parcel.writeString(born);
        parcel.writeString(nationality);
        parcel.writeInt(favorite);
    }
}
