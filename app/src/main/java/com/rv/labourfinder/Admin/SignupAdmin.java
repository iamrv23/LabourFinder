package com.rv.labourfinder.Admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rv.labourfinder.LogInActivity;
import com.rv.labourfinder.R;
import com.rv.labourfinder.custmer.CustmerNavDrawActivity;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class SignupAdmin extends AppCompatActivity {


    //for image upload
    private CircleImageView admin_image;
    final int PICK_IMAGE_REQUEST = 1;
    private Bitmap bitmap;
    private Uri fileUri;


    //for storage
    private StorageReference mStorageRef;
    private ProgressDialog signupDialog;

    //Firebase Auth
    private FirebaseAuth mSignUpAuth;

    private DatabaseReference mRootDB;

    private EditText msignUp_name, msignUp_email, msignUp_mobile, msignUp_password, msignUp_con_password;
    private Button msignUp_btn;

    private TextInputLayout msignUp_name_textinput, msignUp_email_textinput, msignUp_mobile_textinput, msignUp_password_textinput, msignUp_con_password_textinput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_admin);

        //find image
        admin_image = findViewById(R.id.adm_logo);

        //set root as admin
        mRootDB = FirebaseDatabase.getInstance().getReference("Admin");

        ///instanc of auth
        mSignUpAuth = FirebaseAuth.getInstance();

        //find all edit text by id
        msignUp_name = findViewById(R.id.adm_et1);
        msignUp_email = findViewById(R.id.adm_et3);
        //msignUp_dob = findViewById(R.id.signUp_dob);
        msignUp_mobile = findViewById(R.id.adm_et2);
        msignUp_password = findViewById(R.id.adm_et4);
        msignUp_con_password = findViewById(R.id.adm_et5);
        //msignUp_add = findViewById(R.id.signUp_add);


//        //find the input layout by id
//        msignUp_name_textinput = findViewById(R.id.signUp_name_inputtext);
//        msignUp_email_textinput = findViewById(R.id.signUp_email_inputtext);
//        msignUp_mobile_textinput = findViewById(R.id.signUp_mo_nbr_inputtext);
//        msignUp_password_textinput = findViewById(R.id.signUp_pass_inputtext);
//        msignUp_con_password_textinput = findViewById(R.id.signUp_con_pass_inputtext);
        // msignUp_add_textinput = findViewById(R.id.signUp_add_inputtext);

        //mRootDB = FirebaseDatabase.getInstance().getReference("Admin");

    }


    public void signUpData(View view) {
        //final ProgressDialog mCheckUserDialog = null;
        final String madminFullName = msignUp_name.getText().toString().trim();
        final String madminEmail = msignUp_email.getText().toString().trim();
        final String madminMobile = msignUp_mobile.getText().toString().trim();
        final String madminPassword = msignUp_password.getText().toString().trim();
        final String c_madminPassword = msignUp_con_password.getText().toString().trim();

        //String madminAddress = msignUp_password.getText().toString().trim();

        //set the dialog

        signupDialog = new ProgressDialog(SignupAdmin.this);
        signupDialog.setTitle("SignUp");
        signupDialog.setMessage("Please Wait");
        signupDialog.setCancelable(false);
        signupDialog.show();

        mSignUpAuth.createUserWithEmailAndPassword(madminEmail, madminPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {


                            final String uid = mSignUpAuth.getCurrentUser().getUid();

                            mStorageRef = FirebaseStorage.getInstance().getReference();

                            final String user_sid = mSignUpAuth.getCurrentUser().getUid();
                            Toast.makeText(SignupAdmin.this, "SignUp Success ", Toast.LENGTH_SHORT).show();

                            final DatabaseReference mChildRootDB = mRootDB.child(user_sid);
                            /*
                            //store all email
                            final DatabaseReference mEmailRootDB = mRootDB.child("AllEmail");
                            UpdateAdmin up1 = new UpdateAdmin(madminEmail);
                            mEmailRootDB.setValue(up1);
                             */


                            Toast.makeText(SignupAdmin.this, "SignUp Success email", Toast.LENGTH_SHORT).show();

                            //insert the image
                            mStorageRef = FirebaseStorage.getInstance().getReference();
                            final StorageReference adminRef = mStorageRef.child("Admin/" + user_sid + ".jpg");
                            adminRef.putFile(fileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
                            {

                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    //Toast.makeText(SignupAdmin.this, "Okk", Toast.LENGTH_SHORT).show()
                                    Uri downloadURL = taskSnapshot.getDownloadUrl();
                                    mChildRootDB.child("FullName").setValue(madminFullName);
                                    mChildRootDB.child("EmailID").setValue(madminEmail.toLowerCase());
                                    mChildRootDB.child("Password").setValue(madminPassword);
                                    mChildRootDB.child("Mobile_No").setValue(madminMobile);
                                    mChildRootDB.child("Image2").setValue(downloadURL.toString());

                                    Toast.makeText(SignupAdmin.this, "Your Logged In", Toast.LENGTH_SHORT).show();

                                    saveDataPref("Admin",madminEmail,madminPassword,user_sid);
                                    Intent i = new Intent(SignupAdmin.this,LogInActivity.class);
                                    i.putExtra("usid",user_sid);
                                    startActivity(i);
                                    /*
                                    //insert into database
                                    MyDataBase md = new MyDataBase(SignupAdmin.this);
                                    SQLiteDatabase sqldb = md.getWritableDatabase();
                                    try
                                    {
                                        String ins_query ="insert into "+MyDataBase.TABLE_NAME+" values('"+user_sid+"','"+madminFullName+"')";
                                        sqldb.execSQL(ins_query);
                                        saveDataPref("Admin",madminEmail,madminPassword,user_sid);
                                        Toast.makeText(SignupAdmin.this, "Inserted", Toast.LENGTH_SHORT).show();
                                        finish();
                                        Intent i = new Intent(SignupAdmin.this,AdminPanel.class);
                                        i.putExtra("usid",user_sid);
                                        startActivity(i);
                                        //startActivity(new Intent(SignupAdmin.this, AdminPanel.class));
                                        signupDialog.dismiss();
                                    }
                                    catch (Exception e)
                                    {
                                        Toast.makeText(SignupAdmin.this, "Values "+e, Toast.LENGTH_SHORT).show();
                                        e.printStackTrace();
                                    }

                                     */
                                }
                            });
                            signupDialog.dismiss();
                        }
                        else
                        {
                            signupDialog.dismiss();
                            // If sign in fails, display a message to the user.
                            //Toast.makeText(SignupAdmin.this, "Wrong", Toast.LENGTH_SHORT).show();
                           /*
                            msignUp_email_textinput.setErrorEnabled(true);
                            msignUp_email_textinput.setError("Already Register With This Email Id..");
                            msignUp_email_textinput.requestFocus();
                            */
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthUserCollisionException emailExits) {
                                msignUp_email_textinput.setErrorEnabled(true);
                                msignUp_email_textinput.setError("Email Id Exits");
                            } catch (FirebaseAuthWeakPasswordException weakPassword) {
                                msignUp_password_textinput.setErrorEnabled(true);
                                msignUp_password_textinput.setError("Weak Password");
                            } catch (Exception e) {
                                Toast.makeText(SignupAdmin.this, "m " + e, Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                });


        /*
        mRootAdmin.child(madminUserName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    signupDialog.dismiss();
                    //Toast.makeText(SignupAdmin.this, "Exits", Toast.LENGTH_SHORT).show();
                    msignUp_user_textinput.setErrorEnabled(true);
                    msignUp_user_textinput.setError("User Exits Try Another");
                    msignUp_user_textinput.requestFocus();
                }
                else
                {
                    //Toast.makeText(SignupAdmin.this, "Not Exits", Toast.LENGTH_SHORT).show();
                    DatabaseReference mRootAdminuser = mRootAdmin.child(madminUserName);
                    //insert the data
                    mRootAdminuser.child("Name").setValue(madminFullName);
                    //mRootAdmin.child("State").setValue(madminState);
                    //mRootAdmin.child("City").setValue(madminCity);
                    //mRootAdmin.child("College").setValue(madminClg);
                    mRootAdminuser.child("Department").setValue(madminDept);
                    mRootAdminuser.child("Email").setValue(madminEmail);
                    mRootAdminuser.child("Mobile").setValue(madminMobile);
                    mRootAdminuser.child("UserName").setValue(madminUserName);
                    mRootAdminuser.child("Password").setValue(madminPassword);

                    //set the dialog
                    signupDialog = new ProgressDialog(SignupAdmin.this);
                    signupDialog.setTitle("SignUp");
                    signupDialog.setMessage("Please Wait");
                    signupDialog.setCancelable(false);
                    signupDialog.show();



                }
           }
         */


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

    //Validate when SignUp Button is Click..........

   /*

    private boolean signUpValidate()
    {
        boolean status = false;
        int mStateSelectedPosition = mStateSpinner.getSelectedItemPosition();
        int mCitySelectedPosition = mCitySpinner.getSelectedItemPosition();
        int mClgSelectedPosition = mClgSpinner.getSelectedItemPosition();
        int mDeptSelectedPosition = mDeptSpinner.getSelectedItemPosition();

        if (fileUri != null) {
            if (msignUp_name.getText().toString().trim().isEmpty()) {
                //msignUp_name.setError("Empty");
                //msignUp_name.requestFocus();
                msignUp_name_textinput.setError(getString(R.string.name));
                msignUp_name_textinput.requestFocus();
                status = false;
            } else if (msignUp_name.getText().toString().trim().matches("[a-zA-Z ]+")) {
                msignUp_name_textinput.setErrorEnabled(false);
                if (msignUp_lastname.getText().toString().trim().isEmpty()) {
                    msignUp_lastname_textinput.setError(getString(R.string.lastname));
                    msignUp_lastname_textinput.requestFocus();
                    status = false;
                } else if (msignUp_lastname.getText().toString().trim().matches("[a-zA-Z ]+")) {
                    msignUp_lastname_textinput.setErrorEnabled(false);



                    if (mStateSelectedPosition == 0) {
                        Toast.makeText(this, "Select State", Toast.LENGTH_SHORT).show();
                        status = false;
                    } else if (mCitySelectedPosition == 0) {
                        Toast.makeText(this, "Select City", Toast.LENGTH_SHORT).show();
                        status = false;
                    } else if (mCitySelectedPosition == 0) {
                        Toast.makeText(this, "Select College", Toast.LENGTH_SHORT).show();
                        status = false;
                    }


    {
        Toast.makeText(this, "Select Department", Toast.LENGTH_SHORT).show();
        status = false;
    }
                    else if (msignUp_email.getText().toString().trim().isEmpty())
    {
        msignUp_email_textinput.setError(getString(R.string.email));
        msignUp_email_textinput.requestFocus();
        status = false;
    }
                    else if (msignUp_email.getText().toString().trim().matches("[a-zA-z ]+"))
    {
        msignUp_email_textinput.setErrorEnabled(false);
        if (msignUp_mobile.getText().toString().trim().isEmpty())
        {
            msignUp_dob_textinput.setErrorEnabled(false);
            msignUp_mobile_textinput.setError(getString(R.string.mobile));
            msignUp_mobile_textinput.requestFocus();
            status = false;
        }
        else if (msignUp_mobile.getText().toString().trim().matches("[0-9]+"))
        {
            msignUp_mobile_textinput.setErrorEnabled(false);
            if (msignUp_user.getText().toString().trim().isEmpty()) {
                msignUp_user_textinput.setError(getString(R.string.signup_user));
                msignUp_user_textinput.requestFocus();
                status = false;
            }
            else if (msignUp_user.getText().toString().trim().matches("[a-z0-9 ]+")) {
                msignUp_user_textinput.setErrorEnabled(false);
                if (msignUp_password.getText().toString().trim().isEmpty()) {
                    msignUp_password_textinput.setError(getString(R.string.signUp_password));
                    msignUp_password_textinput.requestFocus();
                    status = false;
                }
                else if (msignUp_password.getText().toString().trim().matches("[a-z0-9 ]+"))
                {
                    msignUp_password_textinput.setErrorEnabled(false);
                    if (msignUp_add.getText().toString().trim().isEmpty())
                    {
                        msignUp_add_textinput.setError(getString(R.string.signUp_address));
                        msignUp_add_textinput.requestFocus();
                        status = false;
                    }
                    else if (msignUp_add.getText().toString().trim().matches("[a-zA-Z0-9 ]+"))
                    {
                        msignUp_add_textinput.setErrorEnabled(false);
                        status = true;
                    }
                    else {
                        msignUp_add_textinput.setErrorEnabled(true);
                        msignUp_add_textinput.setError("Invalid Address");
                        msignUp_add_textinput.requestFocus();
                        status = false;
                    }
                } else {
                    msignUp_password_textinput.setErrorEnabled(true);
                    status = false;
                }
            } else {
                msignUp_user_textinput.setErrorEnabled(true);
                msignUp_user_textinput.setError("Invalid UserName");
                msignUp_user_textinput.requestFocus();
                status = false;
            }

        } else {
            msignUp_mobile_textinput.setErrorEnabled(true);
            msignUp_mobile_textinput.setError("Invalid Mobile Number");
            msignUp_mobile_textinput.requestFocus();
            status = false;
        }
    } else {
    msignUp_email_textinput.setErrorEnabled(true);
    msignUp_email_textinput.setError("Invalid Email");
    msignUp_email_textinput.requestFocus();
    status = false;
}
} else {
        msignUp_lastname_textinput.setErrorEnabled(true);
        msignUp_lastname_textinput.setError("Invalid LastName");
        msignUp_lastname.requestFocus();
        status = false;
        }
        } else {
        msignUp_name_textinput.setErrorEnabled(true);
        msignUp_name_textinput.setError("Invalid Name");
        msignUp_name_textinput.requestFocus();
        status = false;
        }
        }
        else {
        status = false;
        }
        return status;
        }
    */


    //upload image
    public void uploadImage(View view) {
        Intent myintent = new Intent();
        myintent.setType("image/*");
        myintent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(myintent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            fileUri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), fileUri);
                admin_image.setImageBitmap(bitmap);
            } catch (IOException e) {
                Toast.makeText(this, "dd " + e, Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

        }
    }


}