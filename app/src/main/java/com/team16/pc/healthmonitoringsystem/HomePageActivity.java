package com.team16.pc.healthmonitoringsystem;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

public class HomePageActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private CardView cardView;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private DatabaseReference databaseReference;
    private String userID;
    private TextView  name, BMI, mainName;

    private boolean isUserClickBackButton = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setControls();
        //Toast.makeText(this,""+userID,Toast.LENGTH_LONG).show();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
         name = (TextView) headerView.findViewById(R.id.headerName);
         BMI = headerView.findViewById(R.id.headerBMI);


        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        userID = user.getUid().trim();

        Query query = FirebaseDatabase.getInstance().getReference("Users").orderByChild("id").equalTo(userID);
        query.addListenerForSingleValueEvent(valueEventListener);
        //Toast.makeText(this,""+userID,Toast.LENGTH_LONG).show();

        cardView = findViewById(R.id.cardViewHealth);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent as = new Intent(HomePageActivity.this, ViewHealthActivity.class);
                startActivity(as);
            }
        });


    }

    private void setControls() {
        //name = findViewById(R.id.headerName);
        //BMI = findViewById(R.id.headerBMI);
        mainName = findViewById(R.id.mainName);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
            // check no navigation
            if(!isUserClickBackButton){
                Toast.makeText(HomePageActivity.this, "Press Back again to exit",Toast.LENGTH_LONG).show();
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

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //@SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_Profile) {
            Intent intent =new  Intent(HomePageActivity.this, ViewProfileActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_Device) {

        } else if (id == R.id.nav_Family) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.Privacy) {

        } else if (id == R.id.nav_LogOut) {
            FirebaseAuth.getInstance().signOut();
            /*Intent intent = new Intent(HomePageActivity.this,LoginActivity.class);
            startActivity(intent);*/
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if(dataSnapshot.exists()){
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    User user = snapshot.getValue(User.class);
                    name.setText(user.getFirstName()+" "+user.getLastName());
                    BMI.setText("BMI: "+user.getWeight()/((user.getHeight()/100)*(user.getHeight()/100)));
                    mainName.setText(user.getFirstName()+" "+user.getLastName());
                    //Toast.makeText(ViewProfileActivity.this,user.getId(),Toast.LENGTH_LONG).show();
                }
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
}
