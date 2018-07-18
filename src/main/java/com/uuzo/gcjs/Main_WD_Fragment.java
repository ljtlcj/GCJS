package com.uuzo.gcjs;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Main_WD_Fragment extends Fragment
{
	Boolean IsDestroy=false;
	Context ThisContext;
    Activity ThisActivity;

    TextView widget_0;

	public Main_WD_Fragment()
	{
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_main_wd, container, false);
		
		IsDestroy=false;
        ThisContext=getContext();
        ThisActivity=getActivity();


        widget_0 = (TextView) view.findViewById(R.id.widget_0);

        widget_0.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                new MessageBox().Show(ThisContext, "提示", "确认登出吗？", getString(R.string.Cancel), getString(R.string.OK)).BtnClick = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if (whichButton == -1) {
                            UserInfo.Clear(ThisContext);

                            new MessageBox().Show(ThisContext, "提示", "登出成功", "", getString(R.string.OK)).BtnClick = new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    if (whichButton == -1) {
                                        startActivity(new Intent(ThisContext, LoginActivity.class));
                                        ThisActivity.finish();
                                    }
                                }
                            };
                        }
                    }
                };

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
