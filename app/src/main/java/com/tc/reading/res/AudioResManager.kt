package com.tc.reading.res

import android.text.TextUtils
import android.util.Log
import com.tc.android.basic.NetworkRequester
import com.tc.reading.AppContext
import com.tc.reading.entity.PkAudio
import com.tc.reading.entity.PkAudioSuit
import com.tc.reading.entity.PkBook
import com.tc.reading.entity.PkBookSuit
import org.json.JSONObject

class AudioResManager(private val appCtx: AppContext) {
    private val TAG = "AudioResManager"

    fun queryAudioSuits(page: Int, pageSize: Int, callback: (MutableList<PkAudioSuit>?) -> Unit) {
        val url = appCtx.getBaseServerUrl() + Api.API_AUDIOSUIT_QUERY + "?Page=" + page + "&PageSize=" + pageSize;
        appCtx.postBgTask {
            val resp = NetworkRequester.httpGet(url);
            if (TextUtils.isEmpty(resp)) {
                Log.e(TAG, "request video suits failed.");
                return@postBgTask;
            }
            val audioSuits = parseAudioSuits(resp!!)
            callback(audioSuits)
        }
    }

    private fun parseAudioSuits(value: String): MutableList<PkAudioSuit>? {
        try {
            val obj = JSONObject(value)
            val code = obj.getInt(Api.KEY_CODE)
            if (code != 200) {
                Log.e(TAG, "Invalid json:$value")
                return null
            }

            val audioSuits = mutableListOf<PkAudioSuit>();

            val dataObj = obj.getJSONObject("Data")
            val vsArray = dataObj.getJSONArray(Api.KEY_AUDIOSUITS)
            Log.i(TAG, "BookSuit size: ${vsArray.length()}")
            for (idx in 0 until vsArray.length()) {
                val itemObj = vsArray[idx] as JSONObject;
                val suit = PkAudioSuit()
                suit.id = itemObj.getString(Api.KEY_ID)
                suit.name = itemObj.getString(Api.KEY_NAME)
                suit.author = itemObj.getString(Api.KEY_AUTHOR)
                suit.cover = itemObj.getString(Api.KEY_COVER)
                suit.coverId = itemObj.getString(Api.KEY_COVER_ID)
                suit.summary = itemObj.getString(Api.KEY_SUMMARY)

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
                audioSuits.add(suit)
            }
            return audioSuits
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "Parse json failed:" + e.message)
            return null
        }
    }

    fun queryAudios(audioSuitId: String, page: Int, pageSize: Int, callback: (MutableList<PkAudio>?) -> Unit) {
        val url = appCtx.getBaseServerUrl() + Api.API_AUDIO_QUERY + "?Page=" + page + "&PageSize=" + pageSize + "&AudioSuitId=" + audioSuitId
        Log.i(TAG, url)
        appCtx.postBgTask {
            val resp = NetworkRequester.httpGet(url);
            if (TextUtils.isEmpty(resp)) {
                Log.e(TAG, "request video suits failed.");
                return@postBgTask;
            }
            val audioSuits = parseAudios(resp!!)
            callback(audioSuits)
        }
    }

    private fun parseAudios(value: String): MutableList<PkAudio>? {
        try {
            val obj = JSONObject(value)
            val code = obj.getInt(Api.KEY_CODE)
            if (code != 200) {
                Log.e(TAG, "Invalid json:$value")
                return null
            }

            val audios = mutableListOf<PkAudio>();

            val dataObj = obj.getJSONObject("Data")
            val vsArray = dataObj.getJSONArray(Api.KEY_AUDIOS)
            Log.i(TAG, "AudioSuit size: ${vsArray.length()}")
            for (idx in 0 until vsArray.length()) {
                val itemObj = vsArray[idx] as JSONObject;
                val audio = PkAudio()
                audio.id = itemObj.getString(Api.KEY_ID)
                audio.name = itemObj.getString(Api.KEY_NAME)
                audio.author = itemObj.getString(Api.KEY_AUTHOR)
                audio.cover = itemObj.getString(Api.KEY_COVER)
                audio.coverId = itemObj.getString(Api.KEY_COVER_ID)
                audio.summary = itemObj.getString(Api.KEY_SUMMARY)
                audio.file = itemObj.getString(Api.KEY_FILE)

                // categories
                val catArray = itemObj.getJSONArray(Api.KEY_CATEGORIES)
                for (catIdx in 0 until catArray.length()) {
                    val catItem = catArray.getString(catIdx)
                    audio.categories.add(catItem)
                }

                // grades
                val gradeArray = itemObj.getJSONArray(Api.KEY_GRADES)
                for (gradeIdx in 0 until gradeArray.length()) {
                    val gradeItem = gradeArray.getString(gradeIdx)
                    audio.grades.add(gradeItem)
                }

                if (!TextUtils.isEmpty(audio.cover)) {
                    audio.cover = audio.cover.replace("\\", "/");
                }
                audios.add(audio)
            }
            return audios
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "Parse json failed:" + e.message)
            return null
        }
    }

}