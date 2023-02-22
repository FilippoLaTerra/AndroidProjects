package com.lock.newtemiactionsystemtest.actions;

import android.util.Log;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lock.newtemiactionsystemtest.R;

/**
 * @author Filippo La Terra Maggiore
 * @version 1.0
 *
 */
@JsonIgnoreProperties(value = { "actionName" })
public class SpeakMessageAction extends Action{

    protected String message;
    protected String language;

    public SpeakMessageAction(){
        super(R.integer.SpeakMessageAction);
    }

    public SpeakMessageAction(String message, String language) {
        super(R.integer.SpeakMessageAction);

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
        super(R.integer.SpeakMessageAction);

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

    @Override
    protected void onError() {

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
