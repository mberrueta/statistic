package com.uade.matt.statistic;

import android.app.Activity;
import android.os.Looper;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.uade.matt.statistic.dummy.DummyContent;
import com.uade.matt.statistic.models.BinomialDistributionCalc;

import library.MinMaxFilter;

public class DistributionDetailFragment extends Fragment {
    public static final String ARG_ITEM_ID = "item_id";
    private DummyContent.DummyItem mItem;
    private EditText etN;
    private EditText etR;
    private EditText etP;
    private EditText etF;
    private EditText etPbin;
    private EditText etG;
    private EditText etResult;

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
                        Toast.makeText(getActivity(),paramThrowable.getMessage(), Toast.LENGTH_LONG).show();
                        Looper.loop();
                    }
                }.start();
                try
                {
                    Thread.sleep(4000); // Let the Toast display before app will get shutdown
                }
                catch (InterruptedException e) {    }
                System.exit(2);
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.distribution_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.distribution_detail)).setText(mItem.details);
            ((EditText)rootView.findViewById(R.id.n)).setFilters(new InputFilter[]{ new MinMaxFilter(1, 99999999)});
            ((EditText)rootView.findViewById(R.id.r)).setFilters(new InputFilter[]{ new MinMaxFilter(0, 99999999)});
            ((EditText)rootView.findViewById(R.id.p)).setFilters(new InputFilter[]{ new MinMaxFilter(0, 1)});
        }


        etN = rootView.findViewById(R.id.n);
        etR = rootView.findViewById(R.id.r);
        etP = rootView.findViewById(R.id.p);
        etF = rootView.findViewById(R.id.f);
        etPbin = rootView.findViewById(R.id.pbin);
        etG = rootView.findViewById(R.id.g);
        etResult = rootView.findViewById(R.id.etResult);


        Button mButton = (Button) rootView.findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n = Integer.parseInt(etN.getText().toString());
                int r = Integer.parseInt(etR.getText().toString());
                double p = Double.parseDouble(etP.getText().toString());
                BinomialDistributionCalc result = new BinomialDistributionCalc(n, r, p).calculatePx();

                etN.setText(result.getN().toString());
                etR.setText(result.getR().toString());
                etP.setText(result.getP().toString());
                etF.setText(result.getF().toString());
                etPbin.setText(result.getPbin().toString());
                etG.setText(result.getG().toString());
                etResult.setText(result.toString());
            }
        });

        return rootView;
    }
}
