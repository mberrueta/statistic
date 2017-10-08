package com.uade.matt.statistic;

import android.app.Activity;
import android.os.Bundle;
import android.os.Looper;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.uade.matt.statistic.dummy.DummyContent;
import com.uade.matt.statistic.models.BinomialDistributionCalc;

import java.util.ArrayList;
import java.util.List;

import library.MinMaxFilter;

import static com.uade.matt.statistic.R.id.f;
import static com.uade.matt.statistic.R.id.p;

public class DistributionDetailFragment extends Fragment {
    public static final String ARG_ITEM_ID = "item_id";
    BinomialDistributionCalc result;
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
                        Toast.makeText(getActivity(), paramThrowable.getMessage(), Toast.LENGTH_LONG).show();
                        Looper.loop();
                    }
                }.start();
                try {
                    Thread.sleep(4000); // Let the Toast display before app will get shutdown
                } catch (InterruptedException e) {
                }
                System.exit(2);
            }
        });

    }

    public Object getParsed(NumberType numberType, EditText et) {

        if (et.getText().toString().trim().length() == 0)
            return null;

        String s = et.getText().toString().trim();
        switch (numberType) {
            case DOUBLE:
                return Double.parseDouble(et.getText().toString().trim());
            case INTEGER:
                return Integer.parseInt(et.getText().toString().trim());
            default:
                return s;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.distribution_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            ((EditText) rootView.findViewById(R.id.n)).setFilters(new InputFilter[]{new MinMaxFilter(1, 99999999)});
            ((EditText) rootView.findViewById(R.id.r)).setFilters(new InputFilter[]{new MinMaxFilter(0, 99999999)});
            ((EditText) rootView.findViewById(p)).setFilters(new InputFilter[]{new MinMaxFilter(0, 1)});
        }

        chart = rootView.findViewById(R.id.chart);
        etN = rootView.findViewById(R.id.n);
        etR = rootView.findViewById(R.id.r);
        etP = rootView.findViewById(p);
        etF = rootView.findViewById(f);
        etPbin = rootView.findViewById(R.id.pbin);
        etG = rootView.findViewById(R.id.g);
        etResult = rootView.findViewById(R.id.etResult);


        FloatingActionButton mButton = (FloatingActionButton) rootView.findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n = Integer.parseInt(etN.getText().toString());
                int r;

                result = new BinomialDistributionCalc()
                        .p((Double) getParsed(NumberType.DOUBLE, etP))
                        .f((Double) getParsed(NumberType.DOUBLE, etF))
                        .n((Integer) getParsed(NumberType.INTEGER, etN))
                        .r((Integer) getParsed(NumberType.INTEGER, etR))
                        .calculatePx();


//                if (etR.getText().length() > 0 ){
//                    r = Integer.parseInt(etR.getText().toString());
//                    result = new BinomialDistributionCalc().calculatePx();
//                }else{
//                    Double f = Double.parseDouble(etF.getText().toString());
//                    result = new BinomialDistributionCalc(n, p, f).calculatePx();
//                }


                etN.setText(result.n().toString());
                etR.setText(result.r().toString());
                etP.setText(result.p().toString());
                etF.setText(result.f().toString());
                etPbin.setText(result.pbin().toString());
                etG.setText(result.g().toString());
                etResult.setText(result.toString());


                List<BinomialDistributionCalc.Dto> list = result.generateSuccessIndex();
                List<BarEntry> entries = new ArrayList<>();
                for (BinomialDistributionCalc.Dto data : list) {
                    entries.add(new BarEntry((float) data.id, data.value.floatValue()));
                }

                BarDataSet dataSet = new BarDataSet(entries, "p = " + result.p().toString());
                BarData barData = new BarData(dataSet);
                chart.setData(barData);
                chart.invalidate();
                chart.animateX(2500, Easing.EasingOption.EaseInBack);
                chart.fitScreen();
                chart.highlightValue(new Highlight(0, 0, n));
                chart.highlightValue(chart.getHighlighter().getHighlight((float) n, 0));

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

    public enum NumberType {
        DOUBLE, INTEGER
    }
}
