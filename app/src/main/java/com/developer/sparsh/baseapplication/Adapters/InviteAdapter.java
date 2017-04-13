package com.developer.sparsh.baseapplication.Adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.developer.sparsh.baseapplication.Classes.AdminContact;
import com.developer.sparsh.baseapplication.R;

import java.util.ArrayList;

/**
 * Created by utkarshnath on 08/04/17.
 */

public class InviteAdapter extends RecyclerView.Adapter<InviteAdapter.MyViewHolder> {

    private ArrayList<AdminContact> adminContacts;
    private ColorGenerator colorGenerator = ColorGenerator.MATERIAL;
    private TextDrawable.IBuilder drawableBuilder = TextDrawable.builder().round();

    public InviteAdapter(ArrayList<AdminContact> adminContacts) {
        this.adminContacts = adminContacts;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.name.setText(adminContacts.get(position).getName());
        holder.contact.setText(adminContacts.get(position).getNumber());
        TextDrawable drawable = drawableBuilder.build(String.valueOf(adminContacts.get(position).getName().charAt(0)), colorGenerator.getColor(adminContacts.get(position).getName()));
        holder.imageView.setImageDrawable(drawable);
        holder.linearLayout.setBackgroundColor(adminContacts.get(position).isSelected() ? Color.CYAN : Color.WHITE);
    }

    @Override
    public int getItemCount() {
        return adminContacts == null ? 0 : adminContacts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView contact;
        private ImageView imageView;
        private LinearLayout linearLayout;


        private MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.contact_name);
            contact = (TextView) itemView.findViewById(R.id.contact_info);
            imageView = (ImageView) itemView.findViewById(R.id.contact_icon);
            linearLayout = (LinearLayout) itemView.findViewById((R.id.contact_linearlayout));

            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Log.d("Selected",position+"");
                    if(adminContacts.get(position).isSelected()){
                        Log.d("Selected","fggf");
                        adminContacts.get(position).setSelected(false);
                        linearLayout.setBackgroundColor(Color.WHITE);
                    }else{
                        adminContacts.get(position).setSelected(true);
                        Log.d("Selected","lkkl");
                        linearLayout.setBackgroundColor(Color.CYAN);
                    }
//                adminContacts.get(position).setSelected(!adminContacts.get(position).isSelected);
//                holder.linearLayout.setBackgroundColor(adminContacts.get(position).isSelected ? Color.CYAN : Color.WHITE);
                    int count = 0;
                    for (int i = 0; i < adminContacts.size(); i++) {
                        if(adminContacts.get(i).isSelected()){
                            count++;
                        }
                    }
                    Log.d("!!@Selected",count+"");
                }
            });
        }
    }
}