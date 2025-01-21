package com.tc.android.basic

import android.util.Log
import okhttp3.FormBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.TimeUnit


class NetworkRequester {

    companion object {
        private const val TAG = "Network";

        fun httpGet(url: String, params: Map<String, String>): String? {
            var targetUrl = url
            var index: Int = 0
            params.forEach { (k, v) ->
                targetUrl += if (index == 0) {
                    "?$k=$v"
                } else {
                    "&$k=$v"
                }
                index++
            }
            Log.i(TAG, "url: $targetUrl")
            return httpGet(targetUrl)
        }

        fun httpGet(url: String): String? {
            try {
                val client = OkHttpClient.Builder()
                    .connectTimeout(3, TimeUnit.SECONDS)
                    .readTimeout(5, TimeUnit.SECONDS)
                    .build();
                val request = Request.Builder().url(url).build();
                val resp = client.newCall(request).execute();
                return resp.body?.string();
            } catch (e: Exception) {
                e.printStackTrace();
                return null;
            }
        }

        fun httpPostJson(url: String, args: Map<String, String>): String? {
            try {
                val client = OkHttpClient()
                val mediaType = "application/json;charset=utf-8".toMediaTypeOrNull()!!
                val jsonBody = JSONObject()
                args.forEach { (k, v) ->
                    jsonBody.put(k, v)
                }
                val requestBody: RequestBody = jsonBody.toString().toRequestBody(mediaType)
                val request = Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build()
                val resp = client.newCall(request).execute()
                if (resp.code != 200) {
                    return null;
                }
                val value = resp.body?.string();
                return value
            } catch (e: Exception) {
                Log.e(TAG, "Failed to execute request: ${e.message}")
                return ""
            }
        }

        fun httpPostForm(url: String, args: Map<String, String>): String? {
            try {
                val okHttpClient: OkHttpClient = OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .build()

                val formBodyBuilder: FormBody.Builder = FormBody.Builder()
                args.forEach { (k, v)->
                    formBodyBuilder.add(k, v)
                }
                val formBody = formBodyBuilder.build()
                val request: Request = Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build()
                val resp = okHttpClient.newCall(request).execute();
                return resp.body?.string();
            } catch (e: Exception) {
                e.printStackTrace();
                return null;
            }
        }

        fun checkRemoteFileExists(url: String): Boolean {
            try {
                val client = OkHttpClient.Builder()
                    .connectTimeout(3, TimeUnit.SECONDS)
                    .readTimeout(5, TimeUnit.SECONDS)
                    .build()
                val request: Request = Request.Builder()
                    .head()
                    .url(url)
                    .build()
                val response: Response = client.newCall(request).execute()
                return response.isSuccessful
            } catch (e: IOException) {
                e.printStackTrace()
                return false
            }
        }

    }

}