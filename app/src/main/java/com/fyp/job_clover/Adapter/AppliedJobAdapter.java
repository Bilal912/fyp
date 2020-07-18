package com.fyp.job_clover.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fyp.job_clover.Data_Classes.AppliedJobs;
import com.fyp.job_clover.Data_Classes.Emp_Post_Data;
import com.fyp.job_clover.Employer.EmpChatActivity;
import com.fyp.job_clover.R;
import com.fyp.job_clover.Seeker.SeekerChatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.skydoves.elasticviews.ElasticCardView;
import com.skydoves.elasticviews.ElasticImageView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AppliedJobAdapter extends RecyclerView.Adapter<AppliedJobAdapter.MyHolder> {
    private Context context;
    private ArrayList<AppliedJobs> list;

    public AppliedJobAdapter(Context context, ArrayList<AppliedJobs> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public AppliedJobAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.applied_job_template,parent,false);
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final AppliedJobAdapter.MyHolder holder, int position) {


        final AppliedJobs aj = list.get(position);
        holder.jobtitle.setText(aj.title);
        holder.com_name.setText(aj.namecity);
        holder.city.setText(aj.position);
        holder.salary.setText(aj.salary);

        final String emp_id = aj.emp_id;
        final String company_name = aj.namecity;

        holder.chatimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SeekerChatActivity.class);
                intent.putExtra("emp_id",emp_id);
                intent.putExtra("company_name",company_name);
                context.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private TextView posttime,jobtitle,com_name,salary,city;
//        private ElasticCardView cardView;
        private ElasticImageView chatimg,videoimg;

        private FirebaseAuth auth;
        private DatabaseReference RootRef;


        public MyHolder(@NonNull final View itemView) {
            super(itemView);

            posttime = itemView.findViewById(R.id.post_timeaply);
            jobtitle = itemView.findViewById(R.id.job_title_view_idaply);
            com_name = itemView.findViewById(R.id.company_name_view_idaply);
            city = itemView.findViewById(R.id.city_view_idaply);
            salary = itemView.findViewById(R.id.salary_view_idaply);
//            cardView = itemView.findViewById(R.id.appliedjobaply);
            chatimg = itemView.findViewById(R.id.chatimageviewaply);
            videoimg = itemView.findViewById(R.id.videoimageviewaply);

            auth = FirebaseAuth.getInstance();
            RootRef = FirebaseDatabase.getInstance().getReference("Messages").child(auth.getCurrentUser().getUid());


            RootRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()){

                        chatimg.setVisibility(itemView.getVisibility());
                        videoimg.setVisibility(itemView.getVisibility());

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
}
