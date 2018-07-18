package com.uuzo.gcjs.View.Project;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.uuzo.gcjs.BaseTemplate.BaseActivity;
import com.uuzo.gcjs.Controller.ProjectNewController;
import com.uuzo.gcjs.Manager.InterfaceManger;
import com.uuzo.gcjs.R;
import com.uuzo.gcjs.Utils.RetrofitUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by jie on 2018/7/16.
 */

public class ProjectNewActivity extends BaseActivity {
    private String temp_path;
    private static final int FILE_SELECT_CODE = 0;
    Intent intent;
    private File file;//选择的文件
    private String path;//选择文件的路径
    private TextView choice;//点击选择txt
    private TextView tv_show_up;//展示是否上传

    private TextView et_item_one_name;//选择所属工程标段
    private EditText et_item_name;//项目名
    private TextView et_time;//区域
    private TextView et_check_man;//地址
    private RatingBar star;//地址et_check_item
    private EditText et_check_item;//项目概况
    private Button bt_submit;//提交按钮

    private TextView tv_add_project;//新建项目
    private TextView tv_add_item;//添加工单

    private String name;
    private String kind;
    private String number;
    private String progress;
    private String id;

    @Override
    public int getLayoutId() {
        return R.layout.activity_project_new;
    }

    @Override
    public void initViews() {
        initrequest();
        et_item_name= findView(R.id.et_item_name);
        et_time = findView(R.id.et_time);
        et_check_man = findView(R.id.et_check_man);
        star = findView(R.id.star);
        et_check_item = findView(R.id.et_check_item);
        bt_submit = findView(R.id.bt_submit);

        choice= findView(R.id.tv_folder_up);
        tv_show_up = findView(R.id.tv_show_up);
        tv_add_project = findView(R.id.tv_add_project);
        et_item_one_name = findView(R.id.et_item_one_name);
        tv_add_item = findView(R.id.tv_add_item);
    }

    @Override
    public void initListener() {
        choice.setOnClickListener(this);
        tv_add_project.setOnClickListener(this);
        et_item_one_name.setOnClickListener(this);
        tv_add_item.setOnClickListener(this);

        bt_submit.setOnClickListener(this);
    }

    @Override
    public void initData() {
        setEdit("表格导入");
        setTitle("立项");
        setTitleCanBack();
        Intent i = getIntent();
        name = i.getStringExtra("name");
        kind = i.getStringExtra("kind");
        number = i.getStringExtra("number");
        progress = i.getStringExtra("progress");
        id = i.getStringExtra("id");
        if (name!=null){
            et_item_one_name.setText(name);
            Log.e("initData: ",id);
        }
    }

    /**
     * @param v
     */
    @Override
    public void processClick(View v) {
        switch (v.getId()){
            case R.id.tv_folder_up:
                choice_click();
                break;
            case R.id.tv_add_project:
                startActivity(ProjectBigNewActivity.class);
                break;
            case R.id.et_item_one_name:
                startActivity(ProjectNewChoiceActivity.class);
//                finish();
                break;
            case R.id.tv_add_item:
                startActivity(ProjectNewItemActivity.class);
                break;
            case R.id.bt_submit:
                send_click();
                break;
            default:
                break;
        }
    }

    /**
     * 上传立项内容
     * @param trouble
     * @param item_name
     * @param counties
     * @param prject_id
     * @param addr
     * @param accessory
     * @param works
     * @param situation
     */
    private void up_second(int trouble,String item_name, String counties,String prject_id,  String addr,String accessory,String works, String situation ) {
        List<String> photos = new ArrayList<>();
        List<MultipartBody.Part> parts = null;
//        parts = RetrofitUtils.filesToMultipartBodyParts("photo", photos);
        Map<String, RequestBody> params = new HashMap<>();
        params.put("token", RetrofitUtils.convertToRequestBody("166b72d497d9f23ab2074734515c6ac908b6e146"));
        params.put("trouble", RetrofitUtils.convertToRequestBody(String.valueOf(trouble)));
        params.put("item_name", RetrofitUtils.convertToRequestBody(item_name));
        params.put("counties", RetrofitUtils.convertToRequestBody(counties));
        params.put("project_id", RetrofitUtils.convertToRequestBody(prject_id));
        params.put("addr", RetrofitUtils.convertToRequestBody(addr));
        params.put("accessory", RetrofitUtils.convertToRequestBody(accessory));
        params.put("works", RetrofitUtils.convertToRequestBody(works));
        params.put("situation", RetrofitUtils.convertToRequestBody(situation));

        ProjectNewController.lixiang(params, parts, new InterfaceManger.OnRequestListener() {
            @Override
            public void onSuccess(Object success) {

                Toast.makeText(ProjectNewActivity.this, success.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String error) {
                Toast.makeText(ProjectNewActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {

            }
        });
    }

    /**
     * 发送文件
     **/
    private void send_click() {
        String temp;
        if(path!=null){
            temp = path;
        }else{
            temp = "";
        }
        if (temp.equals("")) {
            Toast.makeText(ProjectNewActivity.this, "未选择文件", Toast.LENGTH_SHORT).show();
        } else {
            //提交文件
            fanwen(file, path);
        }
    }

    /**
     * 上传文件到服务器中去 ProjectNewController.uploadItemFile
     **/
    private void fanwen(File file, String path) {
//        CustomerController loginController = new CustomerController(this);
        File file1 = new File(path);
//        final List<String> photos = new ArrayList<>();
//        上传单个文件
//        List<MultipartBody.Part> parts = null;
//        parts.add(body);
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file1);
        MultipartBody.Part parts = MultipartBody.Part.createFormData("file", file1.getName(), requestFile);

        Map<String, RequestBody> params = new HashMap<>();
        params.put("token", RetrofitUtils.convertToRequestBody("166b72d497d9f23ab2074734515c6ac908b6e146"));
        ProjectNewController.uploadItemFile(params, parts, new InterfaceManger.OnRequestListener() {
            @Override
            public void onSuccess(Object success) {
                temp_path = String.valueOf(success);
//                Toast.makeText(ProjectNewActivity.this, String.valueOf(success), Toast.LENGTH_SHORT).show();
                int trouble = star.getNumStars();
                String prject_id = id;
                String item_name = et_item_name.getText().toString().trim();
                String counties = et_time.getText().toString().trim();
                String addr =  et_check_man.getText().toString().trim();
                String accessory;
                if (temp_path!=null){
                    accessory = temp_path;
                }else{
                    accessory = "";
                }
                String works =  "[{\"ass_name\":\"定位\",\"ass_type\":\"试验运维\",\"workload\":\"1000\",\"unit\":\"米\"},{\"ass_name\":\"定位\",\"ass_type\":\"试验运维\",\"workload\":\"1000\",\"unit\":\"米\"}]";
                String situation = et_check_item.getText().toString().trim();
                up_second(trouble,item_name,counties,prject_id,addr,accessory,works,situation);
            }

            @Override
            public void onError(String error) {
                Toast.makeText(ProjectNewActivity.this, error, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {

            }
        });
    }

    /**
     * 请求权限
     **/
    private void initrequest() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }
    /**
     * 选择文件
     **/
    private void choice_click() {
        intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult(Intent.createChooser(intent, "请选择一个要上传的文件"), FILE_SELECT_CODE);
//            FILE_SELECT_CODE
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(ProjectNewActivity.this, "请安装文件管理器", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    /**
     * 根据返回选择的文件，来进行上传操作
     **/
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            path = uri.getPath().toString();
            file = new File(path);
            String url;
//            Toast.makeText(this, "文件路径：" + uri.getPath().toString(), Toast.LENGTH_SHORT).show();
            String end = file.getName().substring(file.getName().lastIndexOf(".") + 1, file.getName().length()).toLowerCase();
            String name = file.getName();
            if (end.equals("rar")||end.equals("zip")) {
                tv_show_up.setText("文件已选择");
            } else {
                Toast.makeText(this, "请选择zip或rar格式", Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}
