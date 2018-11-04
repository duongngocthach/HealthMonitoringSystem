package com.team16.pc.healthmonitoringsystem;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.team16.pc.object.User;

public class RegisterIdDeviceActivity extends AppCompatActivity {

    private String fName, lName, birth, gender, email, password;
    private double height, weight;

    private EditText etIdDevice;
    private Button register;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_id_device);

        firebaseAuth = FirebaseAuth.getInstance();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarRegisterDevice);
        setTitle("ID Device");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        fName = intent.getStringExtra("FirstName");
        lName = intent.getStringExtra("LastName");
        birth = intent.getStringExtra("Birthday");
        gender = intent.getStringExtra("Gender");
        height = intent.getDoubleExtra("Height",0);
        weight = intent.getDoubleExtra("Weight",0);
        email = intent.getStringExtra("Email");
        password = intent.getStringExtra("Password");

        setControls();
        setEvent();

        //Toast.makeText(RegisterActivity.this,""+fName+lName+birth+gender+height+weight+email+password,Toast.LENGTH_LONG).show();

    }

    private void setControls() {
        etIdDevice = findViewById(R.id.idDevice);
        register = findViewById(R.id.btRegister);

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

    private void setEvent() {
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();
            }
        });

    }

    public void Load() {
        Intent intentLoad = new Intent(RegisterIdDeviceActivity.this, LoadingActivity.class);
        startActivity(intentLoad);
    }

    private void Register() {

        Load();
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    String idDevice = etIdDevice.getText().toString().trim();
                    String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    //Toast.makeText(RegisterActivity.this,""+fName+lName+birth+gender+height+weight+idDevice,Toast.LENGTH_LONG).show();
                    User user = new User(id, email,fName,lName,birth,gender,height,weight,idDevice);
                    FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(RegisterIdDeviceActivity.this,"Thanh cong",Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(RegisterIdDeviceActivity.this,LoginActivity.class);
                                startActivity(intent);
                            }
                        }
                    });

                } else {
                    Toast.makeText(RegisterIdDeviceActivity.this, "That bai", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private void dialogCancel(){
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

