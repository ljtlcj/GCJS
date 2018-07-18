package com.uuzo.gcjs;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class UploadCls extends Thread
{
	Handler handle = null;
	String HandlerObj="";
    ProgressDialog progressDialog = null;  
    String LoadingStr="";
    String UrlStr="";
    Object[] Parms=null;
    long BeforeSleepTime=0;
    int TimeOutTime=10;//超时时间，单位：秒
    Context _ThisActivity;
	Boolean IsDestroy=false;
	Object LockObj=new Object();
	Boolean IsSendMessage=false;
	File[] UploadFile;
	String FileName="";
	Boolean FileIsDel=false;
    String FileParmName="";
	
    // 构造函数  
    public UploadCls(Context _Activity, Handler hander, String _HandlerObj, long _BeforeSleepTime, String _LoadingStr, String _UrlStr, String _FileName, File _File, int _TimeOutTime)
    {
    	_ThisActivity=_Activity;
        handle = hander;
        HandlerObj=_HandlerObj;
        BeforeSleepTime=_BeforeSleepTime;
        LoadingStr=_LoadingStr;
        UrlStr=_UrlStr;
        FileName=_FileName;
        UploadFile=new File[]{_File};
        TimeOutTime=_TimeOutTime;
    }

    // 构造函数  
    public UploadCls(Context _Activity, Handler hander, String _HandlerObj, long _BeforeSleepTime, String _LoadingStr, String _UrlStr, String _FileName, File _File, int _TimeOutTime, Boolean _FileIsDel)
    {
    	_ThisActivity=_Activity;
        handle = hander;
        HandlerObj=_HandlerObj;
        BeforeSleepTime=_BeforeSleepTime;
        LoadingStr=_LoadingStr;
        UrlStr=_UrlStr;
        FileName=_FileName;
        UploadFile=new File[]{_File};
        TimeOutTime=_TimeOutTime;
        FileIsDel=_FileIsDel;
    }

    public UploadCls(Context _Activity, Handler hander, String _HandlerObj, long _BeforeSleepTime, String _LoadingStr, String _UrlStr, String _FileName, File _File, int _TimeOutTime, Boolean _FileIsDel, String _FileParmName)
    {
        _ThisActivity=_Activity;
        handle = hander;
        HandlerObj=_HandlerObj;
        BeforeSleepTime=_BeforeSleepTime;
        LoadingStr=_LoadingStr;
        UrlStr=_UrlStr;
        FileName=_FileName;
        UploadFile=new File[]{_File};
        TimeOutTime=_TimeOutTime;
        FileIsDel=_FileIsDel;
        FileParmName=_FileParmName;
    }

    // 构造函数
    public UploadCls(Context _Activity, Handler hander, String _HandlerObj, long _BeforeSleepTime, String _LoadingStr, String _UrlStr, File[] _File, int _TimeOutTime, Boolean _FileIsDel)
    {
        _ThisActivity=_Activity;
        handle = hander;
        HandlerObj=_HandlerObj;
        BeforeSleepTime=_BeforeSleepTime;
        LoadingStr=_LoadingStr;
        UrlStr=_UrlStr;
        FileName="";
        UploadFile=_File;
        TimeOutTime=_TimeOutTime;
        FileIsDel=_FileIsDel;
    }
  
    /** 
     * 启动线程 
     */  
    public void Begin()
    {  
    	if (LoadingStr!="")
    	{
    		progressDialog = ProgressDialog.show(_ThisActivity, Common.SoftName, LoadingStr, true);
    	}
    	Thread_TimeToDoing.start();
        this.start();  
    }  
  
    Thread Thread_TimeToDoing = new Thread()
	{
	    public void run()
	    {
	    	int TimeNum=0;
	    	while(!IsDestroy)
	    	{
	    		try
		    	{
	    			TimeNum++;
	    			
	    			if (TimeNum>=TimeOutTime+BeforeSleepTime/1000)
	    			{
	    				IsDestroy=true;
	    				SendMessage("NetError");
	    			}
	    			
					Thread.sleep(1000);
				}
		    	catch (Exception e){}
	    	}
	    }
	};
	
	void SendMessage(String _ReturnValue)
    {
		if (progressDialog!=null)
    	{
    		try
    		{
    			//取消进度对话框  
    			progressDialog.dismiss();
    		}
    		catch (Exception e)
    		{
    			
    		}
    	}
		
    	synchronized(LockObj)
		{
	    	if (handle!=null && !IsSendMessage)
	    	{
	    		IsSendMessage=true;
		        Message message = handle.obtainMessage();
		        message.obj=HandlerObj;
		        Bundle b = new Bundle();  
		        b.putString("ReturnValue", _ReturnValue);
		        message.setData(b);  
		        handle.sendMessage(message);
	    	}
		}
    }
	
    /** 
     * 线程运行 
     */  
    @Override  
    public void run()
    {
        super.run();
        if (BeforeSleepTime>0)
        {
        	try
        	{
				Thread.sleep(BeforeSleepTime);
			} catch (Exception e) {	}
        }
        String ReturnValue="NetError";
        try
        {
        	String end ="\r\n";  
        	String twoHyphens ="--";  
        	String boundary ="*****";  

        	HttpURLConnection _HttpURLConnection=null;
    		try
    		{
    			URL _URL = new URL(UrlStr);  
    			_HttpURLConnection = (HttpURLConnection)_URL.openConnection();
    			_HttpURLConnection.setConnectTimeout(TimeOutTime*1000);
    			_HttpURLConnection.setReadTimeout(TimeOutTime*1000);
    			_HttpURLConnection.setDoInput(true);  
    			_HttpURLConnection.setDoOutput(true);
    			_HttpURLConnection.setDefaultUseCaches(false);
    			_HttpURLConnection.setUseCaches(false);
    			_HttpURLConnection.setRequestMethod("POST");
    			_HttpURLConnection.setRequestProperty("Charset", "UTF-8"); 
    			_HttpURLConnection.setRequestProperty("Connection", "Keep-Alive");
    			_HttpURLConnection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
    			DataOutputStream _DataOutputStream = new DataOutputStream(_HttpURLConnection.getOutputStream());
    			for (int i=0;i<UploadFile.length;i++) {
                    _DataOutputStream.writeBytes(twoHyphens + boundary + end);
                    _DataOutputStream.writeBytes("Content-Disposition: form-data; " +
                            "name=\""+(UploadFile.length==1 && !FileParmName.equals("")?FileParmName:"file"+(i+1))+"\";filename=\"" + (UploadFile.length==1 && !FileName.equals("")?FileName:UploadFile[i].getName()) + "\"" + end);
                    _DataOutputStream.writeBytes(end);
                    FileInputStream _FileInputStream = new FileInputStream(UploadFile[i]);
                    int bufferSize = 1024;
                    byte[] buffer = new byte[bufferSize];
                    int length = -1;
                    while ((length = _FileInputStream.read(buffer)) != -1) {
                        _DataOutputStream.write(buffer, 0, length);
                    }
                    _DataOutputStream.writeBytes(end);
                    _FileInputStream.close();
                }
                _DataOutputStream.writeBytes(twoHyphens + boundary + end);
    		    _DataOutputStream.flush();
    		    if (FileIsDel)
    		    {
                    for (int i=0;i<UploadFile.length;i++) {
                        try {
                            UploadFile[i].delete();
                        } catch (Exception e) { }
                    }
    		    }
    			if (_HttpURLConnection.getResponseCode() == 200)
    			{
    				InputStreamReader in = new InputStreamReader(_HttpURLConnection.getInputStream());
    				BufferedReader _BufferedReader = new BufferedReader(in);
    				ReturnValue="";
    				String inputLine = null;    
    				while (((inputLine = _BufferedReader.readLine()) != null))
    				{
    					ReturnValue += inputLine + "\n";
    				}  
    				_BufferedReader.close();
    				in.close();
    			}
    			else
    			{
    				ReturnValue=_HttpURLConnection.getResponseMessage();
    			}
    			_DataOutputStream.close();
    		}
    		catch (Exception e)
    		{
    			ReturnValue="NetError";
    		}
    		try
			{
				_HttpURLConnection.disconnect();
			}catch(Exception e){ }
        	
        	if (!IsDestroy)
            {
            	IsDestroy=true;
            	SendMessage(ReturnValue);
            }            
        }
        catch(Exception ex)
        {
        	ReturnValue="NetError";
        	if (!IsDestroy)
            {
	        	IsDestroy=true;
	        	SendMessage(ReturnValue);
            }
        }
    }  
}
