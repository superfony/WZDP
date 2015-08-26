package com.epsmart.wzcc.db.table;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by fony on 15-8-10.
 * 交接验收通知单表头信息
 */
public class AppHeadTable {

//    @DatabaseField
//    public int id;
    @DatabaseField(index = true)
    public String DE_ID;
    @DatabaseField
    public String DELIVERINFORMCOD;
    @DatabaseField
    public String PURCHASECODE;
    @DatabaseField
    public String PLANT;
    @DatabaseField
    public String STGE_LOC;
    @DatabaseField
    public String WHSENUMBER;
    @DatabaseField
    public String SUPPLIERCODE;
    @DatabaseField
    public String SUPPLIERNAME;
    @DatabaseField
    public String ACTUALDELIVERYPL;
    @DatabaseField
    public String PROJECTCODE;
    @DatabaseField
    public String PROJECTNAME;
    @DatabaseField
    public String PROJECTUNIT;
    @DatabaseField
    public String SUPPLINKMAN;
    @DatabaseField
    public String SUPPLINKMANTELEP;
    @DatabaseField
    public String CARRLINKMAN;
    @DatabaseField
    public String CARRLINKMANTELEP;
    @DatabaseField
    public String DELINKMAN;
    @DatabaseField
    public String DELINKMANTELEPHO;
    @DatabaseField
    public String PACKCOUNT;
    @DatabaseField
    public String STANDARD;
    @DatabaseField
    public String CONTRACTID;
    @DatabaseField
    public String PLANSTATES;
    @DatabaseField
    public String SENDNAME;
    @DatabaseField
    public String SENDTIME;
    @DatabaseField
    public String WD_STATES;
    @DatabaseField
    public String WD_MEMO;
    @DatabaseField
    public String ZDJZT;
    @DatabaseField
    public String UPDATE_TIME;


//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }

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

    public String getSUPPLIERCODE() {
        return SUPPLIERCODE;
    }

    public void setSUPPLIERCODE(String SUPPLIERCODE) {
        this.SUPPLIERCODE = SUPPLIERCODE;
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

    public String getPROJECTCODE() {
        return PROJECTCODE;
    }

    public void setPROJECTCODE(String PROJECTCODE) {
        this.PROJECTCODE = PROJECTCODE;
    }

    public String getPROJECTNAME() {
        return PROJECTNAME;
    }

    public void setPROJECTNAME(String PROJECTNAME) {
        this.PROJECTNAME = PROJECTNAME;
    }

    public String getPROJECTUNIT() {
        return PROJECTUNIT;
    }

    public void setPROJECTUNIT(String PROJECTUNIT) {
        this.PROJECTUNIT = PROJECTUNIT;
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

    public String getCARRLINKMAN() {
        return CARRLINKMAN;
    }

    public void setCARRLINKMAN(String CARRLINKMAN) {
        this.CARRLINKMAN = CARRLINKMAN;
    }

    public String getCARRLINKMANTELEP() {
        return CARRLINKMANTELEP;
    }

    public void setCARRLINKMANTELEP(String CARRLINKMANTELEP) {
        this.CARRLINKMANTELEP = CARRLINKMANTELEP;
    }

    public String getDELINKMAN() {
        return DELINKMAN;
    }

    public void setDELINKMAN(String DELINKMAN) {
        this.DELINKMAN = DELINKMAN;
    }

    public String getDELINKMANTELEPHO() {
        return DELINKMANTELEPHO;
    }

    public void setDELINKMANTELEPHO(String DELINKMANTELEPHO) {
        this.DELINKMANTELEPHO = DELINKMANTELEPHO;
    }

    public String getPACKCOUNT() {
        return PACKCOUNT;
    }

    public void setPACKCOUNT(String PACKCOUNT) {
        this.PACKCOUNT = PACKCOUNT;
    }

    public String getSTANDARD() {
        return STANDARD;
    }

    public void setSTANDARD(String STANDARD) {
        this.STANDARD = STANDARD;
    }

    public String getCONTRACTID() {
        return CONTRACTID;
    }

    public void setCONTRACTID(String CONTRACTID) {
        this.CONTRACTID = CONTRACTID;
    }

    public String getPLANSTATES() {
        return PLANSTATES;
    }

    public void setPLANSTATES(String PLANSTATES) {
        this.PLANSTATES = PLANSTATES;
    }

    public String getSENDNAME() {
        return SENDNAME;
    }

    public void setSENDNAME(String SENDNAME) {
        this.SENDNAME = SENDNAME;
    }

    public String getSENDTIME() {
        return SENDTIME;
    }

    public void setSENDTIME(String SENDTIME) {
        this.SENDTIME = SENDTIME;
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

    public String getZDJZT() {
        return ZDJZT;
    }

    public void setZDJZT(String ZDJZT) {
        this.ZDJZT = ZDJZT;
    }

    public String getUPDATE_TIME() {
        return UPDATE_TIME;
    }

    public void setUPDATE_TIME(String UPDATE_TIME) {
        this.UPDATE_TIME = UPDATE_TIME;
    }
}
