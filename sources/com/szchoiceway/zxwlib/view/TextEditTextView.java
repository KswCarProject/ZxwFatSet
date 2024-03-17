package com.szchoiceway.zxwlib.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import androidx.appcompat.widget.AppCompatEditText;

public class TextEditTextView extends AppCompatEditText {
    OnKeyBoardHideListener onKeyBoardHideListener;

    public interface OnKeyBoardHideListener {
        void onKeyHide(int i, KeyEvent keyEvent);
    }

    public TextEditTextView(Context context) {
        super(context);
    }

    public TextEditTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public TextEditTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public boolean onKeyPreIme(int i, KeyEvent keyEvent) {
        if (i != 4 || keyEvent.getAction() != 1) {
            return super.onKeyPreIme(i, keyEvent);
        }
        super.onKeyPreIme(i, keyEvent);
        OnKeyBoardHideListener onKeyBoardHideListener2 = this.onKeyBoardHideListener;
        if (onKeyBoardHideListener2 == null) {
            return false;
        }
        onKeyBoardHideListener2.onKeyHide(i, keyEvent);
        return false;
    }

    public void setOnKeyBoardHideListener(OnKeyBoardHideListener onKeyBoardHideListener2) {
        this.onKeyBoardHideListener = onKeyBoardHideListener2;
    }
}
