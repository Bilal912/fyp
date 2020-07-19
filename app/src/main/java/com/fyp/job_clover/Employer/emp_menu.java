package com.fyp.job_clover.Employer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.fyp.job_clover.Data_Classes.Employer_Reg_Data;
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

public class emp_menu extends AppCompatActivity {
    private ActionBarDrawerToggle toggle;
    DrawerLayout drawer;
    TextView textView,Topname;
    private FirebaseAuth auth;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_menu);

        auth = FirebaseAuth.getInstance();
         reference = FirebaseDatabase.getInstance().getReference("Employer_Data").child(auth.getCurrentUser().getUid());


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

                Employer_Reg_Data srd = dataSnapshot.getValue(Employer_Reg_Data.class);
                Nav_email.setText(srd.employer_name);
                Name.setText(srd.employer_email);

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

                    case R.id.nav_home:
                        Fragment newFragment = new emp_home();
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.nav_fragment, newFragment);
                        transaction.commit();
                        drawer.closeDrawer(GravityCompat.START);
                        Topname.setText(R.string.app_name);
                        break;

                    case R.id.nav_profile:
                        Fragment newFragment2 = new emp_edit_profile();
                        FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
                        transaction2.replace(R.id.nav_fragment, newFragment2);
                        transaction2.commit();
                        drawer.closeDrawer(GravityCompat.START);
                        Topname.setText("Edit Profile");
                        break;

                    case R.id.nav_post:
                        Fragment newFragment3 = new Emp_post_job();
                        FragmentTransaction transaction3 = getSupportFragmentManager().beginTransaction();
                        transaction3.replace(R.id.nav_fragment, newFragment3);
                        transaction3.commit();
                        drawer.closeDrawer(GravityCompat.START);
                        Topname.setText("Post A Job");
                        break;

                    case R.id.nav_candidate:
                        Fragment newFragment4 = new EmpFindCandFragment();
                        FragmentTransaction transaction4 = getSupportFragmentManager().beginTransaction();
                        transaction4.replace(R.id.nav_fragment, newFragment4);
                        transaction4.commit();
                        drawer.closeDrawer(GravityCompat.START);
                        Topname.setText("Candidates Resume");
                        break;

                    case R.id.nav_my_post:
                        Fragment newFragment5 = new EmpAllPostFragment();
                        FragmentTransaction transaction5 = getSupportFragmentManager().beginTransaction();
                        transaction5.replace(R.id.nav_fragment, newFragment5);
                        transaction5.commit();
                        drawer.closeDrawer(GravityCompat.START);
                        Topname.setText("My Posts");
                        break;

                    case R.id.nav_logout:

                        auth.signOut();
                        startActivity(new Intent(emp_menu.this, Login.class));


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
