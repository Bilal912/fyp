package com.fyp.job_clover.Employer;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fyp.job_clover.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.skydoves.elasticviews.ElasticButton;
import com.ybs.countrypicker.CountryPicker;
import com.ybs.countrypicker.CountryPickerListener;

import java.util.ArrayList;

public class Emp_post_job extends Fragment {
    private LinearLayout countrypicker,country_name_picker;
    private TextView phoneCode,country;
    private ImageView flg;
    private Spinner educat,no_of_positions;
    private TextInputLayout title_lay,name_lay,vity_lay,address_lay,salaryFrom_lay,salaryTo_lay,email_lay;
    private TextInputEditText job_title,company_name,address,salary_from,salary_to,email,com_city;
    private String title,name,city,c_address,salaryFrom,salaryTo,c_email,phone,p_code,description,edu,no_position;
    private ElasticButton post_continue_btn;
    private ArrayList<String> education;
    private ArrayList<String> noofpostion;
     private ArrayList<String> countrylist;
    private EditText company_phone,job_description;
    private RadioGroup radioGroup;
    private String  jobtype = null;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view = inflater.inflate(R.layout.fragment_post_job_emp, container, false);

        Initialize();

        setSpiners();

        countrypickerValue();

        radiobtnValue();



        post_continue_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getValuesFromEditText();


            }
        });






        return view;
    }

    private void getValuesFromEditText()
    {


        if (TextUtils.isEmpty(job_title.getText().toString())){
            Toast.makeText(getContext(), "Job Title is required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(company_name.getText().toString())) {
            Toast.makeText(getContext(), "Company Name is required", Toast.LENGTH_SHORT).show();

        }
        else if (TextUtils.isEmpty(email.getText().toString())){
            Toast.makeText(getContext(), "Password is required", Toast.LENGTH_SHORT).show();
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()){
            email.setError("Email is not Valid");
            email.requestFocus();
        }
        else if (TextUtils.isEmpty(com_city.getText().toString())){
            Toast.makeText(getContext(), "City is required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(address.getText().toString())){
            Toast.makeText(getContext(), "Address is required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(phoneCode.getText().toString())){
            Toast.makeText(getContext(), "phone code is required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(company_phone.getText().toString())){
            Toast.makeText(getContext(), " phone is required", Toast.LENGTH_SHORT).show();
        }
        else if (edu == null){
            Toast.makeText(getContext(), "Education is required", Toast.LENGTH_SHORT).show();
            educat.requestFocus();
        }
        else if (no_position == null){
            Toast.makeText(getContext(), "No of Position is required", Toast.LENGTH_SHORT).show();
            no_of_positions.requestFocus();
        }

        else if (TextUtils.isEmpty(salary_from.getText().toString())){
            Toast.makeText(getContext(), "Salary from is  required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(salary_to.getText().toString())){
            Toast.makeText(getContext(), "Salary to is required", Toast.LENGTH_SHORT).show();
        }
        else if (jobtype == null){
            Toast.makeText(getContext(), "Job type is required", Toast.LENGTH_SHORT).show();
            radioGroup.requestFocus();
        }
        else if (TextUtils.isEmpty(job_description.getText().toString())){
            Toast.makeText(getContext(), "Job Description is required", Toast.LENGTH_SHORT).show();
        }
        else {
            title = job_title.getText().toString();
            name = company_name.getText().toString();
            c_email = email.getText().toString();
            city = com_city.getText().toString();
            c_address = address.getText().toString();
            p_code = phoneCode.getText().toString();
            phone = company_phone.getText().toString();
            salaryFrom = salary_from.getText().toString();
            salaryTo = salary_to.getText().toString();
            description = job_description.getText().toString();

            sendValueforReview();
        }

    }

    private void sendValueforReview()
    {

        Intent intent = new Intent(getContext(),EmpViewPostActivity.class);
        intent.putExtra("jobtitle",title);
        intent.putExtra("comname",name);
        intent.putExtra("comemail",c_email);
        intent.putExtra("comcity",city);
        intent.putExtra("comaddress",c_address);
        intent.putExtra("compcode",p_code);
        intent.putExtra("comphone",phone);
        intent.putExtra("comedu",edu);
        intent.putExtra("comhire",no_position);
        intent.putExtra("comsalfr",salaryFrom);
        intent.putExtra("comsalto",salaryTo);
        intent.putExtra("comjobtyp",jobtype);
        intent.putExtra("comjobdes",description);
        startActivity(intent);

    }

    private void radiobtnValue()
    {

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.fulltime:
                        jobtype="Full time";
                        break;
                    case  R.id.parttime:
                        jobtype="Part time";
                        break;
                    case R.id.internship:
                        jobtype="Internship";
                        break;
                }
            }
        });


    }

    private void countrypickerValue()
    {

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


    }

    private void setSpiners()
    {

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


        educat.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, education));
        no_of_positions.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line,  noofpostion));

        educat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position){
                    case 0:
                        edu = null ;
                         break;
                    case 1:
                        edu = "Matric";
                         break;
                    case 2:
                        edu = "Inter" ;
                         break;
                    case 3:
                        edu = "Graduation" ;
                        break;
                    case 4:
                        edu = "Masters" ;
                        break;
                    case 5:
                        edu = "Other" ;
                        break;


                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        no_of_positions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position){
                    case 0:
                        no_position = null ;
                        break;
                    case 1:
                        no_position = "1 hire";
                        break;
                    case 2:
                        no_position = "2-4 hires" ;
                        break;
                    case 3:
                        no_position = "5-10 hires" ;
                        break;
                    case 4:
                        no_position = "10+ hires" ;
                        break;

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void Initialize()
    {

        education = new ArrayList<String>();
        noofpostion = new ArrayList<>();
         countrylist = new ArrayList<>();
        educat = view.findViewById(R.id.spinner_career_id);
         no_of_positions = view.findViewById(R.id.spinner_position_id);
        countrypicker=view.findViewById(R.id.post_countrypicker);
        phoneCode=view.findViewById(R.id.emp_post_phone_code);
        flg=view.findViewById(R.id.emp_post_flag);
        company_phone=view.findViewById(R.id.emp_post_phone);
        radioGroup=view.findViewById(R.id.job_type_radio);
        job_description = view.findViewById(R.id.post_Describion);
        job_title = view.findViewById(R.id.job_title_id);
        company_name = view.findViewById(R.id.company_name_id);
        address = view.findViewById(R.id.company_address_txt);
        com_city = view.findViewById(R.id.company_locat_txt);
        salary_from = view.findViewById(R.id.post_salaryFrom);
        salary_to = view.findViewById(R.id.post_salaryto);
        job_description = view.findViewById(R.id.post_Describion);
        post_continue_btn = view.findViewById(R.id.post_continue_btn);
        email = view.findViewById(R.id.company_email_id);




    }


}
