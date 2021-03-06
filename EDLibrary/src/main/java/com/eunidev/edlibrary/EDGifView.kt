package com.eunidev.edlibrary

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.VideoView

class EDGifView : LinearLayout {
    private var isUsingUrl = false
    private var isVideoViewInitialize = false

    private lateinit var videoView: VideoView

    private var url: String?
    private lateinit var uri: Uri

    // Public Value
    var isUriAdded = false

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttrs: Int) : super(context, attrs, defStyleAttrs) {
        orientation = VERTICAL
        LayoutInflater.from(context).inflate(R.layout.gifview_layout, this, true)

        val ta = context.theme.obtainStyledAttributes(attrs, R.styleable.EDGifView, 0, 0)

        try {
            url = ta.getString(R.styleable.EDGifView_useUrl)
        } finally {
            ta.recycle()
        }

        isUsingUrl = url != null

        init()
    }

    private fun init() {
        videoView = findViewById(R.id.videoView)
        isVideoViewInitialize = true

        if (isUsingUrl and (url != null)) {
            videoView.setVideoURI(Uri.parse(url))
        } else {
            Log.i("EDGifView", "No Path Provided")
        }
    }

    fun setVideoUri(uri: Uri) {
        videoView.setVideoURI(uri)
        this.uri = uri
        isUriAdded = true
    }

    fun setOnCompletionListener(listener: MediaPlayer.OnCompletionListener) {
        videoView.setOnCompletionListener(listener)
    }

    fun isPlaying() = videoView.isPlaying

    fun getDruation(): Int {
        if (isVideoViewInitialize) {
            return videoView.duration
        }

        return 0
    }

    fun getCurrentPosition(): Int {
        return videoView.currentPosition
    }

    private fun restart() {
        videoView.setVideoURI(uri)
        start()
    }

    fun start() {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed(run(handler), 20)

        videoView.start()
    }

    fun resume() = videoView.resume()
    fun pause() = videoView.pause()
    fun stop() = videoView.stopPlayback()

    private fun run(handler: Handler): Runnable {
        return Runnable {
            if (getCurrentPosition() >= 30000) {
                restart()
            } else {
                handler.postDelayed(run(handler), 20)
            }
        }
    }
}