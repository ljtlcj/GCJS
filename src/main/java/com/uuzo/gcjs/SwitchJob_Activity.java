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

public class SwitchJob_Activity extends AppCompatActivity {
    Boolean IsDestroy=false;
    Context ThisContext;
    Activity ThisActivity;

    TextView app_title_center,app_title_right2;
    ImageView app_title_left,app_title_right;


    PullToRefreshListView _ListView;
    List<ClsClass.Cls> _List=new ArrayList<>();
    MyListAdapter _MyListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Config.SetStatusBar(this);


        IsDestroy=false;
        ThisContext=this;
        ThisActivity=this;

        if (!UserInfo.IsLogin()){
            finish();
            return;
        }

        app_title_center = (TextView)findViewById(R.id.app_title_center);
        app_title_left = (ImageView)findViewById(R.id.app_title_left);
        app_title_right = (ImageView) findViewById(R.id.app_title_right);
        app_title_right2 = (TextView)findViewById(R.id.app_title_right2);
        app_title_right.setVisibility(View.GONE);
        app_title_right2.setVisibility(View.GONE);
        app_title_center.setText("切换角色");
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

                String ParmStr="?token="+ Common.UrlEncoded(UserInfo.Token);
                ParmStr+="&user_id="+_Cls.Int_1;
                new HttpCls2(ThisContext,HttpHandler,"set",0,"正在切换...", Config.ServiceUrl+"index.php/api/job/changeJob"+ParmStr,"Get",null,10).Begin();
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
        new HttpCls2(ThisContext,HttpHandler,"list",0,"", Config.ServiceUrl+"index.php/api/job/getMyJob"+ParmStr,"Get",null,10).Begin();
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
                                    _Cls.Str_1=JSONObject2.getString("department");
                                    _Cls.Str_2=JSONObject2.getString("job");
                                    _Cls.Int_2=JSONObject2.getInt("job_info");
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
            else if (FunName.equals("set")) {
                try {
                    JSONObject _JSONObject = new JSONObject(msg.getData().getString("ReturnValue"));
                    if (_JSONObject.getBoolean("status")) {
                        UserInfo.Login(ThisContext,_JSONObject.getJSONObject("content"));
                        Common.DisplayToast("切换成功");
                        finish();
                    } else {
                        new MessageBox().Show(ThisContext, getString(R.string.Tip), _JSONObject.getString("content"), "", getString(R.string.OK));
                    }
                    return;
                } catch (Exception e) { }
                new MessageBox().Show(ThisContext, getString(R.string.Tip), getString(R.string.NetErr), "", getString(R.string.OK));
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
                convertView = mInflater.inflate(R.layout.item2, null);
                holder = new ViewHolder();
                holder.item_widget_1 = (LinearLayout)convertView.findViewById(R.id.item_widget_1);
                convertView.setTag(holder);
            }
            else
            {
                holder = (ViewHolder)convertView.getTag();
            }
            ClsClass.Cls _Cls = _List.get(position);
            ((TextView)holder.item_widget_1.getChildAt(0)).setText(_Cls.Str_1);
            ((TextView)holder.item_widget_1.getChildAt(1)).setText(_Cls.Str_2);

            return convertView;
        }
    }
}
