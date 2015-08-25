package com.epsmart.wzcc.activity.supply.approval;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.epsmart.wzcc.R;
import com.epsmart.wzcc.activity.supply.approval.parcelable.BatchBean;
import com.epsmart.wzcc.activity.supply.approval.parcelable.ItemBean;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 *
 *创库查询列表
 * @author fony
 */

public class BatchAdapter extends BaseAdapter {
    private ArrayList<BatchBean> list = null;
    private Context mContext;

    public BatchAdapter(Context mContext, ArrayList<BatchBean> list) {
        this.mContext = mContext;
        this.list = list;
    }

    public void updateListView(ArrayList<BatchBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public int getCount() {
        return this.list.size();
    }

    public Object getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup arg2) {
        ViewHolder viewHolder = null;
        final BatchBean batchBean = list.get(position);
        Log.w("batch","...>>"+batchBean.toString());

        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.batch_list_item,
                    null);
            viewHolder.batch_code = (TextView) view
                    .findViewById(R.id.batch_code);
            viewHolder.batch_name = (TextView) view.findViewById(R.id.batch_name);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.batch_code.setText(batchBean.PLANTNAME);
        viewHolder.batch_name.setText(batchBean.STGE_LOCNAME);

        return view;
    }

    final static class ViewHolder {
        TextView batch_code;
        TextView batch_name;

    }
}