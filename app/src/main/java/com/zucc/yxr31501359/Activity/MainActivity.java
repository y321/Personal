package com.zucc.yxr31501359.Activity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;
import com.zucc.yxr31501359.R;
import com.zucc.yxr31501359.Util.DBHelper;

public class MainActivity extends AppCompatActivity {


    private TextView mTextMessage;
    private Toolbar toolbar;
    public static Context context ;
    private DBHelper dbHelper;
    public static SQLiteDatabase db;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.rc:
                    /* toolbar.setTitle("记账");*/
                    context = MainActivity.this;
                    Rc_home rc_home = new Rc_home();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content, rc_home).commit();
                    return true;
                case R.id.jz:
                    context = MainActivity.this;
                    SZ_home sz_home = new SZ_home();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content, sz_home).commit();

                    return true;
                case R.id.lc:
                    mTextMessage.setText(R.string.lc);
                    return true;
                case R.id.grzx:
                    mTextMessage.setText(R.string.grzx);
                    return true;

            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        context = MainActivity.this;
        Rc_home rc_home = new Rc_home();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, rc_home).commit();

        dbHelper = new DBHelper(MainActivity.this);
        db=dbHelper.getReadableDatabase();



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.header_main, menu);
        return true;
    }

    //选项菜单监听
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        /*if (id == R.id.action_add) {
            context = MainActivity.this;
            *//*AddSZFragment fragment = new AddSZFragment();*//*
            AddRcFragment fragment = new AddRcFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content, fragment).commit();
            Toast.makeText(this, "点击了添加", Toast.LENGTH_SHORT).show();
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }
    /**
     * 沉浸式状态栏
     */
    /*private void initState() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS); //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }*/

}
