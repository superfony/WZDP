package com.epsmart.wzcc.activity.supply.approval.parcelable;

import java.io.Serializable;

/**
 * @author fony
 */
public class ItemBean implements Serializable {
	public String DELIVERINFORMLINE;
	public String DELIVERINFORMCOD;
	public String SUPPLYPLANCODE;
	public String PURCHASELINECODE;
	public String LOCALMATERIALID;
	public String MATERIALTEXT;
	public String MATERIALUNIT;
	public String DELIVERYAMOUNT;
	public String PLANDELIVERYAMOU;
	public String HANDOVERAMOUNT;
	public String RETURNAMOUNT;
	public String ACCEAMOUNT;
	public String EXPECTEDARRIVALD;
	public String ACTUALDELIVERDAT;
	public String STGE_LOC;// 库存地点
	public boolean isCheckbox=false;

	public String getSTGE_LOC() {
		return STGE_LOC;
	}
	public void setSTGE_LOC(String STGE_LOC) {
		this.STGE_LOC = STGE_LOC;
	}
	public String getDELIVERINFORMLINE() {
		return DELIVERINFORMLINE;
	}

	public void setDELIVERINFORMLINE(String DELIVERINFORMLINE) {
		this.DELIVERINFORMLINE = DELIVERINFORMLINE;
	}

	public String getDELIVERINFORMCOD() {
		return DELIVERINFORMCOD;
	}

	public void setDELIVERINFORMCOD(String DELIVERINFORMCOD) {
		this.DELIVERINFORMCOD = DELIVERINFORMCOD;
	}

	public String getSUPPLYPLANCODE() {
		return SUPPLYPLANCODE;
	}

	public void setSUPPLYPLANCODE(String SUPPLYPLANCODE) {
		this.SUPPLYPLANCODE = SUPPLYPLANCODE;
	}

	public String getPURCHASELINECODE() {
		return PURCHASELINECODE;
	}

	public void setPURCHASELINECODE(String PURCHASELINECODE) {
		this.PURCHASELINECODE = PURCHASELINECODE;
	}

	public String getLOCALMATERIALID() {
		return LOCALMATERIALID;
	}

	public void setLOCALMATERIALID(String LOCALMATERIALID) {
		this.LOCALMATERIALID = LOCALMATERIALID;
	}

	public String getMATERIALTEXT() {
		return MATERIALTEXT;
	}

	public void setMATERIALTEXT(String MATERIALTEXT) {
		this.MATERIALTEXT = MATERIALTEXT;
	}

	public String getMATERIALUNIT() {
		return MATERIALUNIT;
	}

	public void setMATERIALUNIT(String MATERIALUNIT) {
		this.MATERIALUNIT = MATERIALUNIT;
	}

	public String getDELIVERYAMOUNT() {
		return DELIVERYAMOUNT;
	}

	public void setDELIVERYAMOUNT(String DELIVERYAMOUNT) {
		this.DELIVERYAMOUNT = DELIVERYAMOUNT;
	}

	public String getPLANDELIVERYAMOU() {
		return PLANDELIVERYAMOU;
	}

	public void setPLANDELIVERYAMOU(String PLANDELIVERYAMOU) {
		this.PLANDELIVERYAMOU = PLANDELIVERYAMOU;
	}

	public String getHANDOVERAMOUNT() {
		return HANDOVERAMOUNT;
	}

	public void setHANDOVERAMOUNT(String HANDOVERAMOUNT) {
		this.HANDOVERAMOUNT = HANDOVERAMOUNT;
	}

	public String getRETURNAMOUNT() {
		return RETURNAMOUNT;
	}

	public void setRETURNAMOUNT(String RETURNAMOUNT) {
		this.RETURNAMOUNT = RETURNAMOUNT;
	}

	public String getACCEAMOUNT() {
		return ACCEAMOUNT;
	}

	public void setACCEAMOUNT(String ACCEAMOUNT) {
		this.ACCEAMOUNT = ACCEAMOUNT;
	}

	public String getEXPECTEDARRIVALD() {
		return EXPECTEDARRIVALD;
	}

	public void setEXPECTEDARRIVALD(String EXPECTEDARRIVALD) {
		this.EXPECTEDARRIVALD = EXPECTEDARRIVALD;
	}

	public String getACTUALDELIVERDAT() {
		return ACTUALDELIVERDAT;
	}

	public void setACTUALDELIVERDAT(String ACTUALDELIVERDAT) {
		this.ACTUALDELIVERDAT = ACTUALDELIVERDAT;
	}

	public ItemBean() {
	}

	@Override
	public String toString() {
		return "Field [DELIVERINFORMLINE=" + DELIVERINFORMLINE + ", DELIVERINFORMCOD="
				+ DELIVERINFORMCOD + ", SUPPLYPLANCODE=" + SUPPLYPLANCODE + ", LOCALMATERIALID="
				+ LOCALMATERIALID + "]";
	}
}
