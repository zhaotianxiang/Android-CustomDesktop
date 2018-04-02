package com.ustc.ztx.desktopapplition;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * The Main Activity.
 */
public class MainActivity extends Activity {
    private static final String TAG ="MainActivity" ;
    private List<ResolveInfo> mApps;
    private ViewPager mPager;
    private List<View> mPagerList;
    private List<AppInfo> mDatas;
    private LinearLayout mLlDot;
    private LayoutInflater inflater;
    /**
     * 总的页数
     */
    private int pageCount;
    /**
     * GridView每列值设置为3，这里设置每页的值为 9
     * 完成3*3布局的要求
     */
    private int pageSize = 9;
    /**
     * 记录当前显示的是第几页
     */
    private int curIndex = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        loadApps();
        //初始化数据源
        initDatas();
        //更新视图
        updateView();
        //设置底部提示圆点
        setOvalLayout();
    }
    @Override
    public void onRestart() {
        super.onRestart();

        //排序应用数据，以更新视图
        sortAppInfosByDate(mDatas);
        //更新视图
        updateView();
        //设置底部提示圆点
        setOvalLayout();
    }
    private void updateView() {
        inflater = LayoutInflater.from(this);
        for (int i = 0; i < pageCount; i++) {
            GridView gridView = (GridView) inflater.inflate(R.layout.gridview, mPager, false);
            GridViewAdapter gridViewAdapter = new GridViewAdapter(this, mDatas, i, pageSize);
            gridView.setAdapter(gridViewAdapter);
            //更新视图
            gridViewAdapter.notifyDataSetChanged();
            //设置监听事件
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //这里要精确计算点击的位置和启动的应用,mApps也要排序
                    int new_pos = position  + curIndex * pageSize;
                    AppInfo appInfo = mDatas.get(new_pos);
                    //该应用的包名
                    String pkg = appInfo.getPackageName();
                    //应用的主activity类
                    String cls = appInfo.getResolveInfo().activityInfo.name;
                    ComponentName componet = new ComponentName(pkg, cls);
                    //启动应用程序
                    try{
                        Intent intent = new Intent();
                        intent.setComponent(componet);
                        startActivity(intent);
                    }catch (Exception e){
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Error in start Application!", Toast.LENGTH_SHORT).show();
                    }
                    //以下主要完成记录时间，然后对应用根据启动时间来排序的功能
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// HH:mm:ss
                    //获取系统启动的时间
                    Date date = new Date(System.currentTimeMillis());
                    //将时间字符串化
                    String currentDate = simpleDateFormat.format(date);
                    //更新时间
                    mDatas.get(new_pos).setDate(currentDate);
                }
            });
            mPagerList.add(gridView);
            ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(mPagerList);
            //设置适配器
            mPager.setAdapter(viewPagerAdapter);
            //viewPagerAdapter.notifyDataSetChanged();
        }
    }
     //使用原点来标识主界面的当前页
    public void setOvalLayout() {
        mLlDot.removeAllViewsInLayout();
        for (int i = 0; i < pageCount; i++) {
            mLlDot.addView(inflater.inflate(R.layout.dot, null));
        }
        // 默认显示第一页
        mLlDot.getChildAt(0).findViewById(R.id.v_dot)
                .setBackgroundResource(R.drawable.dot_selected);
        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageSelected(int position) {
                // 取消圆点
                mLlDot.getChildAt(curIndex)
                        .findViewById(R.id.v_dot)
                        .setBackgroundResource(R.drawable.dot_normal);
                // 选中原点
                mLlDot.getChildAt(position)
                        .findViewById(R.id.v_dot)
                        .setBackgroundResource(R.drawable.dot_selected);
                curIndex = position;
            }

            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }
    //初始化数据
    private void initDatas() {
        mPager = (ViewPager) findViewById(R.id.viewpager);
        mLlDot = (LinearLayout) findViewById(R.id.ll_dot);
        mDatas = getAppInfos();
        //总的页数=总数/每页数量，并取整
        pageCount = (int) Math.ceil(mDatas.size() * 1.0 / pageSize);
        mPagerList = new ArrayList<View>();
    }
    //对List中的应用程序信息按时间排序
    private List<AppInfo> sortAppInfosByDate(List<AppInfo> appInfos) {
        Collections.sort(appInfos, new Comparator<AppInfo>() {
            @Override
            //以下实现降序排序，类AppInfo已经实现了排序接口（Comparable<T>）
            // 按照AppInfo中的时间（date）大小进行降序排序
            public int compare(AppInfo o1, AppInfo o2) {
                if(o1.compareTo(o2) < 0){
                    return +1;
                }
                if(o1.compareTo(o2) > 0){
                    return -1;
                }
                return 0;
            }
        });
        return appInfos;
    }

    private void loadApps() {
        //使用包管理器来加载本地环境下所有的已安装的应用，保存ResolveInfo信息到mApps中
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        mApps = getPackageManager().queryIntentActivities(mainIntent, 0);
    }

    /**
     * Get app infos list.
     * @return the list of application information
     */
    public List<AppInfo> getAppInfos(){
        List<AppInfo> appInfos = new ArrayList<AppInfo>();
        appInfos = new ArrayList<AppInfo>();
        for(ResolveInfo info:mApps){
            PackageManager pm = this.getPackageManager();
            //该应用的包名 packgeName
            String packgeName = info.activityInfo.packageName;
            //应用程序名称 appName
            String appName = info.loadLabel(pm).toString();
            //应用程序图像 drawable
            Drawable drawable = info.activityInfo.applicationInfo.loadIcon(getPackageManager());
            //获取系统的当前时间 date
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// HH:mm:ss
            Date date = new Date(System.currentTimeMillis());
            String currentDate = simpleDateFormat.format(date);
            AppInfo appInfo = new AppInfo(appName,packgeName,drawable,currentDate,info);
            appInfos.add(appInfo);
        }
        return appInfos;
    }
}
