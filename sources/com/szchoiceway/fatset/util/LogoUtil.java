package com.szchoiceway.fatset.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.SystemProperties;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import com.szchoiceway.fatset.SysProviderOpt;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

public class LogoUtil {
    public static final String KSW_BACK_PATH = "/mnt/privdata1/";
    public static final String LOGO_NAME = "logo.bmp";
    public static String LOGO_PATH = "/dev/block/by-name/privdata2";
    public static final String LOGO_ZIP_FOLDER = "bootlogo/";
    private static final String TAG = "LogoUtil";
    private Context mContext;

    public LogoUtil(Context context) {
        this.mContext = context;
        if (Build.MODEL.equals("GT3")) {
            LOGO_PATH = "/dev/block/by-name/logo";
        }
    }

    public void saveLogoFile(Bitmap bitmap) throws IOException {
        Log.d(TAG, "saveLogoFile bitmap = " + bitmap);
        if (bitmap != null) {
            if (SysProviderOpt.getInstance(this.mContext).getRecordValue("RESOLUTION", "1920x720").startsWith("720x1920")) {
                Log.d(TAG, "saveLogoFile rotate 90");
                Matrix matrix = new Matrix();
                matrix.setRotate(90.0f, ((float) bitmap.getWidth()) / 2.0f, ((float) bitmap.getHeight()) / 2.0f);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            }
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            int i = width * 3;
            int i2 = ((width % 4) + i) * height;
            try {
                String str = "chmod 777 " + LOGO_PATH;
                if ("GT3".equals(Build.MODEL)) {
                    str = "su";
                }
                runCmd(str);
                while (!getCmdResult()) {
                }
                RandomAccessFile randomAccessFile = new RandomAccessFile(LOGO_PATH, "rw");
                randomAccessFile.seek(PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED);
                writeWord(randomAccessFile, 19778);
                writeDword(randomAccessFile, (long) (i2 + 54));
                writeWord(randomAccessFile, 0);
                writeWord(randomAccessFile, 0);
                writeDword(randomAccessFile, 54);
                writeDword(randomAccessFile, 40);
                writeLong(randomAccessFile, (long) width);
                writeLong(randomAccessFile, (long) height);
                writeWord(randomAccessFile, 1);
                writeWord(randomAccessFile, 24);
                writeDword(randomAccessFile, 0);
                writeDword(randomAccessFile, 0);
                writeLong(randomAccessFile, 0);
                writeLong(randomAccessFile, 0);
                writeDword(randomAccessFile, 0);
                writeDword(randomAccessFile, 0);
                byte[] bArr = new byte[i2];
                int i3 = i + (width % 4);
                int i4 = height - 1;
                int i5 = 0;
                while (i5 < height) {
                    int i6 = 0;
                    int i7 = 0;
                    while (i6 < width) {
                        int pixel = bitmap.getPixel(i6, i5);
                        int i8 = (i4 * i3) + i7;
                        bArr[i8] = (byte) Color.blue(pixel);
                        bArr[i8 + 1] = (byte) Color.green(pixel);
                        bArr[i8 + 2] = (byte) Color.red(pixel);
                        i6++;
                        i7 += 3;
                    }
                    i5++;
                    i4--;
                }
                randomAccessFile.write(bArr);
                randomAccessFile.close();
                Runtime.getRuntime().exec("sync");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private static void writeWord(RandomAccessFile randomAccessFile, int i) throws IOException {
        randomAccessFile.write(new byte[]{(byte) (i & 255), (byte) ((i >> 8) & 255)});
    }

    private static void writeDword(RandomAccessFile randomAccessFile, long j) throws IOException {
        randomAccessFile.write(new byte[]{(byte) ((int) (j & 255)), (byte) ((int) ((j >> 8) & 255)), (byte) ((int) ((j >> 16) & 255)), (byte) ((int) ((j >> 24) & 255))});
    }

    private static void writeLong(RandomAccessFile randomAccessFile, long j) throws IOException {
        randomAccessFile.write(new byte[]{(byte) ((int) (j & 255)), (byte) ((int) ((j >> 8) & 255)), (byte) ((int) ((j >> 16) & 255)), (byte) ((int) ((j >> 24) & 255))});
    }

    public boolean copyFile(Bitmap bitmap, String str) {
        if (str.contains(KSW_BACK_PATH)) {
            runCmd("chmod 777 /mnt/privdata1/");
            do {
            } while (!getCmdResult());
        }
        try {
            InputStream bitmapToInputStream = bitmapToInputStream(bitmap);
            File file = new File(str);
            Log.i(TAG, "--->>> olderPath = 000");
            if (file.exists()) {
                Log.i(TAG, "--->>> olderPath = 111");
                file.delete();
            }
            file.createNewFile();
            if (bitmapToInputStream != null) {
                FileOutputStream fileOutputStream = new FileOutputStream(str);
                Log.i(TAG, "--->>> olderPath = 222");
                byte[] bArr = new byte[1024];
                while (true) {
                    int read = bitmapToInputStream.read(bArr);
                    if (read == -1) {
                        break;
                    }
                    fileOutputStream.write(bArr, 0, read);
                }
                Log.i(TAG, "--->>> olderPath = 333");
                fileOutputStream.flush();
                fileOutputStream.close();
                bitmapToInputStream.close();
            }
            Runtime.getRuntime().exec("sync");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void runCmd(String str) {
        SystemProperties.set("sys.apk_path", str);
        SystemProperties.set("ctl.start", "install_apk");
    }

    private boolean getCmdResult() {
        return SystemProperties.get("sys.apk_path").equals("true");
    }

    private InputStream bitmapToInputStream(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x005d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.ArrayList<android.graphics.drawable.Drawable> getCustomerLogos() {
        /*
            r7 = this;
            java.io.File r0 = new java.io.File
            java.lang.String r1 = "/mnt/privdata1/bootlogo/"
            r0.<init>(r1)
            boolean r2 = r0.exists()
            if (r2 == 0) goto L_0x009a
            boolean r2 = r0.canRead()
            if (r2 == 0) goto L_0x0019
            boolean r0 = r0.canWrite()
            if (r0 != 0) goto L_0x0025
        L_0x0019:
            java.lang.String r0 = "chmod 777 /mnt/privdata1/bootlogo/"
            r7.runCmd(r0)
        L_0x001e:
            boolean r0 = r7.getCmdResult()
            if (r0 != 0) goto L_0x0025
            goto L_0x001e
        L_0x0025:
            android.content.Context r0 = r7.mContext
            com.szchoiceway.fatset.SysProviderOpt r0 = com.szchoiceway.fatset.SysProviderOpt.getInstance(r0)
            java.lang.String r2 = "KSW_UI_RESOLUTION"
            java.lang.String r3 = ""
            java.lang.String r0 = r0.getRecordValue(r2, r3)
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "getCustomerLogos strResolution = "
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.StringBuilder r2 = r2.append(r0)
            java.lang.String r2 = r2.toString()
            java.lang.String r3 = "LogoUtil"
            android.util.Log.d(r3, r2)
            java.lang.String r2 = "x"
            java.lang.String r3 = "_"
            java.lang.String r0 = r0.replace(r2, r3)
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
            r3 = 1
        L_0x0059:
            r4 = 18
            if (r3 > r4) goto L_0x0099
            java.io.File r4 = new java.io.File
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.StringBuilder r5 = r5.append(r1)
            java.lang.StringBuilder r5 = r5.append(r0)
            java.lang.String r6 = "/logo_customer"
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.StringBuilder r5 = r5.append(r3)
            java.lang.String r6 = ".bmp"
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.String r5 = r5.toString()
            r4.<init>(r5)
            boolean r5 = r4.exists()
            if (r5 == 0) goto L_0x0096
            android.content.Context r5 = r7.mContext
            java.lang.String r4 = r4.getPath()
            android.graphics.drawable.Drawable r4 = path2Drawable(r5, r4)
            r2.add(r4)
        L_0x0096:
            int r3 = r3 + 1
            goto L_0x0059
        L_0x0099:
            return r2
        L_0x009a:
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.szchoiceway.fatset.util.LogoUtil.getCustomerLogos():java.util.ArrayList");
    }

    public static Drawable path2Drawable(Context context, String str) {
        if (str == null || str.isEmpty()) {
            return null;
        }
        try {
            return new BitmapDrawable(context.getResources(), BitmapFactory.decodeStream(new FileInputStream(str)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
