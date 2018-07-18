package com.uuzo.gcjs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TYSQ_Fragment_2 extends Fragment
{
	Boolean IsDestroy=false;
	Context ThisContext;
    Activity ThisActivity;

    PullToRefreshListView _ListView;
    List<ClsClass.Cls> _List=new ArrayList<>();
    MyListAdapter _MyListAdapter;

	public TYSQ_Fragment_2()
	{
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_list, container, false);
		
		IsDestroy=false;
        ThisContext=getContext();
        ThisActivity=getActivity();

        _ListView = (PullToRefreshListView) view.findViewById(R.id.widget_0);

        _MyListAdapter = new MyListAdapter();
        _ListView.setAdapter(_MyListAdapter);
        _ListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        _ListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                Load();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

            }
        });
        _ListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position<1 || position>_List.size()) return;
                ClsClass.Cls _Cls=_List.get(position-1);
                Config.NowCls = _Cls;
                startActivity(new Intent(ThisContext, TYSQ_View_Activity.class));
            }
        });

        Load();

		return view;
	}

    @Override
    public void onStart() {
        super.onStart();
        _MyListAdapter.notifyDataSetChanged();
    }

	@Override
	public void onDestroyView() {
		IsDestroy=true;
		super.onDestroyView();		
	}

    public void Load() {
        if (_List.size()==0) _ListView.setBackgroundResource(R.drawable.listviewloadingbg);

        Bundle _Bundle=new Bundle();
        _Bundle.putString("ReturnValue","{\"status\":true,\"content\":[]}");
        Message _Message=HttpHandler.obtainMessage(0,"list");
        _Message.setData(_Bundle);
        HttpHandler.sendMessageDelayed(_Message,1000);
//        String ParmStr="?token="+ Common.UrlEncoded(UserInfo.Token);
//        new HttpCls2(ThisContext,HttpHandler,"list",0,"",Config.ServiceUrl+"index.php/api/apply/showApply"+ParmStr,"Get",null,10).Begin();
    }

    @SuppressLint("HandlerLeak")
    Handler HttpHandler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            if (IsDestroy || msg.obj == null) return;
            String FunName = msg.obj.toString();
            if (FunName.equals("list")) {
                try
                {
                    Boolean IsOK=false;
                    try
                    {
                        String ReturnValue = msg.getData().getString("ReturnValue");
                        JSONObject _JSONObject = new JSONObject(ReturnValue);
                        if (_JSONObject.getBoolean("status")) {
                            IsOK=true;
                            _List.clear();

                            JSONArray _JSONArray=_JSONObject.getJSONArray("content");

                            for(int i=0;i<_JSONArray.length();i++) {
                                JSONObject JSONObject2 = _JSONArray.getJSONObject(i);
                                int Type=JSONObject2.getInt("apply_type");
                                JSONObject Project_JSONObject=JSONObject2.getJSONObject("project");
                                JSONObject Item_JSONObject=JSONObject2.getJSONObject("item");
                                JSONArray person_JSONArray=JSONObject2.getJSONArray("person");

                                ClsClass.Cls _Cls=new ClsClass().new Cls();
                                _Cls.Int=1;
                                _Cls.Int_1=Project_JSONObject.getInt("id");
                                _Cls.Str_1=Project_JSONObject.getString("project_name");
                                _Cls.Int_2=Project_JSONObject.getInt("project_type");
                                _Cls.Str_2=Project_JSONObject.getString("batch");
                                _Cls.Str_3=Project_JSONObject.getString("bureau");
                                _Cls.Str_4=Project_JSONObject.getString("project_number");
                                _Cls.Str_5=Project_JSONObject.getString("project_addr");
                                _Cls.Str_6=Project_JSONObject.getString("principal");
                                _Cls.Str_7=Project_JSONObject.getString("start_time");
                                _Cls.Str_8=Project_JSONObject.getString("end_time");
                                _Cls.Str_9=Project_JSONObject.getString("action_unit");
                                _Cls.Int_3=Project_JSONObject.getInt("plan");
                                _Cls.Int_4=Project_JSONObject.getInt("submit_time");
                                _Cls.Str_10=Project_JSONObject.getString("file_dir");
                                _Cls.Str_11=Project_JSONObject.getString("work_dir");
                                _Cls.Str_12=Project_JSONObject.getString("goods_dir");
                                _Cls.Str_13=Project_JSONObject.getString("others_dir");
                                _Cls.Int_5=Project_JSONObject.getInt("isread");
                                _Cls.Int_6=Project_JSONObject.getInt("is_pass");
                                _Cls.Int_7=Project_JSONObject.getInt("ready_pass");
                                _Cls.Int_8=Project_JSONObject.getInt("localview");

                                ClsClass.Cls _Cls2=new ClsClass().new Cls();
                                _Cls2.Int=1;
                                _Cls2.Int_1=Item_JSONObject.getInt("id");
                                _Cls2.Str_1=Item_JSONObject.getString("initiator");
                                _Cls2.Int_2=Item_JSONObject.getInt("project_id");
                                _Cls2.Str_2=Item_JSONObject.getString("item_name");
                                _Cls2.Str_3=Item_JSONObject.getString("item_number");
                                _Cls2.Str_4=Item_JSONObject.getString("item_type");
                                _Cls2.Str_5=Item_JSONObject.getString("principal");
                                _Cls2.Str_6=Item_JSONObject.getString("area");
                                _Cls2.Str_7=Item_JSONObject.getString("addr");
                                _Cls2.Int_3=Item_JSONObject.getInt("trouble");
                                _Cls2.Str_8=Item_JSONObject.getString("start_time");
                                _Cls2.Str_9=Item_JSONObject.getString("end_time");
                                _Cls2.Int_4=Item_JSONObject.getInt("work_day");
                                _Cls2.Int_5=Item_JSONObject.getInt("plan");
                                _Cls2.Str_10=Item_JSONObject.getString("situation");
                                _Cls2.Str_11=Item_JSONObject.getString("note");
                                _Cls2.Str_12=Item_JSONObject.getString("matters");
                                _Cls2.Str_13=Item_JSONObject.getString("accessory");
                                _Cls2.Int_6=Item_JSONObject.getInt("status");
                                _Cls2.Int_7=Item_JSONObject.getInt("is_pass");
                                _Cls2.Int_8=Item_JSONObject.getInt("ready_pass");
                                _Cls2.Int_9=Item_JSONObject.getInt("is_change");
                                _Cls2.Int_10=Item_JSONObject.getInt("is_archive");
                                _Cls2.Int_11=Item_JSONObject.getInt("is_delete");
                                try {
                                    if (Item_JSONObject.has("fuzeren")) {
                                        JSONArray fuzeren_JSONArray = Item_JSONObject.getJSONArray("fuzeren");//有些item里的fuzeren为数组，有些为0
                                        for (int j = 0; j < fuzeren_JSONArray.length(); j++) {
                                            JSONObject fuzeren_JSONObject = fuzeren_JSONArray.getJSONObject(j);
                                            ClsClass.Cls _Cls3 = new ClsClass().new Cls();
                                            _Cls3.Str_1 = fuzeren_JSONObject.getString("user_name");
                                            _Cls3.Str_2 = fuzeren_JSONObject.getString("tel");
                                            _Cls2.ClsList_1.add(_Cls3);
                                        }
                                    }
                                }
                                catch (Exception e2){}
                                JSONArray works_JSONArray=Item_JSONObject.getJSONArray("works");
                                for(int j=0;j<works_JSONArray.length();j++) {
                                    JSONObject works_JSONObject = works_JSONArray.getJSONObject(j);
                                    ClsClass.Cls _Cls3=new ClsClass().new Cls();
                                    _Cls3.Int=3;
                                    _Cls3.Int_1=works_JSONObject.getInt("id");
                                    _Cls3.Int_2=works_JSONObject.getInt("project_id");
                                    _Cls3.Int_3=works_JSONObject.getInt("item_id");
                                    _Cls3.Str_1=works_JSONObject.getString("ass_name");
                                    _Cls3.Str_2=works_JSONObject.getString("ass_number");
                                    _Cls3.Str_3=works_JSONObject.getString("ass_type");
                                    _Cls3.Int_4=works_JSONObject.getInt("workload");
                                    _Cls3.Str_4=works_JSONObject.getString("unit");
                                    _Cls3.Str_5=works_JSONObject.getString("note");
                                    _Cls3.Str_6=works_JSONObject.getString("matters");
                                    _Cls3.Str_7=works_JSONObject.getString("matters2");
                                    _Cls3.Str_8=works_JSONObject.getString("accessory");
                                    _Cls3.Str_9=works_JSONObject.getString("accessory2");
                                    _Cls3.Str_10=works_JSONObject.getString("start_time");
                                    _Cls3.Str_11=works_JSONObject.getString("end_time");
                                    _Cls3.Int_5=works_JSONObject.getInt("work_day");
                                    _Cls3.Str_12=works_JSONObject.getString("cm");
                                    _Cls3.Str_13=works_JSONObject.getString("so");
                                    _Cls3.Str_14=works_JSONObject.getString("qc");
                                    _Cls3.Str_15=works_JSONObject.getString("executor");
                                    _Cls3.Int_6=works_JSONObject.getInt("plan");
                                    _Cls3.Int_7=works_JSONObject.getInt("car");
                                    _Cls3.Int_8=works_JSONObject.getInt("machine");
                                    _Cls3.Int_9=works_JSONObject.getInt("type");
                                    _Cls3.Int_10=works_JSONObject.getInt("safe_state");
                                    _Cls3.Int_11=works_JSONObject.getInt("is_change");
                                    _Cls3.Int_12=works_JSONObject.getInt("now_workload");
                                    _Cls3.Int_13=works_JSONObject.getInt("is_limit");
                                    try {
                                        if (works_JSONObject.has("works_JSONObject")) {
                                            JSONArray zhixingren_JSONArray = works_JSONObject.getJSONArray("zhixingren");
                                            for (int k = 0; k < zhixingren_JSONArray.length(); k++) {
                                                JSONObject zhixingren_JSONObject = zhixingren_JSONArray.getJSONObject(k);
                                                ClsClass.Cls _Cls4 = new ClsClass().new Cls();
                                                _Cls4.Str_1 = zhixingren_JSONObject.getString("user_name");
                                                _Cls4.Str_2 = zhixingren_JSONObject.getString("tel");
                                                _Cls3.ClsList_1.add(_Cls4);
                                            }
                                        }
                                    }
                                    catch (Exception e2){}
                                    _Cls2.ClsList_2.add(_Cls3);
                                }
                                _Cls.ClsList_1.add(_Cls2);


                                for(int j=0;j<person_JSONArray.length();j++) {
                                    JSONObject JSONObject9 = person_JSONArray.getJSONObject(j);
                                    ClsClass.Cls _Cls3 = new ClsClass().new Cls();
                                    _Cls3.Int_1 = JSONObject9.getInt("id");
                                    _Cls3.Int_2 = JSONObject9.getInt("project_id");
                                    _Cls3.Int_3 = JSONObject9.getInt("type");
                                    _Cls3.Str_1 = JSONObject9.getString("name");
                                    _Cls3.Str_2 = JSONObject9.getString("job");
                                    _Cls3.Str_3 = JSONObject9.getString("tel");
                                    _Cls3.Str_4= JSONObject9.getString("e_mail");
                                    _Cls3.Str_5= JSONObject9.getString("unit_name");
                                    _Cls3.Str_6= JSONObject9.getString("departents");
                                    _Cls3.Str_7= JSONObject9.getString("note");
                                    _Cls.ClsList_2.add(_Cls3);
                                }


                                _Cls.Int_15=Type;
                                _List.add(_Cls);
                            }
                        }
                    } catch (Exception e) { }
                    _MyListAdapter.notifyDataSetChanged();
                    if (_ListView.isRefreshing()) _ListView.onRefreshComplete();
                    if (_List.size()>0) _ListView.setBackgroundResource(0);
                    else _ListView.setBackgroundResource(IsOK? R.drawable.listviewemptybg: R.drawable.listviewrefreshbg);
                } catch (Exception e) { }
            }
        }
    };

    class ViewHolder
    {
        public LinearLayout item_widget_1,item_widget_2,item_widget_3;
    }

    class MyListAdapter extends BaseAdapter {
        LayoutInflater mInflater;

        MyListAdapter() { mInflater = LayoutInflater.from(ThisContext); }

        @Override
        public int getCount() { return _List.size(); }

        @Override
        public Object getItem(int arg0) { return _List.get(arg0); }

        @Override
        public long getItemId(int position) { return position; }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null)
            {
                convertView = mInflater.inflate(R.layout.item_tysq, null);
                holder = new ViewHolder();
                holder.item_widget_1 = (LinearLayout)convertView.findViewById(R.id.item_widget_1);
                holder.item_widget_2 = (LinearLayout)convertView.findViewById(R.id.item_widget_2);
                holder.item_widget_3 = (LinearLayout)convertView.findViewById(R.id.item_widget_3);
                convertView.setTag(holder);
            }
            else
            {
                holder = (ViewHolder)convertView.getTag();
            }
            ClsClass.Cls _Cls = _List.get(position);

            ((ImageView) holder.item_widget_1.getChildAt(0)).setImageResource(Config.GetTYSQTypeName(_Cls.Int_15).equals("未审批")? R.drawable.add3: R.drawable.add2);
            ((TextView) holder.item_widget_1.getChildAt(1)).setText(_Cls.ClsList_1.get(0).Str_2);
            ((TextView) holder.item_widget_1.getChildAt(1)).setText(_Cls.Str_1);
            ((TextView) holder.item_widget_2.getChildAt(1)).setText(Config.GetTYSQTypeName(_Cls.Int_15));

            Date Time= Common.StrToDate("1900-01-01","yyyy-MM-dd");
            try {
                Time.setTime(Long.valueOf(_Cls.Int_4) * 1000);
            } catch (Exception e1) {
                Time = Common.StrToDate("1900-01-01", "yyyy-MM-dd");
            }
            ((TextView) holder.item_widget_2.getChildAt(2)).setText(Common.DateToStr(Time,"yyyy-MM-dd HH:mm:ss"));

            return convertView;
        }
    }
}
