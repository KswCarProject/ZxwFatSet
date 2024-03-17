package com.szchoiceway.fatset.fragment;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import com.szchoiceway.fatset.R;
import com.szchoiceway.fatset.util.HorizontalPageLayoutManager;
import com.szchoiceway.fatset.util.LogoAdapter;
import com.szchoiceway.fatset.util.LogoUtil;
import com.szchoiceway.fatset.util.PagingScrollHelper;
import com.szchoiceway.zxwlib.GyroScopeWithCompassView;
import com.szchoiceway.zxwlib.bean.Customer;
import java.io.IOException;
import java.util.ArrayList;

public class FragmentLogoPage2 extends FragmentBase implements PagingScrollHelper.onPageChangeListener, LogoAdapter.OnItemClickListener {
    private static final int EVT_UPGRADE_END = 1027;
    private static final int EVT_UPGRADE_FAIL = 1026;
    private static final int EVT_UPGRADE_NOT_USBFOLADER = 1028;
    private static final int EVT_UPGRADE_START = 1025;
    private static final String TAG = "FragmentLogoPage2";
    private LogoAdapter adapter;
    private ArrayList<Drawable> drawables;
    private int iColumns = 3;
    private int iRows = 2;
    private LogoUtil logoUtil;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message message) {
            super.handleMessage(message);
            switch (message.what) {
                case 1025:
                    FragmentLogoPage2.this.showTipString(R.string.lbl_update_logo, 1);
                    return;
                case FragmentLogoPage2.EVT_UPGRADE_FAIL /*1026*/:
                    FragmentLogoPage2.this.showTipString(R.string.lbl_update_logo_error, 5000);
                    return;
                case FragmentLogoPage2.EVT_UPGRADE_END /*1027*/:
                    FragmentLogoPage2.this.showTipString(R.string.lbl_update_logo_ok, 5000);
                    return;
                case FragmentLogoPage2.EVT_UPGRADE_NOT_USBFOLADER /*1028*/:
                    FragmentLogoPage2.this.showTipString(R.string.lb_SD_card_or_USB_no_logo_file, 5000);
                    return;
                default:
                    return;
            }
        }
    };
    private PagingScrollHelper mScrollHelper;
    private Toast mTip = null;
    private ArrayList<String> names;
    private int position = -1;
    private RecyclerView recyclerView;

    public void onPageChange(int i) {
    }

    public void onPageState(int i) {
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        LogoUtil logoUtil2 = new LogoUtil(getContext());
        this.logoUtil = logoUtil2;
        this.drawables = logoUtil2.getCustomerLogos();
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.zxwFatApp.setUpdatingLogo(false);
        return Customer.isSmallResolution(getContext()) ? layoutInflater.inflate(R.layout.small_fragment_logo_page2, (ViewGroup) null) : layoutInflater.inflate(R.layout.fragment_logo_page2, (ViewGroup) null);
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.recyclerView = (RecyclerView) view.findViewById(R.id.recycleView);
        LogoAdapter logoAdapter = new LogoAdapter(getContext());
        this.adapter = logoAdapter;
        logoAdapter.setDrawables(this.drawables);
        this.recyclerView.setAdapter(this.adapter);
        this.recyclerView.setLayoutManager(new HorizontalPageLayoutManager(this.iRows, this.iColumns));
        this.recyclerView.setItemAnimator((RecyclerView.ItemAnimator) null);
        PagingScrollHelper pagingScrollHelper = new PagingScrollHelper();
        this.mScrollHelper = pagingScrollHelper;
        pagingScrollHelper.setUpRecycleView(this.recyclerView);
        this.mScrollHelper.setOnPageChangeListener(this);
        this.recyclerView.setHorizontalScrollBarEnabled(true);
        this.adapter.setOnItemClickListener(this);
    }

    public void onResume() {
        super.onResume();
        ArrayList<Drawable> customerLogos = this.logoUtil.getCustomerLogos();
        this.drawables = customerLogos;
        this.adapter.setDrawables(customerLogos);
        this.adapter.notifyDataSetChanged();
    }

    public void onItemClick(int i) {
        if (!this.zxwFatApp.getUpdatingLogo()) {
            this.position = i;
            setLogo();
        }
    }

    private void setLogo() {
        this.zxwFatApp.setUpdatingLogo(true);
        new Thread(new Runnable() {
            public final void run() {
                FragmentLogoPage2.this.lambda$setLogo$0$FragmentLogoPage2();
            }
        }).start();
    }

    public /* synthetic */ void lambda$setLogo$0$FragmentLogoPage2() {
        this.mHandler.removeMessages(1025);
        this.mHandler.sendEmptyMessage(1025);
        try {
            this.logoUtil.saveLogoFile(((BitmapDrawable) this.drawables.get(this.position)).getBitmap());
            this.logoUtil.copyFile(((BitmapDrawable) this.drawables.get(this.position)).getBitmap(), "/mnt/privdata1/logo.bmp");
            this.mHandler.removeMessages(EVT_UPGRADE_END);
            this.mHandler.sendEmptyMessageDelayed(EVT_UPGRADE_END, 10);
        } catch (IOException e) {
            this.mHandler.removeMessages(EVT_UPGRADE_FAIL);
            this.mHandler.sendEmptyMessageDelayed(EVT_UPGRADE_FAIL, 10);
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public void showTipString(int i, int i2) {
        Toast toast = this.mTip;
        if (toast != null) {
            toast.cancel();
        }
        if (getActivity() != null) {
            Toast makeText = Toast.makeText(getActivity().getApplicationContext(), i, 0);
            this.mTip = makeText;
            TextView textView = (TextView) ((LinearLayout) makeText.getView()).getChildAt(0);
            if (Customer.isSmallResolution(getContext())) {
                textView.setTextSize(20.0f);
            } else {
                textView.setTextSize(26.0f);
            }
            this.mTip.setGravity(80, 0, GyroScopeWithCompassView.CARTYPE_yinglang_LO_NEW);
            this.mTip.setDuration(i2);
            this.mTip.show();
        }
        if (i == R.string.lbl_update_logo_error || i == R.string.lbl_update_logo_ok) {
            this.zxwFatApp.setUpdatingLogo(false);
        }
    }

    private void hideTipString() {
        Toast toast = this.mTip;
        if (toast != null) {
            toast.cancel();
            this.mTip = null;
        }
    }
}
