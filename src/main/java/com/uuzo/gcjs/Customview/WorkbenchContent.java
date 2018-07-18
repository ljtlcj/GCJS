
package com.uuzo.gcjs.Customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uuzo.gcjs.R;

/**
 * Created by jie on 2018/1/14.
 */

public class WorkbenchContent extends RelativeLayout {

    private  View myview;
    private TextView tv_news;
    private ImageView iv_photos;
    private TextView tv_content;
    private Context context;
    private boolean wc_new_notice; //是否有新消息
    private Drawable wc_image;  //图片
    private String wc_content; //功能介绍

    public WorkbenchContent(Context context) {
        this(context, null);
    }

    public WorkbenchContent(Context context, AttributeSet as) {
        this(context, as, 0);
    }

    public WorkbenchContent(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        myview = LayoutInflater.from(context).inflate(R.layout.view_workbench, this, true);
        if (attrs != null)
            initAttrs(context, attrs, defStyleAttr);
        initViews();
    }

    public void initAttrs(Context context, AttributeSet as, int def) {
        TypedArray ta = context.obtainStyledAttributes(as, R.styleable.WorkbenchContent, def, 0);
        wc_image = ta.getDrawable(R.styleable.WorkbenchContent_wc_image);
        wc_content = ta.getString(R.styleable.WorkbenchContent_wc_content);
        wc_new_notice = ta.getBoolean(R.styleable.WorkbenchContent_wc_new_notice, false);
    }

    public void initViews() {
        tv_content = (TextView) myview.findViewById(R.id.wc_content);
        tv_news = (TextView) myview.findViewById(R.id.wc_new_notice);
        iv_photos =  (ImageView) myview.findViewById(R.id.wc_image);
        tv_content.setText(wc_content);
        if (wc_new_notice ) {
            tv_news.setVisibility(VISIBLE);
        } else {
            tv_news.setVisibility(GONE);
        }
    }
}