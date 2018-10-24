package app.com.movielist.Activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import app.com.movielist.R;

public class SplashActivity extends AppCompatActivity {

    Runnable splash_runnable;
    Handler splash_handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        splash_handler = new Handler();


//converted lambda method
        splash_runnable = () -> {
            startActivity(new Intent(SplashActivity.this, MovieListActivity.class));
            finish();

        };
        splash_handler.postDelayed(splash_runnable, 3000);

    }
}
