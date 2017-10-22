package com.uade.matt.statistic.ui;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.uade.matt.statistic.R;
import com.uade.matt.statistic.models.TDistributionCalc;
import com.uade.matt.statistic.utils.Helper;

import java.util.ArrayList;
import java.util.List;

import library.MinMaxFilter;

import static com.uade.matt.statistic.utils.Helper.getParsed;

public class TDistributionFragment extends DistributionFragment {
  public static final String ARG_ITEM_ID = "item_id";
  TDistributionCalc result;
  private EditText etX, etDegreesOfFreedom, etF, etG, etResult, etMean, etStandardDeviation;
  private GraphView graph;
  private ContentType.Item mItem;

  public TDistributionFragment() {
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    final View rootView = inflater.inflate(R.layout.t_distribution_view, container, false);

    FloatingActionButton fab = rootView.findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setMessage(R.string.t_text).setTitle(R.string.help);
        AlertDialog dialog = builder.create();
        dialog.show();
      }
    });

    etDegreesOfFreedom = rootView.findViewById(R.id.etDegreesOfFreedom);
    etX = rootView.findViewById(R.id.etX);

    etF = rootView.findViewById(R.id.f);
    etG = rootView.findViewById(R.id.g);
    etMean = rootView.findViewById(R.id.etMean);
    etStandardDeviation = rootView.findViewById(R.id.etStandardDeviation);

    etResult = rootView.findViewById(R.id.etResult);
    graph = rootView.findViewById(R.id.graph);

    etDegreesOfFreedom.setFilters(new InputFilter[] { new MinMaxFilter(0, 99999) });
    etX.setFilters(new InputFilter[] { new MinMaxFilter(-99999, 99999) });
    etF.setFilters(new InputFilter[] { new MinMaxFilter(0, 1) });
    etG.setFilters(new InputFilter[] { new MinMaxFilter(0, 1) });
    etMean.setFilters(new InputFilter[] { new MinMaxFilter(0, 99999) });
    etStandardDeviation.setFilters(new InputFilter[] { new MinMaxFilter(0.0001f, 999) });

    FloatingActionButton mButton = rootView.findViewById(R.id.button);
    FloatingActionButton mClearButton = rootView.findViewById(R.id.clear);
    mClearButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        etX.setText("");
        etDegreesOfFreedom.setText("");
        etF.setText("");
        etG.setText("");
        etMean.setText("");
        etStandardDeviation.setText("");
        etResult.setText("");

      }
    });

    mButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        result = new TDistributionCalc()
            .degreesOfFreedom((Double) getParsed(Helper.NumberType.DOUBLE, etDegreesOfFreedom))
            .x((Double) getParsed(Helper.NumberType.DOUBLE, etX)).f((Double) getParsed(Helper.NumberType.DOUBLE, etF))
            .g((Double) getParsed(Helper.NumberType.DOUBLE, etG))
            .mean((Double) getParsed(Helper.NumberType.DOUBLE, etMean))
            .standardDeviation((Double) getParsed(Helper.NumberType.DOUBLE, etStandardDeviation)).calculatePx();

        if (!Helper.isNullorEmpty(result.resultMessage())) {
          AlertDialog.Builder builder = new AlertDialog.Builder(rootView.getContext());
          builder.setMessage(result.resultMessage()).setTitle(R.string.help);
          AlertDialog dialog = builder.create();
          dialog.show();
          return;
        }

        etDegreesOfFreedom.setText(result.degreesOfFreedom().toString());
        etX.setText(result.x().toString());
        etF.setText(result.f().toString());
        etG.setText(result.g().toString());
        etMean.setText(result.mean().toString());
        etStandardDeviation.setText(result.standardDeviation().toString());
        etResult.setText(result.toString());

        List<Helper.Dto> list = result.generateSuccessIndex();
        List<DataPoint> entries = new ArrayList<>();
        for (Helper.Dto data : list) {
          entries.add(new DataPoint(data.id.floatValue(), Helper.round(data.value).floatValue()));
        }
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(entries.toArray(new DataPoint[0]));
        graph.addSeries(series);
        graph.getViewport().setScalable(true);
        graph.getViewport().setScrollable(true);
        graph.getViewport().setScalableY(true);
        graph.getViewport().setScrollableY(true);

      }
    });

    return rootView;
  }
}
