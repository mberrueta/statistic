package com.uade.matt.statistic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

  private final int SPLASH_DISPLAY_LENGTH = 5000;

  @Override
  public void onCreate(Bundle icicle) {
    super.onCreate(icicle);
    setContentView(R.layout.activity_splash);

    new Handler().postDelayed(new Runnable(){
      @Override
      public void run() {
        Intent mainIntent = new Intent(SplashActivity.this,DistributionListActivity.class);
        SplashActivity.this.startActivity(mainIntent);
        SplashActivity.this.finish();
      }
    }, SPLASH_DISPLAY_LENGTH);
  }
}
