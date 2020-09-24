package com.fyp.job_clover.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.fyp.job_clover.Data_Classes.AppliedJobs;
import com.fyp.job_clover.Data_Classes.Emp_Post_Data;
import com.fyp.job_clover.Emp_Interface;
import com.fyp.job_clover.Employer.EmpAllPostFragment;
import com.fyp.job_clover.R;
import com.fyp.job_clover.Seeker.DetailJobViewActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.skydoves.elasticviews.ElasticButton;
import com.skydoves.elasticviews.ElasticCardView;
import com.skydoves.elasticviews.ElasticImageView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

public class EmpAllPostAdapter extends RecyclerView.Adapter<EmpAllPostAdapter.MyHolder> {
     private   ArrayList<Emp_Post_Data> postList;
    private Context context;
     private Emp_Interface empInterface;
    private DatabaseReference reference;
    private FirebaseAuth auth;



    public EmpAllPostAdapter(Context context, ArrayList<Emp_Post_Data> postList, Emp_Interface empInterface) {
        this.context = context;
        this.postList = postList;
        this.empInterface = empInterface;

    }

    public void filteredesJob(ArrayList<Emp_Post_Data> filterJob) {
        postList = filterJob;
        notifyDataSetChanged();

    }




    @NonNull
    @Override
    public EmpAllPostAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.all_post_emp,parent,false);
        return new MyHolder(v);

     }

    @Override
    public void onBindViewHolder(@NonNull final EmpAllPostAdapter.MyHolder holder, int position) {

        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("Post_Data");


        Emp_Post_Data epd = postList.get(position);
        holder.jobtitle.setText(epd.job_title);
        holder.com_name.setText(epd.company_name);
        holder.city.setText(epd.company_city);
        final String totalSalary = epd.salary_from + "-" + epd.salary_to;
        holder.salary.setText(totalSalary);

        String time = epd.time;
        holder.postTime.setText(time);

        final String deleteKey = epd.getSpecific_key();

        holder.candidatepost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundl = new Bundle();
                bundl.putString("title",postList.get(holder.getAdapterPosition()).job_title);
                bundl.putString("com_name",postList.get(holder.getAdapterPosition()).company_name);
                bundl.putString("email",postList.get(holder.getAdapterPosition()).company_email);
                bundl.putString("city",postList.get(holder.getAdapterPosition()).company_city);
                bundl.putString("address",postList.get(holder.getAdapterPosition()).company_address);
                bundl.putString("phone",postList.get(holder.getAdapterPosition()).company_phone);
                bundl.putString("education",postList.get(holder.getAdapterPosition()).req_education);
                bundl.putString("position",postList.get(holder.getAdapterPosition()).company_position);
                bundl.putString("salaryfrom",postList.get(holder.getAdapterPosition()).salary_from);
                bundl.putString("salaryto",postList.get(holder.getAdapterPosition()).salary_to);
                bundl.putString("jobtype",postList.get(holder.getAdapterPosition()).job_type);
                bundl.putString("description",postList.get(holder.getAdapterPosition()).description);
                bundl.putString("emp_id",postList.get(holder.getAdapterPosition()).emp_id);
                bundl.putString("p_keys",postList.get(holder.getAdapterPosition()).getSpecific_key());

                empInterface.onNext(bundl);
             }
        });



        holder.detailpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Bundle bundle = new Bundle();
                bundle.putString("title",postList.get(holder.getAdapterPosition()).job_title);
                bundle.putString("com_name",postList.get(holder.getAdapterPosition()).company_name);
                bundle.putString("email",postList.get(holder.getAdapterPosition()).company_email);
                bundle.putString("city",postList.get(holder.getAdapterPosition()).company_city);
                bundle.putString("address",postList.get(holder.getAdapterPosition()).company_address);
                bundle.putString("phone",postList.get(holder.getAdapterPosition()).company_phone);
                bundle.putString("education",postList.get(holder.getAdapterPosition()).req_education);
                bundle.putString("position",postList.get(holder.getAdapterPosition()).company_position);
                bundle.putString("salaryfrom",postList.get(holder.getAdapterPosition()).salary_from);
                bundle.putString("salaryto",postList.get(holder.getAdapterPosition()).salary_to);
                bundle.putString("jobtype",postList.get(holder.getAdapterPosition()).job_type);
                bundle.putString("description",postList.get(holder.getAdapterPosition()).description);
                bundle.putString("emp_id",postList.get(holder.getAdapterPosition()).emp_id);
                bundle.putString("p_key",postList.get(holder.getAdapterPosition()).getSpecific_key());

                empInterface.onNextGo(bundle);

            }
        });

        holder.deletepost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reference.child(auth.getCurrentUser().getUid()).child(deleteKey).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(context, "Post Delete Successfully" + deleteKey, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }

    @Override
    public int getItemCount() {
        return postList.size() ;
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private TextView  jobtitle,com_name,salary,city;
        private ElasticCardView cardView;
        ElasticImageView deletepost;
        private TextView detailpost,candidatepost,postTime;


        public MyHolder(@NonNull View itemView) {
            super(itemView);

            jobtitle = itemView.findViewById(R.id.job_title_view_id);
            com_name = itemView.findViewById(R.id.company_name_view_id);
            city = itemView.findViewById(R.id.city_view_id);
            salary = itemView.findViewById(R.id.salary_view_id);
            cardView = itemView.findViewById(R.id.jobview_card);
            detailpost = itemView.findViewById(R.id.detail);
            postTime = itemView.findViewById(R.id.post_time);
            candidatepost = itemView.findViewById(R.id.candidate);
            deletepost = itemView.findViewById(R.id.deleteimg);

            detailpost.setVisibility(itemView.getVisibility());
            candidatepost.setVisibility(itemView.getVisibility());
            deletepost.setVisibility(itemView.getVisibility());

        }
    }

 }
