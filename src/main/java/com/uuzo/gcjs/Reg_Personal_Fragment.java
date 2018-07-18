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
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Reg_Personal_Fragment extends Fragment
{
    Boolean IsDestroy=false;
    Context ThisContext;
    Activity ThisActivity;

    TextView widget_0;
    LinearLayout widget_1,widget_2,widget_3,widget_4,widget_5,widget_6,widget_7,widget_8;

    ImageView NowClickImageView;
    String NowTakePhotoFileName="";

    String ID="";
    List<Cls> PhotoList=new ArrayList<Cls>();
    int UploadIndex=0;

    public Reg_Personal_Fragment()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_reg_personal, container, false);

        IsDestroy=false;
        ThisContext=getContext();
        ThisActivity=getActivity();


        widget_0 = (TextView) view.findViewById(R.id.widget_0);
        widget_1 = (LinearLayout)view.findViewById(R.id.widget_1);
        widget_2 = (LinearLayout)view.findViewById(R.id.widget_2);
        widget_3 = (LinearLayout)view.findViewById(R.id.widget_3);
        widget_4 = (LinearLayout)view.findViewById(R.id.widget_4);
        widget_5 = (LinearLayout)view.findViewById(R.id.widget_5);
        widget_6 = (LinearLayout)view.findViewById(R.id.widget_6);
        widget_7 = (LinearLayout)view.findViewById(R.id.widget_7);
        widget_8 = (LinearLayout)view.findViewById(R.id.widget_8);

        ((ImageView)((LinearLayout)widget_3.getChildAt(0)).getChildAt(1)).setTag(null);
        ((ImageView)((LinearLayout)widget_3.getChildAt(0)).getChildAt(1)).setOnClickListener(ImageOnClick);
        ((ImageView)((LinearLayout)widget_3.getChildAt(1)).getChildAt(1)).setTag(null);
        ((ImageView)((LinearLayout)widget_3.getChildAt(1)).getChildAt(1)).setOnClickListener(ImageOnClick);
        ((ImageView)((LinearLayout)widget_3.getChildAt(2)).getChildAt(1)).setTag(null);
        ((ImageView)((LinearLayout)widget_3.getChildAt(2)).getChildAt(1)).setOnClickListener(ImageOnClick);

        ((TextView)widget_7.getChildAt(1)).setText("市场部");
        widget_7.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(ThisContext, SelectBuMenActivity.class).putExtra("Title","请选择").putExtra("Url", Config.ServiceUrl+"index.php/api/register/department_list"),3);
            }
        });

        ((TextView)widget_8.getChildAt(1)).setText("业务员");
        widget_8.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(ThisContext, SelectBuMenActivity.class).putExtra("Title","请选择").putExtra("Url", Config.ServiceUrl+"index.php/api/register/job_list"),4);
            }
        });


        widget_0.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                String Value_1=((EditText)widget_1.getChildAt(1)).getText().toString().trim();
                String Tip_1=((EditText)widget_1.getChildAt(1)).getHint().toString().trim();
                String Value_2=((EditText)widget_2.getChildAt(1)).getText().toString().trim();
                String Tip_2=((EditText)widget_2.getChildAt(1)).getHint().toString().trim();
                String Value_3_1=((ImageView)((LinearLayout)widget_3.getChildAt(0)).getChildAt(1)).getTag()==null?"":((ImageView)((LinearLayout)widget_3.getChildAt(0)).getChildAt(1)).getTag().toString();
                String Tip_3_1="请上传"+((TextView)((LinearLayout)widget_3.getChildAt(0)).getChildAt(0)).getText().toString().trim();
                String Value_3_2=((ImageView)((LinearLayout)widget_3.getChildAt(1)).getChildAt(1)).getTag()==null?"":((ImageView)((LinearLayout)widget_3.getChildAt(1)).getChildAt(1)).getTag().toString();
                String Tip_3_2="请上传"+((TextView)((LinearLayout)widget_3.getChildAt(1)).getChildAt(0)).getText().toString().trim();
                String Value_3_3=((ImageView)((LinearLayout)widget_3.getChildAt(2)).getChildAt(1)).getTag()==null?"":((ImageView)((LinearLayout)widget_3.getChildAt(2)).getChildAt(1)).getTag().toString();
                String Tip_3_3="请上传"+((TextView)((LinearLayout)widget_3.getChildAt(2)).getChildAt(0)).getText().toString().trim();
                String Value_4=((EditText)widget_4.getChildAt(1)).getText().toString().trim();
                String Tip_4=((EditText)widget_4.getChildAt(1)).getHint().toString().trim();
                String Value_5=((EditText)widget_5.getChildAt(1)).getText().toString().trim();
                String Tip_5=((EditText)widget_5.getChildAt(1)).getHint().toString().trim();
                String Value_6=((EditText)widget_6.getChildAt(1)).getText().toString().trim();
                String Tip_6=((EditText)widget_6.getChildAt(1)).getHint().toString().trim();
                String Value_7=((TextView)widget_7.getChildAt(1)).getText().toString().trim().equals("请选择")?"":((TextView)widget_7.getChildAt(1)).getText().toString().trim();
                String Tip_7="请选择"+((TextView)widget_7.getChildAt(0)).getText().toString().trim();
                String Value_8=((TextView)widget_8.getChildAt(1)).getText().toString().trim().equals("请选择")?"":((TextView)widget_8.getChildAt(1)).getText().toString().trim();
                String Tip_8="请选择"+((TextView)widget_8.getChildAt(0)).getText().toString().trim();

                if (Value_1.equals("")){
                    new MessageBox().Show(ThisContext, getString(R.string.Tip), Tip_1, "", getString(R.string.OK));
                    return;
                }
                if (Value_2.equals("")){
                    new MessageBox().Show(ThisContext, getString(R.string.Tip), Tip_2, "", getString(R.string.OK));
                    return;
                }
                if (Value_3_1.equals("")){
                    new MessageBox().Show(ThisContext, getString(R.string.Tip), Tip_3_1, "", getString(R.string.OK));
                    return;
                }
                if (Value_3_2.equals("")){
                    new MessageBox().Show(ThisContext, getString(R.string.Tip), Tip_3_2, "", getString(R.string.OK));
                    return;
                }
                if (Value_3_3.equals("")){
                    new MessageBox().Show(ThisContext, getString(R.string.Tip), Tip_3_3, "", getString(R.string.OK));
                    return;
                }
                if (Value_4.equals("")){
                    new MessageBox().Show(ThisContext, getString(R.string.Tip), Tip_4, "", getString(R.string.OK));
                    return;
                }
                if (Value_5.equals("")){
                    new MessageBox().Show(ThisContext, getString(R.string.Tip), Tip_5, "", getString(R.string.OK));
                    return;
                }
                if (Value_6.equals("")){
                    new MessageBox().Show(ThisContext, getString(R.string.Tip), Tip_6, "", getString(R.string.OK));
                    return;
                }
                if (Value_7.equals("")){
                    new MessageBox().Show(ThisContext, getString(R.string.Tip), Tip_7, "", getString(R.string.OK));
                    return;
                }
                if (Value_8.equals("")){
                    new MessageBox().Show(ThisContext, getString(R.string.Tip), Tip_8, "", getString(R.string.OK));
                    return;
                }


                PhotoList.clear();
                PhotoList.add(new Cls(1,Value_3_1));
                PhotoList.add(new Cls(2,Value_3_2));
                PhotoList.add(new Cls(3,Value_3_3));


                Common.HideSoftInput(ThisActivity);
                Object[] obj=new Object[]{
                        "user_name",Value_1,
                        "id_number",Value_2,
                        "unit_name",Value_6,
                        "tel",Value_4,
                        "e_mail",Value_5,
                        "department",Value_7,
                        "job",Value_8
                };
                new HttpCls2(ThisContext, HttpHandler, "reg", 0, "正在处理...", Config.ServiceUrl + "index.php/api/register/personal_register_ios", "Post", obj, 10).Begin();

            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        IsDestroy=true;
        super.onDestroyView();


        Config.DeleteTempFile(ThisContext);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
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
                            bitmap = MediaStore.Images.Media.getBitmap(ThisContext.getContentResolver(), data.getData());
                        }
                        catch (Exception e)
                        {
                            bitmap=null;
                        }
                        if (bitmap==null)
                        {
                            try
                            {
                                bitmap = BitmapFactory.decodeStream(ThisContext.getContentResolver().openInputStream(data.getData()));
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
            case 3:
                if (resultCode== -1 && data!=null)
                {
                    ((TextView)widget_7.getChildAt(1)).setText(data.getStringExtra("Name"));
                }
                break;
            case 4:
                if (resultCode== -1 && data!=null)
                {
                    ((TextView)widget_8.getChildAt(1)).setText(data.getStringExtra("Name"));
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

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

        NowClickImageView.setTag(uri.getPath());
        NowClickImageView.setImageBitmap(bitmap);

        NowTakePhotoFileName="";
    }

    View.OnClickListener ImageOnClick=new View.OnClickListener()
    {
        @Override
        public void onClick(View v) {
            NowClickImageView=(ImageView)v;
            if (NowClickImageView.getTag()==null)
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
                                        intent.setDataAndType(Uri.fromFile(new File(NowClickImageView.getTag().toString())), "image/*");
                                        startActivity(intent);
                                        break;
                                    case 1:
                                        new MessageBox().Show(ThisContext, "提示", "确定要删除吗？", getString(R.string.Cancel), getString(R.string.OK)).BtnClick = new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int whichButton) {
                                                if (whichButton == -1) {
                                                    try
                                                    {
                                                        File _File = new File(NowClickImageView.getTag().toString());
                                                        if (_File.exists())_File.delete();
                                                        NowClickImageView.setTag(null);
                                                        NowClickImageView.setImageResource(R.drawable.addphoto);
                                                    } catch (Exception e) { }
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
    };
    class Cls
    {
        public Cls(int Type, String FilePath)
        {
            this.Type=Type;
            this.FilePath=FilePath;
        }
        public int Type=0;
        public String FilePath="";
    }
    @SuppressLint("HandlerLeak")
    Handler HttpHandler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            if (IsDestroy || msg.obj == null) return;
            String FunName = msg.obj.toString();
            if (FunName.equals("reg")) {
                try
                {
                    JSONObject _JSONObject = new JSONObject(msg.getData().getString("ReturnValue"));
                    if (_JSONObject.getBoolean("status")) {
                        ID=_JSONObject.getJSONObject("content").getString("id");

                        if (PhotoList.size()>0)
                        {
                            UploadIndex=0;
                            if (UploadIndex<PhotoList.size())
                            {
                                String ParmStr="?unit_id="+ID;
                                ParmStr+="&type="+PhotoList.get(UploadIndex).Type;
                                new UploadCls(ThisContext,HttpHandler,"regup",0,"正在上传第"+(UploadIndex+1)+"张照片...", Config.ServiceUrl+"index.php/api/register/personal_register_ios_upload"+ParmStr, Common.DateToStr(new Date(), "yyyyMMddHHmmss")+".jpg",new File(PhotoList.get(UploadIndex).FilePath),60,false,"file[]").Begin();
                                return;
                            }
                        }

                        new MessageBox().Show(ThisContext, getString(R.string.Tip), "提交成功，等待审核", "", getString(R.string.OK)).BtnClick = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int whichButton) {
                                if (whichButton == -1) {
                                    ThisActivity.finish();
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
            else if (FunName.equals("regup")) {
                try
                {
                    JSONObject _JSONObject = new JSONObject(msg.getData().getString("ReturnValue"));
                    if (_JSONObject.getBoolean("status")) {

                        if (PhotoList.size()>0)
                        {
                            UploadIndex++;
                            if (UploadIndex<PhotoList.size())
                            {
                                String ParmStr="?unit_id="+ID;
                                ParmStr+="&type="+PhotoList.get(UploadIndex).Type;
                                new UploadCls(ThisContext,HttpHandler,"regup",0,"正在上传第"+(UploadIndex+1)+"张照片...", Config.ServiceUrl+"index.php/api/register/personal_register_ios_upload"+ParmStr, Common.DateToStr(new Date(), "yyyyMMddHHmmss")+".jpg",new File(PhotoList.get(UploadIndex).FilePath),60,false,"file[]").Begin();
                                return;
                            }
                        }

                        new MessageBox().Show(ThisContext, getString(R.string.Tip), "提交成功，等待审核", "", getString(R.string.OK)).BtnClick = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int whichButton) {
                                if (whichButton == -1) {
                                    ThisActivity.finish();
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
        }
    };
}
