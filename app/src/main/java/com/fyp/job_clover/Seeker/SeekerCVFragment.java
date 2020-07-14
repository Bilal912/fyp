package com.fyp.job_clover.Seeker;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fyp.job_clover.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

/**
 * A simple {@link Fragment} subclass.
 */
public class SeekerCVFragment extends Fragment {


    public SeekerCVFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_seeker_c_v, container, false);


        return v;

    }
}
