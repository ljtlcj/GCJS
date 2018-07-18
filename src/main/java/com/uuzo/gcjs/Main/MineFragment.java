package com.uuzo.gcjs.Main;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.uuzo.gcjs.BaseTemplate.BaseLazyFragment;
import com.uuzo.gcjs.Customview.FastMenuBar;
import com.uuzo.gcjs.R;

/**
 * Created by Diko on 2018/1/13.
 */
public class MineFragment extends BaseLazyFragment implements FastMenuBar.onMenuBarClickListener {
    private FastMenuBar fmb_company_information;
    private FastMenuBar fmb_help;
    private FastMenuBar fmb_cache_clean_up;
    private FastMenuBar fmb_feedback;
    private FastMenuBar fmb_setting;
    private ImageView iv_photo;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initViews() {
//        fmb_company_information = findView(R.id.company_information);
//        fmb_help = findView(R.id.help);
//        fmb_cache_clean_up = findView(R.id.cache_clean_up);
//        fmb_setting = findView(R.id.setting);
//        fmb_feedback = findView(R.id.feedback);
//        iv_photo=findView(R.id.my_photo);
    }

    @Override
    public void initListener() {
//        fmb_company_information.setOnMenuBarClickListener(this);
//        fmb_help.setOnMenuBarClickListener(this);
//        fmb_cache_clean_up.setOnMenuBarClickListener(this);
//        fmb_feedback.setOnMenuBarClickListener(this);
//        fmb_setting.setOnMenuBarClickListener(this);
//        iv_photo.setOnClickListener(this);
    }

    @Override
    public void initData() {
        setTitle("我的");
    }

    @Override
    public void processClick(View v) {
//        if(v.getId()==R.id.my_photo){
//            Intent i=new Intent(getActivity(), MinePersonalInfo.class);
//            startActivity(i);


//        }
    }

    @Override
    public void onMenuBarClick(FastMenuBar view) {
        Intent i;
//        switch (view.getId()) {
////            case R.id.company_information:
////                i=new Intent(getActivity(), MineCompanyInfo.class);
////                startActivity(i);
//                break;
//            case R.id.help:
//                break;
//            case R.id.cache_clean_up:
//                break;
//            case R.id.feedback:
////                i=new Intent(getActivity(), MineFeedback.class);
////                startActivity(i);
//                break;
//            case R.id.setting:
////                i = new Intent(getActivity(), MineSetting.class);
////                startActivity(i);
//                break;
//            default:
//
//        }
    }
}
