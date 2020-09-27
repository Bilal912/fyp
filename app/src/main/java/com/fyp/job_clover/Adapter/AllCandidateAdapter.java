package com.fyp.job_clover.Adapter;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fyp.job_clover.Data_Classes.FileUpload;
import com.fyp.job_clover.Emp_Interface;
import com.fyp.job_clover.Employer.ViewCVActivity;
import com.fyp.job_clover.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.skydoves.elasticviews.ElasticImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
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

    @NonNull
    @Override
    public AllCandidateAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.candidate_view_template,parent,false);
        return new  MyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final AllCandidateAdapter.MyHolder holder, final int position) {

        final FileUpload fg = list.get(position);
        holder.cvname.setText(fg.name);

        holder.favcv.setVisibility(View.VISIBLE);
        File storageDir = null;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            storageDir = new File(String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)));
        }
        final File finalStorageDir = storageDir;

        holder.downloading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                downloadFile(context,"CV "+new Date(),".pdf",
                        finalStorageDir.getAbsolutePath(),fg.url);


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
        private ElasticImageView chatimage,videoimage,favcv,downloading;
        private CardView cvopen;
        public MyHolder(@NonNull View itemView) {
            super(itemView);

            downloading = itemView.findViewById(R.id.downlaod);
            title = itemView.findViewById(R.id.titletv);
            cvname = itemView.findViewById(R.id.cvtv);
            chatimage = itemView.findViewById(R.id.chatimageview);
            videoimage = itemView.findViewById(R.id.videoimageview);
            favcv = itemView.findViewById(R.id.favourcvimageview);
            cvopen = itemView.findViewById(R.id.candidateViewTemplate);

        }
    }

    public long downloadFile(Context context, String fileName, String fileExtension, String destinationDirectory, String url) {


        DownloadManager downloadmanager = (DownloadManager) context.
                getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context, destinationDirectory, fileName + fileExtension);

        return downloadmanager.enqueue(request);
    }

}
