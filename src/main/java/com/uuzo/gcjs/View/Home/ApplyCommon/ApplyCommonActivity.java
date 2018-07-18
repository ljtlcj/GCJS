package com.uuzo.gcjs.View.Home.ApplyCommon;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


import com.uuzo.gcjs.Adapter.MainAdapter;
import com.uuzo.gcjs.BaseTemplate.BaseActivity;
import com.uuzo.gcjs.Main.ApplyCommon.ApplyCommonApprovalFragment;
import com.uuzo.gcjs.Main.ApplyCommon.ApplyCommonSubmitFragment;
import com.uuzo.gcjs.R;

import java.util.ArrayList;

public class ApplyCommonActivity extends BaseActivity implements ViewPager.OnPageChangeListener{
    private ImageButton ib_find;//头部搜素
    private ImageButton ib_add;//添加
    private Button btn_approval;//我审批的
    private Button btn_submit;//我提交的
    private ViewPager vp_apply_common;
    private ArrayList<Fragment> list;
    private MainAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_apply_common;
    }

    @Override
    public void initViews() {
        ib_find = findView(R.id.ib_find);
        ib_add = findView(R.id.ib_add);
        btn_approval = findView(R.id.btn_approval);
        btn_submit = findView(R.id.btn_submit);
        vp_apply_common = findView(R.id.vp_apply_common);
    }

    @Override
    public void initListener() {
        ib_add.setOnClickListener(this);
        setOnClick(btn_approval);
        setOnClick(btn_submit);
        vp_apply_common.setOnPageChangeListener(this);
    }

    @Override
    public void initData() {
        setTitle("通用申请");
        setTitleCanBack();
        initFragments();
    }

    private void initFragments() {
        list = new ArrayList<Fragment>();
        list.add(new ApplyCommonApprovalFragment());
        list.add(new ApplyCommonSubmitFragment());
        adapter =new MainAdapter(this.getSupportFragmentManager(),list);
        vp_apply_common.setAdapter(adapter);
        vp_apply_common.setOffscreenPageLimit(1);
        onPageSelected(0);
    }

    @Override
    public void processClick(View v) {
        switch (v.getId()) {
            case R.id.btn_approval:
                onPageSelected(0);
                break;
            case R.id.btn_submit:
                onPageSelected(1);
                break;
            case R.id.ib_add:
//                Intent intent = new Intent(ApplyCommonActivity.this,OptionProjectActivity.class);
//                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        vp_apply_common.setCurrentItem(position);
        if (position == 0) {
            btn_approval.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            btn_approval.setTextColor(getResources().getColor(R.color.colorPrimary));
            btn_submit.setBackground(getResources().getDrawable(R.drawable.work_task_bd_white_bg_blue));
            btn_submit.setTextColor(getResources().getColor(R.color.white));

        } else {
            btn_submit.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            btn_submit.setTextColor(getResources().getColor(R.color.colorPrimary));
            btn_approval.setBackground(getResources().getDrawable(R.drawable.work_task_bd_white_bg_blue));
            btn_approval.setTextColor(getResources().getColor(R.color.white));
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
