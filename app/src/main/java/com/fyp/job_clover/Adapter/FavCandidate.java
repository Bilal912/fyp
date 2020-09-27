package com.fyp.job_clover.Adapter;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fyp.job_clover.Data_Classes.FileUpload;
import com.fyp.job_clover.Emp_Interface;
import com.fyp.job_clover.Employer.EmpChatActivity;
import com.fyp.job_clover.Employer.EmpFindCandFragment;
import com.fyp.job_clover.R;
import com.fyp.job_clover.VideoActivity;
import com.fyp.job_clover.VideoChatViewActivity;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.skydoves.elasticviews.ElasticImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.Context.MODE_PRIVATE;

public class FavCandidate extends RecyclerView.Adapter<FavCandidate.MyHolder> {
    private Context context;
    private ArrayList<FileUpload> list;
    private Emp_Interface empInterface;

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

        final FileUpload fg = list.get(position);
        holder.cvname.setText(fg.name);
        final String sek_name = fg.name;
        final String sek_id = fg.sek_id;

        File storageDir = null;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            storageDir = new File(String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)));
        }
        final File finalStorageDir = storageDir;
        holder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                downloadFile(context,new Date()+fg.name+" CV",".pdf", finalStorageDir.getAbsolutePath(),fg.url);

            }
        });
        holder.chatimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Intent intent = new Intent(context, EmpChatActivity.class);
               intent.putExtra("sek_id",sek_id);
               intent.putExtra("sek_name",sek_name);
//               intent.putExtra("recKey",list.get(holder.getAdapterPosition()).getKey());
               context.startActivity(intent);

            }
        });

        holder.videoimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, VideoChatViewActivity.class);
//                intent.putExtra("sek_id",sek_id);
//                intent.putExtra("sek_name",sek_name);
                context.startActivity(intent);

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
        private ElasticImageView chatimage,videoimage,favcv,download;
        private CardView cvopen;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            download = itemView.findViewById(R.id.downlaod);
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

    public long downloadFile(Context context, String fileName, String fileExtension, String destinationDirectory, String url) {


        DownloadManager downloadmanager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context, destinationDirectory, fileName + fileExtension);

        return downloadmanager.enqueue(request);
    }

}
