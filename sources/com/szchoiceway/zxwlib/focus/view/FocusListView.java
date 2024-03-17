package com.szchoiceway.zxwlib.focus.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.ViewOverlay;
import android.widget.Adapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import androidx.core.internal.view.SupportMenu;
import com.szchoiceway.zxwlib.R;

public class FocusListView extends ListView implements FocusStateInterface {
    int mFrameColor = SupportMenu.CATEGORY_MASK;
    int mFrameWidth = 2;
    boolean mInFocus = false;
    boolean mNeedDrawFocusFrame = false;
    Paint mPaint = new Paint();
    private int mScrollbarBkOffset = 0;

    public /* bridge */ /* synthetic */ ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return super.generateLayoutParams(attributeSet);
    }

    public /* bridge */ /* synthetic */ Adapter getAdapter() {
        return super.getAdapter();
    }

    public /* bridge */ /* synthetic */ ViewOverlay getOverlay() {
        return super.getOverlay();
    }

    public /* bridge */ /* synthetic */ void setAdapter(Adapter adapter) {
        super.setAdapter((ListAdapter) adapter);
    }

    public FocusListView(Context context) {
        super(context);
    }

    public FocusListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet);
    }

    public FocusListView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.FocusListView);
        this.mNeedDrawFocusFrame = obtainStyledAttributes.getBoolean(R.styleable.FocusListView_needDrawFocusFrame, false);
        this.mFrameColor = obtainStyledAttributes.getColor(R.styleable.FocusListView_focusedFrameColor, this.mFrameColor);
        this.mFrameWidth = obtainStyledAttributes.getInt(R.styleable.FocusListView_focusedFrameWidth, this.mFrameWidth);
        obtainStyledAttributes.recycle();
        this.mPaint.setAntiAlias(true);
        this.mPaint.setColor(this.mFrameColor);
        this.mPaint.setStrokeWidth((float) this.mFrameWidth);
        this.mPaint.setStyle(Paint.Style.STROKE);
    }

    public void setFocusedState() {
        this.mInFocus = true;
        postInvalidate();
    }

    public void clearFocusedState() {
        this.mInFocus = false;
        postInvalidate();
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mInFocus && this.mNeedDrawFocusFrame && getCount() <= 0) {
            canvas.save();
            canvas.drawRect(0.0f, 0.0f, (float) getWidth(), (float) getHeight(), this.mPaint);
            canvas.restore();
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
