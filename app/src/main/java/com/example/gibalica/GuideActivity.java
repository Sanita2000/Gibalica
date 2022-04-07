package com.example.gibalica;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class GuideActivity extends AppCompatActivity {
    private SectionsStatePagerAdapter mSectionsStatePagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        mSectionsStatePagerAdapter = new SectionsStatePagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        //mViewPager.setCurrentItem(2);

    }

    private void setupViewPager(ViewPager viewPager){
    SectionsStatePagerAdapter adapter = new SectionsStatePagerAdapter(getSupportFragmentManager());
    adapter.addFragment(new Fragment1(), "F1");
    adapter.addFragment(new Fragment2(), "F2");
    adapter.addFragment(new Fragment3(), "F3");

    viewPager.setAdapter(adapter);
    }

    public void setViewPager(int fragmentNumber){
        mViewPager.setCurrentItem(fragmentNumber);
    }
}
