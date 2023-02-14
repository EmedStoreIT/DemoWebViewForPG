package com.rum.myapplication;

import android.content.Context;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.rum.myapplication.databinding.ActivitySignatureBinding;
import com.rum.myapplication.generalHelper.DrawingView;

public class SignatureActivity extends AppCompatActivity {

    private Context mContext;
    private ActivitySignatureBinding binding;

    private boolean isPaintEnabled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signature);

        setListeners();
        initDrawingView();
        initAndPlayVideo();

        hidePaint();
    }

    private void setListeners() {
        binding.btnStartStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isPaintEnabled) {
                    hidePaint();
                } else {
                    showPaintOption();
                }
            }
        });
    }

    private void initDrawingView() {
        Paint mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(ContextCompat.getColor(mContext, R.color.colorOrange));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(8);
        DrawingView drawingView = new DrawingView(mContext, mPaint);

        binding.llSignatureView.addView(drawingView);
        binding.llSignatureView.invalidate();
    }

    private void initAndPlayVideo() {
        MediaController mediaController= new MediaController(mContext);
        mediaController.setAnchorView(binding.videoView);
        Uri uri=Uri.parse("http://techslides.com/demos/sample-videos/small.mp4");
        binding.videoView.setMediaController(mediaController);
        binding.videoView.setVideoURI(uri);
        binding.videoView.requestFocus();

        binding.videoView.start();
    }

    private void showPaintOption() {
        isPaintEnabled = true;
        binding.llSignatureView.setVisibility(View.VISIBLE);
    }

    private void hidePaint() {
        isPaintEnabled = false;
        binding.llSignatureView.setVisibility(View.GONE);
    }
}