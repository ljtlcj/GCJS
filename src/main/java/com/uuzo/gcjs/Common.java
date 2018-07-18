package com.uuzo.gcjs;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.Toast;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class Common {
    public static String SoftName = "";
    public static Activity MainActivityContext = null;
    public static Context context = null;

    public static Date StrToDate(String datestr, String dateformat) {
        Date date = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat(dateformat);
            date = format.parse(datestr);
        } catch (Exception e) {
            date = null;
        }
        return date;
    }

    public static String DateToStr(Date date, String dateformat) {
        try {
            return new SimpleDateFormat(dateformat).format(date);
        } catch (Exception e) {
            return "";
        }
    }

    public static String UrlEncoded(String paramString) {
        if (paramString == null || paramString.equals("")) return "";
        try
        {
            String str = new String(paramString.getBytes(), "UTF-8");
            str = URLEncoder.encode(str, "UTF-8");
            return str;
        }
        catch (Exception ex){}
        return "";
    }

    static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {


        Cursor cursor = null;
        String column = "_data";
        String[] projection = {column};
        String ReturnValue="";

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                ReturnValue = cursor.getString(cursor.getColumnIndexOrThrow(column));
            }
        } finally {
            if (cursor != null) cursor.close();
        }
        return ReturnValue;
    }
    public static String getPath(Context context, Uri uri) {

        // DocumentProvider
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if ("com.android.externalstorage.documents".equals(uri.getAuthority())) {
                String docId = DocumentsContract.getDocumentId(uri);
                String[] split = docId.split(":");
                String type = split[0];

                if ("primary".equalsIgnoreCase(type)) return Environment.getExternalStorageDirectory() + "/" + split[1];
            }
            // DownloadsProvider
            else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {


                String id = DocumentsContract.getDocumentId(uri);
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));


                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String docId = DocumentsContract.getDocumentId(uri);
                String[] split = docId.split(":");
                String type = split[0];


                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }


                String selection = "_id=?";
                String[] selectionArgs = new String[]{split[1]};


                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return "";
    }

    public static Boolean HideSoftInput(Activity _Activity)
    {
        Boolean IsOK=false;
        try
        {
            IsOK=((InputMethodManager)_Activity.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(_Activity.getWindow().getDecorView().getWindowToken(), 0);
        }
        catch(Exception e){}
        return IsOK;
    }

    public static int readPictureDegree(String path) {
        int degree  = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return degree;
    }
    public static Bitmap rotaingImageView(int angle , Bitmap bitmap) {
        try
        {
            //旋转图片 动作
            Matrix matrix = new Matrix();
            matrix.postRotate(angle);
            // 创建新的图片
            Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                    bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            return resizedBitmap;
        }
        catch(Exception e){}
        return null;
    }

    public static int getTotalHeightofGridView(GridView _GridView, int columns) {
        try {
            ListAdapter listAdapter = _GridView.getAdapter();
            if (listAdapter == null) return 0;
            if (listAdapter.getCount() == 0) return 0;
            int rows;
            //判断数据总数除以每行个数是否整除。不能整除代表有多余，需要加一行
            if (listAdapter.getCount() % columns > 0) {
                rows = listAdapter.getCount() / columns + 1;
            } else {
                rows = listAdapter.getCount() / columns;
            }
            if (rows <= 0) rows = 1;
            int totalHeight = 0;
            for (int i = 0; i < rows; i++) { //只计算每项高度*行数
                try {
                    if (_GridView != null) {
                        View listItem = listAdapter.getView(i, null, _GridView);
                        if (listItem != null) listItem.measure(0, 0); // 计算子项View 的宽高
                        totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
                    }
                } catch (Exception e1) {
                }
            }
            ViewGroup.LayoutParams params = _GridView.getLayoutParams();
            params.height = totalHeight + _GridView.getPaddingTop() + _GridView.getPaddingBottom();//最后加上分割线总高度
            _GridView.setLayoutParams(params);
            _GridView.requestLayout();
            return params.height;
        } catch (Exception e) {
            return 0;
        }
    }
    public static void WebOpen(Context _Context,String Url) {
        try {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.setData(Uri.parse(Url));
            _Context.startActivity(intent);
        }
        catch (Exception e1){}
    }

    public static void DisplayToast(String str) {
        try {
            Toast.makeText(context, str, Toast.LENGTH_LONG).show();
        } catch (Exception e) {

        }
    }

    public static String StringFillZeroToBegin(String str, int num) {
        String ReturnValue = str;
        if (ReturnValue.length() >= num) {
            return ReturnValue.substring(0, num);
        }
        int FillNum = num - ReturnValue.length();
        for (int i = 0; i < FillNum; i++) {
            ReturnValue = "0" + ReturnValue;
        }
        return ReturnValue;
    }
    public static boolean isInteger(String str) {
        if (str == null || str.equals("")) return false;
        Pattern pattern = Pattern.compile("[0-9]+");
        return pattern.matcher(str).matches();
    }
}
