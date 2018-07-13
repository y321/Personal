package com.zucc.yxr31501359.Activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.zucc.yxr31501359.ICallback.ICallBack;
import com.zucc.yxr31501359.R;

import com.zucc.yxr31501359.Task.LoginTask;
import com.zucc.yxr31501359.Task.UsersTask;
import com.zucc.yxr31501359.Util.DBHelper;
import com.zucc.yxr31501359.entity.Users;

public class LoginActivity extends AppCompatActivity implements ICallBack {
    private EditText name_login;
    private EditText password_Login;

    private Button button_login;
    private Button button_reg;

    private DBHelper dbHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        name_login=(EditText) findViewById(R.id.login_id);
        password_Login = (EditText) findViewById(R.id.login_password);

        button_reg = (Button) findViewById(R.id.reg_button);
        button_login = (Button) findViewById(R.id.login_button);

        dbHelper = new DBHelper(LoginActivity.this);
        db=dbHelper.getReadableDatabase();

        button_reg.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                // 给bnt1添加点击响应事件
                Intent intent =new Intent(LoginActivity.this,RegActivity.class);
                //启动
                startActivity(intent);
            }
        });

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name_login.getText().toString().isEmpty()||name_login.getText().toString().equals(" ")) {
                    Toast.makeText(LoginActivity.this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
                }else if(password_Login.getText().toString().isEmpty()||password_Login.getText().toString().equals(" ")) {
                    Toast.makeText(LoginActivity.this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();

                }else{
                   Users users = new Users();
                   users.setUsername(name_login.getText().toString());
                   users.setPassword(password_Login.getText().toString());
                   LoginTask loginTask = new LoginTask(db,LoginActivity.this);
                   loginTask.execute(users);

                    finish();
                }
            }
        });
    }

    @Override
    public void onFinished(String result) {
        if(result.equals("login fail")){
            Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
            Intent intent =new Intent(LoginActivity.this,LoginActivity.class);
            //启动
            startActivity(intent);
        }else {
            Toast.makeText(LoginActivity.this, result, Toast.LENGTH_SHORT).show();
            Intent intent =new Intent(LoginActivity.this,MainActivity.class);
            //启动
            startActivity(intent);
        }

    }
}
