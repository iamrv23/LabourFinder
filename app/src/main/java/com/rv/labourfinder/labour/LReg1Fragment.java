package com.rv.labourfinder.labour;


import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rv.labourfinder.MainActivity;
import com.rv.labourfinder.R;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;
import static com.rv.labourfinder.labour.LabourRegistActivity.fileUri;

public class LReg1Fragment extends Fragment  {

        public LReg1Fragment() {
        }
    private static final int PICK_IMAGE_REQUEST = 1211;
    EditText f1_et1,f1_et2,f1_et3,f1_et4,f1_et5;
        Button next;
        ImageView f1_iv;
        TextView f1_tv0;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.fragment_lreg1, container, false);

            f1_iv = view.findViewById(R.id.f1_iv);
            f1_tv0 = view.findViewById(R.id.f1_tv0);
            f1_et1 = view.findViewById(R.id.f1_et1);
            f1_et2 = view.findViewById(R.id.f1_et2);
            f1_et3 = view.findViewById(R.id.f1_et3);
            f1_et4 = view.findViewById(R.id.f1_et4);
            f1_et5 = view.findViewById(R.id.f1_et5);
            next = view.findViewById(R.id.button02);

            f1_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    uploadImage(v);
                }
            });

//            f1_tv0.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    uploadImage(v);
//                }
//            });

            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String name  = f1_et1.getText().toString().trim();
                    String phone = f1_et2.getText().toString().trim();
                    String email = f1_et3.getText().toString().trim();
                    String pass = f1_et4.getText().toString().trim();
                    String cpass = f1_et5.getText().toString().trim();

                    if (name.isEmpty()) {
                        f1_et1.setError("Empty");
                        f1_et1.requestFocus();
                    } else {
                        if (phone.isEmpty()) {
                            f1_et2.setError("Empty");
                            f1_et2.requestFocus();
                        } else {
                            if (email.isEmpty()) {
                                f1_et3.setError("Empty");
                                f1_et3.requestFocus();
                            } else {
                                if (pass.isEmpty()) {
                                    f1_et4.setError("Empty");
                                    f1_et4.requestFocus();
                                } else {
                                    if (cpass.isEmpty()) {
                                        f1_et5.setError("Empty");
                                        f1_et5.requestFocus();
                                    } else {
                                        if (cpass.equals(pass)) {

                                            LabourRegistActivity.l_name  = name;
                                            LabourRegistActivity.l_phone = phone;
                                            LabourRegistActivity.l_email = email;
                                            LabourRegistActivity.l_pass = pass;
                                            LabourRegistActivity.l_cpass = cpass;

                                            //move to fragment3
                                            ((LabourRegistActivity) getActivity()).showFragment(new LReg2Fragment());

                                        } else {
                                            f1_et5.setError("Invalid Password");
                                            f1_et5.requestFocus();
                                        }

                                    }

                                }

                            }

                        }
                    }


                }
            });

            return view;
        }


    public void uploadImage(View view) {
        Intent myintent = new Intent();
        myintent.setType("image/*");
        myintent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(myintent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            fileUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), fileUri);
                f1_iv.setImageBitmap(bitmap);
                f1_tv0.setText("");
            } catch (IOException e) {
                Toast.makeText(getContext(), "done " + e, Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

        }

    }


        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onResume() {
            super.onResume();
            //((LabourRegistActivity) getActivity()).setToolbarTitle(R.string.Fragment01Title);
            ((LabourRegistActivity) getActivity()).enableNavigationIcon();
        }


}
