package klivitam.com.chat.utils;

import java.util.Comparator;

import klivitam.com.chat.entry.ContactBean;

/**
 * Created by klivitam on 2016/12/28.
 */

public class PinyinComparator implements Comparator<ContactBean> {
    public int compare(ContactBean o1, ContactBean o2) {
        //这里主要是用来对ListView里面的数据根据ABCDEFG...来排序
        if (o1.getSortLetters().equals("@")
                || o2.getSortLetters().equals("#")) {
            return -1;
        } else if (o1.getSortLetters().equals("#")
                || o2.getSortLetters().equals("@")) {
            return 1;
        } else {
            return o1.getSortLetters().compareTo(o2.getSortLetters());
        }
    }

}
