package com.epsmart.wzcc.activity.supply.approval.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;


/**
 * @author fony
 */
public class BatchBean implements Parcelable {
    @DatabaseField(index = true)
    public String ID;
    @DatabaseField
    public String PLANT;
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


    public BatchBean() {
    }


    public static final Creator<BatchBean> CREATOR = new Creator<BatchBean>() {
        @SuppressWarnings("unchecked")
        public BatchBean createFromParcel(Parcel source) {
            BatchBean field = new BatchBean();
            field.ID = source.readString();
            field.PLANT = source.readString();
            field.PLANTNAME = source.readString();
            field.PLANTADDR = source.readString();
            field.STGE_LOC = source.readString();
            field.STGE_LOCNAME = source.readString();
            field.STGE_LOCADDR = source.readString();
            field.WHSENUMBER = source.readString();
            field.WHSENAME = source.readString();
            field.COM_CODE = source.readString();
            field.UPDATE_TIME = source.readString();
            return field;
        }

        public BatchBean[] newArray(int size) {
            return new BatchBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(ID);
        parcel.writeString(PLANT);
        parcel.writeString(PLANTNAME);
        parcel.writeString(PLANTADDR);
        parcel.writeString(STGE_LOC);
        parcel.writeString(STGE_LOCNAME);
        parcel.writeString(STGE_LOCADDR);
        parcel.writeString(WHSENUMBER);
        parcel.writeString(WHSENAME);
        parcel.writeString(COM_CODE);
        parcel.writeString(UPDATE_TIME);

    }


    @Override
    public String toString() {
        return "BatchBean [PLANTNAME=" + PLANTNAME + ", name="
                + COM_CODE + "]";
    }
}
