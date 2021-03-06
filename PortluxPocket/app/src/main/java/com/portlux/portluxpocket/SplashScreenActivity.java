package com.portlux.portluxpocket;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.Window;
import android.widget.ImageView;

import com.koushikdutta.ion.Ion;


public class SplashScreenActivity extends Activity {

    // Splash screen timer


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_splash_screen);
        ImageView loggo = (ImageView)findViewById(R.id.imageViewLoggo);
        Ion.with(loggo).load("android.resource://com.portlux.portluxpocket/" + R.drawable.portluxloggaspin);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // close this activity
                finish();
            }
        }, Values.SPLASH_TIME_OUT);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
         getMenuInflater().inflate(R.menu.menu_splash_screen, menu);
        return true;
    }


}
