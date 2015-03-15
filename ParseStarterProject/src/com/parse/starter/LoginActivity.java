package com.parse.starter;

/**
 * Created by Kristi-PC on 2/17/2015.
 */
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.FunctionCallback;
import com.parse.LogInCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.HashMap;

public class LoginActivity extends Activity {
    // Declare Variables
    Button loginbutton;
    Button signup;
    String usernametxt;
    String passwordtxt;
    EditText password;
    EditText username;
    String mobiletxt;

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
                    if(usernametxt.isEmpty() || passwordtxt.isEmpty())
                    {
                        Toast.makeText(getApplicationContext(),
                                "Please complete the login form",
                                Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        // Send data to Parse.com for verification
                        ParseUser.logInInBackground(usernametxt, passwordtxt,
                                new LogInCallback() {
                                    public void done(ParseUser user, ParseException e) {
                                        if (user != null) {
                                            boolean verified = user.getBoolean("verifiedPhone");
                                            if(!verified){
                                                showInputDialog();
                                            }
                                            // If user exist and authenticated, send user to Welcome.class
                                            else{
                                                Intent intent = new Intent(
                                                        LoginActivity.this,
                                                        LoggedInActivity.class);
                                                startActivity(intent);
                                                Toast.makeText(getApplicationContext(),
                                                        "Successfully Logged in",
                                                        Toast.LENGTH_LONG).show();
                                                finish();
                                            }

                                        } else {
                                            Toast.makeText(
                                                    getApplicationContext(),
                                                    "No such user exist, please Sign Up",
                                                    Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                    }

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

    private void showInputDialog() {
        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(LoginActivity.this);
        View promptView = layoutInflater.inflate(R.layout.verify_phone_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginActivity.this);
        alertDialogBuilder.setView(promptView);

        final EditText verificationCode = (EditText) promptView.findViewById(R.id.verificationCode);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Verify", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (verificationCode.getText().toString().isEmpty()) {
                            showInputDialog();
                            Toast.makeText(getApplicationContext(), "Enter your verification code!", Toast.LENGTH_LONG).show();
                        } else {
                            HashMap<String, Object> params = new HashMap<String, Object>();
                            params.put("verCode", verificationCode.getText().toString());
                            ParseCloud.callFunctionInBackground("verifyPhoneNumber", params, new FunctionCallback<String>() {
                                public void done(String ratings, ParseException e) {
                                    if (e == null) {
                                        Intent intent = new Intent(LoginActivity.this, LoggedInActivity.class);
                                        startActivity(intent);
                                        Toast.makeText(getApplicationContext(), "Successfully Logged in", Toast.LENGTH_LONG).show();
                                        finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Verification Code Error!", Toast.LENGTH_LONG).show();
                                        showInputDialog();
                                    }
                                }
                            });
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                })
                .setNeutralButton("Get New Code", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ParseUser currentUser = ParseUser.getCurrentUser();
                        if (currentUser != null) {
                            // do stuff with the user
                            mobiletxt = currentUser.getString("phoneNumber");
                            HashMap<String, Object> params = new HashMap<String, Object>();
                            params.put("number", mobiletxt);
                            ParseCloud.callFunctionInBackground("sendVerificationCode", params, new FunctionCallback<String>() {
                                public void done(String ratings, ParseException e) {
                                    if (e == null) {
                                        Toast.makeText(getApplicationContext(), "Verification Code Sent!", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                            showInputDialog();
                        } else {
                            // show the signup or login screen
                            Toast.makeText(getApplicationContext(), "Couldn't find your phone!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
}
