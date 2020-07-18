package com.fyp.job_clover.Employer;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.fyp.job_clover.Adapter.AllCandidateAdapter;
import com.fyp.job_clover.Data_Classes.Fav_CV;
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
    private String url,fileName,key,emp_id;
    DatabaseReference mDatabaseReference;
    List<FileUpload> list;
    private FirebaseAuth auth;
    private RecyclerView recyclerView;
    private AllCandidateAdapter candidateAdapter;
    private Emp_Interface empInterface;
    private FragmentTransaction transaction;
    DatabaseReference databaseReference;



    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_c_v);

//        Bundle bundle = new Bundle();
//
//
//        SharedPreferences pref = getApplicationContext().getSharedPreferences("Emp_Email", MODE_PRIVATE);
//        SharedPreferences.Editor editor = pref.edit();
//        editor.putString("em_email",email);
//        editor.apply();
//        Toast.makeText(getApplicationContext(), email, Toast.LENGTH_SHORT).show();

        auth = FirebaseAuth.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("CV_Data");
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
                    for (DataSnapshot snapshot1 : snapshot.getChildren()){
                        FileUpload upload = snapshot1.getValue(FileUpload.class);
                        upload.setKey(snapshot1.getKey());

                        list.add(upload);
                    }

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



        key = b.getString("fkey",null);
        String name = b.getString("name",null);
        String url = b.getString("url",null);
        String sek_id = b.getString("sek_id",null);

        Toast.makeText(this,   name+ url, Toast.LENGTH_SHORT).show();

        FileUpload upload = new FileUpload(name,url,sek_id);
        databaseReference.child(auth.getCurrentUser().getUid()).child(key).setValue(upload).addOnCompleteListener(new OnCompleteListener<Void>() {
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