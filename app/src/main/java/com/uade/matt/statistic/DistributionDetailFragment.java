package com.uade.matt.statistic;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.uade.matt.statistic.dummy.DummyContent;

import library.MinMaxFilter;

/**
 * A fragment representing a single Distribution detail screen.
 * This fragment is either contained in a {@link DistributionListActivity}
 * in two-pane mode (on tablets) or a {@link DistributionDetailActivity}
 * on handsets.
 */
public class DistributionDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private DummyContent.DummyItem mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public DistributionDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.id);
            }
        }
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

        return rootView;
    }
}
