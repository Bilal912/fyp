package com.fyp.job_clover.Seeker;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.fyp.job_clover.Data_Classes.Seeker_Reg_Data;
import com.fyp.job_clover.Login;
import com.fyp.job_clover.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.fyp.job_clover.Seeker.seeker_login.MY_PREFS_NAME;

public class Seeker_Menu extends AppCompatActivity {
    private ActionBarDrawerToggle toggle;
    DrawerLayout drawer;
    TextView textView,Topname;
    private DatabaseReference reference,databaseReference;
    private FirebaseAuth auth;

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seeker__menu);

        auth = FirebaseAuth.getInstance();

        //Topname=findViewById(R.id.top_name);
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        textView=findViewById(R.id.menu_btn);
        drawer = findViewById(R.id.drawer_layout);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(GravityCompat.START);
            }
        });

        NavigationView navigationView= findViewById(R.id.nav_view);
        View view = navigationView.getHeaderView(0);

        final TextView Name= view.findViewById(R.id.nav_name);
        final TextView Nav_email= view.findViewById(R.id.nav_email);
        SharedPreferences editors = getSharedPreferences(seeker_login.MY_PREFS_NAME, MODE_PRIVATE);
        String seek_name = editors.getString("seeker_name",null);
        String seek_email = editors.getString("seeker_email",null);

        Nav_email.setText(seek_email);
        Name.setText(seek_name);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();
                switch(id)
                {

                    case R.id.nav_home_show:
                        Fragment newFragment = new SeekerHomeFragment();
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.nav_fragment, newFragment);
                        transaction.commit();
                        drawer.closeDrawer(GravityCompat.START);
                        //Topname.setText("Home");
                        break;

                    case R.id.nav_apply_job_show:
                        Fragment newFragment5 = new AppliedJobsFragment();
                        FragmentTransaction transaction5 = getSupportFragmentManager().beginTransaction();
                        transaction5.replace(R.id.nav_fragment, newFragment5);
                        transaction5.commit();
                        drawer.closeDrawer(GravityCompat.START);
                        //Topname.setText("Your Applied Jobs");
                        break;

                    case R.id.nav_profile_show:
                        Fragment newFragment2 = new SeekerProfileFragment();
                        FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
                        transaction2.replace(R.id.nav_fragment, newFragment2);
                        transaction2.commit();
                        drawer.closeDrawer(GravityCompat.START);
                        //Topname.setText("Edit Profile");
                        break;

                    case R.id.nav_post_show:
                        Fragment newFragment3 = new View_Post_Fragment();
                        FragmentTransaction transaction3 = getSupportFragmentManager().beginTransaction();
                        transaction3.replace(R.id.nav_fragment, newFragment3);
                        transaction3.commit();
                        drawer.closeDrawer(GravityCompat.START);
                        //Topname.setText("Jobs");
                        break;
                    case R.id.nav_cv_show:
//                        Fragment newFragment4 = new SeekerCVFragment();
//                        FragmentTransaction transaction4 = getSupportFragmentManager().beginTransaction();
//                        transaction4.replace(R.id.nav_fragment, newFragment4);
//                        transaction4.commit();
//                        drawer.closeDrawer(GravityCompat.START);
//                        Topname.setText("Your CV");
                        startActivity(new Intent(Seeker_Menu.this,SeekerCVMakingActivity.class));
                        break;

                    case R.id.nav_logout_show:

                        drawer.closeDrawer(GravityCompat.START);
                        final SweetAlertDialog dialog = new SweetAlertDialog(Seeker_Menu.this,SweetAlertDialog.WARNING_TYPE);
                        dialog.setTitleText("Are you sure?");
                        dialog.setContentText("you want to logout");
                        dialog.setConfirmText("Yes");
                        dialog.setCancelText("No");
                        dialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                        dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {

                                auth.signOut();
                                SharedPreferences preferences = getSharedPreferences(MY_PREFS_NAME,MODE_PRIVATE);
                                preferences.edit().clear().commit();
                                startActivity(new Intent(Seeker_Menu.this, Login.class));
                                finish();
                            }
                        });
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

        //String jaffery = Topname.getText().toString();

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
        //else if(jaffery.equals("Home")){
            new AlertDialog.Builder(this)
                    .setIcon(R.drawable.ic_baseline_error_24)
                    .setTitle("Exit")
                    .setMessage("Are you sure you want to exit?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }

                    })
                    .setNegativeButton("No", null)
                    .show();
        }
//        else {
//            Fragment newFragment = new SeekerHomeFragment();
//            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//            transaction.replace(R.id.nav_fragment, newFragment);
//            transaction.commit();
//            drawer.closeDrawer(GravityCompat.START);
//            Topname.setText("Home");
//        }

    }
}
