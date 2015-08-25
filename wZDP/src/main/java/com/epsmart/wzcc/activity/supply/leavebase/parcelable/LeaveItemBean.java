package com.epsmart.wzcc.activity.supply.leavebase.parcelable;

import java.io.Serializable;

/**
 * @author fony
 */
public class LeaveItemBean implements Serializable {
	public String RESERV_NO;
	public String RES_ITEM;
	public String RESDATE;
	public String MATERIAL;
	public String MATERIALTEXT;
	public String OPEN_QUAN;
	public String ENTRY_QNT;
	public String UNIT;
	public String BATCH;
	public String PLANT;
	public String WBS_ELEM;
	public String STGE_LOC;
	public String MOVE_TYPE;
	public boolean isCheckbox=false;


	public String getBATCH() {
		return BATCH;
	}

	public void setBATCH(String BATCH) {
		this.BATCH = BATCH;
	}

	public String getENTRY_QNT() {
		return ENTRY_QNT;
	}

	public void setENTRY_QNT(String ENTRY_QNT) {
		this.ENTRY_QNT = ENTRY_QNT;
	}

	public boolean isCheckbox() {
		return isCheckbox;
	}

	public void setIsCheckbox(boolean isCheckbox) {
		this.isCheckbox = isCheckbox;
	}

	public String getMATERIAL() {
		return MATERIAL;
	}

	public void setMATERIAL(String MATERIAL) {
		this.MATERIAL = MATERIAL;
	}

	public String getMATERIALTEXT() {
		return MATERIALTEXT;
	}

	public void setMATERIALTEXT(String MATERIALTEXT) {
		this.MATERIALTEXT = MATERIALTEXT;
	}

	public String getMOVE_TYPE() {
		return MOVE_TYPE;
	}

	public void setMOVE_TYPE(String MOVE_TYPE) {
		this.MOVE_TYPE = MOVE_TYPE;
	}

	public String getOPEN_QUAN() {
		return OPEN_QUAN;
	}

	public void setOPEN_QUAN(String OPEN_QUAN) {
		this.OPEN_QUAN = OPEN_QUAN;
	}

	public String getPLANT() {
		return PLANT;
	}

	public void setPLANT(String PLANT) {
		this.PLANT = PLANT;
	}

	public String getRES_ITEM() {
		return RES_ITEM;
	}

	public void setRES_ITEM(String RES_ITEM) {
		this.RES_ITEM = RES_ITEM;
	}

	public String getRESDATE() {
		return RESDATE;
	}

	public void setRESDATE(String RESDATE) {
		this.RESDATE = RESDATE;
	}

	public String getRESERV_NO() {
		return RESERV_NO;
	}

	public void setRESERV_NO(String RESERV_NO) {
		this.RESERV_NO = RESERV_NO;
	}

	public String getSTGE_LOC() {
		return STGE_LOC;
	}

	public void setSTGE_LOC(String STGE_LOC) {
		this.STGE_LOC = STGE_LOC;
	}

	public String getUNIT() {
		return UNIT;
	}

	public void setUNIT(String UNIT) {
		this.UNIT = UNIT;
	}

	public String getWBS_ELEM() {
		return WBS_ELEM;
	}

	public void setWBS_ELEM(String WBS_ELEM) {
		this.WBS_ELEM = WBS_ELEM;
	}





	public LeaveItemBean() {
	}

	@Override
	public String toString() {
		return "Field [RESERV_NO=" + RESERV_NO + ", RES_ITEM="
				+ RES_ITEM + ", RESDATE=" + RESDATE + ", LOCALMATERIALID="
				 + "]";
	}
}
