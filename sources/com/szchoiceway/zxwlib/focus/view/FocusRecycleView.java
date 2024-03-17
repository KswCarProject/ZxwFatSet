package com.szchoiceway.zxwlib.focus.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.ViewOverlay;
import androidx.core.internal.view.SupportMenu;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FocusRecycleView extends RecyclerView implements FocusStateInterface {
    boolean mInFocus = false;
    Paint mPaint = new Paint();
    private int mScrollbarBkOffset = 0;

    public /* bridge */ /* synthetic */ ViewOverlay getOverlay() {
        return super.getOverlay();
    }

    public FocusRecycleView(Context context) {
        super(context);
        init();
    }

    public FocusRecycleView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public FocusRecycleView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    private void init() {
        this.mPaint.setAntiAlias(true);
        this.mPaint.setColor(SupportMenu.CATEGORY_MASK);
        this.mPaint.setStrokeWidth(2.0f);
        this.mPaint.setStyle(Paint.Style.STROKE);
    }

    public void setFocusedState() {
        this.mInFocus = true;
    }

    public void clearFocusedState() {
        this.mInFocus = false;
    }

    public int getFirstVisiblePosition() {
        RecyclerView.LayoutManager layoutManager = getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            return ((GridLayoutManager) layoutManager).findFirstVisibleItemPosition();
        }
        if (layoutManager instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
        }
        return -1;
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable parcelable) {
        try {
            super.onRestoreInstanceState(parcelable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setmScrollbarBkOffset(int i) {
        this.mScrollbarBkOffset = i;
    }

    /* access modifiers changed from: protected */
    public void onDrawVerticalScrollBar(Canvas canvas, Drawable drawable, int i, int i2, int i3, int i4) {
        int i5 = this.mScrollbarBkOffset;
        super.onDrawVerticalScrollBar(canvas, drawable, i, i2 + i5, i3, i4 - i5);
    }
}
