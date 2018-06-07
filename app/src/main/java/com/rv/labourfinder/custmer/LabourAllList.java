package com.rv.labourfinder.custmer;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rv.labourfinder.R;
import com.rv.labourfinder.firebase.MyLabFirebase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class LabourAllList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference myref;
    private ArrayList al,alName,aluri,alUID;
    String la_prof;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labour_all_list);

        Intent i = getIntent();
        Bundle b = i.getExtras();
        la_prof = (String) b.get("proff");

        al = new ArrayList();

        getAllUid();

        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myref= FirebaseDatabase.getInstance().getReference().child("labour").child(la_prof);
        FirebaseRecyclerAdapter<MyLabFirebase,LabourAllList.BlogViewHolder> recyclerAdapter=new FirebaseRecyclerAdapter<MyLabFirebase,LabourAllList.BlogViewHolder>(
                MyLabFirebase.class,
                R.layout.style_mylistitem,
                LabourAllList.BlogViewHolder.class,
                myref
        ) {
            @Override
            protected void populateViewHolder(LabourAllList.BlogViewHolder viewHolder, MyLabFirebase model, final int position) {
                viewHolder.setTv_name(model.getL_name());
                viewHolder.setTv_add(model.getL_town());
                viewHolder.setTv_status(model.getL_avail());
                viewHolder.setImage(model.getImage1());

                viewHolder.but_lab_view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        myref= FirebaseDatabase.getInstance().getReference().child("labour").child(la_prof).child(""+al.get(position));


                        myref.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                HashMap<String,String> hm = (HashMap<String, String>) dataSnapshot.getValue();
                                String name = hm.get("l_name");
                                String email = hm.get("l_email");
                                String prof = hm.get("l_proff");
                                String phon = String.valueOf(hm.get("l_phone"));
                                String avail = hm.get("l_avail");
                                String loc = hm.get("l_town");
                                String photoUrl = hm.get("image1");

                                LayoutInflater li = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
                                View v = li.inflate(R.layout.activity_profile,null);

                                CircleImageView iv = v.findViewById(R.id.prof_iv);
                                TextView t_name = v.findViewById(R.id.prof_name);
                                TextView t_prof = v.findViewById(R.id.prof_proff);
                                TextView t_email = v.findViewById(R.id.prof_email);
                                TextView t_phon = v.findViewById(R.id.prof_phon);
                                TextView t_loc = v.findViewById(R.id.prof_loc);
                                TextView t_avail = v.findViewById(R.id.prof_avail);
                                Button button = v.findViewById(R.id.btn_dismiss);

                                t_name.setText(name);
                                t_prof.setText(prof);
                                t_email.setText(email);
                                t_prof.setText(prof);
                                t_phon.setText(phon);
                                t_avail.setText(avail);
                                t_loc.setText(loc);

                                Picasso.with(LabourAllList.this).load(photoUrl).into(iv);


                                if (avail.equals("Available")) {
                                    iv.setBorderColor(Color.GREEN);
                                } else {
                                    iv.setBorderColor(Color.WHITE);
                                }

                                AlertDialog.Builder adb = new AlertDialog.Builder(LabourAllList.this);
                                adb.setView(v);
                                final AlertDialog ad = adb.create();
                                ad.show();

                                button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        ad.dismiss();
                                    }
                                });

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                        }
                });
            }
        };
        recyclerView.setAdapter(recyclerAdapter);
    }
    public static class BlogViewHolder extends RecyclerView.ViewHolder {
        View mView;
        TextView tv_name,tv_add,tv_status;
        Button but_lab_view;

        ImageView imageView;
        public BlogViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
            tv_name = (TextView)itemView.findViewById(R.id.tv1);
            tv_add = (TextView)itemView.findViewById(R.id.tv2);
            tv_status = (TextView)itemView.findViewById(R.id.tv3);
            imageView=(ImageView)itemView.findViewById(R.id.iv);
            but_lab_view = itemView.findViewById(R.id.but_view);
        }

        public void setTv_name(String name)
        {
            tv_name.setText(name);
        }
        public void setTv_add(String add)
        {
            tv_add.setText(add);
        }
        public void setTv_status(String avail)
        {
            tv_status.setText(avail);
        }

        public void setImage(String image)
        {
            Picasso.with(mView.getContext())
                    .load(image)
                    .into(imageView);
        }

    }

    private void  getAllUid()
    {
//
//        //-------getting all uid of student to delete data further------
//        //if (Teacher_Attendance_Activity.teach_branchh!= null && Teacher_Attendance_Activity.teach_sem != null) {
//        // Log.e("inside get uid--->", tech_branch + tech_sem);
        myref = FirebaseDatabase.getInstance().getReference("labour").child(la_prof);
        myref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {

                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String uid = ds.getKey();
                        al.add(uid);
                        Log.e("uid ----->", " "+uid);
//                    }
                }
            }
        }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

            });
        }
    }

