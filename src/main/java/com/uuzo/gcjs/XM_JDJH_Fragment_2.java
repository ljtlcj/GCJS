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

public class XM_JDJH_Fragment_2 extends Fragment {
    Boolean IsDestroy = false;
    Context ThisContext;
    Activity ThisActivity;

    EditText widget_1;
    String SearchContent = "";

    PullToRefreshListView _ListView;
    List<ClsClass.Cls> _List = new ArrayList<>();
    MyListAdapter _MyListAdapter;

    int _Type = 0;

    public XM_JDJH_Fragment_2() {
    }

    public static Fragment newInstance(int _Type) {
        XM_JDJH_Fragment_2 fragment = new XM_JDJH_Fragment_2();
        Bundle bundle = new Bundle();
        bundle.putInt("Type", _Type);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list_search, container, false);

        IsDestroy = false;
        ThisContext = getContext();
        ThisActivity = getActivity();

        _Type = getArguments().getInt("Type", 0);

        _ListView = (PullToRefreshListView) view.findViewById(R.id.widget_0);
        widget_1 = (EditText) view.findViewById(R.id.widget_1);

        widget_1.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    SearchContent = widget_1.getText().toString().trim().toLowerCase();
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
        _ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position < 1 || position > _MyListAdapter.list.size()) return;
                ClsClass.Cls _Cls = _MyListAdapter.list.get(position - 1);
                if (_Cls.Int == 1 || _Cls.Int == 2) {
                    startActivity(new Intent(ThisContext, XM_JDJH_View_Activity.class).putExtra("ID", _Cls.Int_1).putExtra("Type", _Cls.Int));
                } else if (_Cls.Int == 3) {
                    new MessageBox().Show(ThisContext, "提示", "我执行的任务只能做出勤", "", getString(R.string.OK));
                    return;
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
        IsDestroy = true;
        super.onDestroyView();
    }

    public void Load() {
        if (_MyListAdapter.list.size() == 0)
            _ListView.setBackgroundResource(R.drawable.listviewloadingbg);

        String ParmStr = "?token=" + Common.UrlEncoded(UserInfo.Token);
        new HttpCls2(ThisContext, HttpHandler, "list", 0, "", Config.ServiceUrl + "index.php/api/project/getOwnWorks" + ParmStr, "Get", null, 10).Begin();
    }

    void SearchContentGo() {
        if (SearchContent.equals("")) _MyListAdapter.list = _List;
        else {
            _MyListAdapter.list = new ArrayList<>();
            for (int i = 0; i < _List.size(); i++) {
                ClsClass.Cls _Cls = _List.get(i);

                if (_Cls.Int == 1 && (_Cls.Str_1.toLowerCase().indexOf(SearchContent) > -1
                        || _Cls.Str_4.toLowerCase().indexOf(SearchContent) > -1
                        || Config.GetProjectTypeName(_Cls.Int_2).toLowerCase().indexOf(SearchContent) > -1
                        || _Cls.Str_3.toLowerCase().indexOf(SearchContent) > -1
                )) {
                    _MyListAdapter.list.add(_Cls);
                } else if (_Cls.Int == 2 && (_Cls.Str_2.toLowerCase().indexOf(SearchContent) > -1
                        || _Cls.Str_3.toLowerCase().indexOf(SearchContent) > -1
                        || _Cls.Str_4.toLowerCase().indexOf(SearchContent) > -1
                )) {
                    _MyListAdapter.list.add(_Cls);
                } else if (_Cls.Int == 3 && (_Cls.Str_1.toLowerCase().indexOf(SearchContent) > -1
                        || _Cls.Str_2.toLowerCase().indexOf(SearchContent) > -1
                        || _Cls.Str_3.toLowerCase().indexOf(SearchContent) > -1
                )) {
                    _MyListAdapter.list.add(_Cls);
                }
            }
        }
        _MyListAdapter.notifyDataSetChanged();
        if (_ListView.isRefreshing()) _ListView.onRefreshComplete();
        if (_MyListAdapter.list.size() > 0) _ListView.setBackgroundResource(0);
        else _ListView.setBackgroundResource(R.drawable.listviewemptybg);
    }

    @SuppressLint("HandlerLeak")
    Handler HttpHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (IsDestroy || msg.obj == null) return;
            String FunName = msg.obj.toString();
            if (FunName.equals("list")) {
                try {
                    Boolean IsOK = false;
                    try {
                        String ReturnValue = msg.getData().getString("ReturnValue");
                        JSONObject _JSONObject = new JSONObject(ReturnValue);
                        if (_JSONObject.getBoolean("status")) {
                            IsOK = true;
                            _List.clear();
                            JSONArray Works_JSONArray = _JSONObject.getJSONArray("content");
                            for (int j = 0; j < Works_JSONArray.length(); j++) {
                                JSONObject works_JSONObject = Works_JSONArray.getJSONObject(j);
                                ClsClass.Cls _Cls3 = new ClsClass().new Cls();
                                _Cls3.Int = 3;
                                _Cls3.Int_1 = works_JSONObject.getInt("id");
                                _Cls3.Int_2 = works_JSONObject.has("project_id") ? works_JSONObject.getInt("project_id") : 0;
                                _Cls3.Int_3 = works_JSONObject.getInt("item_id");
                                _Cls3.Str_1 = works_JSONObject.getString("ass_name");
                                _Cls3.Str_2 = works_JSONObject.has("ass_number") ? works_JSONObject.getString("ass_number") : "";
                                _Cls3.Str_3 = works_JSONObject.has("ass_type") ? works_JSONObject.getString("ass_type") : "";
                                _Cls3.Int_4 = works_JSONObject.getInt("work_load");
                                _Cls3.Str_4 = works_JSONObject.getString("unit");
                                _Cls3.Str_5 = works_JSONObject.has("note") ? works_JSONObject.getString("note") : "";
                                _Cls3.Str_6 = works_JSONObject.has("matters") ? works_JSONObject.getString("matters") : "";
                                _Cls3.Str_7 = works_JSONObject.getString("matters2");
                                _Cls3.Str_8 = works_JSONObject.has("accessory") ? works_JSONObject.getString("accessory") : "";
                                _Cls3.Str_9 = works_JSONObject.getString("accessory2");
                                _Cls3.Str_10 = works_JSONObject.getString("start_time");
                                _Cls3.Str_11 = works_JSONObject.getString("end_time");
                                _Cls3.Int_5 = works_JSONObject.has("work_day") ? works_JSONObject.getInt("work_day") : 0;
                                _Cls3.Str_12 = works_JSONObject.getString("cm");
                                _Cls3.Str_13 = works_JSONObject.getString("so");
                                _Cls3.Str_14 = works_JSONObject.getString("qc");
                                _Cls3.Str_15 = works_JSONObject.has("executor") ? works_JSONObject.getString("executor") : "";
                                _Cls3.Int_6 = works_JSONObject.getInt("plan");
                                _Cls3.Int_7 = works_JSONObject.has("car") ? works_JSONObject.getInt("car") : 0;
                                _Cls3.Int_8 = works_JSONObject.has("machine") ? works_JSONObject.getInt("machine") : 0;
                                _Cls3.Int_9 = works_JSONObject.getInt("type");
                                _Cls3.Int_10 = works_JSONObject.getInt("safe_state");
                                _Cls3.Int_11 = works_JSONObject.has("is_change") ? works_JSONObject.getInt("is_change") : 0;
                                _Cls3.Int_12 = works_JSONObject.has("now_workload") ? works_JSONObject.getInt("now_workload") : 0;
                                _Cls3.Int_13 = works_JSONObject.has("is_limit") ? works_JSONObject.getInt("is_limit") : 0;
                                _Cls3.Int_14 = works_JSONObject.has("finish") ? works_JSONObject.getInt("finish") : 0;
                                _Cls3.Str_15 = works_JSONObject.has("item_name") ? works_JSONObject.getString("item_name") : "";
                                _Cls3.Str_16 = works_JSONObject.has("addr") ? works_JSONObject.getString("addr") : "";
                                if (works_JSONObject.has("zhixingren")) {
                                    JSONArray zhixingren_JSONArray = works_JSONObject.getJSONArray("zhixingren");
                                    for (int k = 0; k < zhixingren_JSONArray.length(); k++) {
                                        JSONObject zhixingren_JSONObject = zhixingren_JSONArray.getJSONObject(k);
                                        ClsClass.Cls _Cls4 = new ClsClass().new Cls();
                                        _Cls4.Str_1 = zhixingren_JSONObject.getString("user_name");
                                        _Cls4.Str_2 = zhixingren_JSONObject.getString("tel");
                                        _Cls3.ClsList_1.add(_Cls4);
                                    }
                                }
                                _List.add(_Cls3);
                            }
                        }
                    } catch (Exception e) {
                    }
                    SearchContentGo();
                } catch (Exception e) {
                }
            }
        }
    };

    class ViewHolder {
        public LinearLayout item_widget_1, item_widget_2, item_widget_3, item_widget_4;
    }

    class MyListAdapter extends BaseAdapter {
        LayoutInflater mInflater;
        public List<ClsClass.Cls> list = new ArrayList<>();

        MyListAdapter() {
            mInflater = LayoutInflater.from(ThisContext);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int arg0) {
            return list.get(arg0);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.item_xm, null);
                holder = new ViewHolder();
                holder.item_widget_1 = (LinearLayout) convertView.findViewById(R.id.item_widget_1);
                holder.item_widget_2 = (LinearLayout) convertView.findViewById(R.id.item_widget_2);
                holder.item_widget_3 = (LinearLayout) convertView.findViewById(R.id.item_widget_3);
                holder.item_widget_4 = (LinearLayout) convertView.findViewById(R.id.item_widget_4);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            ClsClass.Cls _Cls = list.get(position);

            if (_Cls.Int == 1) {
                ((TextView) holder.item_widget_1.getChildAt(0)).setText("工程名称：");
                ((TextView) holder.item_widget_1.getChildAt(1)).setText(_Cls.Str_1);
                ((TextView) holder.item_widget_2.getChildAt(0)).setText("工程编号：");
                ((TextView) holder.item_widget_2.getChildAt(1)).setText(_Cls.Str_4);
                ((TextView) holder.item_widget_3.getChildAt(0)).setText("工程类别：");
                ((TextView) holder.item_widget_3.getChildAt(1)).setText(String.valueOf(_Cls.Int_2));
                ((TextView) holder.item_widget_3.getChildAt(2)).setText("进度：" + _Cls.Int_3 + "%");
                ((TextView) holder.item_widget_4.getChildAt(0)).setText("建设单位：");
                ((TextView) holder.item_widget_4.getChildAt(1)).setText(_Cls.Str_3);
            } else if (_Cls.Int == 2) {
                ((TextView) holder.item_widget_1.getChildAt(0)).setText("项目名称：");
                ((TextView) holder.item_widget_1.getChildAt(1)).setText(_Cls.Str_2);
                ((TextView) holder.item_widget_2.getChildAt(0)).setText("项目编号：");
                ((TextView) holder.item_widget_2.getChildAt(1)).setText(_Cls.Str_3);
                ((TextView) holder.item_widget_3.getChildAt(0)).setText("项目类型：");
                ((TextView) holder.item_widget_3.getChildAt(1)).setText(_Cls.Str_4);
                ((TextView) holder.item_widget_3.getChildAt(2)).setText(Config.GetStatusName(_Cls.Int_6, _Cls.Int_5));
                ((TextView) holder.item_widget_4.getChildAt(0)).setText("项目时间：");
                ((TextView) holder.item_widget_4.getChildAt(1)).setText(_Cls.Str_8 + " 至 " + _Cls.Str_9);
            } else if (_Cls.Int == 3) {
                ((TextView) holder.item_widget_1.getChildAt(0)).setText("任务名称：");
                ((TextView) holder.item_widget_1.getChildAt(1)).setText(_Cls.Str_1);
                holder.item_widget_2.setVisibility(View.GONE);
                ((TextView) holder.item_widget_3.getChildAt(0)).setText("工作量：");
                ((TextView) holder.item_widget_3.getChildAt(1)).setText(_Cls.Int_4 + _Cls.Str_4);
                ((TextView) holder.item_widget_3.getChildAt(2)).setText(Config.GetStatusName(_Cls.Int_9, _Cls.Int_6));
                ((TextView) holder.item_widget_4.getChildAt(0)).setText("任务时间：");
                ((TextView) holder.item_widget_4.getChildAt(1)).setText(_Cls.Str_10 + " 至 " + _Cls.Str_11);
            }
            return convertView;
        }
    }
}
