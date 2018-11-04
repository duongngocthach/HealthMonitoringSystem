package com.team16.pc.healthmonitoringsystem;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.team16.pc.object.User;

import java.nio.Buffer;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EditProfileActivity extends AppCompatActivity {

    private TextView tvName;
    private EditText etFName, etLName, etDay, etMonth, etYear, etHeight, etWeight;
    private RadioButton rbMale, rbFemale;
    private Button btEdit;
    private String userID;
    String date;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference mData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarEditProfile);
        setTitle("Edit Profile");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser user = firebaseAuth.getCurrentUser();
        userID = user.getUid().trim();

        Query query = FirebaseDatabase.getInstance().getReference("Users").orderByChild("id").equalTo(userID);
        query.addListenerForSingleValueEvent(valueEventListener);

        setControls();
        setEvents();
        //setDate();


    }

    private void setEvents() {
        btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonEdit();
            }
        });
    }

    private void setControls() {

        tvName = findViewById(R.id.name);
        etFName  = findViewById(R.id.etFName);
        etLName = findViewById(R.id.etLName);
        etDay = findViewById(R.id.etDay);
        etMonth = findViewById(R.id.etMonth);
        etYear = findViewById(R.id.etYear);
        rbMale = findViewById(R.id.rbMale);
        rbFemale = findViewById(R.id.rbFemale);
        etHeight = findViewById(R.id.etHeight);
        etWeight = findViewById(R.id.etWeight);
        btEdit = findViewById(R.id.btEdit);
    }

    private void setDate()  {

        String date2 = date;


        try {
            //

            //Calendar calendar =  Calendar.getInstance();
            //calendar.setTime(calendar.getTime());
            //int day = calendar.get(Calendar.DAY_OF_MONTH);

        }catch (Exception e){

        }

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case  android.R.id.home:
                //finish();
                dialogCancel();
        }
        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    tvName.setText(user.getFirstName() + " " + user.getLastName());
                    etFName.setText(user.getFirstName());
                    etLName.setText(user.getLastName());
                    date = user.getBirth();
                    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    try {
                        Date date1 = formatter.parse(date);
                        Calendar calendar =  Calendar.getInstance();
                        calendar.setTime(date1);
                        int day = calendar.get(Calendar.DAY_OF_MONTH);
                        int month = calendar.get(Calendar.MONTH)+1;
                        int year = calendar.get(Calendar.YEAR);
                        etDay.setText(String.valueOf(day));
                        etMonth.setText(String.valueOf(month));
                        etYear.setText(String.valueOf(year));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    String Gender = user.getGender();
                    if(Gender.equals("Male")){
                        rbMale.setChecked(true);
                    }else if(Gender.equals("Female")){
                        rbFemale.setChecked(true);
                    }
                    //Toast.makeText(EditProfileActivity.this,String.valueOf(user.getHeight()),Toast.LENGTH_LONG).show();
                    etHeight.setText(String.valueOf(user.getHeight()));
                    etWeight.setText(String.valueOf(user.getWeight()));
                    //BMI.setText("BMI: " + user.getWeight() / ((user.getHeight() / 100) * (user.getHeight() / 100)));

                }
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

        private void buttonEdit(){
        mData = FirebaseDatabase.getInstance().getReference("Users").child(userID);
        mData.child("firstName").setValue(etFName.getText().toString().trim());
        mData.child("lastName").setValue(etLName.getText().toString().trim());
        String birth = etDay.getText().toString().trim()+"/"+ etMonth.getText().toString().trim()+"/"+ etYear.getText().toString().trim();
        mData.child("birth").setValue(birth);
        String gender = null;
        if(rbMale.isChecked()){
            gender = "Male";
        }
        if (rbFemale.isChecked()){
            gender = "Female";
        }
        mData.child("gender").setValue(gender);
        mData.child("height").setValue(Double.parseDouble(etHeight.getText().toString().trim()));
        mData.child("weight").setValue(Double.parseDouble(etWeight.getText().toString().trim()));
        dialogSuccess();
    }

    private void dialogCancel(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Cancel");
        builder.setMessage("Are you sure you want to cancel edit profile?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(EditProfileActivity.this,ViewProfileActivity.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }
    private void dialogSuccess(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Successful");
        builder.setMessage("edit successful");
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(EditProfileActivity.this,ViewProfileActivity.class);
                startActivity(intent);
            }
        });
        builder.show();
    }


}
