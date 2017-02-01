package se.android.praycircle.praycircle.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import se.android.praycircle.praycircle.R;
import se.android.praycircle.praycircle.objects.User;

/** ******************CREATE USER ACCOUNT *********************************************************/

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.btnAlreadyRegistered) Button btnSignIn;
    @BindView(R.id.btnCreateAccount) Button btnSignUp;
    @BindView(R.id.email) EditText inputEmail;
    @BindView(R.id.password) EditText inputPassword;
    @BindView(R.id.progressBar) ProgressBar progressBar;
    @BindView(R.id.btnResetPassword) Button btnResetPassword;

    private FirebaseAuth auth;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        user = new User();

        btnResetPassword.setOnClickListener(this);
        btnSignIn.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);

        Log.d("FB_User", "Passando por aqui SignUpActivity");

    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

    private boolean checkFormFields() {

        user.setUserEmail(inputEmail.getText().toString().trim());
        user.setUserPassword(inputPassword.getText().toString().trim());

        if (TextUtils.isEmpty(user.getUserEmail()) && android.util.Patterns.EMAIL_ADDRESS.matcher(user.getUserEmail()).matches()) {
            Toast.makeText(getApplicationContext(), R.string.enter_email, Toast.LENGTH_LONG).show();
            return false;
        }

        if (TextUtils.isEmpty(user.getUserPassword())) {
            Toast.makeText(getApplicationContext(), R.string.enter_password, Toast.LENGTH_LONG).show();
            return false;
        }

        if (user.getUserPassword().length() < 6) {
            Toast.makeText(getApplicationContext(), R.string.password_too_short, Toast.LENGTH_LONG).show();
            return false;
        }

        return true;

    }



    public void createUserAccount(){

        if(!checkFormFields()){
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        //create user
        auth.createUserWithEmailAndPassword(user.getUserEmail(), user.getUserPassword())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("CREATE_USER", "create User With Email: onComplete: " + task.isSuccessful());
                        progressBar.setVisibility(View.GONE);

                        if (task.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this, "User created", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                            finish();

                        } else {
                            Toast.makeText(SignUpActivity.this,  "Account creation failed" , Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof FirebaseAuthUserCollisionException) {
                    Toast.makeText(SignUpActivity.this, "This email address is already in use.", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(SignUpActivity.this, "" + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.btnAlreadyRegistered:
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                break;

            case R.id.btnCreateAccount:
                createUserAccount();
                break;

            case R.id.btnResetPassword:
                startActivity(new Intent(SignUpActivity.this, ResetPasswordActivity.class));
                break;


        }

    }
}

