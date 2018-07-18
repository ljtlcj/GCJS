package com.uuzo.gcjs;

import android.content.Context;

import org.json.JSONObject;

import java.io.File;

/** 当前帐号信息 **/
public class UserInfo {
	/** 帐号ID **/
	public static int ID=0;
	/** 登录帐号 **/
	public static String LoginName="";
	/** 登录密码 **/
	public static String Password="";
	/** 姓名 **/
	public static String RealName="";
	/** 身份证号码 **/
	public static String IDCard="";

    public static String Token="";

    public static String Department="";

    public static String Job="";

	public static int CompanyID=0;

	
	public static void Clear(Context _Context)
	{
		UserInfo.ID=0;
		UserInfo.Password="";
		UserInfo.RealName="";
		UserInfo.IDCard="";
        UserInfo.Token="";
        UserInfo.Department="";
        UserInfo.Job="";
        UserInfo.CompanyID=0;

        UserInfo.Save(_Context);
	}

	public static Boolean IsLogin()
    {
	    return UserInfo.ID>0 && !UserInfo.Token.equals("");
    }

    public static void Login(Context _Context, JSONObject _JSONObject)
    {
        try {
            UserInfo.ID=_JSONObject.has("userId")?_JSONObject.getInt("userId"):0;
            UserInfo.RealName = _JSONObject.getString("user_name");
            UserInfo.IDCard = _JSONObject.has("id_card")?_JSONObject.getString("id_card"):"";
            UserInfo.Token = _JSONObject.getString("token");
            UserInfo.Department = _JSONObject.has("department")?_JSONObject.getString("department"):"";
            UserInfo.Job = _JSONObject.has("job")?_JSONObject.getString("job"):"";
            UserInfo.CompanyID = _JSONObject.has("unitId")?_JSONObject.getInt("unitId"):0;

            UserInfo.Save(_Context);
        }
        catch (Exception e){}
    }

    public static void Save(Context _Context)
    {
        Config.SetSharedPreferences_Data_Login(_Context, UserInfo.ID
                +"|"+ UserInfo.LoginName.replace("|", "｜")
                +"|"+ UserInfo.Password.replace("|", "｜")
                +"|"+ UserInfo.RealName.replace("|", "｜")
                +"|"+ UserInfo.IDCard.replace("|", "｜")
                +"|"+ UserInfo.Token.replace("|", "｜")
                +"|"+ UserInfo.Department.replace("|", "｜")
                +"|"+ UserInfo.Job.replace("|", "｜")
                +"|"+ UserInfo.CompanyID);
    }
    public static void Init(Context _Context)
    {
        try {
            File _File = new File(Config.DirPath(_Context));
            if (!_File.exists()) _File.mkdirs();
        }
        catch (Exception e){}
        try {
            String[] StrSplit = Config.GetSharedPreferences_Data_Login(_Context).split("\\|");
            if (StrSplit.length >= 9) {
                UserInfo.ID = Integer.valueOf(StrSplit[0].trim());
                UserInfo.LoginName = StrSplit[1].trim();
                UserInfo.Password = StrSplit[2].trim();
                UserInfo.RealName = StrSplit[3].trim();
                UserInfo.IDCard = StrSplit[4].trim();
                UserInfo.Token = StrSplit[5].trim();
                UserInfo.Department = StrSplit[6].trim();
                UserInfo.Job = StrSplit[7].trim();
                UserInfo.CompanyID = Integer.valueOf(StrSplit[8].trim());
            }
        }
        catch (Exception e){
            UserInfo.Clear(_Context);
        }
    }
}
