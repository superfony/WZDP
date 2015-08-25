package com.epsmart.wzcc.activity.supply.leavebase;

import com.epsmart.wzcc.activity.supply.leavebase.parcelable.LeavebaseResponse;
import com.epsmart.wzcc.activity.supply.leavebase.parcelable.LeaveEntityContent;
import com.epsmart.wzcc.activity.supply.leavebase.parcelable.LeaveHeadBean;
import com.epsmart.wzcc.activity.supply.leavebase.parcelable.LeaveItemBean;
import com.epsmart.wzcc.http.xml.handler.BaseParserHandler;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import java.util.ArrayList;

/**
 * 交接验收明细解析类
 */
public class LeaveDetailHandler extends BaseParserHandler {
    private StringBuilder builder;
    private LeavebaseResponse leavebaseResponse;
    private LeaveItemBean leaveItemBean;


    /**
     */
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        super.characters(ch, start, length);
        builder.append(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String name)
            throws SAXException {
        super.endElement(uri, localName, name);

        if ("WHSENUMBER".equalsIgnoreCase(localName)) {
            leavebaseResponse.entity.leaveHeadBean.WHSENUMBER = trim(builder.toString());
        } else if ("RESERV_NO".equalsIgnoreCase(localName)) {
            leavebaseResponse.entity.leaveHeadBean.RESERV_NO = trim(builder.toString());
        } else if ("WBS_ELEM".equalsIgnoreCase(localName)) {
            leavebaseResponse.entity.leaveHeadBean.WBS_ELEM = trim(builder.toString());
        } else if ("COSTCENTER".equalsIgnoreCase(localName)) {
            leavebaseResponse.entity.leaveHeadBean.COSTCENTER = trim(builder.toString());
        } else if ("ORDERID".equalsIgnoreCase(localName)) {
            leavebaseResponse.entity.leaveHeadBean.ORDERID = trim(builder.toString());
        } else if ("PLANT".equalsIgnoreCase(localName)) {
            leavebaseResponse.entity.leaveHeadBean.PLANT = trim(builder.toString());
        } else if ("NETWORK".equalsIgnoreCase(localName)) {
            leavebaseResponse.entity.leaveHeadBean.NETWORK = trim(builder.toString());
        }  else if ("RESERV_NO".equalsIgnoreCase(localName)) {
            leaveItemBean.RESERV_NO = trim(builder.toString());
        } else if ("RES_ITEM".equalsIgnoreCase(localName)) {
            leaveItemBean.RES_ITEM = trim(builder.toString());
        } else if ("RESDATE".equalsIgnoreCase(localName)) {
            leaveItemBean.RESDATE = trim(builder.toString());
        } else if ("MATERIAL".equalsIgnoreCase(localName)) {
            leaveItemBean.MATERIAL = trim(builder.toString());
        } else if ("MATERIALTEXT".equalsIgnoreCase(localName)) {
            leaveItemBean.MATERIALTEXT = trim(builder.toString());
        } else if ("MATERIALTEXT".equalsIgnoreCase(localName)) {
            leaveItemBean.MATERIALTEXT = trim(builder.toString());
        } else if ("OPEN_QUAN".equalsIgnoreCase(localName)) {
            leaveItemBean.OPEN_QUAN = trim(builder.toString());
        } else if ("ENTRY_QNT".equalsIgnoreCase(localName)) {
            leaveItemBean.ENTRY_QNT = trim(builder.toString());
        } else if ("UNIT".equalsIgnoreCase(localName)) {
            leaveItemBean.UNIT = trim(builder.toString());
        } else if ("BATCH".equalsIgnoreCase(localName)) {
            leaveItemBean.BATCH = trim(builder.toString());
        } else if ("PLANT".equalsIgnoreCase(localName)) {
            leaveItemBean.PLANT = trim(builder.toString());
        } else if ("WBS_ELEM".equalsIgnoreCase(localName)) {
            leaveItemBean.WBS_ELEM = trim(builder.toString());
        } else if ("STGE_LOC".equalsIgnoreCase(localName)) {
            leaveItemBean.STGE_LOC = trim(builder.toString());
        } else if ("MOVE_TYPE".equalsIgnoreCase(localName)) {
            leaveItemBean.MOVE_TYPE = trim(builder.toString());
        } else if ("item".equalsIgnoreCase(localName)) {
            leavebaseResponse.entity.leaveItemBeansList.add(leaveItemBean);
            leaveItemBean = null;
        } else if ("result".equalsIgnoreCase(localName)) {
            leavebaseResponse.result = trim(builder.toString());
        } else if ("message".equalsIgnoreCase(localName)) {
            leavebaseResponse.message = trim(builder.toString());
        }
        builder.setLength(0);
    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        builder = new StringBuilder();
        leavebaseResponse = new LeavebaseResponse();
    }

    @Override
    public void startElement(String uri, String localName, String name,
                             Attributes attributes) throws SAXException {
        super.startElement(uri, localName, name, attributes);
        if ("item".equalsIgnoreCase(localName)) {
            leaveItemBean = new LeaveItemBean();
        } else if ("recordInfo".equalsIgnoreCase(localName)) {
            leavebaseResponse.entity = new LeaveEntityContent();
            leavebaseResponse.entity.leaveHeadBean = new LeaveHeadBean();
            leavebaseResponse.entity.leaveItemBeansList = new ArrayList<LeaveItemBean>();

        }
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }

    private String trim(String strToTrim) {
        if (null == strToTrim) {
            return null;
        }
        return strToTrim.trim();
    }

    @Override
    public Object getParseContent() {
        return leavebaseResponse;
    }
}
