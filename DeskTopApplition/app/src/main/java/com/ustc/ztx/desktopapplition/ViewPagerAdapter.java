package com.ustc.ztx.desktopapplition;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Class ViewPagerAdapter
 * @author ztx
 * @date 2018-04-01
 */
public class ViewPagerAdapter extends PagerAdapter {
    private List<View> mViewList;

    /**
     * Instantiates a new View pager adapter.
     *
     * @param mViewList the my  view list
     */
    public ViewPagerAdapter(List<View> mViewList) {
        this.mViewList = mViewList;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViewList.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mViewList.get(position));
        return (mViewList.get(position));
    }

    @Override
    public int getCount() {
        if (mViewList == null)
            return 0;
        return mViewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}