package com.fyp.job_clover.Employer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import cn.pedant.SweetAlert.SweetAlertDialog;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fyp.job_clover.R;

import static android.content.Context.MODE_PRIVATE;

public class emp_home extends Fragment {
    private SharedPreferences emp_pref;
    private String empName,empEmail,empCity,empAddress,emp_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_emp_home, container, false);




        return view;
    }
}
