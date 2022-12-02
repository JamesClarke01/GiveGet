package com.giveget.app;


import android.graphics.Camera;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Size;
import android.view.OrientationEventListener;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;
import androidx.lifecycle.LifecycleOwner;

import com.example.giveget.R;
import com.google.common.util.concurrent.ListenableFuture;

import org.jetbrains.annotations.NonNls;

import java.util.concurrent.ExecutionException;

public class CameraActivity1 extends AppCompatActivity {

    PreviewView previewView = (PreviewView) findViewById(R.id.previewView);
    ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(this);

    protected void OnCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_activity);
        cameraProviderFuture.addListener(new Runnable() {
            @Override
            public void run() {
                try {
                    ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                    bindImageAnalysis(cameraProvider);
                }
                catch (ExecutionException | InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }, ContextCompat.getMainExecutor(this));
    }
    public void bindImageAnalysis(@NonNull ProcessCameraProvider cameraProvider)
    {
        ImageAnalysis imgAnalysis = new ImageAnalysis.Builder()
                                        .setTargetResolution(new Size(1080,1080))
                                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                                        .build();//max image size 3MB (uncompressed)
        imgAnalysis.setAnalyzer(ContextCompat.getMainExecutor(this), new ImageAnalysis.Analyzer() {
            @Override
            public void analyze(@NonNull ImageProxy image) {
                image.close();
            }
        });
        Preview pv = new Preview.Builder().build();
        CameraSelector cameraSelect = new CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build();
        pv.setSurfaceProvider(previewView.getSurfaceProvider());
        cameraProvider.bindToLifecycle(
                                        (LifecycleOwner) this,
                                        cameraSelect,
                                        imgAnalysis,
                                        pv
                                      );
    }
}
