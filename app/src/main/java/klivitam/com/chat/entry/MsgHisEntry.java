package klivitam.com.chat.entry;

/**
 * Created by klivitam on 2016/12/8.
 */

public class MsgHisEntry {
    public int chatIcon; //聊天人的头像
    public String chatWith; //聊天人的用户名
    public String recentlyMsg; //最近一条消息的内容
    public String recentlyTime; //最近一条的时间

//    public MsgHisEntry(int chatIcon, String chatWith, String recentlyMsg, String recentlyTime) {
//        this.chatIcon = chatIcon;
//        this.chatWith = chatWith;
//        this.recentlyMsg = recentlyMsg;
//        this.recentlyTime = recentlyTime;
//    }

    public int getChatIcon() {
        return chatIcon;
    }

    public void setChatIcon(int chatIcon) {
        this.chatIcon = chatIcon;
    }

    public String getChatWith() {
        return chatWith;
    }

    public void setChatWith(String chatWith) {
        this.chatWith = chatWith;
    }

    public String getRecentlyMsg() {
        return recentlyMsg;
    }

    public void setRecentlyMsg(String recentlyMsg) {
        this.recentlyMsg = recentlyMsg;
    }

    public String getRecentlyTime() {
        return recentlyTime;
    }

    public void setRecentlyTime(String recentlyTime) {
        this.recentlyTime = recentlyTime;
    }
}
