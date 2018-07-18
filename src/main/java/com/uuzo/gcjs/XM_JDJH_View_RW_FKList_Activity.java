package com.uuzo.gcjs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import java.util.List;


public class XM_JDJH_View_RW_FKList_Activity extends Activity {
    Boolean IsDestroy=false;
    Context ThisContext;
    Activity ThisActivity;

    TextView app_title_center,app_title_right2;
    ImageView app_title_left,app_title_right;


    PullToRefreshListView _ListView;
    List<ClsClass.Cls> _List=new ArrayList<>();
    MyListAdapter _MyListAdapter;

    ClsClass.Cls _Cls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xm_jdjh_view_rw_fklist);
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
        app_title_right = (ImageView)findViewById(R.id.app_title_right);
        app_title_right2 = (TextView) findViewById(R.id.app_title_right2);
        app_title_right.setVisibility(View.GONE);
        app_title_right2.setVisibility(View.GONE);
        app_title_center.setText("反馈记录");
        app_title_left.setImageResource(R.drawable.back);
        app_title_left.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        _ListView = (PullToRefreshListView) findViewById(R.id.widget_0);

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
        if (_List.size()==0) _ListView.setBackgroundResource(R.drawable.listviewloadingbg);

        String ParmStr="?token="+ Common.UrlEncoded(UserInfo.Token);
        ParmStr+="&work_id="+_Cls.Int_1;
        new HttpCls2(ThisContext,HttpHandler,"list",0,"", Config.ServiceUrl+"index.php/api/progress/getOneWorkfeedBacks"+ParmStr,"Get",null,10).Begin();
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
                            JSONArray _JSONArray= _JSONObject.getJSONArray("content");
                            if (_JSONArray.length()>0)
                            {
                                for (int i=0;i<_JSONArray.length();i++){
                                    JSONObject JSONObject2=_JSONArray.getJSONObject(i);
                                    ClsClass.Cls _Cls=new ClsClass().new Cls();
                                    _Cls.Int_1=JSONObject2.getInt("id");
                                    _Cls.Int_2=JSONObject2.getInt("wid");
                                    _Cls.Int_3=JSONObject2.getInt("loads");
                                    _Cls.Int_4=JSONObject2.getInt("plan");
                                    _Cls.Str_1=JSONObject2.getString("time");
                                    _Cls.Str_2=JSONObject2.getString("uid");
                                    _List.add(_Cls);
                                }
                            }
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
                    if (_List.size()>0) _ListView.setBackgroundResource(0);
                    else _ListView.setBackgroundResource(IsOK? R.drawable.listviewemptybg: R.drawable.listviewrefreshbg);
                } catch (Exception e) { }
            }
        }
    };

    class ViewHolder
    {
        public LinearLayout item_widget_1;
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
                convertView = mInflater.inflate(R.layout.item4, null);
                holder = new ViewHolder();
                holder.item_widget_1 = (LinearLayout) convertView.findViewById(R.id.item_widget_1);
                convertView.setTag(holder);
            }
            else
            {
                holder = (ViewHolder)convertView.getTag();
            }
            ClsClass.Cls _Cls2 = _List.get(position);
            ((TextView)holder.item_widget_1.getChildAt(0)).setText(Common.DateToStr(Common.StrToDate(_Cls2.Str_1,"yyyy-MM-dd HH:mm:ss"),"MM-dd"));
            ((TextView)holder.item_widget_1.getChildAt(1)).setText(_Cls2.Str_2);
            ((TextView)holder.item_widget_1.getChildAt(2)).setText(String.valueOf(_Cls2.Int_3)+_Cls.Str_4);
            ((TextView)holder.item_widget_1.getChildAt(3)).setText(String.valueOf(_Cls2.Int_4)+"%");

            return convertView;
        }
    }
}
