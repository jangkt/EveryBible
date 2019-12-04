package com.every.evebible;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.every.evebible.SelectFargment.BiFrag;
import com.every.evebible.SelectFargment.EndFragment;
import com.every.evebible.SelectFargment.FirFragment;
import com.every.evebible.SelectFargment.chapterFrag;
import com.google.android.material.tabs.TabLayout;

public class ChanActivity extends AppCompatActivity {
    public static ViewPager viewPager;
    TabLayout tabs;
    static FragAdapter adapter;
    public static String TESTMENT;
    public static String BIBLE;
    public static int CHAPTER;
    public static int VERSE;
    public static String RE_SELECT_CHAPTER = "RE_SELECT_CHAPTER";
    public static String RE_SELECT_BIBLE = "RE_SELECT_BIBLE";
    public static String RE_SELECT_VERSE = "RE_SELECT_VERSE";
    public static String SELECT ="SELECT" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabs = (TabLayout) findViewById(R.id.tabs);

        Intent intent = getIntent();

        if (intent.getAction()==SELECT){
            tabs.addTab(tabs.newTab().setText("신/구약"));
            tabs.addTab(tabs.newTab().setText("성경"));
            tabs.addTab(tabs.newTab().setText("장"));
            tabs.addTab(tabs.newTab().setText("절"));
            adapter = new FragAdapter(getSupportFragmentManager(), tabs.getTabCount());
            viewPager.setAdapter(adapter);
            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
            tabs.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
            viewPager.setCurrentItem(0);
        }else if (intent.getAction()==RE_SELECT_CHAPTER){
            tabs.addTab(tabs.newTab().setText("신/구약"));
            tabs.addTab(tabs.newTab().setText("성경"));
            tabs.addTab(tabs.newTab().setText("장"));
            tabs.addTab(tabs.newTab().setText("절"));
            adapter = new FragAdapter(getSupportFragmentManager(), tabs.getTabCount());
            viewPager.setAdapter(adapter);
            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
            tabs.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
            TESTMENT = intent.getExtras().getString("testament");
            BIBLE = intent.getExtras().getString("bible");
            viewPager.setCurrentItem(2);
        }else if (intent.getAction()==RE_SELECT_BIBLE){
            tabs.addTab(tabs.newTab().setText("신/구약"));
            tabs.addTab(tabs.newTab().setText("성경"));
            tabs.addTab(tabs.newTab().setText("장"));
            tabs.addTab(tabs.newTab().setText("절"));
            adapter = new FragAdapter(getSupportFragmentManager(), tabs.getTabCount());
            viewPager.setAdapter(adapter);
            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
            tabs.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
            viewPager.setCurrentItem(0);
        }else {
            tabs.addTab(tabs.newTab().setText("신/구약"));
            tabs.addTab(tabs.newTab().setText("성경"));
            tabs.addTab(tabs.newTab().setText("장"));
            tabs.addTab(tabs.newTab().setText("절"));
            adapter = new FragAdapter(getSupportFragmentManager(), tabs.getTabCount());
            viewPager.setAdapter(adapter);
            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
            tabs.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
            TESTMENT = intent.getExtras().getString("testament");
            BIBLE = intent.getExtras().getString("bible");
            VERSE = intent.getExtras().getInt("verse");
            viewPager.setCurrentItem(3);
        }
    }

    public static String getTESTMENT() {
        return TESTMENT;
    }

    public static String getBIBLE() {
        return BIBLE;
    }

    public static int getCHAPTER() {
        return CHAPTER;
    }

    public static int getVERSE() {
        return VERSE;
    }


    public static void setTESTMENT(String TESTMENT) {
        ChanActivity.TESTMENT = TESTMENT;
    }

    public static void setBIBLE(String BIBLE) {
        ChanActivity.BIBLE = BIBLE;
    }

    public static void setCHAPTER(int CHAPTER) {
        ChanActivity.CHAPTER = CHAPTER;
    }

    public static void setVERSE(int VERSE) {
        ChanActivity.VERSE = VERSE;
    }

    public static void refresh(){
        adapter.notifyDataSetChanged();
    }
}

class FragAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public FragAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                FirFragment FirFragment = new FirFragment();
                return FirFragment;
            case 1:
                BiFrag biFrag = new BiFrag();
                return biFrag;
            case 2:
                chapterFrag chapterFrag = new chapterFrag();
                return chapterFrag;
            case 3:
                EndFragment EndFragment = new EndFragment();
                return EndFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}

