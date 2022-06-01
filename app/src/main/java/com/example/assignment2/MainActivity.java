package com.example.assignment2;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Handler;
import android.view.ViewGroup;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.database.sqlite.*;
import android.database.sqlite.*;
import com.example.assignment2.databinding.ActionbarBinding;
import com.example.assignment2.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        controller control=new controller(getApplicationContext());
        boolean b=control.isAnyOneActive();
        Toast.makeText(getApplicationContext(),"Result:"+b,Toast.LENGTH_SHORT).show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(b){
                    finish();
                    startActivity(new Intent(getApplicationContext(),storeHome.class));
                }else {
                    finish();
                    startActivity(new Intent(getApplicationContext(),signIn.class));
                }
            }
        }, 1500);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
