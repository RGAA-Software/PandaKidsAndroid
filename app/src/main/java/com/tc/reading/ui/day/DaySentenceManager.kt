package com.tc.reading.ui.day

import android.text.TextUtils
import android.util.Log
import com.tc.reading.AppContext
import com.tc.reading.util.DateUtil
import com.tc.reading.util.HttpUtil
import org.json.JSONObject

class DaySentenceManager(private var appContext: AppContext) {

    companion object {
        const val TAG = "Day";
    }

    public fun requestTodaySentence(callback: (st: DaySentence) -> Unit) {
        appContext.postTask{
            val today = DateUtil.fmtCurrentDay();
            // youdao
            val youdao_url = "https://dict.youdao.com/infoline?mode=publish&date=$today&update=auto&apiversion=5.0"
            var resp = HttpUtil.reqUrl(youdao_url);
            //Log.i(TAG, "youdao resp:$resp");
            if (!TextUtils.isEmpty(resp)) {
                try {
                    val sentence = DaySentence("youdao");
                    val obj = JSONObject(resp);
                    if (obj.has(today)) {
                        val objArray = obj.getJSONArray(today);
                        for (i in 0..objArray.length()) {
                            val item = objArray.getJSONObject(i)
                            if (!item.has("type")) {
                                continue;
                            }
                            val type = item.getString("type");
                            val shape = item.getString("shape");
                            if (type == "壹句" && shape == "DAILY") {
                                Log.i(TAG, "resp:${item.toString()}")
                                sentence.content = item.getString("title");
                                sentence.translation = item.getString("summary");
                                if (item.has("gif")) {
                                    val array = item.getJSONArray("gif");
                                    if (array.length() > 0) {
                                        sentence.imageUrl = array.getString(0);
                                    }
                                }
                                sentence.from = item.getString("source");
                                Log.i(TAG, "resp:${sentence.toString()}")
                                callback(sentence);
                                break;
                            }
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            // shanbay
            val shanbay_url = "https://apiv3.shanbay.com/weapps/dailyquote/quote/?date=$today"
            resp = HttpUtil.reqUrl(shanbay_url);
            // Log.i(TAG, "shanbay resp:$resp");
            if (!TextUtils.isEmpty(resp)) {
                try {
                    val sentence = DaySentence("shanbay");
                    val obj = JSONObject(resp!!);
                    if (obj.has("author")) {
                        sentence.author = obj.getString("author");
                    }
                    if (obj.has("content")) {
                        sentence.content = obj.getString("content");
                    }
                    if (obj.has("translation")) {
                        sentence.translation = obj.getString("translation");
                    }
                    if (obj.has("origin_img_urls")) {
                        val array = obj.getJSONArray("origin_img_urls");
                        if (array.length() > 0) {
                            sentence.imageUrl = array.getString(0);
                        }
                    }
                    callback(sentence);
                } catch (e: Exception) {
                    e.printStackTrace();
                }
            }
        };
    }

}