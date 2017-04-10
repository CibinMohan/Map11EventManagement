package com.example.c4cib.map1111.ui;

/**
 * Created by c4cib on 22/11/2016.
 */

        import android.content.Intent;
        import android.os.Handler;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;

        import com.example.c4cib.map1111.R;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_splash);

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreen.this, actLogin.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);


    }


}


