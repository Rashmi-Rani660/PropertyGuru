package com.example.propertyguru;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.propertyguru.fragments.AllResponsesFragment;
import com.example.propertyguru.fragments.AllRespondentsFragment;

public class ViewResponsePagerAdapter extends FragmentStateAdapter {

    public ViewResponsePagerAdapter(@NonNull FragmentActivity fa) {
        super(fa);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) return new AllResponsesFragment();
        else return new AllRespondentsFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
