package com.fyp.job_clover.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fyp.job_clover.Data_Classes.FileUpload;
import com.fyp.job_clover.Emp_Interface;
import com.fyp.job_clover.Employer.ViewCVActivity;
import com.fyp.job_clover.R;
import com.skydoves.elasticviews.ElasticImageView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class AllCandidateAdapter extends RecyclerView.Adapter<AllCandidateAdapter.MyHolder> {
    private Context context;
    private ArrayList<FileUpload> list;
    private Emp_Interface empInterface;


    public AllCandidateAdapter(Context context, List<FileUpload> list,  ViewCVActivity empInterface) {
        this.context = context;
        this.list = (ArrayList<FileUpload>) list;
        this.empInterface = (Emp_Interface) empInterface;
    }

//    public AllCandidateAdapter(Context applicationContext, List<FileUpload> list, ViewCVActivity viewCVActivity) {
//    }

    @NonNull
    @Override
    public AllCandidateAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.candidate_view_template,parent,false);
        return new  MyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final AllCandidateAdapter.MyHolder holder, final int position) {

        FileUpload fg = list.get(position);
        holder.cvname.setText(fg.name);

        holder.cvopen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileUpload fg = list.get(position);

                Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse(fg.url), "application/*");
                context.startActivity(Intent.createChooser(intent, "Choose an Application:"));


            }
        });

        holder.favcv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.favcv.setColorFilter(Color.RED);
                 Bundle bundle = new Bundle();
                bundle.putString("fkey",list.get(holder.getAdapterPosition()).getKey());
                bundle.putString("name",list.get(holder.getAdapterPosition()).getName());
                bundle.putString("url",list.get(holder.getAdapterPosition()).getUrl());
                bundle.putString("sek_id",list.get(holder.getAdapterPosition()).getSek_id());

                empInterface.onNext(bundle);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public void filteredesCV(ArrayList<FileUpload> filterCV) {
        list = filterCV;
        notifyDataSetChanged();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private TextView title,cvname;
        private ElasticImageView chatimage,videoimage,favcv;
        private CardView cvopen;
        public MyHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.titletv);
            cvname = itemView.findViewById(R.id.cvtv);
            chatimage = itemView.findViewById(R.id.chatimageview);
            videoimage = itemView.findViewById(R.id.videoimageview);
            favcv = itemView.findViewById(R.id.favourcvimageview);
            cvopen = itemView.findViewById(R.id.candidateViewTemplate);

            favcv.setVisibility(itemView.getVisibility());

        }
    }
}
