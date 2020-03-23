package com.example.httpclientdemo;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 2019/5/25.
 */

public class HttpUtils {
    /**
     * httpClient发送的GET请求
     *
     * @param path
     * @return
     */
    public static String sendGetClient(String path) {
        String content = null;
        try {
            // 创建一个httpClient的客户端对象
            HttpClient httpClient = new DefaultHttpClient();

            // 发送的Get请求
            HttpGet httpGet = new HttpGet(path);

            // 客户端
            HttpResponse httpResponse = httpClient.execute(httpGet);

            // 判断服务端是否响应成功
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                // 获取响应的内容
                InputStream is = httpResponse.getEntity().getContent();
                byte data[] = StreamTools.isToData(is);
                    content = new String(data);
                // 关闭流
                is.close();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return content;

    }

    /**
     * httpclient客户端发送Post请求
     * @param path
     * @param name
     * @param pass
     * @return
     */
    public static String sendPostClient(String path, String name, String pass) {
        String content = null;

        //创建一个httpClient对象
        HttpClient httpClient = new DefaultHttpClient();

        //创建请求方式对象  path
        HttpPost httpPost = new HttpPost(path);

        //封装请求的参数集合
        List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
        parameters.add(new BasicNameValuePair("user.name", name));
        parameters.add(new BasicNameValuePair("user.pass", pass));

        UrlEncodedFormEntity entity = null;
        try {
            //封装请参数的实体对象
            entity = new UrlEncodedFormEntity(parameters, "UTF-8");
            //把参数设置到 httpPost中
            httpPost.setEntity(entity);
            //执行请求
            HttpResponse httpResponse = httpClient.execute(httpPost);
            //判断响应是否成功
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                //获取响应的内容
                InputStream is = httpResponse.getEntity().getContent();
                //data
                byte data[] = StreamTools.isToData(is);
                //转换成字符串
                content = new String(data);
            }

        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return content;

    }
}