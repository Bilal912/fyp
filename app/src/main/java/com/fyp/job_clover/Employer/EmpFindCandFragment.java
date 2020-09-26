package com.fyp.job_clover.Employer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.fyp.job_clover.Adapter.FavCandidate;
import com.fyp.job_clover.Data_Classes.Fav_CV;
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
    TextView textView;
    private FirebaseAuth auth;
    private RecyclerView recyclerView;
    private EditText search_cv;
    private FavCandidate candidateAdapter;
    ShimmerFrameLayout frameLayout;

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

        frameLayout = v.findViewById(R.id.shimmer_id);
        textView = v.findViewById(R.id.texty);

        search_cv = v.findViewById(R.id.etfav_search);
        list = new ArrayList<>();
        recyclerView = v.findViewById(R.id.favcandidateRecycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext()
//                ,DividerItemDecoration.VERTICAL));
        candidateAdapter = new FavCandidate(getContext(),list,EmpFindCandFragment.class);
        recyclerView.setAdapter(candidateAdapter);


        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            FileUpload upload = snapshot1.getValue(FileUpload.class);
                            upload.setKey(snapshot1.getKey());
                            list.add(upload);

                        }
                        textView.setVisibility(View.GONE);
                        frameLayout.stopShimmerAnimation();
                        frameLayout.setVisibility(View.GONE);

                    }

                    candidateAdapter.notifyDataSetChanged();
                }
                else {
                    frameLayout.stopShimmerAnimation();
                    frameLayout.setVisibility(View.GONE);
                    textView.setVisibility(View.VISIBLE);
                    Toast.makeText(getContext(), "Record No Found", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                textView.setVisibility(View.GONE);
                frameLayout.stopShimmerAnimation();
                frameLayout.setVisibility(View.GONE);

            }
        });


        search_cv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {


                filter(s.toString());

            }
        });

        return v;
    }

    private void filter(String txt) {
        ArrayList<FileUpload> filterCV = new ArrayList<>();
        for (FileUpload pd : list){
            if (pd.getName().toLowerCase().contains(txt.toLowerCase())){
                filterCV.add(pd);
            }
        }
        candidateAdapter.filteredesCV(filterCV);

    }


    @Override
    public void onNextGo(Bundle bundle) {

    }

    @Override
    public void onNext(Bundle b) {

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
