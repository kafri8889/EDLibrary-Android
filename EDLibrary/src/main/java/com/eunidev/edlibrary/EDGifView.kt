package com.eunidev.edlibrary

import android.content.Context
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.VideoView

class EDGifView : LinearLayout {

    private val defaultDuration = 30000
    private var customDuration = 0
    private var startWithDuration = false
    private var isUsingUrl = false
    private var isVideoViewInitialize = false

    private lateinit var videoView: VideoView
    private lateinit var onCompletionListener: OnCompletionListener

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

    fun setOnCompletionListener(listener: OnCompletionListener) {
        this.onCompletionListener = listener
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

    fun muteAudio() {
        videoView.setOnPreparedListener {
            it.setVolume(0f,0f)
        }
    }

    private fun restart() {
        videoView.setVideoURI(uri)
        if (startWithDuration) {
            start(customDuration)
        } else {
            start()
        }
    }

    fun start() {
        startWithDuration = false
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed(run(handler, null), 20)

        videoView.start()
    }

    fun start(duration: Int) {
        customDuration = duration
        startWithDuration = true
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed(run(handler, duration), 20)

        videoView.start()
    }

    fun resume() = videoView.resume()
    fun pause() = videoView.pause()
    fun stop() = videoView.stopPlayback()

    private fun run(handler: Handler, duration: Int?): Runnable {

        return Runnable {
            if (duration == null) {
                if (getCurrentPosition() >= defaultDuration) {
                    onCompletionListener.onComplete(defaultDuration)
                    restart()
                } else {
                    handler.postDelayed(run(handler, duration), 20)
                }
            } else {
                if (getCurrentPosition() >= duration) {
                    onCompletionListener.onComplete(duration)
                    restart()
                } else {
                    handler.postDelayed(run(handler, duration), 20)
                }
            }
        }
    }

    interface OnCompletionListener {

        fun onComplete(durationSet: Int)
    }
}