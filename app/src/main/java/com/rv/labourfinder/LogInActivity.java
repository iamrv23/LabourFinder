package com.rv.labourfinder;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rv.labourfinder.Admin.SignupAdmin;
import com.rv.labourfinder.custmer.CustmerNavDrawActivity;
import com.rv.labourfinder.labour.LProfileView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LogInActivity extends AppCompatActivity
{

    //firebase auth....
    private DatabaseReference mDatabaseRef;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog, mProgressChecking;

    // bind views using bindviews in butter knife..

    @BindView(R.id.user_name) EditText mUsername;
    @BindView(R.id.pass) EditText mPassword;

    public static String login_prof_typ =null;
    public static String login_typ =null;

    @BindView(R.id.user_name_input_login) TextInputLayout user_name_input_login;
    @BindView(R.id.pass_input_login) TextInputLayout pass_input_login;
    private Spinner select_type, select_prof_type;
    private String type[] = {"-:Select Type:-","Admin","Customer","Labour"};
    private String lproff_type[] = {"-:Select Type:-","Carpenter","Plumber","Electrician","Painter","TV_Repair","AC_Repair","Mechanic"};

    private ArrayAdapter select_type_adapter, select_prof_type_adapter ;
    private Button sign_up,login_btn;
    //@BindView(R.id.set_error) TextView mSetError;

    ///flag for user checking
    boolean flag;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        //button ref
        sign_up = findViewById(R.id.sign_btn_all);
        login_btn = findViewById(R.id.loginbut);
        //progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("LogIn");
        progressDialog.setMessage("Please Wait.....");


        //for checking progress
        mProgressChecking = new ProgressDialog(this);
        mProgressChecking.setCancelable(false);
        //progressDialog.setTitle("LogIn");
        mProgressChecking.setMessage("Checking Please Wait.....");

        //instance of firebase auth...
        firebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Admin");

        //spinner
        select_type = findViewById(R.id.select_type);
        select_prof_type = findViewById(R.id.select_prof_type);


        //check the user is logged in or not

        //   bind current activity with Butter knife...

        ButterKnife.bind(this);


        //spinner adapter
        select_type_adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,type)
        {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    return
                            false;

                } else {
                    return
                            true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent)
            {

                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0)
                {
                    tv.setTextColor(Color.GRAY);
                }
                else
                {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };


        select_type.setAdapter(select_type_adapter);

        select_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                if (select_type.getSelectedItemPosition() != 0)
                {
                    mUsername.setEnabled(true);
                    mPassword.setEnabled(true);
                    login_btn.setEnabled(true);
                    sign_up.setEnabled(true);

                }
                if (select_type.getSelectedItem().toString().trim().equals("Labour"))
                {
                    select_prof_type.setVisibility(View.VISIBLE);
                    login_typ = select_type.getSelectedItem().toString().trim();
                }
                else
                {
                    select_prof_type.setVisibility(View.GONE);
                }

                if (select_type.getSelectedItem().toString().trim().equals("Admin")) {
                    sign_up.setVisibility(View.VISIBLE);
                } else {
                    sign_up.setVisibility(View.INVISIBLE);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        //spinner adapter
        select_prof_type_adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,lproff_type)
        {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    return
                            false;

                } else {
                    return
                            true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent)
            {

                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0)
                {
                    tv.setTextColor(Color.GRAY);
                }
                else
                {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        select_prof_type.setAdapter(select_prof_type_adapter);

        select_prof_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (select_type.getSelectedItem().toString().trim().equals("Labour")) {

                    if (select_prof_type.getSelectedItemPosition() != 0) {
                        mUsername.setEnabled(true);
                        mPassword.setEnabled(true);
                        login_btn.setEnabled(true);
                        sign_up.setEnabled(false);

                        login_prof_typ = select_prof_type.getSelectedItem().toString().trim();

                        Log.e("prof -->",select_prof_type.getSelectedItem().toString().trim());

                    }
                    else {

                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    @Override
    protected void onStart()
    {
        super.onStart();
        /*
        if(firebaseAuth.getCurrentUser() != null)
        {
            finish();
            startActivity(new Intent(LogInActivity.this, AdminPanel.class));
        }
         */
        if (firebaseAuth.getCurrentUser() != null)
        {
            //get data from local device....
            SharedPreferences get_saveData = getSharedPreferences("LogInData", MODE_PRIVATE);
            String mType = get_saveData.getString("type", "N/A");
            String mEmail = get_saveData.getString("Email", "N/A");
            String mPass = get_saveData.getString("pass", "N/A");
            String mUsid = get_saveData.getString("usid", "N/A");
            //SaveDataLocal sv = new SaveDataLocal();
            if (mEmail.equals("N/A") && mPass.equals("N/A") && mUsid.equals("N/A"))
            {
                Toast.makeText(this, "Data Null", Toast.LENGTH_SHORT).show();
            }
            else
            {
                if (mType.equals("Admin"))
                {
//                    finish();
//                    Intent i = new Intent(LogInActivity.this,AdminPanel.class);
//                    i.putExtra("usid",mUsid);
//                    startActivity(i);
                    //startActivity(new Intent(LogInActivity.this, AdminPanel.class));
                    Toast.makeText(this, "Admin page", Toast.LENGTH_SHORT).show();
                }
                else if (mType.equals("Customer"))
                {
                    finish();
                    startActivity(new Intent(LogInActivity.this, CustmerNavDrawActivity.class));
                }
                else if (mType.equals("Labour"))
                {
                    finish();
                    startActivity(new Intent(LogInActivity.this, LProfileView.class));
                    Toast.makeText(this, "Labour page", Toast.LENGTH_SHORT).show();


                }
            }
        }
    }

    /**
     * Login Data when Button is Click...
     * @param view
     */
    public void loginData(View view)
    {

        final String userEmail= mUsername.getText().toString().trim();
        final String userPassword = mPassword.getText().toString().trim();

        if (userEmail.isEmpty())
        {
            mUsername.setError("Email Id Required");
            user_name_input_login.requestFocus();
        }
        else
        {
            user_name_input_login.setErrorEnabled(false);
            if (userPassword.isEmpty())
            {
                mPassword.setError("Password Required");
                pass_input_login.requestFocus();
            }
            else
            {
                if (select_type.getSelectedItem().toString().trim().equals("Admin"))
                {
                    adminPanelPage(userEmail,userPassword);
                }
                else if (select_type.getSelectedItem().toString().trim().equals("Customer"))
                {
                    custmerPanel(userEmail,userPassword);
                }
                else if (select_type.getSelectedItem().toString().trim().equals("Labour"))
                {

                    if (select_prof_type.getSelectedItem().toString().trim().equals("Carpenter")) {
                        labourPanel(userEmail, userPassword);
                    } else if (select_prof_type.getSelectedItem().toString().trim().equals("Plumber")) {
                        labourPanel(userEmail, userPassword);

                    } else if (select_prof_type.getSelectedItem().toString().trim().equals("Plumber")) {
                        labourPanel(userEmail, userPassword);

                    }else if (select_prof_type.getSelectedItem().toString().trim().equals("Electrician")) {
                        labourPanel(userEmail, userPassword);

                    }else if (select_prof_type.getSelectedItem().toString().trim().equals("Painter")) {
                        labourPanel(userEmail, userPassword);

                    }else if (select_prof_type.getSelectedItem().toString().trim().equals("TV_Repair")) {
                        labourPanel(userEmail, userPassword);

                    }
                    else if (select_prof_type.getSelectedItem().toString().trim().equals("AC_Repair")) {
                        labourPanel(userEmail, userPassword);

                    }
                    else if (select_prof_type.getSelectedItem().toString().trim().equals("Mechanic")) {
                        labourPanel(userEmail, userPassword);
                    }
                    else {
                        Toast.makeText(this, "Select Proffesion..", Toast.LENGTH_SHORT).show();
                    }
                } 

            }
        }

    }

    private void custmerPanel(final String userEmail, final String userPassword)
    {
        pass_input_login.setErrorEnabled(false);
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(userEmail,userPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {
                            String mCurrentUID = firebaseAuth.getCurrentUser().getUid();
                            //SaveDataLocal saveData = new SaveDataLocal(LogInActivity.this,select_type.getSelectedItem().toString().trim(),userEmail,userPassword,mCurrentUID);
                            saveDataPref(select_type.getSelectedItem().toString().trim(),userEmail,userPassword,mCurrentUID);
                            finish();
                            startActivity(new Intent(LogInActivity.this, CustmerNavDrawActivity.class));
                            progressDialog.dismiss();
                        }
                        else
                        {
                            //mSetError.setVisibility(View.VISIBLE);
                            Toast.makeText(LogInActivity.this, "Username and Password Incorrect", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                });
    }



    private void labourPanel(final String userEmail, final String userPassword)
    {
        pass_input_login.setErrorEnabled(false);
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(userEmail,userPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {
                            String mCurrentUID = firebaseAuth.getCurrentUser().getUid();
                            //SaveDataLocal saveData = new SaveDataLocal(LogInActivity.this,select_type.getSelectedItem().toString().trim(),userEmail,userPassword,mCurrentUID);
                            saveDataPref(select_type.getSelectedItem().toString().trim(),userEmail,userPassword,mCurrentUID);
                            finish();
                            startActivity(new Intent(LogInActivity.this, LProfileView.class));
                            progressDialog.dismiss();
                        }
                        else
                        {
                            //mSetError.setVisibility(View.VISIBLE);
                            Toast.makeText(LogInActivity.this, "Username and Password Incorrect", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                });
    }




    private void adminPanelPage(final String userEmail, final String userPassword)
    {
        pass_input_login.setErrorEnabled(false);
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(userEmail,userPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {
                            String mCurrentUID = firebaseAuth.getCurrentUser().getUid();
                            saveDataPref(select_type.getSelectedItem().toString().trim(),userEmail,userPassword,mCurrentUID);
//                            finish();
//                            Intent i = new Intent(LogInActivity.this,AdminPanel.class);
//                            i.putExtra("usid",mCurrentUID);
//                            startActivity(i);
                            //startActivity(new Intent(LogInActivity.this, AdminPanel.class));
                            Toast.makeText(LogInActivity.this, "admin page", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                        else
                        {
                            //mSetError.setVisibility(View.VISIBLE);
                            Toast.makeText(LogInActivity.this, "Username and Password Incorrect", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                });
    }


    private void saveDataPref(String sel_type,String userEmail, String userPassword, String mCurrentUID)
    {
        SharedPreferences my_saveData = getSharedPreferences("LogInData",MODE_PRIVATE);
        SharedPreferences.Editor editor = my_saveData.edit();
        editor.putString("type",sel_type);
        editor.putString("Email",userEmail);
        editor.putString("pass",userPassword);
        editor.putString("usid",mCurrentUID);
        editor.commit();
    }

    private String getDataPref()
    {
        SharedPreferences get_saveData = getSharedPreferences("LogInData",MODE_PRIVATE);
        String mEmail = get_saveData.getString("Email","N/A");
        String mPass = get_saveData.getString("pass","N/A");
        String mUsid = get_saveData.getString("usid","N/A");
        if (mEmail.equals("N/A") && mPass.equals("N/A") && mUsid.equals("N/A"))
        {
            //saveDataPref();
        }
        return mEmail;
    }
    //check id is admin or not
    private boolean checkIdIsAdmin(String userEmail)
    {
        //progressDialog.show();
        //String uid = firebaseAuth.getCurrentUser().getUid();
        DatabaseReference mCheckData = mDatabaseRef.child("AllEmail").child(userEmail);
        mCheckData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {

                if (dataSnapshot.exists())
                {
                    flag = true;
                }
                else
                {
                    progressDialog.dismiss();
                    flag = false;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });
        return flag;
    }

    private void logInThePage()
    {

    }

    public void signUpPage(View view)
    {
        final Dialog verifyDialog = new Dialog(this);
        verifyDialog.setTitle("Verify Code");
        verifyDialog.setContentView(R.layout.verify_code);
        verifyDialog.setCancelable(false);

        //set progress dialog
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Code Verifying");
        progressDialog.setCancelable(false);

        //assign both button
        Button cancel_butn = verifyDialog.findViewById(R.id.cancel_btn);
        final Button verify_btn  = verifyDialog.findViewById(R.id.verify_btn);
        final EditText verify_txt = verifyDialog.findViewById(R.id.verify_text);
//        final TextInputLayout mSetError = verifyDialog.findViewById(R.id.verify_inputlayot);
        cancel_butn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                verifyDialog.dismiss();
            }
        });

        verify_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                progressDialog.show();
                String  verify = verify_txt.getText().toString().trim();
                if (verify.equals("GD101"))
                {
                    //mSetError.setErrorEnabled(false);
                    verifyDialog.dismiss();
                    startActivity(new Intent(LogInActivity.this, SignupAdmin.class));
                    progressDialog.dismiss();
                }
                else
                {
                    progressDialog.dismiss();
                    //mSetError.setErrorEnabled(true);
                    verify_txt.setError("Invalid Code");
                    verify_txt.requestFocus();
                }
            }
        });

        verifyDialog.show();
    }


}

