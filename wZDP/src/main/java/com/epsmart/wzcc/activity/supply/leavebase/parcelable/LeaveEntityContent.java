package com.epsmart.wzcc.activity.supply.leavebase.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class LeaveEntityContent implements Parcelable {
	public LeaveHeadBean leaveHeadBean;
	public ArrayList<LeaveItemBean> leaveItemBeansList;

	public static final Creator<LeaveEntityContent> CREATOR = new Creator<LeaveEntityContent>() {
		public LeaveEntityContent createFromParcel(Parcel source) {
			LeaveEntityContent field = new LeaveEntityContent();
			field.leaveHeadBean =source.readParcelable(LeaveHeadBean.class.getClassLoader());
			field.leaveItemBeansList = source.readArrayList(LeaveItemBean.class.getClassLoader());
			return field;
		}

		public LeaveEntityContent[] newArray(int size) {
			return new LeaveEntityContent[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeParcelable(leaveHeadBean, Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
		parcel.writeList(leaveItemBeansList);
	}
}
