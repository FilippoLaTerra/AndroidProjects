package com.lock.newtemiactionsystemtest.locker;

import java.util.ArrayList;
import java.util.List;

public class TemiDualSlotLocker extends Locker{

    public TemiDualSlotLocker() {
        super(0, "TemiDualSlotLocker", new ArrayList<Integer>(2));
    }

    @Override
    public void closeSlot(int slotNum) {

    }

    @Override
    public void openSlot(int slotNum) {

    }

    @Override
    public boolean getState(int slotNum) {
        return false;
    }

    @Override
    public void setState(int slotNum, boolean state) {

    }

    @Override
    public int getId() {
        return 0;
    }
}
