package klivitam.com.chat.utils;

import android.util.Log;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.offline.OfflineMessageManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by klivitam on 2016/12/18.
 */

public class OpenfireConnUtils {
    //获取connect连接
    public static XMPPTCPConnection getConnect(){
        String server="192.168.199.132";
//        String server="10.81.253.16";
        int port=5222;
        XMPPTCPConnectionConfiguration.Builder builder = XMPPTCPConnectionConfiguration.builder();
        builder.setServiceName(server);
        builder.setHost(server);
        builder.setPort(port);
        builder.setCompressionEnabled(false);
        builder.setDebuggerEnabled(true);
        builder.setSendPresence(true);
        builder.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
        XMPPTCPConnection connection = new XMPPTCPConnection(builder.build());
        connection.setPacketReplyTimeout(10000);
        return connection;
    }
    public static boolean isLogin(XMPPTCPConnection connection,String username,String password){
        try {
            connection.connect();
            connection.login(username,password);
            UserConnectionListener listener = new UserConnectionListener(connection);
            connection.addConnectionListener(listener);
            getOfflineMsg(connection);
            setUserStutas(connection,0);
            return true;
        } catch (SmackException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XMPPException e) {
            e.printStackTrace();
        }
        return false;

    }

    private static void setUserStutas(XMPPTCPConnection connection, int statusCode) throws SmackException.NotConnectedException {
        Presence presence;
        switch (statusCode){
            case 0:
                presence = new Presence(Presence.Type.available);
                connection.sendPacket(presence);
                Log.v("state", "设置在线");
                break;
            case 1:
                presence = new Presence(Presence.Type.available);
                presence.setMode(Presence.Mode.chat);
                connection.sendPacket(presence);
                Log.v("state", "设置Q我吧");
                System.out.println(presence.toXML());
                break;
            case 2:
                presence = new Presence(Presence.Type.available);
                presence.setMode(Presence.Mode.dnd);
                connection.sendPacket(presence);
                Log.v("state", "设置忙碌");
                System.out.println(presence.toXML());
                break;
            case 3:
                presence = new Presence(Presence.Type.available);
                presence.setMode(Presence.Mode.away);
                connection.sendPacket(presence);
                Log.v("state", "设置离开");
                System.out.println(presence.toXML());
                break;
            case 4:

            case 5:
                presence = new Presence(Presence.Type.unavailable);
                connection.sendPacket(presence);
                Log.v("state", "设置离线");
                break;
            default:
                break;
        }
    }

    private static void getOfflineMsg(XMPPTCPConnection connection) {
        OfflineMessageManager offlineMsgManager = new OfflineMessageManager(connection);
        try {
            List<Message> it = offlineMsgManager.getMessages();
            Log.i("count", "getOfflineMsg: "+offlineMsgManager.getMessageCount());
            Map<String,ArrayList<Message>> offLineMessage = new HashMap<>();
            for(int i = 0;i<it.size();i++){
                Message msg = it.get(i);
                String fromUser = msg.getFrom().split("/")[0];
                if(offLineMessage.containsKey(fromUser)){
                    offLineMessage.get(fromUser).add(msg);
                }else{
                    ArrayList<Message> temp = new ArrayList<>();
                    temp.add(msg);
                    offLineMessage.put(fromUser,temp);
                }
            }
            offlineMsgManager.deleteMessages();
        } catch (SmackException.NoResponseException e) {
            e.printStackTrace();
        } catch (XMPPException.XMPPErrorException e) {
            e.printStackTrace();
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }
    }

    public static void sendPacket(XMPPTCPConnection connection,String msg){
        org.jivesoftware.smack.packet.Message message = new org.jivesoftware.smack.packet.Message();
        message.setTo("all@broadcast.desktop-g51omli");
        message.setSubject("广播");
        message.setBody(msg);
        message.setType(org.jivesoftware.smack.packet.Message.Type.normal);//离线支持
        try {
            connection.sendPacket(message);
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }

    }

    private static class UserConnectionListener implements ConnectionListener{
        private XMPPTCPConnection conn;
        public UserConnectionListener(XMPPTCPConnection conn) {
            this.conn = conn;
        }

        @Override
        public void connected(XMPPConnection connection) {

        }

        @Override
        public void authenticated(XMPPConnection connection, boolean resumed) {

        }

        @Override
        public void connectionClosed() {

        }
        //连接断开的时候
        @Override
        public void connectionClosedOnError(Exception e) {

        }

        @Override
        public void reconnectionSuccessful() {

        }

        @Override
        public void reconnectingIn(int seconds) {

        }

        @Override
        public void reconnectionFailed(Exception e) {

        }
    }
}
