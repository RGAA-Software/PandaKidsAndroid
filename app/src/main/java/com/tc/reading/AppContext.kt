package com.tc.reading

import android.content.Context
import android.os.Handler
import android.os.HandlerThread
import com.tc.android.basic.SPUtils
import com.tc.reading.res.AudioResManager
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
    private var audioResManager: AudioResManager

    private var execService: ExecutorService
    private var spUtil: SPUtils = SPUtils(context)
    private var appSettings: AppSettings = AppSettings(this)

    init {
        handlerThread.start();
        handler = Handler(handlerThread.looper);
        videoResManager = VideoResManager(this);
        bookResManager = BookResManager(this)
        audioResManager = AudioResManager(this)
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
        return appSettings.getServerAddress()
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

    fun getAudioResManager(): AudioResManager {
        return audioResManager
    }

    fun getColor(id: Int): Int {
        return context.getColor(id)
    }

    fun getContext(): Context {
        return context
    }

    fun getSpUtils(): SPUtils {
        return spUtil
    }

    fun getAppSettings(): AppSettings {
        return appSettings
    }
}