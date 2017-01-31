package se.android.praycircle.praycircle.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import se.android.praycircle.praycircle.R;
import se.android.praycircle.praycircle.objects.User;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.sign_in_button) Button btnSignIn;
    @BindView(R.id.sign_up_button) Button btnSignUp;
    @BindView(R.id.btn_go_setting) Button btnGoSetting;
    @BindView(R.id.email) EditText inputEmail;
    @BindView(R.id.password) EditText inputPassword;
    @BindView(R.id.progressBar) ProgressBar progressBar;
    @BindView(R.id.btn_reset_password) Button btnResetPassword;

    private FirebaseAuth auth;
    private FirebaseAnalytics mFaAnalytics;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        mFaAnalytics = FirebaseAnalytics.getInstance(this);

        user = new User();

        btnGoSetting.setOnClickListener(this);
        btnResetPassword.setOnClickListener(this);
        btnSignIn.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }


    public void signUpUserAccount(){

        String email = inputEmail.getText().toString().trim();
        user.setUserEmail(email);
        String password = inputPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), R.string.enter_email, Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), R.string.enter_password, Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(getApplicationContext(), R.string.password_too_short, Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        //create user
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(SignUpActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this, "Authentication failed." + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                            finish();
                        }
                    }
                });

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.sign_in_button:
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                break;

            case R.id.sign_up_button:
                signUpUserAccount();
                break;

            case R.id.btn_go_setting:
                startActivity(new Intent(SignUpActivity.this, UserFirebaseSettingsActivity.class));
                break;

            case R.id.btn_reset_password:
                startActivity(new Intent(SignUpActivity.this, ResetPasswordActivity.class));
                break;


        }

    }
}

