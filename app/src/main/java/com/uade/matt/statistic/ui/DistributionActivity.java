package com.uade.matt.statistic.ui;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.uade.matt.statistic.DistributionListActivity;

abstract class DistributionActivity extends AppCompatActivity {

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpTo(this, new Intent(this, DistributionListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
