package com.fyp.job_clover;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
Thread t;
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();

        t = new Thread() {
            public void run() {
                try {

                    t.sleep(1500);

//                    SharedPreferences editors = getSharedPreferences(MY_PREFS_NAME,MODE_PRIVATE);
//                    String Email = editors.getString("email","Null");
//
//                    if (Email.equals("Null")) {
//                        Intent i = new Intent(MainActivity.this, Login.class);
//                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                        startActivity(i);
//                        finish();
//                    }
//                    else {
                        Intent i = new Intent(MainActivity.this, onboard.class);
                        startActivity(i);
                        finish();
//                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();

}
}
