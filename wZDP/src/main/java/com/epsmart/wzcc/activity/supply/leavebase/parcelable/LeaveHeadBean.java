package com.epsmart.wzcc.activity.supply.leavebase.parcelable;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * @author fony
 */
public class LeaveHeadBean implements Parcelable {
	public String WHSENUMBER;
	public String RESERV_NO;
	public String WBS_ELEM;
	public String COSTCENTER;
	public String ORDERID;
	public String PLANT;
	public String NETWORK;

	public String STGE_LOC;


	public LeaveHeadBean() {
	}


	public static final Creator<LeaveHeadBean> CREATOR = new Creator<LeaveHeadBean>() {
		@SuppressWarnings("unchecked")
		public LeaveHeadBean createFromParcel(Parcel source) {
			LeaveHeadBean field = new LeaveHeadBean();
			field.WHSENUMBER=source.readString();
			field.RESERV_NO=source.readString();
			field.WBS_ELEM=source.readString();
			field.COSTCENTER=source.readString();
			field.WHSENUMBER=source.readString();
			field.ORDERID=source.readString();
			field.PLANT=source.readString();
			field.NETWORK=source.readString();
			field.STGE_LOC=source.readString();



			return field;
		}

		public LeaveHeadBean[] newArray(int size) {
			return new LeaveHeadBean[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeString(WHSENUMBER);
		parcel.writeString(RESERV_NO);
		parcel.writeString(WBS_ELEM);
		parcel.writeString(COSTCENTER);
		parcel.writeString(WHSENUMBER);
		parcel.writeString(ORDERID);
		parcel.writeString(PLANT);
		parcel.writeString(NETWORK);
		parcel.writeString(STGE_LOC);

	}











	@Override
	public String toString() {
		return "HeadBean [WHSENUMBER=" + WHSENUMBER + ", RESERV_NO="
				+ RESERV_NO + ", WBS_ELEM=" + WBS_ELEM + ", CONTRACTID="
				 + "]";
	}
}
