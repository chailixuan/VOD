package com.example.httpclientdemo;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;
/*
public class VideoViewActivity extends AppCompatActivity {
    private VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏显示

        //本地的视频  需要在手机SD卡根目录添加一个 fl1234.mp4 视频
        //String videoUrl1 = Environment.getExternalStorageDirectory().getPath()+"/fl1234.mp4" ;

        //网络视频
        String videoUrl2 = "rtsp://172.24.43.161:80/westbrook.mp4";

        Uri uri = Uri.parse(videoUrl2);

        videoView = (VideoView) this.findViewById(R.id.videoView);

        //设置视频控制器
        videoView.setMediaController(new MediaController(this));

        //播放完成回调
        videoView.setOnCompletionListener(new MyPlayerOnCompletionListener());

        //设置视频路径
        videoView.setVideoURI(uri);
        videoView.requestFocus();
        //开始播放视频
        videoView.start();
    }
    class MyPlayerOnCompletionListener implements MediaPlayer.OnCompletionListener{
        @Override
        public void onCompletion(MediaPlayer mp){
            Toast.makeText(VideoViewActivity.this,"播放完成了",Toast.LENGTH_SHORT).show();
        }
    }

}
*/
public class VideoViewActivity extends AppCompatActivity{

    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 控件
        this.setContentView(R.layout.activity_video_view);

        videoView = (VideoView) findViewById(R.id.videoView);

        String path = this.getIntent().getStringExtra("path");

        if (path != null) {

            // 指定播放的视频文件即可
            videoView.setVideoURI(Uri.parse(path));
            System.out.println(path);
            // 设置视频播放的控制器
            videoView.setMediaController(new MediaController(this));
            // 视频开始播放
            videoView.start();

        } else {
            Toast.makeText(this, "path路径为空", Toast.LENGTH_LONG).show();
        }


    }
}