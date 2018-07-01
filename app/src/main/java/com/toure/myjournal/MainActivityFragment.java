package com.toure.myjournal;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.toure.myjournal.adapter.JournalAdapter;
import com.toure.myjournal.data.AppDatabase;


/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class MainActivityFragment extends Fragment implements ItemOnclickHandler {

    private static final String LOG_TAC = MainActivityFragment.class.getSimpleName();

    RecyclerView mRecyclerView;
    JournalAdapter mJournalAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    // App Database reference
    AppDatabase mDb;

    public MainActivityFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDb = AppDatabase.getsInstance(getActivity().getApplicationContext());
        mJournalAdapter = new JournalAdapter(getContext(), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.journal_recyclerview);
        int recyclerViewOrientation = LinearLayoutManager.VERTICAL;

        /*
         *  This value should be true if you want to reverse your layout. Generally, this is only
         *  true with horizontal lists that need to support a right-to-left layout.
         */
        boolean shouldReverseLayout = false;
        mLayoutManager = new LinearLayoutManager(getContext(), recyclerViewOrientation, shouldReverseLayout);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecyclerView.setLayoutManager(mLayoutManager);
        DividerItemDecoration divider = new DividerItemDecoration(mRecyclerView.getContext(), DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(divider);
          /*
         * Use this setting to improve performance if you know that changes in content do not
         * change the child layout size in the RecyclerView
         */
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setAdapter(mJournalAdapter);
    }

    @Override
    public void onClick() {
        Context context = getContext();
        Intent intentToStartDetailActivity = new Intent(context, DetailActivity.class);
        startActivity(intentToStartDetailActivity);
    }
}
