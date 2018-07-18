package com.uuzo.gcjs.Adapter.Project;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uuzo.gcjs.R;
import com.uuzo.gcjs.Moudule.Project;
import com.uuzo.gcjs.View.Project.ProjectNewActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * 工程标段
 */
public class ProjectChoiceAdapter extends BaseAdapter {
    private List<Project.ContentBean> lists = new ArrayList<>();
    private Context context;

    public ProjectChoiceAdapter(Context context, List<Project.ContentBean> lists) {
        this.context = context;
        this.lists = lists;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int positions = position;
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_option_project, null, false);
            holder = new ViewHolder();
            holder.tv_project_num = (TextView) convertView.findViewById(R.id.tv_item);
//            holder.tv_project_name= (TextView) convertView.findViewById(R.id.tv_work_kind);
            holder.tv_project_manager = (TextView) convertView.findViewById(R.id.tv_managers);
            holder.tv_project_time_limit = (TextView) convertView.findViewById(R.id.tv_work_progress);
            holder.tv_engineering_type = (TextView) convertView.findViewById(R.id.tv_item_kind);
            holder.big = (LinearLayout) convertView.findViewById(R.id.big);
            convertView.setTag(holder);   //将Holder存储到convertView中
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //    设置工程名
        holder.tv_project_num.setText(lists.get(position).getProject_name());
//        //设置工程类别
//        holder.tv_project_name.setText(lists.get(position).getProject_type());
        //设置工程编号
        holder.tv_project_manager.setText(lists.get(position).getProject_number());
        //设置工程时间
        holder.tv_engineering_type.setText(lists.get(position).getStart_time() + "至" + lists.get(position).getEnd_time());
        //设置工程进度
        holder.tv_project_time_limit.setText(lists.get(position).getPlan() + "%");
        holder.big.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ProjectNewActivity.class);
                i.putExtra("name", lists.get(positions).getProject_name());
                i.putExtra("kind", lists.get(positions).getProject_type());
                i.putExtra("number", lists.get(positions).getProject_number());
                i.putExtra("progress", lists.get(positions).getPlan());
                i.putExtra("id",String.valueOf(lists.get(positions).getId()));
                context.startActivity(i);
            }
        });

        return convertView;
    }

    static class ViewHolder {
        TextView tv_project_num;//工程名
        TextView tv_project_name;//工程类别
        TextView tv_engineering_type;//工程时间
        TextView tv_project_manager;//工程编号
        TextView tv_project_time_limit;//工程进度
        LinearLayout big;
    }
}