package com.szchoiceway.fatset.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.szchoiceway.fatset.R;
import com.szchoiceway.fatset.ZXWFatApp;
import com.szchoiceway.zxwlib.bean.Customer;
import java.util.ArrayList;

public class FragmentLogo extends FragmentBase implements View.OnClickListener {
    Button btnPage1;
    Button btnPage2;
    Button btnPage3;
    private ArrayList<Button> buttons;
    private Fragment fragmentCurrent;
    private Fragment fragmentPage1;
    private Fragment fragmentPage2;
    private Fragment fragmentPage3;
    View linearLayout;
    private ZXWFatApp mApp;
    private int position;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.mApp = (ZXWFatApp) getActivity().getApplication();
        View inflate = layoutInflater.inflate(R.layout.fragment_logo, (ViewGroup) null);
        if (Customer.isSmallResolution(getContext())) {
            inflate = layoutInflater.inflate(R.layout.small_fragment_logo, (ViewGroup) null);
        }
        this.fragmentPage1 = new FragmentLogoPage1();
        this.fragmentPage2 = new FragmentLogoPage2();
        this.fragmentPage3 = new FragmentLogoPage3();
        return inflate;
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.buttons = new ArrayList<>();
        Button button = (Button) view.findViewById(R.id.btnPage1);
        this.btnPage1 = button;
        if (button != null) {
            button.setOnClickListener(this);
            this.buttons.add(this.btnPage1);
        }
        Button button2 = (Button) view.findViewById(R.id.btnPage2);
        this.btnPage2 = button2;
        if (button2 != null) {
            button2.setOnClickListener(this);
            this.buttons.add(this.btnPage2);
        }
        Button button3 = (Button) view.findViewById(R.id.btnPage3);
        this.btnPage3 = button3;
        if (button3 != null) {
            button3.setOnClickListener(this);
            this.buttons.add(this.btnPage3);
        }
        View findViewById = view.findViewById(R.id.layoutLogo);
        this.linearLayout = findViewById;
        if (findViewById != null) {
            FragmentTransaction beginTransaction = getChildFragmentManager().beginTransaction();
            beginTransaction.add((int) R.id.layoutLogo, this.fragmentPage1);
            beginTransaction.show(this.fragmentPage1);
            beginTransaction.commit();
            this.fragmentCurrent = this.fragmentPage1;
            this.position = 0;
            switchButtonStatus();
        }
    }

    public void onClick(View view) {
        if (!this.mApp.getUpdatingLogo()) {
            switch (view.getId()) {
                case R.id.btnPage1:
                    Fragment fragment = this.fragmentCurrent;
                    Fragment fragment2 = this.fragmentPage1;
                    if (fragment != fragment2) {
                        switchFragment(fragment2);
                        this.position = 0;
                        switchButtonStatus();
                        this.fragmentCurrent = this.fragmentPage1;
                        return;
                    }
                    return;
                case R.id.btnPage2:
                    Fragment fragment3 = this.fragmentCurrent;
                    Fragment fragment4 = this.fragmentPage2;
                    if (fragment3 != fragment4) {
                        switchFragment(fragment4);
                        this.position = 1;
                        switchButtonStatus();
                        this.fragmentCurrent = this.fragmentPage2;
                        return;
                    }
                    return;
                case R.id.btnPage3:
                    Fragment fragment5 = this.fragmentCurrent;
                    Fragment fragment6 = this.fragmentPage3;
                    if (fragment5 != fragment6) {
                        switchFragment(fragment6);
                        this.position = 2;
                        switchButtonStatus();
                        this.fragmentCurrent = this.fragmentPage3;
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    private void switchFragment(Fragment fragment) {
        FragmentTransaction beginTransaction = getChildFragmentManager().beginTransaction();
        if (!fragment.isAdded()) {
            beginTransaction.add((int) R.id.layoutLogo, fragment);
        }
        beginTransaction.replace(R.id.layoutLogo, fragment);
        beginTransaction.commit();
    }

    private void switchButtonStatus() {
        for (int i = 0; i < this.buttons.size(); i++) {
            if (i == this.position) {
                this.buttons.get(i).setBackground(getResources().getDrawable(R.drawable.button_pressed_round));
            } else {
                this.buttons.get(i).setBackground(getResources().getDrawable(R.drawable.button_normal_round));
            }
        }
    }
}
