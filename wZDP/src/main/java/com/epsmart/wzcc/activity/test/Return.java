package com.epsmart.wzcc.activity.test;

import android.renderscript.BaseObj;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

/**
 * Created by fony on 15-8-10.
 */
public class Return implements KvmSerializable {

    private String TYPE;
    private String MESSAGE;
    private String RETURNVALUE;


    public String getTYPE() {
        return TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }



   public   Return(){}
   public  Return(String TYPE,String MESSAGE,String RETURNVALUE){
        this.TYPE=TYPE;
        this.MESSAGE=MESSAGE;
       this.RETURNVALUE=RETURNVALUE;
    }

    @Override
    public String toString() {
        return "type="+TYPE+"message="+MESSAGE+"returnvalue="+RETURNVALUE;
    }

    @Override
    public Object getProperty(int i) {
        return null;
    }

    @Override
    public int getPropertyCount() {
        return 3;
    }

    @Override
    public void setProperty(int i, Object o) {

        switch (i) {

            case 0:
                TYPE = o.toString();
                break;
            case 1:
                MESSAGE = o.toString();
                break;
            case 2:
                RETURNVALUE = o.toString();
                break;
            default:
                break;
        }

    }

    @Override
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {

        switch (i) {

            case 0:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "TYPE";
                break;
            case 1:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "MESSAGE";
                break;
            case 2:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "RETURNVALUE";
                break;
            default:
                break;


        }

    }
}
