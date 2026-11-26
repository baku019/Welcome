package com.example.welcome

import android.app.Service
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Handler
import android.os.IBinder

class WelcomeService : Service() {

    private var player: MediaPlayer? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Handler(mainLooper).postDelayed({ play() }, 3000)
        return START_NOT_STICKY
    }

    private fun play() {
        val mp = MediaPlayer.create(this, R.raw.welcome)
        if (mp == null) {
            stopSelf()
            return
        }
        player = mp
        mp.setAudioAttributes(
            AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                .build()
        )
        mp.setOnCompletionListener {
            it.release()
            player = null
            stopSelf()
        }
        mp.start()
    }

    override fun onDestroy() {
        player?.release()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
