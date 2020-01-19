package com.byandev.trackadmin.Main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.byandev.trackadmin.Helper.ViewPagerAdapter;
import com.byandev.trackadmin.MainFragment.FragmentListJamaah;
import com.byandev.trackadmin.MainFragment.FragmentListPetugas;
import com.byandev.trackadmin.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class ActivityUsersList extends AppCompatActivity {

  private Context context;
  private Toolbar toolbar;
  private TabLayout tabLayout;
  private ViewPager viewPager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_users_list);
    context = this;
    toolbar = findViewById(R.id.toolbar);
    toolbar.setTitle("List Users");
    tabLayout = findViewById(R.id.tabsHome);
    viewPager = findViewById(R.id.frame_container);
    ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
    adapter.addFragment(new FragmentListJamaah(), "Jamaah");
    adapter.addFragment(new FragmentListPetugas(), "Petugas");
    viewPager.setAdapter(adapter);
    tabLayout.setupWithViewPager(viewPager);
  }

  @Override
  public void onResume() {
    super.onResume();
  }

  @Override
  public void onBackPressed(){
    Intent a = new Intent(context, HomeActivity.class);
    startActivity(a);
    finish();
  }

  class ViewPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager manager) {
      super(manager);
    }

    @Override
    public Fragment getItem(int position) {
      return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
      return mFragmentList.size();
    }

    public void addFragment(Fragment fragment, String title) {
      mFragmentList.add(fragment);
      mFragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
      return mFragmentTitleList.get(position);
    }
  }
}
