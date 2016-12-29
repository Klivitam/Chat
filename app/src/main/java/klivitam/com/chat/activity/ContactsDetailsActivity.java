package klivitam.com.chat.activity;

import android.app.Activity;
import android.os.Bundle;

import klivitam.com.chat.R;

/**
 * Created by klivitam on 2016/12/29.
 */

public class ContactsDetailsActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_details);
        String name = getIntent().getStringExtra("contacts_name");
    }
}
