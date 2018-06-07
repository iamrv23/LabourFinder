package com.rv.labourfinder.custmer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rv.labourfinder.LogInActivity;
import com.rv.labourfinder.R;
import com.rv.labourfinder.labour.MainActivityFragment;
import com.squareup.picasso.Picasso;

public class CustmerNavDrawActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ProgressDialog pd;
    ImageView c_iv;
    TextView c_nm;
    FirebaseDatabase db;
    String name,photoUrl;
    FloatingActionButton fab_car,fab_ele;
    Fragment fragment;
    public static  String custmer_name = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custmer_nav_draw);


        pd = new ProgressDialog(this);
        pd.setMessage("Please wait ...");
        pd.show();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        View view = navigationView.getHeaderView(0);
        c_nm = view.findViewById(R.id.c_nm);
        c_iv = view.findViewById(R.id.c_iv);



        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        //fiebase data retrive
        final String uID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Log.e("user ----->",uID);

        db = FirebaseDatabase.getInstance();

        DatabaseReference dr = db.getReference("Custmer");
        DatabaseReference dbr = dr.child(uID);

        dbr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //if (dataSnapshot.exists()) {

                name = dataSnapshot.child("name").getValue(String.class);
//                email = dataSnapshot.child("l_email").getValue(String.class);
//                prof = dataSnapshot.child("l_proff").getValue(String.class);
//                phon = dataSnapshot.child("l_phone").getValue(Long.class);
//                avail = dataSnapshot.child("l_avail").getValue(String.class);
//                loc = dataSnapshot.child("l_town").getValue(String.class);
                 photoUrl = dataSnapshot.child("image").getValue(String.class);

                c_nm.setText(name);
//                tv_prof.setText(prof);
//                tv_email.setText(email);
//                tv_loc.setText(loc);
//                tv_phon.setText(""+phon);
//                tv_avail.setText(avail);
                Picasso.with(CustmerNavDrawActivity.this).load(photoUrl).into(c_iv);
                pd.dismiss();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        navigationView.setNavigationItemSelectedListener(this);
        fragment = new WelcomeFragment();
        displaySelectedFragment(fragment);



    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.custmer_nav_draw, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.i1) {

            SharedPreferences saveData = getSharedPreferences("LogInData", MODE_PRIVATE);
            SharedPreferences.Editor spd = saveData.edit();
            spd.putString("type", "N/A");
            spd.putString("Email", "N/A");
            spd.putString("pass", "N/A");
            spd.putString("usid", "N/A");
            spd.commit();
            finish();
            startActivity(new Intent(this, LogInActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment = null;

        if (id == R.id.wel_frag) {
            fragment = new WelcomeFragment();
            displaySelectedFragment(fragment);

        } else if (id == R.id.mylab_frag) {
            fragment = new MyLabourListFragment();
            displaySelectedFragment(fragment);

        } else if (id == R.id.nav_slideshow) {
            fragment = new MyLabwishListFragment();
            displaySelectedFragment(fragment);

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void displaySelectedFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainContainer, fragment);
        fragmentTransaction.commit();
    }
    

}
