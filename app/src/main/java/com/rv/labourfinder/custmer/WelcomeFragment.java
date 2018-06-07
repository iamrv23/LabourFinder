package com.rv.labourfinder.custmer;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rv.labourfinder.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WelcomeFragment extends Fragment {


    public WelcomeFragment() {
        // Required empty public constructor
    }
    FloatingActionButton fab_car,fab_ele,fab_plu,fab_paint,fab_tv_rep,fab_ac_rep,fab_mech;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_welcome, container, false);

        fab_car = view.findViewById(R.id.fab_car);
        fab_ele = view.findViewById(R.id.fab_ele);
        fab_plu = view.findViewById(R.id.fab_plu);
        fab_paint = view.findViewById(R.id.fab_paint);
        fab_tv_rep = view.findViewById(R.id.fab_tv_rep);
        fab_ac_rep = view.findViewById(R.id.fab_ac_rep);
        fab_mech = view.findViewById(R.id.fab_mech);


        fab_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("fab click --->", "fab is cliked");

                Intent i = new Intent(getActivity(),LabourAllList.class);
                i.putExtra("proff","Carpenter");
                startActivity(i);
            }
        });

        fab_ele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("fab click --->", "fab is cliked");

                Intent i = new Intent(getActivity(),LabourAllList.class);
                i.putExtra("proff","Electrician");
                startActivity(i);
            }
        });
        fab_plu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("fab click --->", "fab is cliked");

                Intent i = new Intent(getActivity(),LabourAllList.class);
                i.putExtra("proff","Plumber");
                startActivity(i);
            }
        });
        fab_paint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("fab click --->", "fab is cliked");

                Intent i = new Intent(getActivity(),LabourAllList.class);
                i.putExtra("proff","Painter");
                startActivity(i);
            }
        });
        fab_tv_rep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("fab click --->", "fab is cliked");

                Intent i = new Intent(getActivity(),LabourAllList.class);
                i.putExtra("proff","TV_Repair");
                startActivity(i);
            }
        });

        fab_ac_rep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("fab click --->", "fab is cliked");

                Intent i = new Intent(getActivity(),LabourAllList.class);
                i.putExtra("proff","AC_Repair");
                startActivity(i);
            }
        });
        fab_mech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("fab click --->", "fab is cliked");

                Intent i = new Intent(getActivity(),LabourAllList.class);
                i.putExtra("proff","Mechanic");
                startActivity(i);
            }
        });

        return view;

    }

}
