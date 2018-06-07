package com.rv.labourfinder.labour;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.StorageReference;
import com.rv.labourfinder.R;

import java.io.IOException;


public class LabourRegistActivity extends AppCompatActivity {

        Toolbar toolbar;
        public static final int PICK_IMAGE_REQUEST = 1211;
        public static String l_name,l_phone, l_email, l_pass, l_proff, l_avail, l_town, l_dist, l_state, l_abtyou, l_cpass;
        public static String  l_experince;
        public static long l_phonLong;
        public static int l_experinceInt;
        public static Uri fileUri;
        public static StorageReference mStorageRef;
        public static FirebaseAuth mSignUpAuth;
        public static ImageView l_f1_iv;
        public static TextView l_f1_tv0;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_labour_regist);
            toolbar = findViewById(R.id.toolbar);
            //setSupportActionBar(toolbar);
            showFragment(new MainActivityFragment());

            mSignUpAuth = FirebaseAuth.getInstance();

        }


        protected void showFragment(Fragment fragment) {

            String TAG = fragment.getClass().getSimpleName();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

            fragmentTransaction.replace(R.id.mainContainer, fragment, TAG);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commitAllowingStateLoss();
        }

        protected void backstackFragment() {
            Log.d("Stack count", getSupportFragmentManager().getBackStackEntryCount() + "");

            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                finish();
            }
            getSupportFragmentManager().popBackStack();
            removeCurrentFragment();
        }

        private void removeCurrentFragment() {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            android.support.v4.app.Fragment currentFrag = getSupportFragmentManager()
                    .findFragmentById(R.id.mainContainer);

            if (currentFrag != null) {
                transaction.remove(currentFrag);
            }
            transaction.commitAllowingStateLoss();
        }

        @Override
        public void onBackPressed() {
            super.onBackPressed();

            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                finish();
            }
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        protected void enableNavigationIcon() {

            toolbar.setNavigationIcon(R.drawable.back_but);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    backstackFragment();
                }
            });
        }



        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        protected void disableNavigationIcon() {
            toolbar.setNavigationIcon(null);
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        protected void setToolbarTitle(int resID) {
            toolbar.setTitle(resID);
        }

    }
