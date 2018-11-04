package com.team16.pc.healthmonitoringsystem;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity {
    Button btLogin, btRegister;
    private EditText etUsername, etPassword;
    private FirebaseAuth mAuth;
    private boolean isUserClickBackButton = false;
    //public static
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        setControls();
        setEvent();


    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }

    private void setEvent() {

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,CreateAccountActivity.class);
                startActivity(intent);
            }
        });
    }

    private void Login(){
        final String email = etUsername.getText().toString().trim();
        final String password = etPassword.getText().toString().trim();
        Load();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent intent = new Intent(LoginActivity.this,HomePageActivity.class);
                    startActivity(intent);
                    //Toast.makeText(LoginActivity.this,"Thanh cong",Toast.LENGTH_LONG).show();
                }
                else {

                    dialogLogin("The email or password you entered is incorrect. Please try again");
                    //Toast.makeText(LoginActivity.this,"That bai",Toast.LENGTH_LONG).show();
                }

            }
        });
    }
    @Override
    public void onBackPressed() {
            //super.onBackPressed();
            // check no navigation
            if(!isUserClickBackButton){
                Toast.makeText(LoginActivity.this, "Press Back again to exit",Toast.LENGTH_LONG).show();
                isUserClickBackButton = true;
            }else {
                //super.onBackPressed();
                //exit app
                Intent startMain = new Intent(Intent.ACTION_MAIN);
                startMain.addCategory(Intent.CATEGORY_HOME);
                startActivity(startMain);
                //finish();
            }
            // set time restore bacb press
            new CountDownTimer(3000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    isUserClickBackButton = false;
                }
            }.start();


    }
    public void Load(){
        Intent intentLoad = new Intent(LoginActivity.this, LoadingActivity.class);
        startActivity(intentLoad);
    }
    private void dialogLogin(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Login fail");
        builder.setMessage(message);
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //finish();
            }
        });
        builder.show();
    }
    private void setControls() {
        btLogin = findViewById(R.id.buttonLogin);
        btRegister = findViewById(R.id.buttonRegister);
        etUsername = findViewById(R.id.userName);
        etPassword = findViewById(R.id.password);
    }


}
