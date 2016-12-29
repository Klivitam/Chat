package klivitam.com.chat.frag;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import klivitam.com.chat.R;
import klivitam.com.chat.activity.AddHisActivity;
import klivitam.com.chat.activity.ChatActivity;
import klivitam.com.chat.activity.ContactActivity;
import klivitam.com.chat.activity.SearchFrdActivity;
import klivitam.com.chat.adapter.FriendExpandeAdapter;
import klivitam.com.chat.entry.FriendItem;
import klivitam.com.chat.utils.LocalPhoneUtils;

/**
 * Created by klivitam on 2016/12/7.
 */

public class LinkManFragment extends Fragment implements ExpandableListView.OnChildClickListener, View.OnClickListener {
    private View view;
    private List<String> mGroupList;
    private ExpandableListView mListView = null;
    private FriendExpandeAdapter mAdapter = null;
    private List<List<FriendItem>> mData = new ArrayList<List<FriendItem>>();
    private Button mSearchBtn;
    private LinearLayout newFriends;
    private LinearLayout groupChat;
    private LinearLayout yellowPage;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.link_man_frag,null);
        initView();
        initData();
        return view;
    }

    private void initData() {
        mGroupList = new ArrayList<>();
        for (int i =1;i<=6;i++) {
            mGroupList.add("第"+i+"组");
            List<FriendItem> list = new ArrayList<FriendItem>();
            for (int j =1;j<=4;j++) {
                FriendItem item = new FriendItem(R.mipmap.jty, "金泰妍", "美女");
                list.add(item);
            }
            mData.add(list);
        }
        mAdapter = new FriendExpandeAdapter(getActivity(), mData,mGroupList);
        mListView.setAdapter(mAdapter);
        mListView.setOnChildClickListener(this);
    }

    private void initView() {
        mListView = (ExpandableListView)view.findViewById(R.id.group_list);
        View headView=LayoutInflater.from(getContext()).inflate(R.layout.listview_headview,null);
        mSearchBtn  = (Button) headView.findViewById(R.id.search_friend);
        newFriends = (LinearLayout) headView.findViewById(R.id.new_friends);
        groupChat = (LinearLayout) headView.findViewById(R.id.group_chat);
        yellowPage = (LinearLayout) headView.findViewById(R.id.yellow_page);
        mListView.addHeaderView(headView);
        mSearchBtn.setOnClickListener(this);
        newFriends.setOnClickListener(this);
        groupChat.setOnClickListener(this);
        yellowPage.setOnClickListener(this);
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        Intent intent = new Intent(getActivity(),ChatActivity.class);
        startActivity(intent);
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.search_friend:
                Intent it1 = new Intent(getActivity(), SearchFrdActivity.class);
                startActivity(it1);
                break;
            case R.id.new_friends:
                Intent it =new Intent(getActivity(), AddHisActivity.class);
                startActivity(it);
                break;
            case R.id.group_chat:
                break;
            case R.id.yellow_page:
                Intent contactIt = new Intent(getActivity(), ContactActivity.class);
                startActivity(contactIt);
                break;
        }

    }
}
