package com.uuzo.gcjs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

public class XM_Add_Activity extends Activity {

	Boolean IsDestroy=false;
	Context ThisContext;
	Activity ThisActivity;
	
	TextView app_title_center,app_title_right2;
    ImageView app_title_left,app_title_right;

    LinearLayout widget_1,widget_2,widget_3,widget_4,widget_5,widget_6,widget_7,widget_8,widget_9,widget_10,widget_11,widget_12,widget_13,widget_14,widget_15,widget_16,widget_17,widget_18,widget_20,widget_22;
    PullToRefreshListView widget_19,widget_21;
	ClsClass.Cls _Cls;
	List<ClsClass.Cls> CJFList=new ArrayList<>();

	int NowIndex=0;

	Boolean IsCancel=true;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_xm_add);
        Config.SetStatusBar(this);
	    
	    IsDestroy=false;
	    ThisContext=this;
	    ThisActivity=this;

	    if (Config.NowCls==null)
		{
	    	finish();
			return;
		}
        _Cls= Config.NowCls;
	    
	    app_title_center = (TextView) findViewById(R.id.app_title_center);
        app_title_left = (ImageView) findViewById(R.id.app_title_left);
        app_title_right = (ImageView) findViewById(R.id.app_title_right);
        app_title_right2 = (TextView) findViewById(R.id.app_title_right2);
        app_title_right.setVisibility(View.GONE);
        app_title_right2.setVisibility(View.VISIBLE);
        app_title_right2.setText("下一步");
        app_title_center.setText("新建项目");
        app_title_left.setImageResource(R.drawable.back);
        app_title_left.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) {
				finish();
			}
		});
        app_title_right2.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v) {
                widget_22.getChildAt(0).callOnClick();
            }
        });

        widget_1 = (LinearLayout) findViewById(R.id.widget_1);
        widget_2 = (LinearLayout)findViewById(R.id.widget_2);
        widget_3 = (LinearLayout)findViewById(R.id.widget_3);
        widget_4 = (LinearLayout)findViewById(R.id.widget_4);
        widget_5 = (LinearLayout)findViewById(R.id.widget_5);
        widget_6 = (LinearLayout)findViewById(R.id.widget_6);
        widget_7 = (LinearLayout)findViewById(R.id.widget_7);
        widget_8 = (LinearLayout)findViewById(R.id.widget_8);
        widget_9 = (LinearLayout)findViewById(R.id.widget_9);
        widget_10 = (LinearLayout)findViewById(R.id.widget_10);
        widget_11 = (LinearLayout)findViewById(R.id.widget_11);
        widget_12 = (LinearLayout)findViewById(R.id.widget_12);
        widget_13 = (LinearLayout)findViewById(R.id.widget_13);
        widget_14 = (LinearLayout)findViewById(R.id.widget_14);
        widget_15 = (LinearLayout)findViewById(R.id.widget_15);
        widget_16 = (LinearLayout)findViewById(R.id.widget_16);
        widget_17 = (LinearLayout)findViewById(R.id.widget_17);
        widget_18 = (LinearLayout)findViewById(R.id.widget_18);
        widget_19 = (PullToRefreshListView) findViewById(R.id.widget_19);
        widget_20 = (LinearLayout)findViewById(R.id.widget_20);
        widget_21 = (PullToRefreshListView) findViewById(R.id.widget_21);
        widget_22 = (LinearLayout)findViewById(R.id.widget_22);

        widget_7.getChildAt(0).setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if (_Cls.ClsList_1.size()==0) return;
                String[] XMStrSplit=new String[_Cls.ClsList_1.size()];
                for(int i=0;i<_Cls.ClsList_1.size();i++){
                    XMStrSplit[i]=_Cls.ClsList_1.get(i).Str_2;
                }

                new AlertDialog.Builder(ThisContext)
                        .setItems(XMStrSplit, new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                if (which>=0 && which<_Cls.ClsList_1.size()){
                                    NowIndex=which;
                                    UpdateInfo();
                                }
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                dialog.dismiss();
                            }
                        }).show();
            }
        });


        widget_22.getChildAt(0).setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v) {


                startActivityForResult(new Intent(ThisContext, XM_Add_Upload_Activity.class),1);
            }
        });

        List<ClsClass.Cls> TempList=new ArrayList<>();
        for (int i=0;i<_Cls.ClsList_2.size();i++){
            ClsClass.Cls _Cls3=_Cls.ClsList_2.get(i);

            ClsClass.Cls _Cls4=new ClsClass().new Cls();
            _Cls4.Int_1=_Cls3.Int_3;
            _Cls4.Str_1= Config.GetCJFTypeName(_Cls3.Int_3);

            int _Index=-1;
            for (int j=0;j<TempList.size();j++){
                if (TempList.get(j).Int_1==_Cls3.Int_3){
                    _Index=j;
                    break;
                }
            }
            if (_Index==-1){
                _Cls4.ClsList_1.add(_Cls3);
                TempList.add(_Cls4);
            }
            else{
                _Cls4=TempList.get(_Index);
                _Cls4.ClsList_1.add(_Cls3);
            }
        }

        CJFList.clear();
        for (int i=0;i<TempList.size();i++){
            ClsClass.Cls _Cls3=TempList.get(i);
            for (int j=0;j<_Cls3.ClsList_1.size();j++){
                ClsClass.Cls _Cls4=_Cls3.ClsList_1.get(j);
                if (j==0) _Cls4.Str_10=_Cls3.Str_1;
                CJFList.add(_Cls4);
            }
        }

        widget_21.setAdapter(new MyListAdapter2());
        widget_21.setMode(PullToRefreshBase.Mode.DISABLED);
        widget_21.getAndSetTotalHeight(0);

        UpdateInfo();
	}

    @Override
    protected void onStart() {
        super.onStart();
        UpdateInfo();
    }

	void UpdateInfo(){

	    if (NowIndex>=_Cls.ClsList_1.size()) NowIndex=0;

        ((TextView)widget_1.getChildAt(1)).setText(_Cls.Str_1);
        ((TextView)widget_2.getChildAt(1)).setText(Config.GetProjectTypeName(_Cls.Int_2));
        ((TextView)widget_3.getChildAt(1)).setText(_Cls.Str_2);
        ((TextView)widget_4.getChildAt(1)).setText(_Cls.Str_5);
        ((TextView)widget_5.getChildAt(1)).setText(_Cls.Str_4);
        ((TextView)widget_6.getChildAt(1)).setText(_Cls.Str_9);
        ((TextView)widget_7.getChildAt(0)).setText("共有"+_Cls.ClsList_1.size()+"个项目，当前第"+(_Cls.ClsList_1.size()<=0?0:(NowIndex+1))+"个");

        ClsClass.Cls _Cls2=_Cls.ClsList_1.get(NowIndex);
        ((TextView)widget_8.getChildAt(1)).setText(_Cls2.Str_3);
        ((TextView)widget_9.getChildAt(1)).setText(_Cls2.Str_2);
        ((TextView)widget_10.getChildAt(1)).setText(_Cls2.Str_4);
        ((TextView)widget_11.getChildAt(1)).setText(_Cls2.Str_6);
        ((TextView)widget_12.getChildAt(1)).setText(_Cls2.Str_7);
        for (int i=1;i<widget_13.getChildCount();i++){
            ((ImageView)widget_13.getChildAt(i)).setVisibility(View.INVISIBLE);
        }
        for (int i=0;i<_Cls2.Int_3;i++){
            if (i+1>=widget_13.getChildCount()) break;
            ((ImageView)widget_13.getChildAt(i+1)).setVisibility(View.VISIBLE);
        }
        ((TextView)widget_14.getChildAt(1)).setText(_Cls2.Str_8);
        ((TextView)widget_15.getChildAt(1)).setText(_Cls2.Str_9);
        ((TextView)widget_16.getChildAt(1)).setText(_Cls2.Int_4+"天");
        ((TextView)widget_17.getChildAt(1)).setText(_Cls2.Str_10);

        widget_19.setAdapter(new MyListAdapter(_Cls2.ClsList_2));
        widget_19.setMode(PullToRefreshBase.Mode.DISABLED);
        widget_19.getAndSetTotalHeight(0);
    }
	
	@Override
	protected void onDestroy() {
		IsDestroy=true;
		super.onDestroy();

		if (IsCancel){
		    String IDStr="";
		    for (int i=0;i<_Cls.ClsList_1.size();i++){
		        if (!IDStr.equals("")) IDStr+=",";
                IDStr+=String.valueOf(_Cls.ClsList_1.get(i).Int_1);
            }
            String ParmStr="?token="+ Common.UrlEncoded(UserInfo.Token);
            ParmStr+="&items="+ Common.UrlEncoded(IDStr);
            new HttpCls2(ThisContext,null,"",0,"", Config.ServiceUrl+"index.php/api/project/delProject"+ParmStr,"Get",null,10).Begin();
        }
	}

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState); //实现父类方法 放在最后 防止拍照后无法返回当前activity
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    IsCancel=false;
                    finish();
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }



    class ViewHolder
    {
        public LinearLayout item_widget_1,item_widget_2;
        public PullToRefreshListView item_widget_3;
    }

    class MyListAdapter extends BaseAdapter {
        LayoutInflater mInflater;
        List<ClsClass.Cls> ItemList=new ArrayList<ClsClass.Cls>();

        MyListAdapter(List<ClsClass.Cls> _ItemList) {
            mInflater = LayoutInflater.from(ThisContext);
            ItemList=_ItemList;
        }

        @Override
        public int getCount() { return ItemList.size(); }

        @Override
        public Object getItem(int arg0) { return ItemList.get(arg0); }

        @Override
        public long getItemId(int position) { return position; }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null)
            {
                convertView = mInflater.inflate(R.layout.item_xm_view, null);
                holder = new ViewHolder();
                holder.item_widget_1 = (LinearLayout) convertView.findViewById(R.id.item_widget_1);
                holder.item_widget_2 = (LinearLayout) convertView.findViewById(R.id.item_widget_2);
                holder.item_widget_3 = (PullToRefreshListView) convertView.findViewById(R.id.item_widget_3);
                convertView.setTag(holder);
            }
            else
            {
                holder = (ViewHolder)convertView.getTag();
            }
            ClsClass.Cls _Cls3 = ItemList.get(position);

            ((ImageView) holder.item_widget_1.getChildAt(0)).setVisibility(View.INVISIBLE);
            ((TextView) holder.item_widget_1.getChildAt(1)).setText(_Cls3.Str_1);
            ((ImageView) holder.item_widget_2.getChildAt(0)).setVisibility(View.INVISIBLE);
            ((TextView) holder.item_widget_2.getChildAt(1)).setText("类型："+_Cls3.Str_3);
            ((TextView) holder.item_widget_2.getChildAt(3)).setText("工作量："+String.valueOf(_Cls3.Int_4)+_Cls3.Str_4);
            ((ImageView) holder.item_widget_2.getChildAt(5)).setVisibility(View.GONE);
            holder.item_widget_3.setVisibility(View.GONE);

            return convertView;
        }
    }

    class ViewHolder2
    {
        public LinearLayout item_widget_1,item_widget_2,item_widget_3,item_widget_4,item_widget_5;
        public TextView item_widget_0;
    }

    class MyListAdapter2 extends BaseAdapter {
        LayoutInflater mInflater;

        MyListAdapter2() {
            mInflater = LayoutInflater.from(ThisContext);
        }

        @Override
        public int getCount() { return CJFList.size(); }

        @Override
        public Object getItem(int arg0) { return CJFList.get(arg0); }

        @Override
        public long getItemId(int position) { return position; }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder2 holder;
            if (convertView == null)
            {
                convertView = mInflater.inflate(R.layout.item_cjf, null);
                holder = new ViewHolder2();
                holder.item_widget_0 = (TextView) convertView.findViewById(R.id.item_widget_0);
                holder.item_widget_1 = (LinearLayout) convertView.findViewById(R.id.item_widget_1);
                holder.item_widget_2 = (LinearLayout) convertView.findViewById(R.id.item_widget_2);
                holder.item_widget_3 = (LinearLayout) convertView.findViewById(R.id.item_widget_3);
                holder.item_widget_4 = (LinearLayout) convertView.findViewById(R.id.item_widget_4);
                holder.item_widget_5 = (LinearLayout) convertView.findViewById(R.id.item_widget_5);
                convertView.setTag(holder);
            }
            else
            {
                holder = (ViewHolder2)convertView.getTag();
            }
            ClsClass.Cls _Cls3 = CJFList.get(position);

            holder.item_widget_0.setVisibility(_Cls3.Str_10.equals("")?View.GONE:View.VISIBLE);
            holder.item_widget_0.setText(_Cls3.Str_10);
            ((TextView) holder.item_widget_1.getChildAt(1)).setText(_Cls3.Str_1);
            ((TextView) holder.item_widget_1.getChildAt(3)).setText(_Cls3.Str_2);
            ((TextView) holder.item_widget_2.getChildAt(1)).setText(_Cls3.Str_3);
            ((TextView) holder.item_widget_2.getChildAt(3)).setText(_Cls3.Str_4);
            ((TextView) holder.item_widget_3.getChildAt(1)).setText(_Cls3.Str_5);
            ((TextView) holder.item_widget_4.getChildAt(1)).setText(_Cls3.Str_6);
            ((TextView) holder.item_widget_5.getChildAt(1)).setText(_Cls3.Str_7);

            return convertView;
        }
    }
}
