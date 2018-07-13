
package com.zucc.yxr31501359.Task;

import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import com.zucc.yxr31501359.DBService.UserService;
import com.zucc.yxr31501359.ICallback.ICallBack;
import com.zucc.yxr31501359.entity.Users;

public  class LoginTask extends AsyncTask<Users, Integer, String> {
    private SQLiteDatabase db;
    private ICallBack mCallback ;
    public LoginTask(SQLiteDatabase db,ICallBack cb){
        this.mCallback = cb;
        this.db=db;
    }




    @Override
    protected void onPreExecute() {
        Log.d("UsersTask", "onPreExecute");
    }

    @Override
    protected String doInBackground(Users... users) {
        UserService userService = new UserService(db);

        String res = userService.login(users[0]);
        //Log.d("WeatherTask", res);
        return res;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        Log.d("UsersTask", "onPreExecute");
    }

    @Override
    protected void onPostExecute(String result) {
        Log.d("result", result);
        this.mCallback.onFinished(result);
    }

    @Override
    protected void onCancelled(String result) {
    }

    /*public String register(Users users){
        String sql="insert into users(username,password) values(?,?)";
        Object obj[]={users.getUsername(),users.getPassword()};
        sdb.execSQL(sql, obj);
        return "register successful!";
    }*/


/*private String getContent(String addr) {
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

    private String getResult(String jsonData){
        try {
            JSONObject weather = new JSONObject(jsonData);
            Log.d("MainActivity",weather.getString("city"));
            String city = weather.getString("city");
            JSONObject data = weather.getJSONObject("data");
            String wendu = data.getString("wendu");
            return city + ":" + wendu + "度";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "unknown";
    }*/


}

