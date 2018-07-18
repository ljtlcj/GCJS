package com.uuzo.gcjs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.view.WindowManager;

public class MessageBox {
	public static MessageBox instance;
	boolean ReturnValue = false;
	public DialogInterface.OnClickListener BtnClick = null;
	public DialogInterface.OnKeyListener BtnKeyDown = null;
	public AlertDialog _AlertDialog;

	public MessageBox Show(Context _context, String title, String str, String NoBtnStr, String YesBtnStr) {
		return Show(_context,title,str,"",YesBtnStr,NoBtnStr,false);
	}
	
	public MessageBox Show(Context _context, String title, String str, String NoBtnStr, String YesBtnStr, String CancelBtnStr, Boolean IsBackKey) {
		return Show(_context,title,str,NoBtnStr,YesBtnStr,CancelBtnStr,IsBackKey,false);
	}
	
	public MessageBox Show(Context _context, String title, String str, String NoBtnStr, String YesBtnStr, String CancelBtnStr, Boolean IsBackKey, Boolean IsSystemAlert) {
		AlertDialog.Builder NewAlertDialog = new AlertDialog.Builder(_context);
		if (title != null && !title.equalsIgnoreCase("")) {
			NewAlertDialog.setTitle(title);
		}
		NewAlertDialog.setMessage(str);
		if (BtnKeyDown != null) {
			NewAlertDialog.setOnKeyListener(BtnKeyDown);
		}
		NewAlertDialog.setOnCancelListener(new OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				instance = null;
				_AlertDialog = null;
				if (BtnClick != null) {
					BtnClick.onClick(dialog, 0);
				}
			}
		});
		if (!YesBtnStr.equals("")) {
			NewAlertDialog.setPositiveButton(YesBtnStr, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					instance = null;
					_AlertDialog = null;
					if (BtnClick != null) {
						BtnClick.onClick(dialog, whichButton);
					}
				}
			});
		}
		if (!NoBtnStr.equals("")) {
			NewAlertDialog.setNeutralButton(NoBtnStr, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					instance = null;
					_AlertDialog = null;
					if (BtnClick != null) {
						BtnClick.onClick(dialog, whichButton);
					}
				}
			});
		}
		if (!CancelBtnStr.equals("")) {
			NewAlertDialog.setNegativeButton(CancelBtnStr, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					instance = null;
					_AlertDialog = null;
					if (BtnClick != null) {
						BtnClick.onClick(dialog, 0);
					}
				}
			});
		}
		NewAlertDialog.setCancelable(IsBackKey);
		_AlertDialog = NewAlertDialog.create();
		if (IsSystemAlert)
		{
			_AlertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		}
		_AlertDialog.show();
		instance = this;
		return this;		
	}
	public void Close() {
		try {
			if (_AlertDialog != null) {
				_AlertDialog.dismiss();
			}
		} catch (Exception e) {
		}
	}
}
