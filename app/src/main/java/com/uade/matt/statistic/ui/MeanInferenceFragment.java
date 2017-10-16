package com.uade.matt.statistic.ui;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.uade.matt.statistic.R;
import com.uade.matt.statistic.models.MeanInferenceCalc;
import com.uade.matt.statistic.utils.Helper;

import library.MinMaxFilter;

import static com.uade.matt.statistic.utils.Helper.getParsed;
import static com.uade.matt.statistic.utils.Helper.setEditText;

public class MeanInferenceFragment extends DistributionFragment {
    MeanInferenceCalc result;
    private EditText /*etMean,*/ etSize, etStandardDeviation, etSampleMean, etSampleSize,
            etSampleStandardDeviation, etLimitInf, etLimitSup, etSampleError, etAlpha, etResult;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.mean_inference_view, container, false);


        FloatingActionButton fab = rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage(R.string.mean_inference_text)
                        .setTitle(R.string.help);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


//        etMean = rootView.findViewById(etMean);
        etSize = rootView.findViewById(R.id.etSize);
        etStandardDeviation = rootView.findViewById(R.id.etStandardDeviation);
        etSampleMean = rootView.findViewById(R.id.etSampleMean);
        etSampleSize = rootView.findViewById(R.id.etSampleSize);
        etSampleStandardDeviation = rootView.findViewById(R.id.etSampleStandardDeviation);
        etLimitInf = rootView.findViewById(R.id.etLimitInf);
        etLimitSup = rootView.findViewById(R.id.etLimitSup);
        etSampleError = rootView.findViewById(R.id.etSampleError);
        etAlpha = rootView.findViewById(R.id.etAlpha);
        etResult = rootView.findViewById(R.id.etResult);


//        etMean.setFilters(new InputFilter[]{new MinMaxFilter(0, 99999999)});
        etSize.setFilters(new InputFilter[]{new MinMaxFilter(0, 99999999)});
        etStandardDeviation.setFilters(new InputFilter[]{new MinMaxFilter(0, 99999999)});
        etSampleSize.setFilters(new InputFilter[]{new MinMaxFilter(0, 99999999)});
        etSampleStandardDeviation.setFilters(new InputFilter[]{new MinMaxFilter(0, 99999999)});
        etSampleError.setFilters(new InputFilter[]{new MinMaxFilter(0, 99999999)});
        etAlpha.setFilters(new InputFilter[]{new MinMaxFilter(0, 1)});

        FloatingActionButton mButton = rootView.findViewById(R.id.button);
        FloatingActionButton mClearButton = rootView.findViewById(R.id.clear);
        mClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                etMean.setText("");
                etSize.setText("");
                etStandardDeviation.setText("");
                etSampleMean.setText("");
                etSampleSize.setText("");
                etSampleStandardDeviation.setText("");
                etLimitInf.setText("");
                etLimitSup.setText("");
                etSampleError.setText("");
                etAlpha.setText("");
                etResult.setText("");
            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                result = new MeanInferenceCalc()
//                .mean((Double) getParsed(Helper.NumberType.DOUBLE, etMean))
                        .size((Integer) getParsed(Helper.NumberType.INTEGER, etSize))
                        .standardDeviation((Double) getParsed(Helper.NumberType.DOUBLE, etStandardDeviation))
                        .sampleMean((Double) getParsed(Helper.NumberType.DOUBLE, etSampleMean))
                        .sampleSize((Integer) getParsed(Helper.NumberType.INTEGER, etSampleSize))
                        .sampleStandardDeviation((Double) getParsed(Helper.NumberType.DOUBLE, etSampleStandardDeviation))
                        .limitInf((Double) getParsed(Helper.NumberType.DOUBLE, etLimitInf))
                        .limitSup((Double) getParsed(Helper.NumberType.DOUBLE, etLimitSup))
                        .sampleError((Integer) getParsed(Helper.NumberType.INTEGER, etSampleError))
                        .alpha((Double) getParsed(Helper.NumberType.DOUBLE, etAlpha))
                        .calc();

                if (!Helper.isNullorEmpty(result.resultMessage())) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(rootView.getContext());
                    builder.setMessage(result.resultMessage())
                            .setTitle(R.string.help);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return;
                }

//                setEditText(etMean, result.mean());
                setEditText(etSize, result.size());
                setEditText(etStandardDeviation, result.standardDeviation());
                setEditText(etSampleMean, result.sampleMean());
                setEditText(etSampleSize, result.sampleSize());
                setEditText(etSampleStandardDeviation, result.sampleStandardDeviation());
                setEditText(etLimitInf, result.limitInf());
                setEditText(etLimitSup, result.limitSup());
                setEditText(etSampleError, result.sampleError());
                setEditText(etAlpha, result.alpha());
                setEditText(etResult, result.resultMessage());
            }
        });

        return rootView;
    }
}
