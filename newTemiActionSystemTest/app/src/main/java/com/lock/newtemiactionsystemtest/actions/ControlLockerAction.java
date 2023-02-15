package com.lock.newtemiactionsystemtest.actions;

import com.lock.newtemiactionsystemtest.locker.Locker;

public class ControlLockerAction extends Action{

    protected Locker locker;

    public ControlLockerAction() {
        super(103);
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
    public String toString() {
        return "ControlLockerAction{" +
                "action_id=" + action_id +
                ", locker=" + locker +
                '}';
    }
}
