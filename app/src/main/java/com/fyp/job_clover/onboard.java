package com.fyp.job_clover;

import android.os.Bundle;
import com.fyp.job_clover.R;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class onboard extends AppCompatActivity {
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboard);

        viewPager=findViewById(R.id.viewpager);

        screenadapter screenadapter= new screenadapter(getSupportFragmentManager());
        viewPager.setAdapter(screenadapter);


    }
}
