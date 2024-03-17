package com.szchoiceway.fatset.fragment;

import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemProperties;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.szchoiceway.eventcenter.EventUtils;
import com.szchoiceway.fatset.R;
import com.szchoiceway.fatset.SysProviderOpt;
import com.szchoiceway.fatset.util.LogoUtil;
import com.szchoiceway.zxwlib.GyroScopeWithCompassView;
import com.szchoiceway.zxwlib.bean.Customer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FragmentLogoPage3 extends FragmentBase implements View.OnClickListener {
    private static final int EVT_DELETE_END = 1041;
    private static final int EVT_DELETE_FAIL = 1040;
    private static final int EVT_UPGRADE_ANIMATION_END = 1031;
    private static final int EVT_UPGRADE_ANIMATION_FAIL = 1030;
    private static final int EVT_UPGRADE_ANIMATION_NOT_USBFOLADER = 1032;
    private static final int EVT_UPGRADE_ANIMATION_START = 1029;
    private static final int EVT_UPGRADE_END = 1027;
    private static final int EVT_UPGRADE_FAIL = 1026;
    private static final int EVT_UPGRADE_FILE_OUTPACE = 1033;
    private static final int EVT_UPGRADE_NOT_USBFOLADER = 1028;
    private static final int EVT_UPGRADE_START = 1025;
    private static final String KSW_BACK_PATH = "/mnt/privdata1/";
    private static final String TAG = "FragmentLogoPage3";
    private static String animationFileName = "bootanimation.zip";
    private static String defaultFileName = "logo_1920x720_default.bmp";
    private static String fileName = "bootlogo.zip";
    private Button btnDeleteLogo;
    private Button btnImportAnimation;
    private Button btnImportLogo;
    private Button btnRestoreAnimation;
    private Button btnRestoreLogo;
    private File file;
    private boolean isUpdating = false;
    private LogoUtil logoUtil;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message message) {
            super.handleMessage(message);
            int i = message.what;
            if (i == FragmentLogoPage3.EVT_DELETE_FAIL) {
                FragmentLogoPage3.this.showTipString(R.string.lbl_delete_logo_failed, 5000);
            } else if (i != FragmentLogoPage3.EVT_DELETE_END) {
                switch (i) {
                    case 1025:
                        FragmentLogoPage3.this.showTipString(R.string.lbl_update_logo, 1);
                        return;
                    case FragmentLogoPage3.EVT_UPGRADE_FAIL /*1026*/:
                        FragmentLogoPage3.this.showTipString(R.string.lbl_update_logo_error, 5000);
                        return;
                    case FragmentLogoPage3.EVT_UPGRADE_END /*1027*/:
                        FragmentLogoPage3.this.showTipString(R.string.lbl_update_logo_ok, 5000);
                        return;
                    case FragmentLogoPage3.EVT_UPGRADE_NOT_USBFOLADER /*1028*/:
                        FragmentLogoPage3.this.showTipString(R.string.lb_SD_card_or_USB_no_logo_file, 5000);
                        return;
                    case FragmentLogoPage3.EVT_UPGRADE_ANIMATION_START /*1029*/:
                        FragmentLogoPage3.this.showTipString(R.string.lbl_update_animation, 1);
                        return;
                    case FragmentLogoPage3.EVT_UPGRADE_ANIMATION_FAIL /*1030*/:
                        FragmentLogoPage3.this.showTipString(R.string.lbl_update_animation_error, 5000);
                        return;
                    case FragmentLogoPage3.EVT_UPGRADE_ANIMATION_END /*1031*/:
                        FragmentLogoPage3.this.showTipString(R.string.lbl_update_animation_ok, 5000);
                        return;
                    case FragmentLogoPage3.EVT_UPGRADE_ANIMATION_NOT_USBFOLADER /*1032*/:
                        FragmentLogoPage3.this.showTipString(R.string.lb_SD_card_or_USB_no_animation_file, 5000);
                        return;
                    case FragmentLogoPage3.EVT_UPGRADE_FILE_OUTPACE /*1033*/:
                        FragmentLogoPage3.this.showTipString(R.string.lbl_update_file_overpace, 5000);
                        return;
                    default:
                        return;
                }
            } else {
                FragmentLogoPage3.this.showTipString(R.string.lbl_delete_logo_succeed, 5000);
            }
        }
    };
    protected Toast mTip = null;
    private ImageView previewImg;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        if (Customer.isSmallResolution(getActivity())) {
            return layoutInflater.inflate(R.layout.small_fragment_logo_page3, (ViewGroup) null);
        }
        return layoutInflater.inflate(R.layout.fragment_logo_page3, (ViewGroup) null);
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.logoUtil = new LogoUtil(getContext());
        Button button = (Button) view.findViewById(R.id.btnImportLogo);
        this.btnImportLogo = button;
        if (button != null) {
            button.setOnClickListener(this);
        }
        Button button2 = (Button) view.findViewById(R.id.btnDeleteLogo);
        this.btnDeleteLogo = button2;
        if (button2 != null) {
            button2.setOnClickListener(this);
        }
        Button button3 = (Button) view.findViewById(R.id.btnRestoreLogo);
        this.btnRestoreLogo = button3;
        if (button3 != null) {
            button3.setOnClickListener(this);
        }
        Button button4 = (Button) view.findViewById(R.id.btnImportAnimation);
        this.btnImportAnimation = button4;
        if (button4 != null) {
            button4.setOnClickListener(this);
        }
        Button button5 = (Button) view.findViewById(R.id.btnRestoreAnimation);
        this.btnRestoreAnimation = button5;
        if (button5 != null) {
            button5.setOnClickListener(this);
        }
    }

    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0033 A[Catch:{ Exception -> 0x0088 }] */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0047 A[Catch:{ Exception -> 0x0088 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean copyFile(java.lang.String r7, java.lang.String r8) {
        /*
            r6 = this;
            java.lang.String r0 = "FragmentLogoPage3"
            java.lang.String r1 = "/mnt/privdata1/"
            boolean r2 = r7.contains(r1)
            if (r2 != 0) goto L_0x0010
            boolean r1 = r8.contains(r1)
            if (r1 == 0) goto L_0x001c
        L_0x0010:
            java.lang.String r1 = "chmod 777 /mnt/privdata1/"
            r6.runCmd(r1)
        L_0x0015:
            boolean r1 = r6.getCmdResult()
            if (r1 != 0) goto L_0x001c
            goto L_0x0015
        L_0x001c:
            r1 = 0
            r2 = 1
            java.io.File r3 = new java.io.File     // Catch:{ Exception -> 0x0088 }
            r3.<init>(r7)     // Catch:{ Exception -> 0x0088 }
            java.io.File r4 = new java.io.File     // Catch:{ Exception -> 0x0088 }
            r4.<init>(r8)     // Catch:{ Exception -> 0x0088 }
            java.lang.String r5 = "--->>> olderPath = 000"
            android.util.Log.i(r0, r5)     // Catch:{ Exception -> 0x0088 }
            boolean r5 = r4.exists()     // Catch:{ Exception -> 0x0088 }
            if (r5 == 0) goto L_0x003b
            java.lang.String r5 = "--->>> olderPath = 111"
            android.util.Log.i(r0, r5)     // Catch:{ Exception -> 0x0088 }
            r4.delete()     // Catch:{ Exception -> 0x0088 }
        L_0x003b:
            r4.createNewFile()     // Catch:{ Exception -> 0x0088 }
            r4.setReadable(r2)     // Catch:{ Exception -> 0x0088 }
            boolean r3 = r3.exists()     // Catch:{ Exception -> 0x0088 }
            if (r3 == 0) goto L_0x007d
            java.lang.String r3 = "--->>> olderPath = 222"
            android.util.Log.i(r0, r3)     // Catch:{ Exception -> 0x0088 }
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch:{ Exception -> 0x0088 }
            r3.<init>(r7)     // Catch:{ Exception -> 0x0088 }
            java.lang.String r7 = "--->>> olderPath = 333"
            android.util.Log.i(r0, r7)     // Catch:{ Exception -> 0x0088 }
            java.io.FileOutputStream r7 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x0088 }
            r7.<init>(r8)     // Catch:{ Exception -> 0x0088 }
            java.lang.String r8 = "--->>> olderPath = 444"
            android.util.Log.i(r0, r8)     // Catch:{ Exception -> 0x0088 }
            r8 = 1024(0x400, float:1.435E-42)
            byte[] r8 = new byte[r8]     // Catch:{ Exception -> 0x0088 }
        L_0x0064:
            int r4 = r3.read(r8)     // Catch:{ Exception -> 0x0088 }
            r5 = -1
            if (r4 == r5) goto L_0x006f
            r7.write(r8, r1, r4)     // Catch:{ Exception -> 0x0088 }
            goto L_0x0064
        L_0x006f:
            java.lang.String r8 = "--->>> olderPath = 555"
            android.util.Log.i(r0, r8)     // Catch:{ Exception -> 0x0088 }
            r7.flush()     // Catch:{ Exception -> 0x0088 }
            r7.close()     // Catch:{ Exception -> 0x0088 }
            r3.close()     // Catch:{ Exception -> 0x0088 }
        L_0x007d:
            java.lang.Runtime r7 = java.lang.Runtime.getRuntime()     // Catch:{ Exception -> 0x0088 }
            java.lang.String r8 = "sync"
            r7.exec(r8)     // Catch:{ Exception -> 0x0088 }
            r1 = r2
            goto L_0x008c
        L_0x0088:
            r7 = move-exception
            r7.printStackTrace()
        L_0x008c:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.szchoiceway.fatset.fragment.FragmentLogoPage3.copyFile(java.lang.String, java.lang.String):boolean");
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
    }

    private void hideTipString() {
        Toast toast = this.mTip;
        if (toast != null) {
            toast.cancel();
            this.mTip = null;
        }
    }

    public void runLogoImportThread() {
        this.isUpdating = true;
        this.zxwFatApp.setUpdatingLogo(true);
        this.mHandler.removeMessages(1025);
        this.mHandler.sendEmptyMessage(1025);
        new Thread(new Runnable() {
            public final void run() {
                FragmentLogoPage3.this.lambda$runLogoImportThread$0$FragmentLogoPage3();
            }
        }).start();
    }

    public /* synthetic */ void lambda$runLogoImportThread$0$FragmentLogoPage3() {
        if (getActivity() != null) {
            String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
            for (String str : EventUtils.getAllPath(getActivity())) {
                if (!str.equals(absolutePath)) {
                    File file2 = new File(str + "/OEM/" + fileName);
                    this.file = file2;
                    if (file2.exists() && this.file.isFile()) {
                        break;
                    }
                }
            }
        }
        File file3 = this.file;
        if (file3 == null || !file3.exists() || !this.file.isFile()) {
            Log.i(TAG, "--->>> not copy file exists.");
            Handler handler = this.mHandler;
            if (handler != null) {
                handler.removeMessages(EVT_UPGRADE_NOT_USBFOLADER);
                this.mHandler.sendEmptyMessageDelayed(EVT_UPGRADE_NOT_USBFOLADER, 10);
            }
            this.isUpdating = false;
            this.zxwFatApp.setUpdatingLogo(false);
            return;
        }
        Log.d(TAG, "runLogoUpgradeThread path = " + this.file.getAbsolutePath());
        if (this.file.length() > 104857600) {
            this.mHandler.removeMessages(EVT_UPGRADE_FILE_OUTPACE);
            this.mHandler.sendEmptyMessageDelayed(EVT_UPGRADE_FILE_OUTPACE, 10);
            this.isUpdating = false;
            this.zxwFatApp.setUpdatingLogo(false);
            return;
        }
        try {
            unzipFile(this.file.getPath(), "/mnt/privdata1/bootlogo");
            Runtime.getRuntime().exec("sync");
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.mHandler.removeMessages(EVT_UPGRADE_END);
        this.mHandler.sendEmptyMessageDelayed(EVT_UPGRADE_END, 10);
        this.isUpdating = false;
        this.zxwFatApp.setUpdatingLogo(false);
    }

    private void runLogoDeleteThread() {
        new Thread(new Runnable() {
            public final void run() {
                FragmentLogoPage3.this.lambda$runLogoDeleteThread$1$FragmentLogoPage3();
            }
        }).start();
    }

    public /* synthetic */ void lambda$runLogoDeleteThread$1$FragmentLogoPage3() {
        File file2 = new File("/mnt/privdata1/bootlogo");
        if (!file2.exists() || !file2.isDirectory()) {
            this.mHandler.removeMessages(EVT_DELETE_END);
            this.mHandler.sendEmptyMessageDelayed(EVT_DELETE_END, 10);
            return;
        }
        boolean z = true;
        this.isUpdating = true;
        deleteFolder(file2);
        File file3 = new File("/mnt/privdata1/bootlogo");
        if (file3.exists() && file3.isDirectory()) {
            z = false;
        }
        if (z) {
            this.mHandler.removeMessages(EVT_DELETE_END);
            this.mHandler.sendEmptyMessageDelayed(EVT_DELETE_END, 10);
        } else {
            this.mHandler.removeMessages(EVT_DELETE_FAIL);
            this.mHandler.sendEmptyMessageDelayed(EVT_DELETE_FAIL, 10);
        }
        this.isUpdating = false;
        this.zxwFatApp.setUpdatingLogo(false);
    }

    private void runLogoRestoreThread() {
        String recordValue = SysProviderOpt.getInstance(getContext()).getRecordValue("KSW_UI_RESOLUTION", "1920x720");
        if ("1280x480".equalsIgnoreCase(recordValue)) {
            defaultFileName = "logo_1280x480_default.bmp";
        } else if ("1024x600".equalsIgnoreCase(recordValue)) {
            defaultFileName = "logo_1024x600_default.bmp";
        } else if ("1280x720".equalsIgnoreCase(recordValue)) {
            defaultFileName = "logo_1280x720_default.bmp";
        } else if ("1440x540".equalsIgnoreCase(recordValue)) {
            defaultFileName = "logo_1440x540_default.bmp";
        } else if ("1560x700".equalsIgnoreCase(recordValue)) {
            defaultFileName = "logo_1560x700_default.bmp";
        }
        this.isUpdating = true;
        this.zxwFatApp.setUpdatingLogo(true);
        this.mHandler.removeMessages(1025);
        this.mHandler.sendEmptyMessage(1025);
        new Thread(new Runnable() {
            public final void run() {
                FragmentLogoPage3.this.lambda$runLogoRestoreThread$2$FragmentLogoPage3();
            }
        }).start();
    }

    public /* synthetic */ void lambda$runLogoRestoreThread$2$FragmentLogoPage3() {
        AssetManager assets = getResources().getAssets();
        if (assets != null) {
            try {
                InputStream open = assets.open(defaultFileName);
                this.logoUtil.saveLogoFile(BitmapFactory.decodeStream(open));
                open.close();
                this.mHandler.removeMessages(EVT_UPGRADE_END);
                this.mHandler.sendEmptyMessageDelayed(EVT_UPGRADE_END, 10);
            } catch (IOException e) {
                e.printStackTrace();
                this.mHandler.removeMessages(EVT_UPGRADE_FAIL);
                this.mHandler.sendEmptyMessageDelayed(EVT_UPGRADE_FAIL, 10);
                e.printStackTrace();
            }
        }
        this.isUpdating = false;
        this.zxwFatApp.setUpdatingLogo(false);
    }

    public void runAnimationImportThread() {
        this.isUpdating = true;
        this.zxwFatApp.setUpdatingLogo(true);
        this.mHandler.removeMessages(EVT_UPGRADE_ANIMATION_START);
        this.mHandler.sendEmptyMessage(EVT_UPGRADE_ANIMATION_START);
        new Thread(new Runnable() {
            public final void run() {
                FragmentLogoPage3.this.lambda$runAnimationImportThread$3$FragmentLogoPage3();
            }
        }).start();
    }

    public /* synthetic */ void lambda$runAnimationImportThread$3$FragmentLogoPage3() {
        if (getActivity() != null) {
            String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
            for (String str : EventUtils.getAllPath(getActivity())) {
                if (!str.equals(absolutePath)) {
                    File file2 = new File(str + "/OEM/" + animationFileName);
                    this.file = file2;
                    if (file2.exists() && this.file.isFile()) {
                        break;
                    }
                }
            }
        }
        File file3 = this.file;
        if (file3 == null || !file3.exists() || !this.file.isFile()) {
            Log.i(TAG, "--->>> not copy file exists.");
            Handler handler = this.mHandler;
            if (handler != null) {
                handler.removeMessages(EVT_UPGRADE_NOT_USBFOLADER);
                this.mHandler.sendEmptyMessageDelayed(EVT_UPGRADE_NOT_USBFOLADER, 10);
            }
            this.isUpdating = false;
            this.zxwFatApp.setUpdatingLogo(false);
            return;
        }
        Log.d(TAG, "runAnimationUpgradeThread path = " + this.file.getAbsolutePath());
        File file4 = new File("/mnt/privdata1/bootanimation_tmp.zip");
        if (file4.exists()) {
            Log.d(TAG, "runAnimationUpgradeThread 222");
            file4.delete();
        }
        Log.d(TAG, "runAnimationUpgradeThread 333");
        boolean copyFile = copyFile(this.file.getPath(), "/mnt/privdata1/" + animationFileName);
        Log.d(TAG, "copy animation " + copyFile + ", file exist = " + new File("/mnt/privdata1/" + animationFileName).exists());
        if (copyFile) {
            runCmd("chmod 777 /mnt/privdata1/" + animationFileName);
            do {
            } while (!getCmdResult());
            Log.d(TAG, "chmod 777");
            this.mHandler.removeMessages(EVT_UPGRADE_ANIMATION_END);
            this.mHandler.sendEmptyMessageDelayed(EVT_UPGRADE_ANIMATION_END, 10);
        } else {
            this.mHandler.removeMessages(EVT_UPGRADE_ANIMATION_FAIL);
            this.mHandler.sendEmptyMessageDelayed(EVT_UPGRADE_ANIMATION_FAIL, 10);
        }
        this.isUpdating = false;
        this.zxwFatApp.setUpdatingLogo(false);
    }

    private void runAnimationRestoreThread() {
        this.isUpdating = true;
        this.zxwFatApp.setUpdatingLogo(true);
        this.mHandler.removeMessages(EVT_UPGRADE_ANIMATION_START);
        this.mHandler.sendEmptyMessage(EVT_UPGRADE_ANIMATION_START);
        runCmd("chmod 777 /mnt/privdata1/");
        do {
        } while (!getCmdResult());
        File file2 = new File("/mnt/privdata1/" + animationFileName);
        File file3 = new File("/mnt/privdata1/bootanimation_tmp.zip");
        if (file2.exists()) {
            boolean renameTo = file2.renameTo(file3);
            try {
                Runtime.getRuntime().exec("sync");
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (renameTo) {
                this.mHandler.removeMessages(EVT_UPGRADE_ANIMATION_END);
                this.mHandler.sendEmptyMessageDelayed(EVT_UPGRADE_ANIMATION_END, 10);
            } else {
                this.mHandler.removeMessages(EVT_UPGRADE_ANIMATION_FAIL);
                this.mHandler.sendEmptyMessageDelayed(EVT_UPGRADE_ANIMATION_FAIL, 10);
            }
        } else {
            this.mHandler.removeMessages(EVT_UPGRADE_ANIMATION_END);
            this.mHandler.sendEmptyMessageDelayed(EVT_UPGRADE_ANIMATION_END, 10);
        }
        this.isUpdating = false;
        this.zxwFatApp.setUpdatingLogo(false);
    }

    public void onClick(View view) {
        if (!this.isUpdating) {
            switch (view.getId()) {
                case R.id.btnDeleteLogo:
                    runLogoDeleteThread();
                    return;
                case R.id.btnImportAnimation:
                    runAnimationImportThread();
                    return;
                case R.id.btnImportLogo:
                    runLogoImportThread();
                    return;
                case R.id.btnRestoreAnimation:
                    runAnimationRestoreThread();
                    return;
                case R.id.btnRestoreLogo:
                    runLogoRestoreThread();
                    return;
                default:
                    return;
            }
        }
    }

    private void runCmd(String str) {
        SystemProperties.set("sys.apk_path", str);
        SystemProperties.set("ctl.start", "install_apk");
    }

    private boolean getCmdResult() {
        return SystemProperties.get("sys.apk_path").equals("true");
    }

    public void unzipFile(String str, String str2) throws IOException {
        runCmd("chmod 777 /mnt/privdata1/");
        do {
        } while (!getCmdResult());
        Log.i(TAG, "开始解压的文件： " + str + "\n解压的目标路径：" + str2);
        File file2 = new File(str2);
        if (!file2.exists() || !file2.isDirectory()) {
            file2.mkdirs();
            file2.setReadable(true);
        } else {
            deleteFolder(file2);
            File file3 = new File("/mnt/privdata1/bootlogo");
            file3.setReadable(true);
            Log.d(TAG, "unzipFile bootlogo.exists = " + file3.exists() + ", bootlogo.isDirectory = " + file3.isDirectory());
            if (file3.exists() && file3.isDirectory()) {
                this.mHandler.removeMessages(EVT_UPGRADE_FAIL);
                this.mHandler.sendEmptyMessageDelayed(EVT_UPGRADE_FAIL, 10);
                this.isUpdating = false;
                this.zxwFatApp.setUpdatingLogo(false);
                return;
            }
        }
        ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(str));
        ZipEntry nextEntry = zipInputStream.getNextEntry();
        byte[] bArr = new byte[1048576];
        while (nextEntry != null) {
            Log.i(TAG, "解压文件 1： " + nextEntry);
            if (!nextEntry.isDirectory()) {
                String name = nextEntry.getName();
                Log.i(TAG, "解压文件 原来 文件的位置： " + name);
                Log.d(TAG, "解压文件 现在 文件的位置： " + str2 + File.separator + name);
                File file4 = new File(str2 + File.separator + name);
                file4.createNewFile();
                file4.setReadable(true);
                FileOutputStream fileOutputStream = new FileOutputStream(file4);
                while (true) {
                    int read = zipInputStream.read(bArr);
                    if (read <= 0) {
                        break;
                    }
                    fileOutputStream.write(bArr, 0, read);
                }
                fileOutputStream.close();
            } else {
                String str3 = str2 + File.separator + nextEntry.getName();
                File file5 = new File(str3);
                if (!file5.exists()) {
                    Log.d(TAG, "mk = " + file5.mkdirs());
                    "chmod 777 " + str3;
                }
                Log.d(TAG, "folderName = " + str3);
            }
            nextEntry = zipInputStream.getNextEntry();
            Log.i(TAG, "解压文件 2： " + nextEntry);
        }
        zipInputStream.close();
        Log.i(TAG, "解压完成");
    }

    private void deleteFolder(File file2) {
        Log.d(TAG, "deleteFolder folder = " + file2.getName());
        if (file2 != null && file2.exists() && file2.isDirectory()) {
            for (File file3 : file2.listFiles()) {
                if (file3.isFile()) {
                    Log.d(TAG, "delete file name = " + file3.getName() + " " + file3.delete());
                } else if (file3.isDirectory()) {
                    deleteFolder(file3);
                }
            }
            Log.d(TAG, "deleteFolder " + file2.delete());
        }
    }
}
