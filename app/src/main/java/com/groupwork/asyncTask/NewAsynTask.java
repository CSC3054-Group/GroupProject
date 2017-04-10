package com.groupwork.asyncTask;

import android.content.Context;
import android.os.AsyncTask;

import com.groupwork.net.HttpUtils;

/**
 * Created by admin on 2017/4/3.
 */

public class NewAsynTask extends AsyncTask<String,Void,byte[]>{
    private Context context;
    private NewAsynTask.OnResultListener listener;
    private int i;

    public NewAsynTask(Context context, NewAsynTask.OnResultListener listener,int i) {
        this.context = context;
        this.listener = listener;
        this.i=i;
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
        listener.OnResult(bytes,i);
    }

    public interface OnResultListener{
        void OnResult(byte[] result,int i);
    }
}
