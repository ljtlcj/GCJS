package com.uuzo.gcjs;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class HttpCls2 extends Thread
{
	Handler handle = null;
	String HandlerObj="";
    ProgressDialog progressDialog = null;  
    String LoadingStr="";
    String UrlStr="";
    String HttpType="";
    Object[] Parms=null;
    long BeforeSleepTime=0;
    int TimeOutTime=10;//超时时间，单位：秒
    Context _ThisActivity;
	Boolean IsDestroy=false;
	Object LockObj=new Object();
	Boolean IsSendMessage=false;
	Bundle BundleObj=null;
	
    // 构造函数  
    public HttpCls2(Context _Activity, Handler hander, String _HandlerObj, long _BeforeSleepTime, String _LoadingStr, String _UrlStr, String _HttpType, Object[] _Parms, int _TimeOutTime)
    {
    	_ThisActivity=_Activity;
        handle = hander;
        HandlerObj=_HandlerObj;
        BeforeSleepTime=_BeforeSleepTime;
        LoadingStr=_LoadingStr;
        UrlStr=_UrlStr;
        HttpType=_HttpType;
        Parms=_Parms;
        TimeOutTime=_TimeOutTime;
    } 
    // 构造函数  
    public HttpCls2(Context _Activity, Handler hander, String _HandlerObj, long _BeforeSleepTime, String _LoadingStr, String _UrlStr, String _HttpType, Object[] _Parms, int _TimeOutTime, Bundle _BundleObj)
    {
    	_ThisActivity=_Activity;
        handle = hander;
        HandlerObj=_HandlerObj;
        BeforeSleepTime=_BeforeSleepTime;
        LoadingStr=_LoadingStr;
        UrlStr=_UrlStr;
        HttpType=_HttpType;
        Parms=_Parms;
        TimeOutTime=_TimeOutTime;
        BundleObj=_BundleObj;
    } 
  
    /** 
     * 启动线程 
     */  
    public void Begin()
    {  
    	if (LoadingStr!="")
    	{
    	    try {
                progressDialog = ProgressDialog.show(_ThisActivity, Common.SoftName, LoadingStr, true);
            }
            catch (Exception e1){}
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
		        b.putBundle("Bundle", BundleObj);
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
        	if (HttpType.equalsIgnoreCase("Post"))
        	{
        		try
        		{
	        		HttpPost httpRequest = new HttpPost(UrlStr);
	        		List<NameValuePair> params = new ArrayList<NameValuePair>();
	        		if (Parms!=null)
	                {
		               	 for(int i=0;i<Parms.length;i++)
		               	 {
		               		 if (i+1>=Parms.length) break;
		               		 params.add(new BasicNameValuePair(Parms[i].toString(), Parms[i+1].toString()));
		               		 i++;
		               	 }
	                }
        			httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
        			HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest);
        			if(httpResponse.getStatusLine().getStatusCode() == 200) 
        			{
        				ReturnValue = EntityUtils.toString(httpResponse.getEntity());
        			}
        			else
        			{
        				ReturnValue="NetError";
        			}
        		}
        		catch (Exception e)
        		{
        			ReturnValue="NetError";
        		}
        	}
        	else //Get
        	{
        		HttpURLConnection _HttpURLConnection=null;
        		try
        		{
        			URL _URL = new URL(UrlStr);  
        			_HttpURLConnection = (HttpURLConnection)_URL.openConnection();
        			_HttpURLConnection.setConnectTimeout(TimeOutTime*1000);
        			_HttpURLConnection.setDefaultUseCaches(false);
        			_HttpURLConnection.setUseCaches(false);
        			_HttpURLConnection.setRequestMethod("GET");
        			_HttpURLConnection.setRequestProperty("Connection", "Keep-Alive");
        			if (_HttpURLConnection.getResponseCode() == 200)
        			{
        				InputStreamReader in = new InputStreamReader(_HttpURLConnection.getInputStream());
        				BufferedReader buffer = new BufferedReader(in);
        				ReturnValue="";
        				String inputLine = null;    
        				while (((inputLine = buffer.readLine()) != null))
        				{
        					ReturnValue += inputLine + "\n";
        				}  
        				buffer.close();
        				in.close();
        			}
        		}
        		catch (Exception e)
        		{
        			ReturnValue="NetError";
        		}
        		try
				{
					_HttpURLConnection.disconnect();
				}catch(Exception e){ }
        	}
        	
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
