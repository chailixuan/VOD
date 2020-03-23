package com.example.httpclientdemo;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.httpclientdemo.beans.LocalVideoBean;

public class SecondActivity extends AppCompatActivity {
    private static final String TAG = "SecondActivity";
    private TextView mTextMessage;
    private Button record;
    private final static int VIDEO_REQUESTCODE = 22;
    public static final int VIDEO_TRIM_REQUEST_CODE = 23;
   private Bundle bundle;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent intent=new Intent(SecondActivity.this,SecondActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.navigation_dashboard:
                   // Intent intent1=new Intent(SecondActivity.this,LocalVideoActivity.class);
                    //startActivity(intent1);
                    Intent i = new Intent(Intent.ACTION_PICK);
                    i.setType("video/*");//选择视频 （mp4 3gp 是android支持的视频格式）
                    startActivityForResult(i, 66);


                    return true;
                case R.id.navigation_notifications:
                 //   Intent intent2=new Intent(SecondActivity.this,VideoViewActivity.class);
                    Intent intent2=new Intent(SecondActivity.this,ListVideoActivity.class);
                    startActivity(intent2);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
       // @BindView(R.id.tv_path)
       // TextView tvPath;
       bundle=this.getIntent().getExtras();
        mTextMessage = (TextView) findViewById(R.id.message);
        record = (Button)findViewById(R.id.record);
        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SecondActivity.this,RecordActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.i(TAG, "onActivityResult: requestCode=" + requestCode + " resultCode=" + resultCode+data.getStringExtra("video_path")+data);

        if (resultCode == -1 && requestCode == 66) {

            if (data != null) {
                LocalVideoBean localVideoBean = getVideoPath(data);
                Intent intent = new Intent(SecondActivity.this, VideoViewActivity.class);
                intent.putExtra("path", localVideoBean.getPath());
                Log.d(TAG, "onActivityResult: "+localVideoBean.getPath());
               startActivity(intent);
               // intent.putExtra("path",data.getStringExtra("video_path"));
                Log.d(TAG, "onActivityResult: 2");
              //  tvPath.setText("视频路径：\n" + localVideoBean.getPath() + "\n\n" + " 视频缩略图路径：\n" + localVideoBean.getThumbPath());
               // videoCrop(localVideoBean.getPath());


            } else {
                Toast.makeText(this, "data is null", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onActivityResult: 1");
            }


        } else if (requestCode == VIDEO_REQUESTCODE && resultCode == 11) {

            String video_path = data.getStringExtra("video_path");
            String poster_path = data.getStringExtra("poster_path");


          //  tvPath.setText("视频路径：\n" + video_path + "\n\n" + " 视频缩略图路径：\n" + poster_path);
           // videoCrop(video_path);

        } else if (requestCode == VIDEO_REQUESTCODE && resultCode == 22) {//拍摄的视频路径，可以返回到这里

        } else if (requestCode == VIDEO_TRIM_REQUEST_CODE&& resultCode == 0) {

        }


    }
    private LocalVideoBean getVideoPath(Intent data) {

        Uri uri=data.getData();

        // 视频其他信息的查询条件
        String[] mediaColumns = {MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DATA, MediaStore.Video.Media.DURATION, MediaStore.Video.Media.SIZE, MediaStore.Video.Thumbnails.DATA};

        Log.i(TAG, " MediaStore.Video.Media.DATA:"+ MediaStore.Video.Media.DATA+
                " MediaStore.Video.Media.EXTERNAL_CONTENT_URI:"+MediaStore.Video.Media.EXTERNAL_CONTENT_URI+
                " data.getStringExtra:"+data.getStringExtra("video_path"));
       // String selection = MediaStore.Video.Media.DATA+" like ?";
      //  new String[]{data.getStringExtra("video_path")}
        Cursor cursor = getContentResolver().query(uri,
                mediaColumns, null,null , MediaStore.Video.VideoColumns.DATE_ADDED + " DESC");

        if (cursor == null) {
            return null;
        }

        cursor.moveToFirst();

        LocalVideoBean info = new LocalVideoBean();
        info.setPath(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)));
        info.setDuration(cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)));
        info.setSize(cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)));
        info.setThumbPath(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Thumbnails.DATA)));
        Log.i(TAG, "info:"+info.getPath());
        cursor.close();

        return info;
    }
}
