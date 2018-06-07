package com.rv.labourfinder.labour;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.rv.labourfinder.MainActivity;
import com.rv.labourfinder.R;

public class LReg2Fragment extends Fragment {

        public LReg2Fragment() {
        }

        EditText f2_et1,f2_et2,f2_et3;
        Spinner f2_spin1;
        Switch f2_sw1;
        Button next2;
        String prof[] ={"Carpenter","Plumber","Electrician","Painter","TV_Repair","AC_Repair","Mechanic"};
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.fragment_lreg2, container, false);

            f2_et1 = view.findViewById(R.id.f2_et1);
            f2_et2 = view.findViewById(R.id.f2_et2);
            f2_et3 = view.findViewById(R.id.f2_et3);
            f2_spin1 = view.findViewById(R.id.f2_spin1);
            f2_sw1 = view.findViewById(R.id.f2_sw1);
            next2 = view.findViewById(R.id.button03);

            ArrayAdapter c_aa = new ArrayAdapter(getContext(),android.R.layout.simple_dropdown_item_1line,prof);
            f2_spin1.setAdapter(c_aa);


            Boolean res = f2_sw1.isChecked();
            if (res) {
                LabourRegistActivity.l_avail = "Available";
            } else {
                LabourRegistActivity.l_avail = "Not available";

            }

            f2_sw1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                        LabourRegistActivity.l_avail = "Available";
                    } else {
                        LabourRegistActivity.l_avail = "Not available";
                    }
                }
            });

            f2_spin1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            if (position == 0){
                                  LabourRegistActivity.l_proff = prof[position].toString();
                            }
                            if (position == 1){
                                LabourRegistActivity.l_proff = prof[position].toString();
                            }
                            if (position == 2){
                                LabourRegistActivity.l_proff = prof[position].toString();
                            }
                            if (position == 3){
                                LabourRegistActivity.l_proff = prof[position].toString();
                            }
                            if (position == 4){
                                LabourRegistActivity.l_proff = prof[position].toString();
                            }
                            if (position == 5){
                                LabourRegistActivity.l_proff = prof[position].toString();
                            }
                            if (position == 6){
                                LabourRegistActivity.l_proff = prof[position].toString();
                            }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            next2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    String town = f2_et1.getText().toString().trim();
                    String dist = f2_et2.getText().toString().trim();
                    String state = f2_et3.getText().toString().trim();

                    if (town.isEmpty()) {
                        f2_et1.setError("Empty");
                        f2_et1.requestFocus();
                    } else {
                        if (state.isEmpty()) {
                            f2_et2.setError("Empty");
                            f2_et2.requestFocus();
                        } else {
                            if (state.isEmpty()) {
                                f2_et1.setError("Empty");
                                f2_et1.requestFocus();
                            } else {

                                LabourRegistActivity.l_town = town;
                                LabourRegistActivity.l_dist = dist;
                                LabourRegistActivity.l_state = state;

                                //move to fragment3
                                ((LabourRegistActivity) getActivity()).showFragment(new LReg3BlankFragment());
                            }

                        }

                    }


                }
            });

            return view;
        }


        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onResume() {
            super.onResume();
            ((LabourRegistActivity) getActivity()).enableNavigationIcon();
            ((LabourRegistActivity) getActivity()).setToolbarTitle(R.string.Fragment02Title);
        }
    }
