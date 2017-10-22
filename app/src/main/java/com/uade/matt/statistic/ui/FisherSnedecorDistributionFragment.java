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
import com.uade.matt.statistic.models.FisherSnedecorDistributionCalc;
import com.uade.matt.statistic.utils.Helper;

import library.MinMaxFilter;

import static com.uade.matt.statistic.utils.Helper.getParsed;
import static com.uade.matt.statistic.utils.Helper.setEditText;

public class FisherSnedecorDistributionFragment extends DistributionFragment {
  FisherSnedecorDistributionCalc result;
  private EditText etNumeratorDegreesOfFreedom, etDenominatorDegreesOfFreedom, etX, etF, etG, etResult, etMean,
      etStandardDeviation;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    final View rootView = inflater.inflate(R.layout.fisher_snedecor_distribution_view, container, false);

    FloatingActionButton fab = rootView.findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setMessage(R.string.fisher_snedecor_text).setTitle(R.string.help);
        AlertDialog dialog = builder.create();
        dialog.show();
      }
    });

    etNumeratorDegreesOfFreedom = rootView.findViewById(R.id.etNumeratorDegreesOfFreedom);
    etDenominatorDegreesOfFreedom = rootView.findViewById(R.id.etDenominatorDegreesOfFreedom);
    etX = rootView.findViewById(R.id.x);

    etF = rootView.findViewById(R.id.f);
    etG = rootView.findViewById(R.id.g);

    etMean = rootView.findViewById(R.id.etMean);
    etStandardDeviation = rootView.findViewById(R.id.etStandardDeviation);

    etResult = rootView.findViewById(R.id.etResult);

    etNumeratorDegreesOfFreedom.setFilters(new InputFilter[] { new MinMaxFilter(0, 99999999) });
    etDenominatorDegreesOfFreedom.setFilters(new InputFilter[] { new MinMaxFilter(0, 99999999) });
    etX.setFilters(new InputFilter[] { new MinMaxFilter(0, 99999999) });

    etF.setFilters(new InputFilter[] { new MinMaxFilter(0, 1) });
    etG.setFilters(new InputFilter[] { new MinMaxFilter(0, 1) });

    etMean.setFilters(new InputFilter[] { new MinMaxFilter(0, 99999) });
    etStandardDeviation.setFilters(new InputFilter[] { new MinMaxFilter(0.0001f, 999) });

    FloatingActionButton mButton = rootView.findViewById(R.id.button);
    FloatingActionButton mClearButton = rootView.findViewById(R.id.clear);
    mClearButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        etNumeratorDegreesOfFreedom.setText("");
        etDenominatorDegreesOfFreedom.setText("");
        etX.setText("");
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

        result = new FisherSnedecorDistributionCalc()
            .numeratorDegreesOfFreedom((double) getParsed(Helper.NumberType.DOUBLE, etNumeratorDegreesOfFreedom))
            .denominatorDegreesOfFreedom((Double) getParsed(Helper.NumberType.DOUBLE, etDenominatorDegreesOfFreedom))
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

        setEditText(etNumeratorDegreesOfFreedom, result.numeratorDegreesOfFreedom());
        setEditText(etDenominatorDegreesOfFreedom, result.denominatorDegreesOfFreedom());
        setEditText(etX, result.x());
        setEditText(etF, result.f());
        setEditText(etG, result.g());
        setEditText(etMean, result.mean());
        setEditText(etStandardDeviation, result.standardDeviation());
        setEditText(etResult, result.toString());
      }
    });

    return rootView;
  }
}
