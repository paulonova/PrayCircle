package se.android.praycircle.praycircle.objects;

import android.os.Parcel;
import android.os.Parcelable;

/** * Created by Paulo Vila Nova on 2016-11-19.
 */

public class User implements Parcelable {

    private String userName;
    private String userId;
    private String userEmail;
    private String userPassword;
    private String avatarUrl;
    private String location;
    private String gender;
    private int prayersCreated;
    private int prayersAttended;

    public User() {
    }

    public User(String userName, String userId, String userEmail, String userPassword, String avatarUrl, String location, String gender, int prayersCreated, int prayersAttended) {
        this.userName = userName;
        this.userId = userId;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.avatarUrl = avatarUrl;
        this.location = location;
        this.gender = gender;
        this.prayersCreated = prayersCreated;
        this.prayersAttended = prayersAttended;
    }

    protected User(Parcel in) {
        userName = in.readString();
        userId = in.readString();
        userEmail = in.readString();
        userPassword = in.readString();
        avatarUrl = in.readString();
        location = in.readString();
        gender = in.readString();
        prayersCreated = in.readInt();
        prayersAttended = in.readInt();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(userName);
        parcel.writeString(userId);
        parcel.writeString(userEmail);
        parcel.writeString(userPassword);
        parcel.writeString(avatarUrl);
        parcel.writeString(location);
        parcel.writeString(gender);
        parcel.writeInt(prayersCreated);
        parcel.writeInt(prayersAttended);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getPrayersCreated() {
        return prayersCreated;
    }

    public void setPrayersCreated(int prayersCreated) {
        this.prayersCreated = prayersCreated;
    }

    public int getPrayersAttended() {
        return prayersAttended;
    }

    public void setPrayersAttended(int prayersAttended) {
        this.prayersAttended = prayersAttended;
    }
}
