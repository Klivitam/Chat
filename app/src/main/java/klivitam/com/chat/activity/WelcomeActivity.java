package klivitam.com.chat.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import klivitam.com.chat.MainActivity;
import klivitam.com.chat.R;

/**
 * Created by klivitam on 2016/12/10.
 */

public class WelcomeActivity extends Activity {
    private boolean isFrist = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        final SharedPreferences pref = getSharedPreferences("frist_inter",MODE_PRIVATE);
        isFrist = pref.getBoolean("isFirstIn",true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent it;
                if(isFrist){
                    it = new Intent(WelcomeActivity.this,GuideActivity.class);
                    SharedPreferences pref1 = getSharedPreferences("frist_inter", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref1.edit();
                    editor.putBoolean("isFirstIn", false);
                    editor.commit();

                }else{
                    SharedPreferences pref1 = getSharedPreferences("user_info", MODE_PRIVATE);
                    String username = pref1.getString("username","");
                    String password = pref1.getString("password","");
                    if(username.length()>0&&password.length()>0){
                        it = new Intent(WelcomeActivity.this, MainActivity.class);
                        it.putExtra("type","WelcomeActivity");
                    }else {
                        it = new Intent(WelcomeActivity.this, LoginActivity.class);
                    }
                }
                startActivity(it);
                finish();
            }
        },3000);
    }
}
