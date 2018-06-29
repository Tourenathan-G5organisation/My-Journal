package com.toure.myjournal.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.toure.myjournal.R;

/**
 * Created by Toure Nathan on 6/27/2018.
 */
public class JournalAdapter extends RecyclerView.Adapter<JournalAdapter.ViewHolder> {

    // Item types which will be used to determine which type of view to send to the user
    final int TYPE_JOURNAL_ITEM = 1;
    final int TYPE_JOURNAL_ITEM_WITH_HEADING = 2;

    public JournalAdapter(Context context) {

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
        if (position == 0 || position == 5) {
            return TYPE_JOURNAL_ITEM_WITH_HEADING;
        } else return TYPE_JOURNAL_ITEM;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        // TODO: Change this to the real value
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
