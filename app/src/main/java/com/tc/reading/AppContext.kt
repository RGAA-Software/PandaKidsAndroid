package com.tc.reading

import android.content.Context
import android.os.Handler
import android.os.HandlerThread
import com.tc.reading.res.BookResManager
import com.tc.reading.res.VideoResManager
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class AppContext(private var context: Context) {

    private var handlerThread: HandlerThread = HandlerThread("bg");
    private lateinit var handler: Handler;
    private var mainHandler = Handler(context.mainLooper);
    private var videoResManager: VideoResManager;
    private var bookResManager: BookResManager
    private var baseServerUrl: String
    private var execService: ExecutorService;

    init {
//        baseServerUrl = "http://192.168.31.5:9988";
        baseServerUrl = "http://192.168.1.127:9988";
        handlerThread.start();
        handler = Handler(handlerThread.looper);
        videoResManager = VideoResManager(this);
        bookResManager = BookResManager(this)
        execService = Executors.newFixedThreadPool(4);
    }

    fun postTask(task: Runnable) {
        handler.post(task);
    }

    fun postDelayTask(task: Runnable, time: Long) {
        handler.postDelayed(task, time);
    }

    fun postUITask(task: Runnable) {
        mainHandler.post(task);
    }

    fun postDelayUITask(task: Runnable, time: Long) {
        mainHandler.postDelayed(task, time);
    }

    fun getBaseServerUrl(): String {
        return baseServerUrl;
    }

    fun postBgTask(task: Runnable) {
        execService.execute(task);
    }

    fun getVideoResManager(): VideoResManager {
        return videoResManager;
    }

    fun getBookResManager(): BookResManager {
        return bookResManager
    }

    fun getColor(id: Int): Int {
        return context.getColor(id)
    }

    fun getContext(): Context {
        return context
    }
}