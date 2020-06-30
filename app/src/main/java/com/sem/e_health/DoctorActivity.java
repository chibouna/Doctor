package com.sem.e_health;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;



public class DoctorActivity extends AppCompatActivity implements ContactAdapter.ItemClickListener {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference delRf;

    FirebaseAuth mAuth;
    ContactAdapter Adapter;
    EditText searchBar ;
    TextView tvdocName;
    RecyclerView recyclerview;
    List<Client> listData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
        changeStatusBarToWhite(this);
        recyclerview = findViewById(R.id.RC);
        enableSwipeToDeleteAndUndo();
        searchBar = findViewById(R.id.edt_search);
        tvdocName = findViewById(R.id.txt_doctor_name);


        Adapter = new ContactAdapter(this, listData);
        Adapter.setClickListener(this);
        mAuth = FirebaseAuth.getInstance();
        DatabaseReference myRef = database.getReference("E-Health/Doctors/" + Sub() + "/Clients");
        DatabaseReference myRef1 = database.getReference("E-Health");
        delRf = database.getReference("E-Health/Doctors/" + Sub());


        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        ((SimpleItemAnimator) recyclerview.getItemAnimator()).setSupportsChangeAnimations(false);
        recyclerview.setAdapter(Adapter);
        recyclerview.setHasFixedSize(true);
        myRef.addValueEventListener(vel);

        Intent intent = getIntent();
        String docName = intent.getStringExtra("user");


        DatabaseReference Doc = myRef1.child("Doctors");
        Doc.child(Sub()).child("UID").setValue(mAuth.getUid());
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());

            }
        });


    }
    public static void changeStatusBarToWhite(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //  activity.getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN| View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            activity.getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            // edited here
            activity.getWindow().setStatusBarColor(Color.rgb(255,255,255));

        }
    }

    private void filter(String text) {
        //new array list that will hold the filtered data
        ArrayList<Client> filterdNames = new ArrayList<>();

        //looping through existing elements
        for (Client s : listData) {
            //if the existing elements contains the search input
            if (s.getnamaLastName().toLowerCase().contains(text.toLowerCase())) {
                //adding the element to filtered list
                filterdNames.add(s);
            }
        }

        //calling a method of the adapter class and passing the filtered list
        Adapter.filterList(filterdNames);
    }


    ValueEventListener vel = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            Client client;
            listData.clear();
            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                client = ds.getValue(Client.class);
                if (client != null) {
                    listData.add(client);

                }

            }
            Adapter.notifyDataSetChanged();
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }

    };

    private void enableSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(this) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {



                AlertDialog myQuittingDialogBox = new AlertDialog.Builder(DoctorActivity.this)
                        // set message, title, and icon
                        .setTitle("Delete")
                        .setMessage("Do you want to Delete")
                        .setIcon(R.drawable.delet1)

                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                final int position = viewHolder.getAdapterPosition();
                                Adapter.removeItem(position,delRf);
                                dialog.dismiss();
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
    public void onItemClickListener(View view, int position) {

        Intent intent = new Intent(DoctorActivity.this, Addtest.class);

        intent.putExtra("name", listData.get(position).getName());
        intent.putExtra("lastname", listData.get(position).getLastName());
        intent.putExtra("docid", Sub());
        startActivity(intent);

    }

    public String Sub() {

        String filename = (mAuth.getCurrentUser().getEmail());
        int iend = filename.indexOf("@");

        String subString;
        if (iend != -1) {
            subString = filename.substring(0, iend); //this will give abc
            return subString;
        }
        return null;
    }


    public void onLoggedOut(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(DoctorActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void onHistoryItemClickListener(View view, int position) {
        Intent intent = new Intent(DoctorActivity.this, Addtest.class);

        intent.putExtra("name", listData.get(position).getName());
        intent.putExtra("lastname", listData.get(position).getLastName());
        intent.putExtra("docid", Sub());
        startActivity(intent);

    }

    @Override
    public void onCallItemClickListener(View view, int position) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + listData.get(position).getPhone()));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(intent);
    }
}
