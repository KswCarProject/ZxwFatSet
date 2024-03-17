package com.szchoiceway.fatset.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import com.szchoiceway.fatset.R;
import com.szchoiceway.zxwlib.bean.Customer;
import java.util.ArrayList;

public class LogoAdapter extends RecyclerView.Adapter<LogoViewHolder> {
    private ArrayList<Drawable> drawables;
    private Context mContext;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int i);
    }

    public void setDrawables(ArrayList<Drawable> arrayList) {
        this.drawables = arrayList;
    }

    public LogoAdapter(Context context) {
        this.mContext = context;
    }

    public LogoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_logo, viewGroup, false);
        if (Customer.isSmallResolution(this.mContext)) {
            inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.small_list_logo, viewGroup, false);
        }
        return new LogoViewHolder(inflate);
    }

    public void onBindViewHolder(LogoViewHolder logoViewHolder, int i) {
        logoViewHolder.logo.setImageDrawable(this.drawables.get(i));
        logoViewHolder.itemView.setOnClickListener(new View.OnClickListener(i) {
            public final /* synthetic */ int f$1;

            {
                this.f$1 = r2;
            }

            public final void onClick(View view) {
                LogoAdapter.this.lambda$onBindViewHolder$0$LogoAdapter(this.f$1, view);
            }
        });
    }

    public /* synthetic */ void lambda$onBindViewHolder$0$LogoAdapter(int i, View view) {
        this.mListener.onItemClick(i);
    }

    public int getItemCount() {
        return this.drawables.size();
    }

    class LogoViewHolder extends RecyclerView.ViewHolder {
        /* access modifiers changed from: private */
        public ImageView logo;
        private View rootView;

        public LogoViewHolder(View view) {
            super(view);
            this.rootView = view.findViewById(R.id.rootView);
            this.logo = (ImageView) view.findViewById(R.id.logo);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mListener = onItemClickListener;
    }
}
