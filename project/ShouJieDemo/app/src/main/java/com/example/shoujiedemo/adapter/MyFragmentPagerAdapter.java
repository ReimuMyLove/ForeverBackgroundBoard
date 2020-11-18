package com.example.shoujiedemo.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

public class MyFragmentPagerAdapter extends FragmentStateAdapter {

    private List<Fragment> fragmentList;

    public MyFragmentPagerAdapter(@NonNull FragmentActivity fragmentActivity, List<Fragment> fragmetnList) {
        super(fragmentActivity);
        this.fragmentList = fragmetnList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(fragmentList.size() > 0){
            return fragmentList.get(position);
        }
        return null;
    }

    @Override
    public int getItemCount() {
        if(fragmentList.size() >0){
            return fragmentList.size();
        }
        return 0;
    }


}
