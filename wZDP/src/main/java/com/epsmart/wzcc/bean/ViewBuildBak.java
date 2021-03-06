package com.epsmart.wzcc.bean;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.epsmart.wzcc.R;
import com.epsmart.wzcc.activity.supply.bean.Field;

/**
 * @author fony
 */
public class ViewBuildBak extends ViewBuilder<WorkOrder> {

    @Override
    public View createView(LayoutInflater inflater, int position, WorkOrder data) {
        View convertView = inflater.inflate(R.layout.app_connection_item,
                null);
        ViewHolder holder = new ViewHolder();
        holder.icon = (ImageView) convertView.findViewById(R.id.state_icon);

        holder.title = (TextView) convertView.findViewById(R.id.project);
        holder.titleValue = (TextView) convertView
                .findViewById(R.id.project_name);
        holder.num = (TextView) convertView.findViewById(R.id.wztype);
        holder.numValue = (TextView) convertView.findViewById(R.id.wztype_value);
        holder.time = (TextView) convertView.findViewById(R.id.supply);
        holder.timeValue = (TextView) convertView.findViewById(R.id.supply_value);
        convertView.setTag(holder);
        fillData(holder, data);
        return convertView;
    }

    @Override
    public void updateView(View view, int position, WorkOrder data) {
        fillData((ViewHolder) view.getTag(), data);
    }

    private void fillData(ViewHolder holder, WorkOrder workOrder) {


        Field field = workOrder.fields.get("DELIVERINFORMCOD");
        holder.title.setText(null == field ? "" : (field.fieldChName + ":"));
        holder.titleValue.setText(null == field ? "" : "" + field.fieldContent);


        field = null;
        field = workOrder.fields.get("PROJECTNAME");//物料类型
        holder.num.setText(null == field ? "" : (field.fieldChName + ":"));
        holder.numValue.setText(null == field ? "" : (field.fieldContent + ""));

        field = null;
        field = workOrder.fields.get("SUPPLIERNAME");
        holder.time.setText(null == field ? "" : (field.fieldChName + ":"));
        holder.timeValue.setText(null == field ? "" : (field.fieldContent + ""));

        field = null;
        field = workOrder.fields.get("ZDJZT");//0 未完成 1、已交接 2、 已完成
        if(field==null){
            return;
        }
        if ("0".equals(field.fieldContent)) {
            holder.icon.setBackgroundResource(R.drawable.state_noconfrimed);
        } else if ("1".equals(field.fieldContent)) {
            holder.icon.setBackgroundResource(R.drawable.state_alyconfrimed);
        } else if ("2".equals(field.fieldContent)) {
            holder.icon.setBackgroundResource(R.drawable.state_complay);
        }
    }

    class ViewHolder {
        ImageView icon;
        TextView title;
        TextView titleValue;
        TextView num;
        TextView numValue;
        TextView time;
        TextView timeValue;

    }
}
