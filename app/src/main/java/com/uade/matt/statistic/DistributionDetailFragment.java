package com.uade.matt.statistic;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.RectF;
import android.os.Looper;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.MPPointF;
import com.uade.matt.statistic.dummy.DummyContent;
import com.uade.matt.statistic.models.BinomialDistributionCalc;

import java.util.ArrayList;
import java.util.List;

import library.MinMaxFilter;

import static android.R.attr.entries;

public class DistributionDetailFragment extends Fragment {
    public static final String ARG_ITEM_ID = "item_id";
    private DummyContent.DummyItem mItem;
    private EditText etN, etR, etP, etF, etPbin, etG, etResult;
    private BarChart chart;

    public DistributionDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

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
                        Toast.makeText(getActivity(),paramThrowable.getMessage(), Toast.LENGTH_LONG).show();
                        Looper.loop();
                    }
                }.start();
                try
                {
                    Thread.sleep(4000); // Let the Toast display before app will get shutdown
                }
                catch (InterruptedException e) {    }
                System.exit(2);
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.distribution_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            ((EditText)rootView.findViewById(R.id.n)).setFilters(new InputFilter[]{ new MinMaxFilter(1, 99999999)});
            ((EditText)rootView.findViewById(R.id.r)).setFilters(new InputFilter[]{ new MinMaxFilter(0, 99999999)});
            ((EditText)rootView.findViewById(R.id.p)).setFilters(new InputFilter[]{ new MinMaxFilter(0, 1)});

        }

        chart = rootView.findViewById(R.id.chart);
        etN = rootView.findViewById(R.id.n);
        etR = rootView.findViewById(R.id.r);
        etP = rootView.findViewById(R.id.p);
        etF = rootView.findViewById(R.id.f);
        etPbin = rootView.findViewById(R.id.pbin);
        etG = rootView.findViewById(R.id.g);
        etResult = rootView.findViewById(R.id.etResult);


        FloatingActionButton mButton = (FloatingActionButton) rootView.findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n = Integer.parseInt(etN.getText().toString());
                int r = Integer.parseInt(etR.getText().toString());
                double p = Double.parseDouble(etP.getText().toString());
                BinomialDistributionCalc result = new BinomialDistributionCalc(n, r, p).calculatePx();

                etN.setText(result.getN().toString());
                etR.setText(result.getR().toString());
                etP.setText(result.getP().toString());
                etF.setText(result.getF().toString());
                etPbin.setText(result.getPbin().toString());
                etG.setText(result.getG().toString());
                etResult.setText(result.toString());


                List<BinomialDistributionCalc.Dto> list = result.generateSuccessIndex();
                List<BarEntry> entries = new ArrayList<>();
                for (BinomialDistributionCalc.Dto data : list) {
                    entries.add(new BarEntry((float)data.id, data.value.floatValue()));
                }

                BarDataSet dataSet = new BarDataSet(entries, "p = " + result.getP().toString());
                BarData barData = new BarData(dataSet);
                chart.setData(barData);
                chart.invalidate();
                chart.highlightValue((float)n, n);
                chart.animateX(2500, Easing.EasingOption.EaseInBack);
                chart.fitScreen();
                chart.highlightValue(new Highlight((float)n, 0, n));
                chart.highlightValue(chart.getHighlighter().getHighlight((float)n, 0));

                chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                    Toast mCurrentToast;

                    @Override
                    public void onValueSelected(Entry e, Highlight h) {

                        if (mCurrentToast != null) {
                            mCurrentToast.cancel();
                        }

                        mCurrentToast = Toast.makeText(rootView.getContext(),
                                e.getX() + " -> " + e.getY(),
                                Toast.LENGTH_LONG);
                        mCurrentToast.show();
                    }

                    @Override
                    public void onNothingSelected() {

                    }
                });

            }
        });

        return rootView;
    }
}
