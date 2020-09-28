package com.fyp.job_clover.Employer;

import android.content.Intent;
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
import com.fyp.job_clover.Adapter.EmpAllPostAdapter;
import com.fyp.job_clover.Data_Classes.Emp_Post_Data;
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
public class EmpAllPostFragment extends Fragment implements Emp_Interface {
    private View view;
    private RecyclerView recyclerView;
    private   ArrayList<Emp_Post_Data> post_list;
     EmpAllPostAdapter postAdapter;
    private EditText postSearch;
    private String emp_id;
    private View v;
    ShimmerFrameLayout frameLayout;

    TextView textView;
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


                filter(s.toString());

            }
        });



        return  view;
    }


    private void filter(String txt) {

        ArrayList<Emp_Post_Data> filterJob = new ArrayList<>();
        for (Emp_Post_Data pd : post_list){
            if (pd.getJob_title().toLowerCase().contains(txt.toLowerCase())){
                filterJob.add(pd);
            }
        }
        postAdapter.filteredesJob(filterJob);

    }


    private void dataBase()
    {

        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("Post_Data").child( auth.getCurrentUser().getUid());

        reference.addListenerForSingleValueEvent(listener);

    }

    private void initializeData()
    {

        frameLayout = view.findViewById(R.id.shimmer_id);
        textView = view.findViewById(R.id.texty);

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
        String company_name = bundle.getString("com_name");
        String email = bundle.getString("email");
        String address = bundle.getString("address");
        String phone = bundle.getString("phone");
        String edu = bundle.getString("education");
        String city = bundle.getString("city") ;
        String salaryfrom = bundle.getString("salaryfrom");
        String salaryto =  bundle.getString("salaryto");
        String description = bundle.getString("description");
        String job_position = bundle.getString("position");
        emp_id = bundle.getString("emp_id");
        String key = bundle.getString("p_key");


        Intent intent = new Intent(getContext(), EmpPostDetailActivity.class);
        intent.putExtra("title",title);
        intent.putExtra("jobtype",job_type);
        intent.putExtra("name",company_name);
        intent.putExtra("city",city);
        intent.putExtra("salaryfrom",salaryfrom);
        intent.putExtra("salaryto",salaryto);
        intent.putExtra("description",description);
        intent.putExtra("position",job_position);
        intent.putExtra("email",email);
        intent.putExtra("address",address);
        intent.putExtra("phone",phone);
        intent.putExtra("edu",edu);
        intent.putExtra("emp_id",emp_id);
        intent.putExtra("key",key);
        startActivity(intent);
     }

    @Override
    public void onNext(Bundle b) {
        String title = b.getString("title");
        String job_type = b.getString("jobtype");
        String company_name = b.getString("com_name");
        String email = b.getString("email");
        String address = b.getString("address");
        String phone = b.getString("phone");
        String edu = b.getString("education");
        String city = b.getString("city") ;
        String salaryfrom = b.getString("salaryfrom");
        String salaryto =  b.getString("salaryto");
        String description = b.getString("description");
        String job_position = b.getString("position");
        String emp_id = b.getString("emp_id");
        String keys = b.getString("p_keys");


         Intent intent = new Intent(getContext(), ViewCVActivity.class);
         intent.putExtra("mkey",keys);
         intent.putExtra("emp_id",emp_id);
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
                textView.setVisibility(View.GONE);
                frameLayout.stopShimmerAnimation();
                frameLayout.setVisibility(View.GONE);

                postAdapter.notifyDataSetChanged();
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
            textView.setVisibility(View.GONE);
            frameLayout.stopShimmerAnimation();
            frameLayout.setVisibility(View.GONE);

            Toast.makeText(getContext(), databaseError.toString(), Toast.LENGTH_SHORT).show();
        }
    };
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
