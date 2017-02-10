package se.android.praycircle.praycircle.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.EditText;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import se.android.praycircle.praycircle.R;

/** * Created by Paulo Vila Nova on 2017-02-06.
 */

public class HelperClass {

    public static final String EMAIL_PREFERENCES = "email_preferences";
    public static final String EMAIL_KEY = "email_key";
    public static final String ID_PREFERENCES = "id_preferences";
    public static final String ID_KEY = "id_key";
    public static final String TEMP_FILE_JPG = "tmp_file.jpg";

    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 2;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 3;
    public static final String PROFILE_IS_DONE = "profile_is_done";
    public static final String PROFILE_IS_DONE_KEY = "profile_is_done_key";
    private static VarHolder varHolder = VarHolder.getInstance();



    //SharedPreferences

    public static void saveEmailFromFirebase(Context context, String email){
        SharedPreferences sharedPreferences = context.getSharedPreferences(EMAIL_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(EMAIL_KEY, email);
        edit.apply();
    }

    public static String getEmailFromFirebase(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(EMAIL_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getString(EMAIL_KEY, "");
    }


    public static void saveUserIdFromFirebase(Context context, String userId){
        SharedPreferences sharedPreferences = context.getSharedPreferences(ID_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(ID_KEY, userId);
        edit.apply();
    }

    public static String getUserIdFromFirebase(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(ID_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getString(ID_KEY, "");
    }

    public static void setUserProfileDone(Context context, boolean isProfileDone){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PROFILE_IS_DONE, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putBoolean(PROFILE_IS_DONE_KEY, isProfileDone);
        edit.commit();
    }

    public static boolean isProfileDone(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PROFILE_IS_DONE, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(PROFILE_IS_DONE_KEY, false);
    }




    //Photo picker methods

    public static void clearCameraTempFile() {
        try {
            File tempDir = Environment.getExternalStorageDirectory();
            File tempImg = new File(tempDir.getAbsolutePath() , TEMP_FILE_JPG);
            if (tempImg.exists()){
                tempImg.delete();
                Log.d("camtempfile", "REMOVED TEMP FILE");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void callCameraApp(Activity activity) {

        Uri tempUri = createCameraTempFile();
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
        activity.startActivityForResult(cameraIntent, MY_PERMISSIONS_REQUEST_CAMERA);
    }

    public static Uri createCameraTempFile() {
        try {
            File tempDir = Environment.getExternalStorageDirectory();
            File tempImg = new File(tempDir.getAbsolutePath() , TEMP_FILE_JPG);
            tempImg.createNewFile();

            return Uri.fromFile(tempImg);
        } catch (Exception e) {
            Log.v("captureImg", "Can't create file to take picture!" + e);
            return Uri.EMPTY;
        }
    }


    public static Uri getCameraTempFile() {
        try {
            File tempDir = Environment.getExternalStorageDirectory();
            File tempImg = new File(tempDir.getAbsolutePath() , TEMP_FILE_JPG);
            try {
                tempImg.createNewFile();
            }catch (Exception e){
                Log.d("cameratemp", "COULD NOT CREATE A NEW FILE");
            }
            if (tempImg.exists())
                return Uri.fromFile(tempImg);
            return null;
        } catch (Exception e) {
            Log.v("captureimg", "Can't create file to take picture!"+e);
            e.printStackTrace();
            return null;
        }
    }

    public static void callGalleryIntent(Activity activity){
        Uri tempUri = createCameraTempFile();
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(Intent.createChooser(i, activity.getString(R.string.title_dialog_pick_event_cover)), MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
    }





    // VALIDATIONS
    public static boolean editTextOnlyTextValidation(EditText edt1){

        if(edt1.getText().toString().equals("")){
            edt1.setError("Please fill the empty fields..");
            return false;

        }else{
            Pattern ps = Pattern.compile("^[a-zA-Z ]+$");
            Matcher ms = ps.matcher(edt1.getText().toString());
            boolean bs = ms.matches();
            Log.d("PATTERN_TEST", "value: " + bs);

            if(!bs){
                edt1.setError("Please only text..");
                return false;
            }
        }

        return true;
    }







}
