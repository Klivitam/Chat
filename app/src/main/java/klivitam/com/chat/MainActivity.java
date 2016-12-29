package klivitam.com.chat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import org.jivesoftware.smack.tcp.XMPPTCPConnection;

import klivitam.com.chat.utils.OpenfireConnUtils;

public class MainActivity extends FragmentActivity {
    private XMPPTCPConnection connection;
    private MyApplication app;

    private DrawerLayout mDrawerLayout;
    private NavigationView navView;
    private Fragment frameContent;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 100:
                    Toast.makeText(MainActivity.this,"登陆成功",Toast.LENGTH_SHORT).show();
                    break;
                case 101:
                    Toast.makeText(MainActivity.this,"登陆失败",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        app = (MyApplication) getApplication();
        connection = app.getConnection();
        isLogin();
        initView();
        eventView();
    }

    private void eventView() {
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mDrawerLayout.closeDrawer(GravityCompat.START,true);
                return true;
            }
        });
    }

    private void initView() {
        //需要接收用户名信息
        navView = (NavigationView) findViewById(R.id.nav_view);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navView.setCheckedItem(R.id.nav_call);
    }

    public void isLogin() {
        Intent it = getIntent();
        String type = it.getStringExtra("type");
        Log.i("type", "isLogin: "+type);
        if(type.contains("Welcome")){
            SharedPreferences sp = getSharedPreferences("user_info", MODE_PRIVATE);
            final String username = sp.getString("username","");
            final String password = sp.getString("password","");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    boolean b =  OpenfireConnUtils.isLogin(connection,username,password);
                    if(b){
                        handler.sendEmptyMessage(100);
                    }else{
                        handler.sendEmptyMessage(101);
                    }
                }
            }).start();
        }
    }
}
