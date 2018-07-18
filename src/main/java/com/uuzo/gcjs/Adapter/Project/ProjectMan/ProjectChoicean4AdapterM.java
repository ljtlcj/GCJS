package com.uuzo.gcjs.Adapter.Project.ProjectMan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uuzo.gcjs.Moudule.Member;
import com.uuzo.gcjs.R;
import com.uuzo.gcjs.View.Project.ProjectBigNewActivity;
import com.uuzo.gcjs.View.Project.ProjectNewActivity;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


/**
 * 工程标段
 */
public class ProjectChoicean4AdapterM extends BaseAdapter {
    private List<Member.ContentBean> lists = new ArrayList<>();
    private Context context;

    public ProjectChoicean4AdapterM(Context context, List<Member.ContentBean> lists) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_choice_man, null, false);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.tv_item);
            holder.position = (TextView) convertView.findViewById(R.id.tv_work_kind);
            holder.branch = (TextView) convertView.findViewById(R.id.tv_branch_content);
            holder.phone = (TextView) convertView.findViewById(R.id.tv_managers);
            holder.big = (LinearLayout) convertView.findViewById(R.id.big);
            convertView.setTag(holder);   //将Holder存储到convertView中
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.name.setText(lists.get(position).getUser_name());

        holder.position.setText(lists.get(position).getJob());

        holder.branch.setText(lists.get(position).getUnit_name());

        holder.phone.setText(lists.get(position).getDepartment());
        holder.big.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ProjectBigNewActivity.class);
                SharedPreferences information = context.getSharedPreferences("UserInformation", MODE_PRIVATE);
                SharedPreferences.Editor editor = information.edit();
                editor.putString("qc",lists.get(positions).getUnit_name() );
                editor.commit();
                context.startActivity(i);
            }
        });

        return convertView;
    }


    static class ViewHolder {
        TextView name;//姓名
        TextView position;//职位
        TextView branch;//部门
        TextView phone;//手机部门
        LinearLayout big;//大框框
    }
}