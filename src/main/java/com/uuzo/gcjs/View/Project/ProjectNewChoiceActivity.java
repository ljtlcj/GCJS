package com.uuzo.gcjs.View.Project;

import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.uuzo.gcjs.Adapter.Project.ProjectChoiceAdapter;
import com.uuzo.gcjs.BaseTemplate.BaseActivity;
import com.uuzo.gcjs.Controller.ProjectNewController;
import com.uuzo.gcjs.Manager.InterfaceManger;
import com.uuzo.gcjs.Moudule.Project;
import com.uuzo.gcjs.R;
import com.uuzo.gcjs.Utils.RetrofitUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by jie on 2018/7/16.
 */

public class ProjectNewChoiceActivity extends BaseActivity {
    private ListView lv_listview;//展示列表
    private List<Project> lists = new ArrayList<Project>();//listview数据集
    private ProjectChoiceAdapter adapter;//适配器
    @Override
    public int getLayoutId() {
        return R.layout.activity_project_choice;
//        adapter_option_project;
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
            ProjectNewController.show_notice_project(params, parts, new InterfaceManger.OnRequestListener() {
                @Override
                public void onSuccess(Object success) {
                    Project workTaskProjectMessage = (Project) success;
                    adapter = new ProjectChoiceAdapter(ProjectNewChoiceActivity.this,workTaskProjectMessage.getContent());
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
        setTitle("请选择工程标段");
        setTitleCanBack();
    }

    @Override
    public void processClick(View v) {

    }
}
