package com.rv.labourfinder.labour;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rv.labourfinder.LogInActivity;
import com.rv.labourfinder.MainActivity;
import com.rv.labourfinder.R;
import com.rv.labourfinder.custmer.CustmerRegistActivity;
import com.rv.labourfinder.firebase.MyCusFirebase;
import com.rv.labourfinder.firebase.MyLabFirebase;

import java.net.URI;

import static com.rv.labourfinder.labour.LabourRegistActivity.fileUri;
import static com.rv.labourfinder.labour.LabourRegistActivity.l_abtyou;
import static com.rv.labourfinder.labour.LabourRegistActivity.l_avail;
import static com.rv.labourfinder.labour.LabourRegistActivity.l_dist;
import static com.rv.labourfinder.labour.LabourRegistActivity.l_email;
import static com.rv.labourfinder.labour.LabourRegistActivity.l_name;
import static com.rv.labourfinder.labour.LabourRegistActivity.l_pass;
import static com.rv.labourfinder.labour.LabourRegistActivity.l_phonLong;
import static com.rv.labourfinder.labour.LabourRegistActivity.l_phone;
import static com.rv.labourfinder.labour.LabourRegistActivity.l_proff;
import static com.rv.labourfinder.labour.LabourRegistActivity.l_state;
import static com.rv.labourfinder.labour.LabourRegistActivity.l_town;
import static com.rv.labourfinder.labour.LabourRegistActivity.mSignUpAuth;


public class LReg3BlankFragment extends Fragment {

    public LReg3BlankFragment() {
    }

    Spinner f3_spin1;
    EditText f3_et1;
    CheckBox f3_cb1;;
    Button done;
    String exp [] ={"0","1","2","3","4","5","6","7"};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_lreg3_blank, container, false);
        f3_spin1 = view.findViewById(R.id.f3_spin1);
        f3_et1 = view.findViewById(R.id.f3_et1);
        f3_cb1 = view.findViewById(R.id.f3_cb1);
        done = view.findViewById(R.id.button04);

        ArrayAdapter c_aa = new ArrayAdapter(getContext(),android.R.layout.simple_dropdown_item_1line,exp);
        f3_spin1.setAdapter(c_aa);
        done.setClickable(false);


        f3_spin1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    if (position == 1){
                        LabourRegistActivity.l_experince = exp[position].toString();
                    }
                    if (position == 2){
                        LabourRegistActivity.l_experince = exp[position].toString();
                    }
                    if (position == 3){
                        LabourRegistActivity.l_experince = exp[position].toString();
                    }
                    if (position == 4){
                        LabourRegistActivity.l_experince = exp[position].toString();
                    }
                    if (position == 5){
                        LabourRegistActivity.l_experince = exp[position].toString();
                    }
                    if (position == 6){
                        LabourRegistActivity.l_experince = exp[position].toString();
                    }
                } else {
                    //Toast.makeText(getContext(), "Select Proff.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (f3_cb1.isChecked()) {
                    LabourRegistActivity.l_abtyou = f3_et1.getText().toString().trim();

                    final int l_experinceInt = Integer.parseInt(LabourRegistActivity.l_experince);
                    final long l_phonLong = Long.parseLong(LabourRegistActivity.l_phone);



                    //firebase auth
                    mSignUpAuth.createUserWithEmailAndPassword(l_email,l_pass)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()) {

                                        final String uid = mSignUpAuth.getCurrentUser().getUid();
                                        Log.e("log ------>", ""+uid);

                                        //image upload
                                        LabourRegistActivity.mStorageRef = FirebaseStorage.getInstance().getReference();
                                        final StorageReference adminRef = LabourRegistActivity.mStorageRef.child("Labour/" + uid + ".jpg");
                                        adminRef.putFile(LabourRegistActivity.fileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                //Toast.makeText(SignupAdmin.this, "Okk", Toast.LENGTH_SHORT).show()
                                                //finish();
                                                Uri myuri = taskSnapshot.getDownloadUrl();
                                                //firebase coding
                                                MyLabFirebase myLabFirebase = new MyLabFirebase(l_name, l_email, l_pass, l_proff, l_avail, l_town, l_dist, l_state, l_abtyou, l_experinceInt, l_phonLong, myuri.toString());
                                                FirebaseDatabase db = FirebaseDatabase.getInstance();
                                                DatabaseReference dr = db.getReference("labour").child(l_proff).child(uid);
                                                dr.setValue(myLabFirebase);
                                                Toast.makeText(getActivity(), "Registration Successful", Toast.LENGTH_SHORT).show();


                                                getActivity().finish();
                                                startActivity(new Intent(getActivity(), LogInActivity.class));
                                            }
                                        });


                                        //move to fragment3
                                        //((LabourRegistActivity) getActivity()).showFragment(new MainActivityFragment());

                                    }
                                    else {
                                        try {
                                            throw task.getException();
                                        } catch (Exception e) {
                                            Log.e("email ----->",""+l_email);
                                            Log.e("erre ---> "," "+e);
                                            Toast.makeText(getContext(), "----> " + e, Toast.LENGTH_SHORT).show();
                                        }

                                    }


                                }
                            });

                }

                else {
                    f3_cb1.setError("Please Checked first");
                    f3_cb1.requestFocus();
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
