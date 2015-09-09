package com.epsmart.wzcc.db.table;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by fony on 15-8-10.
 * 交接验收通知单表头信息
 */
public class StockTable {

    @DatabaseField(generatedId = true)
    public int id;
    @DatabaseField(index = true)
    public String STOCK_ID;
    @DatabaseField
    public String PLAN;
    @DatabaseField
    public String PLANTNAME;
    @DatabaseField
    public String PLANTADDR;
    @DatabaseField
    public String STGE_LOC;
    @DatabaseField
    public String STGE_LOCNAME;
    @DatabaseField
    public String STGE_LOCADDR;
    @DatabaseField
    public String WHSENUMBER;
    @DatabaseField
    public String WHSENAME;
    @DatabaseField
    public String COM_CODE;
    @DatabaseField
    public String UPDATE_TIME;
}
