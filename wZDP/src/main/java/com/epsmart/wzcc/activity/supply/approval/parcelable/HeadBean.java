package com.epsmart.wzcc.activity.supply.approval.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

import com.epsmart.wzcc.activity.supply.bean.DataRow;

import java.util.HashMap;


/**
 * @author fony
 */
public class HeadBean implements Parcelable {
	public String DELIVERINFORMCOD;
	public String PURCHASECODE;
	public String PLANT;
	public String STGE_LOC;
	public String WHSENUMBER;
	public String SUPPLIERNAME;
	public String ACTUALDELIVERYPL;
	public String PROJECTNAME;
	public String SUPPLINKMAN;
	public String SUPPLINKMANTELEP;
	public String CARRLINKMAN;
	public String CARRLINKMANTELEP;
	public String CONTRACTID;



	public String getCONTRACTID() {
		return CONTRACTID;
	}

	public void setCONTRACTID(String CONTRACTID) {
		this.CONTRACTID = CONTRACTID;
	}

	public String getDELIVERINFORMCOD() {
		return DELIVERINFORMCOD;
	}

	public void setDELIVERINFORMCOD(String DELIVERINFORMCOD) {
		this.DELIVERINFORMCOD = DELIVERINFORMCOD;
	}

	public String getPURCHASECODE() {
		return PURCHASECODE;
	}

	public void setPURCHASECODE(String PURCHASECODE) {
		this.PURCHASECODE = PURCHASECODE;
	}

	public String getPLANT() {
		return PLANT;
	}

	public void setPLANT(String PLANT) {
		this.PLANT = PLANT;
	}

	public String getSTGE_LOC() {
		return STGE_LOC;
	}

	public void setSTGE_LOC(String STGE_LOC) {
		this.STGE_LOC = STGE_LOC;
	}

	public String getWHSENUMBER() {
		return WHSENUMBER;
	}

	public void setWHSENUMBER(String WHSENUMBER) {
		this.WHSENUMBER = WHSENUMBER;
	}

	public String getSUPPLIERNAME() {
		return SUPPLIERNAME;
	}

	public void setSUPPLIERNAME(String SUPPLIERNAME) {
		this.SUPPLIERNAME = SUPPLIERNAME;
	}

	public String getACTUALDELIVERYPL() {
		return ACTUALDELIVERYPL;
	}

	public void setACTUALDELIVERYPL(String ACTUALDELIVERYPL) {
		this.ACTUALDELIVERYPL = ACTUALDELIVERYPL;
	}

	public String getPROJECTNAME() {
		return PROJECTNAME;
	}

	public void setPROJECTNAME(String PROJECTNAME) {
		this.PROJECTNAME = PROJECTNAME;
	}

	public String getSUPPLINKMAN() {
		return SUPPLINKMAN;
	}

	public void setSUPPLINKMAN(String SUPPLINKMAN) {
		this.SUPPLINKMAN = SUPPLINKMAN;
	}

	public String getSUPPLINKMANTELEP() {
		return SUPPLINKMANTELEP;
	}

	public void setSUPPLINKMANTELEP(String SUPPLINKMANTELEP) {
		this.SUPPLINKMANTELEP = SUPPLINKMANTELEP;
	}




	public HeadBean() {
	}


	public static final Parcelable.Creator<HeadBean> CREATOR = new Creator<HeadBean>() {
		@SuppressWarnings("unchecked")
		public HeadBean createFromParcel(Parcel source) {
			HeadBean field = new HeadBean();
			field.DELIVERINFORMCOD=source.readString();
			field.PURCHASECODE=source.readString();
			field.PLANT=source.readString();
			field.STGE_LOC=source.readString();
			field.WHSENUMBER=source.readString();
			field.SUPPLIERNAME=source.readString();
			field.ACTUALDELIVERYPL=source.readString();
			field.PROJECTNAME=source.readString();
			field.SUPPLINKMAN=source.readString();
			field.CARRLINKMAN=source.readString();
			field.CARRLINKMANTELEP=source.readString();
			field.SUPPLINKMANTELEP=source.readString();


			return field;
		}

		public HeadBean[] newArray(int size) {
			return new HeadBean[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeString(DELIVERINFORMCOD);
		parcel.writeString(PURCHASECODE);
		parcel.writeString(PLANT);
		parcel.writeString(STGE_LOC);
		parcel.writeString(WHSENUMBER);
		parcel.writeString(SUPPLIERNAME);
		parcel.writeString(ACTUALDELIVERYPL);
		parcel.writeString(PROJECTNAME);
		parcel.writeString(SUPPLINKMAN);
		parcel.writeString(CARRLINKMAN);
		parcel.writeString(CARRLINKMANTELEP);
		parcel.writeString(SUPPLINKMANTELEP);
	}











	@Override
	public String toString() {
		return "HeadBean [DELIVERINFORMCOD=" + DELIVERINFORMCOD + ", PURCHASECODE="
				+ PURCHASECODE + ", PLANT=" + PLANT + ", CONTRACTID="
				+ CONTRACTID + "]";
	}
}
