package com.szchoiceway.zxwlib.view;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.view.ViewOverlay;
import android.widget.LinearLayout;
import androidx.core.content.ContextCompat;
import com.google.android.material.tabs.TabLayout;
import com.szchoiceway.zxwlib.R;
import com.szchoiceway.zxwlib.utils.MultipleUtils;
import java.lang.reflect.Field;

public class DayNightTabLayout extends TabLayout {
    private static final String SCROLLABLE_TAB_MAX_WIDTH = "requestedTabMaxWidth";
    private static final String SCROLLABLE_TAB_MIN_WIDTH = "scrollableTabMinWidth";
    private int mDividerDrawableDayId = 0;
    private int mDividerDrawableNightId = 0;
    private float mDividerDrawablePadding = 0.0f;
    private int mDividerShowMode = 0;
    boolean mInNightMode = false;
    private int mIndicatorColorDay = -16776961;
    private int mIndicatorColorNight = -1;
    private int mOnePageMaxItem = 6;

    /* access modifiers changed from: protected */
    public /* bridge */ /* synthetic */ ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return super.generateDefaultLayoutParams();
    }

    public /* bridge */ /* synthetic */ ViewOverlay getOverlay() {
        return super.getOverlay();
    }

    public DayNightTabLayout(Context context) {
        super(context);
    }

    public DayNightTabLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet);
    }

    public DayNightTabLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.DayNightTabLayout);
        this.mOnePageMaxItem = obtainStyledAttributes.getInt(R.styleable.DayNightTabLayout_onePageMaxItem, this.mOnePageMaxItem);
        this.mIndicatorColorDay = obtainStyledAttributes.getColor(R.styleable.DayNightTabLayout_indicatorColorDay, this.mIndicatorColorDay);
        this.mIndicatorColorNight = obtainStyledAttributes.getColor(R.styleable.DayNightTabLayout_indicatorColorNight, this.mIndicatorColorNight);
        this.mDividerDrawableDayId = obtainStyledAttributes.getResourceId(R.styleable.DayNightTabLayout_dividerDrawableDay, this.mDividerDrawableDayId);
        this.mDividerDrawableNightId = obtainStyledAttributes.getResourceId(R.styleable.DayNightTabLayout_dividerDrawableNight, this.mDividerDrawableNightId);
        this.mDividerDrawablePadding = obtainStyledAttributes.getDimension(R.styleable.DayNightTabLayout_dividerDrawablePadding, this.mDividerDrawablePadding);
        this.mDividerShowMode = obtainStyledAttributes.getInt(R.styleable.DayNightTabLayout_dividerShowMode, this.mDividerShowMode);
        initTabMinWidth();
        boolean curUIModeNight = MultipleUtils.curUIModeNight(context.getResources().getConfiguration());
        this.mInNightMode = curUIModeNight;
        setSelectedTabIndicatorColor(curUIModeNight ? this.mIndicatorColorNight : this.mIndicatorColorDay);
        setDivider();
        obtainStyledAttributes.recycle();
    }

    private void initTabMinWidth() {
        int i = getResources().getDisplayMetrics().widthPixels;
        int i2 = i / this.mOnePageMaxItem;
        Log.i("TAG", "initTabMinWidth: " + i + " |  " + this.mOnePageMaxItem);
        try {
            Field declaredField = TabLayout.class.getDeclaredField(SCROLLABLE_TAB_MIN_WIDTH);
            declaredField.setAccessible(true);
            declaredField.set(this, Integer.valueOf(i2));
            Field declaredField2 = TabLayout.class.getDeclaredField(SCROLLABLE_TAB_MAX_WIDTH);
            declaredField2.setAccessible(true);
            declaredField2.set(this, Integer.valueOf(i2));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
        }
    }

    private void setDivider() {
        LinearLayout linearLayout = (LinearLayout) getChildAt(0);
        linearLayout.setShowDividers(this.mDividerShowMode);
        linearLayout.setDividerPadding((int) this.mDividerDrawablePadding);
        if (this.mDividerDrawableNightId != 0 && this.mDividerDrawableDayId != 0) {
            linearLayout.setDividerDrawable(ContextCompat.getDrawable(getContext(), this.mInNightMode ? this.mDividerDrawableNightId : this.mDividerDrawableDayId));
        }
    }

    /* access modifiers changed from: protected */
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        boolean curUIModeNight = MultipleUtils.curUIModeNight(configuration);
        if (curUIModeNight != this.mInNightMode) {
            this.mInNightMode = curUIModeNight;
            setSelectedTabIndicatorColor(curUIModeNight ? this.mIndicatorColorNight : this.mIndicatorColorDay);
            setDivider();
        }
    }
}
