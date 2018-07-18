package com.uuzo.gcjs.View.Project.ProjectManChoice;

import android.view.View;
import android.widget.ListView;

import com.uuzo.gcjs.Adapter.Project.ProjectChoiceAdapter;
import com.uuzo.gcjs.Adapter.Project.ProjectMan.ProjectChoicean1AdapterM;
import com.uuzo.gcjs.Adapter.Project.ProjectMan.ProjectChoicean2AdapterM;
import com.uuzo.gcjs.Adapter.Project.ProjectMan.ProjectChoicean4AdapterM;
import com.uuzo.gcjs.BaseTemplate.BaseActivity;
import com.uuzo.gcjs.Controller.ProjectNewController;
import com.uuzo.gcjs.Manager.InterfaceManger;
import com.uuzo.gcjs.Moudule.Member;
import com.uuzo.gcjs.Moudule.Project;
import com.uuzo.gcjs.R;
import com.uuzo.gcjs.Utils.RetrofitUtils;
import com.uuzo.gcjs.View.Project.ProjectNewChoiceActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by jie on 2018/7/17.
 */

public class ProjectMan4Activity extends BaseActivity {
    private ListView lv_listview;//展示列表
    private List<Member.ContentBean> lists = new ArrayList<Member.ContentBean>();//listview数据集
    private ProjectChoicean4AdapterM adapter;//适配器
    @Override
    public int getLayoutId() {
        return  R.layout.activity_man_choice4;
    }

    @Override
    public void initViews() {
        lv_listview = findView(R.id.lv_listview);
        init();
    }

    /**
     * 访问数据
     */
    private void init() {
        List<String> photos = new ArrayList<>();
        List<MultipartBody.Part> parts = null;
        Map<String, RequestBody> params = new HashMap<>();
        params.put("token", RetrofitUtils.convertToRequestBody("166b72d497d9f23ab2074734515c6ac908b6e146"));
        params.put("unit_id", RetrofitUtils.convertToRequestBody("210"));
        ProjectNewController.choice(params, parts, new InterfaceManger.OnRequestListener() {
            @Override
            public void onSuccess(Object success) {
                Member workTaskProjectMessage = (Member) success;
                adapter = new ProjectChoicean4AdapterM(ProjectMan4Activity.this,workTaskProjectMessage.getContent());
//                    adapter = new ProjectChoiceAdapter(ProjectNewChoiceActivity.this, lists);
                lv_listview.setAdapter(adapter);


            }

            @Override
            public void onError(String error) {
                showToast(error);
            }

            @Override
            public void onComplete() {
            }
        });
    }



    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        setTitleCanBack();
        setTitle("请选择负责人");
    }

    @Override
    public void processClick(View v) {

    }
}
