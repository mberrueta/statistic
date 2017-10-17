package com.uade.matt.statistic.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.uade.matt.statistic.R;

public class ChiSquaredDistributionActivity extends DistributionActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_distribution_detail);
    Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
    setSupportActionBar(toolbar);

    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
    }

    if (savedInstanceState == null) {
      Bundle arguments = new Bundle();
      arguments.putString(ChiSquaredDistributionFragment.ARG_ITEM_ID,
        getIntent().getStringExtra(ChiSquaredDistributionFragment.ARG_ITEM_ID));
      ChiSquaredDistributionFragment fragment = new ChiSquaredDistributionFragment();
      fragment.setArguments(arguments);
      getSupportFragmentManager().beginTransaction()
        .add(R.id.distribution_detail_container, fragment)
        .commit();
    }
  }
}
