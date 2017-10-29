package com.uade.matt.statistic.ui;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ToggleButton;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.uade.matt.statistic.R;
import com.uade.matt.statistic.models.SizeBinomialEstimationCalc;
import com.uade.matt.statistic.utils.Helper;

import library.MinMaxFilter;

import static com.uade.matt.statistic.utils.Helper.getParsed;
import static com.uade.matt.statistic.utils.Helper.setEditText;

public class SizeBinomialEstimationFragment extends DistributionFragment {
  SizeBinomialEstimationCalc result;
  private EditText etP0, etAlpha, etP1, etBeta, etResult;
  private ToggleButton tbType;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    final View rootView = inflater.inflate(R.layout.size_binomial_estimation, container, false);

    // FloatingActionButton fab = rootView.findViewById(R.id.fab);
    // fab.setOnClickListener(new View.OnClickListener() {
    //   @Override
    //   public void onClick(View view) {
    //     AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
    //     builder.setMessage(R.string.variance_inference_text).setTitle(R.string.help);
    //     AlertDialog dialog = builder.create();
    //     dialog.show();
    //   }
    // });


    tbType = rootView.findViewById(R.id.tbType);
    etP0 = rootView.findViewById(R.id.etP0);
    etAlpha = rootView.findViewById(R.id.etAlpha);
    etP1 = rootView.findViewById(R.id.etP1);
    etBeta = rootView.findViewById(R.id.etBeta);
    etResult = rootView.findViewById(R.id.etResult);

    etP0.setFilters(new InputFilter[] { new MinMaxFilter(0, 1) });
    etAlpha.setFilters(new InputFilter[] { new MinMaxFilter(0, 1) });
    etP1.setFilters(new InputFilter[] { new MinMaxFilter(0, 1) });
    etBeta.setFilters(new InputFilter[] { new MinMaxFilter(0, 1) });

    FloatingActionButton mButton = rootView.findViewById(R.id.button);
    FloatingActionButton mClearButton = rootView.findViewById(R.id.clear);
    mClearButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        etP0.setText("");
        etAlpha.setText("");
        etP1.setText("");
        etBeta.setText("");
        etResult.setText("");
        tbType.setChecked(false);
      }
    });

    mButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        calculateInOhterThread();
      }
    });

    return rootView;
  }

  private void calculateInOhterThread() {

    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
     builder.setMessage("Long operation started...").setTitle("Please wait");
    final  AlertDialog dialog = builder.create();
     dialog.show();

    new Thread() {
      @Override
      public void run() {

        try {
          result = new SizeBinomialEstimationCalc()
            .lessThan(tbType.isChecked())
            .p0((Double) getParsed(Helper.NumberType.DOUBLE, etP0))
            .alpha((Double) getParsed(Helper.NumberType.DOUBLE, etAlpha))
            .p1((Double) getParsed(Helper.NumberType.DOUBLE, etP1))
            .beta((Double) getParsed(Helper.NumberType.DOUBLE, etBeta)).calc();
        } catch (Exception e) {
          e.printStackTrace();
        }

        try {

          getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
              setEditText(etP0, result.p0());
              setEditText(etAlpha, result.alpha());
              setEditText(etP1, result.p1());
              setEditText(etBeta, result.beta());
              setEditText(etResult, result.toString());
              dialog.dismiss();
            }
          });
        } catch (final Exception ex) {

        }
      }
    }.start();
  }
}
