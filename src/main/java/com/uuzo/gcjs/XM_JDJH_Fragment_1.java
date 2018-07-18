package com.uuzo.gcjs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class XM_JDJH_Fragment_1 extends Fragment
{
	Boolean IsDestroy=false;
	Context ThisContext;
    Activity ThisActivity;

    EditText widget_1;
    String SearchContent="";

    PullToRefreshListView _ListView;
    List<ClsClass.Cls> _List=new ArrayList<>();
    MyListAdapter _MyListAdapter;

    int FunType=0;

	public XM_JDJH_Fragment_1(){}
    public static Fragment newInstance(int FunType){
        XM_JDJH_Fragment_1 fragment = new XM_JDJH_Fragment_1();
        Bundle bundle = new Bundle();
        bundle.putInt("FunType", FunType);
        fragment.setArguments(bundle);
        return fragment;
    }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_list_search, container, false);
		
		IsDestroy=false;
        ThisContext=getContext();
        ThisActivity=getActivity();

        FunType=getArguments().getInt("FunType",0);

        _ListView = (PullToRefreshListView) view.findViewById(R.id.widget_0);
        widget_1 = (EditText) view.findViewById(R.id.widget_1);

        widget_1.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId== EditorInfo.IME_ACTION_SEARCH || (event!=null && event.getKeyCode()== KeyEvent.KEYCODE_ENTER))
                {
                    SearchContent=widget_1.getText().toString().trim().toLowerCase();
                    SearchContentGo();
                    Common.HideSoftInput(ThisActivity);
                    return true;
                }
                return false;
            }
        });

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
                if (position<1 || position>_MyListAdapter.list.size()) return;
                ClsClass.Cls _Cls=_MyListAdapter.list.get(position-1);
                if (_Cls.Int==1 || _Cls.Int==2) {
                    startActivity(new Intent(ThisContext, XM_JDJH_View_Activity.class).putExtra("ID", _Cls.Int_1).putExtra("Type", _Cls.Int).putExtra("FunType",FunType));
                }
                else if (_Cls.Int==3) {
                    Config.NowCls = _Cls;
                    startActivity(new Intent(ThisContext, RW_View_Activity.class).putExtra("IsAPZXR",true));
                }
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
        if (_MyListAdapter.list.size()==0) _ListView.setBackgroundResource(R.drawable.listviewloadingbg);

        String ParmStr="?token="+ Common.UrlEncoded(UserInfo.Token);
        new HttpCls2(ThisContext,HttpHandler,"list",0,"", Config.ServiceUrl+"index.php/api/project/getOwnProject"+ParmStr,"Get",null,10).Begin();
    }

    void SearchContentGo(){
        if (SearchContent.equals("")) _MyListAdapter.list=_List;
        else{
            _MyListAdapter.list=new ArrayList<>();
            for(int i=0;i<_List.size();i++){
                ClsClass.Cls _Cls=_List.get(i);

                if (_Cls.Int==1 && (_Cls.Str_1.toLowerCase().indexOf(SearchContent)>-1
                        || _Cls.Str_4.toLowerCase().indexOf(SearchContent)>-1
                        || Config.GetProjectTypeName(_Cls.Int_2).toLowerCase().indexOf(SearchContent)>-1
                        || _Cls.Str_3.toLowerCase().indexOf(SearchContent)>-1
                )) {
                    _MyListAdapter.list.add(_Cls);
                }
                else if (_Cls.Int==2 && (_Cls.Str_2.toLowerCase().indexOf(SearchContent)>-1
                        || _Cls.Str_3.toLowerCase().indexOf(SearchContent)>-1
                        || _Cls.Str_4.toLowerCase().indexOf(SearchContent)>-1
                )) {
                    _MyListAdapter.list.add(_Cls);
                }
                else if (_Cls.Int==3 && (_Cls.Str_1.toLowerCase().indexOf(SearchContent)>-1
                        || _Cls.Str_2.toLowerCase().indexOf(SearchContent)>-1
                        || _Cls.Str_3.toLowerCase().indexOf(SearchContent)>-1
                )) {
                    _MyListAdapter.list.add(_Cls);
                }
            }
        }
        _MyListAdapter.notifyDataSetChanged();
        if (_ListView.isRefreshing()) _ListView.onRefreshComplete();
        if (_MyListAdapter.list.size()>0) _ListView.setBackgroundResource(0);
        else _ListView.setBackgroundResource(R.drawable.listviewemptybg);
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
                            JSONObject _JSONObject2= _JSONObject.getJSONObject("content");
                            JSONArray Project_JSONArray=_JSONObject2.getJSONArray("project");
                            JSONArray Item_JSONArray=_JSONObject2.getJSONArray("item");
                            JSONArray Works_JSONArray=_JSONObject2.getJSONArray("works");
                            int Type=_JSONObject2.getInt("type");
                            if (Type==1)
                            {
                                for(int i=0;i<Project_JSONArray.length();i++) {
                                    JSONObject JSONObject2=Project_JSONArray.getJSONObject(i);
                                    ClsClass.Cls _Cls=new ClsClass().new Cls();
                                    _Cls.Int=Type;
                                    _Cls.Int_1=JSONObject2.getInt("id");
                                    _Cls.Str_1=JSONObject2.getString("project_name");
                                    _Cls.Int_2=JSONObject2.getInt("project_type");
                                    _Cls.Str_2=JSONObject2.getString("batch");
                                    _Cls.Str_3=JSONObject2.getString("bureau");
                                    _Cls.Str_4=JSONObject2.getString("project_number");
                                    _Cls.Str_5=JSONObject2.getString("project_addr");
                                    _Cls.Str_6=JSONObject2.getString("principal");
                                    _Cls.Str_7=JSONObject2.getString("start_time");
                                    _Cls.Str_8=JSONObject2.getString("end_time");
                                    _Cls.Str_9=JSONObject2.getString("action_unit");
                                    _Cls.Int_3=JSONObject2.getInt("plan");
                                    _Cls.Int_4=JSONObject2.getInt("submit_time");
                                    _Cls.Str_10=JSONObject2.getString("file_dir");
                                    _Cls.Str_11=JSONObject2.getString("work_dir");
                                    _Cls.Str_12=JSONObject2.getString("goods_dir");
                                    _Cls.Str_13=JSONObject2.getString("others_dir");
                                    _Cls.Int_5=JSONObject2.getInt("isread");
                                    _Cls.Int_6=JSONObject2.getInt("is_pass");
                                    _Cls.Int_7=JSONObject2.getInt("ready_pass");
                                    _Cls.Int_8=JSONObject2.getInt("localview");
                                    _List.add(_Cls);
                                }
                            }
                            else if (Type==2)
                            {
                                for(int i=0;i<Item_JSONArray.length();i++) {
                                    JSONObject JSONObject2=Item_JSONArray.getJSONObject(i);
                                    ClsClass.Cls _Cls2=new ClsClass().new Cls();
                                    _Cls2.Int=Type;
                                    _Cls2.Int_1=JSONObject2.getInt("id");
                                    _Cls2.Str_1=JSONObject2.getString("initiator");
                                    _Cls2.Int_2=JSONObject2.getInt("project_id");
                                    _Cls2.Str_2=JSONObject2.getString("item_name");
                                    _Cls2.Str_3=JSONObject2.getString("item_number");
                                    _Cls2.Str_4=JSONObject2.getString("item_type");
                                    _Cls2.Str_5=JSONObject2.getString("principal");
                                    _Cls2.Str_6=JSONObject2.getString("area");
                                    _Cls2.Str_7=JSONObject2.getString("addr");
                                    _Cls2.Int_3=JSONObject2.getInt("trouble");
                                    _Cls2.Str_8=JSONObject2.getString("start_time");
                                    _Cls2.Str_9=JSONObject2.getString("end_time");
                                    _Cls2.Int_4=JSONObject2.getInt("work_day");
                                    _Cls2.Int_5=JSONObject2.getInt("plan");
                                    _Cls2.Str_10=JSONObject2.getString("situation");
                                    _Cls2.Str_11=JSONObject2.getString("note");
                                    _Cls2.Str_12=JSONObject2.getString("matters");
                                    _Cls2.Str_13=JSONObject2.getString("accessory");
                                    _Cls2.Int_6=JSONObject2.getInt("status");
                                    _Cls2.Int_7=JSONObject2.getInt("is_pass");
                                    _Cls2.Int_8=JSONObject2.getInt("ready_pass");
                                    _Cls2.Int_9=JSONObject2.getInt("is_change");
                                    _Cls2.Int_10=JSONObject2.getInt("is_archive");
                                    _Cls2.Int_11=JSONObject2.getInt("is_delete");
                                    try {
                                        JSONArray fuzeren_JSONArray = JSONObject2.getJSONArray("fuzeren");//有些item里的fuzeren为数组，有些为0
                                        for (int j = 0; j < fuzeren_JSONArray.length(); j++) {
                                            JSONObject fuzeren_JSONObject = fuzeren_JSONArray.getJSONObject(j);
                                            ClsClass.Cls _Cls3 = new ClsClass().new Cls();
                                            _Cls3.Str_1 = fuzeren_JSONObject.getString("user_name");
                                            _Cls3.Str_2 = fuzeren_JSONObject.getString("tel");
                                            _Cls2.ClsList_1.add(_Cls3);
                                        }
                                    }
                                    catch (Exception e2){}
                                    JSONArray works_JSONArray=JSONObject2.getJSONArray("works");
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
                                        JSONArray zhixingren_JSONArray=works_JSONObject.getJSONArray("zhixingren");
                                        for(int k=0;k<zhixingren_JSONArray.length();k++) {
                                            JSONObject zhixingren_JSONObject = zhixingren_JSONArray.getJSONObject(k);
                                            ClsClass.Cls _Cls4=new ClsClass().new Cls();
                                            _Cls4.Str_1=zhixingren_JSONObject.getString("user_name");
                                            _Cls4.Str_2=zhixingren_JSONObject.getString("tel");
                                            _Cls3.ClsList_1.add(_Cls4);
                                        }
                                        _Cls2.ClsList_2.add(_Cls3);
                                    }
                                    _Cls2.Boolean_1=false;
                                    _List.add(_Cls2);
                                }
                            }
                            else if (Type==3)
                            {
                                for(int j=0;j<Works_JSONArray.length();j++) {
                                    JSONObject works_JSONObject = Works_JSONArray.getJSONObject(j);
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
                                    JSONArray zhixingren_JSONArray=works_JSONObject.getJSONArray("zhixingren");
                                    for(int k=0;k<zhixingren_JSONArray.length();k++) {
                                        JSONObject zhixingren_JSONObject = zhixingren_JSONArray.getJSONObject(k);
                                        ClsClass.Cls _Cls4=new ClsClass().new Cls();
                                        _Cls4.Str_1=zhixingren_JSONObject.getString("user_name");
                                        _Cls4.Str_2=zhixingren_JSONObject.getString("tel");
                                        _Cls3.ClsList_1.add(_Cls4);
                                    }
                                    _List.add(_Cls3);
                                }
                            }
                            else {

                            }
                        }
                    } catch (Exception e) { }
                    SearchContentGo();
                } catch (Exception e) { }
            }
        }
    };

    class ViewHolder
    {
        public LinearLayout item_widget_1,item_widget_2,item_widget_3,item_widget_4;
    }

    class MyListAdapter extends BaseAdapter {
        LayoutInflater mInflater;
        public List<ClsClass.Cls> list=new ArrayList<>();

        MyListAdapter() { mInflater = LayoutInflater.from(ThisContext); }

        @Override
        public int getCount() { return list.size(); }

        @Override
        public Object getItem(int arg0) { return list.get(arg0); }

        @Override
        public long getItemId(int position) { return position; }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null)
            {
                convertView = mInflater.inflate(R.layout.item_xm, null);
                holder = new ViewHolder();
                holder.item_widget_1 = (LinearLayout) convertView.findViewById(R.id.item_widget_1);
                holder.item_widget_2 = (LinearLayout)convertView.findViewById(R.id.item_widget_2);
                holder.item_widget_3 = (LinearLayout)convertView.findViewById(R.id.item_widget_3);
                holder.item_widget_4 = (LinearLayout)convertView.findViewById(R.id.item_widget_4);
                convertView.setTag(holder);
            }
            else
            {
                holder = (ViewHolder)convertView.getTag();
            }
            ClsClass.Cls _Cls = list.get(position);

            if (_Cls.Int==1) {
                ((TextView) holder.item_widget_1.getChildAt(0)).setText("工程名称：");
                ((TextView) holder.item_widget_1.getChildAt(1)).setText(_Cls.Str_1);
                ((TextView) holder.item_widget_2.getChildAt(0)).setText("工程编号：");
                ((TextView) holder.item_widget_2.getChildAt(1)).setText(_Cls.Str_4);
                ((TextView) holder.item_widget_3.getChildAt(0)).setText("工程类别：");
                ((TextView) holder.item_widget_3.getChildAt(1)).setText(Config.GetProjectTypeName(_Cls.Int_2));
                ((TextView) holder.item_widget_3.getChildAt(2)).setText("进度："+_Cls.Int_3+"%");
                ((TextView) holder.item_widget_4.getChildAt(0)).setText("建设单位：");
                ((TextView) holder.item_widget_4.getChildAt(1)).setText(_Cls.Str_3);
            }
            else if (_Cls.Int==2) {
                ((TextView) holder.item_widget_1.getChildAt(0)).setText("项目名称：");
                ((TextView) holder.item_widget_1.getChildAt(1)).setText(_Cls.Str_2);
                ((TextView) holder.item_widget_2.getChildAt(0)).setText("项目编号：");
                ((TextView) holder.item_widget_2.getChildAt(1)).setText(_Cls.Str_3);
                ((TextView) holder.item_widget_3.getChildAt(0)).setText("项目类型：");
                ((TextView) holder.item_widget_3.getChildAt(1)).setText(_Cls.Str_4);
                ((TextView) holder.item_widget_3.getChildAt(2)).setText(Config.GetStatusName(_Cls.Int_6,_Cls.Int_5));
                ((TextView) holder.item_widget_4.getChildAt(0)).setText("项目时间：");
                ((TextView) holder.item_widget_4.getChildAt(1)).setText(_Cls.Str_8+" 至 "+_Cls.Str_9);
            }
            else if (_Cls.Int==3) {
                ((TextView) holder.item_widget_1.getChildAt(0)).setText("任务名称：");
                ((TextView) holder.item_widget_1.getChildAt(1)).setText(_Cls.Str_1);
                ((TextView) holder.item_widget_2.getChildAt(0)).setText("任务编号：");
                ((TextView) holder.item_widget_2.getChildAt(1)).setText(_Cls.Str_2);
                ((TextView) holder.item_widget_3.getChildAt(0)).setText("任务类型：");
                ((TextView) holder.item_widget_3.getChildAt(1)).setText(_Cls.Str_3);
                ((TextView) holder.item_widget_3.getChildAt(2)).setText(Config.GetStatusName(_Cls.Int_9,_Cls.Int_6));
                ((TextView) holder.item_widget_4.getChildAt(0)).setText("任务时间：");
                ((TextView) holder.item_widget_4.getChildAt(1)).setText(_Cls.Str_10+" 至 "+_Cls.Str_11);
            }
            return convertView;
        }
    }
}
