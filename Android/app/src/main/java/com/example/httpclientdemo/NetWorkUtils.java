package com.example.httpclientdemo;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by ASUS on 2019/5/25.
 */

public class NetWorkUtils {

    private Context context;

    // 网路链接管理对象
    public ConnectivityManager connectivityManager;

    public NetWorkUtils(Context context) {
        this.context = context;
        // 获取网络链接的对象
        connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public boolean setActiveNetWork() {

        boolean flag =false;
        // 获取可用的网络链接对象
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null) {
            new AlertDialog.Builder(context)
                    .setTitle("网络不可用")
                    .setMessage("可以设置网络?")
                    .setPositiveButton("确认",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    Toast.makeText(context, "点击确认",
                                            Toast.LENGTH_LONG).show();


                                    // 声明意图
                                    Intent intent = new Intent();
                                    intent.setAction(Intent.ACTION_MAIN);
                                    intent.addCategory("android.intent.category.LAUNCHER");
                                    intent.setComponent(new ComponentName(
                                            "com.android.settings",
                                            "com.android.settings.Settings"));
                                    intent.setFlags(0x10200000);
                                    // 执行意图
                                    context.startActivity(intent);

                                }

                            })
                    .setNegativeButton("取消",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                }

                            }).show();// 必须.show();


        }
        //判断网络是否可用
        if(networkInfo!=null){
            flag =true;
        }
        return flag;
    }
}