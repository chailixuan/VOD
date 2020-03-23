package com.example.httpclientdemo;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.LoginFilter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int SHOW_RESPONSE=1;
    private static final String TAG = "MainActivity";
    private  String  id;
    private  String  pw
            ;
    public Handler handler=new Handler() {
        public void handleMessage(Message msg)
        {
            switch (msg.what){
                case SHOW_RESPONSE:
                    String response=(String)msg.obj;
                    Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
                    if(response.equals("true")) {
                        Toast.makeText(MainActivity.this,"登陆成功",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                        //c传值
                        Bundle bundle = new Bundle();
                        bundle.putString("id",id);
                        bundle.putString("pw",pw);
                       intent.putExtras(bundle);
                        startActivity(intent);
                    }
                    else
                        Toast.makeText(MainActivity.this,"账户或密码错误",Toast.LENGTH_SHORT).show();
                        break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.Login);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText uid = (EditText) findViewById(R.id.username);
                EditText pwd = (EditText) findViewById(R.id.password);
               id = uid.getText().toString().trim();//trim的作用是去掉字符串左右的空格
               pw = pwd.getText().toString().trim();



                Log.d(TAG, "id:" + id + "pw:" + pw);
                if (uid.getText().toString().length() != 0 && pwd.getText().toString().length() != 0)
                    SendByHttpClient(id, pw);
                else
                    Toast.makeText(MainActivity.this, "请将信息填写完整", Toast.LENGTH_SHORT).show();
                //        Intent intent=new Intent(MainActivity.this,SecondActivity.class);
                 //      startActivity(intent);
            }
        });
        Button button1 = (Button) findViewById(R.id.Register);
        button1.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                                           startActivity(intent);
                                       }
                                   }
        );
    }

    public void SendByHttpClient(final String id, final String pw){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpClient httpclient=new DefaultHttpClient();
                  //  HttpPost httpPost=new HttpPost("http://172.24.43.161:8080/HttpClientDemo/Login");

                    HttpPost httpPost=new HttpPost("http://192.168.1.102:8080/HttpClientDemo/Login");
                    List<NameValuePair> params=new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("ID",id));
                    params.add(new BasicNameValuePair("PW",pw));
                    final UrlEncodedFormEntity entity=new UrlEncodedFormEntity(params,"utf-8");
                    httpPost.setEntity(entity);//方法一个也不能少
                    HttpResponse httpResponse= httpclient.execute(httpPost);
                    if(httpResponse.getStatusLine().getStatusCode()==200)
                    {
                        HttpEntity entity1=httpResponse.getEntity();
                        String response=EntityUtils.toString(entity1, "utf-8");

                       // System.out.println(response);



                        Log.i(TAG, "onCreate exec");
                        Log.i(TAG, response);
                        Message message=new Message();
                        message.what=SHOW_RESPONSE;
                        message.obj=response;
                        handler.sendMessage(message);
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
