package com.epsmart.wzcc.activity.supply.approval;

import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.epsmart.wzcc.R;

import com.epsmart.wzcc.activity.supply.approval.parcelable.ItemBean;

/**
 *
 *
 * @author fony
 */

public class ApprovalAdapter extends BaseAdapter {
    private List<ItemBean> list = null;
    private Context mContext;


    public ApprovalAdapter(Context mContext, List<ItemBean> list) {
        this.mContext = mContext;
        this.list = list;
    }

    public void updateListView(List<ItemBean> list) {
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
        final ItemBean real = list.get(position);

        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.app_approval_detail_item,
                    null);
            viewHolder.wlcode = (TextView) view
                    .findViewById(R.id.wlcode);
            viewHolder.htcount = (TextView) view.findViewById(R.id.htcount);
            viewHolder.connection_count = (EditText) view.findViewById(R.id.connection_count);

            viewHolder.wldesc = (TextView) view
                    .findViewById(R.id.wldesc);
            viewHolder.fhcount = (TextView) view.findViewById(R.id.fhcount);
            viewHolder.stockadress = (EditText) view.findViewById(R.id.stockadress);
            viewHolder.checkBox=(CheckBox)view.findViewById(R.id.checkbox_item);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.wlcode.setText(real.DELIVERINFORMCOD);
        viewHolder.htcount.setText(real.DELIVERYAMOUNT);
        viewHolder.connection_count.setText(real.HANDOVERAMOUNT);
        viewHolder.wldesc.setText(real.MATERIALTEXT);
        viewHolder.fhcount.setText(real.PLANDELIVERYAMOU);
        viewHolder.stockadress.setText(real.STGE_LOC);

        if(real.isCheckbox){
            viewHolder.checkBox.setChecked(true);
        }else{
            viewHolder.checkBox.setChecked(false);
        }

        return view;
    }

    final static class ViewHolder {
        TextView wlcode;
        TextView htcount;
        EditText connection_count;
        TextView wldesc;
        TextView fhcount;
        TextView stockadress;
        CheckBox checkBox;
    }
}