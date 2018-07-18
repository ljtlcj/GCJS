package com.uuzo.gcjs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.HeaderViewListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class XM_QD_View_Activity extends AppCompatActivity {
    Boolean IsDestroy=false;
    Context ThisContext;
    Activity ThisActivity;

    TextView app_title_center,app_title_right2;
    ImageView app_title_left,app_title_right;


    PullToRefreshListView _ListView;
    MyListAdapter _MyListAdapter;

    int ID=0;
    int Type=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Config.SetStatusBar(this);

        IsDestroy=false;
        ThisContext=this;
        ThisActivity=this;

        ID=getIntent().getIntExtra("ID",0);
        Type=getIntent().getIntExtra("Type",0);

        if (!UserInfo.IsLogin() || ID<=0 || Type<=0){
            finish();
            return;
        }

        app_title_center = (TextView) findViewById(R.id.app_title_center);
        app_title_left = (ImageView) findViewById(R.id.app_title_left);
        app_title_right = (ImageView) findViewById(R.id.app_title_right);
        app_title_right2 =(TextView)  findViewById(R.id.app_title_right2);
        app_title_right.setVisibility(View.GONE);
        app_title_right2.setVisibility(View.GONE);
        app_title_center.setText("清单");
        app_title_left.setImageResource(R.drawable.back);
        app_title_left.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        _ListView = (PullToRefreshListView)findViewById(R.id.widget_0);

        _MyListAdapter = new MyListAdapter(new ArrayList<ClsClass.Cls>());
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

        Load();
    }

    @Override
    protected void onStart() {
        super.onStart();
        _MyListAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        IsDestroy=true;
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void Load() {
        if (_MyListAdapter.ItemList.size()==0) _ListView.setBackgroundResource(R.drawable.listviewloadingbg);

        if (Type==1) {
            String ParmStr = "?token=" + Common.UrlEncoded(UserInfo.Token);
            ParmStr += "&project_id=" + ID;
            new HttpCls2(ThisContext, HttpHandler, "list", 0, "", Config.ServiceUrl + "index.php/api/project/getProject" + ParmStr, "Get", null, 10).Begin();
        }
        else if (Type==2) {
            String ParmStr = "?token=" + Common.UrlEncoded(UserInfo.Token);
            ParmStr += "&item_id=" + ID;
            new HttpCls2(ThisContext, HttpHandler, "list2", 0, "", Config.ServiceUrl + "index.php/api/project/getWorkbyItemId" + ParmStr, "Get", null, 10).Begin();
        }
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
                            _MyListAdapter.ItemList.clear();
                            JSONObject _JSONObject2= _JSONObject.getJSONObject("content");
                            JSONObject _JSONObject3=_JSONObject2.getJSONObject("project");

                            ClsClass.Cls _Cls=new ClsClass().new Cls();
                            _Cls.Int=1;
                            _Cls.Int_1=_JSONObject3.getInt("id");
                            _Cls.Str_1=_JSONObject3.getString("project_name");
                            _Cls.Int_2=_JSONObject3.getInt("project_type");
                            _Cls.Str_2=_JSONObject3.getString("batch");
                            _Cls.Str_3=_JSONObject3.getString("bureau");
                            _Cls.Str_4=_JSONObject3.getString("project_number");
                            _Cls.Str_5=_JSONObject3.getString("project_addr");
                            _Cls.Str_6=_JSONObject3.getString("principal");
                            _Cls.Str_7=_JSONObject3.getString("start_time");
                            _Cls.Str_8=_JSONObject3.getString("end_time");
                            _Cls.Str_9=_JSONObject3.getString("action_unit");
                            _Cls.Int_3=_JSONObject3.getInt("plan");
                            _Cls.Int_4=_JSONObject3.getInt("submit_time");
                            _Cls.Str_10=_JSONObject3.getString("file_dir");
                            _Cls.Str_11=_JSONObject3.getString("work_dir");
                            _Cls.Str_12=_JSONObject3.getString("goods_dir");
                            _Cls.Str_13=_JSONObject3.getString("others_dir");
                            _Cls.Int_5=_JSONObject3.getInt("isread");
                            _Cls.Int_6=_JSONObject3.getInt("is_pass");
                            _Cls.Int_7=_JSONObject3.getInt("ready_pass");
                            _Cls.Int_8=_JSONObject3.getInt("localview");
                            _Cls.Boolean_1=true;

                            JSONArray Item_JSONArray=_JSONObject2.getJSONArray("item");
                            for(int i=0;i<Item_JSONArray.length();i++) {
                                JSONObject JSONObject2=Item_JSONArray.getJSONObject(i);
                                ClsClass.Cls _Cls2=new ClsClass().new Cls();
                                _Cls2.Int=2;
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
                                _Cls2.Boolean_1=true;
                                _Cls.ClsList_1.add(_Cls2);
                            }

                            JSONArray person_JSONArray=_JSONObject2.getJSONArray("person");
                            for(int i=0;i<person_JSONArray.length();i++) {
                                JSONObject JSONObject2 = person_JSONArray.getJSONObject(i);
                                ClsClass.Cls _Cls2 = new ClsClass().new Cls();
                                _Cls2.Int_1 = JSONObject2.getInt("id");
                                _Cls2.Int_2 = JSONObject2.getInt("project_id");
                                _Cls2.Int_3 = JSONObject2.getInt("type");
                                _Cls2.Str_1 = JSONObject2.getString("name");
                                _Cls2.Str_2 = JSONObject2.getString("job");
                                _Cls2.Str_3 = JSONObject2.getString("tel");
                                _Cls2.Str_4= JSONObject2.getString("e_mail");
                                _Cls2.Str_5= JSONObject2.getString("unit_name");
                                _Cls2.Str_6= JSONObject2.getString("departents");
                                _Cls2.Str_7= JSONObject2.getString("note");
                                _Cls.ClsList_2.add(_Cls2);
                            }
                            _MyListAdapter.ItemList.add(_Cls);
                        }
                        else {
                            IsOK=true;
                            new MessageBox().Show(ThisContext, "提示", _JSONObject.getString("content"), "", getString(R.string.OK)).BtnClick = new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    if (whichButton == -1) {
                                        finish();
                                    }
                                }
                            };
                        }
                    } catch (Exception e) { }
                    _MyListAdapter.notifyDataSetChanged();
                    if (_ListView.isRefreshing()) _ListView.onRefreshComplete();
                    if (_MyListAdapter.ItemList.size()>0) _ListView.setBackgroundResource(0);
                    else _ListView.setBackgroundResource(IsOK? R.drawable.listviewemptybg: R.drawable.listviewrefreshbg);
                } catch (Exception e) { }
            }
            else if (FunName.equals("list2")) {
                try
                {
                    Boolean IsOK=false;
                    try
                    {
                        String ReturnValue = msg.getData().getString("ReturnValue");
                        JSONObject _JSONObject = new JSONObject(ReturnValue);
                        if (_JSONObject.getBoolean("status")) {
                            IsOK=true;
                            _MyListAdapter.ItemList.clear();
                            JSONObject JSONObject2= _JSONObject.getJSONObject("content");

                            ClsClass.Cls _Cls2=new ClsClass().new Cls();
                            _Cls2.Int=2;
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
                            _Cls2.Boolean_1=true;
                            _MyListAdapter.ItemList.add(_Cls2);
                        }
                        else {
                            IsOK=true;
                            new MessageBox().Show(ThisContext, "提示", _JSONObject.getString("content"), "", getString(R.string.OK)).BtnClick = new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    if (whichButton == -1) {
                                        finish();
                                    }
                                }
                            };
                        }
                    } catch (Exception e) { }
                    _MyListAdapter.notifyDataSetChanged();
                    if (_ListView.isRefreshing()) _ListView.onRefreshComplete();
                    if (_MyListAdapter.ItemList.size()>0) _ListView.setBackgroundResource(0);
                    else _ListView.setBackgroundResource(IsOK? R.drawable.listviewemptybg: R.drawable.listviewrefreshbg);
                } catch (Exception e) { }
            }
        }
    };

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
                holder.item_widget_1 = (LinearLayout)convertView.findViewById(R.id.item_widget_1);
                holder.item_widget_2 = (LinearLayout)convertView.findViewById(R.id.item_widget_2);
                holder.item_widget_3 = (PullToRefreshListView) convertView.findViewById(R.id.item_widget_3);
                convertView.setTag(holder);
            }
            else
            {
                holder = (ViewHolder)convertView.getTag();
            }
            ClsClass.Cls _Cls = ItemList.get(position);

            SetItemXMView(holder,_Cls);

            return convertView;
        }
    }

    void SetItemXMView(ViewHolder holder,ClsClass.Cls _Cls){
        if (_Cls.Int==1) {
            ((ImageView) holder.item_widget_1.getChildAt(0)).setImageResource(_Cls.Boolean_1 ? R.drawable.jian2 : R.drawable.jia2);
            ((ImageView) holder.item_widget_1.getChildAt(0)).setVisibility(_Cls.ClsList_1.size() > 0 ? View.VISIBLE : View.INVISIBLE);
            ((ImageView) holder.item_widget_1.getChildAt(0)).setTag(_Cls);
            ((ImageView) holder.item_widget_1.getChildAt(0)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClsClass.Cls _Cls = (ClsClass.Cls) v.getTag();
                    _Cls.Boolean_1 = !_Cls.Boolean_1;
                    if (!_Cls.Boolean_1) {
                        for (int i = 0; i < _Cls.ClsList_1.size(); i++) {
                            _Cls.ClsList_1.get(i).Boolean_1 = false;
                        }
                    }
                    _MyListAdapter.notifyDataSetChanged();
                }
            });
            ((TextView) holder.item_widget_1.getChildAt(1)).setText(_Cls.Str_1);
            ((ImageView) holder.item_widget_2.getChildAt(0)).setVisibility(View.INVISIBLE);
            ((TextView) holder.item_widget_2.getChildAt(4)).setText("进度："+_Cls.Int_3+"%");
            ((ImageView) holder.item_widget_2.getChildAt(5)).setVisibility(View.GONE);
            holder.item_widget_3.setAdapter(new MyListAdapter(_Cls.ClsList_1));
            holder.item_widget_3.setVisibility(_Cls.Boolean_1 ? View.VISIBLE : View.GONE);
            holder.item_widget_3.getAndSetTotalHeight(20);
        }
        else if (_Cls.Int==2){
            ((ImageView) holder.item_widget_1.getChildAt(0)).setImageResource(_Cls.Boolean_1? R.drawable.jian2: R.drawable.jia2);
            ((ImageView) holder.item_widget_1.getChildAt(0)).setVisibility(_Cls.ClsList_2.size()>0?View.VISIBLE:View.INVISIBLE);
            ((ImageView) holder.item_widget_1.getChildAt(0)).setTag(_Cls);
            ((ImageView) holder.item_widget_1.getChildAt(0)).setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    ClsClass.Cls _Cls = (ClsClass.Cls)v.getTag();
                    _Cls.Boolean_1=!_Cls.Boolean_1;
                    _MyListAdapter.notifyDataSetChanged();
                }
            });
            ((TextView) holder.item_widget_1.getChildAt(1)).setText(_Cls.Str_2);
            ((ImageView) holder.item_widget_2.getChildAt(0)).setVisibility(View.INVISIBLE);
            ((TextView) holder.item_widget_2.getChildAt(1)).setText("类别："+_Cls.Str_4);
            ((TextView) holder.item_widget_2.getChildAt(4)).setText(Config.GetStatusName(_Cls.Int_6,_Cls.Int_5));
            ((ImageView) holder.item_widget_2.getChildAt(5)).setTag(_Cls);
            ((ImageView) holder.item_widget_2.getChildAt(5)).setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    ClsClass.Cls _Cls = (ClsClass.Cls)v.getTag();
                    Config.NowCls=_Cls;
                    startActivity(new Intent(ThisContext, RW_Add_Activity.class));
                }
            });
            holder.item_widget_3.setAdapter(new MyListAdapter(_Cls.ClsList_2));
            holder.item_widget_3.setVisibility(_Cls.Boolean_1?View.VISIBLE:View.GONE);
            holder.item_widget_3.getAndSetTotalHeight(0);
            holder.item_widget_3.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    List<ClsClass.Cls> ItemList=((MyListAdapter)((HeaderViewListAdapter)parent.getAdapter()).getWrappedAdapter()).ItemList;
                    if (position<1 || position>ItemList.size()) return;
                    Config.NowCls=ItemList.get(position-1);
                    startActivity(new Intent(ThisContext, RW_View_Activity.class));
                }
            });
        }
        else if (_Cls.Int==3){
            ((ImageView) holder.item_widget_1.getChildAt(0)).setVisibility(View.INVISIBLE);
            ((TextView) holder.item_widget_1.getChildAt(1)).setText(_Cls.Str_1);
            ((ImageView) holder.item_widget_2.getChildAt(0)).setVisibility(View.INVISIBLE);
            ((TextView) holder.item_widget_2.getChildAt(1)).setText("工作量："+String.valueOf(_Cls.Int_4)+_Cls.Str_4);
            ((TextView) holder.item_widget_2.getChildAt(4)).setText(Config.GetStatusName(_Cls.Int_9,_Cls.Int_6));
            ((ImageView) holder.item_widget_2.getChildAt(5)).setVisibility(View.GONE);
            holder.item_widget_3.setVisibility(View.GONE);
        }

    }
}
