package com.groupwork.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 2016/10/3.
 */
public class HttpUtils {
    /**
     * 判断网络是否可用(
     Determine whether the network is available)
     * @param context 当前上下文(current context)
     * @return true 表示可用(available)
     *         false 表示不可用(unavailable)
     */
    public static boolean isNetworkConn(Context context){
        //获取网络服务管理 (Get network service management)
        ConnectivityManager manager=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //获取网络信息 (Get network information)
        NetworkInfo info=manager.getActiveNetworkInfo();

        if(info==null){
            return false;
        }else {
            return info.isConnected();
        }


    }




    public static byte[] getResultFromNetwork(String urlString) {

        HttpURLConnection conn=null;
        InputStream is=null;
        //定义一个字节数组输出流(Defines a byte array of output streams)
        ByteArrayOutputStream baos=null;

        //下载图片(download picture)
        try {
            //字符串变成URL对象(The string becomes a URL object)
            URL url=new URL(urlString);
            //打开Http 连接(open Http connection)
            conn=(HttpURLConnection) url.openConnection();
            //设置连接超时(Set the connection timeout)
            conn.setConnectTimeout(50*1000);
            //设置读取超时(set the read timeout)
            conn.setReadTimeout(50*1000);

            //判断连接是否成功(Determine if the connection is successful)
            if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
                //获取输入流(get input stream)
                is=conn.getInputStream();
                //设置一个缓冲区(set a Buffer)
                byte[] buf=new byte[1024];
                //每次读取流的长度(Read the length of the stream each time)
                int length=0;

                baos=new ByteArrayOutputStream();
                while((length=is.read(buf)) !=-1){
                    baos.write(buf,0,length);
                }

                //刷新数据(refresh data)
                baos.flush();

                byte[] result=baos.toByteArray();

                return result;

            }
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            //断开连接(disconnect)
            if(conn !=null){
                conn.disconnect();
            }
            //关闭输入流(Close the input stream )
            closeStream(is);
            //关闭输出流(Close the output stream)
            closeStream(baos);
        }

        return null;
    }


    public static void closeStream(Closeable stream){
        if(stream !=null){
            try {
                stream.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}

