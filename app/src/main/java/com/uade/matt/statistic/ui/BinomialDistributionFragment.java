package com.uade.matt.statistic.ui;

import android.app.AlertDialog;
import android.os.Bundle;
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

import static com.uade.matt.statistic.utils.Helper.getParsed;

public class BinomialDistributionFragment extends DistributionFragment {
  BinomialDistributionCalc result;
  private EditText etN, etR, etP, etF, etPbin, etG, etResult, etMean, etStandardDeviation;
  private BarChart chart;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    final View rootView = inflater.inflate(R.layout.binomial_distribution_view, container, false);


    FloatingActionButton fab = rootView.findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
//                Resources res = view.getResources();
//                builder.setMessage(res.getString(res.getIdentifier("binomial_text", null, null)))

        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setMessage(R.string.binomial_text)
          .setTitle(R.string.help);
        AlertDialog dialog = builder.create();
        dialog.show();
      }
    });

    etN = rootView.findViewById(R.id.n);
    etP = rootView.findViewById(R.id.p);
    etR = rootView.findViewById(R.id.r);

    etF = rootView.findViewById(R.id.f);
    etPbin = rootView.findViewById(R.id.pbin);
    etG = rootView.findViewById(R.id.g);

    etMean = rootView.findViewById(R.id.etMean);
    etStandardDeviation = rootView.findViewById(R.id.etStandardDeviation);

    etResult = rootView.findViewById(R.id.etResult);
    chart = rootView.findViewById(R.id.chart);

    etN.setFilters(new InputFilter[]{new MinMaxFilter(1, 99999999)});
    etR.setFilters(new InputFilter[]{new MinMaxFilter(0, 99999999)});
    etP.setFilters(new InputFilter[]{new MinMaxFilter(0, 1)});

    etF.setFilters(new InputFilter[]{new MinMaxFilter(0, 1)});
    etP.setFilters(new InputFilter[]{new MinMaxFilter(0, 1)});
    etG.setFilters(new InputFilter[]{new MinMaxFilter(0, 1)});

    etMean.setFilters(new InputFilter[]{new MinMaxFilter(0, 99999)});
    etStandardDeviation.setFilters(new InputFilter[]{new MinMaxFilter(0.0001f, 999)});

    FloatingActionButton mButton = rootView.findViewById(R.id.button);
    FloatingActionButton mClearButton = rootView.findViewById(R.id.clear);
    mClearButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        etN.setText("");
        etP.setText("");
        etR.setText("");
        etF.setText("");
        etPbin.setText("");
        etG.setText("");
        etMean.setText("");
        etStandardDeviation.setText("");
        etResult.setText("");
        chart.invalidate();
      }
    });

    mButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        result = new BinomialDistributionCalc()
          .n((Integer) getParsed(Helper.NumberType.INTEGER, etN))
          .p((Double) getParsed(Helper.NumberType.DOUBLE, etP))
          .r((Integer) getParsed(Helper.NumberType.INTEGER, etR))
          .f((Double) getParsed(Helper.NumberType.DOUBLE, etF))
          .g((Double) getParsed(Helper.NumberType.DOUBLE, etG))
          .mean((Double) getParsed(Helper.NumberType.DOUBLE, etMean))
          .standardDeviation((Double) getParsed(Helper.NumberType.DOUBLE, etStandardDeviation))
          .calculatePx();

        if (!Helper.isNullorEmpty(result.resultMessage())) {
          AlertDialog.Builder builder = new AlertDialog.Builder(rootView.getContext());
          builder.setMessage(result.resultMessage())
            .setTitle(R.string.help);
          AlertDialog dialog = builder.create();
          dialog.show();
          return;
        }

        etN.setText(result.n().toString());
        etP.setText(result.p().toString());
        etR.setText(result.r().toString());
        etF.setText(result.f().toString());
        etPbin.setText(result.pbin().toString());
        etG.setText(result.g().toString());
        etMean.setText(result.mean().toString());
        etStandardDeviation.setText(result.standardDeviation().toString());
        etResult.setText(result.toString());

        List<Helper.Dto> list = result.generateSuccessIndex();
        List<BarEntry> entries = new ArrayList<>();

        for (Helper.Dto data : list) {
          entries.add(new BarEntry(data.id.floatValue(), data.value.floatValue()));
        }

        BarDataSet dataSet = new BarDataSet(entries, "p = " + result.p().toString());
        BarData barData = new BarData(dataSet);
        chart.setData(barData);
        chart.invalidate();
        chart.animateX(2500, Easing.EasingOption.EaseInBack);
        chart.fitScreen();
        chart.highlightValues(new Highlight[]{
          new Highlight((float) result.r(), result.pbin().floatValue(), 0)
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
