package com.szchoiceway.zxwlib.focus.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.ViewOverlay;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.internal.view.SupportMenu;
import com.szchoiceway.zxwlib.GyroScopeWithCompassView;
import com.szchoiceway.zxwlib.R;

public class FocusIconWithTitleView extends RelativeLayout implements FocusStateInterface {
    boolean mDoEnterWhenFocused = false;
    Drawable mFocusedDrawable = null;
    Drawable mFocusedForegroundDrawable = null;
    int mFrameColor = SupportMenu.CATEGORY_MASK;
    int mFrameWidth = 2;
    public Drawable mIcon;
    private int mIconHeight;
    private int mIconMarginTop;
    private int mIconWidth;
    public Drawable mImgBackground;
    private int mImgBackgroundHeight;
    private int mImgBackgroundWidth;
    public Drawable mImgForeground;
    boolean mInFocus = false;
    /* access modifiers changed from: private */
    public ImageView mItemBk;
    private ImageView mItemImage;
    private TextView mItemText;
    boolean mNeedDrawFocusByBackground = true;
    boolean mNeedDrawFocusFrame = false;
    boolean mNeedPerformClickAnim = false;
    Drawable mOldDrawable = null;
    Drawable mOldForegroundDrawable = null;
    Paint mPaint = new Paint();
    int mShadowColor = 0;
    float mShadowDx = 0.0f;
    float mShadowDy = 0.0f;
    float mShadowRadius = 0.0f;
    private String mTitleText;
    private int mTitleTextColor = -1;
    private int mTitleTextHeight;
    private int mTitleTextMarginBottom;
    private int mTitleTextMarginEnd;
    private int mTitleTextMarginStart;
    private float mTitleTextSize;
    private boolean mTitleTextToUpper = true;
    private int mTitleTextWidth;

    public /* bridge */ /* synthetic */ ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return super.generateLayoutParams(attributeSet);
    }

    public /* bridge */ /* synthetic */ ViewOverlay getOverlay() {
        return super.getOverlay();
    }

    public FocusIconWithTitleView(Context context) {
        super(context);
    }

    public FocusIconWithTitleView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attributeSet) {
        addView(LayoutInflater.from(context).inflate(R.layout.focus_icon_with_title_item, (ViewGroup) null, false));
        this.mItemBk = (ImageView) findViewById(R.id.item_bk);
        this.mItemImage = (ImageView) findViewById(R.id.item_image);
        this.mItemText = (TextView) findViewById(R.id.item_text);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.FocusIconWithTitleView);
        this.mNeedDrawFocusFrame = obtainStyledAttributes.getBoolean(R.styleable.FocusIconWithTitleView_needDrawFocusFrame, false);
        this.mNeedDrawFocusByBackground = obtainStyledAttributes.getBoolean(R.styleable.FocusIconWithTitleView_needDrawFocusByBackground, true);
        this.mFocusedDrawable = obtainStyledAttributes.getDrawable(R.styleable.FocusIconWithTitleView_focusedDrawable);
        this.mFocusedForegroundDrawable = obtainStyledAttributes.getDrawable(R.styleable.FocusIconWithTitleView_focusedForegroundDrawable);
        this.mImgBackground = obtainStyledAttributes.getDrawable(R.styleable.FocusIconWithTitleView_imgBackground);
        this.mImgForeground = obtainStyledAttributes.getDrawable(R.styleable.FocusIconWithTitleView_imgForeground);
        this.mIcon = obtainStyledAttributes.getDrawable(R.styleable.FocusIconWithTitleView_icon);
        this.mImgBackgroundWidth = obtainStyledAttributes.getInt(R.styleable.FocusIconWithTitleView_imgBackgroundWidth, GyroScopeWithCompassView.CARTYPE_HIGHLANDER_LO);
        this.mImgBackgroundHeight = obtainStyledAttributes.getInt(R.styleable.FocusIconWithTitleView_imgBackgroundHeight, 123);
        this.mIconWidth = obtainStyledAttributes.getDimensionPixelSize(R.styleable.FocusIconWithTitleView_iconWidth, 0);
        this.mIconHeight = obtainStyledAttributes.getDimensionPixelSize(R.styleable.FocusIconWithTitleView_iconHeight, 0);
        this.mIconMarginTop = obtainStyledAttributes.getDimensionPixelSize(R.styleable.FocusIconWithTitleView_iconMarginTop, 0);
        this.mTitleTextSize = obtainStyledAttributes.getDimension(R.styleable.FocusIconWithTitleView_titleTextSize, 0.0f);
        this.mTitleTextMarginBottom = obtainStyledAttributes.getDimensionPixelSize(R.styleable.FocusIconWithTitleView_titleTextMarginBottom, 0);
        this.mTitleText = obtainStyledAttributes.getString(R.styleable.FocusIconWithTitleView_titleText);
        this.mTitleTextColor = obtainStyledAttributes.getColor(R.styleable.FocusIconWithTitleView_titleTextColor, this.mTitleTextColor);
        this.mTitleTextWidth = obtainStyledAttributes.getDimensionPixelSize(R.styleable.FocusIconWithTitleView_titleTextWidth, 0);
        this.mTitleTextHeight = obtainStyledAttributes.getDimensionPixelSize(R.styleable.FocusIconWithTitleView_titleTextHeight, 0);
        this.mTitleTextToUpper = obtainStyledAttributes.getBoolean(R.styleable.FocusIconWithTitleView_titleTextUpper, this.mTitleTextToUpper);
        this.mTitleTextMarginStart = obtainStyledAttributes.getDimensionPixelSize(R.styleable.FocusIconWithTitleView_titleTextMarginStart, 0);
        this.mTitleTextMarginEnd = obtainStyledAttributes.getDimensionPixelSize(R.styleable.FocusIconWithTitleView_titleTextMarginEnd, 0);
        this.mDoEnterWhenFocused = obtainStyledAttributes.getBoolean(R.styleable.FocusIconWithTitleView_doEnterWhenfocused, false);
        this.mFrameColor = obtainStyledAttributes.getColor(R.styleable.FocusIconWithTitleView_focusedFrameColor, this.mFrameColor);
        this.mFrameWidth = obtainStyledAttributes.getInt(R.styleable.FocusIconWithTitleView_focusedFrameWidth, this.mFrameWidth);
        this.mNeedPerformClickAnim = obtainStyledAttributes.getBoolean(R.styleable.FocusIconWithTitleView_performClickAnim, false);
        this.mShadowColor = obtainStyledAttributes.getColor(R.styleable.FocusIconWithTitleView_titleTextShadowColor, this.mShadowColor);
        this.mShadowRadius = obtainStyledAttributes.getFloat(R.styleable.FocusIconWithTitleView_titleTextShadowRadius, this.mShadowRadius);
        this.mShadowDx = obtainStyledAttributes.getFloat(R.styleable.FocusIconWithTitleView_titleTextShadowDx, this.mShadowDx);
        this.mShadowDy = obtainStyledAttributes.getFloat(R.styleable.FocusIconWithTitleView_titleTextShadowDy, this.mShadowDy);
        obtainStyledAttributes.recycle();
        TextView textView = this.mItemText;
        if (textView != null) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) textView.getLayoutParams();
            layoutParams.height = this.mTitleTextHeight;
            layoutParams.width = this.mTitleTextWidth;
            layoutParams.topMargin = this.mTitleTextMarginBottom;
            layoutParams.leftMargin = this.mTitleTextMarginStart;
            layoutParams.rightMargin = this.mTitleTextMarginEnd;
            this.mItemText.setLayoutParams(layoutParams);
            this.mItemText.setTextSize(this.mTitleTextSize);
            if (this.mTitleTextToUpper) {
                this.mTitleText.toUpperCase();
            }
            this.mItemText.setText(this.mTitleText);
            this.mItemText.setTextColor(this.mTitleTextColor);
            this.mItemText.setShadowLayer(this.mShadowRadius, this.mShadowDx, this.mShadowDy, this.mShadowColor);
        }
        ImageView imageView = this.mItemImage;
        if (imageView != null) {
            RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
            layoutParams2.height = this.mIconHeight;
            layoutParams2.width = this.mIconWidth;
            layoutParams2.topMargin = this.mIconMarginTop;
            this.mItemImage.setLayoutParams(layoutParams2);
            this.mItemImage.setImageDrawable(this.mIcon);
        }
        ImageView imageView2 = this.mItemBk;
        if (imageView2 != null) {
            RelativeLayout.LayoutParams layoutParams3 = (RelativeLayout.LayoutParams) imageView2.getLayoutParams();
            layoutParams3.height = this.mImgBackgroundHeight;
            layoutParams3.width = this.mImgBackgroundWidth;
            this.mItemBk.setLayoutParams(layoutParams3);
            this.mItemBk.setBackground(this.mImgBackground);
            this.mItemBk.setForeground(this.mImgForeground);
            this.mOldDrawable = this.mItemBk.getBackground();
            this.mOldForegroundDrawable = this.mItemBk.getForeground();
        }
        this.mPaint.setAntiAlias(true);
        this.mPaint.setColor(this.mFrameColor);
        this.mPaint.setStrokeWidth((float) this.mFrameWidth);
        this.mPaint.setStyle(Paint.Style.STROKE);
    }

    public void setTiteText(String str) {
        TextView textView = this.mItemText;
        if (textView != null) {
            textView.setText(str);
        }
    }

    public void setTiteText(int i) {
        TextView textView = this.mItemText;
        if (textView != null) {
            textView.setText(i);
        }
    }

    public void setTitleColor(int i) {
        TextView textView = this.mItemText;
        if (textView != null) {
            textView.setTextColor(i);
        }
    }

    public void setTitleSize(int i) {
        TextView textView = this.mItemText;
        if (textView != null) {
            textView.setTextSize((float) i);
        }
    }

    public void setIcon(Bitmap bitmap) {
        ImageView imageView = this.mItemImage;
        if (imageView != null) {
            imageView.setImageBitmap(bitmap);
        }
    }

    public void setIcon(Drawable drawable) {
        ImageView imageView = this.mItemImage;
        if (imageView != null) {
            imageView.setImageDrawable(drawable);
        }
    }

    public void setImageBackground(Drawable drawable) {
        ImageView imageView = this.mItemBk;
        if (imageView != null) {
            imageView.setBackground(drawable);
            this.mOldDrawable = this.mItemBk.getBackground();
        }
    }

    public void setImageBackground(int i) {
        ImageView imageView = this.mItemBk;
        if (imageView != null) {
            imageView.setBackgroundResource(i);
            this.mOldDrawable = this.mItemBk.getBackground();
        }
    }

    public void setImageForeground(Drawable drawable) {
        ImageView imageView = this.mItemBk;
        if (imageView != null) {
            imageView.setForeground(drawable);
            this.mOldForegroundDrawable = this.mItemBk.getForeground();
        }
    }

    public void setImageForeground(int i) {
        setImageForeground(getResources().getDrawable(i, (Resources.Theme) null));
    }

    public void setFocusedDrawable(Drawable drawable) {
        this.mFocusedDrawable = drawable;
    }

    public void setFocusedDrawable(int i) {
        setFocusedDrawable(getResources().getDrawable(i, (Resources.Theme) null));
    }

    public void setFocusedForegroundDrawable(Drawable drawable) {
        this.mFocusedForegroundDrawable = drawable;
    }

    public void setFocusedForegroundDrawable(int i) {
        setFocusedForegroundDrawable(getResources().getDrawable(i, (Resources.Theme) null));
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        ImageView imageView = this.mItemBk;
        if (imageView != null) {
            imageView.onTouchEvent(motionEvent);
        }
        return super.onTouchEvent(motionEvent);
    }

    public void setFocusedState() {
        ImageView imageView;
        ImageView imageView2;
        this.mInFocus = true;
        if (this.mNeedDrawFocusFrame) {
            postInvalidate();
        } else if (this.mNeedDrawFocusByBackground) {
            Drawable drawable = this.mFocusedDrawable;
            if (drawable != null && (imageView2 = this.mItemBk) != null) {
                imageView2.setBackground(drawable);
            }
        } else {
            Drawable drawable2 = this.mFocusedForegroundDrawable;
            if (drawable2 != null && (imageView = this.mItemBk) != null) {
                imageView.setForeground(drawable2);
            }
        }
    }

    public void clearFocusedState() {
        this.mInFocus = false;
        if (this.mNeedDrawFocusFrame) {
            postInvalidate();
        } else if (this.mNeedDrawFocusByBackground) {
            ImageView imageView = this.mItemBk;
            if (imageView != null) {
                imageView.setBackground(this.mOldDrawable);
            }
        } else {
            ImageView imageView2 = this.mItemBk;
            if (imageView2 != null) {
                imageView2.setForeground(this.mOldForegroundDrawable);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mInFocus && this.mNeedDrawFocusFrame) {
            canvas.save();
            canvas.drawRect(0.0f, 0.0f, (float) getWidth(), (float) getHeight(), this.mPaint);
            canvas.restore();
        }
    }

    public boolean ismDoEnterWhenFocused() {
        return this.mDoEnterWhenFocused;
    }

    public boolean performClick() {
        if (this.mNeedPerformClickAnim) {
            ImageView imageView = this.mItemBk;
            if (imageView != null) {
                imageView.setPressed(true);
            }
            postDelayed(new Runnable() {
                public void run() {
                    if (FocusIconWithTitleView.this.mItemBk != null) {
                        FocusIconWithTitleView.this.mItemBk.setPressed(false);
                    }
                }
            }, 150);
        }
        return super.performClick();
    }
}
