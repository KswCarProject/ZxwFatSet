package com.szchoiceway.fatset.util;

import android.animation.ValueAnimator;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import androidx.appcompat.widget.ActivityChooserView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class PagingScrollHelper {
    private static final double INTERVAL = 3.0d;
    public static final String TAG = "PagingScrollHelper";
    private static final long TIME = 20;
    int accelerated = 50;
    int endPoint = 0;
    /* access modifiers changed from: private */
    public float endX = 0.0f;
    /* access modifiers changed from: private */
    public float endY = 0.0f;
    private int firstItemPosition = -2;
    private int indexPage;
    int intiSpeed = 10;
    int intiSpeed2 = -10;
    private boolean isAdd;
    private int lastItemPosition = -1;
    private ValueAnimator mAnimator = null;
    private float mDensity;
    /* access modifiers changed from: private */
    public int mDx = 0;
    /* access modifiers changed from: private */
    public int mDy = 0;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message message) {
            super.handleMessage(message);
            if (message.what == 1) {
                removeMessages(1);
                if (PagingScrollHelper.this.pointList.size() > 0) {
                    int size = PagingScrollHelper.this.pointList.size();
                    Point point = (Point) PagingScrollHelper.this.pointList.get(0);
                    Point point2 = (Point) PagingScrollHelper.this.pointList.get(size - 1);
                    float unused = PagingScrollHelper.this.startX = point.x;
                    float unused2 = PagingScrollHelper.this.startY = point.y;
                    float unused3 = PagingScrollHelper.this.endX = point2.x;
                    float unused4 = PagingScrollHelper.this.endY = point2.y;
                    if (PagingScrollHelper.this.endX != PagingScrollHelper.this.startX) {
                        PagingScrollHelper.this.pointList.clear();
                        PagingScrollHelper.this.fl();
                    }
                }
            }
        }
    };
    /* access modifiers changed from: private */
    public MyOnFlingListener mOnFlingListener = new MyOnFlingListener();
    private onPageChangeListener mOnPageChangeListener;
    private MyOnScrollListener mOnScrollListener = new MyOnScrollListener();
    private MyOnTouchListener mOnTouchListener = new MyOnTouchListener();
    /* access modifiers changed from: private */
    public ORIENTATION mOrientation = ORIENTATION.HORIZONTAL;
    private int mPage = 0;
    /* access modifiers changed from: private */
    public RecyclerView mRecyclerView = null;
    private int offsetX = 0;
    private int offsetY = 0;
    private int pageNum = -1;
    /* access modifiers changed from: private */
    public List<Point> pointList = new ArrayList();
    /* access modifiers changed from: private */
    public boolean post = false;
    int startPoint = 0;
    /* access modifiers changed from: private */
    public float startX = 0.0f;
    /* access modifiers changed from: private */
    public float startY = 0.0f;
    /* access modifiers changed from: private */
    public float startxp;
    /* access modifiers changed from: private */
    public Handler tickHandler = new Handler();
    /* access modifiers changed from: private */
    public Runnable tickRunnable = new Runnable() {
        public void run() {
            if (PagingScrollHelper.this.endPoint == PagingScrollHelper.this.startPoint || !PagingScrollHelper.this.post) {
                PagingScrollHelper.this.stop();
                return;
            }
            if (PagingScrollHelper.this.startPoint < PagingScrollHelper.this.endPoint) {
                int unused = PagingScrollHelper.this.x = (int) Math.ceil(((double) (((float) PagingScrollHelper.this.endPoint) - PagingScrollHelper.this.startxp)) / PagingScrollHelper.INTERVAL);
                if (PagingScrollHelper.this.intiSpeed < PagingScrollHelper.this.x) {
                    PagingScrollHelper pagingScrollHelper = PagingScrollHelper.this;
                    int unused2 = pagingScrollHelper.x = pagingScrollHelper.intiSpeed;
                    PagingScrollHelper.this.intiSpeed += PagingScrollHelper.this.accelerated;
                }
            } else {
                int unused3 = PagingScrollHelper.this.x = (int) Math.floor(((double) (((float) PagingScrollHelper.this.endPoint) - PagingScrollHelper.this.startxp)) / PagingScrollHelper.INTERVAL);
                if (PagingScrollHelper.this.intiSpeed2 > PagingScrollHelper.this.x) {
                    PagingScrollHelper pagingScrollHelper2 = PagingScrollHelper.this;
                    int unused4 = pagingScrollHelper2.x = pagingScrollHelper2.intiSpeed2;
                    PagingScrollHelper.this.intiSpeed2 -= PagingScrollHelper.this.accelerated;
                }
            }
            PagingScrollHelper pagingScrollHelper3 = PagingScrollHelper.this;
            PagingScrollHelper.access$316(pagingScrollHelper3, (float) pagingScrollHelper3.x);
            Log.i(PagingScrollHelper.TAG, "run: x = " + PagingScrollHelper.this.x + ", startxp = " + PagingScrollHelper.this.startxp + ", endPoint = " + PagingScrollHelper.this.endPoint);
            if (PagingScrollHelper.this.x == 0) {
                PagingScrollHelper.this.stop();
                PagingScrollHelper.this.mOnFlingListener.onFling(0, 0);
            }
            PagingScrollHelper.this.mRecyclerView.scrollBy(PagingScrollHelper.this.x, 0);
            PagingScrollHelper.this.tickHandler.postDelayed(PagingScrollHelper.this.tickRunnable, PagingScrollHelper.TIME);
        }
    };
    private int totalNum;
    /* access modifiers changed from: private */
    public int x = 0;

    private enum ORIENTATION {
        HORIZONTAL,
        VERTICAL,
        NULL
    }

    public interface onPageChangeListener {
        void onPageChange(int i);

        void onPageState(int i);
    }

    public void setDuration(int i) {
    }

    static /* synthetic */ int access$1112(PagingScrollHelper pagingScrollHelper, int i) {
        int i2 = pagingScrollHelper.offsetX + i;
        pagingScrollHelper.offsetX = i2;
        return i2;
    }

    static /* synthetic */ int access$1212(PagingScrollHelper pagingScrollHelper, int i) {
        int i2 = pagingScrollHelper.offsetY + i;
        pagingScrollHelper.offsetY = i2;
        return i2;
    }

    static /* synthetic */ float access$316(PagingScrollHelper pagingScrollHelper, float f) {
        float f2 = pagingScrollHelper.startxp + f;
        pagingScrollHelper.startxp = f2;
        return f2;
    }

    public void addPoint(Point point) {
        List<Point> list = this.pointList;
        if (list != null) {
            list.clear();
            this.pointList.add(point);
        }
    }

    public void setUpRecycleView(RecyclerView recyclerView) {
        if (recyclerView != null) {
            this.mRecyclerView = recyclerView;
            recyclerView.setOnFlingListener(this.mOnFlingListener);
            recyclerView.setOnScrollListener(this.mOnScrollListener);
            recyclerView.setOnTouchListener(this.mOnTouchListener);
            updateLayoutManger();
            this.mDensity = recyclerView.getContext().getResources().getDisplayMetrics().density;
            Log.i(TAG, "setUpRecycleView: mDensity = " + this.mDensity);
            return;
        }
        throw new IllegalArgumentException("recycleView must be not null");
    }

    public void updateLayoutManger() {
        RecyclerView.LayoutManager layoutManager = this.mRecyclerView.getLayoutManager();
        if (layoutManager != null) {
            if (layoutManager.canScrollVertically()) {
                this.mOrientation = ORIENTATION.VERTICAL;
            } else if (layoutManager.canScrollHorizontally()) {
                this.mOrientation = ORIENTATION.HORIZONTAL;
            } else {
                this.mOrientation = ORIENTATION.NULL;
            }
            ValueAnimator valueAnimator = this.mAnimator;
            if (valueAnimator != null) {
                valueAnimator.cancel();
            }
            this.offsetX = 0;
            this.offsetY = 0;
            Log.i(TAG, "updateLayoutManger: ");
        }
    }

    public int getPageCount() {
        if (this.mRecyclerView == null || this.mOrientation == ORIENTATION.NULL) {
            return 0;
        }
        if (this.mOrientation == ORIENTATION.VERTICAL && this.mRecyclerView.computeVerticalScrollExtent() != 0) {
            return this.mRecyclerView.computeVerticalScrollRange() / this.mRecyclerView.computeVerticalScrollExtent();
        }
        if (this.mRecyclerView.computeHorizontalScrollExtent() != 0) {
            return this.mRecyclerView.computeHorizontalScrollRange() / this.mRecyclerView.computeHorizontalScrollExtent();
        }
        return 0;
    }

    public void scrollToPosition(int i) {
        int i2;
        if (i < 0) {
            i = 0;
        }
        if (i >= getPageCount()) {
            i = getPageCount() - 1;
        }
        Log.i(TAG, "scrollToPosition: position = " + i);
        int i3 = this.mOrientation == ORIENTATION.VERTICAL ? this.offsetY : this.offsetX;
        Log.i(TAG, "scrollToPosition: startPoint = " + i3);
        if (this.mOrientation == ORIENTATION.VERTICAL) {
            i2 = this.mRecyclerView.getHeight();
        } else {
            i2 = this.mRecyclerView.getWidth();
        }
        int i4 = i2 * i;
        Log.i(TAG, "scrollToPosition: endPoint = " + i4);
        if (i3 != i4) {
            setPoint(i3, i4);
            start();
        }
    }

    public void setPoint(int i, int i2) {
        this.startPoint = i;
        this.endPoint = i2;
        this.startxp = (float) i;
        Log.i(TAG, "setPoint: startPoint = " + i + ", endPoint = " + i2);
    }

    /* access modifiers changed from: package-private */
    public void start() {
        Log.i(TAG, "start: isAdd = " + this.isAdd);
        this.post = true;
        this.accelerated = (int) Math.ceil((double) (((float) (this.mRecyclerView.getWidth() / 16)) / this.mDensity));
        Log.i(TAG, "start: accelerated = " + this.accelerated);
        float f = this.startX;
        float f2 = this.endX;
        int i = (int) (f - f2);
        this.intiSpeed = i;
        if (i == 0 || i < 10) {
            this.intiSpeed = 10;
        }
        int i2 = (int) (f - f2);
        this.intiSpeed2 = i2;
        if (i2 == 0 || i2 > -10) {
            this.intiSpeed2 = -10;
        }
        Log.i(TAG, "start: accelerated = " + this.accelerated + ",intiSpeed = " + this.intiSpeed + ", intiSpeed2 = " + this.intiSpeed2);
        onPageChangeListener onpagechangelistener = this.mOnPageChangeListener;
        if (onpagechangelistener != null) {
            onpagechangelistener.onPageState(1);
        }
        this.tickHandler.removeCallbacks(this.tickRunnable);
        this.tickHandler.postDelayed(this.tickRunnable, TIME);
    }

    /* access modifiers changed from: package-private */
    public void stop() {
        Log.i(TAG, "stop: ");
        this.post = false;
        this.x = 0;
        this.mDx = 0;
        this.mDy = 0;
        onPageChangeListener onpagechangelistener = this.mOnPageChangeListener;
        if (onpagechangelistener != null) {
            onpagechangelistener.onPageState(0);
            this.mOnPageChangeListener.onPageChange(getPageIndex());
        }
        this.tickHandler.removeCallbacks(this.tickRunnable);
    }

    /* access modifiers changed from: private */
    public void touch() {
        Log.i(TAG, "touch: ");
        float f = this.endX;
        float f2 = this.endY;
        long uptimeMillis = SystemClock.uptimeMillis();
        long j = uptimeMillis;
        long j2 = uptimeMillis;
        float f3 = f;
        float f4 = f2;
        MotionEvent obtain = MotionEvent.obtain(j, j2, 0, f3, f4, 0);
        MotionEvent obtain2 = MotionEvent.obtain(j, j2, 1, f3, f4, 0);
        this.mRecyclerView.onTouchEvent(obtain);
        this.mRecyclerView.onTouchEvent(obtain2);
        obtain.recycle();
        obtain2.recycle();
    }

    private class MyOnFlingListener extends RecyclerView.OnFlingListener {
        private MyOnFlingListener() {
        }

        public boolean onFling(int i, int i2) {
            Log.i(PagingScrollHelper.TAG, "onFling: velocityX = " + i + ", velocityY = " + i2 + ",mOrientation = " + PagingScrollHelper.this.mOrientation);
            if (PagingScrollHelper.this.mOrientation == ORIENTATION.NULL || (i == 0 && i2 == 0)) {
                Log.i(PagingScrollHelper.TAG, "onFling: return false...");
                PagingScrollHelper.this.touch();
                return false;
            }
            Log.i(PagingScrollHelper.TAG, "onFling: return true...");
            return true;
        }
    }

    /* access modifiers changed from: private */
    public void fl() {
        int i;
        int i2;
        int pageCount = getPageCount();
        float f = this.startX - this.endX;
        Log.i(TAG, "fl: mDx = " + this.mDx + ", orientation = " + f);
        int i3 = (f > 0.0f ? 1 : (f == 0.0f ? 0 : -1));
        int i4 = 0;
        if (i3 == 0) {
            this.mRecyclerView.scrollBy(-this.mDx, 0);
            return;
        }
        int startPageIndex = getStartPageIndex();
        if (this.mOrientation == ORIENTATION.VERTICAL) {
            i = this.offsetY;
            if (f == 2.14748365E9f) {
                startPageIndex += this.indexPage;
            } else if (f < 0.0f) {
                startPageIndex--;
            } else if (i3 > 0) {
                startPageIndex++;
            } else {
                int i5 = this.pageNum;
                if (i5 != -1) {
                    startPageIndex = i5 - 1;
                    i = 0;
                }
            }
            if (startPageIndex < 0) {
                startPageIndex = 0;
            }
            if (startPageIndex >= pageCount) {
                startPageIndex = pageCount - 1;
            }
            i2 = this.mRecyclerView.getHeight();
        } else {
            int i6 = this.offsetX;
            if (f == 2.14748365E9f) {
                startPageIndex += this.indexPage;
            } else if (f < 0.0f) {
                startPageIndex--;
                this.isAdd = false;
            } else if (i3 > 0) {
                startPageIndex++;
                this.isAdd = true;
            } else {
                int i7 = this.pageNum;
                if (i7 != -1) {
                    startPageIndex = i7 - 1;
                    i6 = 0;
                }
            }
            Log.i(TAG, "fl: page = " + startPageIndex);
            if (startPageIndex < 0) {
                startPageIndex = 0;
            }
            if (startPageIndex >= pageCount) {
                startPageIndex = pageCount - 1;
            }
            Log.i(TAG, "fl: page = " + startPageIndex);
            i2 = this.mRecyclerView.getWidth();
        }
        int i8 = i2 * startPageIndex;
        if (i8 >= 0) {
            i4 = i8;
        }
        Log.i(TAG, "fl: page = " + startPageIndex);
        setPage(startPageIndex);
        setPoint(i, i4);
        Log.i(TAG, "fl: endPoint = " + i4);
        start();
    }

    private class MyOnScrollListener extends RecyclerView.OnScrollListener {
        private MyOnScrollListener() {
        }

        public void onScrollStateChanged(RecyclerView recyclerView, int i) {
            Log.i(PagingScrollHelper.TAG, "onScrollStateChanged: newState = " + i);
        }

        public void onScrolled(RecyclerView recyclerView, int i, int i2) {
            PagingScrollHelper.access$1112(PagingScrollHelper.this, i);
            PagingScrollHelper.access$1212(PagingScrollHelper.this, i2);
            int unused = PagingScrollHelper.this.mDx = i;
            int unused2 = PagingScrollHelper.this.mDy = i2;
        }
    }

    private class MyOnTouchListener implements View.OnTouchListener {
        private MyOnTouchListener() {
        }

        /* JADX WARNING: Removed duplicated region for block: B:35:0x01f7  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTouch(android.view.View r7, android.view.MotionEvent r8) {
            /*
                r6 = this;
                float r7 = r8.getX()
                float r0 = r8.getY()
                r1 = 0
                int r2 = (r7 > r1 ? 1 : (r7 == r1 ? 0 : -1))
                r3 = 0
                if (r2 != 0) goto L_0x0013
                int r2 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
                if (r2 != 0) goto L_0x0013
                return r3
            L_0x0013:
                int r8 = r8.getAction()
                java.lang.String r2 = ", y = "
                r4 = 1
                java.lang.String r5 = "PagingScrollHelper"
                if (r8 == 0) goto L_0x01bf
                if (r8 == r4) goto L_0x0053
                r1 = 2
                if (r8 == r1) goto L_0x0025
                goto L_0x01f4
            L_0x0025:
                java.lang.StringBuilder r8 = new java.lang.StringBuilder
                r8.<init>()
                java.lang.String r1 = "onTouch: move x = "
                java.lang.StringBuilder r8 = r8.append(r1)
                java.lang.StringBuilder r8 = r8.append(r7)
                java.lang.StringBuilder r8 = r8.append(r2)
                java.lang.StringBuilder r8 = r8.append(r0)
                java.lang.String r8 = r8.toString()
                android.util.Log.i(r5, r8)
                com.szchoiceway.fatset.util.PagingScrollHelper r8 = com.szchoiceway.fatset.util.PagingScrollHelper.this
                java.util.List r8 = r8.pointList
                com.szchoiceway.fatset.util.PagingScrollHelper$Point r1 = new com.szchoiceway.fatset.util.PagingScrollHelper$Point
                r1.<init>(r7, r0)
                r8.add(r1)
                goto L_0x01f4
            L_0x0053:
                com.szchoiceway.fatset.util.PagingScrollHelper r7 = com.szchoiceway.fatset.util.PagingScrollHelper.this
                java.util.List r7 = r7.pointList
                int r7 = r7.size()
                java.lang.StringBuilder r8 = new java.lang.StringBuilder
                r8.<init>()
                java.lang.String r0 = "onTouch: size = "
                java.lang.StringBuilder r8 = r8.append(r0)
                java.lang.StringBuilder r8 = r8.append(r7)
                java.lang.String r8 = r8.toString()
                android.util.Log.i(r5, r8)
                if (r7 <= 0) goto L_0x01f5
                com.szchoiceway.fatset.util.PagingScrollHelper r8 = com.szchoiceway.fatset.util.PagingScrollHelper.this
                java.util.List r8 = r8.pointList
                int r0 = r7 / 2
                if (r0 >= 0) goto L_0x0080
                r0 = r3
            L_0x0080:
                java.lang.Object r8 = r8.get(r0)
                com.szchoiceway.fatset.util.PagingScrollHelper$Point r8 = (com.szchoiceway.fatset.util.PagingScrollHelper.Point) r8
                com.szchoiceway.fatset.util.PagingScrollHelper r0 = com.szchoiceway.fatset.util.PagingScrollHelper.this
                java.util.List r0 = r0.pointList
                int r7 = r7 - r4
                java.lang.Object r7 = r0.get(r7)
                com.szchoiceway.fatset.util.PagingScrollHelper$Point r7 = (com.szchoiceway.fatset.util.PagingScrollHelper.Point) r7
                com.szchoiceway.fatset.util.PagingScrollHelper r0 = com.szchoiceway.fatset.util.PagingScrollHelper.this
                float r2 = r8.x
                float unused = r0.startX = r2
                com.szchoiceway.fatset.util.PagingScrollHelper r0 = com.szchoiceway.fatset.util.PagingScrollHelper.this
                float r8 = r8.y
                float unused = r0.startY = r8
                java.lang.StringBuilder r8 = new java.lang.StringBuilder
                r8.<init>()
                java.lang.String r0 = "onTouch: startX = "
                java.lang.StringBuilder r8 = r8.append(r0)
                com.szchoiceway.fatset.util.PagingScrollHelper r0 = com.szchoiceway.fatset.util.PagingScrollHelper.this
                float r0 = r0.startX
                java.lang.StringBuilder r8 = r8.append(r0)
                java.lang.String r0 = ", startY = "
                java.lang.StringBuilder r8 = r8.append(r0)
                com.szchoiceway.fatset.util.PagingScrollHelper r2 = com.szchoiceway.fatset.util.PagingScrollHelper.this
                float r2 = r2.startY
                java.lang.StringBuilder r8 = r8.append(r2)
                java.lang.String r8 = r8.toString()
                android.util.Log.i(r5, r8)
                com.szchoiceway.fatset.util.PagingScrollHelper r8 = com.szchoiceway.fatset.util.PagingScrollHelper.this
                float r2 = r7.x
                float unused = r8.endX = r2
                com.szchoiceway.fatset.util.PagingScrollHelper r8 = com.szchoiceway.fatset.util.PagingScrollHelper.this
                float r7 = r7.y
                float unused = r8.endY = r7
                java.lang.StringBuilder r7 = new java.lang.StringBuilder
                r7.<init>()
                java.lang.String r8 = "onTouch: endX = "
                java.lang.StringBuilder r7 = r7.append(r8)
                com.szchoiceway.fatset.util.PagingScrollHelper r8 = com.szchoiceway.fatset.util.PagingScrollHelper.this
                float r8 = r8.endX
                java.lang.StringBuilder r7 = r7.append(r8)
                java.lang.String r8 = ", endY = "
                java.lang.StringBuilder r7 = r7.append(r8)
                com.szchoiceway.fatset.util.PagingScrollHelper r8 = com.szchoiceway.fatset.util.PagingScrollHelper.this
                float r8 = r8.endY
                java.lang.StringBuilder r7 = r7.append(r8)
                java.lang.String r7 = r7.toString()
                android.util.Log.i(r5, r7)
                com.szchoiceway.fatset.util.PagingScrollHelper r7 = com.szchoiceway.fatset.util.PagingScrollHelper.this
                float r7 = r7.startX
                com.szchoiceway.fatset.util.PagingScrollHelper r8 = com.szchoiceway.fatset.util.PagingScrollHelper.this
                float r8 = r8.endX
                float r7 = r7 - r8
                java.lang.StringBuilder r8 = new java.lang.StringBuilder
                r8.<init>()
                java.lang.String r2 = "onTouch: mDx = "
                java.lang.StringBuilder r8 = r8.append(r2)
                com.szchoiceway.fatset.util.PagingScrollHelper r2 = com.szchoiceway.fatset.util.PagingScrollHelper.this
                int r2 = r2.mDx
                java.lang.StringBuilder r8 = r8.append(r2)
                java.lang.String r2 = ", orientation = "
                java.lang.StringBuilder r8 = r8.append(r2)
                java.lang.StringBuilder r8 = r8.append(r7)
                java.lang.String r8 = r8.toString()
                android.util.Log.i(r5, r8)
                int r8 = (r7 > r1 ? 1 : (r7 == r1 ? 0 : -1))
                if (r8 >= 0) goto L_0x0146
                com.szchoiceway.fatset.util.PagingScrollHelper r8 = com.szchoiceway.fatset.util.PagingScrollHelper.this
                int r8 = r8.mDx
                if (r8 > 0) goto L_0x0154
            L_0x0146:
                int r7 = (r7 > r1 ? 1 : (r7 == r1 ? 0 : -1))
                if (r7 <= 0) goto L_0x0152
                com.szchoiceway.fatset.util.PagingScrollHelper r8 = com.szchoiceway.fatset.util.PagingScrollHelper.this
                int r8 = r8.mDx
                if (r8 < 0) goto L_0x0154
            L_0x0152:
                if (r7 != 0) goto L_0x0198
            L_0x0154:
                com.szchoiceway.fatset.util.PagingScrollHelper r7 = com.szchoiceway.fatset.util.PagingScrollHelper.this
                java.util.List r7 = r7.pointList
                java.lang.Object r7 = r7.get(r3)
                com.szchoiceway.fatset.util.PagingScrollHelper$Point r7 = (com.szchoiceway.fatset.util.PagingScrollHelper.Point) r7
                com.szchoiceway.fatset.util.PagingScrollHelper r8 = com.szchoiceway.fatset.util.PagingScrollHelper.this
                float r1 = r7.x
                float unused = r8.startX = r1
                com.szchoiceway.fatset.util.PagingScrollHelper r8 = com.szchoiceway.fatset.util.PagingScrollHelper.this
                float r7 = r7.y
                float unused = r8.startY = r7
                java.lang.StringBuilder r7 = new java.lang.StringBuilder
                r7.<init>()
                java.lang.String r8 = "onTouch: diff startX = "
                java.lang.StringBuilder r7 = r7.append(r8)
                com.szchoiceway.fatset.util.PagingScrollHelper r8 = com.szchoiceway.fatset.util.PagingScrollHelper.this
                float r8 = r8.startX
                java.lang.StringBuilder r7 = r7.append(r8)
                java.lang.StringBuilder r7 = r7.append(r0)
                com.szchoiceway.fatset.util.PagingScrollHelper r8 = com.szchoiceway.fatset.util.PagingScrollHelper.this
                float r8 = r8.startY
                java.lang.StringBuilder r7 = r7.append(r8)
                java.lang.String r7 = r7.toString()
                android.util.Log.i(r5, r7)
            L_0x0198:
                com.szchoiceway.fatset.util.PagingScrollHelper r7 = com.szchoiceway.fatset.util.PagingScrollHelper.this
                float r7 = r7.endX
                com.szchoiceway.fatset.util.PagingScrollHelper r8 = com.szchoiceway.fatset.util.PagingScrollHelper.this
                float r8 = r8.startX
                int r7 = (r7 > r8 ? 1 : (r7 == r8 ? 0 : -1))
                if (r7 != 0) goto L_0x01b0
                com.szchoiceway.fatset.util.PagingScrollHelper r7 = com.szchoiceway.fatset.util.PagingScrollHelper.this
                int r7 = r7.mDx
                if (r7 == 0) goto L_0x01b5
            L_0x01b0:
                com.szchoiceway.fatset.util.PagingScrollHelper r7 = com.szchoiceway.fatset.util.PagingScrollHelper.this
                r7.fl()
            L_0x01b5:
                com.szchoiceway.fatset.util.PagingScrollHelper r7 = com.szchoiceway.fatset.util.PagingScrollHelper.this
                java.util.List r7 = r7.pointList
                r7.clear()
                goto L_0x01f5
            L_0x01bf:
                java.lang.StringBuilder r8 = new java.lang.StringBuilder
                r8.<init>()
                java.lang.String r1 = "onTouch: down x = "
                java.lang.StringBuilder r8 = r8.append(r1)
                java.lang.StringBuilder r8 = r8.append(r7)
                java.lang.StringBuilder r8 = r8.append(r2)
                java.lang.StringBuilder r8 = r8.append(r0)
                java.lang.String r8 = r8.toString()
                android.util.Log.i(r5, r8)
                com.szchoiceway.fatset.util.PagingScrollHelper r8 = com.szchoiceway.fatset.util.PagingScrollHelper.this
                java.util.List r8 = r8.pointList
                r8.clear()
                com.szchoiceway.fatset.util.PagingScrollHelper r8 = com.szchoiceway.fatset.util.PagingScrollHelper.this
                java.util.List r8 = r8.pointList
                com.szchoiceway.fatset.util.PagingScrollHelper$Point r1 = new com.szchoiceway.fatset.util.PagingScrollHelper$Point
                r1.<init>(r7, r0)
                r8.add(r1)
            L_0x01f4:
                r4 = r3
            L_0x01f5:
                if (r4 == 0) goto L_0x0201
                com.szchoiceway.fatset.util.PagingScrollHelper r7 = com.szchoiceway.fatset.util.PagingScrollHelper.this
                float unused = r7.startX
                com.szchoiceway.fatset.util.PagingScrollHelper r7 = com.szchoiceway.fatset.util.PagingScrollHelper.this
                float unused = r7.endX
            L_0x0201:
                return r3
            */
            throw new UnsupportedOperationException("Method not decompiled: com.szchoiceway.fatset.util.PagingScrollHelper.MyOnTouchListener.onTouch(android.view.View, android.view.MotionEvent):boolean");
        }
    }

    public int getPage() {
        return this.mPage;
    }

    public void setPage(int i) {
        this.mPage = i;
    }

    private int getPageIndex() {
        int i;
        Log.i(TAG, "getPageIndex: offsetX = " + this.offsetX);
        if (this.mOrientation == ORIENTATION.VERTICAL) {
            i = this.offsetY / this.mRecyclerView.getHeight();
        } else {
            i = this.offsetX / this.mRecyclerView.getWidth();
        }
        Log.i(TAG, "getPageIndex: page = " + i);
        return i;
    }

    private int getStartPageIndex() {
        if (this.mOrientation == ORIENTATION.VERTICAL) {
            return this.endPoint / this.mRecyclerView.getHeight();
        }
        return this.endPoint / this.mRecyclerView.getWidth();
    }

    public void setOnPageChangeListener(onPageChangeListener onpagechangelistener) {
        this.mOnPageChangeListener = onpagechangelistener;
    }

    public void setPageNum(int i) {
        this.mRecyclerView.scrollToPosition(0);
        updateLayoutManger();
        this.pageNum = i;
        this.mOnFlingListener.onFling(0, 0);
    }

    public void setIndexPage(int i) {
        this.indexPage = i;
        this.mOnFlingListener.onFling(ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED, ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED);
    }

    public static class Point {
        float x;
        float y;

        public Point(float f, float f2) {
            this.x = f;
            this.y = f2;
        }
    }
}
