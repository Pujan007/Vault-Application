package com.example.vaultapp;

import android.os.Environment;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import java.io.*;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Objects;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity {
   Button button,button1,button2;
TextView textView;
String filePath;
byte fileContent[];
byte[] encrypteddata;
    private static final int pswdIterations = 10;
    private static final int keySize = 256;
    private static final String cypherInstance = "AES/CBC/PKCS5Padding";
    private static final String secretKeyInstance = "PBKDF2WithHmacSHA1";
    private static final String plainText = "sampleText";
    private static final String AESSalt = "exampleSalt";
    private static final String initializationVector = "8119745113154120";
    byte[] encrypted;
    String key="ABCDEFGHIJKLMNOP";
    File file;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("Hide File");

        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1001);
        }


        button=findViewById(R.id.button4);
        button1=findViewById(R.id.button5);

         button.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 new MaterialFilePicker()
                         .withActivity(MainActivity.this)
                         .withRequestCode(1000)
                         .withHiddenFiles(true) // Show hidden files and folders
                         .start();
             }
         });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialFilePicker()
                        .withActivity(MainActivity.this)
                        .withRequestCode(1002)
                        .withHiddenFiles(true) // Show hidden files and folders
                        .start();
            }
        });


    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1000 && resultCode == RESULT_OK) {
             filePath =(data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH));
            file=new File(filePath);


            try {
                doCrypto(Cipher.ENCRYPT_MODE, key, file, file);
                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("value",String.valueOf(e));
            }


        }
        else if(requestCode == 1002 && resultCode == RESULT_OK)
        {
            filePath =(data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH));
            file=new File(filePath);


            try {
                doCrypto(Cipher.DECRYPT_MODE, key, file, file);
                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("value",String.valueOf(e));
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1001:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission not Granted", Toast.LENGTH_SHORT).show();
                    finish();
                }
        }

    }

    private static void doCrypto(int cipherMode, String key, File inputFile,
                                 File outputFile)  {
        try {
            Key secretKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(cipherMode, secretKey);

            FileInputStream inputStream = new FileInputStream(inputFile);
            byte[] inputBytes = new byte[(int) inputFile.length()];
            inputStream.read(inputBytes);

            byte[] outputBytes = cipher.doFinal(inputBytes);

            FileOutputStream outputStream = new FileOutputStream(outputFile);
            outputStream.write(outputBytes);

            inputStream.close();
            outputStream.close();

        } catch (NoSuchPaddingException | NoSuchAlgorithmException
                | InvalidKeyException | BadPaddingException
                | IllegalBlockSizeException | IOException ex) {

        }
    }

}
