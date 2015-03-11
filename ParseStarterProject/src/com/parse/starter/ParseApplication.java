package com.parse.starter;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseCrashReporting;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class ParseApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();

      ParseObject.registerSubclass(Hairdressers.class);
    // Add your initialization code here
      Parse.initialize(this, "Md7XkHJ4KiXpUuUWiSw7sbrJOXtwm4rzvyzMKMg7", "hgHGfnWphsNmzovysEdkNQomVjHFEaKaoBLk8Ike");

      ParseUser.enableAutomaticUser();
      ParseACL defaultACL = new ParseACL();

      // If you would like all objects to be private by default, remove this
      // line.
      defaultACL.setPublicReadAccess(true);

      ParseACL.setDefaultACL(defaultACL, true);
  }
}
