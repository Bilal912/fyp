package com.fyp.job_clover.Employer;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.fyp.job_clover.Adapter.EmpAllPostAdapter;
import com.fyp.job_clover.Data_Classes.Emp_Post_Data;
import com.fyp.job_clover.Emp_Interface;
import com.fyp.job_clover.R;
import com.fyp.job_clover.Seeker.DetailJobViewActivity;
import com.google.android.gms.tasks.OnCompleteListener;
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
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.DatabaseMetaData;
import java.util.ArrayList;

import static com.google.common.reflect.Reflection.initialize;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmpAllPostFragment extends Fragment implements Emp_Interface {
    private View view;
    private RecyclerView recyclerView;
    private ArrayList<Emp_Post_Data> post_list;
    private EmpAllPostAdapter postAdapter;
    private EditText postSearch;
    private String emp_id;
    private View v;

    private FirebaseAuth auth;
    private DatabaseReference reference;
    private Emp_Interface empInterface;


    public EmpAllPostFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_emp_all_post, container, false);


        initializeData();

        dataBase();

        postSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {


//                filter(s.toString());

            }
        });



        return  view;
    }

//    private void filter(String txt)
//    {
//
//        ArrayList<Emp_Post_Data> filterStu = new ArrayList<>();
//        for (Emp_Post_Data stud : post_list){
//            if (stud.getJob_title().toLowerCase().contains(txt.toLowerCase())){
//                filterStu.add(stud);
//            }
//        }
//       EmpAllPostAdapter.filteredstu(filterStu);
//
//    }

    private void dataBase()
    {

        auth = FirebaseAuth.getInstance();
        emp_id = auth.getCurrentUser().getUid();
        reference = FirebaseDatabase.getInstance().getReference("Post_Data").child(emp_id);


        reference.addListenerForSingleValueEvent(listener);

    }

    private void initializeData()
    {

        recyclerView = view.findViewById(R.id.emp_all_post_fragment_recycle);
        postSearch = view.findViewById(R.id.etpost_search);
        post_list = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext()
//                ,DividerItemDecoration.VERTICAL));
        postAdapter = new EmpAllPostAdapter(getContext(),post_list, EmpAllPostFragment.this);
        recyclerView.setAdapter(postAdapter);



    }

    @Override
    public void onNextGo(Bundle bundle) {


        String title = bundle.getString("title");
        String job_type = bundle.getString("jobtype");
        String company_name_city = bundle.getString("com_name")+ " - " + bundle.getString("city") ;
        String salary = bundle.getString("salaryfrom")+ " - " + bundle.getString("salaryto");
        String description = bundle.getString("description");
        String job_position = bundle.getString("position");


        Intent intent = new Intent(getContext(), EmpPostDetailActivity.class);
        intent.putExtra("title",title);
        intent.putExtra("jobtype",job_type);
        intent.putExtra("name-city",company_name_city);
        intent.putExtra("salary",salary);
        intent.putExtra("description",description);
        intent.putExtra("position",job_position);
        startActivity(intent);
     }

    ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            if (dataSnapshot.exists()){
                post_list.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Emp_Post_Data epd = snapshot.getValue(Emp_Post_Data.class);
                     epd.setSpecific_key(snapshot.getKey());
                     post_list.add(epd);
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

}
