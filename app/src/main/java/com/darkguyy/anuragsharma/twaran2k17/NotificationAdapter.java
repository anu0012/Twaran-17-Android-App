package com.darkguyy.anuragsharma.twaran2k17;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by anuragsharma on 28/03/17.
 */

public class NotificationAdapter extends ArrayAdapter<Notification> {

    public NotificationAdapter(Context context, ArrayList<Notification> venues){
        super(context, 0, venues);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_listview, parent, false);
        }

        Notification notification = getItem(position);
        TextView t = (TextView) listItemView.findViewById(R.id.textview_item);
        t.setText(notification.getTopic());
        return listItemView;
    }
}
