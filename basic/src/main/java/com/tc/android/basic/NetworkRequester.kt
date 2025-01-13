package com.tc.android.basic

import okhttp3.OkHttpClient
import okhttp3.Request

class NetworkRequester {

    companion object {

        fun httpGet(url: String): String {
            var client = OkHttpClient();
            var request = Request.Builder().url(url).build();
            try {
                var resp = client.newCall(request).execute();
                return resp.body!!.string();
            } catch (e: Exception) {
                e.printStackTrace();
                return "";
            }
        }

    }

}