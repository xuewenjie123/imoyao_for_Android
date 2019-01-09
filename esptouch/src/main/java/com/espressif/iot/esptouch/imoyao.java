package com.espressif.iot.esptouch;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import com.espressif.iot.esptouch.EsptouchTask;
import com.espressif.iot.esptouch.IEsptouchResult;
import com.espressif.iot.esptouch.IEsptouchTask;

public class imoyao {
    private EspWifiAdminSimple mWifiAdmin;
    private final Context mContext;
    private moyaoCallback callback;

    public imoyao(Context context) {
        this.mContext = context;
    }

    public String getSSID() {
        mWifiAdmin = new EspWifiAdminSimple(mContext);
        String apSsid = mWifiAdmin.getWifiConnectedSsid();
        return apSsid;
    }

    public String getBSSID() {
        mWifiAdmin = new EspWifiAdminSimple(mContext);
        String apbssid = mWifiAdmin.getWifiConnectedBssid();
        return apbssid;
    }

    public void Connect(final String pwd, final moyaoCallback callback) {
        this.callback = callback;
        String apSsid = mWifiAdmin.getWifiConnectedSsid();
        String apBssid = mWifiAdmin.getWifiConnectedBssid();
        if (apSsid == "" || apBssid == "") {
            Log.e("网络错误###", "33333");
            return;
        }
        new EsptouchAsyncTask().execute(pwd);
    }

    class EsptouchAsyncTask extends AsyncTask<String, Void, List<IEsptouchResult>> {
        private final Object mLock = new Object();
        private IEsptouchTask mEsptouchTask;

        //onPreExecute用于异步处理前的操作
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        //在doInBackground方法中进行异步任务的处理.
        @Override
        protected List<IEsptouchResult> doInBackground(String... params) {
            Log.e("doInBackground", "begin");
            try {
                int taskResultCount = 1;
                synchronized (mLock) {
                    String apSsid = mWifiAdmin.getWifiConnectedSsid();
                    String apPassword = params[0];
                    String apBssid = mWifiAdmin.getWifiConnectedBssid();
                    Log.e("doInBackground", "apSsid =>" + apSsid);
                    Log.e("doInBackground", "apPassword =>" + apPassword);
                    Log.e("doInBackground", "apBssid =>" + apBssid);
                    mEsptouchTask = new EsptouchTask(apSsid, apBssid, apPassword, mContext);
                    List<IEsptouchResult> resultList = mEsptouchTask.executeForResults(taskResultCount);

                    for (int i = 0; i<resultList.size(); i++) {
                        IEsptouchResult item = resultList.get(i);
                        Log.e("IEsptouchResult", "Item =>" + item.getBssid() + " [result] => [" + item.isCancelled() + "] isSucc =>" + item.isSuc());
                    }
                    Log.e("IEsptouchResult", "####### resultList =>" + resultList.toString());

                    return resultList;
                }
            } catch (Exception e) {
                Log.e("#####", "doInBackground: error"+ e.toString());
                List<IEsptouchResult> resultList = new ArrayList<IEsptouchResult>();

                Log.e("catch IEsptouchResult", "resultList =>" + resultList.toString());

                return  resultList;
            }
        }

        //onPostExecute用于UI的更新.此方法的参数为doInBackground方法返回的值.
        @Override
        protected void onPostExecute(List<IEsptouchResult> result) {
            Log.e("onPostExecute", "result =>" + result.toString());
            if (result.size() == 0) {
                Log.e("onPostExecute", "size = 0");
                invokeError("错误###");
            } else {
                IEsptouchResult firstResult = result.get(0);
                execResult(firstResult);
            }
        }
    }

    private void execResult(IEsptouchResult result) {
        if (!result.isCancelled()) {
            if (result.isSuc()) {
                invokeSuccessWithResult(result.getInetAddress().getHostAddress());
            } else {
                invokeError("失败###");
            }
        } else {
            invokeError("cancel");
        }
    }

    /**
     * 识别成功时触发
     *
     */
    private void invokeSuccessWithResult(String result) {
        if (this.callback != null) {
            this.callback.invoke(true, result);
//            this.callback = null;
        }
    }

    /**
     * 失败时触发
     */
    private void invokeError(String result) {
        if (this.callback != null) {
            this.callback.invoke(false, result);
//            this.callback = null;
        }
    }

}
