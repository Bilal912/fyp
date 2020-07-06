package com.fyp.job_clover.Seeker;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.fyp.job_clover.Adapter.EmpAllPostAdapter;
import com.fyp.job_clover.Data_Classes.Emp_Post_Data;
import com.fyp.job_clover.Emp_Interface;
import com.fyp.job_clover.Employer.EmpAllPostFragment;
import com.fyp.job_clover.Employer.EmpPostDetailActivity;
import com.fyp.job_clover.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class View_Post_Fragment extends Fragment implements Emp_Interface {
    private RecyclerView recyclerView;
    private ArrayList<Emp_Post_Data> post_list;
    private ArrayList<Emp_Post_Data> pstlist;
    private EmpAllPostAdapter postAdapter;
    private EditText postSearch;
    private String emp_id;
    private View v;
    private String title,name,city,c_address,salaryFrom,salaryTo,c_email,c_phone,p_code,c_description,c_education,c_positions,jobTy,contact;

    private FirebaseAuth auth;
    private DatabaseReference reference;
    private Emp_Interface empInterface;
    private String id;


    public View_Post_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v =  inflater.inflate(R.layout.fragment_view__post_, container, false);

        initializedata();

        dataBaseset();




        return v;
    }

    private void dataBaseset()
    {
        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("Post_Data");
        emp_id = auth.getCurrentUser().getUid();


        reference.addListenerForSingleValueEvent(listener);


    }

    private void initializedata()
    {
        recyclerView = v.findViewById(R.id.seeker_job_view_recyclerview);
        postSearch = v.findViewById(R.id.seeker_job_search);
        post_list = new ArrayList<>();
        pstlist = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext()
//                ,DividerItemDecoration.VERTICAL));
        postAdapter = new EmpAllPostAdapter(getContext(),post_list, View_Post_Fragment.this);
        recyclerView.setAdapter(postAdapter);


    }

    ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            if (dataSnapshot.exists()){
                post_list.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    for (DataSnapshot snapshot1 : snapshot.getChildren()){
                        Emp_Post_Data epd = snapshot1.getValue(Emp_Post_Data.class);
                        epd.setSpecific_key(snapshot1.getKey());
                        post_list.add(epd);
                    }

                }
                postAdapter.notifyDataSetChanged();
            }
            else {
                Toast.makeText(getContext(), "Record No Found", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

            Toast.makeText(getContext(), databaseError.toString(), Toast.LENGTH_SHORT).show();
        }
    };


    @Override
    public void onNextGo(Bundle bundle) {

        String title = bundle.getString("title");
        String job_type = bundle.getString("jobtype");
        String company_name_city = bundle.getString("com_name")+ " - " + bundle.getString("city") ;
        String salary = bundle.getString("salaryfrom")+ " - " + bundle.getString("salaryto");
        String description = bundle.getString("description");
        String job_position = bundle.getString("position");


        Intent intent = new Intent(getContext(), DetailJobViewActivity.class);
        intent.putExtra("title",title);
        intent.putExtra("jobtype",job_type);
        intent.putExtra("name-city",company_name_city);
        intent.putExtra("salary",salary);
        intent.putExtra("description",description);
        intent.putExtra("position",job_position);
        startActivity(intent);

    }
}