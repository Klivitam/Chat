package klivitam.com.chat.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import klivitam.com.chat.MainActivity;
import klivitam.com.chat.R;
import klivitam.com.chat.utils.DialogUtils;

/**
 * Created by klivitam on 2016/12/21.
 */

public class RegisterActivity extends Activity implements View.OnClickListener, View.OnFocusChangeListener {
    private String TAG = ">>>>>";
    private ImageView mIcon;
    private EditText mUsername;
    private EditText mPwd;
    private EditText mNickname;
    private EditText mRePwd;
    private EditText mEmail;
    private TextView title;
    private Button mBtn;
    private ImageView rightInfo;
    private ImageView mUserErrorImg;
    private ImageView mNickErrorImg;
    private ImageView mPwdErrorImg;
    private ImageView mRePwdErrorImg;
    private ImageView mEmailErrorImg;
    private TextView back;
    private boolean[] mTrue = new boolean[]{false,false,false,false,false};
    private boolean mSelect;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 100:
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    finish();
                    break;
                case 101:  //用户名已经存在
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        eventView();
    }

    private void eventView() {
        mNickname.setOnFocusChangeListener(this);
        mPwd.setOnFocusChangeListener(this);
        mRePwd.setOnFocusChangeListener(this);
        mEmail.setOnFocusChangeListener(this);
        mBtn.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    private void initView() {
        mIcon = (ImageView) findViewById(R.id.register_icon);
        back  = (TextView) findViewById(R.id.back);
        mUsername = (EditText) findViewById(R.id.register_username);
        mPwd = (EditText) findViewById(R.id.register_password);
        mNickname = (EditText) findViewById(R.id.register_nickname);
        mRePwd = (EditText) findViewById(R.id.register_repassword);
        mEmail = (EditText) findViewById(R.id.register_email);
        mBtn = (Button) findViewById(R.id.register_btn);
        rightInfo = (ImageView) findViewById(R.id.right_info);
        rightInfo.setVisibility(View.GONE);
        mUserErrorImg = (ImageView) findViewById(R.id.username_error);
        mNickErrorImg = (ImageView) findViewById(R.id.nickname_error);
        mPwdErrorImg = (ImageView) findViewById(R.id.password_error);
        mRePwdErrorImg = (ImageView) findViewById(R.id.repassword_error);
        mEmailErrorImg = (ImageView) findViewById(R.id.email_error);
        title = (TextView) findViewById(R.id.title);
        title.setText("注册");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case  R.id.back:
                finish();
                break;
            case R.id.register_icon:
                break;
            case R.id.register_btn:
                emailEditStyle();
                boolean b =check();
                if(b){
                    register();
                }else{
                    DialogUtils.simpleDialog(RegisterActivity.this,"请填写上面所有的项目");
                }
                break;
            default:
                break;
        }
    }

    private void register() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = "http://192.168.199.132:9090/plugins/userService/userservice?type=add&secret=bigsecret&";
        // username=kafka&password=drowssap&name=franz&email=franz@kafka.com%27";
        String username = mUsername.getText().toString().trim();
        String pwd = mPwd.getText().toString().trim();
        String name = mNickname.getText().toString().trim();
        String email = mEmail.getText().toString().trim();
        url = url+"username="+username+"&"+"password="+pwd+"&"+"name="+name+"&"+"email="+email+"%27";
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(TAG, "onResponse: "+response);
                if(response.contains("ok")){
                    handler.sendEmptyMessage(100);
                }else if(response.contains("UserAlreadyExistsException")){
                    handler.sendEmptyMessage(101);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "onErrorResponse: ");

            }
        });
        requestQueue.add(request);

    }

    private boolean check() {
        for(int j = 0;j<mTrue.length;j++){
            if(!mTrue[j]) return false;
        }
        return true;
    }

    private void emailEditStyle() {
        if(!isCheck(mEmail.getText().toString().trim(),"^([a-zA-Z0-9]+[_|\\_|\\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\\_|\\.]?)*[a-zA-Z0-9]+\\.[a-zA-Z]{2,3}$")){
            mEmailErrorImg.setVisibility(View.VISIBLE);
            Toast.makeText(RegisterActivity.this, "E-mail格式有误", Toast.LENGTH_SHORT).show();
        }else{
            mEmailErrorImg.setVisibility(View.GONE);
            mTrue[4] = true;
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.register_nickname:
                userEditstyle(hasFocus);
                break;
            case R.id.register_password:
                nickEditstyle(hasFocus);
                break;
            case R.id.register_repassword:
                pwdEditStyle();
                break;
            case R.id.register_email:
                Log.i(TAG, "onFocusChange: ");
                repwdEditStyle();
                break;
            default:
                break;

        }
    }

    private void repwdEditStyle() {
        mSelect = true;
        if (!(mRePwd.getText().toString()).equals(mPwd.getText().toString())) {
            mRePwdErrorImg.setVisibility(View.VISIBLE);
            Toast.makeText(RegisterActivity.this, "两次密码不一致", Toast.LENGTH_SHORT).show();
            Log.i(TAG, "repwdEditStyle: ");
        } else {
            mRePwdErrorImg.setVisibility(View.GONE);
            mTrue[3] = true;
        }
    }

    private void pwdEditStyle() {
        mSelect = true;
        if (!isCheck(mPwd.getText().toString(), "^[\\\\u4e00-\\\\u9fa5_a-zA-Z0-9-]{1,16}$")) {
            mPwdErrorImg.setVisibility(View.VISIBLE);
            Toast.makeText(RegisterActivity.this, "密码格式有误", Toast.LENGTH_SHORT).show();
        } else {
            mPwdErrorImg.setVisibility(View.GONE);
            mTrue[2]=true;
        }

    }

    private void nickEditstyle(boolean hasFocus) {
        mSelect = true;
        if (hasFocus) {
            if(mNickname.getText().toString().length()<1) {
                Toast.makeText(RegisterActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
                mNickErrorImg.setVisibility(View.VISIBLE);
            } else {
                mNickname.setHintTextColor(getResources().getColor(R.color.input_true));
                mNickErrorImg.setVisibility(View.GONE);
                mTrue[1] = true;
            }
        }
    }

    private void userEditstyle(boolean hasFocus) {
        if (hasFocus) {
            if (mUsername.getText().length() < 1) {
                mUsername.setHint("您还未输入用户名");
                mUsername.setHintTextColor(getResources().getColor(R.color.input_error));
                mUserErrorImg.setVisibility(View.VISIBLE);
            } else if (!isCheck(mUsername.getText().toString().trim(), "[1][358]\\d{9}")) {
                Toast.makeText(RegisterActivity.this, "请以电话号码作为用户名!!!", Toast.LENGTH_SHORT).show();
                mUserErrorImg.setVisibility(View.VISIBLE);
            } else {
                mUsername.setHintTextColor(getResources().getColor(R.color.input_true));
                mUserErrorImg.setVisibility(View.GONE);
                mTrue[0] =true;
            }
        }
    }

    private boolean isCheck(String number, String s) {
        if (TextUtils.isEmpty(number)) {
            return false;
        } else {
            return number.matches(s);
        }
    }
}
