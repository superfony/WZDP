package com.epsmart.wzcc.activity.supply.approval;

import com.epsmart.wzcc.activity.supply.approval.parcelable.ApprovalResponse;
import com.epsmart.wzcc.activity.supply.approval.parcelable.EntityContent;
import com.epsmart.wzcc.activity.supply.approval.parcelable.HeadBean;
import com.epsmart.wzcc.activity.supply.approval.parcelable.ItemBean;
import com.epsmart.wzcc.activity.supply.bean.BasicEntity;
import com.epsmart.wzcc.activity.supply.bean.BasicResponse;
import com.epsmart.wzcc.activity.supply.bean.DataRow;
import com.epsmart.wzcc.activity.supply.bean.Field;
import com.epsmart.wzcc.http.xml.handler.BaseParserHandler;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 交接验收明细解析类
 */
public class ApprovalDetailHandler extends BaseParserHandler {
    private StringBuilder builder;
    private ApprovalResponse approvalResponse;
    private ItemBean itemBean;


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

        if ("DELIVERINFORMCOD".equalsIgnoreCase(localName)) {
            approvalResponse.entity.headBean.DELIVERINFORMCOD = trim(builder.toString());
        } else if ("PURCHASECODE".equalsIgnoreCase(localName)) {
            approvalResponse.entity.headBean.PURCHASECODE = trim(builder.toString());
        } else if ("PLANT".equalsIgnoreCase(localName)) {
            approvalResponse.entity.headBean.PLANT = trim(builder.toString());
        } else if ("STGE_LOC".equalsIgnoreCase(localName)) {
            approvalResponse.entity.headBean.STGE_LOC = trim(builder.toString());
        } else if ("WHSENUMBER".equalsIgnoreCase(localName)) {
            approvalResponse.entity.headBean.WHSENUMBER = trim(builder.toString());
        } else if ("SUPPLIERNAME".equalsIgnoreCase(localName)) {
            approvalResponse.entity.headBean.SUPPLIERNAME = trim(builder.toString());
        } else if ("ACTUALDELIVERYPL".equalsIgnoreCase(localName)) {
            approvalResponse.entity.headBean.ACTUALDELIVERYPL = trim(builder.toString());
        } else if ("PROJECTNAME".equalsIgnoreCase(localName)) {
            approvalResponse.entity.headBean.PROJECTNAME = trim(builder.toString());
        } else if ("SUPPLINKMAN".equalsIgnoreCase(localName)) {
            approvalResponse.entity.headBean.SUPPLINKMAN = trim(builder.toString());
        } else if ("SUPPLINKMANTELEP".equalsIgnoreCase(localName)) {
            approvalResponse.entity.headBean.SUPPLINKMANTELEP = trim(builder.toString());
        } else if ("CONTRACTID".equalsIgnoreCase(localName)) {
            approvalResponse.entity.headBean.CONTRACTID = trim(builder.toString());
        } else if ("CARRLINKMAN".equalsIgnoreCase(localName)) {
            approvalResponse.entity.headBean.CARRLINKMAN = trim(builder.toString());
        } else if ("CARRLINKMANTELEP".equalsIgnoreCase(localName)) {
            approvalResponse.entity.headBean.CARRLINKMANTELEP = trim(builder.toString());
        } else if ("DELIVERINFORMLINE".equalsIgnoreCase(localName)) {
            itemBean.DELIVERINFORMLINE = trim(builder.toString());
        } else if ("DELIVERINFORMCODITEM".equalsIgnoreCase(localName)) {
            itemBean.DELIVERINFORMCOD = trim(builder.toString());
        } else if ("SUPPLYPLANCODE".equalsIgnoreCase(localName)) {
            itemBean.SUPPLYPLANCODE = trim(builder.toString());
        } else if ("PURCHASELINECODE".equalsIgnoreCase(localName)) {
            itemBean.PURCHASELINECODE = trim(builder.toString());
        } else if ("LOCALMATERIALID".equalsIgnoreCase(localName)) {
            itemBean.LOCALMATERIALID = trim(builder.toString());
        } else if ("MATERIALTEXT".equalsIgnoreCase(localName)) {
            itemBean.MATERIALTEXT = trim(builder.toString());
        } else if ("MATERIALUNIT".equalsIgnoreCase(localName)) {
            itemBean.MATERIALUNIT = trim(builder.toString());
        } else if ("DELIVERYAMOUNT".equalsIgnoreCase(localName)) {
            itemBean.DELIVERYAMOUNT = trim(builder.toString());
        } else if ("PLANDELIVERYAMOU".equalsIgnoreCase(localName)) {
            itemBean.PLANDELIVERYAMOU = trim(builder.toString());
        } else if ("HANDOVERAMOUNT".equalsIgnoreCase(localName)) {
            itemBean.HANDOVERAMOUNT = trim(builder.toString());
        } else if ("RETURNAMOUNT".equalsIgnoreCase(localName)) {
            itemBean.RETURNAMOUNT = trim(builder.toString());
        } else if ("EXPECTEDARRIVALD".equalsIgnoreCase(localName)) {
            itemBean.EXPECTEDARRIVALD = trim(builder.toString());
        } else if ("ACTUALDELIVERDAT".equalsIgnoreCase(localName)) {
            itemBean.ACTUALDELIVERDAT = trim(builder.toString());
        } else if ("ACCEAMOUNT".equalsIgnoreCase(localName)) {
            itemBean.ACCEAMOUNT = trim(builder.toString());
        } else if ("item".equalsIgnoreCase(localName)) {
            approvalResponse.entity.itemBeansList.add(itemBean);
            itemBean = null;
        } else if ("result".equalsIgnoreCase(localName)) {
            approvalResponse.result = trim(builder.toString());
        } else if ("message".equalsIgnoreCase(localName)) {
            approvalResponse.message = trim(builder.toString());
        }
        builder.setLength(0);
    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        builder = new StringBuilder();
        approvalResponse = new ApprovalResponse();
    }

    @Override
    public void startElement(String uri, String localName, String name,
                             Attributes attributes) throws SAXException {
        super.startElement(uri, localName, name, attributes);
        if ("item".equalsIgnoreCase(localName)) {
            itemBean = new ItemBean();
        } else if ("recordInfo".equalsIgnoreCase(localName)) {
            approvalResponse.entity = new EntityContent();
            approvalResponse.entity.headBean = new HeadBean();
            approvalResponse.entity.itemBeansList = new ArrayList<ItemBean>();

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
        return approvalResponse;
    }
}
