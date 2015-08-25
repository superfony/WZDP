package com.epsmart.wzcc.activity.supply.approval.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

import com.epsmart.wzcc.activity.supply.bean.BasicEntity;

/**
 * 
 * @author fony
 *
 */
public class ApprovalResponse implements Parcelable {
	public String result;
	public String message;
	public EntityContent entity;

	public static final Creator<ApprovalResponse> CREATOR = new Creator<ApprovalResponse>() {
		public ApprovalResponse createFromParcel(Parcel source) {
			ApprovalResponse field = new ApprovalResponse();
			field.result = source.readString();
			field.message = source.readString();
			field.entity = source.readParcelable(BasicEntity.class
					.getClassLoader());
			return field;
		}

		public ApprovalResponse[] newArray(int size) {
			return new ApprovalResponse[size];
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
		parcel.writeParcelable(entity, Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
	}

	@Override
	public String toString() {
		return "BasicResponse [result=" + result + ", message=" + message
				+ ", entity=" + entity + "]";
	}
}
