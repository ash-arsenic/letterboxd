package com.example.letterbox;

import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoadDataFromApi {
    String load(String url) {
        final String[] data = {""};
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                data[0] = "Failed";
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                data[0] = response.body().string();
            }
        });
        while(true) {
            if (data[0].equals("")) {
                continue;
            } else if (data[0].equals("Failed")) {
                return data[0];
            } else {
                return data[0];
            }
        }
    }
}
