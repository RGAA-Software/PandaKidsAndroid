package com.tc.reading.res

import android.text.TextUtils
import android.util.Log
import com.tc.android.basic.NetworkRequester
import com.tc.reading.AppContext
import com.tc.reading.entity.PkBook
import com.tc.reading.entity.PkBookSuit
import org.json.JSONObject

class BookResManager(private var appCtx: AppContext) {
    private val TAG = "BookResManager"

    fun queryBookSuits(page: Int, pageSize: Int, callback: (MutableList<PkBookSuit>?) -> Unit ) {
        val url = appCtx.getBaseServerUrl() + Api.API_BOOKSUIT_QUERY + "?Page=" + page + "&PageSize=" + pageSize;
        appCtx.postBgTask {
            val resp = NetworkRequester.httpGet(url);
            if (TextUtils.isEmpty(resp)) {
                Log.e(TAG, "request video suits failed.");
                return@postBgTask;
            }
            val videoSuits = parseBookSuits(resp!!)
            callback(videoSuits)
        }
    }

    private fun parseBookSuits(value: String) : MutableList<PkBookSuit>? {
        try {
            val obj = JSONObject(value)
            val code = obj.getInt(Api.KEY_CODE)
            if (code != 200) {
                Log.e(TAG, "Invalid json:$value")
                return null
            }

            val bookSuits = mutableListOf<PkBookSuit>();

            val dataObj = obj.getJSONObject("Data")
            val vsArray = dataObj.getJSONArray(Api.KEY_BOOKSUITS)
            Log.i(TAG, "BookSuit size: ${vsArray.length()}")
            for (idx in 0 until vsArray.length()) {
                val itemObj = vsArray[idx] as JSONObject;
                val suit = PkBookSuit()
                suit.id = itemObj.getString(Api.KEY_ID)
                suit.name = itemObj.getString(Api.KEY_NAME)
                suit.author = itemObj.getString(Api.KEY_AUTHOR)
                suit.cover = itemObj.getString(Api.KEY_COVER)
                suit.coverId = itemObj.getString(Api.KEY_COVER_ID)
                suit.summary = itemObj.getString(Api.KEY_SUMMARY)
                suit.content = itemObj.getString(Api.KEY_CONTENT)
                suit.details = itemObj.getString(Api.KEY_DETAILS)
                suit.bookSuitPath = itemObj.getString(Api.KEY_BOOKSUIT_PATH)
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
                bookSuits.add(suit)
            }
            return bookSuits
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "Parse json failed:" + e.message)
            return null
        }
    }

    fun queryBooks(bookSuitId: String, page: Int, pageSize: Int, callback: (MutableList<PkBook>?) -> Unit) {
        val url = appCtx.getBaseServerUrl() + Api.API_BOOK_QUERY + "?Page=" + page + "&PageSize=" + pageSize + "&BookSuitId=" + bookSuitId
        Log.i(TAG, url)
        appCtx.postBgTask {
            val resp = NetworkRequester.httpGet(url);
            if (TextUtils.isEmpty(resp)) {
                Log.e(TAG, "request video suits failed.");
                return@postBgTask;
            }
            val videoSuits = parseBooks(resp!!)
            callback(videoSuits)
        }
    }

    private fun parseBooks(value: String): MutableList<PkBook>? {
        try {
            val obj = JSONObject(value)
            val code = obj.getInt(Api.KEY_CODE)
            if (code != 200) {
                Log.e(TAG, "Invalid json:$value")
                return null
            }

            val books = mutableListOf<PkBook>();

            val dataObj = obj.getJSONObject("Data")
            val vsArray = dataObj.getJSONArray(Api.KEY_BOOKS)
            Log.i(TAG, "BookSuit size: ${vsArray.length()}")
            for (idx in 0 until vsArray.length()) {
                val itemObj = vsArray[idx] as JSONObject;
                val book = PkBook()
                book.id = itemObj.getString(Api.KEY_ID)
                book.name = itemObj.getString(Api.KEY_NAME)
                book.author = itemObj.getString(Api.KEY_AUTHOR)
                book.cover = itemObj.getString(Api.KEY_COVER)
                book.coverId = itemObj.getString(Api.KEY_COVER_ID)
                book.summary = itemObj.getString(Api.KEY_SUMMARY)
                book.content = itemObj.getString(Api.KEY_CONTENT)
                book.details = itemObj.getString(Api.KEY_DETAILS)
                book.file = itemObj.getString(Api.KEY_FILE)

                // categories
                val catArray = itemObj.getJSONArray(Api.KEY_CATEGORIES)
                for (catIdx in 0 until catArray.length()) {
                    val catItem = catArray.getString(catIdx)
                    book.categories.add(catItem)
                }

                // grades
                val gradeArray = itemObj.getJSONArray(Api.KEY_GRADES)
                for (gradeIdx in 0 until gradeArray.length()) {
                    val gradeItem = gradeArray.getString(gradeIdx)
                    book.grades.add(gradeItem)
                }

                if (!TextUtils.isEmpty(book.cover)) {
                    book.cover = book.cover.replace("\\", "/");
                }
                books.add(book)
            }
            return books
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "Parse json failed:" + e.message)
            return null
        }
    }

}