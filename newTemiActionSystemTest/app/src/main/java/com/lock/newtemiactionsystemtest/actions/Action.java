package com.lock.newtemiactionsystemtest.actions;

import java.io.Serializable;

/**
 * @author Filippo La Terra Maggiore
 * @version 1.0
 * The Action abstract class defines a basic instruction to be executed
 * allowing for better customization and management of any desired process
 */
public abstract class Action implements Serializable {

    public int action_id;

    public Action(int action_id) {
        this.action_id = action_id;
    }

    /**
     * Defines what happens when an action is started
     */
    public abstract void start();

    /**
     * Defines how an action is stopped safely
     */
    public abstract void stop();

    /**
     * Defines what an action does when it finishes its lifecycle
     */
    protected abstract void onComplete();

    /**
     * Defines how and which data the action posts  if it needs to (WIP)
     */
    protected abstract void doPost();

    /**
     * Defines how the action handles errors (WIP)
     */
    protected abstract void onError();
}
