package com.rv.labourfinder.custmer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rv.labourfinder.LogInActivity;
import com.rv.labourfinder.R;
import com.rv.labourfinder.WelcomeActivity;
import com.rv.labourfinder.firebase.MyCusFirebase;

import java.io.File;
import java.io.IOException;

public class CustmerRegistActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1211;
    EditText et1,et2,et3,et4;
    Button cus_reg_but;
    ImageView iv;
    TextView tv;
    ProgressDialog pd;
    Uri fileUri;
    StorageReference mStorageRef;
    private FirebaseAuth mSignUpAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custmer_regist);

        et1 = findViewById(R.id.et1);
        et2 = findViewById(R.id.et2);
        et3 = findViewById(R.id.et3);
        et4 = findViewById(R.id.et4);
        cus_reg_but = findViewById(R.id.cusRegBut);
        tv = findViewById(R.id.tv0);
        pd = new ProgressDialog(this);
        iv = findViewById(R.id.iv);

        mSignUpAuth = FirebaseAuth.getInstance();


        cus_reg_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String name = et1.getText().toString().trim();
                final String cNo = et2.getText().toString().trim();
                final String email = et3.getText().toString().trim();
                final String pass = et4.getText().toString().trim();

                if (name.isEmpty()) {
                    et1.setError("Empty");
                    et1.requestFocus();
                } else if (name.matches("[a-zA-Z ]+")) {
                    if (name.contains("  ")) {
                        et1.setError("Multiple WhiteSpace not Allowed");
                    } else if (name.startsWith(" ") || name.endsWith(" ")) {
                        et1.setError("WhiteSpace not Allowed in start and end of Name");
                    } else {
                        if (cNo.isEmpty()) {
                            et2.setError("Empty");
                            et2.requestFocus();

                        } else if (cNo.length()!= 10){
                            et2.setError("Invalid Mobl. No.");
                            et2.requestFocus();
                        }else {

                            if (email.isEmpty()) {
                                et3.setError("Empty");
                                et3.requestFocus();
                            } else if (!email.contains("@")){
                                et3.setError("Invalid email");
                                et3.requestFocus();
                            } else {
                                if (pass.isEmpty()){
                                    et4.setError("Empty");
                                    et4.requestFocus();
                                } else {

                                    mSignUpAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {

                                            if (task.isSuccessful()) {
                                                pd.setMessage("Please wait ...");
                                                pd.show();
                                                final Long phon = Long.parseLong(cNo);

                                                final String uid = mSignUpAuth.getCurrentUser().getUid();

                                                mStorageRef = FirebaseStorage.getInstance().getReference();
                                                final StorageReference adminRef = mStorageRef.child("Admin/" + uid + ".jpg");
                                                adminRef.putFile(fileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                        //Toast.makeText(SignupAdmin.this, "Okk", Toast.LENGTH_SHORT).show()
                                                        //finish();

                                                        Uri uri = taskSnapshot.getDownloadUrl();

                                                        MyCusFirebase my = new MyCusFirebase(name, email, phon, pass,uri.toString());
                                                        String cont = et2.getText().toString();
                                                        FirebaseDatabase db = FirebaseDatabase.getInstance();
                                                        DatabaseReference dr = db.getReference("Custmer").child(uid);
                                                        dr.setValue(my);

                                                        Toast.makeText(CustmerRegistActivity.this, "Your Logged In", Toast.LENGTH_SHORT).show();
                                                        et1.setText("");
                                                        et2.setText("");
                                                        et3.setText("");
                                                        et4.setText("");
                                                        pd.dismiss();
                                                        et1.requestFocus();
                                                        //Toast.makeText(UrRegActivity.this, "Succeesfully Registered", Toast.LENGTH_SHORT).show();
                                                        Intent i = new Intent(CustmerRegistActivity.this,LogInActivity.class);
                                                        startActivity(i);
                                                    }
                                                });

                                            } else {
                                                try {
                                                    throw task.getException();
                                                } catch (Exception e) {
                                                    Log.e("erre ---> "," "+e);
                                                    Toast.makeText(CustmerRegistActivity.this, "----> " + e, Toast.LENGTH_SHORT).show();
                                                }
                                                Toast.makeText(CustmerRegistActivity.this, "not success", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                                }

                            }
                        }
                    }
                } else {
                    et1.setError("Invalid Type");
                }
            }
        });

    }

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
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), fileUri);
                iv.setImageBitmap(bitmap);
                tv.setText("");
            } catch (IOException e) {
                Toast.makeText(this, "dd " + e, Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

        }

    }

    public void goto_loginpg(View view) {
        finish();
        Intent i = new Intent(this,LogInActivity.class);
        startActivity(i);
    }
}
