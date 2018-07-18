package com.uuzo.gcjs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

public class Main_XM_Fragment extends Fragment
{
	Boolean IsDestroy=false;
	Context ThisContext;
    Activity ThisActivity;

    LinearLayout widget_1,widget_2,widget_3,widget_4,widget_5;

	public Main_XM_Fragment()
	{
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_main_xm, container, false);
		
		IsDestroy=false;
        ThisContext=getContext();
        ThisActivity=getActivity();


        widget_1 = (LinearLayout)view.findViewById(R.id.widget_1);
        widget_2 = (LinearLayout)view.findViewById(R.id.widget_2);
        widget_3 = (LinearLayout)view.findViewById(R.id.widget_3);
        widget_4 = (LinearLayout)view.findViewById(R.id.widget_4);
        widget_5 = (LinearLayout)view.findViewById(R.id.widget_5);

        widget_1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ThisContext)
                        .setItems(new String[] { ">> 获取立项表模版", ">> 上传立项表" }, new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                switch (which)
                                {
                                    case 0:
                                        Intent intent = new Intent(Intent.ACTION_SEND);
                                        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(GetDemoFilePath())));
                                        intent.setType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                                        startActivity(intent);
                                        break;
                                    case 1:
                                        Intent intentFromFile = new Intent();
                                        intentFromFile.setType("*/*"); // 设置文件类型
                                        intentFromFile.addCategory(Intent.CATEGORY_OPENABLE);
                                        intentFromFile.setAction(Intent.ACTION_GET_CONTENT);
                                        startActivityForResult(intentFromFile,1);
                                        break;
                                }
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                dialog.dismiss();
                            }
                        }).show();
            }
        });

        widget_2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                new MessageBox().Show(ThisContext, "提示", "待开放", "", getString(R.string.OK));
            }
        });

        widget_3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                new MessageBox().Show(ThisContext, "提示", "待开放", "", getString(R.string.OK));
            }
        });

        widget_4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                new MessageBox().Show(ThisContext, "提示", "待开放", "", getString(R.string.OK));
            }
        });

        widget_5.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                new MessageBox().Show(ThisContext, "提示", "待开放", "", getString(R.string.OK));
            }
        });

		return view;
	}

	@Override
	public void onDestroyView() {
		IsDestroy=true;
		super.onDestroyView();		
	}

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK && data != null && data.getData() != null) {
                    String FilePath= Common.getPath(ThisContext,data.getData());
                    String FileExt=FilePath.split("\\.")[FilePath.split("\\.").length-1].toLowerCase();
                    if (!FileExt.equals("xls") && !FileExt.equals("xlsx")){
                        new MessageBox().Show(ThisContext, getString(R.string.Tip), "不支持此文件格式", "", getString(R.string.OK));
                        return;
                    }

                    String ParmStr="?token="+ Common.UrlEncoded(UserInfo.Token);
                    new UploadCls(ThisContext, HttpHandler, "up", 0, "正在处理...", Config.ServiceUrl + "index.php/api/project/uploadProject" + ParmStr, Common.DateToStr(new Date(), "yyyyMMddHHmmss")+"."+FileExt, new File(FilePath), 120, false, "file").Begin();
                }
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    String GetDemoFilePath() {
        String fileName = "立项信息表.xlsx";

        try {
            File dir = new File(Config.DirPath(ThisContext));
            if (!dir.exists()) dir.mkdirs();

            File file = new File(Config.DirPath(ThisContext) + fileName);
            if (file.exists()) file.delete();
            InputStream ins = getResources().openRawResource(R.raw.demo);
            FileOutputStream fos = new FileOutputStream(file);
            byte[] buffer = new byte[8192];
            int count = 0;
            while ((count = ins.read(buffer)) > 0) {
                fos.write(buffer, 0, count);
            }
            fos.close();
            ins.close();
        } catch (Exception e) {

        }
        return Config.DirPath(ThisContext) + fileName;
    }

    @SuppressLint("HandlerLeak")
    Handler HttpHandler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            if (IsDestroy || msg.obj == null) return;
            String FunName = msg.obj.toString();
            if (FunName.equals("up")) {
                try
                {
                    String ReturnValue = msg.getData().getString("ReturnValue");
                    JSONObject _JSONObject = new JSONObject(ReturnValue);
                    if (_JSONObject.getBoolean("status")) {
                        JSONObject _JSONObject2= _JSONObject.getJSONObject("content");
                        JSONObject _JSONObject3=_JSONObject2.getJSONObject("project");

                        ClsClass.Cls _Cls=new ClsClass().new Cls();
                        _Cls.Int=1;
                        _Cls.Int_1=_JSONObject3.getInt("id");
                        _Cls.Str_1=_JSONObject3.getString("project_name");
                        _Cls.Int_2=_JSONObject3.getInt("project_type");
                        _Cls.Str_2=_JSONObject3.getString("batch");
                        _Cls.Str_3=_JSONObject3.getString("bureau");
                        _Cls.Str_4=_JSONObject3.getString("project_number");
                        _Cls.Str_5=_JSONObject3.getString("project_addr");
                        _Cls.Str_7=_JSONObject3.getString("start_time");
                        _Cls.Str_8=_JSONObject3.getString("end_time");
                        _Cls.Str_9=_JSONObject3.getString("action_unit");
                        _Cls.Int_4=_JSONObject3.getInt("submit_time");

                        JSONArray Item_JSONArray=_JSONObject2.getJSONArray("item");
                        for(int i=0;i<Item_JSONArray.length();i++) {
                            JSONObject JSONObject2=Item_JSONArray.getJSONObject(i);
                            ClsClass.Cls _Cls2=new ClsClass().new Cls();
                            _Cls2.Int=2;
                            _Cls2.Int_1=JSONObject2.getInt("id");
                            _Cls2.Int_2=JSONObject2.getInt("project_id");
                            _Cls2.Str_2=JSONObject2.getString("item_name");
                            _Cls2.Str_3=JSONObject2.getString("item_number");
                            _Cls2.Str_4=JSONObject2.getString("item_type");
                            _Cls2.Str_6=JSONObject2.getString("area");
                            _Cls2.Str_7=JSONObject2.getString("addr");
                            _Cls2.Int_3=JSONObject2.getInt("trouble");
                            _Cls2.Str_8=JSONObject2.getString("start_time");
                            _Cls2.Str_9=JSONObject2.getString("end_time");
                            _Cls2.Int_4=JSONObject2.getInt("work_day");
                            _Cls2.Str_10=JSONObject2.getString("situation");

                            _Cls.Int_8+= _Cls2.Int_4;//总工日

                            JSONArray works_JSONArray=JSONObject2.getJSONArray("work");
                            for(int j=0;j<works_JSONArray.length();j++) {
                                JSONObject works_JSONObject = works_JSONArray.getJSONObject(j);
                                ClsClass.Cls _Cls3=new ClsClass().new Cls();
                                _Cls3.Int=3;
                                _Cls3.Int_1=works_JSONObject.getInt("id");
                                _Cls3.Int_2=works_JSONObject.getInt("project_id");
                                _Cls3.Int_3=works_JSONObject.getInt("item_id");
                                _Cls3.Str_1=works_JSONObject.getString("ass_name");
                                _Cls3.Str_2=works_JSONObject.getString("ass_number");
                                _Cls3.Str_3=works_JSONObject.getString("ass_type");
                                _Cls3.Int_4=works_JSONObject.getInt("workload");
                                _Cls3.Str_4=works_JSONObject.getString("unit");
                                _Cls3.Str_5=works_JSONObject.getString("note");
                                _Cls3.Cls_1=_Cls2;
                                _Cls2.ClsList_2.add(_Cls3);
                            }
                            _Cls2.Cls_1=_Cls;

                            _Cls.ClsList_1.add(_Cls2);
                        }

                        JSONArray person_JSONArray=_JSONObject2.getJSONArray("person");
                        for(int i=0;i<person_JSONArray.length();i++) {
                            JSONObject JSONObject2 = person_JSONArray.getJSONObject(i);
                            ClsClass.Cls _Cls2 = new ClsClass().new Cls();
                            _Cls2.Int_1 = JSONObject2.getInt("id");
                            _Cls2.Int_2 = JSONObject2.getInt("project_id");
                            _Cls2.Int_3 = JSONObject2.getInt("type");
                            _Cls2.Str_1 = JSONObject2.getString("name");
                            _Cls2.Str_2 = JSONObject2.getString("job");
                            _Cls2.Str_3 = JSONObject2.getString("tel");
                            _Cls2.Str_4= JSONObject2.getString("e_mail");
                            _Cls2.Str_5= JSONObject2.getString("unit_name");
                            _Cls2.Str_6= JSONObject2.getString("departents");
                            _Cls2.Str_7= JSONObject2.getString("note");
                            _Cls.ClsList_2.add(_Cls2);
                        }

                        Config.NowCls=_Cls;
                        startActivity(new Intent(ThisContext, XM_Add_Activity.class));
                    }
                    else {
                        new MessageBox().Show(ThisContext, "提示", _JSONObject.getString("content"), "", getString(R.string.OK));
                    }
                    return;
                } catch (Exception e) { }
                new MessageBox().Show(ThisContext, "提示", getString(R.string.NetErr), "", getString(R.string.OK));
            }
        }
    };
}
