package com.wecho.core.util;

import net.sf.json.JSONObject;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;

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
        //post.setHeader("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;)");
        //注 这里一定不能添加 content-Type:multipart/form-data 属性 因为这里面有个boundary参数属性是不可控的。这个值是由浏览器生成的。如果强行指明和可能
        //导致边界值不一致 就会请求失败 详细参见 http://blog.csdn.net/xiaojianpitt/article/details/6856536
        String boundary = UUID.randomUUID().toString().replace("-", "");
        post.setHeader("user-agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");
        //post.setHeader("Content-Type", "multipart/form-data; boundary=" + boundary);
        post.setHeader("Host", "ul-ecsms-pdf-0x01.sh1a.qingstor.com");
        //post.setHeader("Content-Length", String.valueOf(new File("D:\\tools\\123test.pdf").length()));
        FileBody fileBody = new FileBody(new File("D:\\tools\\123test.pdf"), "application/pdf", "utf-8");
        MultipartEntity entity = new MultipartEntity();
        //添加消息体信息

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("key", "授权模板1.pdf");
        BASE64Encoder base64Encoder = new BASE64Encoder();

        String policybase64Encoder = base64Encoder.encode(jsonObject.toString().getBytes());
        System.out.println(policybase64Encoder);
        //entity.addPart("xxxx", new StringBody("xxx", Charset.forName("utf-8")));
        entity.addPart("policy", new StringBody(policybase64Encoder, Charset.forName("utf-8")));
        entity.addPart("access_key_id", new StringBody("NOQPQKIUWIFZWPSIFHTM"));
        entity.addPart("signature", new StringBody(base64Encoder.encode(HMACSHA256.sha256_HMAC(policybase64Encoder, "2Iy8yuAkXNoDaVYaMXFfhfnpAxOqKRyXSHdId3Dj").getBytes())));
        //System.out.println(base64Encoder.encode(HMACSHA256.sha256_HMAC(policybase64Encoder, "2Iy8yuAkXNoDaVYaMXFfhfnpAxOqKRyXSHdId3Dj").getBytes()));
        entity.addPart("key", new StringBody("授权模板1.pdf"));
        //entity.addPart("policy", new StringBody(policybase64Encoder));

        entity.addPart("file", fileBody);

        post.setEntity(entity);
        //HttpResponse response = httpclient.execute(post);
        ResponseHandler<String> responseHandler = response -> {
            int status = response.getStatusLine().getStatusCode();
            response.getEntity();
            System.out.println(response.getStatusLine().getReasonPhrase());
            if (status >= 200 && status < 300) {
                HttpEntity responseEntity = response.getEntity();
                return responseEntity != null ? EntityUtils.toString(responseEntity) : null;
            } else {
                throw new ClientProtocolException("Unexpected response status: " + status);
            }
        };
        String responseBody = httpclient.execute(post, responseHandler);
        System.out.println("----------------------------------------");
        System.out.println(responseBody);
    }

    public static void testUpload() throws IOException {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {

            /*List<NameValuePair> form = new ArrayList<>();
            form.add(new BasicNameValuePair("foo", "bar"));
            form.add(new BasicNameValuePair("employee", "John Doe"));
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(form, Consts.UTF_8);*/

            MultipartEntity entity = new MultipartEntity();
            entity.addPart("param1", new StringBody("中国", Charset.forName("UTF-8")));
            entity.addPart("param2", new StringBody("value2", Charset.forName("UTF-8")));
            entity.addPart("param3", new FileBody(new File("D:\\tools\\123test.pdf")));

            HttpPost httpPost = new HttpPost("http://ul-ecsms-pdf-0x01.sh1a.qingstor.com");
            httpPost.setEntity(entity);
            System.out.println("Executing request " + httpPost.getRequestLine());

            // Create a custom response handler
            ResponseHandler<String> responseHandler = response -> {
                int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity responseEntity = response.getEntity();
                    return responseEntity != null ? EntityUtils.toString(responseEntity) : null;
                } else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            };
            String responseBody = httpclient.execute(httpPost, responseHandler);
            System.out.println("----------------------------------------");
            System.out.println(responseBody);
        }
    }

    /**
     * put 上传文件
     */
    public static void putUpload() throws IOException {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpPut httpPut = new HttpPut("http://ul-ecsms-pdf-0x01.sh1a.qingstor.com/123test.pdf");
            //httpPut.setEntity(new StringEntity("Hello, World"));
            httpPut.setHeader("Host", "ul-ecsms-pdf-0x01.sh1a.qingstor.com");
            httpPut.setHeader("Authorization","QS "+"NOQPQKIUWIFZWPSIFHTM"+":"+getSignature());
            //httpPut.setHeader("Content-Length", String.valueOf(new File("D:\\tools\\123test.pdf").length()));

            httpPut.setEntity(new FileEntity(new File("D:\\tools\\123test.pdf")));
            System.out.println("Executing request " + httpPut.getRequestLine());

            // Create a custom response handler
            ResponseHandler<String> responseHandler = response -> {
                int status = response.getStatusLine().getStatusCode();
                System.out.println(status);
                if (status >= 200 && status < 400) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
                } else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            };
            String responseBody = httpclient.execute(httpPut, responseHandler);
            System.out.println("----------------------------------------");
            System.out.println(responseBody);
        }
    }

    /**
     * 生成签名
     * @return
     */
    public static String getSignature(){
        BASE64Encoder base64Encoder = new BASE64Encoder();

        Calendar cd = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss 'GMT'", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT")); // 设置时区为GMT
        String str = sdf.format(cd.getTime());
        System.out.println(str);

        String temp ="PUT" + "\n"
                +        "\n"
                +        "\n"
                + str + "\n"
                + "/ul-ecsms-pdf-0x01/"+ URLEncoder.encode("123test.pdf");
        System.out.println(temp);
        return base64Encoder.encode(HMACSHA256.sha256_HMAC(temp, "2Iy8yuAkXNoDaVYaMXFfhfnpAxOqKRyXSHdId3Dj").getBytes());
    }

    public static void main(String[] args) throws Exception {
        //new HttpClientUtil().get("https://ul-ecsms-pdf-0x01.sh1a.qingstor.com/a57eee28652fe5d797d11814943cf008.jpg?expires=1513088949&signature=Gtiod%2BTqwDd1P0aQIslwguin53d6%2BDigEEqZ4IYLt%2FQ%3D&access_key_id=DHMMCJWPLCUIJICBLNOM");
        //new HttpClientUtil().formUpload("http://ul-ecsms-pdf-0x01.sh1a.qingstor.com");
        //new HttpClientUtil().getSignature();
        new HttpClientUtil().putUpload();
    }
}
