package com.emedstore.video_with_paint

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Paint
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.emedstore.video_with_paint.databinding.ActivityVideoWithPaintBinding
import com.emedstore.video_with_paint.generalHelper.DrawingView

class VideoWithPaintActivity : Activity() {
    private lateinit var mContext: Context
    private lateinit var binding: ActivityVideoWithPaintBinding
    private lateinit var drawingView: DrawingView

    private var player: ExoPlayer? = null
    private var playWhenReady = true
    private var currentItem = 0
    private var playbackPosition = 0L

    private val playbackStateListener: Player.Listener = playbackStateListener()

    private var playerIsPlaying: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mContext = this
        binding = ActivityVideoWithPaintBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initComponents()
        setListeners()
    }

    private fun initComponents() {
        hideProgress()
        hidePaint()
        initDrawingView()

        val showPaintOptions =
            intent.getBooleanExtra(getString(R.string.bundle_key_pass_show_paint_options), false)
        binding.trPaintOptions.visibility = if (showPaintOptions) View.VISIBLE else View.GONE
    }

    private fun setListeners() {
        binding.btnPaint.setOnClickListener {
            if (playerIsPlaying) {
                setPlaying(false)
                showPaintOption()
            } else {
                setPlaying(true)
                hidePaint()
            }
        }

        binding.btnClear.setOnClickListener {
            try {
                drawingView.clear()
                drawingView.invalidate()
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }

        binding.btnCloseVideo.setOnClickListener { finish() }
    }

    public override fun onStart() {
        super.onStart()
//        if (Util.SDK_INT > 23) {
        checkUrlAndProceed()
//        }
    }

    public override fun onResume() {
        super.onResume()
        hideSystemUi()
        if ((/*Util.SDK_INT <= 23 ||*/ player == null)) {
            checkUrlAndProceed()
        }
    }

    @SuppressLint("InlinedApi")
    private fun hideSystemUi() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, binding.videoView).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

    private fun initDrawingView() {
        val mPaint = Paint()
        mPaint.isAntiAlias = true
        mPaint.isDither = true
        mPaint.color = ContextCompat.getColor(mContext, R.color.colorRed)
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeJoin = Paint.Join.ROUND
        mPaint.strokeCap = Paint.Cap.ROUND
        mPaint.strokeWidth = 8f
        drawingView = DrawingView(mContext, mPaint)
        binding.llSignatureView.addView(drawingView)
        binding.llSignatureView.invalidate()
    }

    private fun checkUrlAndProceed() {
        val videoUrl = intent.getStringExtra(getString(R.string.bundle_key_pass_video_url))
        if (videoUrl != null && !TextUtils.isEmpty(videoUrl)) {
            showDataFound()
            initializePlayer(videoUrl)
        } else {
            showNoDataFound()
        }
    }

    private fun initializePlayer(videoUrl: String?) {
        if (videoUrl != null && !TextUtils.isEmpty(videoUrl)) {
            showDataFound()

            player = ExoPlayer.Builder(mContext).build().also { exoPlayer ->
                binding.videoView.player = exoPlayer

                val mediaItem = MediaItem.fromUri(videoUrl)
                exoPlayer.setMediaItem(mediaItem)

                exoPlayer.playWhenReady = playWhenReady
                exoPlayer.seekTo(currentItem, playbackPosition)
                exoPlayer.addListener(playbackStateListener)
                exoPlayer.prepare()
            }
        }
    }

    private fun releasePlayer() {
        player?.let { exoPlayer ->
            exoPlayer.removeListener(playbackStateListener)
            exoPlayer.release()
        }
        player = null
    }

    private fun showPaintOption() {
        binding.llSignatureView.visibility = View.VISIBLE

        binding.btnPaint.background =
            AppCompatResources.getDrawable(mContext, R.drawable.marker_enable)

        binding.btnClear.visibility = View.VISIBLE
    }

    private fun hidePaint() {
        binding.llSignatureView.visibility = View.GONE

        binding.btnPaint.background =
            AppCompatResources.getDrawable(mContext, R.drawable.marker_disable)

        binding.btnClear.visibility = View.GONE
    }

    private fun hideProgress() {
        binding.progressBar.visibility = View.GONE
    }

    private fun showProgress() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun setPlaying(setPlay: Boolean) {
        if (player != null) {
            player!!.playWhenReady = setPlay

            binding.videoView.useController = setPlay
        }
    }

    private fun playbackStateListener() = object : Player.Listener {
        override fun onPlaybackStateChanged(playbackState: Int) {
            val stateString: String = when (playbackState) {
                ExoPlayer.STATE_IDLE -> "ExoPlayer.STATE_IDLE      -"
                ExoPlayer.STATE_BUFFERING -> "ExoPlayer.STATE_BUFFERING -"
                ExoPlayer.STATE_READY -> "ExoPlayer.STATE_READY     -"
                ExoPlayer.STATE_ENDED -> "ExoPlayer.STATE_ENDED     -"
                else -> "UNKNOWN_STATE             -"
            }
            Log.e("", "changed state to $stateString")


            /*if (playWhenReady && playbackState == Player.STATE_READY) {
                // media actually playing
            } else if (playWhenReady) {
                // might be idle (plays after prepare()),
                // buffering (plays when data available)
                // or ended (plays when seek away from end)
            } else {
                // player paused in any state
            }*/
        }

        override fun onIsPlayingChanged(isPlaying: Boolean) {
            playerIsPlaying = isPlaying
            super.onIsPlayingChanged(isPlaying)
        }
    }

    private fun showDataFound() {
        binding.llMain.visibility = View.VISIBLE
        binding.llError.visibility = View.GONE
    }

    private fun showNoDataFound() {
        binding.llMain.visibility = View.GONE
        binding.llError.visibility = View.VISIBLE
        binding.btnReturn.setOnClickListener {
            finish()
        }

        binding.btnPlayDemoVideo.setOnClickListener {
            initializePlayer(getString(R.string.media_url_mp4))
        }
    }
}