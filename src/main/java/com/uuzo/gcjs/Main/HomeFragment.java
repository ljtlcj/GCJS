package com.uuzo.gcjs.Main;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.uuzo.gcjs.BaseTemplate.BaseLazyFragment;
import com.uuzo.gcjs.Customview.WorkbenchContent;
import com.uuzo.gcjs.R;

/**
 * Created by handsome on 2016/4/7.
 */
public class HomeFragment extends BaseLazyFragment {
    private WorkbenchContent iv_company_announcements;//公司公告
    private WorkbenchContent iv_working_task;//工作任务
    private WorkbenchContent iv_work_report;//工作汇报
    private WorkbenchContent iv_general_settings;//通用申请
    private ImageView iv_detailed_list;//清单
    private ImageView iv_progress_plan;//进度计划
    private ImageView tv_security_check;//安全检查
    private ImageView iv_quality_inspection;//质量检查
    private ImageView iv_record_workpoints;//签到记工
    private ImageView iv_completion_test;//完工验收
    private ImageView iv_materials;//物资
    private ImageView iv_statistics_task;//任务统计
    private ImageView iv_hard_statistics;//用工统计
    private ImageView iv_material_statistics;//物资统计
    private ImageView iv_data_statistics;//资料统计
    private ImageView iv_daily_check;//日常检查

    private LinearLayout one;//不要的
    private LinearLayout two;//不要的Ln_temp
    private LinearLayout three;//不要的
    private LinearLayout four;//不要的Ln_temp
    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initViews() {
        one = findView(R.id.lin_temp);
        two = findView(R.id.lin_temp2);
        three = findView(R.id.Ln_temp);
        four = findView(R.id.ln_temp1);
        one.setVisibility(View.INVISIBLE);
        two.setVisibility(View.INVISIBLE);
        three.setVisibility(View.INVISIBLE);
        four.setVisibility(View.INVISIBLE);
//        iv_company_announcements = findView(R.id.iv_company_announcements);
//        iv_daily_check = findView(R.id.iv_daily_check);
//        iv_working_task = findView(R.id.iv_working_task);
//        iv_work_report = findView(R.id.iv_work_report);
//        iv_general_settings = findView(R.id.iv_general_settings);
//        iv_detailed_list = findView(R.id.iv_detailed_list);
//        iv_progress_plan = findView(R.id.iv_progress_plan);
//        tv_security_check = findView(R.id.tv_security_check);
//        iv_record_workpoints = findView(R.id.iv_record_workpoints);
//        iv_quality_inspection = findView(R.id.iv_quality_inspection);
//        iv_completion_test = findView(R.id.iv_completion_test);
//        iv_materials = findView(R.id.iv_materials);
//        iv_statistics_task = findView(R.id.iv_statistics_task);
//        iv_hard_statistics = findView(R.id.iv_hard_statistics);
//        iv_material_statistics = findView(R.id.iv_material_statistics);
//        iv_data_statistics = findView(R.id.iv_data_statistics);

    }

    @Override
    public void initListener() {
//        iv_daily_check.setOnClickListener(this);
//        iv_data_statistics.setOnClickListener(this);
//        iv_material_statistics.setOnClickListener(this);
//        iv_hard_statistics.setOnClickListener(this);
//        iv_statistics_task.setOnClickListener(this);
//        iv_completion_test.setOnClickListener(this);
//        iv_quality_inspection.setOnClickListener(this);
//        tv_security_check.setOnClickListener(this);
//        iv_progress_plan.setOnClickListener(this);
//        iv_company_announcements.setOnClickListener(this);
//        iv_work_report.setOnClickListener(this);
//        iv_general_settings.setOnClickListener(this);
//        iv_working_task.setOnClickListener(this);
    }

    @Override
    public void initData() {
        setTitle("工作台");
        setEdit("登陆");
        setEdit2("注册");
    }

    @Override
    public void processClick(View v) {
//        Intent i;
//        switch (v.getId()){
////            case R.id.tv_edit://登陆
////                i=new Intent(getActivity(), LoginActivity.class);
////                startActivity(i);
//                break;
////            case R.id.tv_edit2://注册
////                i=new Intent(getActivity(), RegisterActivity.class);
////                startActivity(i);
//                break;
//            case R.id.iv_data_statistics:
////                i=new Intent(getActivity(), InfoStatisticsActivity.class);
////                startActivity(i);
//                break;
//            case R.id.iv_material_statistics:
////                i=new Intent(getActivity(), MaterialStatisticsActivity.class);
////                startActivity(i);
//                break;
//            case R.id.iv_hard_statistics:
////                i=new Intent(getActivity(), EmployeeStatisticsActivity.class);
////                startActivity(i);
//                break;
//            case R.id.iv_statistics_task:
////                i=new Intent(getActivity(), AssignmentStatisticsActivity.class);
////                startActivity(i);
//                break;
//            case R.id.iv_completion_test:
////                i=new Intent(getActivity(), MaterialOutputRecordActivity.class);
////                startActivity(i);
//                break;
//            case R.id.iv_daily_check:
////                i=new Intent(getActivity(), DailyCheckActivity.class);
////                startActivity(i);
//                break;
//            case R.id.iv_quality_inspection:
////                i=new Intent(getActivity(), QualityCheckActivity.class);
////                startActivity(i);
//                break;
//            case R.id.tv_security_check:
////                i=new Intent(getActivity(), SecurityCheckActivity.class);
////                startActivity(i);
//                break;
//            case R.id.iv_progress_plan:
////                i=new Intent(getActivity(),PlanProgressActivity.class);
////                startActivity(i);
//                break;
//            case R.id.iv_company_announcements:
////                i=new Intent(getActivity(), AnnouncementsCompanyActivity.class);
////                startActivity(i);
//                break;
//            case R.id.iv_work_report:
////                i=new Intent(getActivity(), WorkReportActivity.class);
////                startActivity(i);
//                break;
//            case R.id.iv_general_settings:
////                i=new Intent(getActivity(), ApplyCommonActivity.class);
////                startActivity(i);
//                break;
//            case R.id.iv_working_task:
////                i=new Intent(getActivity(),WorkTaskActivity.class);
////                startActivity(i);
//                break;
//            case R.id.iv_materials:
////                i=new Intent(getActivity(), MaterialsManagementActivity.class);
////                startActivity(i);
//            default:
//                break;
//        }
    }
}
