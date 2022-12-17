package com.nicole.autofarm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
/*
    /*FirebaseDatabase firebaseDatabase;

    DatabaseReference databaseReference;

    private TextView pHText;*/
    private DatabaseReference rootDatabaseref;
    private DatabaseReference tempDatabaseref;
    private DatabaseReference humidityDatabaseref;
    private DatabaseReference NDatabaseref;
    private DatabaseReference PDatabaseref;
    private DatabaseReference KDatabaseref;
    private DatabaseReference stageDatabaseref;
    private TextView pHID;
    private TextView tempID;
    private TextView humidityID;
    private TextView NID;
    private TextView PID;
    private TextView KID;
    private Spinner stage;

    private Button refresh_btn,status_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner stageSpinner= findViewById(R.id.stageSpinner);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this, R.array.stage, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stageSpinner.setAdapter(adapter);
        stageSpinner.setOnItemSelectedListener(this);

        refresh_btn=findViewById(R.id.refresh_btn);
        status_btn=findViewById(R.id.statusbtn);
        pHID=findViewById(R.id.pHId);
        tempID=findViewById(R.id.TempId);
        humidityID=findViewById(R.id.HumidityId);
        NID=findViewById(R.id.nitrogenId);
        PID=findViewById(R.id.phosphorousId);
        KID=findViewById(R.id.potassiumId);
        stage=findViewById(R.id.stageSpinner);

        rootDatabaseref=FirebaseDatabase.getInstance().getReference().child("DHT11/PH");
        tempDatabaseref=FirebaseDatabase.getInstance().getReference().child("DHT11/Temperature");
        humidityDatabaseref=FirebaseDatabase.getInstance().getReference().child("DHT11/Humidity");
        NDatabaseref=FirebaseDatabase.getInstance().getReference().child("DHT11/N");
        PDatabaseref=FirebaseDatabase.getInstance().getReference().child("DHT11/P");
        KDatabaseref=FirebaseDatabase.getInstance().getReference().child("DHT11/K");
        //stageDatabaseref=FirebaseDatabase.getInstance().getReference().child("Crop/Stage");

        refresh_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootDatabaseref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            String data=snapshot.getValue().toString();
                            pHID.setText(data);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                tempDatabaseref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            String data=snapshot.getValue().toString();
                            tempID.setText(data);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                humidityDatabaseref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            String data=snapshot.getValue().toString();
                            humidityID.setText(data);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                NDatabaseref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            String data=snapshot.getValue().toString();
                            NID.setText(data);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                PDatabaseref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            String data=snapshot.getValue().toString();
                            PID.setText(data);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                KDatabaseref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            String data=snapshot.getValue().toString();
                            KID.setText(data);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        status_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ReportActivity.class));

            }
        });
        /*
        firebaseDatabase=FirebaseDatabase.getInstance("https://autofarm-b8fbb-default-rtdb.firebaseio.com/");

        databaseReference=firebaseDatabase.getReference("pH");
        pHText=findViewById(R.id.pHId);
        getdata();*/

        //Send values to generate activity
        //pHText = findViewById(R.id.pHId);

    }
    /*
    private void getdata(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String value= snapshot.getValue(String.class);
                pHText.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(MainActivity.this,"Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });
    }*/

    /*public void generateReport(View v){
        //Send values to generate activity
        pHText = findViewById(R.id.pHId);

        String pH=pHText.getText().toString();

        Intent intent= new Intent(getApplicationContext(),ReportActivity.class);

        intent.putExtra("pH",pH);
        startActivity(intent);
        //startActivity(new Intent(MainActivity.this, ReportActivity.class));
    }*/


    public void Logout(View v){
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text= parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(),"Stage has successfully been update to "+text,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}