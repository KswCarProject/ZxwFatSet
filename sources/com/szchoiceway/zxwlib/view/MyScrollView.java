package com.szchoiceway.zxwlib.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.ViewOverlay;
import android.widget.ScrollView;

public class MyScrollView extends ScrollView {
    OnScrollListener mOnScrollListener;

    public interface OnScrollListener {
        void onScroll(int i, float f, float f2);
    }

    /* access modifiers changed from: protected */
    public /* bridge */ /* synthetic */ ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return super.generateDefaultLayoutParams();
    }

    public /* bridge */ /* synthetic */ ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return super.generateLayoutParams(attributeSet);
    }

    public /* bridge */ /* synthetic */ ViewOverlay getOverlay() {
        return super.getOverlay();
    }

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public MyScrollView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        OnScrollListener onScrollListener = this.mOnScrollListener;
        if (onScrollListener != null) {
            onScrollListener.onScroll(motionEvent.getAction(), motionEvent.getY(), (float) getScrollY());
        }
        return super.onTouchEvent(motionEvent);
    }

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.mOnScrollListener = onScrollListener;
    }
}
