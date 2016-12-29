package klivitam.com.chat.frag;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import klivitam.com.chat.R;

/**
 * Created by klivitam on 2016/12/7.
 */

public class MyFragment extends Fragment {
    private View view;
    private ImageView userIcon;
    private TextView albumText;
    private TextView cillectText;
    private TextView aboutText;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.my_frag,null);
        return view;
    }
}
