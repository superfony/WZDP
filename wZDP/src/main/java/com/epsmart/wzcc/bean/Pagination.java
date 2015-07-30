package com.epsmart.wzcc.bean;

import com.epsmart.wzcc.activity.pagination.PaginationWidget;
import com.epsmart.wzcc.http.response.model.Response;
import com.epsmart.wzcc.http.xml.parser.BaseXmlParser;

public class Pagination extends PaginationWidget<WorkOrder> {

	@Override
	public BaseXmlParser getXmlParser(Response response) {
		httpModule.setParseHandler(new WorkOrderHandler());
		return super.getXmlParser(response);
	}

}
