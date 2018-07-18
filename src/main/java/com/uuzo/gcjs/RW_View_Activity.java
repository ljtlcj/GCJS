package com.uuzo.gcjs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class RW_View_Activity extends Activity {

	Boolean IsDestroy=false;
	Context ThisContext;
	Activity ThisActivity;
	
	TextView app_title_center,app_title_right2;
    ImageView app_title_left,app_title_right;

    TextView widget_0;
    LinearLayout widget_1,widget_2,widget_3,widget_4,widget_5,widget_6,widget_7,widget_8,widget_9,widget_10,widget_11,widget_12,widget_13,widget_14,widget_15;
	ClsClass.Cls _Cls;


    int UploadIndex=0;
    List<Cls> _List=new ArrayList<Cls>();
    MyListAdapter _MyListAdapter;
    String NowTakePhotoFileName="";

    Boolean IsAPZXR=false;
    String ChangeID="";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_rw_view);
        Config.SetStatusBar(this);

	    
	    IsDestroy=false;
	    ThisContext=this;
	    ThisActivity=this;

	    if (Config.NowCls==null)
		{
	    	finish();
			return;
		}
        _Cls= Config.NowCls;
        IsAPZXR=getIntent().getBooleanExtra("IsAPZXR",false);
	    
	    app_title_center = (TextView) findViewById(R.id.app_title_center);
        app_title_left = (ImageView) findViewById(R.id.app_title_left);
        app_title_right = (ImageView)findViewById(R.id.app_title_right);
        app_title_right2 = (TextView) findViewById(R.id.app_title_right2);
        app_title_right.setVisibility(View.GONE);
        app_title_right2.setVisibility(IsAPZXR?View.GONE:View.VISIBLE);
        app_title_right2.setText("变更");
        app_title_center.setText("任务详情");
        app_title_left.setImageResource(R.drawable.back);
        app_title_left.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) {
				finish();
			}
		});
        app_title_right2.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if (app_title_right2.getText().toString().equals("变更")){
                    app_title_right2.setText("取消");
                    widget_0.setVisibility(View.VISIBLE);
                    widget_6.setVisibility(View.GONE);
                    widget_7.setVisibility(View.VISIBLE);
                    widget_14.setVisibility(View.VISIBLE);
                    widget_15.setVisibility(View.VISIBLE);
                    Common.HideSoftInput(ThisActivity);
                }
                else if (app_title_right2.getText().toString().equals("取消")){
                    app_title_right2.setText("变更");
                    widget_0.setVisibility(View.GONE);
                    widget_6.setVisibility(View.VISIBLE);
                    widget_7.setVisibility(View.GONE);
                    widget_14.setVisibility(View.GONE);
                    widget_15.setVisibility(View.GONE);
                    Common.HideSoftInput(ThisActivity);
                }
            }
        });

        widget_0 = (TextView) findViewById(R.id.widget_0);
        widget_1 = (LinearLayout) findViewById(R.id.widget_1);
        widget_2 = (LinearLayout)findViewById(R.id.widget_2);
        widget_3 = (LinearLayout)findViewById(R.id.widget_3);
        widget_4 = (LinearLayout)findViewById(R.id.widget_4);
        widget_5 = (LinearLayout)findViewById(R.id.widget_5);
        widget_6 = (LinearLayout)findViewById(R.id.widget_6);
        widget_7 = (LinearLayout)findViewById(R.id.widget_7);
        widget_8 = (LinearLayout)findViewById(R.id.widget_8);
        widget_9 = (LinearLayout)findViewById(R.id.widget_9);
        widget_10 = (LinearLayout)findViewById(R.id.widget_10);
        widget_11 = (LinearLayout)findViewById(R.id.widget_11);
        widget_12 = (LinearLayout)findViewById(R.id.widget_12);
        widget_13 = (LinearLayout)findViewById(R.id.widget_13);
        widget_14 = (LinearLayout)findViewById(R.id.widget_14);
        widget_15 = (LinearLayout)findViewById(R.id.widget_15);

        widget_0.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if (widget_0.getText().toString().equals("安排执行人")){
                    startActivity(new Intent(ThisContext, XM_JDJH_View_RW_SelectMember_More_Activity.class));
                }
                else if (widget_0.getText().toString().equals("提交变更申请")){
                    String Value_1 = ((EditText) widget_7.getChildAt(1)).getText().toString().trim();
                    String Tip_1 = ((EditText) widget_7.getChildAt(1)).getHint().toString().trim();
                    String Value_2 = ((EditText) widget_15.getChildAt(1)).getText().toString().trim();
                    String Tip_2 = ((EditText) widget_15.getChildAt(1)).getHint().toString().trim();

                    if (Value_1.equals("")) {
                        new MessageBox().Show(ThisContext, getString(R.string.Tip), Tip_1, "", getString(R.string.OK));
                        return;
                    }
                    if (Value_2.equals("")) {
                        new MessageBox().Show(ThisContext, getString(R.string.Tip), Tip_2, "", getString(R.string.OK));
                        return;
                    }
                    if (_List.size() <= 1) {
                        new MessageBox().Show(ThisContext, getString(R.string.Tip), "请上传变更申请证明", "", getString(R.string.OK));
                        return;
                    }

                    Common.HideSoftInput(ThisActivity);

                    Object[] obj=new Object[]{
                            "token", UserInfo.Token,
                            "item_id",_Cls.Int_1,
                            "loads",Value_1,
                            "state",Value_2
                    };
                    new HttpCls2(ThisContext, HttpHandler, "check", 0, "正在提交申请...", Config.ServiceUrl + "index.php/api/project/changeOneWork", "Post", obj, 10).Begin();
                }
            }
        });


        ((TextView)widget_10.getChildAt(1)).setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v) {
                String ParmStr = "?token=" + Common.UrlEncoded(UserInfo.Token);
                ParmStr += "&id=" + _Cls.Int_1;
                ParmStr += "&type=1";
                new HttpCls2(ThisContext, HttpHandler, "down", 0, "正在获取...", Config.ServiceUrl + "index.php/api/progress/downloadAcc" + ParmStr, "Get", null, 10).Begin();
            }
        });



        _List.add(new Cls(null,"Add"));
        _MyListAdapter = new MyListAdapter();
        ((GridView)widget_14.getChildAt(1)).setAdapter(_MyListAdapter);
        ((GridView)widget_14.getChildAt(1)).setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
            {
                final Cls _Cls=(Cls)arg1.getTag();
                if (_Cls.FilePath.equals("Add"))
                {
                    new AlertDialog.Builder(ThisContext)
                            .setItems(new String[] { ">> 选择本地图片", ">> 拍照" }, new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    switch (which)
                                    {
                                        case 0:
                                            Intent intentFromGallery = new Intent();
                                            intentFromGallery.setType("image/*"); // 设置文件类型
                                            intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
                                            startActivityForResult(intentFromGallery,1);
                                            break;
                                        case 1:
                                            NowTakePhotoFileName="TakePhoto_"+ Common.DateToStr(new Date(), "yyyyMMddHHmmss")+".jpg";
                                            Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                            intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Config.DirPath(ThisContext),NowTakePhotoFileName)));
                                            startActivityForResult(intentFromCapture,2);
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
                else
                {
                    new AlertDialog.Builder(ThisContext)
                            .setItems(new String[] { ">> 查看照片", ">> 删除" }, new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    switch (which)
                                    {
                                        case 0:
                                            Intent intent = new Intent(Intent.ACTION_VIEW);
                                            intent.setDataAndType(Uri.fromFile(new File(_Cls.FilePath)), "image/*");
                                            startActivity(intent);
                                            break;
                                        case 1:
                                            new MessageBox().Show(ThisContext, "提示", "确定要删除吗？", getString(R.string.Cancel), getString(R.string.OK)).BtnClick = new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int whichButton) {
                                                    if (whichButton == -1) {
                                                        try
                                                        {
                                                            File _File = new File(_Cls.FilePath);
                                                            if (_File.exists())_File.delete();
                                                            _List.remove(_Cls);
                                                            if (_List.size()<4 && !_List.get(_List.size()-1).FilePath.equals("Add")) _List.add(new Cls(null,"Add"));
                                                        } catch (Exception e) { }
                                                        _MyListAdapter.notifyDataSetChanged();
                                                        Common.getTotalHeightofGridView(((GridView)widget_14.getChildAt(1)),4);
                                                    }
                                                }
                                            };
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
            }
        });




        widget_0.setVisibility(View.GONE);
        widget_6.setVisibility(View.VISIBLE);
        widget_7.setVisibility(View.GONE);
        widget_14.setVisibility(View.GONE);
        widget_15.setVisibility(View.GONE);

        UpdateInfo();
	}
    @Override
    protected void onStart() {
        super.onStart();
        UpdateInfo();
    }

    void UpdateInfo(){
        ((TextView)widget_1.getChildAt(1)).setText(_Cls.Str_1);
        ((TextView)widget_2.getChildAt(1)).setText(_Cls.Str_2);
        ((TextView)widget_3.getChildAt(1)).setText(_Cls.Str_3);
        ((TextView)widget_4.getChildAt(1)).setText(_Cls.Str_10);
        ((TextView)widget_5.getChildAt(1)).setText(_Cls.Str_11);
        ((TextView)widget_6.getChildAt(1)).setText(String.valueOf(_Cls.Int_4));
        ((EditText)widget_7.getChildAt(1)).setText(String.valueOf(_Cls.Int_4));
        ((TextView)widget_8.getChildAt(1)).setText(_Cls.Str_4);
        ((TextView)widget_9.getChildAt(1)).setText(_Cls.Str_6);
        ((TextView)widget_10.getChildAt(1)).setTag(_Cls.Str_8);
        ((TextView)widget_11.getChildAt(1)).setText(_Cls.Int_6+"%");
        ((TextView)widget_12.getChildAt(1)).setText(_Cls.Str_12);
        ((TextView)widget_12.getChildAt(3)).setText(_Cls.Str_13);
        ((TextView)widget_12.getChildAt(5)).setText(_Cls.Str_14);

        String ZXR="";
        for (int i=0;i<_Cls.ClsList_1.size();i++){
            if (!ZXR.equals("")) ZXR+="，";
            ZXR+=_Cls.ClsList_1.get(i).Str_1;
        }
        ((TextView)widget_13.getChildAt(1)).setText(ZXR);


        _MyListAdapter.notifyDataSetChanged();
        Common.getTotalHeightofGridView(((GridView)widget_14.getChildAt(1)),4);

        if (IsAPZXR){
            widget_0.setVisibility(View.VISIBLE);
            widget_0.setText("安排执行人");
        }
    }

	@Override
	protected void onDestroy() {
		IsDestroy=true;
		super.onDestroy();


        Config.DeleteTempFile(ThisContext);
	}

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState); //实现父类方法 放在最后 防止拍照后无法返回当前activity
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        switch (requestCode)
        {
            case 1:
                if (resultCode==-1 && data != null && (data.getExtras()!=null || data.getData()!= null))
                {
                    Bitmap bitmap=null;
                    if (data.getExtras() != null)
                    {
                        try
                        {
                            bitmap = data.getExtras().getParcelable("data");
                        }
                        catch (Exception e)
                        {
                            bitmap=null;
                        }
                    }
                    if (bitmap==null && data.getData()!= null)
                    {
                        try
                        {
                            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                        }
                        catch (Exception e)
                        {
                            bitmap=null;
                        }
                        if (bitmap==null)
                        {
                            try
                            {
                                bitmap = BitmapFactory.decodeStream(this.getContentResolver().openInputStream(data.getData()));
                            }
                            catch (Exception e)
                            {
                                bitmap=null;
                            }
                        }
                    }

                    if (bitmap!=null)
                    {
                        NowTakePhotoFileName="TakePhoto_"+ Common.DateToStr(new Date(), "yyyyMMddHHmmss")+".jpg";
                        try
                        {
                            File _File = new File(Config.DirPath(ThisContext),NowTakePhotoFileName);
                            if (_File.exists())_File.delete();
                            FileOutputStream fos = new FileOutputStream(_File);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                            fos.flush();
                            fos.close();
                        } catch (Exception e) { }

                        startPhotoZoom(Uri.fromFile(new File(Config.DirPath(ThisContext),NowTakePhotoFileName)));
                    }
                }
                break;
            case 2:
                if (resultCode==-1)
                {
                    startPhotoZoom(Uri.fromFile(new File(Config.DirPath(ThisContext),NowTakePhotoFileName)));
                }
                else
                {
                    NowTakePhotoFileName="";
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
	
	@SuppressLint("HandlerLeak")
	Handler HttpHandler = new Handler()
	{
		public void handleMessage(Message msg)
		{
			if (IsDestroy || msg.obj == null) return;
			String FunName = msg.obj.toString();
			if (FunName.equals("check")) {
                try {
                    JSONObject _JSONObject = new JSONObject(msg.getData().getString("ReturnValue"));
                    if (_JSONObject.getBoolean("status")) {
                        ChangeID=_JSONObject.getJSONObject("content").getString("id");

                        if (_List.size()>1)
                        {
                            UploadIndex=0;
                            if (UploadIndex<_List.size()-1)
                            {
                                String ParmStr = "?token=" + Common.UrlEncoded(UserInfo.Token);
                                ParmStr += "&id=" + ChangeID;
                                new UploadCls(ThisContext,HttpHandler,"up",0,"正在上传第"+(UploadIndex+1)+"张照片...", Config.ServiceUrl+"index.php/api/project/addOneWorkProve"+ParmStr, Common.DateToStr(new Date(), "yyyyMMddHHmmss")+".jpg",new File(_List.get(UploadIndex).FilePath),60,false,"file[]").Begin();
                                return;
                            }
                        }

                        new MessageBox().Show(ThisContext, getString(R.string.Tip), "提交成功，等待审核", "", getString(R.string.OK)).BtnClick = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int whichButton) {
                                if (whichButton == -1) {
                                    finish();
                                }
                            }
                        };
                    } else {
                        new MessageBox().Show(ThisContext, getString(R.string.Tip), _JSONObject.getString("content"), "", getString(R.string.OK));
                    }
                    return;
                } catch (Exception e) { }
                new MessageBox().Show(ThisContext, getString(R.string.Tip), getString(R.string.NetErr), "", getString(R.string.OK));
            }
            else if (FunName.equals("up")) {
                try
                {
                    JSONObject _JSONObject = new JSONObject(msg.getData().getString("ReturnValue"));
                    if (_JSONObject.getBoolean("status")) {

                        if (_List.size()>1)
                        {
                            UploadIndex++;
                            if (((_List.size()<4 && UploadIndex<_List.size()-1) || (_List.size()==4 && UploadIndex<_List.size())))
                            {
                                String ParmStr = "?token=" + Common.UrlEncoded(UserInfo.Token);
                                ParmStr += "&id=" + ChangeID;
                                new UploadCls(ThisContext,HttpHandler,"up",0,"正在上传第"+(UploadIndex+1)+"张照片...", Config.ServiceUrl+"index.php/api/project/addOneWorkProve"+ParmStr, Common.DateToStr(new Date(), "yyyyMMddHHmmss")+".jpg",new File(_List.get(UploadIndex).FilePath),60,false,"file[]").Begin();
                                return;
                            }
                        }

                        new MessageBox().Show(ThisContext, getString(R.string.Tip), "提交成功，等待审核", "", getString(R.string.OK)).BtnClick = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int whichButton) {
                                if (whichButton == -1) {
                                    finish();
                                }
                            }
                        };
                    }
                    else {
                        new MessageBox().Show(ThisContext, getString(R.string.Tip), _JSONObject.getString("content"), "", getString(R.string.OK));
                    }
                    return;
                } catch (Exception e) { }
                new MessageBox().Show(ThisContext, getString(R.string.Tip), getString(R.string.NetErr), "", getString(R.string.OK));
            }
            else if (FunName.equals("down")) {
                try {
                    JSONObject _JSONObject = new JSONObject(msg.getData().getString("ReturnValue"));
                    if (_JSONObject.getBoolean("status")) {
                        String url=_JSONObject.getString("content");

                        if (url==null || url.equals("")){
                            new MessageBox().Show(ThisContext, getString(R.string.Tip), "无文件", "", getString(R.string.OK));
                            return;
                        }
                        Common.WebOpen(ThisContext,url);

                    } else {
                        new MessageBox().Show(ThisContext, getString(R.string.Tip), _JSONObject.getString("content"), "", getString(R.string.OK));
                    }
                    return;
                } catch (Exception e) { }
                new MessageBox().Show(ThisContext, getString(R.string.Tip), getString(R.string.NetErr), "", getString(R.string.OK));
            }
		}
	};

    void startPhotoZoom(Uri uri)
    {

        int degree = Common.readPictureDegree(uri.getPath());

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//不返回实际的bitmap不给其分配内存空间而里面只包括一些解码边界信息即图片大小信息
        Bitmap bitmap=BitmapFactory.decodeFile(uri.getPath(), options); //此时返回Bitmap为空
        int be = 1;
        if (options.outHeight>options.outWidth) be=(int)(options.outHeight / (float)1280); //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        else be=(int)(options.outWidth / (float)1280); //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        if (be <= 0) be = 1;
        options.inSampleSize = be;
        options.inJustDecodeBounds = false;//返回实际的bitmap并为其分配内存空间
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap=BitmapFactory.decodeFile(uri.getPath(),options);
        bitmap = Common.rotaingImageView(degree, bitmap);
        try
        {
            File _File = new File(uri.getPath());
            if (_File.exists())_File.delete();
            FileOutputStream fos = new FileOutputStream(_File);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 30, fos);
            fos.flush();
            fos.close();
        } catch (Exception e) { }

        options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//不返回实际的bitmap不给其分配内存空间而里面只包括一些解码边界信息即图片大小信息
        bitmap=BitmapFactory.decodeFile(uri.getPath(), options); //此时返回Bitmap为空
        be = 1;
        if (options.outHeight>options.outWidth) be=(int)(options.outHeight / (float)100); //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        else be=(int)(options.outWidth / (float)100); //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        if (be <= 0) be = 1;
        options.inSampleSize = be;
        options.inJustDecodeBounds = false;//返回实际的bitmap并为其分配内存空间
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap=BitmapFactory.decodeFile(uri.getPath(),options);

        _List.add(_List.size()-1,new Cls(bitmap,uri.getPath()));


        if (_List.size()>4) _List.remove(_List.size()-1);

        _MyListAdapter.notifyDataSetChanged();
        Common.getTotalHeightofGridView(((GridView)widget_14.getChildAt(1)),4);

        NowTakePhotoFileName="";
    }

    class Cls
    {
        public Cls(Bitmap _Bitmap, String FilePath)
        {
            this._Bitmap=_Bitmap;
            this.FilePath=FilePath;
        }
        public Bitmap _Bitmap;
        public String FilePath="";
    }


    class MyListAdapter extends BaseAdapter {
        public MyListAdapter() { }

        @Override
        public int getCount() {
            return _List.size();
        }

        @Override
        public Object getItem(int arg0) {
            return _List.get(arg0);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(ThisContext).inflate(R.layout.item_photo, parent,false);
            }
            Cls _Cls = _List.get(position);
            convertView.setTag(_Cls);

            ImageView _SmartImageView=(ImageView)convertView.findViewById(R.id.item_widget_0);
            if (_Cls._Bitmap==null) _SmartImageView.setImageResource(R.drawable.addphoto);
            else _SmartImageView.setImageBitmap(_Cls._Bitmap);
            _SmartImageView.setTag(_Cls.FilePath);

            return convertView;
        }

    }

}
