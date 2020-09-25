package com.fyp.job_clover.Seeker;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.fyp.job_clover.Adapter.AppliedJobAdapter;
import com.fyp.job_clover.Adapter.EmpAllPostAdapter;
import com.fyp.job_clover.Adapter.FavCandidate;
import com.fyp.job_clover.Data_Classes.AppliedJobs;
import com.fyp.job_clover.Data_Classes.Emp_Post_Data;
import com.fyp.job_clover.Data_Classes.FileUpload;
import com.fyp.job_clover.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class AppliedJobsFragment extends Fragment {
    DatabaseReference mDatabaseReference;
    ArrayList<AppliedJobs> list;
    TextView textView;
    private FirebaseAuth auth;
    private RecyclerView recyclerView;
    private AppliedJobAdapter adapter;
    ShimmerFrameLayout frameLayout;

    public AppliedJobsFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_applied_jobs, container, false);

        auth = FirebaseAuth.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Applied_Jobs");

        final String uid = auth.getCurrentUser().getUid();

        frameLayout = v.findViewById(R.id.shimmer_id);
        textView = v.findViewById(R.id.texty);

        list = new ArrayList<>();
        recyclerView = v.findViewById(R.id.appliedjobRecycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AppliedJobAdapter(getContext(),list);
        recyclerView.setAdapter(adapter);

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    list.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        for (DataSnapshot snapshot1 : snapshot.getChildren()){
                            AppliedJobs epd = snapshot1.getValue(AppliedJobs.class);
                            epd.setDkey(snapshot1.getKey());
                            list.add(epd);
                        }
                    }
                    textView.setVisibility(View.GONE);
                    frameLayout.stopShimmerAnimation();
                    frameLayout.setVisibility(View.GONE);
                    adapter.notifyDataSetChanged();
                }

                else {
                    textView.setVisibility(View.VISIBLE);
                    frameLayout.stopShimmerAnimation();
                    frameLayout.setVisibility(View.GONE);

                    Toast.makeText(getContext(), "Record No Found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return v;
    }

    @Override
    public void onResume() {

        super.onResume();
        frameLayout.startShimmerAnimation();
    }
    @Override
    public void onPause() {

        super.onPause();
        frameLayout.startShimmerAnimation();
    }

}