/*
 * Copyright 2020 Google LLC. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.gibalica.mlkitextensions;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.os.CountDownTimer;
import android.util.Log;
import android.util.Size;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory;

import com.example.gibalica.HomeActivity;
import com.example.gibalica.R;
import com.example.gibalica.mlkitextensions.posedetector.PoseDetectorProcessor;
import com.google.android.gms.common.annotation.KeepName;
//import com.google.mlkit.linkfirebase.FirebaseModelSource;
//import com.example.gibalica.mlkitextensions.automl.AutoMLImageLabelerProcessor;

///import com.example.gibalica.mlkitextensions.automl.AutoMLImageLabelerProcessor.Mode;
import com.example.gibalica.mlkitextensions.preference.PreferenceUtils;
import com.google.mlkit.common.MlKitException;
import com.google.mlkit.vision.pose.PoseDetectorOptionsBase;

import java.util.ArrayList;
import java.util.List;

/** Live preview demo app for ML Kit APIs using CameraX. */
@KeepName
@RequiresApi(VERSION_CODES.LOLLIPOP)
public final class CameraXLivePreviewActivity extends AppCompatActivity
   /** implements OnRequestPermissionsResultCallback,
        OnItemSelectedListener,
        CompoundButton.OnCheckedChangeListener **/
{
  private static final String TAG = "CameraXLivePreview";
  private static final int PERMISSION_REQUESTS = 1;
  private static final String CUSTOM_AUTOML_LABELING = "Custom AutoML Image Labeling";
  private static final String CUSTOM_AUTOML_OBJECT_DETECTION = "Custom AutoML Object Detection";

  private static final String STATE_SELECTED_MODEL = "selected_model";
  private static final String STATE_LENS_FACING = "lens_facing";

  private PreviewView previewView;
  private GraphicOverlay graphicOverlay;

  private static final int REQUEST_CAMERA_PERMISSION = 200;

  private String poseName;
  private int sec;

  @Nullable private ProcessCameraProvider cameraProvider;
  @Nullable private Preview previewUseCase;
  @Nullable private ImageAnalysis analysisUseCase;
  @Nullable private VisionImageProcessor imageProcessor;
  private boolean needUpdateGraphicOverlayImageSourceInfo;

  private String selectedModel = CUSTOM_AUTOML_LABELING;
  private int lensFacing = CameraSelector.LENS_FACING_FRONT;
  private CameraSelector cameraSelector;


  protected int timer = 10000;
  private TextView tw;

  protected CountDownTimer Timer;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Log.d(TAG, "onCreate");

    cameraSelector = new CameraSelector.Builder().requireLensFacing(lensFacing).build();
    Log.d(TAG, "cameraSelector");
    setContentView(R.layout.activity_vision_camerax_live_preview);
    previewView = findViewById(R.id.preview_view);
    tw = (TextView) findViewById(R.id.timeRemainedTextView);
    if (previewView == null) {
      Log.d(TAG, "previewView is null");
    }
    graphicOverlay = findViewById(R.id.graphic_overlay);
    if (graphicOverlay == null) {
      Log.d(TAG, "graphicOverlay is null");
    }


    List<String> options = new ArrayList<>();
    options.add(CUSTOM_AUTOML_LABELING);
    options.add(CUSTOM_AUTOML_OBJECT_DETECTION);

    Bundle b = getIntent().getExtras();
    this.poseName = b.getString("pose");
    this.sec = b.getInt("sec");
    if (this.sec == 0 ||this.sec == -1){
      timer = 10;
    }else{
      timer = this.sec;
    }
    System.out.println("_time: " + timer);
    timer *= 1000;
    System.out.println("_time: " + timer);

    Timer = new CountDownTimer(timer, 1000) {

      public void onTick(long millisUntilFinished) {
        tw.setText("" + millisUntilFinished / 1000);
      }

      public void onFinish() {
        System.out.println("done_");
        imageProcessor.stop();
        int score = imageProcessor.getRepNum();
        System.out.println("REPETITIONS: "  + imageProcessor.getRepNum());
        Intent i = new Intent(getApplicationContext(), TrainingResultActivity.class);
        i.putExtra("score", score);
        i.putExtra("sec", sec);
        startActivity(i);
      }
    };
    // Creating adapter for spinner



    new ViewModelProvider(this, AndroidViewModelFactory.getInstance(getApplication()))
        .get(CameraXViewModel.class)
        .getProcessCameraProvider()
        .observe(
            this,
            provider -> {
              cameraProvider = provider;
              if (allPermissionsGranted()) {
                bindAllCameraUseCases();
              }
            });

    Timer.start();
  }

  @Override
  protected void onSaveInstanceState(@NonNull Bundle bundle) {
    super.onSaveInstanceState(bundle);
    bundle.putString(STATE_SELECTED_MODEL, selectedModel);
    bundle.putInt(STATE_LENS_FACING, lensFacing);
  }


  @Override
  public void onResume() {
    super.onResume();
    bindAllCameraUseCases();
  }

  @Override
  protected void onPause() {
    super.onPause();
    if (imageProcessor != null) {
      imageProcessor.stop();
    }
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    if (imageProcessor != null) {
      imageProcessor.stop();
    }
  }

  private void bindAllCameraUseCases() {
    bindPreviewUseCase();
    bindAnalysisUseCase();
  }

  private void bindPreviewUseCase() {
   /* if (!PreferenceUtils.isCameraLiveViewportEnabled(this)) {
      return;
    }*/
    if (cameraProvider == null) {
      return;
    }
    if (previewUseCase != null) {
      cameraProvider.unbind(previewUseCase);
    }

    Preview.Builder builder = new Preview.Builder();
    Size targetResolution = PreferenceUtils.getCameraXTargetResolution(this, lensFacing);
    if (targetResolution != null) {
      builder.setTargetResolution(targetResolution);
    }
    previewUseCase = builder.build();
    previewUseCase.setSurfaceProvider(previewView.getSurfaceProvider());
    cameraProvider.bindToLifecycle(/* lifecycleOwner= */ this, cameraSelector, previewUseCase);
  }

  private void bindAnalysisUseCase() {
    if (cameraProvider == null) {
      return;
    }
    if (analysisUseCase != null) {
      cameraProvider.unbind(analysisUseCase);
    }
    if (imageProcessor != null) {
      imageProcessor.stop();
    }

    try {
      /*String autoMLRemoteModelName = PreferenceUtils.getAutoMLRemoteModelName(this);
      switch (selectedModel) {
        case CUSTOM_AUTOML_LABELING:
          Log.i(TAG, "Create Custom AutoML Image Label Processor");
                    CustomRemoteModel customRemoteModel =
              new CustomRemoteModel.Builder(
                      new FirebaseModelSource.Builder(autoMLRemoteModelName).build())
                  .build();

          CustomImageLabelerOptions customImageLabelerOptions =
              new CustomImageLabelerOptions.Builder(customRemoteModel)
                  .setConfidenceThreshold(0)
                  .build();
          System.out.println("----------------------------------------------------");
          System.out.println(customRemoteModel);
          System.out.println( customImageLabelerOptions);
          System.out.println("----------------------------------------------------");
          imageProcessor =
              new AutoMLImageLabelerProcessor(
                  this, customRemoteModel, customImageLabelerOptions, AutoMLImageLabelerProcessor.Mode.LIVE_PREVIEW);
          break;

        default:
          throw new IllegalStateException("Invalid model name");*/
      PoseDetectorOptionsBase poseDetectorOptions =
              PreferenceUtils.getPoseDetectorOptionsForLivePreview(this);
      boolean shouldShowInFrameLikelihood = true;
              //PreferenceUtils.shouldShowPoseDetectionInFrameLikelihoodLivePreview(this);
      boolean visualizeZ = true;//PreferenceUtils.shouldPoseDetectionVisualizeZ(this);
      boolean rescaleZ = true;//PreferenceUtils.shouldPoseDetectionRescaleZForVisualization(this);
      boolean runClassification = true;//PreferenceUtils.shouldPoseDetectionRunClassification(this);
      System.out.println("POSE: "+this.poseName);
      imageProcessor =
              new PoseDetectorProcessor(
                      this,
                      poseDetectorOptions,
                      this.poseName,
                      shouldShowInFrameLikelihood,
                      visualizeZ,
                      rescaleZ,
                      runClassification,
                      true);

    } catch (RuntimeException e) {
      Log.e(TAG, "Can not create image processor: " + selectedModel, e);
      Toast.makeText(
              getApplicationContext(),
              "Can not create image processor: " + e.getLocalizedMessage(),
              Toast.LENGTH_LONG)
          .show();
      return;
    }

    ImageAnalysis.Builder builder = new ImageAnalysis.Builder();
    Size targetResolution = PreferenceUtils.getCameraXTargetResolution(this, lensFacing);
    if (targetResolution != null) {
      builder.setTargetResolution(targetResolution);
    }
    analysisUseCase = builder.build();

    needUpdateGraphicOverlayImageSourceInfo = true;
    analysisUseCase.setAnalyzer(
        //imageProcessor.processImageProxy will use another thread to run the detection underneath,
        // thus we can just runs the analyzer itself on main thread.
        ContextCompat.getMainExecutor(this),
        imageProxy -> {
          if (needUpdateGraphicOverlayImageSourceInfo) {
            boolean isImageFlipped = lensFacing == CameraSelector.LENS_FACING_FRONT;
            int rotationDegrees = imageProxy.getImageInfo().getRotationDegrees();
            if (rotationDegrees == 0 || rotationDegrees == 180) {
              graphicOverlay.setImageSourceInfo(
                  imageProxy.getWidth(), imageProxy.getHeight(), isImageFlipped);
            } else {
              graphicOverlay.setImageSourceInfo(
                  imageProxy.getHeight(), imageProxy.getWidth(), isImageFlipped);
            }
            needUpdateGraphicOverlayImageSourceInfo = false;
          }
          try
          {
            imageProcessor.processImageProxy(imageProxy, graphicOverlay);
          } catch (MlKitException e) {
           Log.e(TAG, "Failed to process image. Error: " + e.getLocalizedMessage());
            Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT)
                .show();
         }
        });

    cameraProvider.bindToLifecycle(/* lifecycleOwner= */ this, cameraSelector, analysisUseCase);
  }

  private String[] getRequiredPermissions() {
    try {
      PackageInfo info =
          this.getPackageManager()
              .getPackageInfo(this.getPackageName(), PackageManager.GET_PERMISSIONS);
      String[] ps = info.requestedPermissions;
      if (ps != null && ps.length > 0) {
        return ps;
      } else {
        return new String[0];
      }
    } catch (Exception e) {
      return new String[0];
    }
  }

  private boolean allPermissionsGranted() {
    for (String permission : getRequiredPermissions()) {
      if (!isPermissionGranted(this, permission)) {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
          ActivityCompat.requestPermissions(this,new String[]{
                  Manifest.permission.CAMERA
          },200);

        }

        return false;
      }
    }
    return true;
  }

  private void getRuntimePermissions() {
    List<String> allNeededPermissions = new ArrayList<>();
    for (String permission : getRequiredPermissions()) {
      if (!isPermissionGranted(this, permission)) {
        allNeededPermissions.add(permission);
      }
    }

    if (!allNeededPermissions.isEmpty()) {
      ActivityCompat.requestPermissions(
          this, allNeededPermissions.toArray(new String[0]), PERMISSION_REQUESTS);
    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if (requestCode == REQUEST_CAMERA_PERMISSION) {
      if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
        Toast.makeText(this, "You can't use camera without permission", Toast.LENGTH_SHORT).show();
        finish();
      }
    }
  }

  private static boolean isPermissionGranted(Context context, String permission) {
    if (ContextCompat.checkSelfPermission(context, permission)
        == PackageManager.PERMISSION_GRANTED) {
      Log.i(TAG, "Permission granted: " + permission);
      return true;
    }
    Log.i(TAG, "Permission NOT granted: " + permission);
    return true;
  }

  @Override
  public void onBackPressed() {
    imageProcessor.stop();
    Timer.cancel();
    Intent i = new Intent(this, HomeActivity.class);
    startActivity(i);
  }

}
