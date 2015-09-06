package com.epsmart.wzcc.db.table;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by fony on 15-8-10.
 * 发货通知单表头信息
 */
public class LeaveHeadTable {
    @DatabaseField
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

}
