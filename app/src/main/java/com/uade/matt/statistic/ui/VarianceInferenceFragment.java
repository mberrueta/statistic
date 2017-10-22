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
import com.uade.matt.statistic.models.VarianceInferenceCalc;
import com.uade.matt.statistic.utils.Helper;

import library.MinMaxFilter;

import static com.uade.matt.statistic.utils.Helper.getParsed;
import static com.uade.matt.statistic.utils.Helper.setEditText;

public class VarianceInferenceFragment extends DistributionFragment {
  VarianceInferenceCalc result;
  private EditText etSampleSize, etDegreesOfFreedom, etSampleStandardDeviation, etLimitInfVariance, etLimitSupVariance,
      etLimitRelationshipVariance, etLimitInfStandardDeviation, etLimitSupStandardDeviation,
      etLimitRelationshipStandardDeviation, etAlpha, etResult;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    final View rootView = inflater.inflate(R.layout.variance_inference_view, container, false);

    FloatingActionButton fab = rootView.findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setMessage(R.string.variance_inference_text).setTitle(R.string.help);
        AlertDialog dialog = builder.create();
        dialog.show();
      }
    });

    etSampleSize = rootView.findViewById(R.id.etSampleSize);
    etDegreesOfFreedom = rootView.findViewById(R.id.etDegreesOfFreedom);
    etSampleStandardDeviation = rootView.findViewById(R.id.etSampleStandardDeviation);
    etLimitInfVariance = rootView.findViewById(R.id.etLimitInfVariance);
    etLimitSupVariance = rootView.findViewById(R.id.etLimitSupVariance);
    etLimitRelationshipVariance = rootView.findViewById(R.id.etLimitRelationshipVariance);
    etLimitInfStandardDeviation = rootView.findViewById(R.id.etLimitInfStandardDeviation);
    etLimitSupStandardDeviation = rootView.findViewById(R.id.etLimitSupStandardDeviation);
    etLimitRelationshipStandardDeviation = rootView.findViewById(R.id.etLimitRelationshipStandardDeviation);
    etAlpha = rootView.findViewById(R.id.etAlpha);
    etResult = rootView.findViewById(R.id.etResult);

    etSampleSize.setFilters(new InputFilter[] { new MinMaxFilter(0, 99999999) });
    etDegreesOfFreedom.setFilters(new InputFilter[] { new MinMaxFilter(0, 99999999) });
    etSampleStandardDeviation.setFilters(new InputFilter[] { new MinMaxFilter(0, 99999999) });
    etLimitInfVariance.setFilters(new InputFilter[] { new MinMaxFilter(0, 99999999) });
    etLimitSupVariance.setFilters(new InputFilter[] { new MinMaxFilter(0, 99999999) });
    etLimitRelationshipVariance.setFilters(new InputFilter[] { new MinMaxFilter(0, 99999999) });
    etLimitInfStandardDeviation.setFilters(new InputFilter[] { new MinMaxFilter(0, 99999999) });
    etLimitSupStandardDeviation.setFilters(new InputFilter[] { new MinMaxFilter(0, 99999999) });
    etLimitRelationshipStandardDeviation.setFilters(new InputFilter[] { new MinMaxFilter(0, 99999999) });
    etAlpha.setFilters(new InputFilter[] { new MinMaxFilter(0, 1) });

    FloatingActionButton mButton = rootView.findViewById(R.id.button);
    FloatingActionButton mClearButton = rootView.findViewById(R.id.clear);
    mClearButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        etSampleSize.setText("");
        etDegreesOfFreedom.setText("");
        etSampleStandardDeviation.setText("");
        etLimitInfVariance.setText("");
        etLimitSupVariance.setText("");
        etLimitRelationshipVariance.setText("");
        etLimitInfStandardDeviation.setText("");
        etLimitSupStandardDeviation.setText("");
        etLimitRelationshipStandardDeviation.setText("");
        etAlpha.setText("");
        etResult.setText("");

      }
    });

    mButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        result = new VarianceInferenceCalc().sampleSize((Integer) getParsed(Helper.NumberType.INTEGER, etSampleSize))
            .degreesOfFreedom((Integer) getParsed(Helper.NumberType.INTEGER, etDegreesOfFreedom))
            .sampleStandardDeviation((Double) getParsed(Helper.NumberType.DOUBLE, etSampleStandardDeviation))
            .limitInfVariance((Double) getParsed(Helper.NumberType.DOUBLE, etLimitInfVariance))
            .limitSupVariance((Double) getParsed(Helper.NumberType.DOUBLE, etLimitSupVariance))
            .limitRelationshipVariance((Double) getParsed(Helper.NumberType.DOUBLE, etLimitRelationshipVariance))
            .limitInfStandardDeviation((Double) getParsed(Helper.NumberType.DOUBLE, etLimitInfStandardDeviation))
            .limitSupStandardDeviation((Double) getParsed(Helper.NumberType.DOUBLE, etLimitSupStandardDeviation))
            .limitRelationshipStandardDeviation(
                (Double) getParsed(Helper.NumberType.DOUBLE, etLimitRelationshipStandardDeviation))
            .alpha((Double) getParsed(Helper.NumberType.DOUBLE, etAlpha)).calc();

        if (!Helper.isNullorEmpty(result.resultMessage())) {
          AlertDialog.Builder builder = new AlertDialog.Builder(rootView.getContext());
          builder.setMessage(result.resultMessage()).setTitle(R.string.help);
          AlertDialog dialog = builder.create();
          dialog.show();
          return;
        }

        setEditText(etSampleSize, result.sampleSize());
        setEditText(etDegreesOfFreedom, result.degreesOfFreedom());
        setEditText(etSampleStandardDeviation, result.sampleStandardDeviation());
        setEditText(etLimitInfVariance, result.limitInfVariance());
        setEditText(etLimitSupVariance, result.limitSupVariance());
        setEditText(etLimitRelationshipVariance, result.limitRelationshipVariance());
        setEditText(etLimitInfStandardDeviation, result.limitInfStandardDeviation());
        setEditText(etLimitSupStandardDeviation, result.limitSupStandardDeviation());
        setEditText(etLimitRelationshipStandardDeviation, result.limitRelationshipStandardDeviation());
        setEditText(etAlpha, result.alpha());
        setEditText(etResult, result.toString());
      }
    });

    return rootView;
  }
}
