package com.uuzo.gcjs;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.view.Window;
import android.view.WindowManager;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;


public class Config {
    public static String Lwopen="";
    public static String Luser_name="";
    public static String Ljob="";
    public static int Luserid = 0;
    public static String Ltoken="";

    public static String APPType="GCJS";
	public static String ServiceUrl="http://www.gcgl.online/";
    public static String WX_APP_ID="wxba422dcaa259c42e";//微信APIID
    public static String WX_Secret="5b40d80a1184db6ed52513ce21c19c46";
    public static ClsClass.Cls NowCls=null;
    public static List<ClsClass.Cls> NowList=new ArrayList<>();

	public static void SetStatusBar(Activity _Activity)
	{
        try {
            if (Build.VERSION.SDK_INT >= 21) {
                Window window = _Activity.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(_Activity.getResources().getColor(R.color.main));
            }
        } catch (Exception e) {

        }
	}
    public static void SetStatusBar(Activity _Activity,String _ColorStr)
    {
        try {
            if (Build.VERSION.SDK_INT >= 21) {
                Window window = _Activity.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(_ColorStr.equals("")?_Activity.getResources().getColor(R.color.main):Color.parseColor(_ColorStr));
            }
        } catch (Exception e) {

        }
    }

    public static String GetSDCardPath(Context _Context)
    {
        File sdcardPath = null;
        try {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                sdcardPath = Environment.getExternalStorageDirectory();
            } else {
                sdcardPath = _Context.getFilesDir();
            }
        }
        catch (Exception e){
            sdcardPath = _Context.getFilesDir();
        }
        return sdcardPath.getAbsolutePath();
    }
	public static String DirPath(Context _Context)
	{
		return GetSDCardPath(_Context)+"/"+APPType+"/";
	}

	public  static  Uri GetFileUri(Context _Context,File _File){
        if (Build.VERSION.SDK_INT>=24){
            return FileProvider.getUriForFile(_Context,"com.uuzo.gcjs.fileprovider", _File);
        }
        else{
            return Uri.fromFile(_File);
        }
    }
	public static void DeleteTempFile(Context _Context){
        try {
            File _File = new File(Config.DirPath(_Context));
            if (_File.exists()){
                File[] _Files=_File.listFiles(new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String name) {
                        if (name.split("\\.").length==2 && name.split("\\.")[1].toLowerCase().equals("jpg") && name.toLowerCase().startsWith("takephoto_")) return true;
                        return false;
                    }
                });
                if (_Files.length>0){
                    for (File file:_Files) {
                        try {
                            file.delete();
                        }
                        catch (Exception e1){}
                    }
                }
            }
        }
        catch (Exception e){}
    }

    public static String GetTYSQTypeName(int _Type){
        String ReturnValue="";
//        if (_Type==1) ReturnValue="未审批";
//        else if (_Type==2) ReturnValue="审批通过";
//        else if (_Type==3) ReturnValue="审批不通过";

        if (_Type==1) ReturnValue="审批通过";
        else if (_Type==2) ReturnValue="审批不通过";
        else if (_Type==3) ReturnValue="未审批";
        return ReturnValue;
    }

    public static String GetCJFTypeName(int _Type){
        String ReturnValue="";
        if (_Type==1) ReturnValue="业主单位";
        else if (_Type==2) ReturnValue="设计单位";
        else if (_Type==3) ReturnValue="监理单位";
        else if (_Type==4) ReturnValue="合同单位";
        else if (_Type==5) ReturnValue="施工单位";
        return ReturnValue;
    }

    public static String GetProjectTypeName(int _Type){
        String ReturnValue="";
        if (_Type==1) ReturnValue="系统内工程";
        else if (_Type==2) ReturnValue="系统外工程";
        else if (_Type==3) ReturnValue="业扩工程";
        else if (_Type==4) ReturnValue="其它";
        return ReturnValue;
    }

    public static String GetStatusName(int _Status){
	    String ReturnValue="";
	    if (_Status==0) ReturnValue="未计划";
        else if (_Status==1) ReturnValue="已计划";
        else if (_Status==2) ReturnValue="进行中";
        else if (_Status==3) ReturnValue="已完工";
        else if (_Status==4) ReturnValue="已超期";
        else if (_Status==5) ReturnValue="待验收";
        else if (_Status==6) ReturnValue="已验收";
        else if (_Status==7) ReturnValue="质量整改";
        else if (_Status==8) ReturnValue="安全整改";
        return ReturnValue;
    }

    public static String GetStatusName(int _Status,int _JD){
        String ReturnValue="";
        if (_Status==0) ReturnValue="未计划";
        else if (_Status==1) ReturnValue="已计划";
        else if (_Status==2) ReturnValue="进行中 "+_JD+"%";
        else if (_Status==3) ReturnValue="已完工";
        else if (_Status==4) ReturnValue="已超期 "+_JD+"%";
        else if (_Status==5) ReturnValue="待验收";
        else if (_Status==6) ReturnValue="已验收";
        else if (_Status==7) ReturnValue="质量整改 "+_JD+"%";
        else if (_Status==8) ReturnValue="安全整改 "+_JD+"%";
        return ReturnValue;
    }

    public static String GetAJStatusName(int _Type){
        String ReturnValue="未检";
        if (_Type==1) ReturnValue="已检";
        return ReturnValue;
    }


	

	/** 获取本地保存的登录信息 **/
	public static String GetSharedPreferences_Data_Login(Context _Context)
	{
		return _Context.getSharedPreferences("data", Context.MODE_PRIVATE).getString("Login", "");
	}
	/** 设置本地保存的登录信息 **/
	public static Boolean SetSharedPreferences_Data_Login(Context _Context,String _Value)
	{
		return _Context.getSharedPreferences("data", Context.MODE_PRIVATE).edit().putString("Login", _Value).commit();
	}


}
