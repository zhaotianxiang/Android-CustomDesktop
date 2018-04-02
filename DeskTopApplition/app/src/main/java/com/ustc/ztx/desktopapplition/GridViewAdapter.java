package com.ustc.ztx.desktopapplition;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

/**
 * Class GridViewAdapter
 *
 * @author ztx
 * @date 2018 -04-01
 */
public class GridViewAdapter extends BaseAdapter {
    private List<AppInfo> mDatas;
    private LayoutInflater inflater;
    private int curIndex;//当前是第几页
    private int pageSize;//每页item的大小

    /**
     * Instantiates a new Grid view adapter.
     *
     * @param context  the context
     * @param mDatas   the m datas
     * @param curIndex the cur index
     * @param pageSize the page size
     */
    public GridViewAdapter(Context context, List<AppInfo> mDatas, int curIndex, int pageSize) {
        inflater = LayoutInflater.from(context);
        this.mDatas = mDatas;
        this.curIndex = curIndex;
        this.pageSize = pageSize;
    }

    /**
     * 先判断数据集的大小是否足够显示满本页,如果够，则直接返回每一页显示的最大条目个数pageSize.
     * 如果不够充满页面，则显示其余的几项。也就是最后一页的显示。
     */
    @Override
    public int getCount() {
        return mDatas.size() > (curIndex + 1) * pageSize ? pageSize : (mDatas.size() - curIndex * pageSize);

    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position + curIndex * pageSize);
    }

    @Override
    public long getItemId(int position) {
        return position + curIndex * pageSize;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_gridview, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tv = (TextView) convertView.findViewById(R.id.textView);
            viewHolder.iv = (ImageView) convertView.findViewById(R.id.imageView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        /**
         * 在给View绑定显示的数据时，计算正确的position = position + curIndex * pageSize
         */
        int pos = position + curIndex * pageSize;
        viewHolder.tv.setText(mDatas.get(pos).appName);
        viewHolder.iv.setImageDrawable(mDatas.get(pos).drawable);
        return convertView;
    }

    /**
     * The calss of ViewHolder.
     */
    class ViewHolder {
        private TextView tv;
        private ImageView iv;
    }
}