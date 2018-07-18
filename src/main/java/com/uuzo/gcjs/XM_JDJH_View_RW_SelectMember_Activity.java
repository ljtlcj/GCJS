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
import android.view.View.OnClickListener;
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

public class XM_JDJH_View_RW_SelectMember_Activity extends Activity {

	Boolean IsDestroy=false;
	Context ThisContext;
	Activity ThisActivity;

	TextView app_title_center,app_title_right2;
    ImageView app_title_left,app_title_right;

    PullToRefreshListView _ListView;
    List<ClsClass.Cls> _List=new ArrayList<>();
    MyListAdapter _MyListAdapter;

    ClsClass.Cls _Cls;

    int CM_UserID=0;
    String CM_UserName="";
    String CM_Tel="";

    int SO_UserID=0;
    String SO_UserName="";
    String SO_Tel="";

    int QC_UserID=0;
    String QC_UserName="";
    String QC_Tel="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
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
        app_title_left = (ImageView)findViewById(R.id.app_title_left);
        app_title_right = (ImageView)findViewById(R.id.app_title_right);
        app_title_right2 = (TextView) findViewById(R.id.app_title_right2);
        app_title_right.setVisibility(View.GONE);
        app_title_right2.setVisibility(View.GONE);
        app_title_center.setText("请选择任务负责人");
        app_title_left.setImageResource(R.drawable.back);
        app_title_left.setOnClickListener(new OnClickListener()
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
                final ClsClass.Cls _Cls2=_List.get(position-1);

                if (CM_UserID==0) {
                    new MessageBox().Show(ThisContext, "提示", "确认把任务负责人设为" + _Cls2.Str_3 + "吗？", getString(R.string.Cancel), getString(R.string.OK)).BtnClick = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int whichButton) {
                            if (whichButton == -1) {
                                CM_UserID= _Cls2.Int_1;
                                CM_UserName=_Cls2.Str_3;
                                CM_Tel=_Cls2.Str_5;
                                new MessageBox().Show(ThisContext, "提示", "请您任命安全员", "", getString(R.string.OK));
                                app_title_center.setText("请选择安全员");
                            }
                        }
                    };
                }
                else if (SO_UserID==0) {
                    new MessageBox().Show(ThisContext, "提示", "确认把安全员设为" + _Cls2.Str_3 + "吗？", getString(R.string.Cancel), getString(R.string.OK)).BtnClick = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int whichButton) {
                            if (whichButton == -1) {
                                SO_UserID= _Cls2.Int_1;
                                SO_UserName=_Cls2.Str_3;
                                SO_Tel=_Cls2.Str_5;
                                new MessageBox().Show(ThisContext, "提示", "请您任命质量员", "", getString(R.string.OK));
                                app_title_center.setText("请选择质量员");
                            }
                        }
                    };
                }
                else if (QC_UserID==0) {
                    new MessageBox().Show(ThisContext, "提示", "确认把质量员设为" + _Cls2.Str_3 + "吗？", getString(R.string.Cancel), getString(R.string.OK)).BtnClick = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int whichButton) {
                            if (whichButton == -1) {
                                QC_UserID= _Cls2.Int_1;
                                QC_UserName=_Cls2.Str_3;
                                QC_Tel=_Cls2.Str_5;
                                startActivity(new Intent(ThisContext, XM_JDJH_View_RW_AP_Activity.class)
                                        .putExtra("CM_UserID", CM_UserID).putExtra("CM_UserName", CM_UserName).putExtra("CM_Tel", CM_Tel)
                                        .putExtra("SO_UserID", SO_UserID).putExtra("SO_UserName", SO_UserName).putExtra("SO_Tel", SO_Tel)
                                        .putExtra("QC_UserID", QC_UserID).putExtra("QC_UserName", QC_UserName).putExtra("QC_Tel", QC_Tel)
                                );
                                finish();
                            }
                        }
                    };
                }
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
        new HttpCls2(ThisContext,HttpHandler,"list",0,"", Config.ServiceUrl+"index.php/api/job/getLower"+ParmStr,"Get",null,10).Begin();
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
                                    _Cls.Int_2=JSONObject2.getInt("jid");
                                    _Cls.Str_3=JSONObject2.getString("user_name");
                                    _Cls.Str_4=JSONObject2.getString("unit_name");
                                    _Cls.Str_5=JSONObject2.getString("tel");
                                    _Cls.Str_6=JSONObject2.getString("e_mail");
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

                    new MessageBox().Show(ThisContext, "提示", "请您任命任务负责人", "", getString(R.string.OK));
                } catch (Exception e) { }
            }
        }
    };

    class ViewHolder
    {
        public LinearLayout item_widget_1,item_widget_2;
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
                convertView = mInflater.inflate(R.layout.item_member, null);
                holder = new ViewHolder();
                holder.item_widget_1 = (LinearLayout) convertView.findViewById(R.id.item_widget_1);
                holder.item_widget_2 = (LinearLayout)convertView.findViewById(R.id.item_widget_2);
                convertView.setTag(holder);
            }
            else
            {
                holder = (ViewHolder)convertView.getTag();
            }
            ClsClass.Cls _Cls = _List.get(position);
            ((TextView)holder.item_widget_1.getChildAt(0)).setText("姓名：");
            ((TextView)holder.item_widget_1.getChildAt(1)).setText(_Cls.Str_3);
            ((TextView)holder.item_widget_1.getChildAt(2)).setText("所属部门：");
            ((TextView)holder.item_widget_1.getChildAt(3)).setText(_Cls.Str_1);
            ((TextView)holder.item_widget_2.getChildAt(0)).setText("职位：");
            ((TextView)holder.item_widget_2.getChildAt(1)).setText(_Cls.Str_2);

            return convertView;
        }
    }
}
