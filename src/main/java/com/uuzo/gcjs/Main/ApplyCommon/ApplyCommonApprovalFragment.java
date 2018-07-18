package com.uuzo.gcjs.Main.ApplyCommon;

import android.view.View;
import android.widget.ListView;
import android.widget.Toast;


import com.uuzo.gcjs.Adapter.Home.Applycommon.ApplyCommonApprovalAdapter;
import com.uuzo.gcjs.Adapter.Project.ProjectChoiceAdapter;
import com.uuzo.gcjs.BaseTemplate.BaseLazyFragment;
import com.uuzo.gcjs.Config;
import com.uuzo.gcjs.Controller.ApplyCommonController;
import com.uuzo.gcjs.Controller.ProjectNewController;
import com.uuzo.gcjs.Manager.InterfaceManger;
import com.uuzo.gcjs.Moudule.ApplyCommon;
import com.uuzo.gcjs.Moudule.Project;
import com.uuzo.gcjs.R;
import com.uuzo.gcjs.Utils.RetrofitUtils;
import com.uuzo.gcjs.View.Project.ProjectNewActivity;
import com.uuzo.gcjs.View.Project.ProjectNewChoiceActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.uuzo.gcjs.R.id.lv_listview;

/**
 * Created by YX_PC on 2018/1/25.
 */

public class ApplyCommonApprovalFragment extends BaseLazyFragment {
    private ListView lv_content;//listview容器
    private ApplyCommonApprovalAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_apply_common_approval;
    }

    @Override
    public void initViews() {
        lv_content = findView(R.id.lv_content);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        fanwen();
    }

    /**
     * 访问服务器获取数据
     */
    private void fanwen() {
        List<String> photos = new ArrayList<>();
        List<MultipartBody.Part> parts = null;
//        parts = RetrofitUtils.filesToMultipartBodyParts("photo", photos);
        Map<String, RequestBody> params = new HashMap<>();
        params.put("token", RetrofitUtils.convertToRequestBody(Config.Ltoken));

        ApplyCommonController.getTask(params, parts, new InterfaceManger.OnRequestListener() {
            @Override
            public void onSuccess(Object success) {
                ApplyCommon workTaskProjectMessage = (ApplyCommon) success;
                adapter = new ApplyCommonApprovalAdapter(workTaskProjectMessage.getContent(),getActivity());
//                    adapter = new ProjectChoiceAdapter(ProjectNewChoiceActivity.this, lists);
                lv_content.setAdapter(adapter);
                Toast.makeText(getActivity(), success.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String error) {
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void processClick(View v) {

    }
}
