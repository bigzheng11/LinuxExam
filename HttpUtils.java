package com.example.book.utils;

import android.util.Log;

import com.example.book.bean.BookBean;
import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpUtils {
    public static final int MSG_QUERY_OK = 1;
    public static final int MSG_CREATE_OK = 2;
    public static final int MSG_UPDATE_OK = 3;
    public static final int MSG_DELETE_OK = 4;
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    public static final String URL = "http://121.43.43.193:8080";

    public static Request postRequestBuilder(String action, BookBean req) {
        RequestBody body = RequestBody.create(JSON, new Gson().toJson(req));
        Request request = new Request.Builder().url(String.format("%s/%s", URL, action)).post(body)
                .addHeader("content-type", "application/json").build();
        Log.i("request:", request.body().toString());
        return request;
    }

    public static Request getRequestBuilder(String action) {
        return new Request.Builder().url(String.format("%s/%s", URL, action)).build();
    }
}