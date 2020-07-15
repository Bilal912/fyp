package com.fyp.job_clover.Employer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fyp.job_clover.Adapter.FavCandidate;
import com.fyp.job_clover.Data_Classes.FileUpload;
import com.fyp.job_clover.Emp_Interface;
import com.fyp.job_clover.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmpFindCandFragment extends Fragment implements Emp_Interface  {
    private String url,fileName;
    DatabaseReference mDatabaseReference;
    ArrayList<FileUpload> list;
    private FirebaseAuth auth;
    private RecyclerView recyclerView;
    private FavCandidate candidateAdapter;
    private Emp_Interface empInterface;



    public EmpFindCandFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_emp_find_cand, container, false);

//        Bundle bundle = this.getArguments();
//        String key = bundle.getString("ikey");
//        Toast.makeText(getContext(), key, Toast.LENGTH_SHORT).show();


        auth = FirebaseAuth.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Fav_CV_Data");


        list = new ArrayList<>();
        recyclerView = v.findViewById(R.id.favcandidateRecycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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


                candidateAdapter = new FavCandidate(getContext(),list,EmpFindCandFragment.class);
                recyclerView.setAdapter(candidateAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return v;
    }


    @Override
    public void onNextGo(Bundle bundle) {

    }

    @Override
    public void onNext(Bundle b) {

    }
}
