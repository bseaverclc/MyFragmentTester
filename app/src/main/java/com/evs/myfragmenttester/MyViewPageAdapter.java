package com.evs.myfragmenttester;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MyViewPageAdapter extends FragmentStateAdapter {


    public MyViewPageAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0: return new HomeFragment();
            case 1: return new ScoreboardFragment();
            case 2: return new GameStatsFragment();
            case 3: return new PointHistoryFragment();

            default: return new PointHistoryFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
