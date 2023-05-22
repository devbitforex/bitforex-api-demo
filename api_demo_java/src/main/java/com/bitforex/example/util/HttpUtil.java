package com.bitforex.example.util;

import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

public class HttpUtil {

    private static final String REQUEST_HOST = "https://api.bitforex.com";

    private static final OkHttpClient OK_HTTP_CLIENT = createOkHttpClient();

    private static final String accessKey = "b84fdc186908a1d14cdd2e6bfb456f67";
    private static final String secretKey = "a2043aad6fe350aab37b12ec7030aeba";

    private static OkHttpClient createOkHttpClient() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .connectTimeout(45, TimeUnit.SECONDS)
                .readTimeout(45, TimeUnit.SECONDS)
                .writeTimeout(45, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor)
                .build();
    }

    public static String get(String uri, TreeMap<String, Object> params) {
        try {

            String url = REQUEST_HOST + uri + "?" + SignatureUtil.toQueryString(uri, params, accessKey, secretKey);
            Response response = OK_HTTP_CLIENT
                    .newCall(new Request.Builder().url(url).get().build())
                    .execute();
            return response.body().string();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String post(String uri, TreeMap<String, Object> params) {
        try {
            String url = REQUEST_HOST + uri;
            FormBody.Builder builder = new FormBody.Builder();

            params = SignatureUtil.toQueryMap(uri, params, accessKey, secretKey);
            for (String param : params.keySet()) {
                builder.add(param, String.valueOf(params.get(param)));
            }
            RequestBody body = builder.build();
            Response response = OK_HTTP_CLIENT
                    .newCall(new Request.Builder()
                            .url(url)
                            .post(body).build()).execute();
            return response.body().string();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        TreeMap<String, Object> params = new TreeMap<String, Object>();
        String result = HttpUtil.post("/api/v1/fund/allAccount", params);
        System.out.println(result);
    }
}
