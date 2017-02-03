package com.developer.sparsh.baseapplication.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


import com.developer.sparsh.baseapplication.Helpers.DatabaseHelper;
import com.developer.sparsh.baseapplication.R;
import com.nostra13.universalimageloader.core.ImageLoader;


/**
 * Created by SPARSH on 1/27/2017.
 */

public class FeedCursorAdapter extends CursorRecyclerViewAdapter<FeedCursorAdapter.VH> {

    Context context;
    DatabaseHelper helper;
    public FeedCursorAdapter(Context context, Cursor mCursor) {
        super(context, mCursor);
        this.context = context;
        helper = new DatabaseHelper(context);
    }

    @Override
    public void onBindViewHolder(VH viewHolder, Cursor cursor) {
        viewHolder.description.setText(cursor.getString(cursor.getColumnIndex(helper.Post_Discription)));
        String pictureUri = cursor.getString(cursor.getColumnIndex(helper.Post_File_Url));
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(pictureUri, viewHolder.uploadedPhoto);
    }


    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        return new VH(itemView);
    }

    class VH extends RecyclerView.ViewHolder {

        public FrameLayout postCard;
        public ImageView UploaderPhoto;
        public TextView Uploadername;
        public TextView description;
        public ImageView uploadedPhoto;
        public ImageView shareButton;
        public ImageView commentButton;
        public ImageView downloadButton;
        public ImageView shareExternalButton;

        public VH(View itemView) {

            super(itemView);

            postCard = (FrameLayout) itemView.findViewById(R.id.cv);
            Uploadername = (TextView) itemView.findViewById(R.id.uploaderName);
            UploaderPhoto = (ImageView) itemView.findViewById(R.id.uploaderdp);
            description = (TextView) itemView.findViewById(R.id.discription);
            uploadedPhoto = (ImageView) itemView.findViewById(R.id.uploadedImage);
            commentButton = (ImageView) itemView.findViewById(R.id.commentbutton);
            downloadButton = (ImageView) itemView.findViewById(R.id.downloadbutton);
            shareButton = (ImageView) itemView.findViewById(R.id.share_button);
            shareExternalButton = (ImageView) itemView.findViewById(R.id.share_external_button);
        }
    }
}
