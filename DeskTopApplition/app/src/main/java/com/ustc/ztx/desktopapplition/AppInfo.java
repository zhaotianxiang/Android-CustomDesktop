package com.ustc.ztx.desktopapplition;

import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;


/**
 * Class AppInfo
 * @author ztx
 * @date 2018-04-01
 */
public class AppInfo implements Comparable<AppInfo>{
    /**
     * The Application name installed in android.
     */
    String appName;
    /**
     * The installed Application Package name.
     */
    String packageName;
    /**
     * The Drawable.
     */
    Drawable drawable;
    /**
     * The Date of start application.
     */
    String date;
    /**
     * The Resolve info.
     */
    ResolveInfo resolveInfo;

    /**
     * Instantiates a new App info.
     */
    public AppInfo(){}

    /**
     * Instantiates a new App info.
     *
     * @param name       the name
     * @param packgeName the packge name
     * @param drawable   the drawable
     * @param date       the date
     * @param info       the info
     */
    public AppInfo(String name, String packgeName, Drawable drawable, String date, ResolveInfo info){
        this.appName = name;
        this.packageName = packgeName;
        this.drawable = drawable;
        this.date = date;
        this.resolveInfo = info;
    }

    /**
     * Instantiates a new App info.
     *
     * @param appName the app name
     * @param drawabl the drawabl
     */
    public AppInfo(String appName, Drawable drawabl){
        this.appName = appName;
        this.drawable = drawabl;
    }

    /**
     * Instantiates a new App info.
     *
     * @param appName     the app name
     * @param packageName the package name
     */
    public AppInfo(String appName, String packageName){
        this.appName = appName;
        this.packageName = packageName;
    }

    /**
     * Instantiates a new App info.
     *
     * @param appName     the app name
     * @param packageName the package name
     * @param drawable    the drawable
     */
    public AppInfo(String appName,String packageName, Drawable drawable){
        this.appName = appName;
        this.packageName = packageName;
        this.drawable = drawable;
    }

    /**
     * Instantiates a new App info.
     *
     * @param appName     the app name
     * @param packageName the package name
     * @param drawable    the drawable
     * @param date        the date
     */
    public AppInfo(String appName,String packageName, Drawable drawable,String date){
        this.appName = appName;
        this.packageName = packageName;
        this.drawable = drawable;
        this.date = date;
    }


    /**
     * Gets app name.
     *
     * @return the app name
     */
    public String getAppName() {
        if(null == appName)
            return "";
        else
            return appName;
    }

    /**
     * Sets app name.
     *
     * @param appName the app name
     */
    public void setAppName(String appName) {
        this.appName = appName;
    }

    /**
     * Gets package name.
     *
     * @return the package name
     */
    public String getPackageName() {
        if(null == packageName)
            return "";
        else
            return packageName;
    }

    /**
     * Sets package name.
     *
     * @param packageName the package name
     */
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    /**
     * Gets drawable.
     *
     * @return the drawable
     */
    public Drawable getDrawable() {
        return drawable;
    }

    /**
     * Sets drawable.
     *
     * @param drawable the drawable
     */
    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    /**
     * Gets date.
     *
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets date.
     *
     * @param date the date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Gets resolve info.
     *
     * @return the resolve info
     */
    public ResolveInfo getResolveInfo() {
        return resolveInfo;
    }

    /**
     * Sets resolve info.
     *
     * @param resolveInfo the resolve info
     */
    public void setResolveInfo(ResolveInfo resolveInfo) {
        this.resolveInfo = resolveInfo;
    }
    /**
     *  实现Comparable接口，为排序做准备
     */
    @Override
    public int compareTo(@NonNull AppInfo that) {
        return Integer.compare(this.date.compareTo(that.date), 0);
    }

}
