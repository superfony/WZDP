package com.epsmart.wzcc.db.table;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by fony on 15-8-10.
 * 交接验收通知单表头信息
 */
public class AppDetailTable {

//    @DatabaseField(generatedId = true)
//    public int id;
    @DatabaseField(index = true)
    public String DED_ID;
    @DatabaseField
    public String DE_ID;
    @DatabaseField
    public String DELIVERINFORMCOD;
    @DatabaseField
    public String DELIVERINFORMLINE;
    @DatabaseField
    public String SUPPLYPLANCODE;
    @DatabaseField
    public String PURCHASELINECODE;
    @DatabaseField
    public String LOCALMATERIALID;
    @DatabaseField
    public String MATERIALTEXT;
    @DatabaseField
    public String MATERIALUNIT;
    @DatabaseField
    public String DELIVERYAMOUNT;
    @DatabaseField
    public String PLANDELIVERYAMOU;
    @DatabaseField
    public String HANDOVERAMOUNT;
    @DatabaseField
    public String RETURNAMOUNT;
    @DatabaseField
    public String ACCEAMOUNT;
    @DatabaseField
    public String EXPECTEDARRIVALD;
    @DatabaseField
    public String ACTUALDELIVERDAT;
    @DatabaseField
    public String PREEXPECTEDARRIVALD;
    @DatabaseField
    public String WD_STATES;
    @DatabaseField
    public String WD_MEMO;
    @DatabaseField
    public String UPDATE_TIME;


//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }

    public String getDED_ID() {
        return DED_ID;
    }

    public void setDED_ID(String DED_ID) {
        this.DED_ID = DED_ID;
    }

    public String getDE_ID() {
        return DE_ID;
    }

    public void setDE_ID(String DE_ID) {
        this.DE_ID = DE_ID;
    }

    public String getDELIVERINFORMCOD() {
        return DELIVERINFORMCOD;
    }

    public void setDELIVERINFORMCOD(String DELIVERINFORMCOD) {
        this.DELIVERINFORMCOD = DELIVERINFORMCOD;
    }

    public String getDELIVERINFORMLINE() {
        return DELIVERINFORMLINE;
    }

    public void setDELIVERINFORMLINE(String DELIVERINFORMLINE) {
        this.DELIVERINFORMLINE = DELIVERINFORMLINE;
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

    public String getPREEXPECTEDARRIVALD() {
        return PREEXPECTEDARRIVALD;
    }

    public void setPREEXPECTEDARRIVALD(String PREEXPECTEDARRIVALD) {
        this.PREEXPECTEDARRIVALD = PREEXPECTEDARRIVALD;
    }

    public String getWD_STATES() {
        return WD_STATES;
    }

    public void setWD_STATES(String WD_STATES) {
        this.WD_STATES = WD_STATES;
    }

    public String getWD_MEMO() {
        return WD_MEMO;
    }

    public void setWD_MEMO(String WD_MEMO) {
        this.WD_MEMO = WD_MEMO;
    }

    public String getUPDATE_TIME() {
        return UPDATE_TIME;
    }

    public void setUPDATE_TIME(String UPDATE_TIME) {
        this.UPDATE_TIME = UPDATE_TIME;
    }
}
