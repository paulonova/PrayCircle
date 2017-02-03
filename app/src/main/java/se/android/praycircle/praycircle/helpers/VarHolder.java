package se.android.praycircle.praycircle.helpers;

import java.util.ArrayList;

import se.android.praycircle.praycircle.objects.MyPrayerSubject;

/** * Created by Paulo Vila Nova on 2016-11-21.
 */

public class VarHolder {



    /** To instantiate the VarHolder in Activities use this call...
     * private VarHolder varHolder = VarHolder.getInstance();  */



    private static VarHolder ourInstance = null;

    public static VarHolder getInstance(){
        if(ourInstance == null){
            ourInstance = new VarHolder();
        }
        return ourInstance;
    }



    private ArrayList<MyPrayerSubject> myPrayerSubject = new ArrayList<>();

    public ArrayList<MyPrayerSubject> getMyPrayerSubject() {
        return myPrayerSubject;
    }

    public void setMyPrayerSubject(ArrayList<MyPrayerSubject> myPrayerSubject) {
        this.myPrayerSubject = myPrayerSubject;
    }
}
