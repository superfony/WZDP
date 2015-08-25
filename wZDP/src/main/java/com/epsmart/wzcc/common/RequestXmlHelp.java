package com.epsmart.wzcc.common;

public class RequestXmlHelp {

	public RequestXmlHelp() {

	}

	public static StringBuffer getReqXML(String key,String value) {
		StringBuffer reqXML = new StringBuffer();
		
		reqXML.append("<param>").append("<key>").append(key).append("</key>")
				.append("<value>").append(value).append("</value>").append("</param>");
		return reqXML;
	}

	public static String getHeadXML(StringBuffer requestXML) {
		return "<opdetail><head>"
				+ requestXML.toString() + "</head></opdetail>";
	}
	public static String getCommonXML(StringBuffer requestXML) {
		return "<opdetail>"
				+ requestXML.toString() + "</opdetail>";
	}
	public static String getHeadDetailXML(StringBuffer requestXML,StringBuffer detailXmL){

		return "<opdetail><head>"
				+ requestXML.toString()+"</head>"
				+"<items>"
				+detailXmL.toString()
				+ "</items></opdetail>";
	}
	public static StringBuffer getDetailXML(StringBuffer requestXML) {
		StringBuffer reqXML = new StringBuffer();

		return reqXML.append("<item>").append(requestXML).append("</item>");
	}
}
