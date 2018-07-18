package com.uuzo.gcjs;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Reg_Vehicle_Fragment extends Fragment
{
	Boolean IsDestroy=false;
	Context ThisContext;
    Activity ThisActivity;


	public Reg_Vehicle_Fragment()
	{
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_err, container, false);
		
		IsDestroy=false;
        ThisContext=getContext();
        ThisActivity=getActivity();

		return view;
	}

	@Override
	public void onDestroyView() {
		IsDestroy=true;
		super.onDestroyView();		
	}
}
