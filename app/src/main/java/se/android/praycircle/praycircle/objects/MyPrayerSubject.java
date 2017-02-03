package se.android.praycircle.praycircle.objects;

import android.os.Parcel;
import android.os.Parcelable;

/** * Created by Paulo Vila Nova on 2017-02-02. */

public class MyPrayerSubject implements Parcelable {


    private String id;
    private String name;
    private String imageUrl;
    private String date;
    private String praySubject;
    private String prayerTitle;

    public MyPrayerSubject() {
    }

    public MyPrayerSubject(String id, String name, String imageUrl, String date, String praySubject, String prayerTitle) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.date = date;
        this.praySubject = praySubject;
        this.prayerTitle = prayerTitle;
    }

    protected MyPrayerSubject(Parcel in) {
        id = in.readString();
        name = in.readString();
        imageUrl = in.readString();
        date = in.readString();
        praySubject = in.readString();
        prayerTitle = in.readString();
    }

    public static final Creator<MyPrayerSubject> CREATOR = new Creator<MyPrayerSubject>() {
        @Override
        public MyPrayerSubject createFromParcel(Parcel in) {
            return new MyPrayerSubject(in);
        }

        @Override
        public MyPrayerSubject[] newArray(int size) {
            return new MyPrayerSubject[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(imageUrl);
        parcel.writeString(date);
        parcel.writeString(praySubject);
        parcel.writeString(prayerTitle);
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPraySubject() {
        return praySubject;
    }

    public void setPraySubject(String praySubject) {
        this.praySubject = praySubject;
    }

    public String getPrayerTitle() {
        return prayerTitle;
    }

    public void setPrayerTitle(String prayerTitle) {
        this.prayerTitle = prayerTitle;
    }
}
