package com.fyp.job_clover.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.fyp.job_clover.Data_Classes.Emp_Post_Data;
import com.fyp.job_clover.Emp_Interface;
import com.fyp.job_clover.R;
import com.fyp.job_clover.Seeker.View_Post_Fragment;
import com.skydoves.elasticviews.ElasticButton;
import com.skydoves.elasticviews.ElasticCardView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

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
        return new  MyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final SeekkerViewPostAdapter.MyHolder holder, int position) {

     //   Collections.reverse(postList);
        Emp_Post_Data epd = postList.get(position);
        holder.jobtitle.setText(epd.job_title);
        holder.com_name.setText("Company : ".concat(epd.company_name));
        holder.city.setText(epd.company_city);
        final String totalSalary = "From: ".concat(epd.salary_from) + " - " + "To: ".concat(epd.salary_to);
        holder.salary.setText(totalSalary);

        String time = epd.time;

        SimpleDateFormat timeStampFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date myDate = new Date();
        String filename = timeStampFormat.format(myDate);
        try {
            Date date2 = timeStampFormat.parse(filename);
            Date date1 = timeStampFormat.parse(time);

            long diff = date2.getTime() - date1.getTime();

            long diffSeconds = diff / 1000 ;
            long diffMinutes = diff / (60 * 1000) ;
            long diffHours = diff / (60 * 60 * 1000);
            long diffDays = diff / (24 * 60 * 60 * 1000);

            if (diffSeconds > 60){
                if (diffMinutes > 60){
                    if (diffHours > 24){
                        holder.posttime.setText(diffDays+" day ago");
                    }
                    else {
                        holder.posttime.setText(diffHours+" hour ago");
                    }
                }
                else {
                    holder.posttime.setText(diffMinutes+" minutes ago");
                }
            }
            else {
                holder.posttime.setText(diffSeconds+" seconds ago");
            }

        } catch (ParseException e) {
            //Toast.makeText(context, "Error "+e, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

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
