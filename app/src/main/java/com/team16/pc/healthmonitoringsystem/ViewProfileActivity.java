package com.team16.pc.healthmonitoringsystem;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.team16.pc.object.User;

public class ViewProfileActivity extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    private String userID;
    TextView name, fName, lName, birth, gender, height, weight, BMI;
    private Button btEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        
        setControls();
        setEvents();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarViewProfile);
        setTitle("Profile");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser user = firebaseAuth.getCurrentUser();
        userID = user.getUid().trim();

        Query query = FirebaseDatabase.getInstance().getReference("Users").orderByChild("id").equalTo(userID);
        query.addListenerForSingleValueEvent(valueEventListener);
    }

    private void setEvents() {
        btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewProfileActivity.this, EditProfileActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setControls() {
        name = findViewById(R.id.name);
        fName = findViewById(R.id.fName);
        lName = findViewById(R.id.lName);
        birth = findViewById(R.id.birthday);
        gender = findViewById(R.id.gender);
        height = findViewById(R.id.height);
        weight = findViewById(R.id.weight);
        BMI =findViewById(R.id.BMI);
        btEdit = findViewById(R.id.btEdit);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case  android.R.id.home:
                Intent intent = new Intent(ViewProfileActivity.this,HomePageActivity.class);
                startActivity(intent);
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
            if(dataSnapshot.exists()){
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    User user = snapshot.getValue(User.class);
                    name.setText(user.getFirstName()+" "+user.getLastName());
                    fName.setText("First Name: "+user.getFirstName());
                    lName.setText("Last Name: "+user.getLastName());
                    birth.setText("Birthday: "+user.getBirth());
                    gender.setText("Gender: "+user.getGender());
                    height.setText("Height: "+user.getHeight());
                    weight.setText("Weight: "+user.getWeight());
                    BMI.setText("BMI: "+user.getWeight()/((user.getHeight()/100)*(user.getHeight()/100)));
                    //Toast.makeText(ViewProfileActivity.this,user.getId(),Toast.LENGTH_LONG).show();
                }
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
}
