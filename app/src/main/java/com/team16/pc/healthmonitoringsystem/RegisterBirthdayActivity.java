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
import android.widget.DatePicker;
import android.widget.Toast;

public class RegisterBirthdayActivity extends AppCompatActivity {

    private String fName,lName;
    private Button btNext;
    private DatePicker datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_birthday);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarRegisterBirth);
        setTitle("Birth");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        fName = intent.getStringExtra("FirstName");
        lName = intent.getStringExtra("LastName");

        setControls();
        setEvent();


    }

    private void setEvent() {

        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth()+1;
                int year = datePicker.getYear();
                String birth = day+"/"+month+"/"+year;
                Toast.makeText(RegisterBirthdayActivity.this,""+ birth, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(RegisterBirthdayActivity.this, RegisterGenderActivity.class);
                intent.putExtra("FirstName", fName);
                intent.putExtra("LastName", lName);
                intent.putExtra("Birthday",birth);
                startActivity(intent);
            }
        });

    }

    private void setControls() {
        btNext = findViewById(R.id.buttonNext2);
        datePicker = findViewById(R.id.datePicker);
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
