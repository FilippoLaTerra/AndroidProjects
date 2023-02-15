package com.lock.newtemiactionsystemtest.locker;

import java.util.ArrayList;
import java.util.List;

public abstract class Locker {

    public int locker_id;
    public String locker_type;
    protected ArrayList<Integer> slots;

    public Locker(int locker_id, String locker_type, ArrayList<Integer> slots) {
        this.locker_id = locker_id;
        this.locker_type = locker_type;
        this.slots = slots;
    }

    public abstract void closeSlot(int slotNum);
    public abstract void openSlot(int slotNum);
    public abstract boolean getState(int slotNum);
    public abstract void setState(int slotNum, boolean state);
    public abstract int getId();

}
