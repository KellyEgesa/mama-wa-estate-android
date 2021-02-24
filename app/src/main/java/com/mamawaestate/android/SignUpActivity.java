package com.mamawaestate.android;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mamawaestate.android.Network.BackEndApi;
import com.mamawaestate.android.Network.BackEndClient;
import com.mamawaestate.android.models.UserData;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    @BindView(R.id.showExtra)
    LinearLayout mShowExtra;
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
    @BindView(R.id.firstNameEditText)
    EditText mFirstName;
    @BindView(R.id.lastNameEditText)
    EditText mLastName;
    @BindView(R.id.checkBoxVendor)
    CheckBox mCheckBoxVendor;

    private ProgressDialog progressDialog;
    private Button CreateUserButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

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

        if(mCheckBoxVendor.isChecked()){
            mShowExtra.setVisibility(View.VISIBLE);
        }else {
            mShowExtra.setVisibility(View.GONE);
        }

        createUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
            }
        });
    }

    private void createUser() {
        String userName = mUserName.getText().toString().trim();
        String firstName = mFirstName.getText().toString().trim();
        String lastName = mLastName.getText().toString().trim();
        String email = mUserEmail.getText().toString().trim();
        String password = mUserPassword.getText().toString().trim();
        String confirmPassword = mConfirmPassword.getText().toString().trim();

        if (!isValidUserName(userName, 1) || !isEmailValid(email) || !isValidPassword(password, confirmPassword)) {
            return;
        }

        progressDialog.show();

        if (mCheckBoxVendor.isChecked()) {
            UserData userData = new UserData(userName, email, firstName, lastName, password);
            BackEndApi client = BackEndClient.urlRequest();
            Call<UserData> call = client.registerVendor(userData);
            call.enqueue(new Callback<UserData>() {
                @Override
                public void onResponse(Call<UserData> call, Response<UserData> response) {
                    if (response.isSuccessful()) {
                        progressDialog.dismiss();
                        Intent intent = new Intent(SignUpActivity.this, vendorregister.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<UserData> call, Throwable t) {
                    progressDialog.dismiss();
                }
            });
        } else {
            UserData userData = new UserData(userName, email, password);
            BackEndApi client = BackEndClient.urlRequest();
            Call<UserData> call = client.registerConsumer(userData);
            call.enqueue(new Callback<UserData>() {
                @Override
                public void onResponse(Call<UserData> call, Response<UserData> response) {
                    if (response.isSuccessful()) {
                        progressDialog.dismiss();
                        Intent intent = new Intent(SignUpActivity.this, MapActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                }
                @Override
                public void onFailure(Call<UserData> call, Throwable t) {
                    progressDialog.dismiss();
                }
            });
        }
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

    private boolean isValidUserName(String name, int position) {
        if (name.equals("")) {
            if (position == 1) {
                mUserName.setError("Enter a valid Username");
            } else if (position == 2) {
                mFirstName.setError("Enter a valid first name");
            } else {
                mLastName.setError("Enter a valid last name");
            }
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