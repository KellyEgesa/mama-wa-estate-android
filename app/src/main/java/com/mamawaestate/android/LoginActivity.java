package com.mamawaestate.android;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.mamawaestate.android.Network.BackEndApi;
import com.mamawaestate.android.Network.BackEndClient;
import com.mamawaestate.android.models.UserData;
import com.mamawaestate.android.models.UserLocation;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {


    @BindView(R.id.registerTextView2)
    TextView createAccount;
    @BindView(R.id.emailEditText2)
    EditText mUserName;
    @BindView(R.id.passwordEditText2)
    EditText userPassword;
    @BindView(R.id.loginButton2)
    Button mLogin;
    LocationManager locationManager;
    LocationListener locationListener;
    UserLocation userLocation;
    private ProgressDialog progressDialog;

    private Button LoginButton2;

    private SharedPreferences preferences;
    private SharedPreferences.Editor mEditor;


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 10, locationListener);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = preferences.edit();

        loadingScreen();

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                Toast.makeText(LoginActivity.this, "Welcome to Mama wa Estate android application", Toast.LENGTH_SHORT).show();

            }
        });

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logIn();
            }
        });

        boolean Islogin = preferences.getBoolean("Islogin", false);
        Log.i("AAAA", String.valueOf(Islogin));
        if (Islogin) {   // condition true means user is already login
            Intent intent = new Intent(LoginActivity.this, MapActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }


    private void logIn() {
        String userName = mUserName.getText().toString().trim();
        String userPasswordText = userPassword.getText().toString().trim();

        if (!isValidUserName(userName) || !isValidPassword(userPasswordText)) {
            return;
        }

        progressDialog.show();

        UserData userData = new UserData(userName, userPasswordText);
        BackEndApi client = BackEndClient.urlRequest();
        Call<UserData> call = client.getLogin(userData);
        call.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {
                if(response.isSuccessful()){
                    progressDialog.dismiss();
                    Intent intent = new Intent(LoginActivity.this, MapActivity.class);
                    UserData user = response.body();
                    intent.putExtra("userData", Parcels.wrap(user));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {

            }
        });

    }


    private boolean isValidUserName(String name) {
        if (name.equals("")) {

            mUserName.setError("Enter a valid Username");
            return false;
        }
        return true;
    }



    private boolean isValidPassword(String password) {
        if (password.length() < 6) {
            userPassword.setError("Please enter a password containing at least 6 characters");
            return false;
        }
        return true;
    }

    private void loadingScreen() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Logging In");
        progressDialog.setMessage("Authenticating....");
        progressDialog.setCancelable(false);
    }


}



