package com.fyp.job_clover.Employer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.fyp.job_clover.R;
import com.fyp.job_clover.Seeker.seeker_register;
import com.ybs.countrypicker.CountryPicker;
import com.ybs.countrypicker.CountryPickerListener;

import java.util.ArrayList;

public class post_job extends Fragment {
    private LinearLayout countrypicker;
    private TextView phoneCode;
    private ImageView flg;
    private Spinner educat,no_of_positions,country,city,salary_wise;
    private ArrayList<String> education;
    private ArrayList<String> noofpostion;
    private ArrayList<String> salarylist;
    private ArrayList<String> countrylist;
    private EditText phone;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post_job, container, false);

        education = new ArrayList<String>();
        noofpostion = new ArrayList<>();
        salarylist = new ArrayList<>();
        countrylist = new ArrayList<>();
        educat = view.findViewById(R.id.spinner_career_id);
        country = view.findViewById(R.id.spinner_country_id);
        salary_wise = view.findViewById(R.id.spinner_salary_id);
        no_of_positions = view.findViewById(R.id.spinner_position_id);
        countrypicker=view.findViewById(R.id.post_countrypicker);
        phoneCode=view.findViewById(R.id.emp_post_phone_code);
        flg=view.findViewById(R.id.emp_post_flag);
        phone=view.findViewById(R.id.emp_post_phone);

        education.add("Require Education");
        education.add("Matric");
        education.add("Inter");
        education.add("Graduation");
        education.add("Masters");
        education.add("Other");

        noofpostion.add("select No of positions");
        noofpostion.add("1 hire");
        noofpostion.add("2-4 hires");
        noofpostion.add("5-10 hires");
        noofpostion.add("10+ hires");

        salarylist.add("per hour");
        salarylist.add("per day");
        salarylist.add("per month");
        salarylist.add("per year");

        educat.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, education));
        no_of_positions.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line,  noofpostion));
        salary_wise.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line,  salarylist));



        countrypicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CountryPicker picker = CountryPicker.newInstance("Select Country");  // dialog title
                picker.setListener(new CountryPickerListener() {
                    @Override
                    public void onSelectCountry(String name, String code, String dialCode, int flagDrawableResID) {
                        phoneCode.setText(dialCode);
                        flg.setImageResource(flagDrawableResID);
                        picker.dismiss();
                    }
                });
                picker.show(getFragmentManager(), "COUNTRY_PICKER");
            }
        });


        final CountryPicker picker = CountryPicker.newInstance("Select Country");  // dialog title
        picker.setListener(new CountryPickerListener() {
            @Override
            public void onSelectCountry(String name, String code, String dialCode, int flagDrawableResID) {
                String countryName = name;
                countrylist.add(name);
                picker.dismiss();
            }
        });
        picker.show(getFragmentManager(), "COUNTRY_PICKER");



         country.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line,  countrylist));


        return view;
    }


}
