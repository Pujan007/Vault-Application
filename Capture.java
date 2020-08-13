package com.example.vaultapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Capture extends AppCompatActivity  implements SurfaceHolder.Callback{
        Camera camera;
        SurfaceView surfaceView;
        SurfaceHolder surfaceHolder;
        Camera.PictureCallback jpegCallback;
        private final String tag = "VideoServer";
        Button start;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_capture);
            start =  findViewById(R.id.button);
            surfaceView=findViewById(R.id.surfaceView);
            surfaceHolder=surfaceView.getHolder();
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    start.performClick();
                }
            }, 5000);
            start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    camera.takePicture(null,null,jpegCallback);
                    Toast.makeText(Capture.this, "Sorry your login attempt are over", Toast.LENGTH_SHORT).show();
                }
            });
            jpegCallback=new Camera.PictureCallback() {
                @Override
                public void onPictureTaken(byte[] bytes, Camera camera) {
                    {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                        Bitmap bitmap1=Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),null,true);
                        String path=currentDateFromat();
                        storephoto(bitmap1,path);
                        Capture.this.camera.startPreview();
                    }
                }
            };
        }

        private void storephoto(Bitmap bitmap1, String path) {

            File output=new File(Environment.getExternalStorageDirectory(),"/DCIM"+"photo"+path+".jpeg");
            try {
                {
                    FileOutputStream fileOutputStream=new FileOutputStream(output);
                    bitmap1.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);
                    fileOutputStream.close();
                    fileOutputStream.flush();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        private String currentDateFromat() {

            SimpleDateFormat dateFormat =new SimpleDateFormat("yyyyMMdd_HH_mm_ss");
            String current=dateFormat.format(new Date());
            return current;
        }

        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            Camera.CameraInfo cameraInfo=new Camera.CameraInfo();
            try {
                camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);


            } catch (RuntimeException e) {
                Log.e(tag, "init_camera: " + e);
                return;
            }

            Camera.Parameters param;
            param = camera.getParameters();
            //modify parameter
            param.set("camera is",2);
            param.setPreviewFrameRate(20);
            param.setPreviewSize(176, 144);
            camera.setParameters(param);
            try {
                camera.setPreviewDisplay(surfaceHolder);
                camera.startPreview();
            } catch (Exception e) {
                Log.e(tag, "init_camera: " + e);
                return;
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            camera.stopPreview();
            camera.release();
            camera=null;
        }
    }
