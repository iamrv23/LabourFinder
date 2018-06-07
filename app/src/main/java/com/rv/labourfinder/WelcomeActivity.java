package com.rv.labourfinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.rv.labourfinder.custmer.CustmerRegistActivity;
import com.rv.labourfinder.labour.LabourRegistActivity;

public class WelcomeActivity extends AppCompatActivity {

    Button login_pg,but_reg,but_labour;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        login_pg = findViewById(R.id.login_pg);
        but_reg = findViewById(R.id.but_user);
        but_labour = findViewById(R.id.but_labour);

        login_pg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  i  = new Intent(WelcomeActivity.this,LogInActivity.class);
                startActivity(i);
            }
        });

        but_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  i  = new Intent(WelcomeActivity.this,CustmerRegistActivity.class);
                startActivity(i);
            }
        });

        but_labour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  i  = new Intent(WelcomeActivity.this,LabourRegistActivity.class);
                startActivity(i);
            }
        });
    }

}
