package com.dyshkotaras.todolist;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleEmitter;
import io.reactivex.rxjava3.core.SingleOnSubscribe;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ApiRequest {
    private static final String API_URL = "https://ohmytraff.space/api";

    public Single<Boolean> makeApiRequest() {
        return Single.create(new SingleOnSubscribe<Boolean>() {
            @Override
            public void subscribe(SingleEmitter<Boolean> emitter) throws Exception {
                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url(API_URL)
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
//                        emitter.onError(e);
                        Log.d("WebViewActivity1", "1111111");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        int statusCode = response.code();
                        Log.d("WebViewActivity1", String.valueOf(statusCode));
                        boolean is404 = statusCode == 404;
                        emitter.onSuccess(is404);
                    }
                });
            }
        });
    }
}

