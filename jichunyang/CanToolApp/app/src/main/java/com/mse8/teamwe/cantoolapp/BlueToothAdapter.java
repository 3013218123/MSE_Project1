package com.mse8.teamwe.cantoolapp;

import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.widget.TextView;
import android.widget.BaseAdapter;
import android.view.LayoutInflater;

import java.util.ArrayList;

/**
 * Created by JIChunYang on 2017/10/17.
 */

public class BlueToothAdapter extends BaseAdapter {

    private ArrayList<BlueToothMes> _blueToothList;
    private LayoutInflater mInflater;

    public BlueToothAdapter(Context context, ArrayList<BlueToothMes> blueToothList) {

        this._blueToothList = blueToothList;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return this._blueToothList.size();
    }

    @Override
    public Object getItem(int position) {
        return this._blueToothList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        BlueToothMes item = this._blueToothList.get(position);

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.ui_list_item, parent, false);
            viewHolder = new ViewHolder((View) convertView.findViewById(R.id.list_child), (TextView) convertView.findViewById(R.id.chat_msg));
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (item._isSucceed) {
            viewHolder.child.setBackgroundResource(R.drawable.msgbox_rec);
        } else {
            viewHolder.child.setBackgroundResource(R.drawable.msgbox_send);
        }
        viewHolder.msg.setText(item._message);

        return convertView;
    }

    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }


    class ViewHolder {
        protected View child;
        protected TextView msg;

        public ViewHolder(View child, TextView msg) {
            this.child = child;
            this.msg = msg;

        }
    }
}