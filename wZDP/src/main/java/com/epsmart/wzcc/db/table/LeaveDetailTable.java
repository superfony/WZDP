package com.epsmart.wzcc.db.table;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by fony on 15-8-10.
 * 发货明细信息
 */
public class LeaveDetailTable {
    @DatabaseField(generatedId = true)
    public int id;
    @DatabaseField(index = true)
    public String RED_ID;
    @DatabaseField
    public String RE_ID;
    @DatabaseField
    public String RESERV_NO;
    @DatabaseField
    public String DELIVERINFORMLINE;
    @DatabaseField
    public String RES_ITEM;
    @DatabaseField
    public String RESDATE;
    @DatabaseField
    public String MATERIAL;
    @DatabaseField
    public String MATERIALTEXT;
    @DatabaseField
    public String OPEN_QUAN;
    @DatabaseField
    public String ENTRY_QNT;
    @DatabaseField
    public String UNIT;
    @DatabaseField
    public String BATCH;
    @DatabaseField
    public String PLANT;
    @DatabaseField
    public String STGE_LOC;
    @DatabaseField
    public String WBS_ELEM;
    @DatabaseField
    public String MOVE_TYPE;
    @DatabaseField
    public String WD_STATES;
    @DatabaseField
    public String WD_MEMO;
    @DatabaseField
    public String UPDATE_TIME;


}
