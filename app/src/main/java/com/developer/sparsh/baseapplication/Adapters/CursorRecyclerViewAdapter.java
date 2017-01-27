package com.developer.sparsh.baseapplication.Adapters;

import android.content.Context;
import android.content.IntentFilter;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.developer.sparsh.baseapplication.R;

/**
 * Created by SPARSH on 1/27/2017.
 */

public abstract class CursorRecyclerViewAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    private final Context context;
    private Cursor mCursor;


    public CursorRecyclerViewAdapter(Context context, Cursor mCursor) {
        this.context = context;
        this.mCursor = mCursor;

    }

    public abstract void onBindViewHolder(VH viewHolder, Cursor cursor);

    @Override
    public void onBindViewHolder(VH holder, int position) {
        if (mCursor != null) {
            if (mCursor.moveToPosition(position))
                onBindViewHolder(holder, mCursor);
            else
                throw new IllegalStateException("couldn't move the cursor to postion " + position);
        }
    }

    @Override
    public int getItemCount() {
        if (mCursor != null)
            return mCursor.getCount();
        else
            return 0;
    }

    @Override
    public void setHasStableIds(boolean hasStableIds) {
        super.setHasStableIds(true);
    }

    public Cursor swapCursor(Cursor newCursor) {
        final Cursor oldCursor = newCursor;

        if (newCursor == mCursor) {
            return null;
        }

        mCursor = newCursor;
        notifyDataSetChanged();

        return oldCursor;
    }

}
