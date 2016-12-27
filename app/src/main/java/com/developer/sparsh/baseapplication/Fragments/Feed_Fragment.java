package com.developer.sparsh.baseapplication.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.developer.sparsh.baseapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Feed_Fragment extends Fragment {


    public Feed_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Toast.makeText(getContext(),"AAAAA",Toast.LENGTH_SHORT).show();
        return inflater.inflate(R.layout.fragment_layout_feed, container, false);
    }

}
