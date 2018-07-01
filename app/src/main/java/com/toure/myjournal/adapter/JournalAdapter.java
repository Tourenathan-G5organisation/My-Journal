package com.toure.myjournal.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.toure.myjournal.ItemOnclickHandler;
import com.toure.myjournal.R;
import com.toure.myjournal.data.Note;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by Toure Nathan on 6/27/2018.
 */
public class JournalAdapter extends RecyclerView.Adapter<JournalAdapter.ViewHolder> {

    private static final String LOG_TAC = JournalAdapter.class.getSimpleName();

    // Item types which will be used to determine which type of view to send to the user
    final int TYPE_JOURNAL_ITEM = 1;
    final int TYPE_JOURNAL_ITEM_WITH_HEADING = 2;
    /*
     * An on-click handler that we've defined to make it easy for the fragment to interface with
     * our RecyclerView
     */
    final private ItemOnclickHandler mClickHandler;
    List<Note> mNotes;
    Calendar previousItemCalendar;
    Calendar currentItemCalendar;
    int prevPosition;
    int currentPosition;

    public JournalAdapter(Context context, ItemOnclickHandler clickHandler) {

        mClickHandler = clickHandler;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem;
        if (viewType == TYPE_JOURNAL_ITEM_WITH_HEADING) {
            layoutIdForListItem = R.layout.journal_layout_item_with_heading;
        } else {
            layoutIdForListItem = R.layout.journal_layout_item;
        }
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new ViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        super.getItemViewType(position);
        Log.d(LOG_TAC, "position: " + position);
        if (position == 0) {
            previousItemCalendar = mNotes.get(position).getCalendar();
            prevPosition = 0;
            return TYPE_JOURNAL_ITEM_WITH_HEADING;
        } else {
            int type = TYPE_JOURNAL_ITEM;
            currentPosition = position;
            /*if (prevPosition < currentPosition) {
                currentItemCalendar = mNotes.get(position).getCalendar();
                if (previousItemCalendar.get(Calendar.YEAR) > currentItemCalendar.get(Calendar.YEAR) ||
                        previousItemCalendar.get(Calendar.MONTH) > currentItemCalendar.get(Calendar.MONTH)) {
                    type = TYPE_JOURNAL_ITEM_WITH_HEADING;
                }
            }else if (prevPosition > currentPosition){
                currentItemCalendar = mNotes.get(position).getCalendar();
                previousItemCalendar = mNotes.get(position - 1).getCalendar();
                if ((previousItemCalendar.get(Calendar.YEAR) > currentItemCalendar.get(Calendar.YEAR)) ||
                        (previousItemCalendar.get(Calendar.MONTH) > currentItemCalendar.get(Calendar.MONTH))) {
                    type = TYPE_JOURNAL_ITEM_WITH_HEADING;
                }
            }*/

            currentItemCalendar = mNotes.get(position).getCalendar();
            previousItemCalendar = mNotes.get(position - 1).getCalendar();
            if ((previousItemCalendar.get(Calendar.YEAR) != currentItemCalendar.get(Calendar.YEAR)) ||
                    (previousItemCalendar.get(Calendar.MONTH) != currentItemCalendar.get(Calendar.MONTH))) {
                type = TYPE_JOURNAL_ITEM_WITH_HEADING;
            }

            prevPosition = currentPosition;
            //previousItemCalendar = currentItemCalendar;
            return type;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Note note = mNotes.get(position);
        holder.noteTitle.setText(note.getNoteTitle());
        holder.noteContent.setText(note.getNoteContent());
        holder.noteWeekDay.setText(note.getCalendar().getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault()));
        holder.noteMonthDay.setText(String.format(Locale.getDefault(), "%02d", note.getCalendar().get(Calendar.DAY_OF_MONTH)));
        holder.noteTime.setText(String.format(Locale.getDefault(), "%02d:%02d", note.getCalendar().get(Calendar.HOUR_OF_DAY), note.getCalendar().get(Calendar.MINUTE)));
        if (holder.getItemViewType() == TYPE_JOURNAL_ITEM_WITH_HEADING) {
            holder.noteMonthYear.setText(String.format(Locale.getDefault(), "%s %d",
                    note.getCalendar().getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()),
                    note.getCalendar().get(Calendar.YEAR)));
        }
    }

    @Override
    public int getItemCount() {
        return mNotes == null ? 0 : mNotes.size();
    }

    /**
     * Pass new set of data to the JournalAdapter
     *
     * @param notes
     */
    public void setNotes(List<Note> notes) {
        mNotes = notes;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        LinearLayout journalItem;
        TextView noteTitle;
        TextView noteContent;
        TextView noteMonthDay;
        TextView noteWeekDay;
        TextView noteTime;
        TextView noteMonthYear;
        TextView noteColor;

        public ViewHolder(View itemView) {
            super(itemView);
            journalItem = itemView.findViewById(R.id.journal_item_layout);
            noteTitle = itemView.findViewById(R.id.note_title_textview);
            noteContent = itemView.findViewById(R.id.note_content_textview);
            noteMonthDay = itemView.findViewById(R.id.calendar_month_day);
            noteWeekDay = itemView.findViewById(R.id.calendar_week_day);
            noteTime = itemView.findViewById(R.id.time_textview);
            noteMonthYear = itemView.findViewById(R.id.journal_item_month_year);
            noteColor = itemView.findViewById(R.id.color_textview);
            journalItem.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mClickHandler.onClick();
        }
    }
}
