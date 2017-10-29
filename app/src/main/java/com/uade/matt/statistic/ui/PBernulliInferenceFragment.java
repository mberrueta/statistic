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
import com.uade.matt.statistic.models.PBernulliInferenceCalc;
import com.uade.matt.statistic.utils.Helper;

import library.MinMaxFilter;

import static com.uade.matt.statistic.utils.Helper.getParsed;
import static com.uade.matt.statistic.utils.Helper.setEditText;

public class PBernulliInferenceFragment extends DistributionFragment {
  PBernulliInferenceCalc result;
  private EditText etSize, etSampleSize, etSampleP, etLimitInf, etLimitSup, etError, etAlpha, etResult;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    final View rootView = inflater.inflate(R.layout.p_bernulli_inference_view, container, false);

    // FloatingActionButton fab = rootView.findViewById(R.id.fab);
    // fab.setOnClickListener(new View.OnClickListener() {
    //   @Override
    //   public void onClick(View view) {
    //     AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
    //     builder.setMessage(R.string.p_bernulli_inference_text).setTitle(R.string.help);
    //     AlertDialog dialog = builder.create();
    //     dialog.show();
    //   }
    // });

    etSize = rootView.findViewById(R.id.etSize);
    etSampleSize = rootView.findViewById(R.id.etSampleSize);
    //        etP = rootView.findViewById(R.id.etP);
    etSampleP = rootView.findViewById(R.id.etSampleP);
    etLimitInf = rootView.findViewById(R.id.etLimitInf);
    etLimitSup = rootView.findViewById(R.id.etLimitSup);
    etError = rootView.findViewById(R.id.etError);
    etAlpha = rootView.findViewById(R.id.etAlpha);
    etResult = rootView.findViewById(R.id.etResult);

    etSize.setFilters(new InputFilter[] { new MinMaxFilter(0, 99999999) });
    etSampleSize.setFilters(new InputFilter[] { new MinMaxFilter(0, 99999999) });
    //        etP.setFilters(new InputFilter[]{new MinMaxFilter(0, 1)});
    etSampleP.setFilters(new InputFilter[] { new MinMaxFilter(0, 1) });
    etLimitInf.setFilters(new InputFilter[] { new MinMaxFilter(0, 99999999) });
    etLimitSup.setFilters(new InputFilter[] { new MinMaxFilter(0, 99999999) });
    etError.setFilters(new InputFilter[] { new MinMaxFilter(0, 1) });
    etAlpha.setFilters(new InputFilter[] { new MinMaxFilter(0, 1) });

    FloatingActionButton mButton = rootView.findViewById(R.id.button);
    FloatingActionButton mClearButton = rootView.findViewById(R.id.clear);
    mClearButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        etSize.setText("");
        etSampleSize.setText("");
        //                etP.setText("");
        etSampleP.setText("");
        etLimitInf.setText("");
        etLimitSup.setText("");
        etError.setText("");
        etAlpha.setText("");
        etResult.setText("");

      }
    });

    mButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        result = new PBernulliInferenceCalc()

            .size((Integer) getParsed(Helper.NumberType.INTEGER, etSize))
            .sampleSize((Integer) getParsed(Helper.NumberType.INTEGER, etSampleSize))
            //                .p((Double) getParsed(Helper.NumberType.DOUBLE, etP))
            .sampleP((Double) getParsed(Helper.NumberType.DOUBLE, etSampleP))
            .limitInf((Double) getParsed(Helper.NumberType.DOUBLE, etLimitInf))
            .limitSup((Double) getParsed(Helper.NumberType.DOUBLE, etLimitSup))
            .error((Double) getParsed(Helper.NumberType.DOUBLE, etError))
            .alpha((Double) getParsed(Helper.NumberType.DOUBLE, etAlpha)).calc();

        if (!Helper.isNullorEmpty(result.resultMessage())) {
          AlertDialog.Builder builder = new AlertDialog.Builder(rootView.getContext());
          builder.setMessage(result.resultMessage()).setTitle(R.string.help);
          AlertDialog dialog = builder.create();
          dialog.show();
          return;
        }

        setEditText(etSize, result.size());
        setEditText(etSampleSize, result.sampleSize());
        //                setEditText(etP, result.p());
        setEditText(etSampleP, result.sampleP());
        setEditText(etLimitInf, result.limitInf());
        setEditText(etLimitSup, result.limitSup());
        setEditText(etError, result.error());
        setEditText(etAlpha, result.alpha());
        setEditText(etResult, result.toString());
      }
    });

    return rootView;
  }
}
