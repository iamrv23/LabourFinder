package com.rv.labourfinder.custmer;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rv.labourfinder.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyLabwishListFragment extends Fragment {


    public MyLabwishListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_labwish_list, container, false);
    }

}
