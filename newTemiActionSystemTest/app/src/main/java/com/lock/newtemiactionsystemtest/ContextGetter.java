package com.lock.newtemiactionsystemtest;

import android.app.Application;
import android.content.Context;

public class ContextGetter extends Application {

    private static ContextGetter instance;

    public static ContextGetter getinstance(){
        return instance;
    }

    public static Context getContext(){
        return instance;
    }

    @Override
    public void onCreate(){
        instance = this;
        super.onCreate();
    }

}
