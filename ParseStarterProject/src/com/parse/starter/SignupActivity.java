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
import com.parse.SignUpCallback;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SignupActivity extends Activity {
    // Declare Variables
    Button signup;
    String usernametxt;
    String passwordtxt;
    String mobiletxt;
    String firstname;
    String lastname;
    EditText password;
    EditText username;
    EditText mobilePhone;
    EditText firstName;
    EditText lastName;

    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from main.xml
        setContentView(R.layout.signup);
        // Locate EditTexts in main.xml
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        mobilePhone = (EditText) findViewById(R.id.mobilePhone);
        firstName = (EditText) findViewById(R.id.firstname);
        lastName = (EditText) findViewById(R.id.lastname);

        // Locate Buttons in main.xml
        signup = (Button) findViewById(R.id.signup);

        // Sign up Button Click Listener
        signup.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                // Retrieve the text entered from the EditText
                usernametxt = username.getText().toString();
                passwordtxt = password.getText().toString();
                mobiletxt = mobilePhone.getText().toString();
                firstname = firstName.getText().toString();
                lastname = lastName.getText().toString();

                // Force user to fill up the form
                if (usernametxt.equals("") || passwordtxt.equals("") || mobiletxt.equals("") || firstname.equals("") || lastname.equals("")) {
                    Toast.makeText(getApplicationContext(),
                            "Please complete the sign up form",
                            Toast.LENGTH_LONG).show();
                } else {
                    if(isNetworkAvailable())
                    {
                        // Save new user data into Parse.com Data Storage
                        ParseUser user = new ParseUser();
                        user.setUsername(usernametxt);
                        user.setPassword(passwordtxt);
                        user.put("phoneNumber", mobiletxt);
                        user.put("firstName", firstname);
                        user.put("lastName", lastname);
                        user.signUpInBackground(new SignUpCallback() {
                            public void done(ParseException e) {
                                if (e == null) {
                                    // Show a simple Toast message upon successful registration
                                    Toast.makeText(getApplicationContext(),
                                            "Successfully Signed up, please log in.",
                                            Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(),
                                            "Account already exists!", Toast.LENGTH_LONG)
                                            .show();
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
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
