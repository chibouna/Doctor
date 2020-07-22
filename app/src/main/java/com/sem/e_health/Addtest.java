package com.sem.e_health;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.sem.e_health.App.CHANNEL_1_ID;
import static com.sem.e_health.DoctorActivity.changeStatusBarToWhite;


public class Addtest extends AppCompatActivity {
    List<Test> testList = new ArrayList<>();
    RecAdapter adapter ;
    RecyclerView recyclerview ;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference tempRef;
    DatabaseReference glucRef;
    DatabaseReference hardbeatsRef;
    DatabaseReference emgRef;
    String emg ;
    String glucose ;
    String temp ;
    String hartbeats ;
    String finalDate;
    String name;
    String lastname ;
    DatabaseReference testRef ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_tests);
        changeStatusBarToWhite(this);
        recyclerview = findViewById(R.id.RC1);
        enableSwipeToDeleteAndUndo();

        adapter = new RecAdapter(this,testList);
       ((SimpleItemAnimator) recyclerview.getItemAnimator()).setSupportsChangeAnimations(false);

       findViewById(R.id.img_back).setOnClickListener(v -> finish());


        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(adapter);
        recyclerview.setHasFixedSize(true);
        Intent intent = getIntent();
         name = intent.getStringExtra("name");
         lastname = intent.getStringExtra("lastname");
        String docID = intent.getStringExtra("docid");
        tempRef = database.getReference("E-Health/Client live test /"+name+" "+lastname+"/Temp");
        hardbeatsRef = database.getReference("E-Health/Client live test /"+name+" "+lastname+"/Heart Beats");
        emgRef = database.getReference("E-Health/Client live test /"+name+" "+lastname+"/EMG");
        glucRef = database.getReference("E-Health/Client live test /"+name+" "+lastname+"/Glucose");
        DatabaseReference myRef = database.getReference("E-Health/Doctors/"+docID+"/Clients TESTS");
        testRef = myRef.child(name+" "+lastname+" TESTS");
        hardbeatsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                hartbeats = (String) dataSnapshot.getValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        glucRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                glucose = (String) dataSnapshot.getValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        emgRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                emg = (String) dataSnapshot.getValue();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        tempRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                temp = (String) dataSnapshot.getValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Date c = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        String dateformatted = dateFormat.format(date);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);
        finalDate = formattedDate +" "+dateformatted ;
        /*FloatingActionButton back = findViewById(R.id.back);
        back.setOnClickListener(v ->{

            startActivity(new Intent(Addtest.this,DoctorActivity.class));
        });
*/



        testRef.addValueEventListener(vel);
    }

    private void enableSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(this) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                AlertDialog myQuittingDialogBox = new AlertDialog.Builder(Addtest.this)
                        // set message, title, and icon
                        .setTitle("Delete")
                        .setMessage("Do you want to Delete")
                        .setIcon(R.drawable.delete)

                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                final int position = viewHolder.getAdapterPosition();
                                if (position+1 == testList.size()){
                                    Toast.makeText(Addtest.this, "Can't delete last test", Toast.LENGTH_SHORT).show();
                                    finish();
                                    startActivity(getIntent());
                                }
                                else{
                                    adapter.removeItem(position,testRef);
                                    dialog.dismiss();}
                            }

                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();
                                finish();
                                startActivity(getIntent());


                            }
                        })
                        .create();
                myQuittingDialogBox.setCanceledOnTouchOutside(false);
                myQuittingDialogBox.show();








            }

        };
        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(recyclerview);
    }

    ValueEventListener vel = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            Test test ;
            testList.clear();
            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                test = ds.getValue(Test.class);
                if (test != null) {
                    testList.add(test);

                }

            }
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

}
