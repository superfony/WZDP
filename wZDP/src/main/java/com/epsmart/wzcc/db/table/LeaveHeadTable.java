package com.epsmart.wzcc.db.table;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by fony on 15-8-10.
 * 发货通知单表头信息
 */
public class LeaveHeadTable {
    @DatabaseField(generatedId = true)
    public int id;
    @DatabaseField(index = true)
    public String RE_ID;
    @DatabaseField
    public String RESERV_NO;
    @DatabaseField
    public String WBS_ELEM;
    @DatabaseField
    public String COSTCENTER;
    @DatabaseField
    public String ORDERID;
    @DatabaseField
    public String PLANT;
    @DatabaseField
    public String WHSENUMBER;
    @DatabaseField
    public String NETWORK;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRE_ID() {
        return RE_ID;
    }

    public void setRE_ID(String RE_ID) {
        this.RE_ID = RE_ID;
    }

    public String getRESERV_NO() {
        return RESERV_NO;
    }

    public void setRESERV_NO(String RESERV_NO) {
        this.RESERV_NO = RESERV_NO;
    }

    public String getWBS_ELEM() {
        return WBS_ELEM;
    }

    public void setWBS_ELEM(String WBS_ELEM) {
        this.WBS_ELEM = WBS_ELEM;
    }

    public String getCOSTCENTER() {
        return COSTCENTER;
    }

    public void setCOSTCENTER(String COSTCENTER) {
        this.COSTCENTER = COSTCENTER;
    }

    public String getORDERID() {
        return ORDERID;
    }

    public void setORDERID(String ORDERID) {
        this.ORDERID = ORDERID;
    }

    public String getPLANT() {
        return PLANT;
    }

    public void setPLANT(String PLANT) {
        this.PLANT = PLANT;
    }

    public String getWHSENUMBER() {
        return WHSENUMBER;
    }

    public void setWHSENUMBER(String WHSENUMBER) {
        this.WHSENUMBER = WHSENUMBER;
    }

    public String getNETWORK() {
        return NETWORK;
    }

    public void setNETWORK(String NETWORK) {
        this.NETWORK = NETWORK;
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
