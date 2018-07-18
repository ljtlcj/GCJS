package com.uuzo.gcjs.View.Project;

import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uuzo.gcjs.BaseTemplate.BaseActivity;
import com.uuzo.gcjs.R;

/**
 * Created by jie on 2018/7/16.
 */

public class ProjectNewItemActivity extends BaseActivity implements PopupWindow.OnDismissListener{
    private EditText et_item_one_name;
    private EditText et_item_name;
    private TextView et_time;
    private EditText et_check_man;
    private EditText et_input;

    private PopupWindow popupWindow;//模仿ios弹框
    private int navigationHeight;
    @Override
    public int getLayoutId() {
        return R.layout.activity_project_new_item;
    }

    @Override
    public void initViews() {
        et_time = findView(R.id.et_time);
    }

    @Override
    public void initListener() {
        et_time.setOnClickListener(this);
    }

    @Override
    public void initData() {
        setTitle("新建项目工单");
        setTitleCanBack();
        setEdit("表格导入");
        int resourceId = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        navigationHeight = getResources().getDimensionPixelSize(resourceId);
    }

    @Override
    public void processClick(View v) {
        switch (v.getId()){
            case R.id.et_time:
                openPopupWindow(v);
                break;
            case R.id.tv_pick_phone:
                et_time.setText("电器安装工程");
                popupWindow.dismiss();
                break;
            case R.id.tv_pick_zone:
                et_time.setText("土建工程");
                popupWindow.dismiss();
                break;
            case R.id.tv_top:
                et_time.setText("顶管");
                popupWindow.dismiss();
                break;
            case R.id.tv_try:
                et_time.setText("实验运维");
                popupWindow.dismiss();
                break;
            case R.id.tv_pick_auto:
                et_time.setText("通信与自动化工程");
                popupWindow.dismiss();
                break;
            case R.id.tv_else:
                et_time.setText("其他");
                popupWindow.dismiss();
                break;
            case R.id.tv_cancel:
                popupWindow.dismiss();
                break;
        }
    }

    private void openPopupWindow(View v) {
        //防止重复按按钮
        if (popupWindow != null && popupWindow.isShowing()) {
            return;
        }
        //设置PopupWindow的View
        View view = LayoutInflater.from(this).inflate(R.layout.view_popupwindow, null);
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

    private void setOnPopupViewClick(View view) {
        TextView tv_pick_phone, tv_pick_zone, tv_cancel,tv_pick_auto,tv_try,tv_top,tv_else;
        tv_pick_phone = (TextView) view.findViewById(R.id.tv_pick_phone);
        tv_pick_zone = (TextView) view.findViewById(R.id.tv_pick_zone);
        tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        tv_pick_auto = (TextView) view.findViewById(R.id.tv_pick_auto);
        tv_try = (TextView) view.findViewById(R.id.tv_try);
        tv_top = (TextView) view.findViewById(R.id.tv_top);
        tv_else = (TextView) view.findViewById(R.id.tv_else);
        tv_pick_phone.setOnClickListener(this);
        tv_pick_zone.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
        tv_pick_auto.setOnClickListener(this);
        tv_try.setOnClickListener(this);
        tv_top.setOnClickListener(this);
        tv_else.setOnClickListener(this);
    }

    //设置屏幕背景透明效果
    public void setBackgroundAlpha(float alpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = alpha;
        getWindow().setAttributes(lp);
    }

    @Override
    public void onDismiss() {
        setBackgroundAlpha(1);
    }
}
