package com.fyp.job_clover;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;


public class screenfrgthree extends Fragment {

    View done;
    ViewPager viewPager;
    View back;
    LinearLayout dot;
    TextView[] dott;
    public screenfrgthree() {
        // Required empty public constructor
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_screenfrgthree, container, false);
        done = view.findViewById(R.id.nextthree);
        viewPager = getActivity().findViewById(R.id.viewpager);
        back = view.findViewById(R.id.backthree);
        dot=view.findViewById(R.id.dot);
        viewPager= getActivity().findViewById(R.id.viewpager);
        adddot();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Login.class));
                getActivity().getFragmentManager().popBackStack();
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getActivity(),"Done",Toast.LENGTH_LONG).show();
                startActivity(new Intent(getActivity(), Login.class));
                getActivity().getFragmentManager().popBackStack();
                getActivity().finish();
            }
        });

        return view;
    }
    public void adddot(){
        dott=new TextView[3];
        for(int i=0; i<dott.length; i++){
            dott[i] = new TextView(getActivity());
            dott[i].setText(Html.fromHtml("&#8226"));
            //dott[i].setTextColor(getResources().getColor(R.color.inactivedoots));
            dott[i].setTextSize(40);
            dot.addView(dott[i]);
            if(i==2){
                //dott[i].setText(Html.fromHtml("&#103777"));
                dott[i].setTextColor(getResources().getColor(R.color.activedotts));
            }
        }
    }

}
