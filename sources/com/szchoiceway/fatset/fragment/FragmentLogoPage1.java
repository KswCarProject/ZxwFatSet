package com.szchoiceway.fatset.fragment;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.szchoiceway.fatset.R;
import com.szchoiceway.fatset.SysProviderOpt;
import com.szchoiceway.fatset.util.LogoUtil;
import com.szchoiceway.zxwlib.GyroScopeWithCompassView;
import com.szchoiceway.zxwlib.bean.Customer;
import java.io.IOException;
import java.util.ArrayList;

public class FragmentLogoPage1 extends FragmentBase implements View.OnClickListener {
    private static final int EVT_UPGRADE_END = 1027;
    private static final int EVT_UPGRADE_FAIL = 1026;
    private static final int EVT_UPGRADE_NOT_USBFOLADER = 1028;
    private static final int EVT_UPGRADE_START = 1025;
    private static final String TAG = "FragmentLogoPage1";
    private ArrayList<Bitmap> bitmaps;
    private String[] logoNameList;
    private LogoUtil logoUtil;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message message) {
            super.handleMessage(message);
            switch (message.what) {
                case 1025:
                    FragmentLogoPage1.this.showTipString(R.string.lbl_update_logo, 1);
                    return;
                case FragmentLogoPage1.EVT_UPGRADE_FAIL /*1026*/:
                    FragmentLogoPage1.this.showTipString(R.string.lbl_update_logo_error, 5000);
                    return;
                case FragmentLogoPage1.EVT_UPGRADE_END /*1027*/:
                    FragmentLogoPage1.this.showTipString(R.string.lbl_update_logo_ok, 5000);
                    return;
                case FragmentLogoPage1.EVT_UPGRADE_NOT_USBFOLADER /*1028*/:
                    FragmentLogoPage1.this.showTipString(R.string.lb_SD_card_or_USB_no_logo_file, 5000);
                    return;
                default:
                    return;
            }
        }
    };
    private Toast mTip = null;
    private int position = -1;

    public void onAttach(Context context) {
        super.onAttach(context);
        String recordValue = SysProviderOpt.getInstance(context).getRecordValue("KSW_UI_RESOLUTION", "1920x720");
        boolean recordBoolean = SysProviderOpt.getInstance(context).getRecordBoolean(SysProviderOpt.SYS_FACTORY_SET_SHOW_KSW_LOGO, false);
        Log.d(TAG, "resolution = " + recordValue + ", showKswLogo = " + recordBoolean);
        if (recordBoolean) {
            this.logoNameList = new String[]{"logo_1920x720_default.bmp", "logo_1920x720_default_en.bmp"};
            if ("1280x480".equalsIgnoreCase(recordValue)) {
                this.logoNameList = new String[]{"logo_1280x480_default.bmp", "logo_1280x480_default_en.bmp"};
            } else if ("1024x600".equalsIgnoreCase(recordValue)) {
                this.logoNameList = new String[]{"logo_1024x600_default.bmp", "logo_1024x600_default_en.bmp"};
            } else if ("1280x720".equalsIgnoreCase(recordValue)) {
                this.logoNameList = new String[]{"logo_1280x720_default.bmp", "logo_1280x720_default.bmp"};
            } else if ("1440x540".equalsIgnoreCase(recordValue)) {
                this.logoNameList = new String[]{"logo_1440x540_default.bmp", "logo_1440x540_default.bmp"};
            } else if ("1560x700".equalsIgnoreCase(recordValue)) {
                this.logoNameList = new String[]{"logo_1560x700_default.bmp", "logo_1560x700_default.bmp"};
            }
        } else {
            this.logoNameList = new String[]{"logo_1920x720_2.bmp", "logo_1920x720_1.bmp"};
            if ("1280x480".equalsIgnoreCase(recordValue)) {
                this.logoNameList = new String[]{"logo_1280x480_2.bmp", "logo_1280x480_1.bmp"};
            } else if ("1024x600".equalsIgnoreCase(recordValue)) {
                this.logoNameList = new String[]{"logo_1024x600_2.bmp", "logo_1024x600_1.bmp"};
            } else if ("1280x720".equalsIgnoreCase(recordValue)) {
                this.logoNameList = new String[]{"logo_1280x720_2.bmp", "logo_1280x720_1.bmp"};
            } else if ("1440x540".equalsIgnoreCase(recordValue)) {
                this.logoNameList = new String[]{"logo_1440x540_2.bmp", "logo_1440x540_1.bmp"};
            } else if ("1560x700".equalsIgnoreCase(recordValue)) {
                this.logoNameList = new String[]{"logo_1560x700_2.bmp", "logo_1560x700_1.bmp"};
            }
        }
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.zxwFatApp.setUpdatingLogo(false);
        View inflate = layoutInflater.inflate(R.layout.fragment_logo_page1, (ViewGroup) null);
        if (Customer.isSmallResolution(getActivity())) {
            inflate = layoutInflater.inflate(R.layout.small_fragment_logo_page1, (ViewGroup) null);
        }
        this.logoUtil = new LogoUtil(getContext());
        return inflate;
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        AssetManager assets = getResources().getAssets();
        this.bitmaps = new ArrayList<>();
        ArrayList arrayList = new ArrayList();
        ImageView imageView = (ImageView) view.findViewById(R.id.defaultLogo1);
        if (imageView != null) {
            arrayList.add(imageView);
        }
        ImageView imageView2 = (ImageView) view.findViewById(R.id.defaultLogo2);
        if (imageView2 != null) {
            arrayList.add(imageView2);
        }
        for (int i = 0; i < this.logoNameList.length; i++) {
            try {
                Log.d(TAG, "name = " + this.logoNameList[i]);
                Bitmap decodeStream = BitmapFactory.decodeStream(assets.open(this.logoNameList[i]));
                this.bitmaps.add(decodeStream);
                BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), decodeStream);
                ImageView imageView3 = (ImageView) arrayList.get(i);
                if (imageView3 != null) {
                    imageView3.setImageDrawable(bitmapDrawable);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        View findViewById = view.findViewById(R.id.defaultLogoView1);
        if (findViewById != null) {
            findViewById.setOnClickListener(this);
        }
        View findViewById2 = view.findViewById(R.id.defaultLogoView2);
        if (findViewById2 != null) {
            findViewById2.setOnClickListener(this);
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.defaultLogoView1 /*2131230977*/:
                this.position = 0;
                break;
            case R.id.defaultLogoView2 /*2131230978*/:
                this.position = 1;
                break;
            case R.id.defaultLogoView3 /*2131230979*/:
                this.position = 2;
                break;
        }
        if (!this.zxwFatApp.getUpdatingLogo()) {
            setLogo();
        }
    }

    private void setLogo() {
        this.zxwFatApp.setUpdatingLogo(true);
        new Thread(new Runnable() {
            public final void run() {
                FragmentLogoPage1.this.lambda$setLogo$0$FragmentLogoPage1();
            }
        }).start();
    }

    public /* synthetic */ void lambda$setLogo$0$FragmentLogoPage1() {
        this.mHandler.removeMessages(1025);
        this.mHandler.sendEmptyMessage(1025);
        try {
            this.logoUtil.saveLogoFile(this.bitmaps.get(this.position));
            this.logoUtil.copyFile(this.bitmaps.get(this.position), "/mnt/privdata1/logo.bmp");
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
            ((TextView) ((LinearLayout) makeText.getView()).getChildAt(0)).setTextSize(26.0f);
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
