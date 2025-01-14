package com.tc.reading.res

import android.text.TextUtils
import android.util.Log
import com.tc.android.basic.NetworkRequester
import com.tc.reading.AppContext
import com.tc.reading.entity.Video
import com.tc.reading.entity.VideoSuit
import org.json.JSONObject

class VideoResManager(private var appCtx: AppContext) {

    private val TAG = "VideoResManager";

    init {

    }

    fun queryVideoSuits(page: Int, pageSize: Int, callback: (Boolean, MutableList<VideoSuit>?) -> Unit ) {
        val url = appCtx.getBaseServerUrl() + Api.API_VIDEOSUIT_QUERY + "?Page=" + page + "&PageSize=" + pageSize;
        appCtx.postBgTask {
            val resp = NetworkRequester.httpGet(url);
            if (TextUtils.isEmpty(resp)) {
                Log.e(TAG, "request video suits failed.");
                return@postBgTask;
            }

            try {
                val obj = JSONObject(resp);
                val code = obj.getInt("Code")
                if (code != 200) {
                    Log.e(TAG, "Invalid json:$resp")
                    callback(false, null);
                    return@postBgTask
                }

                val videoSuits = mutableListOf<VideoSuit>();

                val dataObj = obj.getJSONObject("Data")
                val vsArray = dataObj.getJSONArray("VideoSuits")
                for (idx in 0 until vsArray.length()) {
                    val itemObj = vsArray[idx] as JSONObject;
                    val suit = VideoSuit()
                    suit.id = itemObj.getString(Api.KEY_ID)
                    suit.name = itemObj.getString("Name")
                    suit.cover = itemObj.getString("Cover")
                    if (!TextUtils.isEmpty(suit.cover)) {
                        suit.cover = suit.cover.replace("\\", "/");
                    }
                    videoSuits.add(suit)
                }
                callback(true, videoSuits)
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e(TAG, "Parse json failed:" + e.message)
                callback(false, null);
            }
        }

    }

    fun getTodayVideoSuits(callback: (Boolean, MutableList<VideoSuit>?) -> Unit) {
        val url = appCtx.getBaseServerUrl() + Api.API_TODAY_VIDEOSUITS
        appCtx.postBgTask {
            val resp = NetworkRequester.httpGet(url);
            if (TextUtils.isEmpty(resp)) {
                Log.e(TAG, "request video suits failed.");
                return@postBgTask;
            }
            try {
                val obj = JSONObject(resp);
                val code = obj.getInt("Code")
                if (code != 200) {
                    Log.e(TAG, "Invalid json:$resp")
                    callback(false, null);
                    return@postBgTask
                }

                val videoSuits = mutableListOf<VideoSuit>();

                val dataObj = obj.getJSONObject("Data")
                val vsArray = dataObj.getJSONArray("VideoSuits")
                for (idx in 0 until vsArray.length()) {
                    val itemObj = vsArray[idx] as JSONObject;
                    val suit = VideoSuit()
                    suit.id = itemObj.getString(Api.KEY_ID)
                    suit.name = itemObj.getString("Name")
                    suit.cover = itemObj.getString("Cover")
                    if (!TextUtils.isEmpty(suit.cover)) {
                        suit.cover = suit.cover.replace("\\", "/");
                    }
                    videoSuits.add(suit)
                }
                callback(true, videoSuits)
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e(TAG, "Parse json failed:" + e.message)
                callback(false, null);
            }
        }
    }

    fun queryVideos(page: Int, pageSize: Int, videoSuitId: String, callback: (Boolean, MutableList<Video>?) -> Unit) {
        val url = appCtx.getBaseServerUrl() + Api.API_VIDEO_QUERY
        appCtx.postBgTask {
            val params = HashMap<String, String>()
            params[Api.KEY_VIDEOSUIT_ID] = videoSuitId
            params[Api.KEY_PAGE] = page.toString()
            params[Api.KEY_PAGE_SIZE] = pageSize.toString()
            val resp = NetworkRequester.httpGet(url, params)
            if (TextUtils.isEmpty(resp)) {
                Log.e(TAG, "request video suits failed.");
                return@postBgTask;
            }

            try {
                val obj = JSONObject(resp);
                val code = obj.getInt("Code")
                if (code != 200) {
                    Log.e(TAG, "Invalid json:$resp")
                    callback(false, null);
                    return@postBgTask
                }

                val videos = mutableListOf<Video>();

                val dataObj = obj.getJSONObject("Data")
                val vsArray = dataObj.getJSONArray(Api.KEY_VIDEOS)
                for (idx in 0 until vsArray.length()) {
                    val itemObj = vsArray[idx] as JSONObject;
                    val video = Video()
                    video.name = itemObj.getString(Api.KEY_NAME)
                    video.file = itemObj.getString(Api.KEY_FILE)
                    if (!TextUtils.isEmpty(video.file)) {
                        video.file = video.file.replace("\\", "/");
                    }
                    videos.add(video)
                }
                callback(true, videos)
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e(TAG, "Parse json failed:" + e.message)
                callback(false, null);
            }
        }
    }
}