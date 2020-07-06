package com.fyp.job_clover.Employer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fyp.job_clover.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmpFindCandFragment extends Fragment {

    public EmpFindCandFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_emp_find_cand, container, false);




        return v;
    }
}
