package com.fyp.job_clover.Seeker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.fyp.job_clover.Data_Classes.Constants;
import com.fyp.job_clover.Data_Classes.FileUpload;
import com.fyp.job_clover.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewCVActivity extends AppCompatActivity {
    ListView listView;
    private String url,fileName;
    DatabaseReference mDatabaseReference;
    List<FileUpload> uploadList;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_c_v);

        auth = FirebaseAuth.getInstance();

        uploadList = new ArrayList<>();
        listView = (ListView) findViewById(R.id.listView);

        //getting the database reference
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("CV_Data").child(auth.getCurrentUser().getUid());

        //retrieving upload data from firebase database
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FileUpload upload = dataSnapshot.getValue(FileUpload.class);

                uploadList.add(upload);
//                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//
//                }

                String[] uploads = new String[uploadList.size()];

                for (int i = 0; i < uploads.length; i++) {
                    uploads[i] = uploadList.get(i).getName();
                }

                //displaying it to list
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, uploads);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //adding a clicklistener on listview
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //getting the upload
                FileUpload upload = uploadList.get(i);
//
//                //Opening the upload file in browser using the upload url
//                Intent intent = new Intent();
//                intent.setType(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse(upload.url));
//                startActivity(intent);

                Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse(upload.url), "application/*");
                startActivity(Intent.createChooser(intent, "Choose an Application:"));


            }
        });

    }

}