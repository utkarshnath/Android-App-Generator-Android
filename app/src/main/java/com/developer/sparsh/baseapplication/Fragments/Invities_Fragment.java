package com.developer.sparsh.baseapplication.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.developer.sparsh.baseapplication.InviteActivity;
import com.developer.sparsh.baseapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Invities_Fragment extends Fragment implements View.OnClickListener {

    private Button invite_button;
    private int REQUEST_CODE = 0;

    public Invities_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout_invities, container, false);
        invite_button = (Button) view.findViewById(R.id.invite_button);
        invite_button.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        intent.setClass(getContext(), InviteActivity.class);
        startActivityForResult(intent,REQUEST_CODE);
    }
}
