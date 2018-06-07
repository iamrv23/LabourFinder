package com.rv.labourfinder.labour;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rv.labourfinder.MainActivity;
import com.rv.labourfinder.R;

public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main_activity, container, false);



        view.findViewById(R.id.button01).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //move to fragment3
                ((LabourRegistActivity) getActivity()).showFragment(new LReg1Fragment());
            }
        });


        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onResume() {
        super.onResume();
        ((LabourRegistActivity) getActivity()).disableNavigationIcon();
        //((LabourRegistActivity) getActivity()).setToolbarTitle(R.string.MainFragmentTitle);
    }

}
