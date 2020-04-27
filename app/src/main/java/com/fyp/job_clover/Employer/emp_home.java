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

        emp_pref =  getActivity().getSharedPreferences("Emp_Pref", 0);


        emp_id = emp_pref.getString("emp_id",null);
        empName = emp_pref.getString("emp_name",null);
        empEmail = emp_pref.getString("emp_email",null);
        empCity = emp_pref.getString("emp_city",null);
        empAddress = emp_pref.getString("emp_address",null);

        final SweetAlertDialog dialog = new SweetAlertDialog(getActivity(),SweetAlertDialog.SUCCESS_TYPE);
        dialog.setTitleText( emp_id +"\n"+ empName +"\n"+ empEmail +"\n"+ empCity +"\n"+ empAddress);
        dialog.show();
        dialog.setConfirmText("OK");
        dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                dialog.dismiss();
             //   startActivity(new Intent(emp_login.this,emp_menu.class));
            }
        });



        return view;
    }
}
