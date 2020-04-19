package com.fyp.job_clover.Employer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fyp.job_clover.R;
import com.google.android.material.navigation.NavigationView;

public class emp_menu extends AppCompatActivity {
    private ActionBarDrawerToggle toggle;
    DrawerLayout drawer;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_menu);

        Toolbar toolbar = findViewById(R.id.toolbar_main);
        textView=findViewById(R.id.menu_btn);
        drawer = findViewById(R.id.drawer_layout);

        //        SharedPreferences editors = getSharedPreferences(MY_PREFS_NAME,MODE_PRIVATE);
//        String Email = editors.getString("email","Null");
//        String First = editors.getString("firstname","Null");

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(GravityCompat.START);
            }
        });

        NavigationView navigationView= findViewById(R.id.nav_view);
        View view = navigationView.getHeaderView(0);

        final TextView Name=view.findViewById(R.id.nav_name);
        final TextView Nav_email=view.findViewById(R.id.nav_email);

//        Nav_email.setText(Email);
//        Name.setText(First);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();
                switch(id)
                {

                    case R.id.nav_home:
//                        Fragment newFragment = new Home();
//                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//                        transaction.replace(R.id.nav_fragment, newFragment);
//                        transaction.commit();
//                        drawer.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_logout:

                        break;

                    default:
                        return true;
                }
                return true;
            }
        });

    }
    @Override
    public void onBackPressed(){
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {

        }
    }
}
