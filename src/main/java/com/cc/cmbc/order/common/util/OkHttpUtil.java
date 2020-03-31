package com.cc.cmbc.order.common.util;

import com.alibaba.fastjson.JSONObject;
import com.cc.cmbc.order.common.interceptor.OkHttpTraceIdInterceptor;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * OkHttp工具类
 *
 * @author hetiantian
 * @version 1.0
 * @Date 2020/03/19 14:28
 */
public class OkHttpUtil {
    private static final Logger logger = LoggerFactory.getLogger(OkHttpUtil.class);

    private final static int READ_TIMEOUT = 100;
    private final static int CONNECT_TIMEOUT = 60;
    private final static int WRITE_TIMEOUT = 60;

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private static final String CONTENT_TYPE = "Content-Type";

    private static final byte[] LOCKER = new byte[0];
    private static OkHttpUtil mInstance;
    private static OkHttpClient client;

    static {
        okhttp3.OkHttpClient.Builder clientBuilder = new okhttp3.OkHttpClient.Builder();
        //设置读取超市时间
        clientBuilder.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS);
        //设置超时连接时间
        clientBuilder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS);
        //设置写入超时时间
        clientBuilder.writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS);

        //支持HTTPS请求，跳过证书验证
        sslSocketFactory(clientBuilder);
        clientBuilder.hostnameVerifier((hostname, session) -> true);

        //添加拦截器
        clientBuilder.addNetworkInterceptor(new OkHttpTraceIdInterceptor());
        client = clientBuilder.build();
    }


    /**
     * GET请求
     *
     * @param url 请求地址
     * @return
     */
    public static String doGet(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        final Call call = client.newCall(request);
        Response response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            throw new IOException("Unexpected code " + response);
        }
        if (response != null && response.body() != null){
            return JsonUtil.convertObjectToJSON(response.body());
        }
        return null;

    }


    /**
     * 针对json post处理
     *
     * @param url 请求地址
     * @param json 请求参数
     * @return
     * @throws IOException
     */
    public String doPostJson(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder().url(url).post(body).build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful() && response.body() != null) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    public void doPostJsonAsync(String url, String json, final AsyncNetCall asyncNetCall) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        //2 构造Request
        Request.Builder requestBuilder = new Request.Builder();
        Request request = requestBuilder.post(body).url(url).build();
        //3 将Request封装为Call
        Call call = client.newCall(request);
        //4 执行Call
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                asyncNetCall.failed(call, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                asyncNetCall.success(call, response);

            }
        });
    }

    /**
     * 自定义网络回调接口
     */
    public interface AsyncNetCall {
        /**
         * 成功
         *
         * @param call 请求参数
         * @param response 返回结果
         * @throws IOException 异常
         */
        void success(Call call, Response response) throws IOException;

        /**
         * 失败
         *
         * @param call 请求参数
         * @param e 异常
         */
        void failed(Call call, IOException e);
    }


    private static void sslSocketFactory(okhttp3.OkHttpClient.Builder clientBuilder) {
        X509TrustManager trustManager = null;
        try {
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init((KeyStore) null);
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
                throw new IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers));
            }
            trustManager = (X509TrustManager) trustManagers[0];
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{trustManager}, null);
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            clientBuilder.sslSocketFactory(sslSocketFactory, trustManager);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
