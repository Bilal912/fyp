package com.fyp.job_clover.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fyp.job_clover.Data_Classes.Emp_Post_Data;
import com.fyp.job_clover.Emp_Interface;
import com.fyp.job_clover.R;
import com.fyp.job_clover.Seeker.View_Post_Fragment;
import com.skydoves.elasticviews.ElasticButton;
import com.skydoves.elasticviews.ElasticCardView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SeekkerViewPostAdapter extends RecyclerView.Adapter<SeekkerViewPostAdapter.MyHolder>  {
    private Context context;
    private ArrayList<Emp_Post_Data> postList;
    private Emp_Interface empInterface;

    public SeekkerViewPostAdapter(Context context, ArrayList<Emp_Post_Data> postList, Emp_Interface empInterface) {
        this.context = context;
        this.postList = postList;
        this.empInterface = empInterface;
    }

    @NonNull
    @Override
    public SeekkerViewPostAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.job_view_template,parent,false);
        return new  MyHolder(v);    }

    @Override
    public void onBindViewHolder(@NonNull final SeekkerViewPostAdapter.MyHolder holder, int position) {

        Emp_Post_Data epd = postList.get(position);
        holder.jobtitle.setText(epd.job_title);
        holder.com_name.setText("Company : ".concat(epd.company_name));
        holder.city.setText(epd.company_city);
        final String totalSalary = "From: ".concat(epd.salary_from) + " - " + "To: ".concat(epd.salary_to);
        holder.salary.setText(totalSalary);

        String time = epd.time;
        holder.posttime.setText(time);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
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

    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        private TextView posttime,jobtitle,com_name,salary,city;
        private ElasticCardView cardView;
        private TextView detailpost,candidatepost;


        public MyHolder(@NonNull View itemView) {
            super(itemView);

            posttime = itemView.findViewById(R.id.post_time);
            jobtitle = itemView.findViewById(R.id.job_title_view_id);
            com_name = itemView.findViewById(R.id.company_name_view_id);
            city = itemView.findViewById(R.id.city_view_id);
            salary = itemView.findViewById(R.id.salary_view_id);
            cardView = itemView.findViewById(R.id.jobview_card);
            detailpost = itemView.findViewById(R.id.detail);
            candidatepost = itemView.findViewById(R.id.candidate);

//            detailpost.setVisibility(itemView.getVisibility());
        }
    }
}
