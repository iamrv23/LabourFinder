package com.rv.labourfinder.labour;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rv.labourfinder.LogInActivity;
import com.rv.labourfinder.R;
import com.rv.labourfinder.firebase.MyLabFirebase;
import com.squareup.picasso.Picasso;

public class LProfileView extends AppCompatActivity {

    TextView tv_prof,tv_email,tv_phon, tv_loc, tv_avail, tv_name;
    String  name,lname;
    String email;
    String photoUrl;
    String uid;
    String emailVerified;
    String loc;
    String prof;
    String avail;
    Long  phon;
    ImageView iv;

    public static String lab_prof = null;

    ProgressDialog pd;
    FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lprofile_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        tv_name = findViewById(R.id.tv_name);
        tv_prof = findViewById(R.id.tv_profname);
        tv_email = findViewById(R.id.tv_email);
        tv_phon = findViewById(R.id.tv_phon);
        tv_loc = findViewById(R.id.tv_loc);
        tv_avail = findViewById(R.id.tv_avail);
        iv = findViewById(R.id.iv);

        pd = new ProgressDialog(this);
        pd.setMessage("Please wait ...");
        pd.show();

        String login_type = LogInActivity.login_typ;
        String login_prof_type  = LogInActivity.login_prof_typ;


        final String uID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Log.e("user ----->",uID);

        db = FirebaseDatabase.getInstance();



        DatabaseReference dr = db.getReference("labour").child(login_prof_type);
        DatabaseReference dbr = dr.child(uID);

        Log.e("logintype ---->",login_type);
        Log.e("loginproftype ---->",login_prof_type);


        dbr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                //if (dataSnapshot.exists()) {

                    name = dataSnapshot.child("l_name").getValue(String.class);
                    email = dataSnapshot.child("l_email").getValue(String.class);
                    prof = dataSnapshot.child("l_proff").getValue(String.class);
                    phon = dataSnapshot.child("l_phone").getValue(Long.class);
                    avail = dataSnapshot.child("l_avail").getValue(String.class);
                    loc = dataSnapshot.child("l_town").getValue(String.class);
                    photoUrl = dataSnapshot.child("image1").getValue(String.class);

                    Log.e("Photourl ----->", ""+photoUrl);



                    tv_name.setText(name);
                    tv_prof.setText(prof);
                    tv_email.setText(email);
                    tv_loc.setText(loc);
                    tv_phon.setText(""+phon);
                    tv_avail.setText(avail);
                    Picasso.with(LProfileView.this).load(photoUrl).into(iv);
                    pd.dismiss();


//                } else {
//                    pd.dismiss();
//                    Toast.makeText(LProfileView.this, "No Data found..", Toast.LENGTH_SHORT).show();
//                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void logoutfromprof(View view) {

        SharedPreferences saveData = getSharedPreferences("LogInData", MODE_PRIVATE);
        SharedPreferences.Editor spd = saveData.edit();
        spd.putString("type", "N/A");
        spd.putString("Email", "N/A");
        spd.putString("pass", "N/A");
        spd.putString("usid", "N/A");
        spd.commit();
        finish();
        startActivity(new Intent(LProfileView.this, LogInActivity.class));
    }
}
