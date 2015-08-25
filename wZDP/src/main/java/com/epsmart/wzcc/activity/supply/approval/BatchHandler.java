package com.epsmart.wzcc.activity.supply.approval;

import com.epsmart.wzcc.activity.supply.approval.parcelable.BatchBean;
import com.epsmart.wzcc.activity.supply.approval.parcelable.BatchResponse;
import com.epsmart.wzcc.http.xml.handler.BaseParserHandler;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import java.util.ArrayList;

/**
 * 仓库的
 */
public class BatchHandler extends BaseParserHandler {
    private StringBuilder builder;
    private BatchResponse batchResponse;
    private BatchBean itemBean;

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

        if ("ID".equalsIgnoreCase(localName)) {
            itemBean.ID = trim(builder.toString());
        } else if ("PLANT".equalsIgnoreCase(localName)) {
            itemBean.PLANT = trim(builder.toString());
        } else if ("PLANTNAME".equalsIgnoreCase(localName)) {
            itemBean.PLANTNAME = trim(builder.toString());
        } else if ("PLANTADDR".equalsIgnoreCase(localName)) {
            itemBean.PLANTADDR = trim(builder.toString());
        } else if ("STGE_LOC".equalsIgnoreCase(localName)) {
            itemBean.STGE_LOC = trim(builder.toString());
        } else if ("STGE_LOCNAME".equalsIgnoreCase(localName)) {
            itemBean.STGE_LOCNAME = trim(builder.toString());
        } else if ("STGE_LOCADDR".equalsIgnoreCase(localName)) {
            itemBean.STGE_LOCADDR = trim(builder.toString());
        } else if ("WHSENUMBER".equalsIgnoreCase(localName)) {
            itemBean.WHSENUMBER = trim(builder.toString());
        } else if ("WHSENAME".equalsIgnoreCase(localName)) {
            itemBean.WHSENAME = trim(builder.toString());
        } else if ("COM_CODE".equalsIgnoreCase(localName)) {
            itemBean.COM_CODE = trim(builder.toString());
        } else if ("UPDATE_TIME".equalsIgnoreCase(localName)) {
            itemBean.UPDATE_TIME = trim(builder.toString());
        } else if ("item".equalsIgnoreCase(localName)) {
            batchResponse.itemBeansList.add(itemBean);
            itemBean = null;
        } else if ("result".equalsIgnoreCase(localName)) {
            batchResponse.result = trim(builder.toString());
        } else if ("message".equalsIgnoreCase(localName)) {
            batchResponse.message = trim(builder.toString());
        }
        builder.setLength(0);
    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        builder = new StringBuilder();
        batchResponse = new BatchResponse();
    }

    @Override
    public void startElement(String uri, String localName, String name,
                             Attributes attributes) throws SAXException {
        super.startElement(uri, localName, name, attributes);
        if ("item".equalsIgnoreCase(localName)) {
            itemBean = new BatchBean();
        } else if ("opdetail".equalsIgnoreCase(localName)) {
            batchResponse.itemBeansList = new ArrayList<BatchBean>();
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
        return batchResponse;
    }
}
