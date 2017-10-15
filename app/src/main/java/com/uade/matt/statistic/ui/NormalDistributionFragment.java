package com.uade.matt.statistic.ui;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.github.mikephil.charting.charts.LineChart;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.uade.matt.statistic.R;
import com.uade.matt.statistic.models.NormalDistributionCalc;
import com.uade.matt.statistic.utils.Helper;

import java.util.ArrayList;
import java.util.List;

import static com.uade.matt.statistic.utils.Helper.getParsed;

public class NormalDistributionFragment extends DistributionFragment {
    public static final String ARG_ITEM_ID = "item_id";
    NormalDistributionCalc result;
    private EditText etX, etF, etG, etResult, etMean, etStandardDeviation;
    private LineChart chart;
    private GraphView graph;
    private ContentType.Item mItem;

    public NormalDistributionFragment() {
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.normal_distribution_view, container, false);

        FloatingActionButton fab = rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage(R.string.normal_text)
                        .setTitle(R.string.help);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


        etMean = rootView.findViewById(R.id.etMean);
        etStandardDeviation = rootView.findViewById(R.id.etStandardDeviation);
        etX = rootView.findViewById(R.id.x);

        etF = rootView.findViewById(R.id.f);
        etG = rootView.findViewById(R.id.g);

        etResult = rootView.findViewById(R.id.etResult);
        graph = rootView.findViewById(R.id.graph);

        etMean.setFilters(new InputFilter[]{new MinMaxFilter(0, 99999)});
        etStandardDeviation.setFilters(new InputFilter[]{new MinMaxFilter(0.0001, 999)});
        etX.setFilters(new InputFilter[]{new MinMaxFilter(-99999, 99999)});
        etF.setFilters(new InputFilter[]{new MinMaxFilter(0, 1)});
        etG.setFilters(new InputFilter[]{new MinMaxFilter(0, 1)});
        
        FloatingActionButton mButton = rootView.findViewById(R.id.button);
        FloatingActionButton mClearButton = rootView.findViewById(R.id.clear);
        mClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etMean.setText("0");
                etStandardDeviation.setText("1");
                etX.setText("");
                etF.setText("");
                etG.setText("");
                etResult.setText("");
            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                result = new NormalDistributionCalc()
                    .mean((Double) getParsed(Helper.NumberType.DOUBLE, etMean))
                    .standardDeviation((Double) getParsed(Helper.NumberType.DOUBLE, etStandardDeviation))
                    .x((Double) getParsed(Helper.NumberType.DOUBLE, etX))
                    .f((Double) getParsed(Helper.NumberType.DOUBLE, etF))
                    .g((Double) getParsed(Helper.NumberType.DOUBLE, etG))
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
                etMean.setText(result.mean().toString());
                etStandardDeviation.setText(result.standardDeviation().toString());
                etX.setText(result.x().toString());
                etF.setText(result.f().toString());
                etG.setText(result.g().toString());
                etResult.setText(result.toString());

                List<Helper.Dto> list = result.generateSuccessIndex();
                List<DataPoint> entries = new ArrayList<>();
                for (Helper.Dto data : list) {
                    entries.add( new DataPoint(data.id.floatValue(), Helper.round(data.value).floatValue()));
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
