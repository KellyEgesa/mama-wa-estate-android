package com.mamawaestate.android;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpActivity extends AppCompatActivity {

    @BindView(R.id.nameEditText)
    EditText mUserName;
    @BindView(R.id.emailEditText)
    EditText mUserEmail;
    @BindView(R.id.passwordEditText)
    EditText mUserPassword;
    @BindView(R.id.confirmPasswordEditText)
    EditText mConfirmPassword;
    @BindView(R.id.createUserButton)
    Button createUser;
    @BindView(R.id.loginTextView)
    TextView logIn;

    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        loadingScreen();

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        createUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
            }
        });

    }

    private void createUser() {
        String userName = mUserName.getText().toString().trim();
        String email = mUserEmail.getText().toString().trim();
        String password = mUserPassword.getText().toString().trim();
        String confirmPassword = mConfirmPassword.getText().toString().trim();

        if (!isValidUserName(userName) || !isEmailValid(email) || !isValidPassword(password, confirmPassword)) {
            return;
        }

        progressDialog.show();

        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }

    private void loadingScreen() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("Loading....");
        progressDialog.setCancelable(false);
    }

    private boolean isEmailValid(String email) {
        boolean isGoodEmail = (email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches());
        if (!isGoodEmail) {
            mUserEmail.setError("Enter a valid email address");
            return false;
        }
        return true;
    }

    private boolean isValidUserName(String name) {
        if (name.equals("")) {
            mUserName.setError("Enter a name");
            return false;
        }
        return true;
    }

    private boolean isValidPassword(String password, String confirmPassword) {
        if (password.length() < 6) {
            mUserPassword.setError("Please create a password containing at least 6 characters");
            return false;
        } else if (!password.equals(confirmPassword)) {
            mConfirmPassword.setError("Passwords do not match");
            return false;
        }
        return true;
    }
}