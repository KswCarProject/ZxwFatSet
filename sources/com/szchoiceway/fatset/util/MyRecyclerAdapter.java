package com.szchoiceway.fatset.util;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.szchoiceway.fatset.R;
import com.szchoiceway.fatset.util.MyRecyclerAdapter;
import com.szchoiceway.zxwlib.bean.Customer;
import java.util.List;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private Context mContext;
    /* access modifiers changed from: private */
    public boolean mHasLine = false;
    private List<String> mList;
    private OnItemClickListener mListener;
    private int mPosition = 0;
    /* access modifiers changed from: private */
    public int mTextSize = 43;

    public interface OnItemClickListener {
        void onItemClick(int i);
    }

    public MyRecyclerAdapter(List<String> list, boolean z, int i) {
        this.mList = list;
        this.mHasLine = z;
        if (i != 0) {
            this.mTextSize = i;
        }
    }

    public void updateList(List<String> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view;
        if (Customer.isSmallResolution(this.mContext)) {
            if (!this.mHasLine) {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.small_left_list_item_small, viewGroup, false);
            } else {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.small_left_list_item, viewGroup, false);
            }
        } else if (!this.mHasLine) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.left_list_item_small, viewGroup, false);
        } else {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.left_list_item, viewGroup, false);
        }
        return new MyViewHolder(view);
    }

    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        myViewHolder.title.setText(this.mList.get(i));
        int i2 = this.mPosition;
        if (i2 < 0 || i2 != i) {
            myViewHolder.title.setTextColor(Color.parseColor("#ffffffff"));
        } else {
            myViewHolder.title.setTextColor(Color.parseColor("#FF3300"));
        }
        myViewHolder.background.setVisibility(8);
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener(i) {
            public final /* synthetic */ int f$1;

            {
                this.f$1 = r2;
            }

            public final void onClick(View view) {
                MyRecyclerAdapter.this.lambda$onBindViewHolder$0$MyRecyclerAdapter(this.f$1, view);
            }
        });
    }

    public /* synthetic */ void lambda$onBindViewHolder$0$MyRecyclerAdapter(int i, View view) {
        this.mListener.onItemClick(i);
    }

    public int getItemCount() {
        List<String> list = this.mList;
        if (list == null || list.size() == 0) {
            return 0;
        }
        return this.mList.size();
    }

    public void setPosition(int i) {
        this.mPosition = i;
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        /* access modifiers changed from: private */
        public ImageView background;
        private ImageView line;
        /* access modifiers changed from: private */
        public TextView title;

        public MyViewHolder(View view) {
            super(view);
            TextView textView = (TextView) view.findViewById(R.id.tv);
            this.title = textView;
            textView.setTextSize(0, (float) MyRecyclerAdapter.this.mTextSize);
            this.background = (ImageView) view.findViewById(R.id.background);
            this.line = (ImageView) view.findViewById(R.id.line);
            if (MyRecyclerAdapter.this.mHasLine) {
                this.line.setVisibility(0);
            } else {
                this.line.setVisibility(8);
            }
            view.setOnTouchListener(new View.OnTouchListener() {
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    return MyRecyclerAdapter.MyViewHolder.this.lambda$new$0$MyRecyclerAdapter$MyViewHolder(view, motionEvent);
                }
            });
        }

        public /* synthetic */ boolean lambda$new$0$MyRecyclerAdapter$MyViewHolder(View view, MotionEvent motionEvent) {
            ImageView imageView;
            int action = motionEvent.getAction();
            if (action == 0) {
                ImageView imageView2 = this.background;
                if (imageView2 != null) {
                    imageView2.setVisibility(0);
                }
            } else if (action == 1 && (imageView = this.background) != null) {
                imageView.setVisibility(8);
            }
            return false;
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mListener = onItemClickListener;
    }
}
