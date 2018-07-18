package com.uuzo.gcjs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.mm.opensdk.utils.Log;
import com.uuzo.gcjs.Controller.LoginController;
import com.uuzo.gcjs.Controller.ProjectNewController;
import com.uuzo.gcjs.Manager.InterfaceManger;
import com.uuzo.gcjs.Moudule.LoginMessage;
import com.uuzo.gcjs.Moudule.Member;
import com.uuzo.gcjs.Utils.RetrofitUtils;
import com.uuzo.gcjs.View.Main.Main1Activity;
import com.uuzo.gcjs.View.Project.ProjectNewActivity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 *
 */
public class LoginActivity extends Activity {
    Boolean IsDestroy = false;
    Context ThisContext;
    Activity ThisActivity;

    TextView app_title_center, app_title_right2;
    ImageView app_title_left, app_title_right;

    LinearLayout widget_1;
    TextView widget_2;

    IWXAPI WXApi;


    String UpdateUrl = "";
    String UpdateReadme = "";

    private TextView login;//登陆按钮
    private EditText input_account;//账号
    private EditText input_password;//密码


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Config.SetStatusBar(this);


        IsDestroy = false;
        ThisContext = this;
        ThisActivity = this;

        Common.context = this;
        Common.SoftName = getString(R.string.app_name);

        IntentFilter filter = new IntentFilter();
        filter.addAction("GCJS_WX_Login");
        registerReceiver(_Receiver, filter);

        login = (TextView) findViewById(R.id.login);
        input_account = (EditText) findViewById(R.id.input_account);
        input_password = (EditText) findViewById(R.id.input_password);


        app_title_center = (TextView) findViewById(R.id.app_title_center);
        app_title_left = (ImageView) findViewById(R.id.app_title_left);
        app_title_right = (ImageView) findViewById(R.id.app_title_right);
        app_title_right2 = (TextView) findViewById(R.id.app_title_right2);
        app_title_right.setVisibility(View.GONE);
        app_title_right2.setVisibility(View.GONE);
        app_title_center.setText("登录");
        app_title_left.setImageResource(R.drawable.back);
        app_title_left.setVisibility(View.INVISIBLE);

        widget_1 = (LinearLayout) findViewById(R.id.widget_1);
        widget_1.setVisibility(View.GONE);
        widget_2 = (TextView) findViewById(R.id.widget_2);

        widget_1.getChildAt(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MessageBox().Show(ThisContext, getString(R.string.Tip), "未开放", "", getString(R.string.OK));
            }
        });
        widget_1.getChildAt(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WXApi = WXAPIFactory.createWXAPI(ThisContext, Config.WX_APP_ID, false);
                WXApi.registerApp(Config.WX_APP_ID);

                SendAuth.Req req = new SendAuth.Req();
                req.scope = "snsapi_userinfo";
                req.state = "Login";
                WXApi.sendReq(req);
            }
        });
        widget_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ThisContext, RegActivity.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = input_account.getText().toString().trim();
                String temp1 = input_password.getText().toString().trim();
                if (temp==null||temp1==null){
                    Toast.makeText(LoginActivity.this, "请输入账号密码", Toast.LENGTH_SHORT).show();
                }else{
                    List<String> photos = new ArrayList<>();
                    List<MultipartBody.Part> parts = null;
                    Map<String, RequestBody> params = new HashMap<>();
                    params.put("name", RetrofitUtils.convertToRequestBody("测试杰"));
                    params.put("id_num", RetrofitUtils.convertToRequestBody("441421199910100000"));

                    LoginController.login(params, parts, new InterfaceManger.OnRequestListener() {
                        @Override
                        public void onSuccess(Object success) {
                            LoginMessage workTaskProjectMessage = (LoginMessage) success;
                            Config.Ljob = workTaskProjectMessage.getContent().getJob();
                            Config.Ltoken = workTaskProjectMessage.getContent().getToken();
                            Config.Luser_name = workTaskProjectMessage.getContent().getUser_name();
                            Config.Lwopen = workTaskProjectMessage.getContent().getWopen_id();
                            Config.Luserid = workTaskProjectMessage.getContent().getUserId();
                            android.util.Log.e("onSuccess: ","12312312312");
//                            startActivity(new Intent(ThisContext, Main1Activity.class));
                            Intent intent = new Intent(LoginActivity.this, Main1Activity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onError(String error) {
                            Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        CheckUserLogin();
    }

    @Override
    protected void onDestroy() {
        IsDestroy = true;
        super.onDestroy();
        unregisterReceiver(_Receiver);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    void CheckUserLogin() {
        if (UserInfo.IsLogin()) {
            startActivity(new Intent(ThisContext, MainActivity.class));
            finish();
            return;
        }
    }

    BroadcastReceiver _Receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("GCJS_WX_Login")) {
                try {
                    if (intent.getStringExtra("Status").equals("OK")) {
                        new HttpCls2(ThisContext, HttpHandler, "getwxopenid", 0, "正在处理...", "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + Config.WX_APP_ID + "&secret=" + Config.WX_Secret + "&code=" + intent.getStringExtra("Code") + "&grant_type=authorization_code", "Get", null, 10).Begin();
                    }
                } catch (Exception e) {
                }
            }
        }
    };

    @SuppressLint("HandlerLeak")
    Handler HttpHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (IsDestroy || msg.obj == null) return;
            String FunName = msg.obj.toString();
            if (FunName.equals("getwxopenid")) {
                try {
                    JSONObject _JSONObject = new JSONObject(msg.getData().getString("ReturnValue"));
                    if (_JSONObject.has("openid")) {
                        String openid = _JSONObject.getString("openid");

                        Bundle _Bundle = new Bundle();
                        _Bundle.putString("OpenID", openid);
                        String ParmStr = "?wopen_id=" + Common.UrlEncoded(openid);
                        new HttpCls2(ThisContext, HttpHandler, "wxlogin", 0, "正在处理...", Config.ServiceUrl + "index.php/api/login/judge_weixin_login" + ParmStr, "Get", null, 10, _Bundle).Begin();
                    } else {
                        new MessageBox().Show(ThisContext, getString(R.string.Tip), "授权失败", "", getString(R.string.OK));
                    }
                    return;
                } catch (Exception e) {
                }
                new MessageBox().Show(ThisContext, getString(R.string.Tip), getString(R.string.NetErr), "", getString(R.string.OK));
            } else if (FunName.equals("wxlogin")) {
                try {
                    JSONObject _JSONObject = new JSONObject(msg.getData().getString("ReturnValue"));
                    if (_JSONObject.getBoolean("status")) {
                        UserInfo.Login(ThisContext, _JSONObject.getJSONObject("content"));
                        CheckUserLogin();
                    } else {
                        startActivity(new Intent(ThisContext, StringActivity.class).putExtra("Type", 1).putExtra("Value", "").putExtra("Int", 0).putExtra("Str", msg.getData().getBundle("Bundle").getString("OpenID")));
                    }
                    return;
                } catch (Exception e) {
                }
                new MessageBox().Show(ThisContext, getString(R.string.Tip), getString(R.string.NetErr), "", getString(R.string.OK));
            }
        }
    };
}
