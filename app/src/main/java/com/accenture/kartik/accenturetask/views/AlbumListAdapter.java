package com.accenture.kartik.accenturetask.views;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.accenture.kartik.accenturetask.R;
import com.accenture.kartik.accenturetask.domain.Album;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AlbumListAdapter  extends BaseAdapter {

    /**
     * The layout inflater used to inflate the list items.
     */
    private LayoutInflater mLayoutInflater;

    /**
     * The data of the list.
     */
    private List<Album> mData;

    /**
     * Constructor.
     * @param context The context.
     * @param data The data of the list.
     */
    public AlbumListAdapter(Context context, List<Album> data) {

        mLayoutInflater = LayoutInflater.from(context);
        mData = data;
    }

    @Override
    public int getCount() {
        return mData == null ? -1 : mData.size();
    }

    @Override
    public Album getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {

        final AlbumViewHolder holder;

        if (view != null) {
            holder = (AlbumViewHolder) view.getTag();
        } else {
            view = mLayoutInflater.inflate(R.layout.item_album, parent, false);
            holder = new AlbumViewHolder(view);
            view.setTag(holder);
        }

        Album record = getItem(position);

        holder.mTextViewTitle.setText(record.getTitle());

        return view;
    }


    /**
     * The view holder of the list view. It holds the child views so they are not being recreated.
     */
    static class AlbumViewHolder {

        @BindView(R.id.textview_title) TextView mTextViewTitle;

        /**
         * Constructor.
         * @param view The parent view containing the child views we want to hold.
         */
        public AlbumViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
