package com.capstone.notekeeper.Activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.capstone.notekeeper.Adapter.ProfileViewPagerAdapter;
import com.capstone.notekeeper.Fragments.BlogQueryFragment;
import com.capstone.notekeeper.Fragments.BuyProductListFragment;
import com.capstone.notekeeper.Models.User;
import com.capstone.notekeeper.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserProfileActivity extends AppCompatActivity {

    private static final int CHOOSE_IMAGE = 101;
    TextView mUserName;
    ImageView mUserProfileImage;
    FirebaseAuth mAuth;
    Toolbar mToolbar;
    private ProfileViewPagerAdapter mPagerAdapter;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        mAuth = FirebaseAuth.getInstance();

        mToolbar = findViewById(R.id.user_toolbar);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mUserProfileImage = findViewById(R.id.user_profile_image);
        mUserName = findViewById(R.id.user_name);
        loadUserInformation();
        setUpViewPager();
    }

    private void setUpViewPager() {
        mViewPager = findViewById(R.id.viewpager);
        mTabLayout = findViewById(R.id.tabLayout);
        mPagerAdapter = new ProfileViewPagerAdapter(getSupportFragmentManager(), UserProfileActivity.this);
        mPagerAdapter.addFragment(new BlogQueryFragment(), "ANSWERS");
        mPagerAdapter.addFragment(new BuyProductListFragment(), "QUESTIONS");
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        highLightCurrentTab(0);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                highLightCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void highLightCurrentTab(int position) {
        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            assert tab != null;
            tab.setCustomView(null);
            tab.setCustomView(mPagerAdapter.getTabView(i));
        }
        TabLayout.Tab tab = mTabLayout.getTabAt(position);
        assert tab != null;
        tab.setCustomView(null);
        tab.setCustomView(mPagerAdapter.getSelectedTabView(position));
    }
    @Override
    public void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser()==null){
            finish();
        }
    }

    private void loadUserInformation() {
        final FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            if (user.getPhotoUrl() != null) {
                String photoUrl = user.getPhotoUrl().toString();
                Glide.with(this)
                        .load(photoUrl)
                        .into(mUserProfileImage);
            }
            if (user.getDisplayName() != null) {
                String displayName = user.getDisplayName();
                mUserName.setText(displayName);
            }
        }
    }

}