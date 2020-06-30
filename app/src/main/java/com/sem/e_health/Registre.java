package com.sem.e_health;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import static com.sem.e_health.DoctorActivity.changeStatusBarToWhite;


public class Registre extends AppCompatActivity {
    private FirebaseAuth mAuth;

    ImageView BtRegistre ;
    EditText user ;
    EditText email ;
    EditText password ;
    EditText confirmPassword;
    TextView login ;


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        changeStatusBarToWhite(this);
        login = findViewById(R.id.tc_login);
        mAuth = FirebaseAuth.getInstance();
        BtRegistre = findViewById(R.id.imageView3);
        email = findViewById(R.id.edt_email2);
        password = findViewById(R.id.edt_password2);
        confirmPassword =findViewById(R.id.edt_password_confirm);
        user= findViewById(R.id.edt_username2);
//s1=findViewById(R.id.switch2);


        login.setOnClickListener((v -> finish()));
        findViewById(R.id.img_back_sign_up).setOnClickListener((v -> finish()));

        BtRegistre.setOnClickListener(view -> SignUP());

    }
   /* BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
        @Override
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
    protected void onStart(){

        super.onStart();
    }
    @Override
    protected void onStop() {
        super.onStop();
    }*/

    private void updateUI(FirebaseUser currentUser) {


        if(currentUser != null){


            Intent intent = new Intent(Registre.this,DoctorActivity.class);
            intent.putExtra("user",user.getText().toString());
            startActivity(intent);
        }

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
        // alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;
        alertDialog.show();
    }

    public void SignUP(){


        String Spassword =  password.getText().toString() ;
        String Sconfirmpassword = confirmPassword.getText().toString() ;
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null || !networkInfo.isConnected() || !networkInfo.isAvailable()) { showDialog();}
       else   if ( !Sconfirmpassword.equals( Spassword)){

            confirmPassword.setError("Passwords do not match !");


        }
        else if (user.getText().toString().length() == 0) {   user.setError("UserName is required!");}
        else if (email.getText().toString().length() == 0){   email.setError("Email is required!");}
        else if (password.getText().toString().length() == 0) {   password.setError("Password is required!");}
        else {

            mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);


                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Registre.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    });
        }




    }







}
