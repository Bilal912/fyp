package com.fyp.job_clover.Employer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import cn.pedant.SweetAlert.SweetAlertDialog;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.fyp.job_clover.R;
import com.skydoves.elasticviews.ElasticCardView;

import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;
import static com.fyp.job_clover.Seeker.seeker_login.MY_PREFS_NAME;

public class emp_home extends Fragment {
    private SharedPreferences emp_pref;
    private String empName,empEmail,empCity,empAddress,emp_id;
    private SliderLayout mDemoSlider;
    ElasticCardView View,Apply,CV;
    String cv_path;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_emp_home, container, false);

        mDemoSlider = view.findViewById(R.id.slider);
        Slider_view();

        SharedPreferences editors = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        cv_path = editors.getString("cv_path",null);
//        Toast.makeText(getActivity(), cv_path, Toast.LENGTH_SHORT).show();

        View = view.findViewById(R.id.view_job);
        Apply = view.findViewById(R.id.apply_job);
        CV = view.findViewById(R.id.cv);

        View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Fragment newFragment3 = new Emp_post_job();
                FragmentTransaction transaction3 = getActivity().getSupportFragmentManager().beginTransaction();
                transaction3.replace(R.id.nav_fragment, newFragment3);
                transaction3.commit();

            }
        });
        Apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Fragment newFragment3 = new EmpAllPostFragment();
                FragmentTransaction transaction3 = getActivity().getSupportFragmentManager().beginTransaction();
                transaction3.replace(R.id.nav_fragment, newFragment3);
                transaction3.commit();


            }
        });
        CV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {

                Fragment newFragment3 = new EmpFindCandFragment();
                FragmentTransaction transaction3 = getActivity().getSupportFragmentManager().beginTransaction();
                transaction3.replace(R.id.nav_fragment, newFragment3);
                transaction3.commit();

            }
        });

        return view;
    }

    public void Slider_view(){

        //from image link
//        HashMap<String,String> url_maps = new HashMap<String, String>();
//        url_maps.put("Protection", "https://image.freepik.com/free-vector/family-visiting-doctor-health-center_1262-19776.jpg");
//        url_maps.put("Health and care", "https://image.freepik.com/free-vector/syringe-medicine-pastel-color_67590-517.jpg");
//        url_maps.put("Care", "https://image.freepik.com/free-vector/caduceus-medical-symbol-abstract-geometric-with-medicine-science-concept-background_41814-395.jpg");
//        url_maps.put("Happy Life", "https://image.freepik.com/free-photo/close-up-doctor-front-bright-background_53419-4416.jpg");

//        from drawable
        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("Job Clover",R.drawable.emp_banner1);
        file_maps.put("Post Job",R.drawable.emp_banner2);
        file_maps.put("Check CV",R.drawable.emp_banner3);
        file_maps.put("View Cadidates",R.drawable.emp_banner2);

        for(String name : file_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(getContext());
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit);
            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);
            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(5000);
    }
}
