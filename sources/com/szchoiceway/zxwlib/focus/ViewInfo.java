package com.szchoiceway.zxwlib.focus;

public class ViewInfo {
    private int nextFocusDownId;
    private int nextFocusLeftId;
    private int nextFocusRightId;
    private int nextFocusUpId;
    private String page;
    private String viewClassName;
    private int viewId;

    public int getViewId() {
        return this.viewId;
    }

    public void setViewId(int i) {
        this.viewId = i;
    }

    public int getNextFocusLeftId() {
        return this.nextFocusLeftId;
    }

    public void setNextFocusLeftId(int i) {
        this.nextFocusLeftId = i;
    }

    public int getNextFocusRightId() {
        return this.nextFocusRightId;
    }

    public void setNextFocusRightId(int i) {
        this.nextFocusRightId = i;
    }

    public int getNextFocusUpId() {
        return this.nextFocusUpId;
    }

    public void setNextFocusUpId(int i) {
        this.nextFocusUpId = i;
    }

    public int getNextFocusDownId() {
        return this.nextFocusDownId;
    }

    public void setNextFocusDownId(int i) {
        this.nextFocusDownId = i;
    }

    public String getViewClassName() {
        return this.viewClassName;
    }

    public void setViewClassName(String str) {
        this.viewClassName = str;
    }

    public String getPage() {
        return this.page;
    }

    public void setPage(String str) {
        this.page = str;
    }

    public String toString() {
        return "viewId = " + toHex(this.viewId) + "  nextFocusLeftId = " + toHex(this.nextFocusLeftId) + "  nextFocusRightId = " + toHex(this.nextFocusRightId) + "  nextFocusUpId = " + toHex(this.nextFocusUpId) + "  nextFocusDownId = " + toHex(this.nextFocusDownId) + "  viewClassName = " + this.viewClassName + "  page = " + this.page;
    }

    private String toHex(int i) {
        return "0x" + Integer.toHexString(i);
    }
}
