package klivitam.com.chat.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import klivitam.com.chat.R;
import klivitam.com.chat.frag.ContactsFragment;
import klivitam.com.chat.frag.ContactsHisFragment;

/**
 * Created by klivitam on 2016/12/27.
 * 获取本地的电话联系人
 */

public class ContactActivity extends FragmentActivity {
    private List<Fragment> list;
    private TabLayout tab;
    private ViewPager pager;
    private MyViewPagerAdapter adapter;
    private String[] tabItem = {"通话历史","联系人"};
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        initView();
    }


    private void initView() {
        tab = (TabLayout) findViewById(R.id.contacts_tab);
        pager = (ViewPager) findViewById(R.id.contacts_vp);
        initData();
        adapter = new MyViewPagerAdapter(getSupportFragmentManager(),this,list);
        pager.setAdapter(adapter);
        tab.setupWithViewPager(pager);
        tab.setTabMode(TabLayout.MODE_FIXED);
    }

    private void initData() {
        list = new ArrayList<>();
        list.add(new ContactsHisFragment());
        list.add(new ContactsFragment());
    }

    private class MyViewPagerAdapter extends FragmentPagerAdapter {
        private Context context;
        private List<Fragment> list;


        public MyViewPagerAdapter(FragmentManager fm, Context c,List list) {
            super(fm);
            this.context = c;
            this.list = list;
        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabItem[position];
        }
    }
}
