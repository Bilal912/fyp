package com.fyp.job_clover.Employer;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.fyp.job_clover.Adapter.AllCandidateAdapter;
import com.fyp.job_clover.Data_Classes.FileUpload;
import com.fyp.job_clover.Emp_Interface;
import com.fyp.job_clover.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ViewCVActivity extends AppCompatActivity implements Emp_Interface {
    private String url,fileName,key;
    DatabaseReference mDatabaseReference;
    List<FileUpload> list;
    private FirebaseAuth auth;
    private RecyclerView recyclerView;
    private AllCandidateAdapter candidateAdapter;
    private Emp_Interface empInterface;
    private FragmentTransaction transaction;
    DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_c_v);

        key = getIntent().getStringExtra("mkey");
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("sh_key",key);
        editor.apply();
        Toast.makeText(getApplicationContext(), key, Toast.LENGTH_SHORT).show();

        auth = FirebaseAuth.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("CV_Data").child(key);
        databaseReference = FirebaseDatabase.getInstance().getReference("Fav_CV_Data");


        list = new ArrayList<>();
        recyclerView = findViewById(R.id.allcandidateRecycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext()
//                ,DividerItemDecoration.VERTICAL));

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    FileUpload upload = snapshot.getValue(FileUpload.class);

                    list.add(upload);
                }



                candidateAdapter = new AllCandidateAdapter(getApplicationContext(),list,  ViewCVActivity.this);
                recyclerView.setAdapter(candidateAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public void onNextGo(Bundle bundle) {

    }

    @Override
    public void onNext(Bundle b) {


        String name = b.getString("name");
        String url = b.getString("url");

        Toast.makeText(this, key + name+ url, Toast.LENGTH_SHORT).show();

        FileUpload upload = new FileUpload(name,url);
        databaseReference.child(key).push().setValue(upload).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(ViewCVActivity.this, "Add CV", Toast.LENGTH_SHORT).show();
            }
        });

//        Fragment fg = new EmpFindCandFragment();
//        Bundle bundle1 = new Bundle();
//        bundle1.putString("ikey",key);
//        fg.setArguments(bundle1);
//        transaction =  getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.viewcvactivity, fg);
//        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//        transaction.commit();

    }
}