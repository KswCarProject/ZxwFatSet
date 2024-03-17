package com.szchoiceway.fatset;

import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;
import android.util.Log;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import com.szchoiceway.eventcenter.EventUtils;
import com.szchoiceway.fatset.fragment.FragmentCan;
import com.szchoiceway.fatset.fragment.FragmentCarSpecial;
import com.szchoiceway.fatset.fragment.FragmentCarType;
import com.szchoiceway.fatset.fragment.FragmentFunction;
import com.szchoiceway.fatset.fragment.FragmentLogo;
import com.szchoiceway.fatset.fragment.FragmentUISelect;
import com.szchoiceway.fatset.fragment.FragmentUIStyle;
import com.szchoiceway.fatset.fragment.FragmentUpgrade;
import com.szchoiceway.fatset.fragment.FragmentVehicle;
import com.szchoiceway.fatset.fragment.FragmentVehicleButton;
import com.szchoiceway.fatset.util.MultipleUtils;
import com.szchoiceway.fatset.util.MyRecyclerAdapter;
import com.szchoiceway.zxwlib.bean.Customer;
import java.util.ArrayList;
import java.util.List;

public class FatSetMainActivity extends FragmentActivity {
    private static final String TAG = "FatSetMainActivity";
    /* access modifiers changed from: private */
    public MyRecyclerAdapter adapter;
    /* access modifiers changed from: private */
    public List<Fragment> fragments;
    private RecyclerView mRecyclerView;
    private ViewPager mViewPager;
    private int m_iModeSet = 16;
    private int m_iUITypeVer = 41;
    private ZXWFatApp main;
    private FragmentPagerAdapter pagerAdapter;
    private List<String> strs = new ArrayList();

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        MultipleUtils.setCustomDensity(this, getApplication());
        ZXWFatApp zXWFatApp = (ZXWFatApp) getApplication();
        this.main = zXWFatApp;
        this.m_iUITypeVer = zXWFatApp.GetUItypeVer();
        this.m_iModeSet = this.main.ksw_GetModeSet();
        Log.d(TAG, "onCreate: m_iUITypeVer = " + this.m_iUITypeVer + ", m_iModeSet = " + this.m_iModeSet);
        if (Customer.isSmallResolution(this)) {
            setContentView(R.layout.small_kesaiwei_1280x480_fatsetmain_layout);
        } else {
            setContentView(R.layout.kesaiwei_1280x480_fatsetmain_layout);
        }
        if (this.m_iModeSet == 39) {
            this.strs.add(getString(R.string.lbl_ui_style));
            this.strs.add(getString(R.string.lb_logo_settings));
        } else if (Customer.getCarFactoryIndex(this) == 1 || Customer.getCarFactoryIndex(this) == 0) {
            this.strs.add(getString(R.string.lb_function_settings));
            this.strs.add(getString(R.string.lb_vehicle_settings));
            this.strs.add(getString(R.string.lb_touch_settings));
            this.strs.add(getString(R.string.lb_carType_settings));
            this.strs.add(getString(R.string.lb_can_settings));
            this.strs.add(getString(R.string.lb_ui_settings));
            this.strs.add(getString(R.string.lb_upgrade_configuration));
            this.strs.add(getString(R.string.lb_logo_settings));
        } else {
            this.strs.add(getString(R.string.lb_function_settings));
            this.strs.add(getString(R.string.lb_vehicle_settings));
            this.strs.add(getString(R.string.lb_car_special_settings));
            this.strs.add(getString(R.string.lb_touch_settings));
            this.strs.add(getString(R.string.lb_carType_settings));
            this.strs.add(getString(R.string.lb_can_settings));
            this.strs.add(getString(R.string.lb_ui_settings));
            this.strs.add(getString(R.string.lb_upgrade_configuration));
            this.strs.add(getString(R.string.lb_logo_settings));
        }
        if (Customer.isSmallResolution(getApplicationContext())) {
            this.adapter = new MyRecyclerAdapter(this.strs, true, (int) getResources().getDimension(R.dimen.small_font_size_main));
        } else {
            this.adapter = new MyRecyclerAdapter(this.strs, true, (int) getResources().getDimension(R.dimen.font_size_main));
        }
        initListView();
        initFragments();
        initViewPager();
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        sendModeTitle(EventUtils.eSrcMode.SRC_FATSET.getIntValue());
    }

    private void initListView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.listview);
        this.mRecyclerView = recyclerView;
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            this.mRecyclerView.setAdapter(this.adapter);
            this.adapter.setOnItemClickListener(new MyRecyclerAdapter.OnItemClickListener() {
                public final void onItemClick(int i) {
                    FatSetMainActivity.this.lambda$initListView$0$FatSetMainActivity(i);
                }
            });
            this.mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                public void onScrollStateChanged(RecyclerView recyclerView, int i) {
                    super.onScrollStateChanged(recyclerView, i);
                    if (i == 0) {
                        FatSetMainActivity.this.adapter.notifyDataSetChanged();
                    }
                }
            });
        }
    }

    public /* synthetic */ void lambda$initListView$0$FatSetMainActivity(int i) {
        this.adapter.setPosition(i);
        this.mViewPager.setCurrentItem(i, false);
    }

    private void initFragments() {
        this.fragments = new ArrayList();
        if (this.m_iModeSet == 39) {
            FragmentUIStyle fragmentUIStyle = new FragmentUIStyle();
            FragmentLogo fragmentLogo = new FragmentLogo();
            this.fragments.add(fragmentUIStyle);
            this.fragments.add(fragmentLogo);
        } else if (Customer.getCarFactoryIndex(this) == 1 || Customer.getCarFactoryIndex(this) == 0) {
            FragmentFunction fragmentFunction = new FragmentFunction();
            FragmentVehicle fragmentVehicle = new FragmentVehicle();
            FragmentVehicleButton fragmentVehicleButton = new FragmentVehicleButton();
            FragmentCarType fragmentCarType = new FragmentCarType();
            FragmentCan fragmentCan = new FragmentCan();
            FragmentUISelect fragmentUISelect = new FragmentUISelect();
            FragmentUpgrade fragmentUpgrade = new FragmentUpgrade();
            FragmentLogo fragmentLogo2 = new FragmentLogo();
            this.fragments.add(fragmentFunction);
            this.fragments.add(fragmentVehicle);
            this.fragments.add(fragmentVehicleButton);
            this.fragments.add(fragmentCarType);
            this.fragments.add(fragmentCan);
            this.fragments.add(fragmentUISelect);
            this.fragments.add(fragmentUpgrade);
            this.fragments.add(fragmentLogo2);
        } else {
            FragmentFunction fragmentFunction2 = new FragmentFunction();
            FragmentVehicle fragmentVehicle2 = new FragmentVehicle();
            FragmentCarSpecial fragmentCarSpecial = new FragmentCarSpecial();
            FragmentVehicleButton fragmentVehicleButton2 = new FragmentVehicleButton();
            FragmentCarType fragmentCarType2 = new FragmentCarType();
            FragmentCan fragmentCan2 = new FragmentCan();
            FragmentUISelect fragmentUISelect2 = new FragmentUISelect();
            FragmentUpgrade fragmentUpgrade2 = new FragmentUpgrade();
            FragmentLogo fragmentLogo3 = new FragmentLogo();
            this.fragments.add(fragmentFunction2);
            this.fragments.add(fragmentVehicle2);
            this.fragments.add(fragmentCarSpecial);
            this.fragments.add(fragmentVehicleButton2);
            this.fragments.add(fragmentCarType2);
            this.fragments.add(fragmentCan2);
            this.fragments.add(fragmentUISelect2);
            this.fragments.add(fragmentUpgrade2);
            this.fragments.add(fragmentLogo3);
        }
    }

    private void initViewPager() {
        this.mViewPager = (ViewPager) findViewById(R.id.viewPager);
        MyFragmentPagerAdapter myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        this.pagerAdapter = myFragmentPagerAdapter;
        ViewPager viewPager = this.mViewPager;
        if (viewPager != null) {
            viewPager.setAdapter(myFragmentPagerAdapter);
            this.mViewPager.setCurrentItem(0, false);
        }
    }

    class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        public MyFragmentPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        public Fragment getItem(int i) {
            return (Fragment) FatSetMainActivity.this.fragments.get(i);
        }

        public int getCount() {
            return FatSetMainActivity.this.fragments.size();
        }
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
        sendModeTitle(EventUtils.eSrcMode.SRC_NULL.getIntValue());
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void sendModeTitle(int i) {
        Intent intent = new Intent("com.szchoiceway.action.mode_title_change");
        intent.putExtra("com.szchoiceway.action.mode_title_change_extra", i);
        intent.putExtra("com.szchoiceway.action.mode_title_change_screen_extra", true);
        sendBroadcastAsUser(intent, UserHandle.ALL);
    }
}
