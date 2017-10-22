package com.uade.matt.statistic.ui;

import android.app.Activity;
import android.os.Bundle;
import android.os.Looper;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.uade.matt.statistic.R;

public class DistributionFragment extends Fragment {
  public static final String ARG_ITEM_ID = "item_id";
  private ContentType.Item mItem;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (getArguments().containsKey(ARG_ITEM_ID)) {
      mItem = ContentType.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

      Activity activity = this.getActivity();
      CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
      if (appBarLayout != null) {
        appBarLayout.setTitle(mItem.id);
      }

    }

    Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
      @Override
      public void uncaughtException(Thread paramThread, final Throwable paramThrowable) {

        new Thread() {
          @Override
          public void run() {
            Looper.prepare();
            Toast.makeText(getActivity(), paramThrowable.getMessage(), Toast.LENGTH_LONG).show();
            Looper.loop();
          }
        }.start();
        try {
          Thread.sleep(4000); // Let the Toast display before app will get shutdown
        } catch (InterruptedException ignored) {
        }
        System.exit(2);
      }
    });

  }
}
