package com.steven.android33_customletterindexview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.steven.android33_customletterindexview.R;
import com.steven.android33_customletterindexview.model.UserModel;

import java.util.List;

/**
 * Created by StevenWang on 16/6/18.
 */
public class MyIndexerAdapter extends BaseAdapter implements SectionIndexer {
    private Context context = null;
    private List<UserModel> list = null;
    private LayoutInflater mInflater = null;

    public MyIndexerAdapter(Context context, List<UserModel> list) {
        this.context = context;
        this.list = list;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        ViewHolder mHolder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_listview_main , parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        mHolder.textView_item_username.setText(list.get(position).getUsername());
        //给头像加载网络图片
        //mHolder

        //给首字母赋值
        int section = getSectionForPosition(position);
        int position_firstone = getPositionForSection(section);
        if (position == position_firstone) {
            mHolder.textView_item_firstletter.setVisibility(View.VISIBLE);
            mHolder.textView_item_firstletter.setText(list.get(position).getFirstLetter());
        } else {
            mHolder.textView_item_firstletter.setVisibility(View.GONE);
        }
        return convertView;
    }

    @Override
    public Object[] getSections() {
        return new Object[0];
    }

    /**
     * 根据元素所在的分组，获取该分组中第一个元素的position
     *
     * @param sectionIndex
     * @return
     */
    @Override
    public int getPositionForSection(int sectionIndex) {
        for (int i = 0; i < getCount(); i++) {
            String firstLetter = list.get(i).getFirstLetter();
            int char_firstletter = firstLetter.charAt(0);
            if (char_firstletter == sectionIndex) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 根据元素所在的position，确定该元素所在的分组
     *
     * @param position
     * @return
     */
    @Override
    public int getSectionForPosition(int position) {
        return list.get(position).getFirstLetter().charAt(0);
    }

    class ViewHolder {
        private TextView textView_item_username;
        private TextView textView_item_firstletter;
        private ImageView imageView_item_icon;

        public ViewHolder(View itemView) {
            textView_item_username = (TextView) itemView.findViewById(R.id.textView_item_username);
            textView_item_firstletter = (TextView) itemView.findViewById(R.id
                    .textView_item_firstletter);
            imageView_item_icon = (ImageView) itemView.findViewById(R.id.imageView_item_icon);
        }
    }
}
