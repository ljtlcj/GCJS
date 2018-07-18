package com.uuzo.gcjs.BaseTemplate;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.uuzo.gcjs.R;

import java.util.ArrayList;
import java.util.List;


public abstract class BaseLazyFragment extends Fragment implements View.OnClickListener {

    private boolean isVisible = false;
    private boolean isInitView = false;
    private boolean isFirstLoad = true;

    private View convertView;
    private SparseArray<View> mViews;
    private Intent intent;

    public Bundle mSavedInstanceState;

    public abstract int getLayoutId();

    public abstract void initViews();

    public abstract void initListener();

    public abstract void initData();

    public abstract void processClick(View v);

    @Override
    public void onClick(View v) {
        processClick(v);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isVisible = true;
            lazyLoad();
        } else {
            isVisible = false;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mViews = new SparseArray<>();
        convertView = inflater.inflate(getLayoutId(), container, false);
        this.mSavedInstanceState = savedInstanceState;
        initViews();

        isInitView = true;
        lazyLoad();
        return convertView;
    }

    private void lazyLoad() {
        if (!isFirstLoad || !isVisible || !isInitView) {
            //Don't load data
            return;
        }
        //Load data
        initListener();
        initData();

        isFirstLoad = false;
    }

    /**
     * Encapsulation findViewById
     *
     * @param viewId
     * @param <E>
     * @return
     */
    public <E extends View> E findView(int viewId) {
        if (convertView != null) {
            E view = (E) mViews.get(viewId);
            if (view == null) {
                view = (E) convertView.findViewById(viewId);
                mViews.put(viewId, view);
            }
            return view;
        }
        return null;
    }

    /**
     * Encapsulation setOnClickListener
     *
     * @param view
     * @param <E>
     */
    public <E extends View> void setOnClick(E view) {
        view.setOnClickListener(this);
    }

    /**
     * Open the interface
     *
     * @param clazz
     */
    public void startActivity(Class clazz) {
        intent = new Intent(getActivity(), clazz);
        startActivity(intent);
    }

    /**
     * Popup toast
     *
     * @param msg
     */
    public void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * Request permission
     *
     * @param permissions
     */
    public void requestPermissions(String... permissions) {
        if (Build.VERSION.SDK_INT >= 23) {
            List<String> list = new ArrayList<>();
            for (int i = 0; i < permissions.length; i++) {
                if (ContextCompat.checkSelfPermission(getActivity(), permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                    list.add(permissions[i]);
                    if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permissions[i])) {
                        showToast("没有开启权限将会导致部分功能不可使用");
                    }
                }
            }
            ActivityCompat.requestPermissions(getActivity(), list.toArray(new String[permissions.length]), 0);
        }
    }

    /**
     * 设置标题栏标题
     *
     * @param title
     */
    public void setTitle(String title) {
        TextView tv_title = findView(R.id.tv_title);
        tv_title.setText(title);
    }

    /**
     * 设置标题栏有返回按钮
     */
    public void setTitleCanBack() {
        ImageView iv_finish = findView(R.id.iv_finish);
        iv_finish.setVisibility(View.VISIBLE);
        setOnClick(iv_finish);
    }

    /**
     * 设置编辑菜单
     *
     * @param text
     */
    public void setEdit(String text) {
        TextView tv_edit = findView(R.id.tv_edit);
        tv_edit.setVisibility(View.VISIBLE);
        tv_edit.setText(text);
        setOnClick(tv_edit);
    }

    /**
     * 设置编辑菜单
     *
     * @param text
     */
    public void setEdit2(String text) {
        TextView tv_edit2 = findView(R.id.tv_edit2);
        tv_edit2.setVisibility(View.VISIBLE);
        tv_edit2.setText(text);
        setOnClick(tv_edit2);
    }
}
