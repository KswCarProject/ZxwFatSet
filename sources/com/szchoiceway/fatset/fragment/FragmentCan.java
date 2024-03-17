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

public class FragmentCan extends FragmentBase implements View.OnClickListener {
    private static final String TAG = "FragmentCan";
    MyRecyclerAdapter adapter;
    Button btnCancel;
    Button btnDetermine;
    private List<String> canIdList;
    RecyclerView canList;
    private HashMap<Integer, String> canMap = new HashMap<>();
    private List<String> canNameList;
    TextView currentCan;
    private int mCanId = 0;
    private String mCanName = "";
    private int mPosition = -1;
    private int selectPosition = -1;
    View viewConfirm;

    public void onAttach(Context context) {
        super.onAttach(context);
        this.mCanId = this.mProvider.getRecordInteger(SysProviderOpt.KSW_AGREEMENT_SELECT_INDEX, this.mCanId);
        getCanList();
        Log.d(TAG, "mCanId = " + this.mCanId + ", mPosition = " + this.mPosition);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return Customer.isSmallResolution(getContext()) ? layoutInflater.inflate(R.layout.small_fragment_can, (ViewGroup) null) : layoutInflater.inflate(R.layout.fragment_can, (ViewGroup) null);
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
        this.currentCan = (TextView) view.findViewById(R.id.currentCan);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.canList);
        this.canList = recyclerView;
        if (recyclerView != null) {
            if (Customer.isSmallResolution(getContext())) {
                this.adapter = new MyRecyclerAdapter(this.canNameList, false, (int) getResources().getDimension(R.dimen.small_font_size_content));
            } else {
                this.adapter = new MyRecyclerAdapter(this.canNameList, false, (int) getResources().getDimension(R.dimen.font_size_list));
            }
            this.adapter.setPosition(this.mPosition);
            TextView textView = this.currentCan;
            if (textView != null) {
                textView.setText(this.mCanName);
            }
            this.adapter.setOnItemClickListener(new MyRecyclerAdapter.OnItemClickListener() {
                public final void onItemClick(int i) {
                    FragmentCan.this.lambda$onViewCreated$0$FragmentCan(i);
                }
            });
            this.canList.setLayoutManager(new LinearLayoutManager(getContext()));
            this.canList.setAdapter(this.adapter);
            this.canList.addOnScrollListener(new RecyclerView.OnScrollListener() {
                public void onScrollStateChanged(RecyclerView recyclerView, int i) {
                    super.onScrollStateChanged(recyclerView, i);
                    if (i == 0) {
                        FragmentCan.this.adapter.notifyDataSetChanged();
                    }
                }
            });
        }
    }

    public /* synthetic */ void lambda$onViewCreated$0$FragmentCan(int i) {
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
            this.mCanName = this.canNameList.get(this.mPosition);
            TextView textView = this.currentCan;
            if (textView != null) {
                textView.setText(this.canNameList.get(this.mPosition));
            }
            for (Integer intValue : this.canMap.keySet()) {
                int intValue2 = intValue.intValue();
                if (this.canMap.get(Integer.valueOf(intValue2)).equals(this.mCanName)) {
                    this.mCanId = intValue2;
                }
            }
            Log.d(TAG, "mCanId = " + this.mCanId + ", mCanName" + this.mCanName);
            this.mProvider.updateRecord(SysProviderOpt.KSW_AGREEMENT_SELECT_INDEX, this.mCanId + "");
            kesaiwei_sendBroadcast(29);
            this.viewConfirm.setVisibility(8);
        }
    }

    private void getCanList() {
        try {
            if (getContext() != null) {
                this.canIdList = new ArrayList();
                this.canNameList = new ArrayList();
                SharedPreferences sharedPreferences = getContext().createPackageContext("com.szchoiceway.eventcenter", 2).getSharedPreferences(EventUtils.ZXW_DATABASE_CAN_FILENAME, 4);
                this.canIdList.addAll(sharedPreferences.getAll().keySet());
                this.canIdList.sort(new CarTypeIdComparator());
                for (String next : this.canIdList) {
                    this.canNameList.add(sharedPreferences.getAll().get(next) + "");
                    this.canMap.put(Integer.valueOf(Integer.parseInt(next)), sharedPreferences.getAll().get(next) + "");
                }
                Log.d(TAG, "canMap = " + this.canMap);
                this.mCanName = this.canMap.get(Integer.valueOf(this.mCanId));
                Log.d(TAG, "mCanId = " + this.mCanId + ", mCanName = " + this.mCanName);
                for (int i = 0; i < this.canNameList.size(); i++) {
                    if (this.canNameList.get(i).equals(this.mCanName)) {
                        this.mPosition = i;
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    static class CarTypeIdComparator implements Comparator<String> {
        CarTypeIdComparator() {
        }

        public int compare(String str, String str2) {
            try {
                return Integer.parseInt(str) - Integer.parseInt(str2);
            } catch (Exception unused) {
                return 1;
            }
        }
    }
}
