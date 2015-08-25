package com.epsmart.wzcc.activity.supply.approval.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

import com.epsmart.wzcc.activity.supply.bean.BasicEntity;

import java.util.ArrayList;

/**
 * 
 * @author fony
 *
 */
public class BatchResponse implements Parcelable {
	public String result;
	public String message;
	public ArrayList<BatchBean> itemBeansList;

	public static final Creator<BatchResponse> CREATOR = new Creator<BatchResponse>() {
		public BatchResponse createFromParcel(Parcel source) {
			BatchResponse field = new BatchResponse();
			field.result = source.readString();
			field.message = source.readString();
			field.itemBeansList = source.readArrayList(BatchBean.class.getClassLoader());
			return field;
		}

		public BatchResponse[] newArray(int size) {
			return new BatchResponse[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeString(result);
		parcel.writeString(message);
		parcel.writeList(itemBeansList);
	}

	@Override
	public String toString() {
		return "BasicResponse [result=" + result + ", message=" + message
				+ ", entity="  + "]";
	}
}
