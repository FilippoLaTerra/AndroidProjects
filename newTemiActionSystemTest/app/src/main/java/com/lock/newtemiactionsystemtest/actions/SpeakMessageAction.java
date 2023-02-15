package com.lock.newtemiactionsystemtest.actions;

import android.util.Log;

public class SpeakMessageAction extends Action{

    protected String message;
    protected String language;

    public SpeakMessageAction(String message, String language) {
        super(102);

        if(message.equals("") || message == null){
            //handles error
        } else {
            this.message = message;
        }

        if(language.equals("") || language == null){
            //uses default language
        } else {
            this.language = language;
        }
    }

    public SpeakMessageAction(String message) {
        super(102);

        if(message.equals("") || message == null){
            //handles error
        } else {
            this.message = message;
        }
    }

    @Override
    public void start() {
      System.out.println(this.toString());
      onComplete();
    }

    @Override
    public void stop() {

    }

    @Override
    protected void onComplete() {
        Log.i("Action", "Action completed");
    }

    @Override
    protected void doPost() {

    }

    public String getMessage() { return message; }

    public String getLanguage() { return language; }

    @Override
    public String toString() {
        return "SpeakMessageAction{" +
                "action_id=" + action_id +
                ", message='" + message + '\'' +
                ", language='" + language + '\'' +
                '}';
    }
}
