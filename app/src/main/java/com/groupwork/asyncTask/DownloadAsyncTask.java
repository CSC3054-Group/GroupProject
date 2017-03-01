package com.groupwork.asyncTask;

import android.content.Context;
import android.os.AsyncTask;

import com.groupwork.net.HttpUtils;


/**
 * Created by Administrator on 2016/10/3.
 */
public class DownloadAsyncTask extends AsyncTask<String,Void,byte[]> {

    private Context context;
    private OnResultListener listener;

    public DownloadAsyncTask(Context context, OnResultListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected byte[] doInBackground(String... params) {
        if(HttpUtils.isNetworkConn(context)){
            byte[] result =HttpUtils.getResultFromNetwork(params[0]);
            return result;
        }
        return null;
    }

    @Override
    protected void onPostExecute(byte[] bytes) {
        listener.OnResult(bytes);
    }

    public interface OnResultListener{
        void OnResult(byte[] result);
    }
}


