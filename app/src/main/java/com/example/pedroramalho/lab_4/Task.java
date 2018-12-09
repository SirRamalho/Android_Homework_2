package com.example.pedroramalho.lab_4;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class Task implements Parcelable {

    public String title;
    public String description;
    public String age="";
    public String picPath;



Task(String task1, String s, Object o, String s1){

};

void addPicPath(String pPath){
    picPath=pPath;
}

Task(String t, String d){
    title = t;
    description = d;
    age="";
    picPath = null;


}

Task(String t, String d, String a ){

    title = t;
    description = d;
    age=a;
    picPath = null;

}

    protected Task(Parcel in) {
        title = in.readString();
        description = in.readString();
        age=in.readString();
        picPath = in.readString();
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {

            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {

            return new Task[size];
        }
    };

    @Override
    public String toString(){

        return title ;
   }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(age);
        dest.writeString(picPath);
    }
}
