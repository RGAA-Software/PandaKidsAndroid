package com.tc.reading.ui.audio

import android.media.MediaPlayer
import android.media.MediaPlayer.OnCompletionListener
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tc.reading.App
import com.tc.reading.AppContext
import com.tc.reading.R
import com.tc.reading.entity.PkAudio
import com.tc.reading.entity.PkAudioSuit
import com.tc.reading.res.AudioResManager
import com.tc.reading.ui.video.VideoListItemDecoration
import com.tc.reading.util.ScreenUtil
import java.util.Timer
import java.util.TimerTask

class AudioListActivity : AppCompatActivity() {
    private val TAG = "AudioList"

    private lateinit var appCtx: AppContext
    private lateinit var audioResManager: AudioResManager
    private var mainAudios: MutableList<PkAudio> = mutableListOf()
    private lateinit var audioListAdapter: AudioListAdapter
    private lateinit var playingTitle: TextView
    private lateinit var playingControl: SeekBar
    private lateinit var mediaPlayer: MediaPlayer
    private var progressTimer: Timer = Timer()
    private var playingCompleted = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ScreenUtil.makeActivityFullScreen(this)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setContentView(R.layout.activity_audio_list)
        appCtx = (application as App).getAppContext()
        audioResManager = appCtx.getAudioResManager()
        playingTitle = findViewById(R.id.id_playing_audio_title)
        playingControl = findViewById(R.id.id_play_control_bar)
        mediaPlayer = MediaPlayer()

        val videoList = findViewById<RecyclerView>(R.id.id_audio_list)
        val layoutManager = LinearLayoutManager(this)
        videoList.layoutManager = layoutManager
        videoList.addItemDecoration(VideoListItemDecoration())
        audioListAdapter = AudioListAdapter(appCtx, mainAudios)
        videoList.adapter = audioListAdapter
        audioListAdapter.onAudioClickListener = object: AudioListAdapter.OnAudioClickListener {
            override fun onAudioClicked(audio: PkAudio) {
                playingTitle.text = audio.name
                Log.i(TAG, "is playing : " + mediaPlayer.isPlaying)
                if (mediaPlayer.isPlaying || playingCompleted) {
                    mediaPlayer.stop()
                    mediaPlayer.reset()
                }
                val url = appCtx.getBaseServerUrl() + "/" + audio.file
                mediaPlayer.setOnBufferingUpdateListener { mp, percent ->
                    Log.i(TAG, "percent: " + percent)
                }
                mediaPlayer.setOnSeekCompleteListener {

                }
                mediaPlayer.setOnPreparedListener { mp ->
                    playingCompleted = false
                    appCtx.postUITask {
                        playingControl.max = mp.duration
                    }
                }
                mediaPlayer.setOnCompletionListener { mp ->
                    playingCompleted = true
                }
                mediaPlayer.setOnErrorListener { mp, what, extra ->
                    playingCompleted = true
                    true
                }

                mediaPlayer.setDataSource(url)
                mediaPlayer.prepare()
                mediaPlayer.start()
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

        playingControl.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                Log.i(TAG, "progress: $progress")
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                Log.i(TAG, "progress touch end: " + seekBar!!.progress)

                if (mediaPlayer.isPlaying) {
                    mediaPlayer.seekTo(seekBar.progress)
                }
            }

        })

        progressTimer.schedule(object: TimerTask() {
            override fun run() {
                appCtx.postUITask {
                    if (mediaPlayer.isPlaying) {
                        playingControl.progress = mediaPlayer.currentPosition
                    }
                }
            }
        }, 300, 1000)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        progressTimer.cancel()
    }

}