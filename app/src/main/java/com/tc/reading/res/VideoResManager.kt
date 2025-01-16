package com.tc.reading.res

import android.text.TextUtils
import android.util.Log
import com.tc.android.basic.NetworkRequester
import com.tc.reading.AppContext
import com.tc.reading.entity.PkVideo
import com.tc.reading.entity.PkVideoSuit
import org.json.JSONObject

class VideoResManager(private var appCtx: AppContext) {

    private val TAG = "VideoResManager";

    init {

    }

    fun queryVideoSuits(page: Int, pageSize: Int, callback: (MutableList<PkVideoSuit>?) -> Unit ) {
        val url = appCtx.getBaseServerUrl() + Api.API_VIDEOSUIT_QUERY + "?Page=" + page + "&PageSize=" + pageSize;
        appCtx.postBgTask {
            val resp = NetworkRequester.httpGet(url);
            if (TextUtils.isEmpty(resp)) {
                Log.e(TAG, "request video suits failed.");
                return@postBgTask;
            }
            val videoSuits = parseVideoSuits(resp!!)
            callback(videoSuits)
        }
    }

    fun getTodayVideoSuits(callback: (MutableList<PkVideoSuit>?) -> Unit) {
        val url = appCtx.getBaseServerUrl() + Api.API_TODAY_VIDEOSUITS
        appCtx.postBgTask {
            val resp = NetworkRequester.httpGet(url);
            if (TextUtils.isEmpty(resp)) {
                Log.e(TAG, "request video suits failed.");
                return@postBgTask;
            }
            val videoSuits = parseVideoSuits(resp!!)
            callback(videoSuits)
        }
    }

    // Parse video suit
    private fun parseVideoSuits(value: String): MutableList<PkVideoSuit>? {
        try {
            val obj = JSONObject(value)
            val code = obj.getInt(Api.KEY_CODE)
            if (code != 200) {
                Log.e(TAG, "Invalid json:$value")
                return null
            }

            val videoSuits = mutableListOf<PkVideoSuit>();

            val dataObj = obj.getJSONObject("Data")
            val vsArray = dataObj.getJSONArray("VideoSuits")
            for (idx in 0 until vsArray.length()) {
                val itemObj = vsArray[idx] as JSONObject;
                val suit = PkVideoSuit()
                suit.id = itemObj.getString(Api.KEY_ID)
                suit.name = itemObj.getString(Api.KEY_NAME)
                suit.cover = itemObj.getString(Api.KEY_COVER)
                suit.coverId = itemObj.getString(Api.KEY_COVER_ID)
                suit.summary = itemObj.getString(Api.KEY_SUMMARY)
                suit.content = itemObj.getString(Api.KEY_CONTENT)
                suit.details = itemObj.getString(Api.KEY_DETAILS)
                suit.series = itemObj.getString(Api.KEY_SERIES)

                // categories
                val catArray = itemObj.getJSONArray(Api.KEY_CATEGORIES)
                for (catIdx in 0 until catArray.length()) {
                    val catItem = catArray.getString(catIdx)
                    suit.categories.add(catItem)
                }

                // grades
                val gradeArray = itemObj.getJSONArray(Api.KEY_GRADES)
                for (gradeIdx in 0 until gradeArray.length()) {
                    val gradeItem = gradeArray.getString(gradeIdx)
                    suit.grades.add(gradeItem)
                }

                if (!TextUtils.isEmpty(suit.cover)) {
                    suit.cover = suit.cover.replace("\\", "/");
                }
                videoSuits.add(suit)
            }
            return videoSuits
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "Parse json failed:" + e.message)
            return null
        }
    }

    fun queryVideos(page: Int, pageSize: Int, videoSuitId: String, callback: (MutableList<PkVideo>?) -> Unit) {
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

            val videos = parseVideos(resp!!)
            callback(videos)
        }
    }

    private fun parseVideos(value: String): MutableList<PkVideo>? {
        try {
            val obj = JSONObject(value);
            val code = obj.getInt("Code")
            if (code != 200) {
                Log.e(TAG, "Invalid json:$value")
                return null
            }

            val videos = mutableListOf<PkVideo>();

            val dataObj = obj.getJSONObject("Data")
            val vsArray = dataObj.getJSONArray(Api.KEY_VIDEOS)
            for (idx in 0 until vsArray.length()) {
                val itemObj = vsArray[idx] as JSONObject;
                val video = PkVideo()
                video.id = itemObj.getString(Api.KEY_ID)
                video.name = itemObj.getString(Api.KEY_NAME)
                video.file = itemObj.getString(Api.KEY_FILE)
                video.cover = itemObj.getString(Api.KEY_COVER)
                video.videoSuitId = itemObj.getString(Api.KEY_VIDEOSUIT_ID)
                if (!TextUtils.isEmpty(video.file)) {
                    video.file = video.file.replace("\\", "/");
                }
                videos.add(video)
            }
            return videos
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "Parse json failed:" + e.message)
            return null
        }
    }
}