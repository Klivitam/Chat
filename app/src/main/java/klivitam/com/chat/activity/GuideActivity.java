package klivitam.com.chat.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import klivitam.com.chat.R;
import klivitam.com.chat.adapter.GuideAdapter;

/**
 * Created by klivitam on 2016/12/10.
 */

public class GuideActivity extends Activity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private ViewPager vp;
    private int[] pics = {R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher};
    private ImageView[] dots = new ImageView[5];
    private int[] ids = {R.id.dot_1,R.id.dot_2,R.id.dot_3,R.id.dot_4};
    private GuideAdapter adapter;
    private List<ImageView> list;
    private LinearLayout ll;
    private Button mBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initView();

    }

    private void initView() {
        vp = (ViewPager) findViewById(R.id.main_viewPager);
        mBtn = (Button) findViewById(R.id.inter_main);
        ll = (LinearLayout) findViewById(R.id.ll);
        list = new ArrayList<>();
        for(int i = 0;i<pics.length;i++){
            dots[i] = (ImageView) findViewById(ids[i]);
            ImageView view = new ImageView(this);
            view.setImageResource(pics[i]);
            list.add(view);
        }
        adapter = new GuideAdapter(list);
        vp.setAdapter(adapter);
        vp.setOnPageChangeListener(this);
        vp.setCurrentItem(0);
        initDots(0);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if(position == 3) {
            mBtn.setVisibility(View.VISIBLE);
            mBtn.setOnClickListener(this);
            ll.setVisibility(View.GONE);
        }else{
            mBtn.setVisibility(View.GONE);
            ll.setVisibility(View.VISIBLE);
        }
        initDots(position);
    }

    private void initDots(int position) {
        for(int i = 0 ;i<pics.length;i++){
            if(i == position){
                dots[i].setImageResource(R.drawable.welcome_dot_select);
            }else{
                dots[i].setImageResource(R.drawable.welcome_dot_normal);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(GuideActivity.this,LoginActivity.class));
        finish();

    }


}
