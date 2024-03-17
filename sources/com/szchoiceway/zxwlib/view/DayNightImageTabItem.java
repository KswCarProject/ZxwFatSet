package com.szchoiceway.zxwlib.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.tabs.TabItem;
import com.szchoiceway.zxwlib.R;

public class DayNightImageTabItem extends TabItem {
    private int mTabItemDrawable;
    private int mTabItemDrawableColorDay;
    private int mTabItemDrawableColorNight;
    private String mTabItemTag = "";

    public DayNightImageTabItem(Context context) {
        super(context);
    }

    public DayNightImageTabItem(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.DayNightTabItem);
        this.mTabItemDrawable = obtainStyledAttributes.getResourceId(R.styleable.DayNightTabItem_tabItemDrawable, this.mTabItemDrawable);
        this.mTabItemDrawableColorDay = obtainStyledAttributes.getColor(R.styleable.DayNightTabItem_tabItemDrawableColorDay, this.mTabItemDrawableColorDay);
        this.mTabItemDrawableColorNight = obtainStyledAttributes.getColor(R.styleable.DayNightTabItem_tabItemDrawableColorNight, this.mTabItemDrawableColorNight);
        this.mTabItemTag = obtainStyledAttributes.getString(R.styleable.DayNightTabItem_tabItemTag);
        View findViewById = LayoutInflater.from(context).inflate(this.customLayout, (ViewGroup) null, false).findViewById(R.id.btnTabItem);
        if (findViewById != null && (findViewById instanceof SVGCheckView)) {
            SVGCheckView sVGCheckView = (SVGCheckView) findViewById;
            sVGCheckView.setSrcDrawable(this.mTabItemDrawable);
            sVGCheckView.setSrcDrawableColorDayNight(this.mTabItemDrawableColorDay, this.mTabItemDrawableColorNight);
        }
        obtainStyledAttributes.recycle();
    }
}
