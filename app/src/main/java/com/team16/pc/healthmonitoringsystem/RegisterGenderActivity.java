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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class RegisterGenderActivity extends AppCompatActivity {

    private String fName, lName, birth;
    private EditText height, weight;
    private Button btNext;
    private String gender;
    private RadioButton radioButtonMale, radioButtonFemale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_other);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarRegisterGender);
        setTitle("Other");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        fName = intent.getStringExtra("FirstName");
        lName = intent.getStringExtra("LastName");
        birth = intent.getStringExtra("Birthday");


        setControls();
        setEvent();
    }

    private void setControls() {
        btNext = findViewById(R.id.btNext3);
        radioButtonMale = findViewById(R.id.male);
        radioButtonFemale = findViewById(R.id.female);
        height = findViewById(R.id.height);
        weight = findViewById(R.id.weight);
    }

    private void setEvent() {

        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(radioButtonMale.isChecked()){
                    gender = "Male";
                }
                if (radioButtonFemale.isChecked()){
                    gender = "Female";
                }
                double height1 = Double.valueOf(height.getText().toString());
                double weight1 = Double.valueOf(weight.getText().toString());
                Toast.makeText(RegisterGenderActivity.this,""+height1,Toast.LENGTH_LONG).show();
                Intent intent = new Intent(RegisterGenderActivity.this, RegisterInfoLoginActivity.class);
                intent.putExtra("FirstName",fName);
                intent.putExtra("LastName",lName);
                intent.putExtra("Birthday",birth);
                intent.putExtra("Gender",gender);
                intent.putExtra("Height", height1);
                intent.putExtra("Weight", weight1);
                startActivity(intent);
            }
        });

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
