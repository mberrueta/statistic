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
import com.uade.matt.statistic.models.BinomialDistributionCalc;
import com.uade.matt.statistic.utils.Helper;

import java.util.ArrayList;
import java.util.List;

import library.MinMaxFilter;

import static com.uade.matt.statistic.R.id.f;
import static com.uade.matt.statistic.R.id.n;
import static com.uade.matt.statistic.R.id.p;
import static com.uade.matt.statistic.R.id.r;
import static com.uade.matt.statistic.utils.Helper.getParsed;

public class OldDistributionDetailFragment extends Fragment {
    public static final String ARG_ITEM_ID = "item_id";
    BinomialDistributionCalc result;
    private ContentType.Item mItem;
    private EditText etN, etR, etP, etF, etPbin, etG, etResult, etMean, etStandardDeviation;
    private BarChart chart;

    public OldDistributionDetailFragment() {
    }

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.binomial_distribution_view, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            ((EditText) rootView.findViewById(n)).setFilters(new InputFilter[]{new MinMaxFilter(1, 99999999)});
            ((EditText) rootView.findViewById(r)).setFilters(new InputFilter[]{new MinMaxFilter(0, 99999999)});
            ((EditText) rootView.findViewById(p)).setFilters(new InputFilter[]{new MinMaxFilter(0, 1)});
        }

        chart = rootView.findViewById(R.id.chart);
        etN = rootView.findViewById(n);
        etR = rootView.findViewById(r);
        etP = rootView.findViewById(p);
        etF = rootView.findViewById(f);
        etMean = rootView.findViewById(R.id.etMean);
        etStandardDeviation = rootView.findViewById(R.id.etStandardDeviation);
        etPbin = rootView.findViewById(R.id.pbin);
        etG = rootView.findViewById(R.id.g);
        etResult = rootView.findViewById(R.id.etResult);


        FloatingActionButton mButton = rootView.findViewById(R.id.button);
        FloatingActionButton mClearButton = rootView.findViewById(R.id.clear);
        mClearButton.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   etN.setText("");
                   etR.setText("");
                   etP.setText("");
                   etF.setText("");
                   etStandardDeviation.setText("");
                   etMean.setText("");
                   etPbin.setText("");
                   etG.setText("");
                   etResult.setText("");
                   chart.invalidate();
               }
           });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                result = new BinomialDistributionCalc()
                        .p((Double) getParsed(Helper.NumberType.DOUBLE, etP))
                        .f((Double) getParsed(Helper.NumberType.DOUBLE, etF))
                        .g((Double) getParsed(Helper.NumberType.DOUBLE, etG))
                        .mean((Double) getParsed(Helper.NumberType.DOUBLE, etMean))
                        .standardDeviation((Double) getParsed(Helper.NumberType.DOUBLE, etStandardDeviation))
                        .n((Integer) getParsed(Helper.NumberType.INTEGER, etN))
                        .r((Integer) getParsed(Helper.NumberType.INTEGER, etR))
                        .calculatePx();

                if(!Helper.isNullorEmpty(result.resultMessage()))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(rootView.getContext());
                    builder.setMessage(result.resultMessage())
                            .setTitle(R.string.help);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return;
                }

                etN.setText(result.n().toString());
                etR.setText(result.r().toString());
                etP.setText(result.p().toString());
                etF.setText(result.f().toString());
                etStandardDeviation.setText(result.standardDeviation().toString());
                etMean.setText(result.mean().toString());
                etPbin.setText(result.pbin().toString());
                etG.setText(result.g().toString());
                etResult.setText(result.toString());

                List<Helper.Dto> list = result.generateSuccessIndex();
                List<BarEntry> entries = new ArrayList<>();

                for (Helper.Dto data : list) {
                    entries.add( new BarEntry(data.id.floatValue(), data.value.floatValue()));
                }

                BarDataSet dataSet = new BarDataSet(entries, "p = " + result.p().toString());
                BarData barData = new BarData(dataSet);
                chart.setData(barData);
                chart.invalidate();
                chart.animateX(2500, Easing.EasingOption.EaseInBack);
                chart.fitScreen();
                chart.highlightValues(new Highlight[]{
                        new Highlight((float)result.r(), result.pbin().floatValue(), 0)
                });

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
