package com.example.httpclientdemo;

import android.content.Intent;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.httpclientdemo.beans.LocalVideoBean;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/******
 * 通过用户id去获取服务器端MySQL数据库中存储的视频数据
 * 设置访问的servlet地址
 *
 */

public class RecordActivity extends AppCompatActivity {
    private static final String TAG = "RecordActivity";
    public LayoutInflater inflater;
    // 声明listView控件
    private ListView listView;
    // 声明handler对象
    private Handler handler;
    private static final int INTENTDATA = 1;
    private List<LocalVideoBean> video_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        // 根据服务获取inflater对象
        inflater = (LayoutInflater) this
                .getSystemService(LAYOUT_INFLATER_SERVICE);

        Bundle bundle1=this.getIntent().getExtras();
        String id=bundle1.getString("id");
        String pw=bundle1.getString("pw");

        SendByHttpClient(id,pw);
        listView = (ListView) findViewById(R.id.lv_video2);
        // 实例化 handler操作
        handler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case INTENTDATA:
                        // 获取数据操作
                        // 判断不为空 并且不等于""

                            try {

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


                                        LocalVideoBean loc= (LocalVideoBean)parent.getItemAtPosition(position);

                                        Intent intent = new Intent(RecordActivity.this, VideoViewActivity.class);

                                        try {
                                            intent.putExtra("path", loc.getPath());
                                            startActivity(intent);
                                        } catch (Exception e) {
                                            // TODO Auto-generated catch block
                                            e.printStackTrace();
                                        }

                                    }

                                });


                            } catch (Exception e) {
                                System.out
                                        .println("------------exeception-------------"
                                                + e.getMessage());


                        }

                        break;

                    default:
                        break;
                }
            }
        };

    }


    class VideoAdapter extends BaseAdapter {

        @Override
        public int getCount() {

            return video_list.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            try {
                return video_list.get(position);
            } catch (Exception e) {
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
                LocalVideoBean obj = video_list.get(position);
                // 设置显示控件的文本
                tv_title.setText("标题:" + obj.getVname());
                tv_duration.setText("地址:" + obj.getPath());
                Log.i(TAG, "getView: "+obj.getVname()+obj.getPath());
                //   tv_date.setText("发布时间:" + jsonObj.getString("published"));

                // iv_icon.setImageBitmap(getURLimage(jsonObj.getString("image")));


            } catch (Exception e) {
                System.out.println("eeeee" + e.getMessage());
                e.printStackTrace();
            }
            // 返回v对象
            return v;
        }

    }



    public void SendByHttpClient(final String id, final String pw){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpClient httpclient=new DefaultHttpClient();
                    //  HttpPost httpPost=new HttpPost("http://172.24.43.161:8080/HttpClientDemo/Login");

                    HttpPost httpPost=new HttpPost("http://192.168.1.102:8080/HttpClientDemo/RecordServlet");
                    Log.i(TAG, "onCreate exec");
                    List<NameValuePair> params=new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("ID",id));
                    params.add(new BasicNameValuePair("PW",pw));
                    final UrlEncodedFormEntity entity=new UrlEncodedFormEntity(params,"utf-8");
                    httpPost.setEntity(entity);//方法一个也不能少
                    HttpResponse httpResponse= httpclient.execute(httpPost);
                    if(httpResponse.getStatusLine().getStatusCode()==200)
                    {
                        HttpEntity entity1=httpResponse.getEntity();
                        String response= EntityUtils.toString(entity1, "utf-8");
                        video_list = new ArrayList<LocalVideoBean>();
                        // System.out.println(response);
                        String arr[] = response.split("\n");
                        for (String str : arr){
                            String detail[] = str.split(",");
                            LocalVideoBean video = new LocalVideoBean(detail[0],detail[1],detail[2],detail[3]);
                            video_list.add(video);
                        }
                        Log.i(TAG, "onCreate exec");
                        Log.i(TAG, response);
                        Message message=new Message();
                        message.what=INTENTDATA;
                  //      message.obj=response;
                        handler.sendMessage(message);
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
