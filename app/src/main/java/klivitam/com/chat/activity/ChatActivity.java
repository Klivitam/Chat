package klivitam.com.chat.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import klivitam.com.chat.R;
import klivitam.com.chat.adapter.ChatListAdapter;
import klivitam.com.chat.entry.ChatListEntry;

/**
 * Created by klivitam on 2016/12/10.
 */

public class ChatActivity extends Activity implements View.OnClickListener {
    private ListView mChatList;
    private ChatListAdapter adapter;
    private List<ChatListEntry> chatList;
    private TextView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initToolbal();
        initView();
        eventView();
    }

    private void eventView() {
        back.setOnClickListener(this);
    }

    private void initToolbal() {
    }

    private void initView() {
        mChatList = (ListView) findViewById(R.id.chat_list);
        back = (TextView) findViewById(R.id.back);

        //doing something...

        //loading data;
        chatList = new ArrayList<>();
        for(int i = 0;i<=20;i++){
            if(i%2==0) {
                ChatListEntry entry = new ChatListEntry();
                entry.setChatIcon(R.mipmap.jty);
                entry.setChatMsg("hello");
                entry.setIndex(0);
                chatList.add(entry);
            }else{
                ChatListEntry entry = new ChatListEntry();
                entry.setChatIcon(R.mipmap.ic_launcher);
                entry.setChatMsg("hello");
                entry.setIndex(1);
                chatList.add(entry);
            }
        }
        adapter = new ChatListAdapter(this,chatList);
        mChatList.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
        }
    }
}
