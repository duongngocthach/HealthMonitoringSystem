package com.team16.pc.healthmonitoringsystem;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterInfoLoginActivity extends AppCompatActivity {
    private String fName, lName, birth, gender;
    private double height, weight;
    private EditText etEmail, etPassword;
    private Button btNext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_info_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarRegisterAcc);
        setTitle("Account");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        fName = intent.getStringExtra("FirstName");
        lName = intent.getStringExtra("LastName");
        birth = intent.getStringExtra("Birthday");
        gender = intent.getStringExtra("Gender");
        height = intent.getDoubleExtra("Height",0);
        weight = intent.getDoubleExtra("Weight",0);

        setControls();
        setEvents();
    }

    private void setEvents() {
        buttonNext();
    }

    private void setControls() {
        etEmail = findViewById(R.id.edEmail);
        etPassword = findViewById(R.id.edpassword);
        btNext = findViewById(R.id.buttonNextAcc);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case  android.R.id.home:
                //finish();
                dialogLogin();
        }
        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }
    private void buttonNext(){
        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                Intent intent = new Intent(RegisterInfoLoginActivity.this, RegisterIdDeviceActivity.class);
                intent.putExtra("FirstName",fName);
                intent.putExtra("LastName",lName);
                intent.putExtra("Birthday",birth);
                intent.putExtra("Gender",gender);
                intent.putExtra("Height", height);
                intent.putExtra("Weight", weight);
                intent.putExtra("Email",email);
                intent.putExtra("Password", password);
                startActivity(intent);
            }
        });
    }
    private void dialogLogin(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Cancel Account Creation");
        builder.setMessage("Are you sure you want to cancel account creation? This will discard any information you've entered so far");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }
}
