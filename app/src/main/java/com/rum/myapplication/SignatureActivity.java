package com.rum.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.rum.myapplication.databinding.ActivitySignatureBinding;
import com.rum.myapplication.generalHelper.DrawingView;
import com.rum.myapplication.generalHelper.L;
import com.rum.myapplication.generalHelper.SP;

import java.io.File;
import java.io.FileOutputStream;

public class SignatureActivity extends AppCompatActivity implements View.OnClickListener {
    private DrawingView drawingView;
    private ActivitySignatureBinding binding;
    private Context mContext;
    private boolean isImageSaved = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signature);

        initDrawingView();


        binding.llSignatureView.addView(drawingView);
        binding.llSignatureView.invalidate();
//
        binding.btnSaveSignature.setOnClickListener(this);
        binding.btnClearSignature.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSaveSignature:
                saveSignature();
                break;
            case R.id.btnClearSignature:
                SP.savePreferences(mContext, SP.SIGNATURE_IMAGE_PATH, "");
                drawingView.clear();
                drawingView.invalidate();
                break;
        }
    }

    public void saveSignature() {
        if (!DrawingView.haveDrawSomething) {
            L.showToast(mContext, getString(R.string.take_valid_signature));
            return;
        }

        binding.llSignatureView.setDrawingCacheEnabled(true);
        binding.llSignatureView.buildDrawingCache(true);
        Bitmap bitmap = binding.llSignatureView.getDrawingCache();

        File pictureFile = getOutputMediaFile();
        if (pictureFile == null) {
            L.showToast(mContext, getString(R.string.error_saving_signature));
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.close();
            isImageSaved = true;
        } catch (Exception e) {
            isImageSaved = false;
        } finally {
            binding.llSignatureView.setDrawingCacheEnabled(false);
            binding.llSignatureView.buildDrawingCache(false);
            drawingView.clear();
            drawingView.invalidate();

            if (isImageSaved) {
                SP.savePreferences(mContext, SP.SIGNATURE_IMAGE_PATH, pictureFile.toString());
                L.showToast(mContext, getString(R.string.signatur_saved));
                Intent intent = new Intent();
                setResult(Activity.RESULT_OK, intent);
                finish();
            } else {
                L.showToast(mContext, getString(R.string.error_saving_signature));
            }
        }
    }

    private File getOutputMediaFile() {
        File mediaStorageDir = new File(getExternalFilesDir(null).toString() + "/" + getString(R.string.app_name));
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                L.showToast(mContext, getString(R.string.error_saving_signature));
            }
        }
        File mediaFile;
        String mImageName = "SIGN_" + System.currentTimeMillis() + ".png";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }
}