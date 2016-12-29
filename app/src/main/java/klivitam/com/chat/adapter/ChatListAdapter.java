package klivitam.com.chat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import klivitam.com.chat.R;
import klivitam.com.chat.entry.ChatListEntry;

/**
 * Created by klivitam on 2016/12/10.
 */
public class ChatListAdapter extends BaseAdapter{
    private Context context;
    private List<ChatListEntry> list;

    public ChatListAdapter(Context context, List<ChatListEntry> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        if(list.get(position).getIndex() == 0){
            convertView = LayoutInflater.from(context).inflate(R.layout.chat_msg_for_i,null);
        }else{
            convertView = LayoutInflater.from(context).inflate(R.layout.chat_msg_for_other,null);
        }
        holder.icon = (ImageView) convertView.findViewById(R.id.chat_icon);
        holder.msg = (TextView) convertView.findViewById(R.id.chat_text);
//        convertView = LayoutInflater.from(context).inflate(R.layout.chat_msg_for_i,null);
        holder.icon.setImageResource(list.get(position).chatIcon);
        holder.msg.setText(list.get(position).chatMsg);
        return convertView;
    }
    class ViewHolder{
        public ImageView icon;
        public TextView msg;
    }
}
