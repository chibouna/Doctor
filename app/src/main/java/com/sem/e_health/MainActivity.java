package com.sem.e_health;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import static com.sem.e_health.DoctorActivity.changeStatusBarToWhite;


public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    TextView registre ;
    ImageView BtLogin ;
    EditText email ;
    EditText password ;
  //private   Switch s1;
SwipeRefreshLayout swipeRefreshLayout;

   private WifiManager wifiManager;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        changeStatusBarToWhite(this);
        mAuth = FirebaseAuth.getInstance();
        BtLogin = findViewById(R.id.imageView33);
        email = findViewById(R.id.edt_email);
        password = findViewById(R.id.edt_password);
       BtLogin.setOnClickListener(view -> Login());
       registre = findViewById(R.id.tc_registre);
        //registre.setOnClickListener(ls);
        //s1=findViewById(R.id.switch2);
        registre.setOnClickListener(v -> register());
swipeRefreshLayout=findViewById(R.id.swip);

        wifiManager=(WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
              //  checkconnection();
                //showCustomDialog();
                swipeRefreshLayout.setRefreshing(false);
            }
        },2000);
    }
});


       /* s1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {



                if(isChecked){

                    WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                    wifiManager.setWifiEnabled(true);
                    s1.setText("wifi  on");

                   Toast.makeText(MainActivity.this, "wifi on", Toast.LENGTH_SHORT).show();
                }
                else{wifiManager.setWifiEnabled(false);
                    s1.setText("wifi  off");
                    showDialog();
                  //  Toast.makeText(MainActivity.this, "wifi off", Toast.LENGTH_SHORT).show();
                    }
            }
        });

*/


        /*s1.setOnClickListener(new  View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (s1.isChecked()) {

                    WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                    wifiManager.setWifiEnabled(true);

                }
                else {

                    WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                    wifiManager.setWifiEnabled(false);
                }
            }
        });*/

    }
    void showDialog() {

        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.alert_dialog, null);

        Button acceptButton = view.findViewById(R.id.acceptButton);
        Button cancelButton = view.findViewById(R.id.cancelButton);

        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recreate();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             finish();
              //  Log.e(TAG, "onClick: cancel button" );
            }
        });

        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setView(view)
                .create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;
        alertDialog.show();
    }


    /*BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {


        public void onReceive(Context context, Intent intent) {
            int WifiStatExtra=intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE,WifiManager.WIFI_STATE_UNKNOWN);
            if(WifiStatExtra == WifiManager.WIFI_STATE_ENABLED){

                s1.setChecked(true);
                s1.setText("wifi is on");


            }
            else if(WifiStatExtra==WifiManager.WIFI_STATE_DISABLED){
                s1.setChecked(false);
                s1.setText("Wifi off");

            }

        }



    };
*/
/*    private  BroadcastReceiver wifiStatReciver =new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int WifiStatExtra=intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE,WifiManager.WIFI_STATE_UNKNOWN);
            switch (WifiStatExtra){
                case WifiManager.WIFI_STATE_ENABLED:
                    s1.setChecked(true);
                    s1.setText("wifi on");
                    break;
                case WifiManager.WIFI_STATE_DISABLED:
                    s1.setChecked(false);
                    s1.setText("wifi off");
                    break;
            }

        }
    };*/


    public void onStart() {
        super.onStart();

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);

        //IntentFilter intentFilter=new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION);
      // registerReceiver(wifiStatReciver,intentFilter);
    }
    public void onRestart(){
        super.onRestart();
        //checkconnection();

    }
    @Override
    protected void onStop() {
        super.onStop();
       //unregisterReceiver(wifiStatReciver);
    }
public void register(){ ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().
                getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            if (networkInfo == null || !networkInfo.isConnected() || !networkInfo.isAvailable()) { showDialog();}

            else{ startActivity(new Intent(MainActivity.this,Registre.class));} };
        // View.OnClickListener ls = v -> startActivity(new Intent(MainActivity.this,Registre.class));

        private void updateUI(FirebaseUser currentUser) {


            if(currentUser != null){

                startActivity(new Intent(MainActivity.this,DoctorActivity.class));
            }

    }
    public void Login(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null || !networkInfo.isConnected() || !networkInfo.isAvailable()) { showDialog();}
       else if (email.getText().toString().length() == 0){   email.setError("Email is required!");}
        else if (password.getText().toString().length() == 0) {   password.setError("Password is required!");}

        else {

            mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            // Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    });
        }
    }
 /*if(!isconnected(this)){

        showCustomDialog();
    }

    /*check
    internet coneection
     */
   /* private boolean isconnected(MainActivity mainActivity) {
        ConnectivityManager connectivityManager=(ConnectivityManager) mainActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wificon =connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobilcon =connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if((wificon != null && wificon.isConnected() || mobilcon !=null && mobilcon.isConnected())){
            return true;

        }else{
            return false;
        }

    }*/
   /* private void showCustomDialog() {
        AlertDialog.Builder builder =new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("please connect to the internet to proceed further")
                .setCancelable(false).
                setPositiveButton("connect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
startActivity(new Intent(getApplicationContext(),MainActivity.class));
finish();
            }
        });



    }*/


    public void checkconnection(){
    ConnectivityManager conMgr = (ConnectivityManager) getSystemService (Context.CONNECTIVITY_SERVICE);
    NetworkInfo activNetwork=conMgr.getActiveNetworkInfo();

if(null!=activNetwork){
    if(activNetwork.getType()==ConnectivityManager.TYPE_MOBILE){ Toast.makeText(MainActivity.this, "enable mobile data",
            Toast.LENGTH_SHORT).show();

    }
    if (activNetwork.getType()==ConnectivityManager.TYPE_WIFI){ Toast.makeText(MainActivity.this, "enable wifi ",
            Toast.LENGTH_SHORT).show();
}



}
else {Toast.makeText(MainActivity.this, "no internet connection  ",
        Toast.LENGTH_SHORT).show();}
}}
