package com.tc.reading.ui.audio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tc.reading.App
import com.tc.reading.AppContext
import com.tc.reading.R
import com.tc.reading.entity.PkAudio
import com.tc.reading.entity.PkAudioSuit
import com.tc.reading.entity.PkBook
import com.tc.reading.entity.PkBookSuit
import com.tc.reading.res.AudioResManager
import com.tc.reading.res.BookResManager
import com.tc.reading.ui.video.VideoListItemDecoration
import com.tc.reading.util.ScreenUtil

class AudioListActivity : AppCompatActivity() {
    private val TAG = "AudioList"

    private lateinit var appCtx: AppContext
    private lateinit var audioResManager: AudioResManager
    private var mainAudios: MutableList<PkAudio> = mutableListOf()
    private lateinit var audioListAdapter: AudioListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ScreenUtil.makeActivityFullScreen(this)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setContentView(R.layout.activity_audio_list)
        appCtx = (application as App).getAppContext()
        audioResManager = appCtx.getAudioResManager()

        val videoList = findViewById<RecyclerView>(R.id.id_audio_list)
        val layoutManager = LinearLayoutManager(this)
        videoList.layoutManager = layoutManager
        videoList.addItemDecoration(VideoListItemDecoration())
        audioListAdapter = AudioListAdapter(appCtx, mainAudios)
        videoList.adapter = audioListAdapter
        audioListAdapter.onAudioClickListener = object: AudioListAdapter.OnAudioClickListener {
            override fun onAudioClicked(book: PkAudio) {
//                val intent = Intent(this@AudioListActivity, PdfActivity::class.java)
//                intent.putExtra("book", book)
//                startActivity(intent)
            }
        }

        val audioSuit = intent.getSerializableExtra("audioSuit") as PkAudioSuit
        audioResManager.queryAudios(audioSuit.id, 1, 20) { audios ->
            if (audios == null) {
                return@queryAudios
            }
            this.mainAudios.removeAll(audios)
            this.mainAudios.addAll(audios)
            appCtx.postUITask {
                audioListAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item)
    }

}