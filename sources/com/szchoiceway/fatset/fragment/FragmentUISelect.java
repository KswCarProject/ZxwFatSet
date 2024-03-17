package com.szchoiceway.fatset.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.vectordrawable.graphics.drawable.PathInterpolatorCompat;
import com.szchoiceway.eventcenter.EventUtils;
import com.szchoiceway.fatset.R;
import com.szchoiceway.fatset.SysProviderOpt;
import com.szchoiceway.fatset.util.MyRecyclerAdapter;
import com.szchoiceway.zxwlib.GyroScopeWithCompassView;
import com.szchoiceway.zxwlib.bean.Customer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentUISelect extends FragmentBase implements View.OnClickListener {
    private static final String TAG = "FragmentUISelect";
    MyRecyclerAdapter adapter;
    Button btnCancel;
    Button btnDetermine;
    TextView currentUi;
    private int mPosition = 0;
    private Toast mTip = null;
    private int m_iModeSet = 16;
    private int selectPosition = 0;
    RecyclerView uiList;
    private HashMap<Integer, String> uiMap;
    private List<String> uis;
    View viewConfirm;

    public void onAttach(Context context) {
        super.onAttach(context);
        this.m_iModeSet = this.mProvider.getRecordInteger("KESAIWEI_SYS_MODE_SELECTION", this.m_iModeSet);
        this.uis = new ArrayList();
        this.uiMap = new HashMap<>();
        getUIList();
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return Customer.isSmallResolution(getContext()) ? layoutInflater.inflate(R.layout.small_fragment_ui_select, (ViewGroup) null) : layoutInflater.inflate(R.layout.fragment_ui_select, (ViewGroup) null);
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        View findViewById = view.findViewById(R.id.viewConfirm);
        this.viewConfirm = findViewById;
        if (findViewById != null) {
            findViewById.setVisibility(8);
        }
        Button button = (Button) view.findViewById(R.id.btnDetermine);
        this.btnDetermine = button;
        if (button != null) {
            button.setOnClickListener(this);
        }
        Button button2 = (Button) view.findViewById(R.id.btnCancel);
        this.btnCancel = button2;
        if (button2 != null) {
            button2.setOnClickListener(this);
        }
        this.currentUi = (TextView) view.findViewById(R.id.currentUi);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.uiList);
        this.uiList = recyclerView;
        if (recyclerView != null) {
            if (Customer.isSmallResolution(getContext())) {
                this.adapter = new MyRecyclerAdapter(this.uis, false, (int) getResources().getDimension(R.dimen.small_font_size_content));
            } else {
                this.adapter = new MyRecyclerAdapter(this.uis, false, (int) getResources().getDimension(R.dimen.font_size_list));
            }
            this.adapter.setPosition(this.mPosition);
            if (this.currentUi != null && !this.uis.isEmpty()) {
                this.currentUi.setText(this.uis.get(this.mPosition));
            }
            this.adapter.setOnItemClickListener(new MyRecyclerAdapter.OnItemClickListener() {
                public final void onItemClick(int i) {
                    FragmentUISelect.this.lambda$onViewCreated$0$FragmentUISelect(i);
                }
            });
            this.uiList.setLayoutManager(new LinearLayoutManager(getContext()));
            this.uiList.setAdapter(this.adapter);
            this.uiList.addOnScrollListener(new RecyclerView.OnScrollListener() {
                public void onScrollStateChanged(RecyclerView recyclerView, int i) {
                    super.onScrollStateChanged(recyclerView, i);
                    if (i == 0) {
                        FragmentUISelect.this.adapter.notifyDataSetChanged();
                    }
                }
            });
        }
    }

    public /* synthetic */ void lambda$onViewCreated$0$FragmentUISelect(int i) {
        this.selectPosition = i;
        this.viewConfirm.setVisibility(0);
    }

    public void onClick(View view) {
        int i;
        int id = view.getId();
        if (id == R.id.btnCancel) {
            this.viewConfirm.setVisibility(8);
        } else if (id == R.id.btnDetermine) {
            int i2 = this.selectPosition;
            this.mPosition = i2;
            String str = this.uis.get(i2);
            Log.d(TAG, "uiName = " + str);
            for (Map.Entry next : this.uiMap.entrySet()) {
                Log.d(TAG, "me.getValue() " + ((String) next.getValue()) + ", me.getKey() = " + next.getKey());
                if (str.equals(next.getValue())) {
                    this.m_iModeSet = ((Integer) next.getKey()).intValue();
                }
            }
            Log.d(TAG, "change m_iModeSet = " + this.m_iModeSet + ", client = " + this.client);
            if (!"ALS_6208".equals(this.client) && ((i = this.m_iModeSet) == 19 || i == 20)) {
                showTipString(R.string.lb_ui_settings_error_select, PathInterpolatorCompat.MAX_NUM_POINTS);
            } else if (!"GS".equals(this.client) && this.m_iModeSet == 26) {
                showTipString(R.string.lb_ui_settings_error_select, PathInterpolatorCompat.MAX_NUM_POINTS);
            } else if (!"LHX2306".equals(this.client) && this.m_iModeSet == 29) {
                showTipString(R.string.lb_ui_settings_error_select, PathInterpolatorCompat.MAX_NUM_POINTS);
            } else if (!"LS2682".equals(this.client) && this.m_iModeSet == 39) {
                showTipString(R.string.lb_ui_settings_error_select, PathInterpolatorCompat.MAX_NUM_POINTS);
            } else if ("CK".equals(this.client) || this.m_iModeSet != 40) {
                this.adapter.setPosition(this.mPosition);
                TextView textView = this.currentUi;
                if (textView != null) {
                    textView.setText(this.uis.get(this.mPosition));
                }
                this.mProvider.updateRecord("KESAIWEI_SYS_MODE_SELECTION", this.m_iModeSet + "");
            } else {
                showTipString(R.string.lb_ui_settings_error_select, PathInterpolatorCompat.MAX_NUM_POINTS);
            }
            this.viewConfirm.setVisibility(8);
        }
    }

    private void getUIList() {
        try {
            if (getContext() != null) {
                SharedPreferences sharedPreferences = getContext().createPackageContext("com.szchoiceway.eventcenter", 2).getSharedPreferences(EventUtils.ZXW_DATABASE_UI_FILENAME, 4);
                int recordInteger = this.mProvider.getRecordInteger(SysProviderOpt.KSW_DATA_PRODUCT_INDEX, 0);
                Log.d(TAG, "UIMap = " + sharedPreferences.getAll());
                for (String next : sharedPreferences.getAll().keySet()) {
                    if (recordInteger == 1) {
                        try {
                            int parseInt = Integer.parseInt(next);
                            if (parseInt == 18 || parseInt == 21 || parseInt == 22) {
                                String obj = sharedPreferences.getAll().get(next).toString();
                                this.uis.add(obj);
                                this.uiMap.put(Integer.valueOf(Integer.parseInt(next)), obj);
                            }
                        } catch (ClassCastException e) {
                            e.printStackTrace();
                        }
                    } else if (recordInteger == 2) {
                        try {
                            if (Integer.parseInt(next) == 17) {
                                String obj2 = sharedPreferences.getAll().get(next).toString();
                                this.uis.add(obj2);
                                this.uiMap.put(Integer.valueOf(Integer.parseInt(next)), obj2);
                            }
                        } catch (ClassCastException e2) {
                            e2.printStackTrace();
                        }
                    } else {
                        String obj3 = sharedPreferences.getAll().get(next).toString();
                        this.uis.add(obj3);
                        this.uiMap.put(Integer.valueOf(Integer.parseInt(next)), obj3);
                    }
                }
                for (int i = 0; i < this.uis.size(); i++) {
                    if (this.uiMap.containsKey(Integer.valueOf(this.m_iModeSet)) && this.uiMap.get(Integer.valueOf(this.m_iModeSet)).equals(this.uis.get(i))) {
                        this.mPosition = i;
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e3) {
            e3.printStackTrace();
        }
    }

    private void showTipString(int i, int i2) {
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
}
