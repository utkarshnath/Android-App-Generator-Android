package com.developer.sparsh.baseapplication.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.developer.sparsh.baseapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Event_Info_Fragment extends Fragment {


    public Event_Info_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_layout_event_info, container, false);
    }

}
