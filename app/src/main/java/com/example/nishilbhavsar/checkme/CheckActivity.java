package com.example.nishilbhavsar.checkme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CheckActivity extends AppCompatActivity implements View.OnClickListener{

    TextView textViewUser ;
    FirebaseDatabase dataBase;
    DatabaseReference countRef;
    CheckBox checkMe;
    Button buttonReset;

    int count = 0;
    int prevCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

        textViewUser = (TextView)findViewById(R.id.textViewUser);
        checkMe = (CheckBox)findViewById(R.id.checkBox);
        dataBase =  FirebaseDatabase.getInstance();
        countRef = dataBase.getReference();
        buttonReset = (Button)findViewById(R.id.buttonReset);

        textViewUser.setText("Hello! I am unchecked.");
        findViewById(R.id.textViewUser).setOnClickListener(this);


        countRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                prevCount = dataSnapshot.getValue(Integer.class);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                //Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        checkMe.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(checkMe.isChecked())
                        {
                            textViewUser.setText("You checked me out!");
                            prevCount++;
                            //String id = countRef.push().getKey();
                            countRef.setValue(prevCount);
                            Toast.makeText(getApplicationContext(), "I was checked "+prevCount+" times today.", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            textViewUser.setText("Hello! I am unchecked.");
                        }
                    }
                }
        );

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prevCount = 0;
                countRef.setValue(prevCount);
            }
        });

    }

    @Override
    public void onClick(View view)
    {
        /*switch(view.getId()) {

            case R.id.checkBox:
                checkChange();
                break;
        }*/


    }
}
