package com.tc.reading

import android.app.Application
import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi
import com.shuyu.gsyvideoplayer.player.IjkPlayerManager
import com.shuyu.gsyvideoplayer.player.PlayerFactory
import com.shuyu.gsyvideoplayer.player.SystemPlayerManager
import com.tc.reading.ui.video.subtitle.GSYExoSubTitlePlayerManager
import tv.danmaku.ijk.media.exo2.Exo2PlayerManager


class App : Application() {

    private lateinit var appContext: AppContext

    @OptIn(UnstableApi::class)
    override fun onCreate() {
        super.onCreate()
        appContext = AppContext(applicationContext)

        //EXOPlayer内核，支持格式更多
        //PlayerFactory.setPlayManager(Exo2PlayerManager::class.java)
        //PlayerFactory.setPlayManager(SystemPlayerManager::class.java)
        //PlayerFactory.setPlayManager(IjkPlayerManager::class.java)
        PlayerFactory.setPlayManager(GSYExoSubTitlePlayerManager::class.java)

    }

    fun getAppContext(): AppContext {
        return appContext;
    }

}