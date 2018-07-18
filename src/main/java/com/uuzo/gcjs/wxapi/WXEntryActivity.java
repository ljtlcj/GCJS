/*
 * 官网地站:http://www.mob.com
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 mob.com. All rights reserved.
 */

package com.uuzo.gcjs.wxapi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.uuzo.gcjs.Config;
import com.uuzo.gcjs.R;


public class WXEntryActivity extends Activity  implements IWXAPIEventHandler {

    Boolean IsDestroy=false;
    Context ThisContext;
    Activity ThisActivity;

    IWXAPI WXApi;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);

        IsDestroy=false;
        ThisContext=this;
        ThisActivity=this;


        WXApi = WXAPIFactory.createWXAPI(ThisContext, Config.WX_APP_ID, false);

        try {
            if (!WXApi.handleIntent(getIntent(), this)){
                finish();
                return;
            }
        } catch (Exception e) {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        IsDestroy=true;
        super.onDestroy();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        setIntent(intent);

        try {
            if (!WXApi.handleIntent(intent, this)){
                finish();
                return;
            }
        } catch (Exception e) {
            finish();
        }
    }


    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        String result = "";

        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                result="OK";
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = "Cancel";
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = "Err";
                break;
            case BaseResp.ErrCode.ERR_UNSUPPORT:
                result = "Err";
                break;
            default:
                result = "Err";
                break;
        }
        SendAuth.Resp _Resp= (SendAuth.Resp)baseResp;

        Intent _Intent=new Intent("GCJS_WX_"+_Resp.state);
        _Intent.putExtra("Status",result);
        _Intent.putExtra("Code",_Resp.code);
        sendBroadcast(_Intent);
        finish();
    }
}
