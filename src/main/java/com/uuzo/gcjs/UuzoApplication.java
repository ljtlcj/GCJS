package com.uuzo.gcjs;


import android.app.ActivityManager;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import java.io.File;
import java.util.List;

public class UuzoApplication extends MultiDexApplication {

	public static Context applicationContext;
	@Override
	public void onCreate() {
		super.onCreate();

		String processName=getProcessName(getApplicationContext());
		if (processName.equals("com.uuzo.gcjs")) {
            applicationContext = getApplicationContext();
            try
            {
                File _File=new File(Config.DirPath(getApplicationContext()));
                if (!_File.exists()) _File.mkdir();
            }
            catch(Exception e){}
        }
	}

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    String getProcessName(Context context) {
	    try {
            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
            if (runningApps == null) return "";
            for (ActivityManager.RunningAppProcessInfo proInfo : runningApps) {
                if (proInfo.pid == android.os.Process.myPid()) {
                    if (proInfo.processName != null) {
                        return proInfo.processName;
                    }
                }
            }
        }
        catch (Exception e){}
        return "";
    }
}
