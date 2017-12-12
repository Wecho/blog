package com.wecho.core.util;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

public class HttpClientUtil {

    public void get(String url) throws IOException {
        CloseableHttpResponse response = null;
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpGet httpget = new HttpGet(url);
            // 执行get请求.
            response = httpclient.execute(httpget);
            // 获取响应实体
            HttpEntity entity = response.getEntity();
            Header[] Headers = response.getAllHeaders();
            for (Header header : Headers) {
                System.out.println(header.getName());
            }
            System.out.println("--------------------------------------");
            // 打印响应状态
            System.out.println(response.getStatusLine());
            if (entity != null) {
                // 打印响应内容长度
                System.out.println("Response content length: " + entity.getContentLength());
                // 打印响应内容
                // System.out.println("Response content: " + EntityUtils.toString(entity));
            }
            System.out.println("------------------------------------");
        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        } finally {
            response.close();
        }
    }

    public void postUpload(String url) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpPost httpPost = new HttpPost(url);
            FileBody FileEntity = new FileBody(new File("C:\\Users\\wecho\\Desktop\\study\\自学文档\\java\\0.5----Head First Java(中文版).pdf"));
            StringBody comment = new StringBody("A binary file of some kind", ContentType.create("application/pdf"));

            HttpEntity reqEntity = MultipartEntityBuilder.create().addPart("bin", FileEntity).addPart("comment", comment).build();

            httpPost.setEntity(reqEntity);
            System.out.println("executing request " + httpPost.getRequestLine());

            CloseableHttpResponse response = httpclient.execute(httpPost);
            try {
                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    System.out.println("Response content length: " + resEntity.getContentLength());
                }
                EntityUtils.consume(resEntity);
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void formUpload(String url) throws Exception {
        HttpClient httpclient = new DefaultHttpClient();

//        HttpHost proxy = new HttpHost(p.getHost(), Integer.valueOf(p.getPort()), "http");
//        httpclient.getParams().setParameter(ConnRouteParams.DEFAULT_PROXY, proxy);
        //请求路径
        HttpPost post = new HttpPost(url);
//添加header头信息
        post.setHeader("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;)");
//注 这里一定不能添加 content-Type:multipart/form-data 属性 因为这里面有个boundary参数属性是不可控的。这个值是由浏览器生成的。如果强行指明和可能
//导致边界值不一致 就会请求失败 详细参见 http://blog.csdn.net/xiaojianpitt/article/details/6856536

//      post.setHeader("content-Type", "multipart/form-data");
        post.setHeader("Host", "****");
        post.setHeader("Accept-Encoding","gzip");
        post.setHeader("charset", "utf-8");
        FileBody fileBody = new FileBody(new File("C:\\Users\\wecho\\Desktop\\study\\自学文档\\java\\0.5----Head First Java(中文版).pdf"),"application/pdf","utf-8");
        MultipartEntity entity = new MultipartEntity();
//添加消息体信息
        //entity.addPart("xxxx", new StringBody("xxx", Charset.forName("utf-8")));
        entity.addPart("imagefile", fileBody);
        entity.addPart("key", new StringBody("test"));
        post.setEntity(entity);
        HttpResponse response = httpclient.execute(post);
        if(HttpStatus.SC_OK==response.getStatusLine().getStatusCode()){

            HttpEntity entitys = response.getEntity();
            if (entity != null) {
                System.out.println(EntityUtils.toString(entitys));
            }
        }
        httpclient.getConnectionManager().shutdown();
    }

    public static void main(String[] args) throws Exception {
        //new HttpClientUtil().get("https://ul-ecsms-pdf-0x01.sh1a.qingstor.com/a57eee28652fe5d797d11814943cf008.jpg?expires=1513088949&signature=Gtiod%2BTqwDd1P0aQIslwguin53d6%2BDigEEqZ4IYLt%2FQ%3D&access_key_id=DHMMCJWPLCUIJICBLNOM");
        new HttpClientUtil().formUpload("http://ul-ecsms-pdf-0x01.sh1a.qingstor.com");
    }
}
