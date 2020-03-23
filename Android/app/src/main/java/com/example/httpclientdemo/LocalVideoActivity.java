package com.example.httpclientdemo;

import android.media.MediaPlayer;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import android.widget.VideoView;
import java.io.File;
import static com.example.httpclientdemo.R.id.video_view;

public class LocalVideoActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_video);

        //  Uri uri = Uri.parse("rtsp://172.24.43.161:80/curry.mp4");
        /*
        Uri uri = Uri.parse("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4");
        VideoView videoView = (VideoView)this.findViewById(R.id.video_view);
        videoView.setMediaController(new MediaController(this));
        videoView.setVideoURI(uri);
        videoView.start();
        videoView.requestFocus();
        */
        /***************加载要播放的视频***************/
        /*
        VideoView video=(VideoView)findViewById(video_view) ;
        File file=new File(Environment.getExternalStorageDirectory()+"/20190407_124943_3922.mp4");//获取文件对象
        if(file.exists()){
            video.setVideoPath(file.getAbsolutePath());//指定要播放的视频
        }else{
            Toast.makeText(LocalVideoActivity.this,"要播放的视频文件不存在",Toast.LENGTH_SHORT).show();

        }
        */
        /***************控制视频的播放*************/
       /*
        android.widget.MediaController mc=new android.widget.MediaController(this);//创建MediaControl对象
        video.setMediaController(mc);//让VideoView和MediaController关联
        video.requestFocus();//获得焦点
        video.start();//开始播放视频
        video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Toast.makeText(LocalVideoActivity.this,"视频播放完毕",Toast.LENGTH_SHORT).show();
            }
        });//匿名内部类
        */
    }

}
