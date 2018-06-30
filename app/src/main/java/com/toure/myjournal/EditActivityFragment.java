package com.toure.myjournal;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * A placeholder fragment containing a simple view.
 */
public class EditActivityFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    TextView timeTextView;
    TextView dayOfWeekTextview;
    TextView dayOfMonthTextview;
    TextView monthYearTextview;
    Calendar mNoteDate;

    public EditActivityFragment() {
        mNoteDate = Calendar.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit, container, false);
        timeTextView = view.findViewById(R.id.time_textview);
        dayOfWeekTextview = view.findViewById(R.id.calendar_week_day);
        dayOfMonthTextview = view.findViewById(R.id.calendar_month_day);
        monthYearTextview = view.findViewById(R.id.calendar_month_and_year);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        dayOfWeekTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDatePickerDialog();
            }
        });
        dayOfMonthTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDatePickerDialog();
            }
        });
        monthYearTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDatePickerDialog();
            }
        });
        timeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTimePickerDialog();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
        mNoteDate.set(year, monthOfYear, dayOfMonth);
        setSelectedDate();
    }

    /**
     * Build and display a date picker dialog
     */
    void getDatePickerDialog() {
        Calendar currentDate = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, currentDate.get(Calendar.YEAR),
                currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();

    }

    /**
     * Get the time picker dialog
     */
    void getTimePickerDialog() {
        Date d = new Date();
        Calendar currentTime = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                mNoteDate.set(Calendar.HOUR_OF_DAY, selectedHour);
                mNoteDate.set(Calendar.MINUTE, selectedHour);
                mNoteDate.set(Calendar.SECOND, 0);
                timeTextView.setText(String.format("%02d:%02d", selectedHour, selectedMinute));

            }
        }, currentTime.get(Calendar.HOUR_OF_DAY), currentTime.get(Calendar.MINUTE), true);
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();

    }

    void setSelectedDate() {
        dayOfWeekTextview.setText(mNoteDate.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault()));
        dayOfMonthTextview.setText(String.valueOf(mNoteDate.get(Calendar.DAY_OF_MONTH)));
        monthYearTextview.setText(String.format("%s %d",
                mNoteDate.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()),
                mNoteDate.get(Calendar.YEAR)));

    }

    void setSelectedTime() {

    }
}
