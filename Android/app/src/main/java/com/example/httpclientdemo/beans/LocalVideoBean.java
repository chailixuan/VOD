package com.example.httpclientdemo.beans;

import android.graphics.Bitmap;

import com.example.httpclientdemo.LocalVideoActivity;

/**
 * Created by ASUS on 2019/5/30.
 */

public class LocalVideoBean {
    private String path;
    private String vid;
    private String vname;
    private String vtype;
    private long duration;
    private long size;
    private String thumbPath;
    private Bitmap bitmap;

    public LocalVideoBean(){}

    public LocalVideoBean(String vid,String vname,String path,String vtype){
        this.vid = vid;
        this.vname = vname;
        this.path = path;
        this.vtype = vtype;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getThumbPath() {
        return thumbPath;
    }

    public void setThumbPath(String thumbPath) {
        this.thumbPath = thumbPath;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getVname() {
        return vname;
    }

    public void setVname(String vname) {
        this.vname = vname;
    }

    public String getVtype() {
        return vtype;
    }

    public void setVtype(String vtype) {
        this.vtype = vtype;
    }
}