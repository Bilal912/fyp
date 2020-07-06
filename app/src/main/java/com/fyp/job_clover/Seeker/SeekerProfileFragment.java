package com.fyp.job_clover.Seeker;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fyp.job_clover.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SeekerProfileFragment extends Fragment {
    private TextView name,email,phone,qualification,gender,address;

    public SeekerProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_seeker_profile, container, false);

        name = v.findViewById(R.id.seeker_profile_name);
        email = v.findViewById(R.id.seeker_profile_email);
        phone = v.findViewById(R.id.seeker_profile_phone);
        qualification = v.findViewById(R.id.seeker_profile_qualification);
        gender = v.findViewById(R.id.seeker_profile_gender);
        address = v.findViewById(R.id.seeker_profile_address);




        return v;
    }

}
