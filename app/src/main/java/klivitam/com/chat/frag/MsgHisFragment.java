package klivitam.com.chat.frag;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import klivitam.com.chat.R;
import klivitam.com.chat.activity.ChatActivity;
import klivitam.com.chat.adapter.MsgHisListViewAdapter;
import klivitam.com.chat.entry.MsgHisEntry;

/**
 * Created by klivitam on 2016/12/7.
 */

public class MsgHisFragment extends Fragment implements AdapterView.OnItemClickListener {
    private View view;
    private ListView mMsgHislist;
    private MsgHisListViewAdapter adapter;
    private List<MsgHisEntry> list;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.msg_his_frag,null);
        initView();
        initDatas();
        eventView();
        return view;
    }

    private void eventView() {
        mMsgHislist.setOnItemClickListener(this);
    }

    private void initDatas() {
        //1.获取data
        //2.将data封装入list里面
        list = new ArrayList<>();
        for(int i = 0 ;i<=20;i++){
            MsgHisEntry entry = new MsgHisEntry();
            entry.setChatIcon(R.mipmap.ic_launcher);
            entry.setChatWith("奥巴马");
            entry.setRecentlyMsg("你好吗？");
            entry.setRecentlyTime("20:00");
            list.add(entry);
        }
        //3.将list装载进adapter
        adapter = new MsgHisListViewAdapter(list,getContext());
        mMsgHislist.setAdapter(adapter);
    }

    private void initView() {
        mMsgHislist = (ListView) view.findViewById(R.id.msg_list);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent it = new Intent(getContext(), ChatActivity.class);
        //传输数据 现在能想到的就是  账号和密码 以及聊天人
        startActivity(it);
    }
}
