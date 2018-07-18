package com.uuzo.gcjs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class Main_GZT_Fragment extends Fragment
{
	Boolean IsDestroy=false;
	Context ThisContext;
    Activity ThisActivity;

    LinearLayout widget_1,widget_2,widget_3,widget_4;

	public Main_GZT_Fragment()
	{
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_main_gzt, container, false);
		
		IsDestroy=false;
        ThisContext=getContext();
        ThisActivity=getActivity();


        widget_1 = (LinearLayout) view.findViewById(R.id.widget_1);
        widget_2 = (LinearLayout)view.findViewById(R.id.widget_2);
        widget_3 = (LinearLayout)view.findViewById(R.id.widget_3);
        widget_4 = (LinearLayout)view.findViewById(R.id.widget_4);

        widget_1.getChildAt(0).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                new MessageBox().Show(ThisContext, "提示", "待开放", "", getString(R.string.OK));
            }
        });
        widget_1.getChildAt(1).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                new MessageBox().Show(ThisContext, "提示", "待开放", "", getString(R.string.OK));
            }
        });
        widget_1.getChildAt(2).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                new MessageBox().Show(ThisContext, "提示", "待开放", "", getString(R.string.OK));
            }
        });
        widget_1.getChildAt(3).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ThisContext, TYSQ_Activity.class));
            }
        });

        widget_2.getChildAt(0).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ThisContext, XM_QD_Activity.class));
            }
        });
        widget_2.getChildAt(1).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ThisContext, XM_JDJH_Activity.class).putExtra("FunType",0));
            }
        });
        widget_2.getChildAt(2).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ThisContext, XM_JDJH_Activity.class).putExtra("FunType",1));
            }
        });
        widget_2.getChildAt(3).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                new MessageBox().Show(ThisContext, "提示", "待开放", "", getString(R.string.OK));
            }
        });


        widget_3.getChildAt(0).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                new MessageBox().Show(ThisContext, "提示", "待开放", "", getString(R.string.OK));
            }
        });
        widget_3.getChildAt(1).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                new MessageBox().Show(ThisContext, "提示", "待开放", "", getString(R.string.OK));
            }
        });
        widget_3.getChildAt(2).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                new MessageBox().Show(ThisContext, "提示", "待开放", "", getString(R.string.OK));
            }
        });
        widget_3.getChildAt(3).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                new MessageBox().Show(ThisContext, "提示", "待开放", "", getString(R.string.OK));
            }
        });


        widget_4.getChildAt(0).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                new MessageBox().Show(ThisContext, "提示", "待开放", "", getString(R.string.OK));
            }
        });
        widget_4.getChildAt(1).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                new MessageBox().Show(ThisContext, "提示", "待开放", "", getString(R.string.OK));
            }
        });
        widget_4.getChildAt(2).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                new MessageBox().Show(ThisContext, "提示", "待开放", "", getString(R.string.OK));
            }
        });
        widget_4.getChildAt(3).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                new MessageBox().Show(ThisContext, "提示", "待开放", "", getString(R.string.OK));
            }
        });

		return view;
	}

	@Override
	public void onDestroyView() {
		IsDestroy=true;
		super.onDestroyView();		
	}
}
