package com.lock.newtemiactionsystemtest.actions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lock.newtemiactionsystemtest.R;
import com.lock.newtemiactionsystemtest.locker.Locker;

@JsonIgnoreProperties(value = { "actionName" })
public class ControlLockerAction extends Action{

    protected Locker locker;
    protected String code;
    protected boolean needsInput;
    protected String state;

    public ControlLockerAction() {
        super(R.integer.ControlLockerAction);
    }

    public ControlLockerAction(Locker locker, String code, boolean needsInput, String state) {
        super(R.integer.ControlLockerAction);
        this.locker = locker;
        this.code = code;
        this.needsInput = needsInput;
        this.state = state;
    }

    public ControlLockerAction(int action_id, Locker locker, String state) {
        super(R.integer.ControlLockerAction);
        this.locker = locker;
        this.state = state;
    }

    @Override
    public void start() {

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

    @Override
    public String toString() {
        return "ControlLockerAction{" +
                "action_id=" + action_id +
                ", locker=" + locker +
                '}';
    }
}
