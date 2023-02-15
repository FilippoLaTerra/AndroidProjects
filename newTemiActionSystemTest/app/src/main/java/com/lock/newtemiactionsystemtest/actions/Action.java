package com.lock.newtemiactionsystemtest.actions;

import java.io.Serializable;

public abstract class Action implements Serializable {

    public int action_id;

    public Action(int action_id) {
        this.action_id = action_id;
    }

    public abstract void start();
    public abstract void stop();
    protected abstract void onComplete();
    protected abstract void doPost();

    public int getAction_id() {
        return action_id;
    }

    public void setAction_id(int action_id) {
        this.action_id = action_id;
    }
}
