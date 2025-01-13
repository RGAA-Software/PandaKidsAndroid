package com.tc.reading

import android.content.Context
import android.os.Handler
import android.os.HandlerThread
import com.tc.reading.res.VideoResManager
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class AppContext(private var context: Context) {

    private var handlerThread: HandlerThread = HandlerThread("bg");
    private lateinit var handler: Handler;
    private var mainHandler = Handler(context.mainLooper);
    private var videoResManager: VideoResManager;
    private var baseServerUrl: String;
    private var es: ExecutorService;

    init {
        baseServerUrl = "http://192.168.31.5:9988";
        handlerThread.start();
        handler = Handler(handlerThread.looper);
        videoResManager = VideoResManager(this);
        es = Executors.newFixedThreadPool(4);
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
        es.execute(task);
    }

    fun getVideoResManager(): VideoResManager {
        return videoResManager;
    }
}