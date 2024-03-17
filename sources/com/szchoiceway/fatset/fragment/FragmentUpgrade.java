package com.szchoiceway.fatset.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.szchoiceway.eventcenter.EventUtils;
import com.szchoiceway.fatset.R;
import com.szchoiceway.fatset.SysProviderOpt;
import com.szchoiceway.zxwlib.bean.Customer;

public class FragmentUpgrade extends FragmentBase implements View.OnClickListener {
    private static final int EVT_UPGRADE_END = 1027;
    private static final int EVT_UPGRADE_FACTORY_FILE_ERROR = 1031;
    private static final int EVT_UPGRADE_FAIL = 1026;
    private static final int EVT_UPGRADE_FAIL_VERSION = 1030;
    private static final int EVT_UPGRADE_LCD_FILE_ERROR = 1032;
    private static final int EVT_UPGRADE_NOT_USBFOLADER = 1028;
    private static final int EVT_UPGRADE_START = 1029;
    private static final String TAG = "FragmentUpgrade";
    ImageButton btnHideLytMsg;
    Button btnSave;
    Button btnUpgrade;
    RelativeLayout mLytMsgYesNo = null;
    /* access modifiers changed from: private */
    public Handler mhandler = new Handler() {
        public void handleMessage(Message message) {
            switch (message.what) {
                case FragmentUpgrade.EVT_UPGRADE_FAIL /*1026*/:
                    FragmentUpgrade fragmentUpgrade = FragmentUpgrade.this;
                    fragmentUpgrade.is_mLytMsgYesNo_show_hide(true, fragmentUpgrade.getString(R.string.lbl_update_factory_error));
                    return;
                case FragmentUpgrade.EVT_UPGRADE_END /*1027*/:
                    FragmentUpgrade fragmentUpgrade2 = FragmentUpgrade.this;
                    fragmentUpgrade2.is_mLytMsgYesNo_show_hide(true, fragmentUpgrade2.getString(R.string.lbl_update_factory_ok_resart));
                    return;
                case FragmentUpgrade.EVT_UPGRADE_NOT_USBFOLADER /*1028*/:
                    Log.d(FragmentUpgrade.TAG, "not file exist");
                    FragmentUpgrade fragmentUpgrade3 = FragmentUpgrade.this;
                    fragmentUpgrade3.is_mLytMsgYesNo_show_hide(true, fragmentUpgrade3.getString(R.string.lb_SD_card_or_USB_no_factory_file));
                    return;
                case FragmentUpgrade.EVT_UPGRADE_START /*1029*/:
                    FragmentUpgrade fragmentUpgrade4 = FragmentUpgrade.this;
                    fragmentUpgrade4.is_mLytMsgYesNo_show_hide(true, fragmentUpgrade4.getString(R.string.lb_updating_factory));
                    return;
                case FragmentUpgrade.EVT_UPGRADE_FAIL_VERSION /*1030*/:
                    FragmentUpgrade fragmentUpgrade5 = FragmentUpgrade.this;
                    fragmentUpgrade5.is_mLytMsgYesNo_show_hide(true, fragmentUpgrade5.getString(R.string.lb_update_configuration_failed_version));
                    return;
                case FragmentUpgrade.EVT_UPGRADE_FACTORY_FILE_ERROR /*1031*/:
                    FragmentUpgrade fragmentUpgrade6 = FragmentUpgrade.this;
                    fragmentUpgrade6.is_mLytMsgYesNo_show_hide(true, fragmentUpgrade6.getString(R.string.lb_updating_factory_file_error));
                    return;
                case FragmentUpgrade.EVT_UPGRADE_LCD_FILE_ERROR /*1032*/:
                    FragmentUpgrade fragmentUpgrade7 = FragmentUpgrade.this;
                    fragmentUpgrade7.is_mLytMsgYesNo_show_hide(true, fragmentUpgrade7.getString(R.string.lb_updating_lcd_file_error));
                    return;
                default:
                    return;
            }
        }
    };
    private MyBroadcast myBroadcast;
    TextView tvFactoryVer = null;
    TextView tvShowWarning = null;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_upgrade, (ViewGroup) null);
        if (Customer.isSmallResolution(getContext())) {
            inflate = layoutInflater.inflate(R.layout.small_fragment_upgrade, (ViewGroup) null);
        }
        registerMyBroadcast();
        return inflate;
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        Button button = (Button) view.findViewById(R.id.btnUpgrade);
        this.btnUpgrade = button;
        if (button != null) {
            button.setOnClickListener(this);
        }
        Button button2 = (Button) view.findViewById(R.id.btnSave);
        this.btnSave = button2;
        if (button2 != null) {
            button2.setOnClickListener(this);
        }
        ImageButton imageButton = (ImageButton) view.findViewById(R.id.btnHideLytMsg);
        this.btnHideLytMsg = imageButton;
        if (imageButton != null) {
            imageButton.setOnClickListener(this);
        }
        this.mLytMsgYesNo = (RelativeLayout) view.findViewById(R.id.LytMsgYesNo);
        this.tvShowWarning = (TextView) view.findViewById(R.id.tvShowWarning);
        this.tvFactoryVer = (TextView) view.findViewById(R.id.tvFactoryVer);
        String recordValue = this.mProvider.getRecordValue(SysProviderOpt.KSW_FACTORY_SET_CLIENT);
        String recordValue2 = this.mProvider.getRecordValue(SysProviderOpt.KSW_FACTORY_VER);
        Log.d(TAG, "xml_client = " + recordValue + ", mFactoryVer + " + recordValue2);
        TextView textView = this.tvFactoryVer;
        if (textView != null) {
            textView.setText("ver:" + recordValue + "-" + recordValue2);
        }
    }

    public void onDestroyView() {
        super.onDestroyView();
        unregisterMyBroadcast();
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btnHideLytMsg) {
            is_mLytMsgYesNo_show_hide(false, "");
        } else if (id == R.id.btnSave) {
            this.zxwFatApp.sendBroadcast(new Intent("com.szchoiceway.action.reboot"));
        } else if (id == R.id.btnUpgrade) {
            this.mhandler.sendEmptyMessage(EVT_UPGRADE_START);
            this.zxwFatApp.sendBroadcast(new Intent(EventUtils.ZXW_ACTION_UPDATE_CONFIGURATION));
        }
    }

    /* access modifiers changed from: private */
    public void is_mLytMsgYesNo_show_hide(boolean z, String str) {
        if (z) {
            RelativeLayout relativeLayout = this.mLytMsgYesNo;
            if (relativeLayout != null) {
                relativeLayout.setVisibility(0);
            }
            TextView textView = this.tvShowWarning;
            if (textView != null) {
                textView.setText(str);
                return;
            }
            return;
        }
        RelativeLayout relativeLayout2 = this.mLytMsgYesNo;
        if (relativeLayout2 != null) {
            relativeLayout2.setVisibility(8);
        }
        TextView textView2 = this.tvShowWarning;
        if (textView2 != null) {
            textView2.setText("");
        }
    }

    private void registerMyBroadcast() {
        if (this.myBroadcast == null) {
            this.myBroadcast = new MyBroadcast();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(EventUtils.ZXW_ACTION_UPDATE_CONFIGURATION_FILE_NOT_EXIT);
            intentFilter.addAction(EventUtils.ZXW_ACTION_UPDATE_CONFIGURATION_FILE_ERROR);
            intentFilter.addAction(EventUtils.ZXW_ACTION_UPDATE_CONFIGURATION_SUCCED);
            intentFilter.addAction(EventUtils.ZXW_ACTION_UPDATE_CONFIGURATION_FAILED);
            intentFilter.addAction(EventUtils.ZXW_ACTION_UPDATE_CONFIGURATION_FAILED_VERSION);
            if (getActivity() != null) {
                getActivity().registerReceiver(this.myBroadcast, intentFilter);
            }
        }
    }

    private void unregisterMyBroadcast() {
        if (this.myBroadcast != null && getActivity() != null) {
            getActivity().unregisterReceiver(this.myBroadcast);
            this.myBroadcast = null;
        }
    }

    class MyBroadcast extends BroadcastReceiver {
        MyBroadcast() {
        }

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d(FragmentUpgrade.TAG, "onReceive = " + action);
            if (EventUtils.ZXW_ACTION_UPDATE_CONFIGURATION_FILE_NOT_EXIT.equals(action)) {
                FragmentUpgrade.this.mhandler.sendEmptyMessage(FragmentUpgrade.EVT_UPGRADE_NOT_USBFOLADER);
            } else if (EventUtils.ZXW_ACTION_UPDATE_CONFIGURATION_SUCCED.equals(action)) {
                FragmentUpgrade.this.mhandler.sendEmptyMessage(FragmentUpgrade.EVT_UPGRADE_END);
            } else if (EventUtils.ZXW_ACTION_UPDATE_CONFIGURATION_FAILED.equals(action)) {
                FragmentUpgrade.this.mhandler.sendEmptyMessage(FragmentUpgrade.EVT_UPGRADE_FAIL);
            } else if (EventUtils.ZXW_ACTION_UPDATE_CONFIGURATION_FAILED_VERSION.equals(action)) {
                FragmentUpgrade.this.mhandler.sendEmptyMessage(FragmentUpgrade.EVT_UPGRADE_FAIL_VERSION);
            } else if (EventUtils.ZXW_ACTION_UPDATE_CONFIGURATION_FILE_ERROR.equals(action)) {
                FragmentUpgrade.this.mhandler.sendEmptyMessage(FragmentUpgrade.EVT_UPGRADE_FACTORY_FILE_ERROR);
            } else if (EventUtils.ZXW_ACTION_UPDATE_CONFIGURATION_LCD_FILE_ERROR.equals(action)) {
                FragmentUpgrade.this.mhandler.sendEmptyMessage(FragmentUpgrade.EVT_UPGRADE_LCD_FILE_ERROR);
            }
        }
    }
}
