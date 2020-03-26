package com.bekaim.highping.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.bekaim.highping.MainActivity;
import com.bekaim.highping.R;
import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {

    public static Boolean started = false;
    private FirebaseAuth mAuth;

    private final int SPLASH_DISPLAY_LENGTH = 1000;
    private Context mContext = SplashActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                mAuth = FirebaseAuth.getInstance();
                if (mAuth.getCurrentUser() != null) {
//                    FirebaseDatabase.getInstance().getReference().keepSynced(true);
                    Intent intent = new Intent(mContext, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );
                    startActivity(intent);
                    finish();

                } else {
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
//                Intent intent = new Intent(mContext, LoginActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
//                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
