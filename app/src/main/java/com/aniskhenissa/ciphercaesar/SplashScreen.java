package com.aniskhenissa.ciphercaesar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ScheduledExecutorService worker = Executors.newSingleThreadScheduledExecutor();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        };
        worker.schedule(runnable, 2300, TimeUnit.MILLISECONDS);
    }
}