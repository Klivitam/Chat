package klivitam.com.chat;

import android.app.Application;

import org.jivesoftware.smack.tcp.XMPPTCPConnection;

import klivitam.com.chat.utils.OpenfireConnUtils;

/**
 * Created by klivitam on 2016/12/18.
 */

public class MyApplication extends Application {
    private XMPPTCPConnection connection;
    @Override
    public void onCreate() {
        super.onCreate();
        connection = OpenfireConnUtils.getConnect();
    }

    public XMPPTCPConnection getConnection() {
        return connection;
    }
}
