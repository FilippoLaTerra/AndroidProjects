package com.lock.newtemiactionsystemtest.actions;

import android.app.Activity;
import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.lock.newtemiactionsystemtest.ContextGetter;
import com.lock.newtemiactionsystemtest.R;

public class testAction extends Action{

    ImageView testImage;
    Context context;

    public testAction(ImageView image, Context context) {
        super(0);
        this.testImage = image;
        this.context = context;
    }

    @Override
    public void start() {
        Activity test = new Activity();
        Animation rotateImage = AnimationUtils.loadAnimation(ContextGetter.getContext(), R.anim.rotate);

        test.runOnUiThread(new Runnable() {

            @Override
            public void run() {

                testImage.startAnimation(rotateImage);

            }

        });


    }

    @Override
    public void stop() {

    }

    @Override
    protected void onComplete() {

    }

    @Override
    protected void doPost() {

    }

    @Override
    protected void onError() {

    }
}
