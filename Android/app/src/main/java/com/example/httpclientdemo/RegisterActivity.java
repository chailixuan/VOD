package com.example.httpclientdemo;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.httpclientdemo.R;
import com.example.httpclientdemo.SecondActivity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;


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

public class RegisterActivity extends AppCompatActivity {
    public static final int SHOW_RESPONSE = 1;
    private static final String TAG = "RegisterActivity";
    public Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_RESPONSE:
                    String response = (String) msg.obj;
                    Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_SHORT).show();
                    if (response.equals("true")) {
                        Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else
                        Toast.makeText(RegisterActivity.this, "账户已存在", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button button = (Button) findViewById(R.id.reg);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText ruid = (EditText) findViewById(R.id.ruid);
                EditText rpwd = (EditText) findViewById(R.id.rpassword);
                EditText remail = (EditText) findViewById(R.id.remail);
                EditText rname= (EditText) findViewById(R.id.rname);
                String id = ruid.getText().toString().trim();//trim的作用是去掉字符串左右的空格
                String pw = rpwd.getText().toString().trim();
                String email= remail.getText().toString().trim();
                String name = rname.getText().toString().trim();
                Log.i(TAG, "id:" + id + "pw:" + pw+"name"+name);
                if (ruid.getText().toString().length() != 0 &&
                        rpwd.getText().toString().length() != 0 &&
                        remail.getText().toString().length() != 0 &&
                        rname.getText().toString().length() != 0
                        )
                    SendByHttpClient(id, pw,email,name);
                else
                    Toast.makeText(RegisterActivity.this, "请将信息填写完整", Toast.LENGTH_SHORT).show();
                //        Intent intent=new Intent(MainActivity.this,SecondActivity.class);
                //       startActivity(intent);
            }
        });

    }

    public void SendByHttpClient(final String id, final String pw,final String email,final String name) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpClient httpclient = new DefaultHttpClient();
                  //  HttpPost httpPost = new HttpPost("http://172.24.43.161:8080/HttpClientDemo/Register");
                    HttpPost httpPost = new HttpPost("http://192.168.1.102:8080/HttpClientDemo/Register");

                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("ID", id));
                    params.add(new BasicNameValuePair("PW", pw));
                    params.add(new BasicNameValuePair("EMAIL", email));
                    params.add(new BasicNameValuePair("NAME", name));

                    final UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "utf-8");
                    httpPost.setEntity(entity);//方法一个也不能少
                    HttpResponse httpResponse = httpclient.execute(httpPost);
                    if (httpResponse.getStatusLine().getStatusCode() == 200) {
                        HttpEntity entity1 = httpResponse.getEntity();
                        String response = EntityUtils.toString(entity1, "utf-8");

                        // System.out.println(response);


                        Log.i(TAG, "onCreate exec");
                        Log.i(TAG, response);
                        Message message = new Message();
                        message.what = SHOW_RESPONSE;
                        message.obj = response;
                        handler.sendMessage(message);
                    }
                } catch (Exception e) {
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
}