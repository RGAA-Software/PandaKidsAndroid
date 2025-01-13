package com.tc.reading.res

import android.text.TextUtils
import android.util.Log
import com.tc.android.basic.NetworkRequester
import com.tc.reading.AppContext
import com.tc.reading.entity.VideoSuit
import org.json.JSONObject

class VideoResManager(private var appCtx: AppContext) {

    private val TAG = "VideoResManager";

    init {

    }

    fun queryVideoSuits(page: Int, pageSize: Int, callback: (Boolean, MutableList<VideoSuit>?) -> Unit ) {
        var url = appCtx.getBaseServerUrl() + Api.API_VIDEOSUIT_QUERY + "?Page=" + page + "&PageSize=" + pageSize;
        //Log.i(TAG, "req url: " + url)
        appCtx.postBgTask {
            var resp = NetworkRequester.httpGet(url);
            if (TextUtils.isEmpty(resp)) {
                Log.e(TAG, "request video suits failed.");
                return@postBgTask;
            }
            //Log.i(TAG, "video suits: " + resp);

            try {
                var obj = JSONObject(resp);
                var code = obj.getInt("Code")
                if (code != 200) {
                    Log.e(TAG, "Invalid json:$resp")
                    callback(false, null);
                    return@postBgTask
                }

                var videoSuits = mutableListOf<VideoSuit>();

                var dataObj = obj.getJSONObject("Data")
                var vsArray = dataObj.getJSONArray("VideoSuits")
                for (idx in 0 until vsArray.length()) {
                    var itemObj = vsArray[idx] as JSONObject;
                    var suit = VideoSuit()
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

}