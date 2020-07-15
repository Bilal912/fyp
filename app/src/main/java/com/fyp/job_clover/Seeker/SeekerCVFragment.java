package com.fyp.job_clover.Seeker;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fyp.job_clover.R;
import com.google.firebase.database.DatabaseReference;
import com.skydoves.elasticviews.ElasticButton;

public class SeekerCVFragment extends Fragment {
ElasticButton Upload,Create;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_seeker_c_v, container, false);

        Upload = v.findViewById(R.id.upload);
        Create = v.findViewById(R.id.create);

        Create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),SeekerCVMakingActivity.class));
            }
        });

        Upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Still Working",Toast.LENGTH_SHORT).show();
            }
        });


        return v;

    }
}
