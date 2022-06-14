package com.example.pastagram;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {


    // initialize parse sdk as soon as application is started
    @Override
    public void onCreate() {
        super.onCreate();

        // Register parse models
        ParseObject.registerSubclass(Post.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("He9OiCkKWqOwLtLEQBWNDwiwt7hAbwhEZngHL12E")
                .clientKey("RT4B4WAU0YrQzOrRurMdKJOffliLbvsiSD9WiLfi")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
