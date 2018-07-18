package com.uuzo.gcjs.Controller;

import android.util.Log;

import com.google.gson.Gson;
import com.uuzo.gcjs.Manager.InterfaceManger;
import com.uuzo.gcjs.Moudule.LoginMessage;
import com.uuzo.gcjs.Moudule.Project;
import com.uuzo.gcjs.Utils.RetrofitUtils;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jie on 2018/7/18.
 */

public class LoginController {
    /**
     * 登陆模块
     */
    public static void login(Map<String, RequestBody> map, List<MultipartBody.Part> parts, final InterfaceManger.OnRequestListener listener) {
        Call<ResponseBody> call = RetrofitUtils.getInstance().login(map, parts);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (listener == null) {
                    return;
                }
                if (!response.isSuccessful() || response == null) {
                    listener.onError("服务器错误，error code:" + response.code());
                    return;
                }
                try {
                    String body = response.body().string().toString();
                    JSONObject jsonObject = new JSONObject(body);
                    Log.e("onResponse", body);
                    boolean status = jsonObject.getBoolean("status");
                    if (status) {
                        listener.onSuccess(new Gson().fromJson(body, LoginMessage.class));
                    } else {
                        listener.onError("获取失败:" + jsonObject.getString("content"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                listener.onComplete();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (listener == null) {
                    return;
                }
                Log.e("onFailure", t.toString());
                listener.onError(t.toString());
                listener.onComplete();
            }
        });
    }
}
