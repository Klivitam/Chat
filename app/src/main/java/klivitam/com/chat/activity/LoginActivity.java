package klivitam.com.chat.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.jivesoftware.smack.tcp.XMPPTCPConnection;

import cn.pedant.SweetAlert.SweetAlertDialog;
import klivitam.com.chat.MainActivity;
import klivitam.com.chat.MyApplication;
import klivitam.com.chat.R;
import klivitam.com.chat.utils.DialogUtils;
import klivitam.com.chat.utils.OpenfireConnUtils;
import klivitam.com.chat.utils.ToastUtils;

/**
 * Created by klivitam on 2016/12/8.
 */

public class LoginActivity extends Activity implements View.OnClickListener {
    private MyApplication app;
    private XMPPTCPConnection connection;
    private TextView forgetPwd;
    private CheckBox savePwd;
    private ImageView user_icon;
    private EditText user_name,user_pwd;
    private Button regist,login;
    private Message msg;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 101: //没有输入密码
                    DialogUtils.simpleDialog(LoginActivity.this,"没有输入密码");
                    break;
                case 102: //没有输入账号
                    DialogUtils.simpleDialog(LoginActivity.this,"没有输入账号");
                    break;
                case 103:// 账号密码错误
                    DialogUtils.simpleDialog(LoginActivity.this,"请检查是否账号密码是否正确");
                    break;
                case 104:// 输入正确
                    ToastUtils.userUtils(LoginActivity.this,"登陆成功");
                    if(savePwd.isChecked()){
                        savePwd(user_name.getText().toString().trim(),user_pwd.getText().toString().trim());
                    }else{
                        savePwd(user_name.getText().toString().trim(),null);
                    }
                    Intent it = new Intent(LoginActivity.this, MainActivity.class);
                    it.putExtra("type","LoginActivity");
                    startActivity(it);
                    finish();
                    break;
            }
        }
    };

    private void savePwd(String username, String password) {
        SharedPreferences sp = getSharedPreferences("user_info", MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.putString("username",username);
        ed.putString("password",password);
        ed.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        checkLocalData();
        eventView();
    }

    private void eventView() {
        login.setOnClickListener(this);
        forgetPwd.setOnClickListener(this);
        regist.setOnClickListener(this);
    }

    private void checkInputStyle() {
        String user = user_name.getText().toString();
        String pwd = user_pwd.getText().toString();
        if (pwd.equals("")) {
            handler.sendEmptyMessage(101);
        } else if (user.equals("")) {
            handler.sendEmptyMessage(102);
        } else {
            checkUser(user, pwd);
        }
    }

    private void checkUser(final String user, final String pwd) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                boolean b;
                Log.i("testt", "run: "+user+":"+connection);
                b =  OpenfireConnUtils.isLogin(connection,user,pwd);
                if(b){
                    handler.sendEmptyMessage(104);
                }else{
                    handler.sendEmptyMessage(103);
                }
            }
        });
        thread.start();
    }

    private boolean checkLocalData() {
        SharedPreferences sp = getSharedPreferences("user_info", MODE_PRIVATE);
        String username = sp.getString("username","");
        String password = sp.getString("password","");
        if(username.length()>0){
            user_name.setText(username);
            if(password.length()>0){
                user_pwd.setText(password);
            }
        }

        return false;
    }

    private void initView() {
        app = (MyApplication) getApplication();
        connection = app.getConnection();
        forgetPwd = (TextView) findViewById(R.id.forget_pwd);
        //设置下划线
        forgetPwd.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        forgetPwd.getPaint().setAntiAlias(true);
        user_icon = (ImageView) findViewById(R.id.login_icon);
        user_name = (EditText) findViewById(R.id.user_name);
        user_pwd = (EditText) findViewById(R.id.user_pwd);
        regist = (Button) findViewById(R.id.register);
        login = (Button) findViewById(R.id.login);
        savePwd = (CheckBox) findViewById(R.id.save_pwd);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                checkInputStyle();
                break;
            case R.id.register:
                Intent it = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(it);
                break;
        }

    }
}
