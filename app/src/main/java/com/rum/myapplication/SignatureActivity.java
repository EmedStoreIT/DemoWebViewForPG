package com.rum.myapplication;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.rum.myapplication.databinding.ActivitySignatureBinding;
import com.rum.myapplication.generalHelper.DrawingView;

public class SignatureActivity extends AppCompatActivity {

    private Context mContext;
    private ActivitySignatureBinding binding;
    private DrawingView drawingView;
    private boolean isImageSaved = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signature);

        initDrawingView();


        binding.llSignatureView.addView(drawingView);
        binding.llSignatureView.invalidate();
    }

    private void initDrawingView() {
        Paint mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(ContextCompat.getColor(this, R.color.colorBlack));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(8);
        drawingView = new DrawingView(mContext, mPaint);
    }
}