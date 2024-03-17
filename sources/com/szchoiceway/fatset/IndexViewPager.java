package com.szchoiceway.fatset;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewOverlay;
import androidx.viewpager.widget.ViewPager;

public class IndexViewPager extends ViewPager {
    private boolean isCanScroll = false;

    public /* bridge */ /* synthetic */ ViewOverlay getOverlay() {
        return super.getOverlay();
    }

    public IndexViewPager(Context context) {
        super(context);
    }

    public IndexViewPager(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void setScanScroll(boolean z) {
        this.isCanScroll = z;
    }

    public void scrollTo(int i, int i2) {
        super.scrollTo(i, i2);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.isCanScroll) {
            return super.onTouchEvent(motionEvent);
        }
        return false;
    }

    public void setCurrentItem(int i, boolean z) {
        super.setCurrentItem(i, z);
    }

    public void setCurrentItem(int i) {
        super.setCurrentItem(i);
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (this.isCanScroll) {
            return super.onInterceptTouchEvent(motionEvent);
        }
        return false;
    }
}
