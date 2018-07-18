package com.uuzo.gcjs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabWidget;
import android.widget.TextView;

import com.tencent.smtt.sdk.QbSdk;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Boolean IsDestroy=false;
    Context ThisContext;
    Activity ThisActivity;

    TextView app_title_center,app_title_right2;
    ImageView app_title_left,app_title_right;


    TabWidget _TabWidget;
    ViewPager _ViewPager;
    List<Fragment> FragmentList=new ArrayList<>();
    MyPagerAdapter _MyPagerAdapter;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Config.SetStatusBar(this);

        IsDestroy=false;
        ThisContext=this;
        ThisActivity=this;

        Common.context=this;
        Common.MainActivityContext=this;
        Common.SoftName=getString(R.string.app_name);

        UserInfo.Init(ThisContext);

        if (!UserInfo.IsLogin()){
            startActivity(new Intent(ThisContext, LoginActivity.class));
            finish();
            return;
        }

        QbSdk.initX5Environment(getApplicationContext(),new QbSdk.PreInitCallback(){
            @Override
            public void onViewInitFinished(boolean arg0) {
                Log.d("MainActivity", "tbs_x5_onViewInitFinished is " +arg0);
            }
            @Override
            public void onCoreInitFinished() {
                Log.d("MainActivity", "tbs_x5_onCoreInitFinished");
            }
        });

        app_title_center = (TextView) findViewById(R.id.app_title_center);
        app_title_left =(ImageView) findViewById(R.id.app_title_left);
        app_title_right =(ImageView) findViewById(R.id.app_title_right);
        app_title_right2 =(TextView) findViewById(R.id.app_title_right2);
        app_title_right.setVisibility(View.GONE);
        app_title_right2.setVisibility(View.GONE);
        app_title_center.setText(getText(R.string.app_name));
        app_title_left.setImageResource(R.drawable.list);
        app_title_left.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ThisContext, SwitchJob_Activity.class));
            }
        });

        FragmentList.clear();
        FragmentList.add(new Main_GZT_Fragment());
        FragmentList.add(new Main_XM_Fragment());
        FragmentList.add(new Main_Msg_Fragment());
        FragmentList.add(new Main_WD_Fragment());
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
                ((ImageView)((LinearLayout)_TabWidget.getChildAt(0)).getChildAt(0)).setImageResource(arg0==0?R.drawable.tab_1_select:R.drawable.tab_1);
                ((TextView)((LinearLayout)_TabWidget.getChildAt(0)).getChildAt(1)).setTextColor(arg0==0?getResources().getColor(R.color.main):Color.parseColor("#999999"));
                ((ImageView)((LinearLayout)_TabWidget.getChildAt(1)).getChildAt(0)).setImageResource(arg0==1?R.drawable.tab_2_select:R.drawable.tab_2);
                ((TextView)((LinearLayout)_TabWidget.getChildAt(1)).getChildAt(1)).setTextColor(arg0==1?getResources().getColor(R.color.main):Color.parseColor("#999999"));
                ((ImageView)((LinearLayout)_TabWidget.getChildAt(2)).getChildAt(0)).setImageResource(arg0==2?R.drawable.tab_3_select:R.drawable.tab_3);
                ((TextView)((LinearLayout)_TabWidget.getChildAt(2)).getChildAt(1)).setTextColor(arg0==2?getResources().getColor(R.color.main):Color.parseColor("#999999"));
                ((ImageView)((LinearLayout)_TabWidget.getChildAt(3)).getChildAt(0)).setImageResource(arg0==3?R.drawable.tab_4_select:R.drawable.tab_4);
                ((TextView)((LinearLayout)_TabWidget.getChildAt(3)).getChildAt(1)).setTextColor(arg0==3?getResources().getColor(R.color.main):Color.parseColor("#999999"));

                _TabWidget.setCurrentTab(arg0);
            }
        });

        _TabWidget.setCurrentTab(0);
        _ViewPager.setCurrentItem(0);
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
