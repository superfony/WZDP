package com.epsmart.wzcc.activity.supply.approval.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

import com.epsmart.wzcc.activity.supply.bean.DataRow;
import com.epsmart.wzcc.activity.supply.bean.Field;

import java.util.ArrayList;
import java.util.HashMap;

public class EntityContent implements Parcelable {
	public HeadBean headBean;
	public ArrayList<ItemBean> itemBeansList;

	public static final Creator<EntityContent> CREATOR = new Creator<EntityContent>() {
		public EntityContent createFromParcel(Parcel source) {
			EntityContent field = new EntityContent();
			field.headBean=source.readParcelable(HeadBean.class.getClassLoader());
			field.itemBeansList = source.readArrayList(ItemBean.class.getClassLoader());
			return field;
		}

		public EntityContent[] newArray(int size) {
			return new EntityContent[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeParcelable(headBean, Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
		parcel.writeList(itemBeansList);
	}
}
