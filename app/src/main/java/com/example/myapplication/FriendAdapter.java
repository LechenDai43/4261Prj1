package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class FriendAdapter extends BaseAdapter {

    LayoutInflater layoutInflater;
    ArrayList<String> names = null, emails = null, types = null;

    public FriendAdapter(Context c, ArrayList<String> n, ArrayList<String> e, ArrayList<String> t) {
        names = n;
        emails = e;
        types = t;
        layoutInflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return names == null? 0: names.size();
    }

    @Override
    public Object getItem(int i) {
        return names == null? null: names.size() > i? names.get(i): null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = layoutInflater.inflate(R.layout.friend_item_list, null);

        TextView userNameTVFli = (TextView)v.findViewById(R.id.userNameTVFli);
        userNameTVFli.setText(names.get(i));
        TextView emailTVFli = (TextView)v.findViewById(R.id.emailTVFli);
        emailTVFli.setText(emails.get(i));
        TextView relationTVFli = (TextView)v.findViewById(R.id.relationTVFli);
        relationTVFli.setText(types.get(i));
        return v;
    }
}
