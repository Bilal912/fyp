package com.fyp.job_clover.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fyp.job_clover.Data_Classes.FileUpload;
import com.fyp.job_clover.Emp_Interface;
import com.fyp.job_clover.Employer.EmpChatActivity;
import com.fyp.job_clover.Employer.EmpFindCandFragment;
import com.fyp.job_clover.R;
import com.skydoves.elasticviews.ElasticImageView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class FavCandidate extends RecyclerView.Adapter<FavCandidate.MyHolder> {
    private Context context;
    private ArrayList<FileUpload> list;
    private Emp_Interface empInterface;


//    public FavCandidate(Context context, ArrayList<FileUpload> list, EmpFindCandFragment empInterface) {
//        this.context = context;
//        this.list = list;
//        this.empInterface = empInterface;
//    }

    public FavCandidate(Context context, ArrayList<FileUpload> list, Class<EmpFindCandFragment> empFindCandFragmentClass) {
        this.context = context;
        this.list = list;

    }

    @NonNull
    @Override
    public FavCandidate.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.candidate_view_template,parent,false);
        return new  MyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final FavCandidate.MyHolder holder, final int position) {

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
        holder.chatimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Intent intent = new Intent(context, EmpChatActivity.class);
               intent.putExtra("recKey",list.get(holder.getAdapterPosition()).getKey());
               context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
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

            chatimage.setVisibility(itemView.getVisibility());
            videoimage.setVisibility(itemView.getVisibility());


        }
    }
}
