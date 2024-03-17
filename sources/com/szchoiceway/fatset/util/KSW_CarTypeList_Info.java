package com.szchoiceway.fatset.util;

public class KSW_CarTypeList_Info {
    protected static final String TAG = "KSW_CarTypeList_Info";
    private int iCarId;
    private String strCarName;

    public int getCarId() {
        return this.iCarId;
    }

    public String getCarName() {
        return this.strCarName;
    }

    public void setCarId(int i) {
        this.iCarId = i;
    }

    public void setCarName(String str) {
        this.strCarName = str;
    }

    public String toString() {
        return "--->>> iCarID = " + this.iCarId + ", strCarName = " + this.strCarName;
    }
}
