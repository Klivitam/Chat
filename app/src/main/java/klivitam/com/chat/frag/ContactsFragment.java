package klivitam.com.chat.frag;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import klivitam.com.chat.R;
import klivitam.com.chat.activity.ContactsDetailsActivity;
import klivitam.com.chat.activity.LoginActivity;
import klivitam.com.chat.adapter.SortAdapter;
import klivitam.com.chat.custom.EditTextWithDel;
import klivitam.com.chat.custom.SideBar;
import klivitam.com.chat.entry.ContactBean;
import klivitam.com.chat.utils.LocalPhoneUtils;
import klivitam.com.chat.utils.PinyinComparator;
import klivitam.com.chat.utils.PinyinUtils;

/**
 * Created by klivitam on 2016/12/27.
 */

public class ContactsFragment extends Fragment{
    private String TAG = "contacts";
    private ListView sortListView;
    private SideBar sideBar;
    private TextView dialog;
    private SortAdapter adapter;
    private EditTextWithDel mEtSearchName;
    private List<ContactBean> SourceDateList;
    private View view;
    public List<String> list1 = new ArrayList<>();
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1000:
                    initDatas();
                    break;
            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_contacts,null);
        initViews();
        initDatas();
        initEvents();
        return view;
    }

    private void initEvents() {
        //设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    sortListView.setSelection(position + 1);
                }
            }
        });

        //ListView的点击事件
        sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent it  = new Intent(getActivity(), ContactsDetailsActivity.class);
                it.putExtra("contacts_name",((ContactBean)adapter.getItem(position)).getName());
                startActivity(it);
                Toast.makeText(getContext(), ((ContactBean) adapter.getItem(position)).getName(), Toast.LENGTH_SHORT).show();
            }
        });

        //根据输入框输入值的改变来过滤搜索
        mEtSearchName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                filterData(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    /**
     * 根据输入框中的值来过滤数据并更新ListView
     *
     * @param filterStr
     */
    private void filterData(String filterStr) {
        List<ContactBean> mSortList = new ArrayList<>();
        if (TextUtils.isEmpty(filterStr)) {
            mSortList = SourceDateList;
        } else {
            mSortList.clear();
            for (ContactBean sortModel : SourceDateList) {
                String name = sortModel.getName();
                if (name.toUpperCase().indexOf(filterStr.toString().toUpperCase()) != -1 || PinyinUtils.getPingYin(name).toUpperCase().startsWith(filterStr.toString().toUpperCase())) {
                    mSortList.add(sortModel);
                }
            }
        }
        // 根据a-z进行排序
        Collections.sort(mSortList, new PinyinComparator());
        adapter.updateListView(mSortList);
    }

    private List<ContactBean> filledData(List<String> date) {
        List<ContactBean> mSortList = new ArrayList<>();
        ArrayList<String> indexString = new ArrayList<>();
        for (int i = 0; i < date.size(); i++) {
            Log.i(TAG, "filledData: "+date.get(i));
            ContactBean sortModel = new ContactBean();
            sortModel.setName(date.get(i));
            String pinyin = PinyinUtils.getPingYin(date.get(i));
            String sortString = pinyin.substring(0, 1).toUpperCase();
            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
                if (!indexString.contains(sortString)) {
                    Log.i(TAG, "filledData: "+sortString);
                    indexString.add(sortString);
                }
            }
            mSortList.add(sortModel);
        }
        Collections.sort(indexString);
        sideBar.setIndexText(indexString);
        return mSortList;
    }

    private void initViews() {
        mEtSearchName = (EditTextWithDel)view.findViewById(R.id.et_search);
        sideBar = (SideBar) view.findViewById(R.id.sidrbar);
        dialog = (TextView) view.findViewById(R.id.dialog);
        sortListView = (ListView) view.findViewById(R.id.lv_contact);
    }


    private void initDatas() {
        sideBar.setTextView(dialog);
        LocalPhoneUtils.testReadAllContacts(getContext(),list1);
        list1.add("畅聊小妹妹");
        SourceDateList = filledData(list1);
        Log.i(TAG, "initDatas: "+SourceDateList.size());
        Collections.sort(SourceDateList, new PinyinComparator());
        adapter = new SortAdapter(getContext(), SourceDateList);
        sortListView.setAdapter(adapter);
    }
}
