package klivitam.com.chat.frag;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.List;

import klivitam.com.chat.R;

/**
 * Created by klivitam on 2016/12/7.
 */

public class ContentFragment extends Fragment implements RadioGroup.OnCheckedChangeListener {
    private int mIndex;
    private View view;
    private Toolbar toolbar;
    private List<View> list;
    private RadioButton msgHisBtn;
    private RadioButton linkManBtn;
    private RadioButton myBtn;
    private Fragment linkManFragment;
    private Fragment msgHisFragment;
    private Fragment myFragment;
    private RadioGroup mTab;
    private Fragment[] mFragments;
    private ImageView toolIcon;
    private TextView toolTitle;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_main_content,null);
        initView();
        initToolbar();
        eventView();
        return view;
    }

    private void initToolbar() {
        toolIcon = (ImageView) view.findViewById(R.id.toolbar_back);
        toolTitle = (TextView) view.findViewById(R.id.toolbar_title);
        toolTitle.setText("聊天");
    }

    private void eventView() {
        mTab.setOnCheckedChangeListener(this);
    }

    private void initView() {
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mTab = (RadioGroup) view.findViewById(R.id.bottom_tab);
        msgHisBtn = (RadioButton) view.findViewById(R.id.msg_his1);
        linkManBtn = (RadioButton) view.findViewById(R.id.link_man1);
        myBtn = (RadioButton) view.findViewById(R.id.my1);
        //init frag
        linkManFragment = new LinkManFragment();
        msgHisFragment = new MsgHisFragment();
        myFragment = new MyFragment();
        mFragments = new Fragment[]{msgHisFragment,linkManFragment,myFragment};
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.add(R.id.content_frame,msgHisFragment).commit();
        setIndexSelected(0);
    }

    private void setIndexSelected(int index) {
        if(mIndex==index){
            return;
        }
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction ft= fragmentManager.beginTransaction();
        //隐藏
        ft.hide(mFragments[mIndex]);
        //判断是否添加
        if(!mFragments[index].isAdded()){
            ft.add(R.id.content_frame,mFragments[index]).show(mFragments[index]);
        }else {
            ft.show(mFragments[index]);
        }
        ft.commit();
        //再次赋值
        mIndex=index;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.msg_his1:
                setIndexSelected(0);
                toolTitle.setText("聊天");
                break;
            case R.id.link_man1:
                setIndexSelected(1);
                toolTitle.setText("联系人");
                break;
            case R.id.my1:
                setIndexSelected(2);
                toolTitle.setText("基本设置");
                break;
        }

    }
}
