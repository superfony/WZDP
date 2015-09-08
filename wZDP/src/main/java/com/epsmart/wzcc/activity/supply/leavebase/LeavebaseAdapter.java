package com.epsmart.wzcc.activity.supply.leavebase;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.epsmart.wzcc.R;
import com.epsmart.wzcc.activity.supply.leavebase.parcelable.LeaveItemBean;

import java.util.List;

/**
 *
 *
 * @author fony
 */

public class LeavebaseAdapter extends BaseAdapter {
    private List<LeaveItemBean> list = null;
    private Context mContext;

    public LeavebaseAdapter(Context mContext, List<LeaveItemBean> list) {
        this.mContext = mContext;
        this.list = list;
    }

    public void updateListView(List<LeaveItemBean> list) {
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
       final   ViewHolder viewHolder;
        final LeaveItemBean real = list.get(position);

        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.leave_detail_listitem,null);

            viewHolder.wlcode = (TextView) view.findViewById(R.id.wlcode);
            viewHolder.reservasion_num = (TextView) view.findViewById(R.id.reservasion_num);
            viewHolder.hangxm = (TextView) view.findViewById(R.id.hangxm);
            viewHolder.leave_factory = (TextView) view.findViewById(R.id.leave_factory);
            viewHolder.wq_count = (TextView) view.findViewById(R.id.wq_count);
            viewHolder.wldesc = (TextView) view.findViewById(R.id.wldesc);
            viewHolder.actual_count = (EditText) view.findViewById(R.id.actual_count);
            viewHolder.stockadress = (EditText) view.findViewById(R.id.stockadress);
            viewHolder.pc = (EditText) view.findViewById(R.id.pc);
            viewHolder.checkbox_item=(CheckBox)view.findViewById(R.id.checkbox_item);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.wlcode.setText(real.MATERIAL);
        viewHolder.reservasion_num.setText(real.RESERV_NO);
        viewHolder.hangxm.setText(real.RES_ITEM);
        viewHolder.wldesc.setText(real.MATERIALTEXT);
        viewHolder.wq_count.setText(real.OPEN_QUAN);
        viewHolder.leave_factory.setText(real.PLANT);
        viewHolder.pc.setText(real.BATCH);
        viewHolder.actual_count.setText(real.ENTRY_QNT);
        viewHolder.stockadress.setText(real.STGE_LOC);
        viewHolder.pc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                real.BATCH = s.toString();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        viewHolder.actual_count.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                real.ENTRY_QNT = s.toString();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        viewHolder.checkbox_item.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                  if(b){
                      viewHolder.checkbox_item.setChecked(true);
                      real.isCheckbox=true;

                  }else{
                      viewHolder.checkbox_item.setChecked(false);
                      real.isCheckbox=false;
                  }
            }
        });
    //
        if(real.isCheckbox){
            viewHolder.checkbox_item.setChecked(true);
        }else{
            viewHolder.checkbox_item.setChecked(false);
        }

        return view;
    }

    class ViewHolder {
         TextView wlcode;
         TextView reservasion_num;
         TextView hangxm;
         TextView wq_count;
         TextView leave_factory;

         TextView wldesc;
         EditText actual_count;
         EditText stockadress;
         EditText pc;
         CheckBox checkbox_item;
    }

}