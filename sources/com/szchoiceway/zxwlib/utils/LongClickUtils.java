package com.szchoiceway.zxwlib.utils;

import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import com.szchoiceway.zxwlib.utils.LongClickUtils;

public class LongClickUtils {
    private static final String TAG = "LongClickUtils";
    /* access modifiers changed from: private */
    public static boolean hasBreak = false;

    public void setLongClick(Handler handler, View view, long j, View.OnLongClickListener onLongClickListener) {
        view.setOnTouchListener(new View.OnTouchListener(handler, j, view, onLongClickListener) {
            private int TOUCH_MAX = 50;
            private long downTime = 0;
            private boolean longClick;
            private int mLastMotionX;
            private int mLastMotionY;
            private Runnable r;
            final /* synthetic */ long val$delayMillis;
            final /* synthetic */ Handler val$handler;
            final /* synthetic */ View.OnLongClickListener val$longClickListener;
            final /* synthetic */ View val$longClickView;

            {
                this.val$handler = r2;
                this.val$delayMillis = r3;
                this.val$longClickView = r5;
                this.val$longClickListener = r6;
                this.r = new Runnable(r6, r5) {
                    public final /* synthetic */ View.OnLongClickListener f$0;
                    public final /* synthetic */ View f$1;

                    {
                        this.f$0 = r1;
                        this.f$1 = r2;
                    }

                    public final void run() {
                        LongClickUtils.AnonymousClass1.lambda$$0(this.f$0, this.f$1);
                    }
                };
            }

            public boolean onTouch(View view, MotionEvent motionEvent) {
                int x = (int) motionEvent.getX();
                int y = (int) motionEvent.getY();
                int action = motionEvent.getAction();
                if (action == 0) {
                    Log.d(LongClickUtils.TAG, "ACTION_DOWN");
                    this.longClick = false;
                    boolean unused = LongClickUtils.hasBreak = false;
                    this.downTime = System.currentTimeMillis();
                    this.val$handler.removeCallbacks(this.r);
                    this.mLastMotionX = x;
                    this.mLastMotionY = y;
                    this.val$handler.postDelayed(this.r, this.val$delayMillis);
                } else if (action == 1) {
                    Log.d(LongClickUtils.TAG, "ACTION_UP");
                    if (System.currentTimeMillis() - this.downTime > 2000) {
                        this.longClick = true;
                    }
                    boolean unused2 = LongClickUtils.hasBreak = false;
                    this.val$handler.removeCallbacks(this.r);
                } else if (action == 2) {
                    Log.d(LongClickUtils.TAG, "ACTION_MOVE Math.abs(mLastMotionX - x) = " + Math.abs(this.mLastMotionX - x));
                    if (Math.abs(this.mLastMotionX - x) > this.TOUCH_MAX || Math.abs(this.mLastMotionY - y) > this.TOUCH_MAX) {
                        boolean unused3 = LongClickUtils.hasBreak = false;
                        this.val$handler.removeCallbacks(this.r);
                    }
                }
                if (!this.longClick) {
                    return false;
                }
                this.val$longClickView.setPressed(false);
                return true;
            }

            static /* synthetic */ void lambda$$0(View.OnLongClickListener onLongClickListener, View view) {
                if (onLongClickListener != null && !LongClickUtils.hasBreak) {
                    onLongClickListener.onLongClick(view);
                    boolean unused = LongClickUtils.hasBreak = false;
                }
            }
        });
    }

    public void breakLongClick() {
        hasBreak = true;
    }
}
