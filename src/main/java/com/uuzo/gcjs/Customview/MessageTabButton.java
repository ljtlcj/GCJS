package com.uuzo.gcjs.Customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uuzo.gcjs.R;

/**
 * Created by 柯东煜 on 2018/1/14.
 */

public class MessageTabButton extends RelativeLayout {
    private View mtb_view;
    private TextView tv_title;
    private TextView tv_unread;
    private Context context;
    private String mtb_title; //标题
    private String mtb_num_unread;  //未读数
    private boolean mtb_selected; //是否选中

    public MessageTabButton(Context context) {
        this(context, null);
    }

    public MessageTabButton(Context context, AttributeSet as) {
        this(context, as, 0);
    }

    public MessageTabButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        mtb_view = LayoutInflater.from(context).inflate(R.layout.view_message_tab, this, true);
        if (attrs != null)
            initAttrs(context, attrs, defStyleAttr);
        initViews();
    }

    public void initAttrs(Context context, AttributeSet as, int def) {
        TypedArray ta = context.obtainStyledAttributes(as, R.styleable.MessageTabButton, def, 0);
        mtb_num_unread = ta.getString(R.styleable.MessageTabButton_mtb_unread);
        mtb_title = ta.getString(R.styleable.MessageTabButton_mtb_title);
        mtb_selected = ta.getBoolean(R.styleable.MessageTabButton_mtb_selected, false);
    }

    public void initViews() {
        tv_title = (TextView) findViewById(R.id.mtb_title);
        tv_unread = (TextView) findViewById(R.id.mtb_unread);
        tv_title.setText(mtb_title);
        if (mtb_num_unread == null) {
            tv_unread.setVisibility(GONE);
        } else {
            tv_unread.setText(mtb_num_unread);
        }
        isSelected(mtb_selected);
    }

    public void isSelected(boolean b) {
        if (b) {
            mtb_view.setBackgroundColor(getResources().getColor(R.color.color_view_mtb));
            tv_title.setTextColor(getResources().getColor(R.color.colorWhite));
            //  tv_unread.setVisibility(GONE);
        } else {
            mtb_view.setBackground(getResources().getDrawable(R.drawable.message_tab_bg_unselected_shape));
            tv_title.setTextColor(getResources().getColor(R.color.color_view_mtb));
        }
    }

}