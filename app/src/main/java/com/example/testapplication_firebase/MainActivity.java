package com.example.testapplication_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    EditText editText_Name;
    EditText editText_Address;
    EditText editText_conNum;

    Button btn_save;
    Button btn_show;
    Button btn_update;
    Button btn_delete;

    //object to increment student id
    static int sID = 001;

    Student studentObj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
try {

    //data base reference
    final DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Student");
    studentObj = new Student();

    editText_Name = findViewById(R.id.name);
    editText_Address = findViewById(R.id.address);
    editText_conNum = findViewById(R.id.conNum);

    btn_delete = findViewById(R.id.btn_delete);
    btn_save = findViewById(R.id.btn_save);
    btn_show = findViewById(R.id.btn_show);
    btn_update = findViewById(R.id.btn_update);

    //Save data to the database
    btn_save.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(TextUtils.isEmpty(editText_Name.getText().toString())){
                Toast.makeText(MainActivity.this,"Please enter Name",Toast.LENGTH_SHORT).show();
            }
            else if(TextUtils.isEmpty(editText_Address.getText().toString())){
                Toast.makeText(MainActivity.this,"Please enter Address",Toast.LENGTH_SHORT).show();
            }
            else if(TextUtils.isEmpty(editText_conNum.getText().toString())){
                Toast.makeText(MainActivity.this,"Please enter Contact",Toast.LENGTH_SHORT).show();
            }
            else {
                studentObj.setID("ID"+sID);
                studentObj.setName(editText_Name.getText().toString().trim());
                studentObj.setAddress(editText_Address.getText().toString().trim());
                studentObj.setConNo(Integer.parseInt(editText_conNum.getText().toString().trim()));

                reference.child("Sid1").setValue(studentObj);
                sID++;
                Toast.makeText(MainActivity.this,"Data saved successfully",Toast.LENGTH_SHORT).show();
                clearControls();
            }
        }
    });

    //show data in the database
    final DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference().child("Student").child("Sid1");
    btn_show.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            reference2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.hasChildren()){
                        editText_Name.setText(dataSnapshot.child("name").getValue().toString());
                        editText_Address.setText(dataSnapshot.child("address").getValue().toString());
                        editText_conNum.setText(dataSnapshot.child("conNo").getValue().toString());

                    }else {
                        Toast.makeText(MainActivity.this,"No data to show",Toast.LENGTH_SHORT);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    });

    //update data on database
    final DatabaseReference reference3 = FirebaseDatabase.getInstance().getReference().child("Student").child("Sid1");
    btn_update.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            reference3.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChildren()) {


                        if (TextUtils.isEmpty(editText_Name.getText().toString())) {
                            Toast.makeText(MainActivity.this, "Please enter Name", Toast.LENGTH_SHORT).show();
                        } else if (TextUtils.isEmpty(editText_Address.getText().toString())) {
                            Toast.makeText(MainActivity.this, "Please enter Address", Toast.LENGTH_SHORT).show();
                        } else if (TextUtils.isEmpty(editText_conNum.getText().toString())) {
                            Toast.makeText(MainActivity.this, "Please enter Contact", Toast.LENGTH_SHORT).show();
                        } else {
                            studentObj.setName(editText_Name.getText().toString().trim());
                            studentObj.setAddress(editText_Address.getText().toString().trim());
                            studentObj.setConNo(Integer.parseInt(editText_conNum.getText().toString().trim()));
                            studentObj.setID(dataSnapshot.child("id").getValue().toString());

                            reference3.setValue(studentObj);

                            Toast.makeText(MainActivity.this, "Data Updated successfully", Toast.LENGTH_SHORT).show();
                            clearControls();
                        }

                    }else{
                        Toast.makeText(MainActivity.this,"No data to update",Toast.LENGTH_SHORT);
                    }
                }

                    @Override
                    public void onCancelled (@NonNull DatabaseError databaseError){

                    }

            });

        }
    });

    //delete data on database
    btn_delete.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            reference3.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.hasChildren()){
                        reference3.removeValue();
                        Toast.makeText(MainActivity.this,"Data deleted",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(MainActivity.this,"No data to update",Toast.LENGTH_SHORT);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    });
}catch (Exception ex){
    Toast.makeText(this,"Error on save data",Toast.LENGTH_SHORT).show();
}

    }

    //clear data on the data fields
    private void clearControls() {

        editText_Name.setText("");
        editText_Address.setText("");
        editText_conNum.setText("");
    }
}
