package com.bekaim.highping;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bekaim.highping.activities.UserProfileActivity;
import com.bekaim.highping.fragments.OverPoweredFragment;
import com.bekaim.highping.fragments.PlayFragment;
import com.bekaim.highping.fragments.ResultFragment;
import com.bekaim.highping.utils.SetupViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {


    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private ImageView ivProfile;
    private ImageView invite;
    private Context mContext = MainActivity.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        ivProfile = findViewById(R.id.profile);
        invite = findViewById(R.id.ic_share);

        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, UserProfileActivity.class);
                startActivity(intent);
            }
        });

        invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Hey, \n\nHighPing is a gaming tournament app. We can play with our friends\n"
                        + "and earn money using this app. \nYou can download this app from playstore.\n"
                        + "Get it free at \nhttps://play.google.com/store/apps/";
                String shareSub = "HighPing";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share using"));
            }
        });

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
