package se.android.praycircle.praycircle.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.afollestad.materialdialogs.MaterialDialog;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import se.android.praycircle.praycircle.Manifest;
import se.android.praycircle.praycircle.R;
import se.android.praycircle.praycircle.helpers.HelperClass;
import se.android.praycircle.praycircle.helpers.ImageResizeHelperClass;

public class CreateUserProfileActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.relativeLayoutProfilePhotoRegister)  RelativeLayout relativeLayoutProfilePhotoRegister;
    @BindView(R.id.circleImgRegisterProfileImage)  CircleImageView circleImgRegisterProfileImage;
    @BindView(R.id.imgButtonEditProfileImage)    ImageButton imgButtonEditProfileImage;

    @BindView(R.id.editTextProfileFirstName)    EditText editTextProfileFirstName;
    @BindView(R.id.editTextProfileLastName)    EditText editTextProfileLastName;
    @BindView(R.id.editTextProfileCountry)    EditText editTextProfileCountry;
    @BindView(R.id.editTextProfileCity)    EditText editTextProfileCity;
    @BindView(R.id.relativeLayoutRegisterProfileButton) RelativeLayout relativeLayoutRegisterProfileButton;

    public static final String USER_INFO = "user_info";
    public static final String AVATAR_URL = "avatar_url";

    private ProgressDialog progressDialogCreateProfile;
    private CreateUserProfileActivity createProfileActivity = this;
    private String imageUploadPath = "";

    private String firstName = "";
    private String lastName = "";
    private String country = "";
    private String city = "";
    private String avatarURL = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_my_prayers);
        ButterKnife.bind(this);

        relativeLayoutRegisterProfileButton.setOnClickListener(this);
        imgButtonEditProfileImage.setOnClickListener(this);


    }

    public void showDialogPickPhoto(){
        new MaterialDialog.Builder(this)
                .title(R.string.pick_a_profile_picture)
                .items(R.array.pictures)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        if(position == 0){
                            if(ContextCompat.checkSelfPermission(createProfileActivity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

                                if (ActivityCompat.shouldShowRequestPermissionRationale(createProfileActivity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                                    ActivityCompat.requestPermissions(createProfileActivity, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, HelperClass.MY_PERMISSIONS_REQUEST_CAMERA);
                                } else {
                                    ActivityCompat.requestPermissions(createProfileActivity, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, HelperClass.MY_PERMISSIONS_REQUEST_CAMERA);
                                }

                            }else{
                                HelperClass.clearCameraTempFile();
                                HelperClass.callCameraApp(createProfileActivity);

                            }

                        }else{
                            if(ContextCompat.checkSelfPermission(createProfileActivity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

                                if (ActivityCompat.shouldShowRequestPermissionRationale(createProfileActivity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                                    ActivityCompat.requestPermissions(createProfileActivity, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, HelperClass.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                                } else {
                                    ActivityCompat.requestPermissions(createProfileActivity, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, HelperClass.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                                }

                            }else{
                                HelperClass.clearCameraTempFile();
                                HelperClass.callGalleryIntent(createProfileActivity);
                            }
                        }

                    }
                }).show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case HelperClass.MY_PERMISSIONS_REQUEST_CAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    HelperClass.clearCameraTempFile();
                    HelperClass.callCameraApp(this);
                }
                break;
            case HelperClass.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    HelperClass.clearCameraTempFile();
                    HelperClass.callGalleryIntent(this);
                }
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //callbackManager.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){

            case HelperClass.MY_PERMISSIONS_REQUEST_CAMERA:

                Uri cameraUri = HelperClass.getCameraTempFile();
                imageUploadPath = cameraUri.getPath().toString();
                imageUploadPath = ImageResizeHelperClass.decodeFilePathCamera(imageUploadPath,1024,768);
                Log.d("imagesize", "IMAGE SIZE: "+new File(imageUploadPath).length());

                if(new File(imageUploadPath).length() > 0){
                    Bitmap tempBitmap = BitmapFactory.decodeFile(imageUploadPath);
                    circleImgRegisterProfileImage.setImageBitmap(tempBitmap);
                }else{
                    imageUploadPath = "";
                }
                break;

            case HelperClass.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if(data != null && data.getData() != null){
                    Uri uri = data.getData();

                    String[] projection = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(projection[0]);
                    imageUploadPath = cursor.getString(columnIndex);
                    cursor.close();

                    imageUploadPath = ImageResizeHelperClass.decodeFilePathCamera(imageUploadPath, 1024, 768);

                    if(new File(imageUploadPath).length() > 0){
                        Bitmap tempBitmap = BitmapFactory.decodeFile(imageUploadPath);
                        circleImgRegisterProfileImage.setImageBitmap(tempBitmap);
                    }else{
                        imageUploadPath = "";
                    }
                }
                break;
        }
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.relativeLayoutRegisterProfileButton:

                break;

            case R.id.imgButtonEditProfileImage:
                showDialogPickPhoto();
                break;
        }
    }
}
