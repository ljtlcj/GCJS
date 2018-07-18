package com.uuzo.gcjs.Main.ProjectBigNew;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uuzo.gcjs.BaseTemplate.BaseLazyFragment;
import com.uuzo.gcjs.Controller.ProjectNewController;
import com.uuzo.gcjs.Manager.InterfaceManger;
import com.uuzo.gcjs.R;
import com.uuzo.gcjs.Utils.RetrofitUtils;
import com.uuzo.gcjs.View.Project.ProjectManChoice.ProjectMan1Activity;
import com.uuzo.gcjs.View.Project.ProjectManChoice.ProjectMan2Activity;
import com.uuzo.gcjs.View.Project.ProjectManChoice.ProjectMan3Activity;
import com.uuzo.gcjs.View.Project.ProjectManChoice.ProjectMan4Activity;
import com.uuzo.gcjs.View.Project.ProjectManChoice.ProjectMan5Activity;
import com.uuzo.gcjs.View.Project.ProjectManChoice.ProjectMan6Activity;
import com.uuzo.gcjs.View.Project.ProjectManChoice.ProjectMan7Activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by jie on 2018/7/16.
 */

public class ProjectBigNewOneFragment extends BaseLazyFragment implements PopupWindow.OnDismissListener {
    private TextView et_item_name;//选择工程类别
    private PopupWindow popupWindow;//模仿ios弹框
    private int navigationHeight;

    private EditText et_item_one_name;//工程名称
    private EditText et_time;//工程编号
    private EditText et_check_man;//建设单位
    private EditText et_start;//开始时间
    private EditText et_end;//截至时间
    private EditText days;//天数
    private EditText et_check_item;//概况

    private TextView man_1;//1人
    private TextView man_2;//2人
    private TextView man_3;//3人
    private TextView man_4;//4人
    private TextView man_5;//5人
    private TextView man_6;//6人
    private TextView man_7;//7人

    private LinearLayout one;
    private LinearLayout tow;
    private LinearLayout three;
    private LinearLayout four;
    private LinearLayout five;
    private LinearLayout six;
    private LinearLayout seven;

    private Button btn_submit;//提交按钮


    @Override
    public int getLayoutId() {
        return R.layout.activity_project_big_new_one;
    }

    @Override
    public void initViews() {
        btn_submit = findView(R.id.btn_submit);

        et_item_name = findView(R.id.et_item_name);
        one = findView(R.id.LL_temp);
        tow = findView(R.id.Ln2);
        three = findView(R.id.Ln3);
        four = findView(R.id.Ln4);
        five = findView(R.id.Ln5);
        six = findView(R.id.Ln6);
        seven = findView(R.id.Ln7);

        man_1 = findView(R.id.man_1);
        man_2 = findView(R.id.man_2);
        man_3 = findView(R.id.man_3);
        man_4 = findView(R.id.man_4);
        man_5 = findView(R.id.man_5);
        man_6 = findView(R.id.man_6);
        man_7 = findView(R.id.man_7);

        et_item_one_name = findView(R.id.et_item_one_name);
        et_time = findView(R.id.et_time);
        et_check_man = findView(R.id.et_check_man);
        et_start = findView(R.id.et_start);
        et_end = findView(R.id.et_end);
        days = findView(R.id.days);
        et_check_item = findView(R.id.et_check_item);

        getdata();
    }

    private void getdata() {
        if (mSavedInstanceState != null) {
            et_item_one_name.setText(mSavedInstanceState.getString("name"));
            et_time.setText(mSavedInstanceState.getString("number"));
            et_check_man.setText(mSavedInstanceState.getString("unit"));
            et_start.setText(mSavedInstanceState.getString("begin"));
            et_end.setText(mSavedInstanceState.getString("end"));
            days.setText(mSavedInstanceState.getString("day"));
            et_check_item.setText(mSavedInstanceState.getString("content"));
            Log.e("onCreate:123",mSavedInstanceState.getString("name"));
        }
    }

    @Override
    public void initListener() {
        et_item_name.setOnClickListener(this);
        one.setOnClickListener(this);
        tow.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);
        five.setOnClickListener(this);
        six.setOnClickListener(this);
        seven.setOnClickListener(this);
        btn_submit.setOnClickListener(this);

    }

    @Override
    public void initData() {
        int resourceId = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        navigationHeight = getResources().getDimensionPixelSize(resourceId);
    }

    @Override
    public void processClick(View v) {
        switch (v.getId()) {
            case R.id.et_item_name:
                openPopupWindow(v);
                break;
            case R.id.tv_pick_phone:
                et_item_name.setText("主网项目");
                popupWindow.dismiss();
                break;
            case R.id.tv_pick_zone:
                et_item_name.setText("配网项目");
                popupWindow.dismiss();
                break;
            case R.id.tv_try:
                et_item_name.setText("其他");
                popupWindow.dismiss();
                break;
            case R.id.tv_pick_auto:
                et_item_name.setText("小型基建项目");
                popupWindow.dismiss();
                break;
            case R.id.tv_cancel:
                popupWindow.dismiss();
                break;

            case R.id.btn_submit:
                fanwen();
                break;
            case R.id.LL_temp:
                startActivity(new Intent(getActivity(), ProjectMan1Activity.class));
                break;
            case R.id.Ln2:
                startActivity(new Intent(getActivity(), ProjectMan2Activity.class));
                break;
            case R.id.Ln3:
                startActivity(new Intent(getActivity(), ProjectMan3Activity.class));
                break;
            case R.id.Ln4:
                startActivity(new Intent(getActivity(), ProjectMan4Activity.class));
                break;
            case R.id.Ln5:
                startActivity(new Intent(getActivity(), ProjectMan5Activity.class));
                break;
            case R.id.Ln6:
                startActivity(new Intent(getActivity(), ProjectMan6Activity.class));
                break;
            case R.id.Ln7:
                startActivity(new Intent(getActivity(), ProjectMan7Activity.class));
                break;
        }
    }

    /**
     * 提交文件
     */
    private void fanwen() {
        String project_name = et_item_one_name.getText().toString().trim();
        String project_number = et_time.getText().toString().trim();
        String action_unit = et_check_man.getText().toString().trim();
        String start_time = et_start.getText().toString().trim();
        String end_time = et_end.getText().toString().trim();
        String work_day = days.getText().toString().trim();
        String situation = et_check_item.toString().trim();
        String pm = man_1.getText().toString().trim();
        String etc = man_2.getText().toString().trim();
        String so = man_3.getText().toString().trim();
        String qc = man_4.getText().toString().trim();
        String technical = man_5.getText().toString().trim();
        String material = man_6.getText().toString().trim();
        String em = man_7.getText().toString().trim();
        String project_type= et_item_name.getText().toString().trim();
        String ptype= et_item_name.getText().toString().trim();
        List<String> photos = new ArrayList<>();
        List<MultipartBody.Part> parts = null;
//        parts = RetrofitUtils.filesToMultipartBodyParts("photo", photos);
        Map<String, RequestBody> params = new HashMap<>();
        params.put("token", RetrofitUtils.convertToRequestBody("166b72d497d9f23ab2074734515c6ac908b6e146"));
        params.put("project_name", RetrofitUtils.convertToRequestBody(String.valueOf(project_name)));
        params.put("project_number", RetrofitUtils.convertToRequestBody(project_number));
        params.put("action_unit", RetrofitUtils.convertToRequestBody(action_unit));
        params.put("start_time", RetrofitUtils.convertToRequestBody(start_time));
        params.put("end_time", RetrofitUtils.convertToRequestBody(end_time));
        params.put("work_day", RetrofitUtils.convertToRequestBody(work_day));
        params.put("situation", RetrofitUtils.convertToRequestBody(situation));
        params.put("pm", RetrofitUtils.convertToRequestBody(pm));

        params.put("etc", RetrofitUtils.convertToRequestBody(etc));
        params.put("so", RetrofitUtils.convertToRequestBody(so));
        params.put("qc", RetrofitUtils.convertToRequestBody(qc));
        params.put("technical", RetrofitUtils.convertToRequestBody(technical));
        params.put("material", RetrofitUtils.convertToRequestBody(material));
        params.put("em", RetrofitUtils.convertToRequestBody(em));
        params.put("project_type", RetrofitUtils.convertToRequestBody(project_type));
        params.put("ptype", RetrofitUtils.convertToRequestBody(ptype));


        ProjectNewController.lixiang(params, parts, new InterfaceManger.OnRequestListener() {
            @Override
            public void onSuccess(Object success) {
                Toast.makeText(getActivity(), success.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String error) {
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {

            }
        });

    }

    private void choice() {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            et_item_one_name.setText(savedInstanceState.getString("name"));
            et_time.setText(savedInstanceState.getString("number"));
            et_check_man.setText(savedInstanceState.getString("unit"));
            et_start.setText(savedInstanceState.getString("begin"));
            et_end.setText(savedInstanceState.getString("end"));
            days.setText(savedInstanceState.getString("day"));
            et_check_item.setText(savedInstanceState.getString("content"));
        }
    }

    /**
     * 弹框设置
     * @param v
     */
    private void openPopupWindow(View v) {
        //防止重复按按钮
        if (popupWindow != null && popupWindow.isShowing()) {
            return;
        }
        //设置PopupWindow的View
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.view_popupwindow1, null);
        popupWindow = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        //设置背景,这个没什么效果，不添加会报错
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //设置点击弹窗外隐藏自身
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        //设置动画
        popupWindow.setAnimationStyle(R.style.PopupWindow);
        //设置位置
        popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, navigationHeight);
        //设置消失监听
        popupWindow.setOnDismissListener(this);
        //设置PopupWindow的View点击事件
        setOnPopupViewClick(view);
        //设置背景色
        setBackgroundAlpha(0.5f);
    }

    /**
     * 弹框点击
     * @param view
     */
    private void setOnPopupViewClick(View view) {
        TextView tv_pick_phone, tv_pick_zone, tv_cancel,tv_pick_auto,tv_try,tv_top,tv_else;
        tv_pick_phone = (TextView) view.findViewById(R.id.tv_pick_phone);
        tv_pick_zone = (TextView) view.findViewById(R.id.tv_pick_zone);
        tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        tv_pick_auto = (TextView) view.findViewById(R.id.tv_pick_auto);
        tv_try = (TextView) view.findViewById(R.id.tv_try);
        tv_pick_phone.setOnClickListener(this);
        tv_pick_zone.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
        tv_pick_auto.setOnClickListener(this);
        tv_try.setOnClickListener(this);
    }

    //设置屏幕背景透明效果
    public void setBackgroundAlpha(float alpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = alpha;
        getActivity().getWindow().setAttributes(lp);
    }

    /**
     * 未仿ios制作的
     */
    @Override
    public void onDismiss() {
        setBackgroundAlpha(1);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);
        String name = et_item_one_name.getText().toString().trim();
        String number = et_time.getText().toString().trim();
        String unit = et_check_man.getText().toString().trim();
        String begin = et_start.getText().toString().trim();
        String end = et_end.getText().toString().trim();
        String day = days.getText().toString().trim();
        String content = et_check_item.toString().trim();
        outState.putString("name", name);
        outState.putString("number", number);
        outState.putString("unit", unit);
        outState.putString("begin", begin);
        outState.putString("end", end);
        outState.putString("day", day);
        outState.putString("content", content);
        Log.e("onCreate:123",outState.getString("name"));
    }


    @Override
    public void onResume() {
        super.onResume();

        if (mSavedInstanceState != null) {
            et_item_one_name.setText(mSavedInstanceState.getString("name"));
            et_time.setText(mSavedInstanceState.getString("number"));
            et_check_man.setText(mSavedInstanceState.getString("unit"));
            et_start.setText(mSavedInstanceState.getString("begin"));
            et_end.setText(mSavedInstanceState.getString("end"));
            days.setText(mSavedInstanceState.getString("day"));
            et_check_item.setText(mSavedInstanceState.getString("content"));
        }
    }
}
