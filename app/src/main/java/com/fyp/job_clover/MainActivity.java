package com.fyp.job_clover;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
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

    public static final int MY_PERMISSIONS_REQUEST_WRITE_FIELS = 102;
    private AlertDialog dialog;
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

                    checkAppPermissions();
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

    public void checkAppPermissions() {
        if (!(ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this,
                        android.Manifest.permission.ACCESS_NETWORK_STATE)
                        != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED
                ||ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED||
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.MODIFY_AUDIO_SETTINGS)
                        != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED||
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.READ_PHONE_STATE)
                        != PackageManager.PERMISSION_GRANTED)
        ) {
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            android.Manifest.permission.INTERNET,
                            android.Manifest.permission.ACCESS_NETWORK_STATE,
                            Manifest.permission.CAMERA,
                            Manifest.permission.MODIFY_AUDIO_SETTINGS,
                            Manifest.permission.RECORD_AUDIO,
                            Manifest.permission.READ_PHONE_STATE,
                    },
                    MY_PERMISSIONS_REQUEST_WRITE_FIELS);
        }

    }



    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_WRITE_FIELS) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[4] == PackageManager.PERMISSION_GRANTED
                    && grantResults[5] == PackageManager.PERMISSION_GRANTED
                    && grantResults[6] == PackageManager.PERMISSION_GRANTED
                    && grantResults[7] == PackageManager.PERMISSION_GRANTED
            ) {
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("App required some permission please enable it")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                openPermissionScreen();
                            }
                        })
                        .setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                                dialog.dismiss();
                            }
                        });
                dialog = builder.show();
            }
            return;
        }
    }
    public void openPermissionScreen() {
        // startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.fromParts("package", MainActivity.this.getPackageName(), null));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
