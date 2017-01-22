package com.developer.sparsh.baseapplication.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.developer.sparsh.baseapplication.Classes.Post;
import com.developer.sparsh.baseapplication.R;


/**
 * Created by utkarshnath on 15/01/17.
 */

public class FeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Post[] posts;
    private Context context;

    public FeedAdapter(Post[] posts, Context context){
        this.posts = posts;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        RecyclerView.ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return posts.length;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public FrameLayout postCard;
        public ImageView UploaderPhoto;
        public TextView Uploadername;
        public TextView description;
        public ImageView uploadedPhoto;
        public ImageView shareButton;
        public ImageView commentButton;
        public ImageView downloadButton;
        public ImageView shareExternalButton;
        public ViewHolder(View itemView) {
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
