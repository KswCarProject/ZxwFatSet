package com.szchoiceway.fatset.util;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import com.szchoiceway.fatset.R;
import java.util.List;

public class MyRecyclerAdapter2 extends RecyclerView.Adapter<MyViewHolder2> {
    private List<Drawable> list;
    private OnItemClickListener mListener;
    private int selectIndex = -1;

    public interface OnItemClickListener {
        void onItemClick(int i);
    }

    public MyRecyclerAdapter2(List<Drawable> list2) {
        this.list = list2;
    }

    public MyViewHolder2 onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder2(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_logo, viewGroup, false));
    }

    public void onBindViewHolder(MyViewHolder2 myViewHolder2, int i) {
        myViewHolder2.logo.setImageDrawable(this.list.get(i));
        if (this.selectIndex == i) {
            myViewHolder2.rootView.setSelected(true);
        } else {
            myViewHolder2.rootView.setSelected(false);
        }
        myViewHolder2.itemView.setOnClickListener(new View.OnClickListener(i) {
            public final /* synthetic */ int f$1;

            {
                this.f$1 = r2;
            }

            public final void onClick(View view) {
                MyRecyclerAdapter2.this.lambda$onBindViewHolder$0$MyRecyclerAdapter2(this.f$1, view);
            }
        });
    }

    public /* synthetic */ void lambda$onBindViewHolder$0$MyRecyclerAdapter2(int i, View view) {
        this.mListener.onItemClick(i);
    }

    public int getItemCount() {
        return this.list.size();
    }

    public void setSelectIndex(int i) {
        this.selectIndex = i;
        notifyDataSetChanged();
    }

    class MyViewHolder2 extends RecyclerView.ViewHolder {
        /* access modifiers changed from: private */
        public ImageView logo;
        /* access modifiers changed from: private */
        public View rootView;

        public MyViewHolder2(View view) {
            super(view);
            this.logo = (ImageView) view.findViewById(R.id.logo);
            this.rootView = view.findViewById(R.id.rootView);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mListener = onItemClickListener;
    }
}
