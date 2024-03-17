package com.szchoiceway.fatset.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.szchoiceway.fatset.R;

public class HorizontalListViewAdapter extends BaseAdapter {
    Bitmap iconBitmap;
    private Context mContext;
    private int[] mIconIDs;
    private LayoutInflater mInflater;
    private String[] mTitles;
    private int selectIndex = -1;

    public long getItemId(int i) {
        return (long) i;
    }

    public HorizontalListViewAdapter(Context context, String[] strArr, int[] iArr) {
        this.mContext = context;
        this.mIconIDs = iArr;
        this.mTitles = strArr;
        this.mInflater = (LayoutInflater) context.getSystemService("layout_inflater");
    }

    public int getCount() {
        return this.mIconIDs.length;
    }

    public Object getItem(int i) {
        return Integer.valueOf(i);
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            ViewHolder viewHolder2 = new ViewHolder();
            View inflate = this.mInflater.inflate(R.layout.horizontal_list_item, (ViewGroup) null);
            ImageView unused = viewHolder2.mImage = (ImageView) inflate.findViewById(R.id.img_list_item);
            TextView unused2 = viewHolder2.mTitle = (TextView) inflate.findViewById(R.id.text_list_item);
            inflate.setTag(viewHolder2);
            View view2 = inflate;
            viewHolder = viewHolder2;
            view = view2;
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        int i2 = this.selectIndex;
        if (i2 < 0) {
            view.setSelected(false);
        } else if (i == i2) {
            view.setSelected(true);
        } else {
            view.setSelected(false);
        }
        viewHolder.mTitle.setText(this.mTitles[i]);
        this.iconBitmap = getPropThumnail(this.mIconIDs[i]);
        viewHolder.mImage.setImageBitmap(this.iconBitmap);
        return view;
    }

    private static class ViewHolder {
        /* access modifiers changed from: private */
        public ImageView mImage;
        /* access modifiers changed from: private */
        public TextView mTitle;

        private ViewHolder() {
        }
    }

    private Bitmap getPropThumnail(int i) {
        return ThumbnailUtils.extractThumbnail(BitmapUtil.drawableToBitmap(this.mContext.getResources().getDrawable(i)), this.mContext.getResources().getDimensionPixelOffset(R.dimen.thumnail_default_width), this.mContext.getResources().getDimensionPixelSize(R.dimen.thumnail_default_height));
    }

    public void setSelectIndex(int i) {
        this.selectIndex = i;
    }
}
