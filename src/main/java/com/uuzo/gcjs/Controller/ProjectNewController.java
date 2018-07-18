package com.uuzo.gcjs.Controller;

import android.util.Log;

import com.google.gson.Gson;
import com.uuzo.gcjs.Manager.InterfaceManger;
import com.uuzo.gcjs.Moudule.Member;
import com.uuzo.gcjs.Moudule.Project;
import com.uuzo.gcjs.Utils.RetrofitUtils;

import org.json.JSONArray;
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
 * Created by jie on 2018/7/17.
 */

public class ProjectNewController {
    /**
     * 显示工程标段信息
     */
    public static void show_notice_project(Map<String, RequestBody> map, List<MultipartBody.Part> parts, final InterfaceManger.OnRequestListener listener) {
        Call<ResponseBody> call = RetrofitUtils.getInstance().show_notice_project(map, parts);
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
                        listener.onSuccess(new Gson().fromJson(body, Project.class));
                    } else {
//                        listener.onError("获取失败:" + jsonObject.getString("content"));
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

    public static void uploadItemFile(Map<String, RequestBody> map, MultipartBody.Part parts, final InterfaceManger.OnRequestListener listener) {
        Call<ResponseBody> call = RetrofitUtils.getInstance().uploadItemFile(map, parts);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (listener == null) {
                    return;
                }
                if (!response.isSuccessful() || response == null) {
                    listener.onError(String.valueOf(response.code()));
                    return;
                }
                try {
                    String body = response.body().string();
                    Log.e("onResponse:1234", body);
                    JSONObject jsonObject = new JSONObject(body);
                    Boolean status = jsonObject.getBoolean("status");
                    String address = jsonObject.getString("content");
                    Object object = body;
                    if (status) {
                        Log.e("onResponse:",address);
                        listener.onSuccess(address);
                    } else {
                        listener.onError("上传失败");
                    }
                } catch (Exception e) {
                    listener.onError(e.toString());
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
                if (t.toString().contains("ConnectException")) {
                    listener.onError("网络异常");
                } else {
                    listener.onError("网络异常");
                }
                listener.onComplete();
            }
        });
    }

    /**
     * 提交工程段信息
     */
    public static void lixiang(Map<String, RequestBody> map, List<MultipartBody.Part> parts, final InterfaceManger.OnRequestListener listener) {
        Call<ResponseBody> call = RetrofitUtils.getInstance().lixiang(map, parts);
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
                        listener.onSuccess("提交成功");
                    } else {
//                        listener.onError("获取失败:" + jsonObject.getString("content"));
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

    /**
     * 显示人员信息
     */
    public static void choice(Map<String, RequestBody> map, List<MultipartBody.Part> parts, final InterfaceManger.OnRequestListener listener) {
        Call<ResponseBody> call = RetrofitUtils.getInstance().choice(map, parts);
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
                        listener.onSuccess(new Gson().fromJson(body, Member.class));
                    } else {
//                        listener.onError("获取失败:" + jsonObject.getString("content"));
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

    /**
     * 提交项目
     */
    public static void addOneProject(Map<String, RequestBody> map, List<MultipartBody.Part> parts, final InterfaceManger.OnRequestListener listener) {
        Call<ResponseBody> call = RetrofitUtils.getInstance().addOneProject(map, parts);
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
                        listener.onSuccess(new Gson().fromJson(body, Member.class));
                    } else {
//                        listener.onError("获取失败:" + jsonObject.getString("content"));
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
