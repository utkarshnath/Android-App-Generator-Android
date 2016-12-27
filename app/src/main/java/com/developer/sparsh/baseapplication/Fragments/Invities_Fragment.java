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
public class Invities_Fragment extends Fragment {


    public Invities_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Toast.makeText(getContext(),"BBBBB",Toast.LENGTH_SHORT).show();
        return inflater.inflate(R.layout.fragment_layout_invities, container, false);
    }

}
