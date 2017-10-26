package listvieweditview;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.LoggingMXBean;

import com.example.wenhao.cantoolapp.R;

import trylistviewcheckbox.ViewHolder;

/**
 * Created by wenhao on 2017/10/26.
 */
public class ListviewEditviewAdapter  {
//public class ListviewEditviewAdapter extends BaseAdapter {
//    //private ViewHolder mViewHolder;
//    private List<Map<String,Object>> mData;
//
//    private LayoutInflater mLayoutInflater;
//    private List<String> mList;
//    private int mTouchItemPosition=-1;
//    private   ViewHolder mViewHolder;
//    private  HashMap<Integer, String> hashMap;
//
//    public ListviewEditviewAdapter(Context context, List<String> list, HashMap<Integer, String> hashMap) {
//        mLayoutInflater = LayoutInflater.from(context);
//        mList = list;
//        this.hashMap=hashMap;
//
//    }
//
//    @Override
//    public int getCount() {
//        return mList.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return mList.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(final int position, View convertView, ViewGroup parent) {
//
//        if (convertView == null) {
//            mViewHolder = new ViewHolder();
//            convertView = mLayoutInflater.inflate(R.layout.edittext_item, null);
//            mViewHolder.mTextView = (TextView) convertView.findViewById(R.id.text_view);
//            mViewHolder.mEditText = (EditText) convertView.findViewById(R.id.edit_text);
//            //mViewHolder.mEditText.setTag(position);
//            mViewHolder.mTextView.setText(mList.get(position));
//            mViewHolder.mEditText.setOnTouchListener(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View view, MotionEvent event) {
//                    //注意，此处必须使用getTag的方式，不能将position定义为final，写成mTouchItemPosition = position
//                    mTouchItemPosition = (Integer) view.getTag();
//                    Log.i("tag",String.valueOf(mTouchItemPosition));
//                    //触摸的是EditText并且当前EditText可以滚动则将事件交给EditText处理；否则将事件交由其父类处理
//                    if ((view.getId() == R.id.edit_text && canVerticalScroll((EditText)view))) {
//                        view.getParent().requestDisallowInterceptTouchEvent(true);
//                        if (event.getAction() == MotionEvent.ACTION_UP) {
//                            view.getParent().requestDisallowInterceptTouchEvent(false);
//                        }
//                    }
//                    return false;
//                }
//            });
//            mViewHolder.mEditText.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                }
//
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                }
//
//                @Override
//                public void afterTextChanged(Editable s) {
//                    hashMap.put(position,s.toString());
//                }
//            });
//            if(hashMap.get(position)!=null){
//                mViewHolder.mEditText.setText(hashMap.get(position));
//            }
//
//            convertView.setTag(mViewHolder);
//        } else {
//            mViewHolder = (ViewHolder) convertView.getTag();
//            mViewHolder.mEditText.setTag(position);
//
//        }
//
//
//        //mViewHolder.mTextView.setText(mList.get(position));
//        //聚焦
//        mViewHolder.mEditText.setTag(position);
//
//        if (mTouchItemPosition == position) {
//            mViewHolder.mEditText.requestFocus();
//            mViewHolder.mEditText.setSelection(mViewHolder.mEditText.getText().length());
//        } else {
//            mViewHolder.mEditText.clearFocus();
//        }
//
//        return convertView;
//    }
//     static  class ViewHolder {
//        TextView mTextView;
//        EditText mEditText;
////        MyTextWatcher mTextWatcher;
////
////        //动态更新TextWathcer的position
////        public void updatePosition(int position) {
////            mTextWatcher.updatePosition(position);
////        }
//
//    }
////    class MyTextWatcher implements TextWatcher {
////        //由于TextWatcher的afterTextChanged中拿不到对应的position值，所以自己创建一个子类
////        private int mPosition;
////
////        public void updatePosition(int position) {
////            mPosition = position;
////        }
////
////        @Override
////        public void onTextChanged(CharSequence s, int start, int before, int count) {
////
////        }
////
////        @Override
////        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
////
////        }
////
////        @Override
////        public void afterTextChanged(Editable s) {
////            mList.set(mPosition, s.toString());
////        }
////    };
//    /**
//     * EditText竖直方向是否可以滚动
//     * @param editText 需要判断的EditText
//     * @return true：可以滚动 false：不可以滚动
//     */
//    private boolean canVerticalScroll(EditText editText) {
//        //滚动的距离
//        int scrollY = editText.getScrollY();
//        //控件内容的总高度
//        int scrollRange = editText.getLayout().getHeight();
//        //控件实际显示的高度
//        int scrollExtent = editText.getHeight() - editText.getCompoundPaddingTop() -editText.getCompoundPaddingBottom();
//        //控件内容总高度与实际显示高度的差值
//        int scrollDifference = scrollRange - scrollExtent;
//
//        if(scrollDifference == 0) {
//            return false;
//        }
//
//        return (scrollY > 0) || (scrollY < scrollDifference - 1);
//    }





}
