package com.uade.matt.statistic.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Looper;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.uade.matt.statistic.R;
import com.uade.matt.statistic.models.NormalDistributionCalc;
import com.uade.matt.statistic.utils.Helper;

import java.util.ArrayList;
import java.util.List;

import library.MinMaxFilter;

import static com.uade.matt.statistic.R.id.f;
import static com.uade.matt.statistic.R.id.n;
import static com.uade.matt.statistic.R.id.p;
import static com.uade.matt.statistic.R.id.r;
import static com.uade.matt.statistic.utils.Helper.getParsed;

public class NormalDistributionFragment extends DistributionFragment {
    public static final String ARG_ITEM_ID = "item_id";
    NormalDistributionCalc result;
    private ContentType.Item mItem;
    private EditText etN, etR, etP, etF, etPbin, etG, etResult, etMean, etStandardDeviation;
    private BarChart chart;

    public NormalDistributionFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.normal_distribution_view, container, false);


        return rootView;
    }
}
