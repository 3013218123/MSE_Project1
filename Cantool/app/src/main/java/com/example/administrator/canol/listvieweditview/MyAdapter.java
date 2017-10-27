package com.example.administrator.canol.listvieweditview;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.canol.R;

import java.util.List;

/**
 * Created by wenhao on 2017/10/26.
 */

public class MyAdapter extends BaseAdapter{
    private Context context;
    private List<Bean> lists;
    private int mTouchItemPosition = -1;
    public MyAdapter(Context context, List<Bean> lists) {
        this.context = context;
        this.lists = lists;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder vh;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.edittext_item, null);
            vh = new ViewHolder(convertView);

            convertView.setTag(vh);


        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        final Bean bean = lists.get(position);

        vh.tvname.setText(bean.getName());
        //把Bean与输入框进行绑定
        vh.mEditText.setTag(bean);
        Log.e("fan", "*------------" + position);

        //清除焦点
         vh.mEditText.clearFocus();
        //大部分情况下，Adapter里面有if必须有else
        if (!TextUtils.isEmpty(bean.getInput())) {
            vh.mEditText.setText(bean.getInput());
        } else {
            vh.mEditText.setText("");
        }



        vh.mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //获得Edittext所在position里面的Bean，并设置数据
                Bean bean = (Bean) vh.mEditText.getTag();
                bean.setInput(s + "");
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });



        return convertView;
    }

    public class ViewHolder{
        TextView tvname;
        EditText mEditText;

        public ViewHolder(View convertView) {
            tvname = (TextView) convertView.findViewById(R.id.text_view);
            mEditText = (EditText) convertView.findViewById(R.id.edit_text);
        }
    }
}
