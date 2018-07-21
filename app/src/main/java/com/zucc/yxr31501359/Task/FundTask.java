package com.zucc.yxr31501359.Task;

import android.os.AsyncTask;
import android.util.Log;
import com.zucc.yxr31501359.ICallback.ICallBack;
import com.zucc.yxr31501359.entity.Fundbean;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FundTask extends
        AsyncTask<String, Integer, String> {

    private ICallBack mCallback;
    public FundTask(ICallBack cb){
        this.mCallback = cb;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected String doInBackground(String... urls) {
        String retValue = getContent(urls[0]);

        return retValue;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {

    }

    @Override
    protected void onPostExecute(String result) {

        List<Fundbean> fundbeans = getResult(result);
        this.mCallback.onFinishedFund(fundbeans);
    }

    @Override
    protected void onCancelled(String result) {
    }



    private String getContent(String addr) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(addr);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(8000);
            connection.setReadTimeout(8000);
            InputStream in = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder response = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            return response.toString();
        } catch (Exception e) {
            Log.e("error", e.toString());
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return "";
    }

    private List<Fundbean> getResult(String jsonData){
        List<Fundbean> fundbeans = new ArrayList<>();
        try {
            JSONObject jsonObject1 = new JSONObject(jsonData);
            String result = jsonObject1.getString("result");
            Log.d("an", result);
            JSONArray jsonArray = new JSONArray(result);
            JSONObject jsonObject3 = jsonArray.getJSONObject(0);
            String r="1";


            for (int i = 1; i < 20; i++) {
                Fundbean fundbean = new Fundbean();
                String mm = jsonObject3.getString(Integer.toString(i));
                JSONObject jsonObject5 = new JSONObject(mm);
                fundbean.setCode(jsonObject5.getString("code"));
                fundbean.setName(jsonObject5.getString("name"));
                fundbean.setNetincome(jsonObject5.getString("netincome"));
                fundbean.setAssincome(jsonObject5.getString("assincome"));
                fundbean.setNetassrate(jsonObject5.getString("netassrate"));
                fundbean.setNetgrowrate(jsonObject5.getString("netgrowrate"));
                fundbean.setTonetgrora(jsonObject5.getString("tonetgrora"));
                fundbean.setTime(jsonObject5.getString("time"));
                fundbeans.add(fundbean);

            }
            return fundbeans;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fundbeans;
    }
}
