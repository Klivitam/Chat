package klivitam.com.chat.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import klivitam.com.chat.R;

/**
 * Created by klivitam on 2016/12/27.
 */

public class SearchFrdActivity extends Activity {
    private Button cancelBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_frd_list);
        initView();
        eventView();
    }

    private void eventView() {
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        cancelBtn = (Button) findViewById(R.id.search_frd_cancel);
    }
}
