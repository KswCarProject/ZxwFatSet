package com.szchoiceway.zxwlib.view;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewCompat;
import com.szchoiceway.zxwlib.R;
import com.szchoiceway.zxwlib.utils.MultipleUtils;

public class DayNightImageView extends AppCompatImageView {
    private Drawable mImgDrawable;
    private int mImgDrawableColorDay = ViewCompat.MEASURED_STATE_MASK;
    private int mImgDrawableColorNight = -16776961;
    boolean mInNightMode = false;
    private boolean mNeedMirror = false;

    public DayNightImageView(Context context) {
        super(context);
    }

    public DayNightImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet);
    }

    public DayNightImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.DayNightImageView);
        this.mNeedMirror = obtainStyledAttributes.getBoolean(R.styleable.DayNightImageView_imgDrawableMirror, this.mNeedMirror);
        this.mImgDrawable = obtainStyledAttributes.getDrawable(R.styleable.DayNightImageView_imgDrawable);
        this.mImgDrawableColorDay = obtainStyledAttributes.getColor(R.styleable.DayNightImageView_imgDrawableColorDay, this.mImgDrawableColorDay);
        this.mImgDrawableColorNight = obtainStyledAttributes.getColor(R.styleable.DayNightImageView_imgDrawableColorNight, this.mImgDrawableColorNight);
        obtainStyledAttributes.recycle();
        boolean curUIModeNight = MultipleUtils.curUIModeNight(context.getResources().getConfiguration());
        this.mInNightMode = curUIModeNight;
        setDrawableColor(curUIModeNight ? this.mImgDrawableColorNight : this.mImgDrawableColorDay);
    }

    /* access modifiers changed from: protected */
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        boolean curUIModeNight = MultipleUtils.curUIModeNight(configuration);
        if (curUIModeNight != this.mInNightMode) {
            this.mInNightMode = curUIModeNight;
            setDrawableColor(curUIModeNight ? this.mImgDrawableColorNight : this.mImgDrawableColorDay);
        }
    }

    public void setLevel(int i) {
        this.mImgDrawable.setLevel(i);
        resetDrawable();
    }

    private void setDrawableColor(int i) {
        Drawable mutate = DrawableCompat.wrap(this.mImgDrawable).mutate();
        mutate.setTint(i);
        this.mImgDrawable = mutate;
        resetDrawable();
    }

    private void resetDrawable() {
        Drawable drawable = this.mImgDrawable;
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), this.mImgDrawable.getIntrinsicHeight());
        invalidate();
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        canvas.save();
        if (isLayoutRtl() && this.mNeedMirror) {
            canvas.scale(-1.0f, 1.0f, (float) (getWidth() / 2), 0.0f);
        }
        Drawable drawable = this.mImgDrawable;
        if (drawable != null) {
            drawable.draw(canvas);
        }
        canvas.restore();
    }
}
