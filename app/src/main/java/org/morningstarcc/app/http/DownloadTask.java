package org.morningstarcc.app.http;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.PowerManager;
import android.widget.Toast;

import org.morningstarcc.app.activities.PDFActivity;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * History:
 * 11/12/2015 - Juan Manuel Gomez - Created
 */
public class DownloadTask extends AsyncTask<String, Integer, String> {

    private Context context;
    private PowerManager.WakeLock mWakeLock;
    private ProgressDialog mProgressDialog;

    public DownloadTask(Context context, ProgressDialog mProgressDialog){
        this.context = context;
        this.mProgressDialog = mProgressDialog;
    }

    @Override
    protected String doInBackground(String... sUrl) {
        InputStream input = null;
        OutputStream output = null;
        HttpURLConnection connection = null;
        try{
            URL url = new URL(sUrl[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            if(connection.getResponseCode() != HttpURLConnection.HTTP_OK){
                return "Server returned HTTP " + connection.getResponseCode()
                        + " " + connection.getResponseMessage();
            }

            int fileLength = connection.getContentLength();

            input = connection.getInputStream();
            output = new FileOutputStream("/sdcard/bulletin.pdf");

            byte data[] = new byte[4096];
            long total = 0;
            int count;
            while((count = input.read(data)) != -1){
                if(isCancelled()){
                    input.close();
                    return null;
                }
                total += count;
                if(fileLength > 0)
                    publishProgress((int)(total * 100 / fileLength));
                output.write(data, 0, count);
            }
        }catch (Exception e){
            e.printStackTrace();
            return e.toString();
        }finally{
            try{
                if(output != null){
                    output.close();
                }
                if(input != null){
                    input.close();
                }
            }catch (IOException ignored){

            }

            if(connection != null){
                connection.disconnect();
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, getClass().getName());
        mWakeLock.acquire();
        mProgressDialog.show();
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        super.onProgressUpdate(progress);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setMax(100);
        mProgressDialog.setProgress(progress[0]);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        mWakeLock.release();
        mProgressDialog.dismiss();
        if(result != null){
            Toast.makeText(context, "Download error: " + result, Toast.LENGTH_LONG).show();
        }else{
            Intent i = new Intent(context, PDFActivity.class);
            context.startActivity(i);
        }
    }
}
