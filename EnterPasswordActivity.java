package com.example.vaultapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EnterPasswordActivity extends AppCompatActivity {
EditText editText;
String password;
Button button,reset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_password);
        SharedPreferences settings=getSharedPreferences("PREFS",0);
        password=settings.getString("password","");
        editText=findViewById(R.id.editText3);
        button=findViewById(R.id.button3);
        reset=findViewById(R.id.button18);
        this.setTitle("Password");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String text=editText.getText().toString();
               if(text.equals(password))
               {
                   Intent intent=new Intent(EnterPasswordActivity.this,Home.class);
                   startActivity(intent);
                   finish();
               }
               else{
                   Toast.makeText(EnterPasswordActivity.this, "Wrong password", Toast.LENGTH_SHORT).show();
               }
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(EnterPasswordActivity.this,CreatePasswordActivity.class);
                startActivity(intent);
            }
        });
    }
}
