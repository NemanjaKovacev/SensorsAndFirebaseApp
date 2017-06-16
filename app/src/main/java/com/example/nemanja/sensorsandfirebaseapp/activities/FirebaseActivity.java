package com.example.nemanja.sensorsandfirebaseapp.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.nemanja.sensorsandfirebaseapp.R;
import com.example.nemanja.sensorsandfirebaseapp.adapters.FirebaseListViewAdapter;
import com.example.nemanja.sensorsandfirebaseapp.models.FirebaseUser;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FirebaseActivity extends AppCompatActivity {

    private EditText input_name, input_email;
    private ListView list_data;
    private ProgressBar circular_progress;
    private DatabaseReference databaseReference;
    @NonNull
    private final List<FirebaseUser> list_Firebase_users = new ArrayList<>();
    private FirebaseUser selectedFirebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firebase_activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.contacts);
        setSupportActionBar(toolbar);

        circular_progress = (ProgressBar) findViewById(R.id.circular_progress);
        input_name = (EditText) findViewById(R.id.name);
        input_email = (EditText) findViewById(R.id.email);
        list_data = (ListView) findViewById(R.id.list_data);
        list_data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull AdapterView<?> adapterView, View view, int i, long l) {
                FirebaseUser firebaseUser = (FirebaseUser) adapterView.getItemAtPosition(i);
                selectedFirebaseUser = firebaseUser;
                input_name.setText(firebaseUser.getName());
                input_email.setText(firebaseUser.getEmail());
            }
        });
        initFirebase();
        addEventFirebaseListener();
    }

    private void addEventFirebaseListener() {
        circular_progress.setVisibility(View.VISIBLE);
        list_data.setVisibility(View.INVISIBLE);

        databaseReference.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (list_Firebase_users.size() > 0)
                    list_Firebase_users.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    FirebaseUser firebaseUser = postSnapshot.getValue(FirebaseUser.class);
                    list_Firebase_users.add(firebaseUser);
                }
                FirebaseListViewAdapter adapter = new FirebaseListViewAdapter(FirebaseActivity.this, list_Firebase_users);
                list_data.setAdapter(adapter);

                circular_progress.setVisibility(View.INVISIBLE);
                list_data.setVisibility(View.VISIBLE);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void initFirebase() {
        FirebaseApp.initializeApp(this);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.firebase_menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_add) {
            createUser();
        } else if (item.getItemId() == R.id.menu_save) {
            FirebaseUser firebaseUser = new FirebaseUser(selectedFirebaseUser.getUid(),
                    input_name.getText().toString(), input_email.getText().toString());
            updateUser(firebaseUser);
        } else if (item.getItemId() == R.id.menu_remove) {
            deleteUser(selectedFirebaseUser);
        }
        return true;
    }

    private void deleteUser(@NonNull FirebaseUser selectedFirebaseUser) {
        databaseReference.child("users").child(selectedFirebaseUser.getUid()).removeValue();
        clearEditText();
    }

    private void updateUser(@NonNull FirebaseUser firebaseUser) {
        databaseReference.child("users").child(firebaseUser.getUid()).child("name").setValue(firebaseUser.getName());
        databaseReference.child("users").child(firebaseUser.getUid()).child("email").setValue(firebaseUser.getEmail());
        clearEditText();
    }

    private void createUser() {
        FirebaseUser firebaseUser = new FirebaseUser(UUID.randomUUID().toString(), input_name.getText().toString(), input_email.getText().toString());
        databaseReference.child("users").child(firebaseUser.getUid()).setValue(firebaseUser);
        clearEditText();
    }

    private void clearEditText() {
        input_name.setText("");
        input_email.setText("");
    }
}
