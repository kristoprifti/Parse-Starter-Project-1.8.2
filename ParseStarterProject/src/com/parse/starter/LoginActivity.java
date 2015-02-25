package com.parse.starter;

/**
 * Created by Kristi-PC on 2/17/2015.
 */
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends Activity {
    // Declare Variables
    Button loginbutton;
    Button signup;
    String usernametxt;
    String passwordtxt;
    EditText password;
    EditText username;

    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from main.xml
        setContentView(R.layout.login);
        // Locate EditTexts in main.xml
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

        // Locate Buttons in main.xml
        loginbutton = (Button) findViewById(R.id.login);
        signup = (Button) findViewById(R.id.signup);

        // Login Button Click Listener
        loginbutton.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                // Retrieve the text entered from the EditText
                usernametxt = username.getText().toString();
                passwordtxt = password.getText().toString();

                if(isNetworkAvailable())
                {
                    // Send data to Parse.com for verification
                    ParseUser.logInInBackground(usernametxt, passwordtxt,
                            new LogInCallback() {
                                public void done(ParseUser user, ParseException e) {
                                    if (user != null) {
                                        // If user exist and authenticated, send user to Welcome.class
                                        Intent intent = new Intent(
                                                LoginActivity.this,
                                                LoggedInActivity.class);
                                        startActivity(intent);
                                        Toast.makeText(getApplicationContext(),
                                                "Successfully Logged in",
                                                Toast.LENGTH_LONG).show();
                                        finish();
                                    } else {
                                        Toast.makeText(
                                                getApplicationContext(),
                                                "No such user exist, please Sign Up",
                                                Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
                else
                {
                    Toast.makeText(getApplicationContext(),
                            "No Internet Connection!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        // Sign up Button Click Listener
        signup.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
