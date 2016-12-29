package klivitam.com.chat.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by klivitam on 2016/12/10.
 */

public class ToastUtils {
    public static void userUtils(Context context, String msg){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }
}
