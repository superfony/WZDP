package com.epsmart.wzcc.activity.supply.leavebase.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

import com.epsmart.wzcc.activity.supply.bean.BasicEntity;

/**
 * 
 * @author fony
 *
 */
public class LeavebaseResponse implements Parcelable {
	public String result;
	public String message;
	public LeaveEntityContent entity;

	public static final Creator<LeavebaseResponse> CREATOR = new Creator<LeavebaseResponse>() {
		public LeavebaseResponse createFromParcel(Parcel source) {
			LeavebaseResponse field = new LeavebaseResponse();
			field.result = source.readString();
			field.message = source.readString();
			field.entity = source.readParcelable(BasicEntity.class
					.getClassLoader());
			return field;
		}

		public LeavebaseResponse[] newArray(int size) {
			return new LeavebaseResponse[size];
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
