package com.bekaim.highping;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.graphics.PorterDuff;
import android.os.Bundle;

import com.bekaim.highping.fragments.OverPoweredFragment;
import com.bekaim.highping.fragments.PlayFragment;
import com.bekaim.highping.fragments.ResultFragment;
import com.bekaim.highping.utils.SetupViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.roughike.bottombar.BottomBar;

public class MainActivity extends AppCompatActivity {


    ViewPager mViewPager;
    TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        setupViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        SetupViewPagerAdapter adapter = new SetupViewPagerAdapter(getSupportFragmentManager());
        // add Fragments to ViewPager
        adapter.addFragment(new OverPoweredFragment());
        adapter.addFragment(new PlayFragment());
        adapter.addFragment(new ResultFragment());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.over_powered_not_fill);
        tabLayout.getTabAt(1).setIcon(R.drawable.play_icon_not_fill);
        tabLayout.getTabAt(2).setIcon(R.drawable.result_icon_not_fill);
        tabLayout.getTabAt(0).setText("OP");
        tabLayout.getTabAt(1).setText("PLAY");
        tabLayout.getTabAt(2).setText("RESULT");
    }

}
