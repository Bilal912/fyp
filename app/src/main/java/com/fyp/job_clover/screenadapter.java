package com.fyp.job_clover;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class screenadapter extends FragmentPagerAdapter {
    public screenadapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new screenfrgone();
            case 1:
                return new screenfrgtwo();
            case 2:
                return new screenfrgthree();
            default: return null;
        }

    }

    @Override
    public int getCount() {
        return 3;
    }
}
