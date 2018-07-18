package com.uuzo.gcjs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabWidget;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class XM_JDJH_Activity extends AppCompatActivity {
    Boolean IsDestroy=false;
    Context ThisContext;
    Activity ThisActivity;

    TextView app_title_center,app_title_right2;
    ImageView app_title_left,app_title_right;


    TabWidget _TabWidget;
    ViewPager _ViewPager;
    List<Fragment> FragmentList=new ArrayList<>();
    MyPagerAdapter _MyPagerAdapter;

    int _Index=0;
    int FunType=0;//0为进度计划,1为安全检查

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xm_jdjh);
        Config.SetStatusBar(this);

        IsDestroy=false;
        ThisContext=this;
        ThisActivity=this;

        _Index=getIntent().getIntExtra("Index", 0);
        FunType=getIntent().getIntExtra("FunType", 0);

        app_title_center = (TextView) findViewById(R.id.app_title_center);
        app_title_left = (ImageView) findViewById(R.id.app_title_left);
        app_title_right =(ImageView)  findViewById(R.id.app_title_right);
        app_title_right2 = (TextView)findViewById(R.id.app_title_right2);
        app_title_right.setVisibility(View.GONE);
        app_title_right2.setVisibility(View.GONE);
        app_title_center.setText(GetTypeName());
        app_title_left.setImageResource(R.drawable.back);
        app_title_left.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        FragmentList.clear();
        FragmentList.add(XM_JDJH_Fragment_1.newInstance(FunType));
        FragmentList.add(XM_JDJH_Fragment_2.newInstance(FunType));
        _TabWidget = (TabWidget) findViewById(R.id.widget_0);
        _TabWidget.setVisibility(FragmentList.size()<=1?View.GONE:View.VISIBLE);
        _TabWidget.setStripEnabled(false);
        _TabWidget.setDividerDrawable(null);
        _TabWidget.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int c = (int) (event.getX()/_TabWidget.getWidth()*_TabWidget.getChildCount());
                if (c>=0 && c<_TabWidget.getChildCount())
                {
                    _ViewPager.setCurrentItem(c);
                    v.performClick();
                }
                return true;
            }
        });

        _ViewPager = (ViewPager) findViewById(R.id.widget_1);
        _ViewPager.setOffscreenPageLimit(FragmentList.size());
        _MyPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        _ViewPager.setAdapter(_MyPagerAdapter);
        _ViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }
            @Override
            public void onPageSelected(int arg0) {
                _TabWidget.setCurrentTab(arg0);
                for(int i=0;i<_TabWidget.getChildCount();i++) ((LinearLayout)_TabWidget.getChildAt(i)).getChildAt(1).setVisibility(View.INVISIBLE);
                ((LinearLayout)_TabWidget.getChildAt(arg0)).getChildAt(1).setVisibility(View.VISIBLE);
            }
        });

        _TabWidget.setCurrentTab(_Index);
        _ViewPager.setCurrentItem(_Index);
    }

    String GetTypeName(){
        String ReturnValue="进度计划";
        if (FunType==1) ReturnValue="安全检查";
        return ReturnValue;
    }
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        IsDestroy=true;
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private class MyPagerAdapter extends FragmentStatePagerAdapter
    {

        MyPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {
            return FragmentList.get(position);
        }

        @Override
        public int getCount()
        {
            return FragmentList.size();
        }

    }
}
