package com.fyp.job_clover.Seeker;

import android.content.Intent;
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
        String uid = auth.getCurrentUser().getUid();
        reference = FirebaseDatabase.getInstance().getReference("Seeker_Data").child(uid);

        Topname=findViewById(R.id.top_name);
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

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Seeker_Reg_Data srd = dataSnapshot.getValue(Seeker_Reg_Data.class);
                    Nav_email.setText(srd.seeker_name);
                    Name.setText(srd.seeker_email);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

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
                        Topname.setText("Home");
                        break;

                    case R.id.nav_apply_job_show:
                        Fragment newFragment5 = new AppliedJobsFragment();
                        FragmentTransaction transaction5 = getSupportFragmentManager().beginTransaction();
                        transaction5.replace(R.id.nav_fragment, newFragment5);
                        transaction5.commit();
                        drawer.closeDrawer(GravityCompat.START);
                        Topname.setText("Your Applied Jobs");
                        break;

                    case R.id.nav_profile_show:
                        Fragment newFragment2 = new SeekerProfileFragment();
                        FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
                        transaction2.replace(R.id.nav_fragment, newFragment2);
                        transaction2.commit();
                        drawer.closeDrawer(GravityCompat.START);
                        Topname.setText("Edit Profile");
                        break;

                    case R.id.nav_post_show:
                        Fragment newFragment3 = new View_Post_Fragment();
                        FragmentTransaction transaction3 = getSupportFragmentManager().beginTransaction();
                        transaction3.replace(R.id.nav_fragment, newFragment3);
                        transaction3.commit();
                        drawer.closeDrawer(GravityCompat.START);
                        Topname.setText("Jobs");
                        break;
                    case R.id.nav_cv_show:
                        Fragment newFragment4 = new SeekerCVFragment();
                        FragmentTransaction transaction4 = getSupportFragmentManager().beginTransaction();
                        transaction4.replace(R.id.nav_fragment, newFragment4);
                        transaction4.commit();
                        drawer.closeDrawer(GravityCompat.START);
                        Topname.setText("Your CV");
                        break;

                    case R.id.nav_logout_show:

                        auth.signOut();
                        startActivity(new Intent(Seeker_Menu.this, Login.class));
                        finish();


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
