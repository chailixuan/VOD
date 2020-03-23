package com.example.httpclientdemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ListVideoActivity extends AppCompatActivity {
    private static final String TAG = "ListVideoActivity";
    // 获取视频数据的地址 寝室有线
   // private String path = "http://172.24.43.161:8080/HttpClientDemo/data.json";
    //寝室无线
    private String path = "http://192.168.1.102:8080/HttpClientDemo/datewireless.json";
    // 接受服务器端响应的数据
    private String content;

    // 声明listView控件
    private ListView listView;
    // 声明handler对象
    private Handler handler;
    private static final int INTENTDATA = 1;

    public JSONArray array;

    public LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_video);

        // 根据服务获取inflater对象
        inflater = (LayoutInflater) this
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        // 发送请求 获取网络数据
        sendGet();
        // 获取控件对象
        listView = (ListView) findViewById(R.id.lv_videos);


        // 实例化 handler操作
        handler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case INTENTDATA:
                        // 获取数据操作
                        // 判断不为空 并且不等于""
                        if (content != null && (!"".equals(content))) {
                            try {
                                // 把它转换成json对象 {} []
                                JSONObject obj = new JSONObject(content);
                                array = obj.getJSONArray("videos");
                                listView.setAdapter(new VideoAdapter());// 设置显示的视图


                                //listView注册事件
                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                    /**
                                     * parent :listView
                                     * view 每个条目控件
                                     * position:条目所在的位置
                                     * id：行号 0
                                     */
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view,
                                                            int position, long id) {


                                        JSONObject jsonObj = (JSONObject) parent.getItemAtPosition(position);

                                        Intent intent = new Intent(ListVideoActivity.this, VideoViewActivity.class);

                                        try {
                                            intent.putExtra("path", jsonObj.getString("player"));
                                            startActivity(intent);
                                        } catch (JSONException e) {
                                            // TODO Auto-generated catch block
                                            e.printStackTrace();
                                        }

                                    }

                                });


                            } catch (JSONException e) {
                                System.out
                                        .println("------------exeception-------------"
                                                + e.getMessage());
                            }

                        }

                        break;

                    default:
                        break;
                }
            }
        };

    }

    public void sendGet() {
        // 操作发送网络请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                content = HttpUtils.sendGetClient(path);
                // 发送消息
                handler.sendEmptyMessage(ListVideoActivity.INTENTDATA);

            }

        }).start();
    }

    class VideoAdapter extends BaseAdapter {

        @Override
        public int getCount() {

            return array.length();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            try {
                return array.get(position);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public long getItemId(int position) {

            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            // 创建一个显示的控件 每个条目对应的控件
            // 根据inflate方法 把一个布局文件转换成View控件对象
            View v = inflater.inflate(R.layout.activity_list, null);
            // findViewById()来获取View布局对象中的控件
            TextView tv_title = (TextView) v.findViewById(R.id.tv_title);
            TextView tv_duration = (TextView) v.findViewById(R.id.tv_duration);
            //  ImageView tv_date = (ImageView) v.findViewById(R.id.tv_date);

            ImageView iv_icon = (ImageView) v.findViewById(R.id.iv_image);
            try {
                JSONObject jsonObj = (JSONObject) array.get(position);
                // 设置显示控件的文本
                tv_title.setText("标题:" + jsonObj.getString("title"));
                tv_duration.setText("地址:" + jsonObj.getString("player"));
                //   tv_date.setText("发布时间:" + jsonObj.getString("published"));

               // iv_icon.setImageBitmap(getURLimage(jsonObj.getString("image")));

                Log.d("image", jsonObj.getString("image"));
            } catch (Exception e) {
                System.out.println("eeeee" + e.getMessage());
                e.printStackTrace();
            }
            // 返回v对象
            return v;
        }

    }

    /**
     * 加载网络图片，获取网络图片的bitmap
     *
     * @param url：网络图片的地址
     * @return
     */
//加载图片
    /*
    public static Bitmap getURLimage(String url) {
        Bitmap bmp = null;
        try {
            URL myurl = new URL(url);
            // 获得连接
            HttpURLConnection conn = (HttpURLConnection) myurl.openConnection();
            conn.setConnectTimeout(6000);//设置超时
            conn.setDoInput(true);
            conn.setUseCaches(false);//不缓存
            conn.connect();
            InputStream is = conn.getInputStream();//获得图片的数据流
*/
         /*   String content = null;
            try {
                // 创建一个httpClient的客户端对象
                HttpClient httpClient = new DefaultHttpClient();

                // 发送的Get请求
                HttpGet httpGet = new HttpGet(url);

                // 客户端
                HttpResponse httpResponse = httpClient.execute(httpGet);

                // 判断服务端是否响应成功
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    // 获取响应的内容
                    InputStream is = httpResponse.getEntity().getContent();
                    */
      /*      bmp = BitmapFactory.decodeStream(is);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bmp;
    }
    */
}
