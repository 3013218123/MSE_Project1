package trylistviewcheckbox;
import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.wenhao.cantoolapp.R;

/**
 * Created by wenhao on 2017/10/25.
 */

public class ListitemAdapter extends BaseAdapter{
    private List<DataHolder> mList;
    private Context mContext;
    private LayoutInflater mInflater;
    public ListitemAdapter(Context context,List<DataHolder> list){
        mList = list;
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder = null;
        if (convertView == null) {

            holder=new ViewHolder();

            convertView = mInflater.inflate(R.layout.check_list_item, null);
            holder.mTitle = (TextView)convertView.findViewById(R.id.title);
            holder.mSubTitile = (TextView)convertView.findViewById(R.id.subtitle);
            convertView.setTag(holder);

        }else {

            holder = (ViewHolder)convertView.getTag();
        }

        holder.mTitle.setText((String)mList.get(position).titleStr);
        holder.mSubTitile.setText((String)mList.get(position).subTitleStr);
        return convertView;
    }
}
