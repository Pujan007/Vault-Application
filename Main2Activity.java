package com.example.vaultapp;

import androidx.annotation.NonNull;
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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class Main2Activity extends AppCompatActivity {
Button button;
String filePath;
    static String salt="t784";
    String cryptpassword="873147cbn9x5'2 79'79314";
TextView textView;
    StringBuilder sb = new StringBuilder();
    String strLine = "";
    String str_data = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        button=findViewById(R.id.button6);
        textView=findViewById(R.id.textView3);
        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1002);
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialFilePicker()
                        .withActivity(Main2Activity.this)
                        .withRequestCode(1000)
                        .withHiddenFiles(true)
                        .start();
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1000 && resultCode == RESULT_OK) {
            filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            textView.setText(filePath);
            try {
                String abc= decrypt(filePath,cryptpassword);
                Log.e("value",abc);
            }
            catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                Toast.makeText(Main2Activity.this, "2", Toast.LENGTH_SHORT).show();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
                Toast.makeText(Main2Activity.this, "3", Toast.LENGTH_SHORT).show();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
                Toast.makeText(Main2Activity.this, "4", Toast.LENGTH_SHORT).show();
            } catch (NoSuchProviderException e) {
                e.printStackTrace();
                Toast.makeText(Main2Activity.this, "5", Toast.LENGTH_SHORT).show();;
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(Main2Activity.this, "file", Toast.LENGTH_SHORT).show();;

            }

        }
    }


    public String decrypt(String path, String password) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, NoSuchProviderException {
        Log.e("value",path);

        FileOutputStream fos = new FileOutputStream("storage/emulated/0/");
        byte[] key=(salt+password).getBytes("UTF-8");
        MessageDigest sha=MessageDigest.getInstance("SHA-1");
        key=sha.digest(key);
        key= Arrays.copyOf(key,16);
        Log.e("value1", String.valueOf(key));
        SecretKeySpec sks = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7PADDING", "BC");
        cipher.init(Cipher.DECRYPT_MODE, sks);
        fos.flush();
        fos.close();

        Toast.makeText(Main2Activity.this,"decypt",Toast.LENGTH_LONG).show();
        return "Encrypt complemets";
    }

}

