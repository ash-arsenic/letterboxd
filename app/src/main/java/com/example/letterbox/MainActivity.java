package com.example.letterbox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new ShowMoviesFragment());
        fragments.add(new ReviewsFragment());
        ArrayList<String> tabNames = new ArrayList<>();
        tabNames.add("FILMS");
        tabNames.add("REVIEWS");

        viewPager.setAdapter(new MovieListAdapter(getSupportFragmentManager(), fragments, tabNames));
        tabLayout.setupWithViewPager(viewPager);

    }

    private class MovieListAdapter extends FragmentStatePagerAdapter {

        ArrayList<Fragment> fragments;
        ArrayList<String> tabNames;
        public MovieListAdapter(@NonNull FragmentManager fm, ArrayList<Fragment> fragments, ArrayList<String> tabNames) {
            super(fm);
            this.fragments = fragments;
            this.tabNames = tabNames;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return tabNames.get(position);
        }
    }
}