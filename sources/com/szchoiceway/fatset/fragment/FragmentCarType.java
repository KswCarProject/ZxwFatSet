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
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.szchoiceway.eventcenter.EventUtils;
import com.szchoiceway.fatset.R;
import com.szchoiceway.fatset.SysProviderOpt;
import com.szchoiceway.fatset.util.MyRecyclerAdapter;
import com.szchoiceway.zxwlib.bean.Customer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class FragmentCarType extends FragmentBase implements View.OnClickListener {
    private static final String TAG = "FragmentCarType";
    MyRecyclerAdapter adapter;
    Button btnCancel;
    Button btnDetermine;
    private int carTypeId = 0;
    RecyclerView carTypeListView;
    private HashMap<Integer, String> carTypeMap = new HashMap<>();
    private String carTypeName = "";
    private List<String> carTypeNameList = new ArrayList();
    TextView currentCarType;
    private int mPosition = -1;
    private int selectPosition = -1;
    View viewConfirm;

    public void onAttach(Context context) {
        Log.d(TAG, "onAttach");
        super.onAttach(context);
        this.carTypeId = this.mProvider.getRecordInteger(SysProviderOpt.KESAIWEI_RECORD_CAR_TYPE, this.carTypeId);
        getCarTypeList();
        Log.d(TAG, "carTypeId = " + this.carTypeId + ", mPosition = " + this.mPosition);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return Customer.isSmallResolution(getContext()) ? layoutInflater.inflate(R.layout.small_fragment_car_type, (ViewGroup) null) : layoutInflater.inflate(R.layout.fragment_car_type, (ViewGroup) null);
    }

    public void onViewCreated(View view, Bundle bundle) {
        Log.d(TAG, "onViewCreated");
        super.onViewCreated(view, bundle);
        this.currentCarType = (TextView) view.findViewById(R.id.currentCarType);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.carTypeList);
        this.carTypeListView = recyclerView;
        if (recyclerView != null) {
            if (Customer.isSmallResolution(getContext())) {
                this.adapter = new MyRecyclerAdapter(this.carTypeNameList, false, (int) getResources().getDimension(R.dimen.small_font_size_content));
            } else {
                this.adapter = new MyRecyclerAdapter(this.carTypeNameList, false, (int) getResources().getDimension(R.dimen.font_size_list));
            }
            Log.d(TAG, "setPosition = " + this.mPosition);
            this.adapter.setPosition(this.mPosition);
            TextView textView = this.currentCarType;
            if (textView != null) {
                textView.setText(this.carTypeName);
            }
            this.adapter.setOnItemClickListener(new MyRecyclerAdapter.OnItemClickListener() {
                public final void onItemClick(int i) {
                    FragmentCarType.this.lambda$onViewCreated$0$FragmentCarType(i);
                }
            });
            this.carTypeListView.setLayoutManager(new LinearLayoutManager(getContext()));
            this.carTypeListView.setAdapter(this.adapter);
            this.carTypeListView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                public void onScrollStateChanged(RecyclerView recyclerView, int i) {
                    super.onScrollStateChanged(recyclerView, i);
                    if (i == 0) {
                        FragmentCarType.this.adapter.notifyDataSetChanged();
                    }
                }
            });
        }
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
    }

    public /* synthetic */ void lambda$onViewCreated$0$FragmentCarType(int i) {
        this.selectPosition = i;
        this.viewConfirm.setVisibility(0);
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btnCancel) {
            this.viewConfirm.setVisibility(8);
        } else if (id == R.id.btnDetermine) {
            int i = this.selectPosition;
            this.mPosition = i;
            this.adapter.setPosition(i);
            String str = this.carTypeNameList.get(this.mPosition);
            this.carTypeName = str;
            TextView textView = this.currentCarType;
            if (textView != null) {
                textView.setText(str);
            }
            for (Integer intValue : this.carTypeMap.keySet()) {
                int intValue2 = intValue.intValue();
                if (this.carTypeMap.get(Integer.valueOf(intValue2)).equals(this.carTypeName)) {
                    this.carTypeId = intValue2;
                }
            }
            Log.d(TAG, "carTypeId = " + this.carTypeId + ", carTypeName = " + this.carTypeName);
            this.mProvider.updateRecord(SysProviderOpt.KESAIWEI_RECORD_CAR_TYPE, this.carTypeId + "");
            kesaiwei_sendBroadcast(10);
            this.viewConfirm.setVisibility(8);
        }
    }

    private void getCarTypeList() {
        try {
            if (getContext() != null) {
                SharedPreferences sharedPreferences = getContext().createPackageContext("com.szchoiceway.eventcenter", 2).getSharedPreferences(EventUtils.ZXW_DATABASE_CARTYPE_FILENAME, 4);
                Log.d(TAG, "carTypeMap : " + sharedPreferences.getAll());
                for (String next : sharedPreferences.getAll().keySet()) {
                    this.carTypeNameList.add(sharedPreferences.getAll().get(next) + "");
                    Log.d(TAG, "key = " + next + ", value = " + sharedPreferences.getAll().get(next));
                    this.carTypeMap.put(Integer.valueOf(Integer.parseInt(next)), sharedPreferences.getAll().get(next) + "");
                }
                this.carTypeNameList.sort(new carTypeNameComparator());
                Log.d(TAG, "carTypeMap = " + this.carTypeMap + ", carTypeNameList = " + this.carTypeNameList);
                this.carTypeName = this.carTypeMap.get(Integer.valueOf(this.carTypeId));
                Log.d(TAG, "carTypeId = " + this.carTypeId + ", carTypeName = " + this.carTypeName);
                for (int i = 0; i < this.carTypeNameList.size(); i++) {
                    if (this.carTypeNameList.get(i).equals(this.carTypeName)) {
                        this.mPosition = i;
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    class carTypeNameComparator implements Comparator<String> {
        carTypeNameComparator() {
        }

        public int compare(String str, String str2) {
            return Integer.parseInt(FragmentCarType.this.getNameIndex(str)) - Integer.parseInt(FragmentCarType.this.getNameIndex(str2));
        }
    }

    /* access modifiers changed from: private */
    public String getNameIndex(String str) {
        return str.split("]")[0].substring(1);
    }
}
