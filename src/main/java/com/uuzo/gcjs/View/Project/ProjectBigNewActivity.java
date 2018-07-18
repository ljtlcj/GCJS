package com.uuzo.gcjs.View.Project;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.uuzo.gcjs.Adapter.MainAdapter;
import com.uuzo.gcjs.BaseTemplate.BaseActivity;
import com.uuzo.gcjs.Main.ProjectBigNew.ProjectBigNewOneFragment;
import com.uuzo.gcjs.Main.ProjectBigNew.ProjectBigNewThreeFragment;
import com.uuzo.gcjs.Main.ProjectBigNew.ProjectBigNewTwoFragment;
import com.uuzo.gcjs.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jie on 2018/7/16.
 */

public class ProjectBigNewActivity extends BaseActivity implements ViewPager.OnPageChangeListener{
    private TextView tv_project_summary, tv_project_data, tv_project_participant;
    private ViewPager vp_project_new;

    private MainAdapter adapter;
    private List<Fragment> list;

    @Override
    public int getLayoutId() {
        return R.layout.activity_project_big_new;
    }

    @Override
    public void initViews() {
        tv_project_summary = findView(R.id.project_summary);
        tv_project_data = findView(R.id.project_data);
        tv_project_participant = findView(R.id.project_participant);
        vp_project_new = findView(R.id.vp_project_new);
    }

    @Override
    public void initListener() {
        setOnClick(tv_project_data);
        setOnClick(tv_project_participant);
        setOnClick(tv_project_summary);
        vp_project_new.setOnPageChangeListener(this);
    }

    @Override
    public void initData() {
        setTitle("新建项目");
        setEdit2("");
        setTitleCanBack();
        //初始化碎片
        initFragments();
    }

    @Override
    public void processClick(View v) {
        switch (v.getId()) {
            case R.id.project_summary:
                selectPager(0);
                break;
            case R.id.project_data:
                selectPager(1);
                break;
            case R.id.project_participant:
                selectPager(2);
                break;
        }
    }
    /**
     * 初始化碎片
     */
    private void initFragments() {
        list = new ArrayList<>();
        list.add(new ProjectBigNewOneFragment());
        list.add(new ProjectBigNewTwoFragment());
        list.add(new ProjectBigNewThreeFragment());
        adapter = new MainAdapter(getSupportFragmentManager(), list);
        vp_project_new.setAdapter(adapter);
        vp_project_new.setOffscreenPageLimit(3);
        //初始化图标
        selectPager(0);
    }

    public void selectPager(int position) {
        vp_project_new.setCurrentItem(position);
        int blueColor = getResources().getColor(R.color.colorPrimary);
        int whiteColor = getResources().getColor(R.color.colorWhite);
        Drawable bg_unselected = getResources().getDrawable(R.drawable.project_title_white_shape);
        Drawable bg_selected = getResources().getDrawable(R.color.colorWhite);
        tv_project_summary.setTextColor(position == 0 ? blueColor : whiteColor);
        tv_project_summary.setBackground(position == 0 ? bg_selected : bg_unselected);
        tv_project_data.setTextColor(position == 1 ? blueColor : whiteColor);
        tv_project_data.setBackground(position == 1 ? bg_selected : bg_unselected);
        tv_project_participant.setTextColor(position == 2 ? blueColor : whiteColor);
        tv_project_participant.setBackground(position == 2 ? bg_selected : bg_unselected);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        selectPager(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
