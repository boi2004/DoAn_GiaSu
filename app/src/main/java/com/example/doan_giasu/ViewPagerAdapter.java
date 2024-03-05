package com.example.doan_giasu;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.doan_giasu.Fragment.NewclassFragment;
import com.example.doan_giasu.Fragment.TeacherFragment;
import com.example.doan_giasu.Fragment.ThongbaoFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new NewclassFragment();
            case 1:
                return new TeacherFragment();
            case 2:
                return new ThongbaoFragment();
            default:
                return new NewclassFragment();
        }

    }

    @Override
    public int getCount() {
        return 4;
    }
}
