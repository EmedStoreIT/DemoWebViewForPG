package com.rum.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.common.util.Util
import androidx.media3.exoplayer.ExoPlayer
import com.rum.myapplication.databinding.ActivitySignatureBinding
import com.rum.myapplication.generalHelper.DrawingView

@UnstableApi
class SignatureActivity2 : AppCompatActivity() {
    private lateinit var mContext: Context
    private lateinit var binding: ActivitySignatureBinding

    private lateinit var drawingView: DrawingView
    private var isPaintEnabled = false

    private var player: ExoPlayer? = null
    private var playWhenReady = true
    private var currentItem = 0
    private var playbackPosition = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mContext = this
        binding = ActivitySignatureBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initComponents()
        setListeners()
    }

    private fun initComponents() {
        hideProgress()
        hidePaint()
        initDrawingView()
    }

    private fun setListeners() {
        binding.btnStartStop.setOnClickListener {
            if (isPaintEnabled) {
                hidePaint()
            } else {
                showPaintOption()
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
    }

    public override fun onStart() {
        super.onStart()
        if (Util.SDK_INT > 23) {
            initializePlayer()
        }
    }

    public override fun onResume() {
        super.onResume()
        hideSystemUi()
        if ((Util.SDK_INT <= 23 || player == null)) {
            initializePlayer()
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
        drawingView= DrawingView(mContext, mPaint)
        binding.llSignatureView.addView(drawingView)
        binding.llSignatureView.invalidate()
    }

    private fun initializePlayer() {
        player = ExoPlayer.Builder(this)
            .build()
            .also { exoPlayer ->
                binding.videoView.player = exoPlayer

                val mediaItem = MediaItem.fromUri(getString(R.string.media_url_mp4))
                exoPlayer.setMediaItem(mediaItem)

                exoPlayer.playWhenReady = playWhenReady
                exoPlayer.seekTo(currentItem, playbackPosition)
                exoPlayer.prepare()

            }
    }

    private fun showPaintOption() {
        isPaintEnabled = true
        binding.llSignatureView.visibility = View.VISIBLE
    }

    private fun hidePaint() {
        isPaintEnabled = false
        binding.llSignatureView.visibility = View.GONE
    }

    private fun hideProgress() {
        binding.progressBar.visibility = View.GONE
    }

    private fun showProgress() {
        binding.progressBar.visibility = View.VISIBLE
    }
}