package com.toure.myjournal;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.toure.myjournal.data.AppDatabase;
import com.toure.myjournal.data.AppExecutors;
import com.toure.myjournal.data.Note;

import java.util.Calendar;
import java.util.Locale;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {

    public static final String ITEM_ID_KEY = "item_id_key";
    private static final String LOG_TAC = DetailActivityFragment.class.getSimpleName();
    final int DEFAULT_ITEM_ID = -1;
    int mItemId;
    TextView timeTextView;
    TextView dayOfWeekTextview;
    TextView dayOfMonthTextview;
    TextView monthYearTextview;
    Calendar mNoteDate;
    TextView noteTileTextviTextView;
    TextView noteContentTextView;

    // App Database reference
    AppDatabase mDb;
    LiveData<Note> mNote;

    public DetailActivityFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        timeTextView = view.findViewById(R.id.time_textview);
        dayOfWeekTextview = view.findViewById(R.id.calendar_week_day);
        dayOfMonthTextview = view.findViewById(R.id.calendar_month_day);
        monthYearTextview = view.findViewById(R.id.calendar_month_and_year);
        noteTileTextviTextView = view.findViewById(R.id.journal_item_title_textview);
        noteContentTextView = view.findViewById(R.id.journal_item_content_textview);
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ITEM_ID_KEY, mItemId);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mDb = AppDatabase.getsInstance(getActivity().getApplicationContext());

        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra(ITEM_ID_KEY)) {
            mItemId = getActivity().getIntent().getIntExtra(ITEM_ID_KEY, DEFAULT_ITEM_ID);
            mNote = mDb.noteDao().getNoteWithId(mItemId);
            mNote.observe(this, new Observer<Note>() {
                @Override
                public void onChanged(@Nullable Note note) {
                    try {
                        mNoteDate = note.getCalendar();
                        initialiseView(note);
                    } catch (NullPointerException e) {
                        Log.d(LOG_TAC, e.getMessage());
                    }

                }
            });
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_edit) {
            Intent intent = new Intent(getActivity(), EditActivity.class);
            intent.putExtra(EditActivityFragment.ITEM_ID_KEY, mNote.getValue().getId());
            startActivity(intent);
            return true;
        } else if (id == R.id.action_delete) {
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    mDb.noteDao().delete(mNote.getValue());
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getActivity().finish();
                        }
                    });
                }
            });
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Populate the various views with data
     */
    void initialiseView(Note note) {
        timeTextView.setText(String.format(Locale.getDefault(), "%02d:%02d", mNoteDate.get(Calendar.HOUR_OF_DAY), mNoteDate.get(Calendar.MINUTE)));
        dayOfWeekTextview.setText(mNoteDate.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault()));
        dayOfMonthTextview.setText(String.valueOf(mNoteDate.get(Calendar.DAY_OF_MONTH)));
        monthYearTextview.setText(String.format(Locale.getDefault(), "%s %d",
                mNoteDate.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()),
                mNoteDate.get(Calendar.YEAR)));
        noteTileTextviTextView.setText(note.getNoteTitle());
        noteContentTextView.setText(note.getNoteContent());
    }
}
