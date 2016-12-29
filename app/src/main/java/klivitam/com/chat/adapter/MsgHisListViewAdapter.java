package klivitam.com.chat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import klivitam.com.chat.R;
import klivitam.com.chat.entry.MsgHisEntry;

/**
 * Created by klivitam on 2016/12/8.
 */

public class MsgHisListViewAdapter extends BaseAdapter {
    private List<MsgHisEntry> list;
    private Context context;

    public MsgHisListViewAdapter(List<MsgHisEntry> list,Context context) {
        this.list = list;
        this.context = context;
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
        ViewHolder holder = null;
        if(convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.his_msg_list_item, null);
            holder = new ViewHolder();
            holder.img = (CircleImageView) convertView.findViewById(R.id.chat_icon);
            holder.chatWith = (TextView) convertView.findViewById(R.id.chat_with);
            holder.recentlyMsg = (TextView) convertView.findViewById(R.id.recently_msg);
            holder.recentlytime = (TextView) convertView.findViewById(R.id.recently_time);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        local(holder);
        return convertView;
    }

    private void local(ViewHolder holder) {
    }


    class ViewHolder{
        CircleImageView img;
        TextView chatWith;
        TextView recentlyMsg;
        TextView recentlytime;
    }
}
