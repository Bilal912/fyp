package com.fyp.job_clover;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Toast;

import com.fyp.job_clover.Employer.emp_menu;
import com.fyp.job_clover.Seeker.Seeker_Menu;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Locale;

import static com.fyp.job_clover.Seeker.seeker_login.MY_PREFS_NAME;

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
        SharedPreferences editors = getSharedPreferences(MY_PREFS_NAME,MODE_PRIVATE);
        final String type = editors.getString("Type","Null");

        t = new Thread() {
            public void run() {
                try {

                    t.sleep(1000);


                    if (type == null) {
                        Intent i = new Intent(MainActivity.this, onboard.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                        finish();
                    }
                    else if (type.equals("Seeker")){
                        Intent i = new Intent(MainActivity.this, Seeker_Menu.class);
                        startActivity(i);
                        finish();
                    }
                    else if (type.equals("Employer")){
                        Intent i = new Intent(MainActivity.this, emp_menu.class);
                        startActivity(i);
                        finish();
                    }
                    else {
                        Intent i = new Intent(MainActivity.this, onboard.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                        finish();
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
}
}
