package com.szchoiceway.fatset.view;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOverlay;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.Scroller;
import androidx.appcompat.widget.ActivityChooserView;
import java.util.LinkedList;
import java.util.Queue;

public class HorizontalListView extends AdapterView<ListAdapter> {
    protected ListAdapter mAdapter;
    public boolean mAlwaysOverrideTouch = true;
    protected int mCurrentX;
    /* access modifiers changed from: private */
    public boolean mDataChanged = false;
    private DataSetObserver mDataObserver = new DataSetObserver() {
        public void onChanged() {
            synchronized (HorizontalListView.this) {
                boolean unused = HorizontalListView.this.mDataChanged = true;
            }
            HorizontalListView.this.invalidate();
            HorizontalListView.this.requestLayout();
        }

        public void onInvalidated() {
            HorizontalListView.this.reset();
            HorizontalListView.this.invalidate();
            HorizontalListView.this.requestLayout();
        }
    };
    private int mDisplayOffset = 0;
    private GestureDetector mGesture;
    /* access modifiers changed from: private */
    public int mLeftViewIndex = -1;
    private int mMaxX = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
    protected int mNextX;
    private GestureDetector.OnGestureListener mOnGesture = new GestureDetector.SimpleOnGestureListener() {
        public boolean onDown(MotionEvent motionEvent) {
            return HorizontalListView.this.onDown(motionEvent);
        }

        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            return HorizontalListView.this.onFling(motionEvent, motionEvent2, f, f2);
        }

        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            synchronized (HorizontalListView.this) {
                HorizontalListView.this.mNextX += (int) f;
            }
            HorizontalListView.this.requestLayout();
            return true;
        }

        public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
            int i = 0;
            while (true) {
                if (i >= HorizontalListView.this.getChildCount()) {
                    break;
                }
                View childAt = HorizontalListView.this.getChildAt(i);
                if (isEventWithinView(motionEvent, childAt)) {
                    if (HorizontalListView.this.mOnItemClicked != null) {
                        AdapterView.OnItemClickListener access$200 = HorizontalListView.this.mOnItemClicked;
                        HorizontalListView horizontalListView = HorizontalListView.this;
                        access$200.onItemClick(horizontalListView, childAt, horizontalListView.mLeftViewIndex + 1 + i, HorizontalListView.this.mAdapter.getItemId(HorizontalListView.this.mLeftViewIndex + 1 + i));
                    }
                    if (HorizontalListView.this.mOnItemSelected != null) {
                        AdapterView.OnItemSelectedListener access$400 = HorizontalListView.this.mOnItemSelected;
                        HorizontalListView horizontalListView2 = HorizontalListView.this;
                        access$400.onItemSelected(horizontalListView2, childAt, horizontalListView2.mLeftViewIndex + 1 + i, HorizontalListView.this.mAdapter.getItemId(HorizontalListView.this.mLeftViewIndex + 1 + i));
                    }
                } else {
                    i++;
                }
            }
            return true;
        }

        public void onLongPress(MotionEvent motionEvent) {
            int childCount = HorizontalListView.this.getChildCount();
            int i = 0;
            while (i < childCount) {
                View childAt = HorizontalListView.this.getChildAt(i);
                if (!isEventWithinView(motionEvent, childAt)) {
                    i++;
                } else if (HorizontalListView.this.mOnItemLongClicked != null) {
                    AdapterView.OnItemLongClickListener access$500 = HorizontalListView.this.mOnItemLongClicked;
                    HorizontalListView horizontalListView = HorizontalListView.this;
                    access$500.onItemLongClick(horizontalListView, childAt, horizontalListView.mLeftViewIndex + 1 + i, HorizontalListView.this.mAdapter.getItemId(HorizontalListView.this.mLeftViewIndex + 1 + i));
                    return;
                } else {
                    return;
                }
            }
        }

        private boolean isEventWithinView(MotionEvent motionEvent, View view) {
            Rect rect = new Rect();
            int[] iArr = new int[2];
            view.getLocationOnScreen(iArr);
            int i = iArr[0];
            int i2 = iArr[1];
            rect.set(i, i2, view.getWidth() + i, view.getHeight() + i2);
            return rect.contains((int) motionEvent.getRawX(), (int) motionEvent.getRawY());
        }
    };
    /* access modifiers changed from: private */
    public AdapterView.OnItemClickListener mOnItemClicked;
    /* access modifiers changed from: private */
    public AdapterView.OnItemLongClickListener mOnItemLongClicked;
    /* access modifiers changed from: private */
    public AdapterView.OnItemSelectedListener mOnItemSelected;
    private Queue<View> mRemovedViewQueue = new LinkedList();
    private int mRightViewIndex = 0;
    protected Scroller mScroller;

    public View getSelectedView() {
        return null;
    }

    public /* bridge */ /* synthetic */ ViewOverlay getOverlay() {
        return super.getOverlay();
    }

    public HorizontalListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initView();
    }

    private synchronized void initView() {
        this.mLeftViewIndex = -1;
        this.mRightViewIndex = 0;
        this.mDisplayOffset = 0;
        this.mCurrentX = 0;
        this.mNextX = 0;
        this.mMaxX = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
        this.mScroller = new Scroller(getContext());
        this.mGesture = new GestureDetector(getContext(), this.mOnGesture);
    }

    public void setOnItemSelectedListener(AdapterView.OnItemSelectedListener onItemSelectedListener) {
        this.mOnItemSelected = onItemSelectedListener;
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.mOnItemClicked = onItemClickListener;
    }

    public void setOnItemLongClickListener(AdapterView.OnItemLongClickListener onItemLongClickListener) {
        this.mOnItemLongClicked = onItemLongClickListener;
    }

    public ListAdapter getAdapter() {
        return this.mAdapter;
    }

    public void setAdapter(ListAdapter listAdapter) {
        ListAdapter listAdapter2 = this.mAdapter;
        if (listAdapter2 != null) {
            listAdapter2.unregisterDataSetObserver(this.mDataObserver);
        }
        this.mAdapter = listAdapter;
        listAdapter.registerDataSetObserver(this.mDataObserver);
        reset();
    }

    /* access modifiers changed from: private */
    public synchronized void reset() {
        initView();
        removeAllViewsInLayout();
        requestLayout();
    }

    public void setSelection(int i) {
        Log.e("setSelection", "setSelection, setSelection, ");
    }

    private void addAndMeasureChild(View view, int i) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new ViewGroup.LayoutParams(-1, -1);
        }
        addViewInLayout(view, i, layoutParams, true);
        view.measure(View.MeasureSpec.makeMeasureSpec(getWidth(), Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(getHeight(), Integer.MIN_VALUE));
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0067, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void onLayout(boolean r1, int r2, int r3, int r4, int r5) {
        /*
            r0 = this;
            monitor-enter(r0)
            super.onLayout(r1, r2, r3, r4, r5)     // Catch:{ all -> 0x0068 }
            android.widget.ListAdapter r1 = r0.mAdapter     // Catch:{ all -> 0x0068 }
            if (r1 != 0) goto L_0x000a
            monitor-exit(r0)
            return
        L_0x000a:
            boolean r1 = r0.mDataChanged     // Catch:{ all -> 0x0068 }
            r2 = 0
            if (r1 == 0) goto L_0x001b
            int r1 = r0.mCurrentX     // Catch:{ all -> 0x0068 }
            r0.initView()     // Catch:{ all -> 0x0068 }
            r0.removeAllViewsInLayout()     // Catch:{ all -> 0x0068 }
            r0.mNextX = r1     // Catch:{ all -> 0x0068 }
            r0.mDataChanged = r2     // Catch:{ all -> 0x0068 }
        L_0x001b:
            android.widget.Scroller r1 = r0.mScroller     // Catch:{ all -> 0x0068 }
            boolean r1 = r1.computeScrollOffset()     // Catch:{ all -> 0x0068 }
            if (r1 == 0) goto L_0x002b
            android.widget.Scroller r1 = r0.mScroller     // Catch:{ all -> 0x0068 }
            int r1 = r1.getCurrX()     // Catch:{ all -> 0x0068 }
            r0.mNextX = r1     // Catch:{ all -> 0x0068 }
        L_0x002b:
            int r1 = r0.mNextX     // Catch:{ all -> 0x0068 }
            r3 = 1
            if (r1 > 0) goto L_0x0037
            r0.mNextX = r2     // Catch:{ all -> 0x0068 }
            android.widget.Scroller r1 = r0.mScroller     // Catch:{ all -> 0x0068 }
            r1.forceFinished(r3)     // Catch:{ all -> 0x0068 }
        L_0x0037:
            int r1 = r0.mNextX     // Catch:{ all -> 0x0068 }
            int r2 = r0.mMaxX     // Catch:{ all -> 0x0068 }
            if (r1 < r2) goto L_0x0044
            r0.mNextX = r2     // Catch:{ all -> 0x0068 }
            android.widget.Scroller r1 = r0.mScroller     // Catch:{ all -> 0x0068 }
            r1.forceFinished(r3)     // Catch:{ all -> 0x0068 }
        L_0x0044:
            int r1 = r0.mCurrentX     // Catch:{ all -> 0x0068 }
            int r2 = r0.mNextX     // Catch:{ all -> 0x0068 }
            int r1 = r1 - r2
            r0.removeNonVisibleItems(r1)     // Catch:{ all -> 0x0068 }
            r0.fillList(r1)     // Catch:{ all -> 0x0068 }
            r0.positionItems(r1)     // Catch:{ all -> 0x0068 }
            int r1 = r0.mNextX     // Catch:{ all -> 0x0068 }
            r0.mCurrentX = r1     // Catch:{ all -> 0x0068 }
            android.widget.Scroller r1 = r0.mScroller     // Catch:{ all -> 0x0068 }
            boolean r1 = r1.isFinished()     // Catch:{ all -> 0x0068 }
            if (r1 != 0) goto L_0x0066
            com.szchoiceway.fatset.view.HorizontalListView$2 r1 = new com.szchoiceway.fatset.view.HorizontalListView$2     // Catch:{ all -> 0x0068 }
            r1.<init>()     // Catch:{ all -> 0x0068 }
            r0.post(r1)     // Catch:{ all -> 0x0068 }
        L_0x0066:
            monitor-exit(r0)
            return
        L_0x0068:
            r1 = move-exception
            monitor-exit(r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.szchoiceway.fatset.view.HorizontalListView.onLayout(boolean, int, int, int, int):void");
    }

    private void fillList(int i) {
        View childAt = getChildAt(getChildCount() - 1);
        int i2 = 0;
        fillListRight(childAt != null ? childAt.getRight() : 0, i);
        View childAt2 = getChildAt(0);
        if (childAt2 != null) {
            i2 = childAt2.getLeft();
        }
        fillListLeft(i2, i);
    }

    private void fillListRight(int i, int i2) {
        while (i + i2 < getWidth() && this.mRightViewIndex < this.mAdapter.getCount()) {
            View view = this.mAdapter.getView(this.mRightViewIndex, this.mRemovedViewQueue.poll(), this);
            addAndMeasureChild(view, -1);
            i += view.getMeasuredWidth();
            if (this.mRightViewIndex == this.mAdapter.getCount() - 1) {
                this.mMaxX = (this.mCurrentX + i) - getWidth();
            }
            if (this.mMaxX < 0) {
                this.mMaxX = 0;
            }
            this.mRightViewIndex++;
        }
    }

    private void fillListLeft(int i, int i2) {
        int i3;
        while (i + i2 > 0 && (i3 = this.mLeftViewIndex) >= 0) {
            View view = this.mAdapter.getView(i3, this.mRemovedViewQueue.poll(), this);
            addAndMeasureChild(view, 0);
            i -= view.getMeasuredWidth();
            this.mLeftViewIndex--;
            this.mDisplayOffset -= view.getMeasuredWidth();
        }
    }

    private void removeNonVisibleItems(int i) {
        View childAt = getChildAt(0);
        while (childAt != null && childAt.getRight() + i <= 0) {
            this.mDisplayOffset += childAt.getMeasuredWidth();
            this.mRemovedViewQueue.offer(childAt);
            removeViewInLayout(childAt);
            this.mLeftViewIndex++;
            childAt = getChildAt(0);
        }
        View childAt2 = getChildAt(getChildCount() - 1);
        while (childAt2 != null && childAt2.getLeft() + i >= getWidth()) {
            this.mRemovedViewQueue.offer(childAt2);
            removeViewInLayout(childAt2);
            this.mRightViewIndex--;
            childAt2 = getChildAt(getChildCount() - 1);
        }
    }

    private void positionItems(int i) {
        if (getChildCount() > 0) {
            int i2 = this.mDisplayOffset + i;
            this.mDisplayOffset = i2;
            for (int i3 = 0; i3 < getChildCount(); i3++) {
                View childAt = getChildAt(i3);
                int measuredWidth = childAt.getMeasuredWidth();
                childAt.layout(i2, 0, i2 + measuredWidth, childAt.getMeasuredHeight());
                i2 += measuredWidth + childAt.getPaddingRight();
            }
        }
    }

    public synchronized void scrollTo(int i) {
        Scroller scroller = this.mScroller;
        int i2 = this.mNextX;
        scroller.startScroll(i2, 0, i - i2, 0);
        requestLayout();
    }

    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        getParent().requestDisallowInterceptTouchEvent(true);
        return this.mGesture.onTouchEvent(motionEvent) | super.dispatchTouchEvent(motionEvent);
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return super.onInterceptTouchEvent(motionEvent);
    }

    /* access modifiers changed from: protected */
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        synchronized (this) {
            this.mScroller.fling(this.mNextX, 0, (int) (-f), 0, 0, this.mMaxX, 0, 0);
        }
        requestLayout();
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean onDown(MotionEvent motionEvent) {
        this.mScroller.forceFinished(true);
        return true;
    }
}
