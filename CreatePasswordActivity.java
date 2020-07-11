package com.example.vaultapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreatePasswordActivity extends AppCompatActivity {
 EditText editText,editText1;
 Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_password);
        editText=findViewById(R.id.editText);
        editText1=findViewById(R.id.editText2);
        button=findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text=editText.getText().toString().trim();
                String text1=editText1.getText().toString().trim();
                if(text.equals("") || text1.equals(""))
                {
                    Toast.makeText(CreatePasswordActivity.this, "No password", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(text.equals(text1))
                    {
                        SharedPreferences settings=getSharedPreferences("PREFS",0);
                        SharedPreferences.Editor editor=settings.edit();
                        editor.putString("password",text1);
                        editor.apply();
                        Intent intent=new Intent(CreatePasswordActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();

                    }
                    else
                    {
                        Toast.makeText(CreatePasswordActivity.this, " password not match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
