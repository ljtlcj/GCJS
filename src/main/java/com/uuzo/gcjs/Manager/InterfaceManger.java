package com.uuzo.gcjs.Manager;

/**
 * =====作者=====
 * 许英俊
 * =====时间=====
 * 2017/11/24.
 */

public class InterfaceManger {

    public interface OnRequestListener {
        void onSuccess(Object success);

        void onError(String error);

        void onComplete();
    }
}
