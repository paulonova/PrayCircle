package se.android.praycircle.praycircle.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import se.android.praycircle.praycircle.R;
import se.android.praycircle.praycircle.helpers.HelperClass;
import se.android.praycircle.praycircle.objects.User;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String BUTTON_ID = "ButtonId";
    public static final String LOGIN_SUCCESS = "Login_Success!";
    private final String TAG = "FB_SIGNIN";

    // Firebase Analytics settings
    private final int MIN_SESSION_DURATION = 5000;

    @BindView(R.id.email)               EditText inputEmail;
    @BindView(R.id.password)            EditText inputPassword;
    @BindView(R.id.progressBar)         ProgressBar progressBar;
    @BindView(R.id.btn_signup)          Button btnSignup;
    @BindView(R.id.btn_login)           Button btnLogin;
    @BindView(R.id.btnResetPassword)    Button btnReset;

    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAnalytics mFaAnalytics;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        mFaAnalytics = FirebaseAnalytics.getInstance(this);
        // Wait 5 seconds before counting this as a session
        mFaAnalytics.setMinimumSessionDuration(MIN_SESSION_DURATION);


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser fbUser = firebaseAuth.getCurrentUser();

                if (fbUser != null) {

                    saveEmailAndIdFromFirebaseInSharedPreferences(fbUser);
                    Log.d(TAG, "Signed in: " + fbUser.getUid());
                    Log.d(TAG, "HelperClass: " + HelperClass.getEmailFromFirebase(getApplicationContext()));
                    Log.d(TAG, "HelperClass: " + HelperClass.getUserIdFromFirebase(getApplicationContext()));
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                } else {
                    // User is signed out
                    Log.d(TAG, "Currently signed out");
                }
            }
        };

        btnSignup.setOnClickListener(this);
        btnReset.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }

    public void saveEmailAndIdFromFirebaseInSharedPreferences(FirebaseUser firebaseUser){

        if(firebaseUser != null){
            HelperClass.saveEmailFromFirebase(this, firebaseUser.getEmail());
            HelperClass.saveUserIdFromFirebase(this, firebaseUser.getUid());
        }

    }

    /**
     * When the Activity starts and stops, the app needs to connect and
     * disconnect the AuthListener
     */
    @Override
    public void onStart() {
        super.onStart();
        // add the AuthListener
        auth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        // Remove the AuthListener
        if (mAuthListener != null) {
            auth.removeAuthStateListener(mAuthListener);
        }
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btn_signup:
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
                break;

            case R.id.btn_login:
                signUserIn(view);
                break;

            case R.id.btnResetPassword:
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
                break;

        }

    }

    private void signUserIn(View view) {

        final Bundle bundle = new Bundle();
        bundle.putInt(BUTTON_ID, view.getId());

        String email = inputEmail.getText().toString();
        final String password = inputPassword.getText().toString();
        bundle.putString("email", email);
        bundle.putString("password", password);
        mFaAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN, bundle);

        if (TextUtils.isEmpty(email)) {
            inputEmail.setError("Please, enter email address!");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            inputPassword.setError("Please, enter password!");
            return;
        }

        Log.i(TAG, "Login requested!");
        progressBar.setVisibility(View.VISIBLE);

        //authenticate user
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                progressBar.setVisibility(View.GONE);

                if (task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Signed in", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this, "Sign in failed", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                if(e instanceof FirebaseAuthInvalidCredentialsException){
                    Toast.makeText(LoginActivity.this, "Invalid password.", Toast.LENGTH_LONG).show();

                }else if(e instanceof FirebaseAuthInvalidUserException) {
                    Toast.makeText(LoginActivity.this, "No account with this email.", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    public void signUserOut() {
        // TODO: sign the user out
        auth.signOut();
    }


}

