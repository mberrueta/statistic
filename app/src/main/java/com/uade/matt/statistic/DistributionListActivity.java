package com.uade.matt.statistic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uade.matt.statistic.ui.ContentType;
import com.uade.matt.statistic.ui.DistributionFragment;

import java.util.List;

public class DistributionListActivity extends AppCompatActivity {

  private boolean mTwoPane;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_distribution_list);

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    toolbar.setTitle(getTitle());

    View recyclerView = findViewById(R.id.distribution_list);
    assert recyclerView != null;
    setupRecyclerView((RecyclerView) recyclerView);

    // Tablet mode
    if (findViewById(R.id.distribution_detail_container) != null) {
      mTwoPane = true;
    }
  }

  private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
    recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(ContentType.ITEMS));
  }

  public class SimpleItemRecyclerViewAdapter extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

    private final List<ContentType.Item> mValues;

    public SimpleItemRecyclerViewAdapter(List<ContentType.Item> items) {
      mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.distribution_list_content, parent, false);
      return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
      holder.mItem = mValues.get(position);
      holder.mIdView.setText(mValues.get(position).id);

      holder.mView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

          // Tablet mode
          if (mTwoPane) {
            try {
              Class<?> cls = Class.forName(holder.mItem.activityClassName.replace("Activity", "Fragment"));
              Fragment fragment = (Fragment) cls.newInstance();
              Bundle arguments = new Bundle();
              fragment.setArguments(arguments);
              getSupportFragmentManager().beginTransaction().replace(R.id.distribution_detail_container, fragment)
                  .commit();

            } catch (Exception e) {
              e.printStackTrace();
            }

          } else {

            //Phone mode
            Context context = v.getContext();

            try {
              Class<?> cls = Class.forName(holder.mItem.activityClassName);
              Intent intent = new Intent(context, cls);
              intent.putExtra(DistributionFragment.ARG_ITEM_ID, holder.mItem.id);
              context.startActivity(intent);

            } catch (ClassNotFoundException e) {
              e.printStackTrace();
            }
          }
        }
      });
    }

    @Override
    public int getItemCount() {
      return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
      public final View mView;
      public final TextView mIdView;
      public ContentType.Item mItem;

      public ViewHolder(View view) {
        super(view);
        mView = view;
        mIdView = view.findViewById(R.id.id);
      }
    }
  }
}
