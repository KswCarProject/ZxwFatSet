package com.szchoiceway.zxwlib.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewOverlay;
import android.view.ViewParent;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.Scroller;
import androidx.constraintlayout.solver.widgets.analyzer.BasicMeasure;
import androidx.core.view.MotionEventCompat;
import com.szchoiceway.zxwlib.GyroScopeWithCompassView;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class OverScrollView extends FrameLayout implements View.OnTouchListener {
    static final int ANIMATED_SCROLL_GAP = 250;
    private static final int INVALID_POINTER = -1;
    static final float MAX_SCROLL_FACTOR = 0.5f;
    static final float OVERSHOOT_TENSION = 0.75f;
    private static final float SCROLL_RATIO = 0.3f;
    private static final String TAG = "com.szchoiceway.zxwlib.view.OverScrollView";
    protected View child;
    float fistIn;
    boolean fistb;
    boolean hasFailedObtainingScrollFields;
    LayoutInflater inflater;
    boolean isInFlingMode;
    private int mActivePointerId;
    private View mChildToScrollTo;
    protected Context mContext;
    private boolean mFillViewport;
    private boolean mIsBeingDragged;
    private boolean mIsLayoutDirty;
    private float mLastMotionY;
    private long mLastScroll;
    private int mMaximumVelocity;
    private int mMinimumVelocity;
    OnScrollChangeListener mOnScrollChangeListener;
    private boolean mScrollViewMovedFocus;
    Field mScrollXField;
    Field mScrollYField;
    /* access modifiers changed from: private */
    public Scroller mScroller;
    private boolean mSmoothScrollingEnabled;
    private final Rect mTempRect;
    private int mTouchSlop;
    private VelocityTracker mVelocityTracker;
    DisplayMetrics metrics;
    private Runnable overScrollerSpringbackTask;
    int prevScrollY;

    public interface OnScrollChangeListener {
        void onScrollChange(float f, int i);
    }

    private int clamp(int i, int i2, int i3) {
        if (i2 >= i3 || i < 0) {
            return 0;
        }
        return i2 + i > i3 ? i3 - i2 : i;
    }

    /* access modifiers changed from: protected */
    public /* bridge */ /* synthetic */ ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return super.generateDefaultLayoutParams();
    }

    public /* bridge */ /* synthetic */ ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return super.generateLayoutParams(attributeSet);
    }

    public /* bridge */ /* synthetic */ ViewOverlay getOverlay() {
        return super.getOverlay();
    }

    public OverScrollView(Context context) {
        this(context, (AttributeSet) null);
        this.mContext = context;
        initBounce();
    }

    public OverScrollView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
        this.mContext = context;
        initBounce();
    }

    public OverScrollView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mTempRect = new Rect();
        this.isInFlingMode = false;
        this.mIsLayoutDirty = true;
        this.mChildToScrollTo = null;
        this.mIsBeingDragged = false;
        this.mSmoothScrollingEnabled = true;
        this.mActivePointerId = -1;
        this.fistIn = 0.0f;
        this.fistb = true;
        this.mContext = context;
        initScrollView();
        setFillViewport(true);
        initBounce();
    }

    private void initBounce() {
        this.metrics = this.mContext.getResources().getDisplayMetrics();
        this.mScroller = new Scroller(getContext(), new OvershootInterpolator(OVERSHOOT_TENSION));
        this.overScrollerSpringbackTask = new Runnable() {
            public void run() {
                OverScrollView.this.mScroller.computeScrollOffset();
                OverScrollView overScrollView = OverScrollView.this;
                overScrollView.scrollTo(0, overScrollView.mScroller.getCurrY());
                if (!OverScrollView.this.mScroller.isFinished()) {
                    OverScrollView.this.post(this);
                }
            }
        };
        this.prevScrollY = getPaddingTop();
        try {
            this.mScrollXField = View.class.getDeclaredField("mScrollX");
            this.mScrollYField = View.class.getDeclaredField("mScrollY");
        } catch (Exception unused) {
            this.hasFailedObtainingScrollFields = true;
        }
    }

    private void SetScrollY(int i) {
        Field field = this.mScrollYField;
        if (field != null) {
            try {
                field.setInt(this, i);
            } catch (Exception unused) {
            }
        }
    }

    private void SetScrollX(int i) {
        Field field = this.mScrollXField;
        if (field != null) {
            try {
                field.setInt(this, i);
            } catch (Exception unused) {
            }
        }
    }

    public void initChildPointer() {
        Log.i(TAG, "initChildPointer");
        View childAt = getChildAt(0);
        this.child = childAt;
        childAt.setPadding(0, 1500, 0, 1500);
    }

    /* access modifiers changed from: protected */
    public float getTopFadingEdgeStrength() {
        if (getChildCount() == 0) {
            return 0.0f;
        }
        int verticalFadingEdgeLength = getVerticalFadingEdgeLength();
        if (getScrollY() < verticalFadingEdgeLength) {
            return ((float) getScrollY()) / ((float) verticalFadingEdgeLength);
        }
        return 1.0f;
    }

    /* access modifiers changed from: protected */
    public float getBottomFadingEdgeStrength() {
        if (getChildCount() == 0) {
            return 0.0f;
        }
        int verticalFadingEdgeLength = getVerticalFadingEdgeLength();
        int bottom = (getChildAt(0).getBottom() - getScrollY()) - (getHeight() - getPaddingBottom());
        if (bottom < verticalFadingEdgeLength) {
            return ((float) bottom) / ((float) verticalFadingEdgeLength);
        }
        return 1.0f;
    }

    public int getMaxScrollAmount() {
        return (int) (((float) (getBottom() - getTop())) * MAX_SCROLL_FACTOR);
    }

    private void initScrollView() {
        this.mScroller = new Scroller(getContext());
        setFocusable(true);
        setDescendantFocusability(262144);
        setWillNotDraw(false);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(this.mContext);
        this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
        this.mMinimumVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        this.mMaximumVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        setOnTouchListener(this);
        post(new Runnable() {
            public void run() {
                OverScrollView overScrollView = OverScrollView.this;
                overScrollView.scrollTo(0, overScrollView.child.getPaddingTop());
            }
        });
    }

    public void addView(View view) {
        if (getChildCount() <= 0) {
            super.addView(view);
            initChildPointer();
            return;
        }
        throw new IllegalStateException("ScrollView can host only one direct child");
    }

    public void addView(View view, int i) {
        if (getChildCount() <= 0) {
            super.addView(view, i);
            initChildPointer();
            return;
        }
        throw new IllegalStateException("ScrollView can host only one direct child");
    }

    public void addView(View view, ViewGroup.LayoutParams layoutParams) {
        if (getChildCount() <= 0) {
            super.addView(view, layoutParams);
            initChildPointer();
            return;
        }
        throw new IllegalStateException("ScrollView can host only one direct child");
    }

    public void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        if (getChildCount() <= 0) {
            super.addView(view, i, layoutParams);
            return;
        }
        throw new IllegalStateException("ScrollView can host only one direct child");
    }

    private boolean canScroll() {
        View childAt = getChildAt(0);
        if (childAt == null) {
            return false;
        }
        if (getHeight() < childAt.getHeight() + getPaddingTop() + getPaddingBottom()) {
            return true;
        }
        return false;
    }

    public boolean isFillViewport() {
        return this.mFillViewport;
    }

    public void setFillViewport(boolean z) {
        if (z != this.mFillViewport) {
            this.mFillViewport = z;
            requestLayout();
        }
    }

    public boolean isSmoothScrollingEnabled() {
        return this.mSmoothScrollingEnabled;
    }

    public void setSmoothScrollingEnabled(boolean z) {
        this.mSmoothScrollingEnabled = z;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (this.mFillViewport && View.MeasureSpec.getMode(i2) != 0 && getChildCount() > 0) {
            View childAt = getChildAt(0);
            int measuredHeight = getMeasuredHeight();
            if (childAt.getMeasuredHeight() < measuredHeight) {
                childAt.measure(getChildMeasureSpec(i, getPaddingLeft() + getPaddingRight(), ((FrameLayout.LayoutParams) childAt.getLayoutParams()).width), View.MeasureSpec.makeMeasureSpec((measuredHeight - getPaddingTop()) - getPaddingBottom(), BasicMeasure.EXACTLY));
            }
        }
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        return super.dispatchKeyEvent(keyEvent) || executeKeyEvent(keyEvent);
    }

    public boolean executeKeyEvent(KeyEvent keyEvent) {
        this.mTempRect.setEmpty();
        boolean canScroll = canScroll();
        int i = GyroScopeWithCompassView.CARTYPE_MACAN_HI;
        if (!canScroll) {
            if (!isFocused() || keyEvent.getKeyCode() == 4) {
                return false;
            }
            View findFocus = findFocus();
            if (findFocus == this) {
                findFocus = null;
            }
            View findNextFocus = FocusFinder.getInstance().findNextFocus(this, findFocus, GyroScopeWithCompassView.CARTYPE_MACAN_HI);
            if (findNextFocus == null || findNextFocus == this || !findNextFocus.requestFocus(GyroScopeWithCompassView.CARTYPE_MACAN_HI)) {
                return false;
            }
            return true;
        } else if (keyEvent.getAction() != 0) {
            return false;
        } else {
            int keyCode = keyEvent.getKeyCode();
            if (keyCode != 19) {
                if (keyCode != 20) {
                    if (keyCode != 62) {
                        return false;
                    }
                    if (keyEvent.isShiftPressed()) {
                        i = 33;
                    }
                    pageScroll(i);
                    return false;
                } else if (!keyEvent.isAltPressed()) {
                    return arrowScroll(GyroScopeWithCompassView.CARTYPE_MACAN_HI);
                } else {
                    return fullScroll(GyroScopeWithCompassView.CARTYPE_MACAN_HI);
                }
            } else if (!keyEvent.isAltPressed()) {
                return arrowScroll(33);
            } else {
                return fullScroll(33);
            }
        }
    }

    public boolean inChild(int i, int i2) {
        if (getChildCount() <= 0) {
            return false;
        }
        int scrollY = getScrollY();
        View childAt = getChildAt(0);
        if (i2 < childAt.getTop() - scrollY || i2 >= childAt.getBottom() - scrollY || i < childAt.getLeft() || i >= childAt.getRight()) {
            return false;
        }
        return true;
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        Log.i(TAG, "onInterceptTouchEvent: " + motionEvent.getAction());
        int action = motionEvent.getAction();
        if (action == 2 && this.mIsBeingDragged) {
            return true;
        }
        int i = action & 255;
        if (i != 0) {
            if (i != 1) {
                if (i == 2) {
                    int i2 = this.mActivePointerId;
                    if (i2 != -1) {
                        float y = motionEvent.getY(motionEvent.findPointerIndex(i2));
                        if (((int) Math.abs(y - this.mLastMotionY)) > this.mTouchSlop) {
                            this.mIsBeingDragged = true;
                            this.mLastMotionY = y;
                        }
                    }
                } else if (i != 3) {
                    if (i == 6) {
                        onSecondaryPointerUp(motionEvent);
                    }
                }
            }
            this.mIsBeingDragged = false;
            this.mActivePointerId = -1;
        } else {
            float y2 = motionEvent.getY();
            if (!inChild((int) motionEvent.getX(), (int) y2)) {
                this.mIsBeingDragged = false;
            } else {
                this.mLastMotionY = y2;
                this.mActivePointerId = motionEvent.getPointerId(0);
                this.mIsBeingDragged = !this.mScroller.isFinished();
            }
        }
        return this.mIsBeingDragged;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        Log.i(TAG, "onTouchEvent: onScrollChange----   " + motionEvent.getAction());
        if (motionEvent.getAction() == 0 && motionEvent.getEdgeFlags() != 0) {
            return false;
        }
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(motionEvent);
        int action = motionEvent.getAction() & 255;
        if (action == 0) {
            float y = motionEvent.getY();
            boolean inChild = inChild((int) motionEvent.getX(), (int) y);
            this.mIsBeingDragged = inChild;
            if (!inChild) {
                return false;
            }
            if (!this.mScroller.isFinished()) {
                this.mScroller.abortAnimation();
            }
            this.mLastMotionY = y;
            this.mActivePointerId = motionEvent.getPointerId(0);
        } else if (action != 1) {
            if (action != 2) {
                if (action != 3) {
                    if (action == 6) {
                        onSecondaryPointerUp(motionEvent);
                    }
                } else if (this.mIsBeingDragged && getChildCount() > 0) {
                    this.mActivePointerId = -1;
                    this.mIsBeingDragged = false;
                    VelocityTracker velocityTracker = this.mVelocityTracker;
                    if (velocityTracker != null) {
                        velocityTracker.recycle();
                        this.mVelocityTracker = null;
                    }
                }
            } else if (this.mIsBeingDragged) {
                float y2 = motionEvent.getY(motionEvent.findPointerIndex(this.mActivePointerId));
                int i = (int) (this.mLastMotionY - y2);
                this.mLastMotionY = y2;
                if (isOverScrolled()) {
                    scrollBy(0, (int) (((float) i) * SCROLL_RATIO));
                } else {
                    scrollBy(0, i);
                }
            }
        } else if (this.mIsBeingDragged) {
            VelocityTracker velocityTracker2 = this.mVelocityTracker;
            velocityTracker2.computeCurrentVelocity(1000, (float) this.mMaximumVelocity);
            int yVelocity = (int) velocityTracker2.getYVelocity(this.mActivePointerId);
            if (getChildCount() > 0 && Math.abs(yVelocity) > this.mMinimumVelocity) {
                fling(-yVelocity);
            }
            this.mActivePointerId = -1;
            this.mIsBeingDragged = false;
            VelocityTracker velocityTracker3 = this.mVelocityTracker;
            if (velocityTracker3 != null) {
                velocityTracker3.recycle();
                this.mVelocityTracker = null;
            }
        }
        return true;
    }

    public boolean isOverScrolled() {
        return getScrollY() < this.child.getPaddingTop() || getScrollY() > (this.child.getBottom() - this.child.getPaddingBottom()) - getHeight();
    }

    private void onSecondaryPointerUp(MotionEvent motionEvent) {
        int action = (motionEvent.getAction() & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8;
        if (motionEvent.getPointerId(action) == this.mActivePointerId) {
            int i = action == 0 ? 1 : 0;
            this.mLastMotionY = motionEvent.getY(i);
            this.mActivePointerId = motionEvent.getPointerId(i);
            VelocityTracker velocityTracker = this.mVelocityTracker;
            if (velocityTracker != null) {
                velocityTracker.clear();
            }
        }
    }

    private View findFocusableViewInMyBounds(boolean z, int i, View view) {
        int verticalFadingEdgeLength = getVerticalFadingEdgeLength() / 2;
        int i2 = i + verticalFadingEdgeLength;
        int height = (i + getHeight()) - verticalFadingEdgeLength;
        if (view == null || view.getTop() >= height || view.getBottom() <= i2) {
            return findFocusableViewInBounds(z, i2, height);
        }
        return view;
    }

    private View findFocusableViewInBounds(boolean z, int i, int i2) {
        ArrayList focusables = getFocusables(2);
        int size = focusables.size();
        View view = null;
        boolean z2 = false;
        for (int i3 = 0; i3 < size; i3++) {
            View view2 = (View) focusables.get(i3);
            int top = view2.getTop();
            int bottom = view2.getBottom();
            if (i < bottom && top < i2) {
                boolean z3 = i < top && bottom < i2;
                if (view == null) {
                    view = view2;
                    z2 = z3;
                } else {
                    boolean z4 = (z && top < view.getTop()) || (!z && bottom > view.getBottom());
                    if (z2) {
                        if (z3) {
                            if (!z4) {
                            }
                        }
                    } else if (z3) {
                        view = view2;
                        z2 = true;
                    } else if (!z4) {
                    }
                    view = view2;
                }
            }
        }
        return view;
    }

    public boolean pageScroll(int i) {
        boolean z = i == 130;
        int height = getHeight();
        if (z) {
            this.mTempRect.top = getScrollY() + height;
            int childCount = getChildCount();
            if (childCount > 0) {
                View childAt = getChildAt(childCount - 1);
                if (this.mTempRect.top + height > childAt.getBottom()) {
                    this.mTempRect.top = childAt.getBottom() - height;
                }
            }
        } else {
            this.mTempRect.top = getScrollY() - height;
            if (this.mTempRect.top < 0) {
                this.mTempRect.top = 0;
            }
        }
        Rect rect = this.mTempRect;
        rect.bottom = rect.top + height;
        return scrollAndFocus(i, this.mTempRect.top, this.mTempRect.bottom);
    }

    public boolean fullScroll(int i) {
        int childCount;
        boolean z = i == 130;
        int height = getHeight();
        this.mTempRect.top = 0;
        this.mTempRect.bottom = height;
        if (z && (childCount = getChildCount()) > 0) {
            this.mTempRect.bottom = getChildAt(childCount - 1).getBottom();
            Rect rect = this.mTempRect;
            rect.top = rect.bottom - height;
        }
        return scrollAndFocus(i, this.mTempRect.top, this.mTempRect.bottom);
    }

    private boolean scrollAndFocus(int i, int i2, int i3) {
        boolean z;
        int height = getHeight();
        int scrollY = getScrollY();
        int i4 = height + scrollY;
        boolean z2 = i == 33;
        View findFocusableViewInBounds = findFocusableViewInBounds(z2, i2, i3);
        if (findFocusableViewInBounds == null) {
            findFocusableViewInBounds = this;
        }
        if (i2 < scrollY || i3 > i4) {
            doScrollY(z2 ? i2 - scrollY : i3 - i4);
            z = true;
        } else {
            z = false;
        }
        if (findFocusableViewInBounds != findFocus() && findFocusableViewInBounds.requestFocus(i)) {
            this.mScrollViewMovedFocus = true;
            this.mScrollViewMovedFocus = false;
        }
        return z;
    }

    public boolean arrowScroll(int i) {
        int bottom;
        View findFocus = findFocus();
        if (findFocus == this) {
            findFocus = null;
        }
        View findNextFocus = FocusFinder.getInstance().findNextFocus(this, findFocus, i);
        int maxScrollAmount = getMaxScrollAmount();
        if (findNextFocus == null || !isWithinDeltaOfScreen(findNextFocus, maxScrollAmount, getHeight())) {
            if (i == 33 && getScrollY() < maxScrollAmount) {
                maxScrollAmount = getScrollY();
            } else if (i == 130 && getChildCount() > 0 && (bottom = getChildAt(0).getBottom() - (getScrollY() + getHeight())) < maxScrollAmount) {
                maxScrollAmount = bottom;
            }
            if (maxScrollAmount == 0) {
                return false;
            }
            if (i != 130) {
                maxScrollAmount = -maxScrollAmount;
            }
            doScrollY(maxScrollAmount);
        } else {
            findNextFocus.getDrawingRect(this.mTempRect);
            offsetDescendantRectToMyCoords(findNextFocus, this.mTempRect);
            doScrollY(computeScrollDeltaToGetChildRectOnScreen(this.mTempRect));
            findNextFocus.requestFocus(i);
        }
        if (findFocus == null || !findFocus.isFocused() || !isOffScreen(findFocus)) {
            return true;
        }
        int descendantFocusability = getDescendantFocusability();
        setDescendantFocusability(131072);
        requestFocus();
        setDescendantFocusability(descendantFocusability);
        return true;
    }

    private boolean isOffScreen(View view) {
        return !isWithinDeltaOfScreen(view, 0, getHeight());
    }

    private boolean isWithinDeltaOfScreen(View view, int i, int i2) {
        view.getDrawingRect(this.mTempRect);
        offsetDescendantRectToMyCoords(view, this.mTempRect);
        return this.mTempRect.bottom + i >= getScrollY() && this.mTempRect.top - i <= getScrollY() + i2;
    }

    private void doScrollY(int i) {
        if (i == 0) {
            return;
        }
        if (this.mSmoothScrollingEnabled) {
            smoothScrollBy(0, i);
        } else {
            scrollBy(0, i);
        }
    }

    public final void smoothScrollBy(int i, int i2) {
        if (getChildCount() != 0) {
            if (AnimationUtils.currentAnimationTimeMillis() - this.mLastScroll > 250) {
                int max = Math.max(0, getChildAt(0).getHeight() - ((getHeight() - getPaddingBottom()) - getPaddingTop()));
                int scrollY = getScrollY();
                this.mScroller.startScroll(getScrollX(), scrollY, 0, Math.max(0, Math.min(i2 + scrollY, max)) - scrollY);
                invalidate();
            } else {
                if (!this.mScroller.isFinished()) {
                    this.mScroller.abortAnimation();
                }
                scrollBy(i, i2);
            }
            this.mLastScroll = AnimationUtils.currentAnimationTimeMillis();
        }
    }

    public final void smoothScrollToTop() {
        smoothScrollTo(0, this.child.getPaddingTop());
    }

    public final void smoothScrollToBottom() {
        smoothScrollTo(0, (this.child.getHeight() - this.child.getPaddingTop()) - getHeight());
    }

    public final void smoothScrollTo(int i, int i2) {
        smoothScrollBy(i - getScrollX(), i2 - getScrollY());
    }

    /* access modifiers changed from: protected */
    public int computeVerticalScrollRange() {
        int childCount = getChildCount();
        int height = (getHeight() - getPaddingBottom()) - getPaddingTop();
        if (childCount == 0) {
            return height;
        }
        return getChildAt(0).getBottom();
    }

    /* access modifiers changed from: protected */
    public int computeVerticalScrollOffset() {
        return Math.max(0, super.computeVerticalScrollOffset());
    }

    /* access modifiers changed from: protected */
    public void measureChild(View view, int i, int i2) {
        view.measure(getChildMeasureSpec(i, getPaddingLeft() + getPaddingRight(), view.getLayoutParams().width), View.MeasureSpec.makeMeasureSpec(0, 0));
    }

    /* access modifiers changed from: protected */
    public void measureChildWithMargins(View view, int i, int i2, int i3, int i4) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        view.measure(getChildMeasureSpec(i, getPaddingLeft() + getPaddingRight() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin + i2, marginLayoutParams.width), View.MeasureSpec.makeMeasureSpec(marginLayoutParams.topMargin + marginLayoutParams.bottomMargin, 0));
    }

    public void computeScroll() {
        if (this.hasFailedObtainingScrollFields) {
            super.computeScroll();
        } else if (this.mScroller.computeScrollOffset()) {
            int scrollX = getScrollX();
            int scrollY = getScrollY();
            int currX = this.mScroller.getCurrX();
            int currY = this.mScroller.getCurrY();
            if (getChildCount() > 0) {
                View childAt = getChildAt(0);
                int clamp = clamp(currX, (getWidth() - getPaddingRight()) - getPaddingLeft(), childAt.getWidth());
                int clamp2 = clamp(currY, (getHeight() - getPaddingBottom()) - getPaddingTop(), childAt.getHeight());
                if (!(clamp == scrollX && clamp2 == scrollY)) {
                    SetScrollX(clamp);
                    SetScrollY(clamp2);
                    onScrollChanged(clamp, clamp2, scrollX, scrollY);
                }
            }
            awakenScrollBars();
            postInvalidate();
        }
    }

    private void scrollToChild(View view) {
        view.getDrawingRect(this.mTempRect);
        offsetDescendantRectToMyCoords(view, this.mTempRect);
        int computeScrollDeltaToGetChildRectOnScreen = computeScrollDeltaToGetChildRectOnScreen(this.mTempRect);
        if (computeScrollDeltaToGetChildRectOnScreen != 0) {
            scrollBy(0, computeScrollDeltaToGetChildRectOnScreen);
        }
    }

    private boolean scrollToChildRect(Rect rect, boolean z) {
        int computeScrollDeltaToGetChildRectOnScreen = computeScrollDeltaToGetChildRectOnScreen(rect);
        boolean z2 = computeScrollDeltaToGetChildRectOnScreen != 0;
        if (z2) {
            if (z) {
                scrollBy(0, computeScrollDeltaToGetChildRectOnScreen);
            } else {
                smoothScrollBy(0, computeScrollDeltaToGetChildRectOnScreen);
            }
        }
        return z2;
    }

    /* access modifiers changed from: protected */
    public int computeScrollDeltaToGetChildRectOnScreen(Rect rect) {
        int i;
        int i2;
        if (getChildCount() == 0) {
            return 0;
        }
        int height = getHeight();
        int scrollY = getScrollY();
        int i3 = scrollY + height;
        int verticalFadingEdgeLength = getVerticalFadingEdgeLength();
        if (rect.top > 0) {
            scrollY += verticalFadingEdgeLength;
        }
        if (rect.bottom < getChildAt(0).getHeight()) {
            i3 -= verticalFadingEdgeLength;
        }
        if (rect.bottom > i3 && rect.top > scrollY) {
            if (rect.height() > height) {
                i2 = rect.top - scrollY;
            } else {
                i2 = rect.bottom - i3;
            }
            return Math.min(i2 + 0, getChildAt(0).getBottom() - i3);
        } else if (rect.top >= scrollY || rect.bottom >= i3) {
            return 0;
        } else {
            if (rect.height() > height) {
                i = 0 - (i3 - rect.bottom);
            } else {
                i = 0 - (scrollY - rect.top);
            }
            return Math.max(i, -getScrollY());
        }
    }

    public void requestChildFocus(View view, View view2) {
        if (!this.mScrollViewMovedFocus) {
            if (!this.mIsLayoutDirty) {
                scrollToChild(view2);
            } else {
                this.mChildToScrollTo = view2;
            }
        }
        super.requestChildFocus(view, view2);
    }

    /* access modifiers changed from: protected */
    public boolean onRequestFocusInDescendants(int i, Rect rect) {
        View view;
        if (i == 2) {
            i = GyroScopeWithCompassView.CARTYPE_MACAN_HI;
        } else if (i == 1) {
            i = 33;
        }
        if (rect == null) {
            view = FocusFinder.getInstance().findNextFocus(this, (View) null, i);
        } else {
            view = FocusFinder.getInstance().findNextFocusFromRect(this, rect, i);
        }
        if (view != null && !isOffScreen(view)) {
            return view.requestFocus(i, rect);
        }
        return false;
    }

    public boolean requestChildRectangleOnScreen(View view, Rect rect, boolean z) {
        rect.offset(view.getLeft() - view.getScrollX(), view.getTop() - view.getScrollY());
        return scrollToChildRect(rect, z);
    }

    public void requestLayout() {
        this.mIsLayoutDirty = true;
        super.requestLayout();
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.mIsLayoutDirty = false;
        View view = this.mChildToScrollTo;
        if (view != null && isViewDescendantOf(view, this)) {
            scrollToChild(this.mChildToScrollTo);
        }
        this.mChildToScrollTo = null;
        scrollTo(getScrollX(), getScrollY());
        post(new Runnable() {
            public void run() {
                OverScrollView overScrollView = OverScrollView.this;
                overScrollView.scrollTo(0, overScrollView.child.getPaddingTop());
            }
        });
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        View findFocus = findFocus();
        if (findFocus != null && this != findFocus && isWithinDeltaOfScreen(findFocus, 0, i4)) {
            findFocus.getDrawingRect(this.mTempRect);
            offsetDescendantRectToMyCoords(findFocus, this.mTempRect);
            doScrollY(computeScrollDeltaToGetChildRectOnScreen(this.mTempRect));
        }
    }

    /* access modifiers changed from: protected */
    public void onScrollChanged(int i, int i2, int i3, int i4) {
        int height = getHeight();
        int paddingTop = this.child.getPaddingTop();
        int height2 = this.child.getHeight() - this.child.getPaddingBottom();
        if (!this.isInFlingMode || (i2 >= paddingTop && i2 <= height2 - height)) {
            super.onScrollChanged(i, i2, i3, i4);
            return;
        }
        if (i2 < paddingTop) {
            this.mScroller.startScroll(0, i2, 0, paddingTop - i2, 1000);
        } else {
            int i5 = height2 - height;
            if (i2 > i5) {
                this.mScroller.startScroll(0, i2, 0, i5 - i2, 1000);
            }
        }
        post(this.overScrollerSpringbackTask);
        this.isInFlingMode = false;
    }

    private boolean isViewDescendantOf(View view, View view2) {
        if (view == view2) {
            return true;
        }
        ViewParent parent = view.getParent();
        if (!(parent instanceof ViewGroup) || !isViewDescendantOf((View) parent, view2)) {
            return false;
        }
        return true;
    }

    public void fling(int i) {
        Log.i(TAG, "fling: onScrollChange");
        if (getChildCount() > 0) {
            this.mScroller.fling(getScrollX(), getScrollY(), 0, i, 0, 0, 0, Math.max(0, getChildAt(0).getHeight() - ((getHeight() - getPaddingBottom()) - getPaddingTop())));
            boolean z = i > 0;
            View findFocusableViewInMyBounds = findFocusableViewInMyBounds(z, this.mScroller.getFinalY(), findFocus());
            if (findFocusableViewInMyBounds == null) {
                findFocusableViewInMyBounds = this;
            }
            if (findFocusableViewInMyBounds != findFocus()) {
                if (findFocusableViewInMyBounds.requestFocus(z ? GyroScopeWithCompassView.CARTYPE_MACAN_HI : 33)) {
                    this.mScrollViewMovedFocus = true;
                    this.mScrollViewMovedFocus = false;
                }
            }
            invalidate();
        }
    }

    public void scrollTo(int i, int i2) {
        if (getChildCount() > 0) {
            View childAt = getChildAt(0);
            int clamp = clamp(i, (getWidth() - getPaddingRight()) - getPaddingLeft(), childAt.getWidth());
            int clamp2 = clamp(i2, (getHeight() - getPaddingBottom()) - getPaddingTop(), childAt.getHeight());
            if (clamp != getScrollX() || clamp2 != getScrollY()) {
                super.scrollTo(clamp, clamp2);
            }
        }
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        Log.i(TAG, "onTouch---: " + motionEvent.getAction() + "  |  " + motionEvent.getY() + "  |  " + view.getScrollY());
        OnScrollChangeListener onScrollChangeListener = this.mOnScrollChangeListener;
        if (onScrollChangeListener != null) {
            onScrollChangeListener.onScrollChange((float) view.getScrollY(), motionEvent.getAction());
        }
        this.mScroller.forceFinished(true);
        removeCallbacks(this.overScrollerSpringbackTask);
        if (this.fistb) {
            this.fistIn = (float) view.getScrollY();
            this.fistb = false;
        }
        if (motionEvent.getAction() == 1) {
            if (this.fistIn - ((float) view.getScrollY()) <= 150.0f) {
                return overScrollView();
            }
            this.fistb = true;
            return true;
        } else if (motionEvent.getAction() == 3) {
            return overScrollView();
        } else {
            return false;
        }
    }

    private boolean overScrollView() {
        int onOverScroll;
        int height = getHeight();
        String str = TAG;
        Log.i(str, "displayHeight = " + height);
        int paddingTop = this.child.getPaddingTop();
        int height2 = this.child.getHeight() - this.child.getPaddingBottom();
        int scrollY = getScrollY();
        Log.i(str, "currScrollY = " + scrollY);
        Log.i(str, "contentTop = " + paddingTop);
        if (scrollY < paddingTop) {
            onOverScroll(scrollY);
            onOverScroll = paddingTop - scrollY;
        } else if (scrollY + height > height2) {
            onOverScroll = ((this.child.getHeight() - this.child.getPaddingTop()) - this.child.getPaddingBottom() < height ? paddingTop - scrollY : (height2 - height) - scrollY) + onOverScroll(scrollY);
        } else {
            this.isInFlingMode = true;
            return false;
        }
        this.mScroller.startScroll(0, scrollY, 0, onOverScroll, 500);
        post(this.overScrollerSpringbackTask);
        this.prevScrollY = scrollY;
        return true;
    }

    /* access modifiers changed from: protected */
    public int onOverScroll(int i) {
        Log.i(TAG, "onOverScroll: ");
        return 0;
    }

    public void setOnScrollChangeListener(OnScrollChangeListener onScrollChangeListener) {
        this.mOnScrollChangeListener = onScrollChangeListener;
    }
}
