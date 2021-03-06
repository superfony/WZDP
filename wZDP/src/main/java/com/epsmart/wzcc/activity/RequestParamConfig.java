package com.epsmart.wzcc.activity;

/**
 * webservice 接口定义
 * http://211.137.182.251:9080/eoms3/services/PhoneInterFaceClass?WSDL
 * @author fony
 */
public class RequestParamConfig {
	public final  static boolean isDubeg = false;
	public static final int FTP_PORT = 21;
	public static final int pagesize = 5;

	public final static String IP = "127.0.0.1";
	public final static int PORT = 28080;// 暂时用不到
	/**************** 正式库 **************************/
//	 public final static  String ServerUrl =
//	 "http://127.0.0.1:28080/wzdp/services/cmisService";
//	 public final static String loginUrl =
//	 "http://127.0.0.1:28099/lnptgl/webservices/IMobilePlatformsWebservices";
	/************* 本地测试地址 apn方式访问 ***********************/
	// 148cmis 服务器
//	 public final static  String ServerUrl =
//	 "http://127.0.0.1:8551/wzdp/services/cmisService";
	// 153 cmis服务器
	 public static String ServerUrl =
	 "http://127.0.0.1:8553/lnptgl/webservices/IMobilePlatformsWebservices";

	 /************* 本地测试地址 电脑模拟器访问 ***********************/
	// 148cmis 服务器
//	 public static String ServerUrl =
//	 "http://10.192.29.148:8080/wzdp/services/cmisService";
	// 153 cmis服务器
//	public static String ServerUrl = "http://10.192.29.153:8080/wzdp/services/cmisService";
	// 153 中间件服务器
//	public static String loginUrl = "http://10.192.29.153:8899/lnptgl/webservices/IMobilePlatformsWebservices";
	/************************ end ******************************/

	// 登录接口名称
	public static final String loginname = "validateTerminalUser";
	// 命名空间
	public static final String serviceNameSpace = "http://webservices.lnptgl.aowei.com";
	// 接口名称
	public static final String selectNowWork = "selectNowWOrk";
	// 权限接口
	public static final String userLogin = "userLogin";
	// 供应商生成过程监控
	public static final String procedureReq = "procedureReq";
	// 关键点见证基础信息字段请求接口
	public static final String keyPotReq = "keyPotReq";
	// 项目现场管理基础信息(供应进度维护基础信息接收接口)
	public static final String projectlistReq = "projectlistReq";
	// 工作日志基础信息接口
	public static final String dairylistReq = "dairylistReq";
	// 生产过程信息字段请求接口
	public static final String procedureDownload = "procedureDownload";
	// 生产过程信息上传
	public static final String procedureUpload = "procedureUpload";
	// 质量问题清单列表请求接口
	public static final String issuelistDownload = "issuelistDownload";
	// 质量问题信息字段请求接口/质量问题处理信息字段获取接口/问题处理结果信息字段请求接口/质量问题信息查询
	public static final String qualityIssueDownload = "qualityIssueDownload";// qualityIssueDownload
	// 质量问题信息上传/质量问题处理结果上传/质量问题处理结果上传接口
	public static final String qualityIssueUpload = "qualityIssueUpload";
	// 关键点见证信息字段请求接口/新增关键点
	public static final String keyPotQueryDownload = "keyPotQueryDownload";
	// 关键点见证基础信息上传
	public static final String keyPotQueryUpload = "keyPotQueryUpload";
	// 项目施工节点信息请求接口
	public static final String projectQueryReq = "projectQueryReq";
	// 项目施工节点信息上传接口
	public static final String projectNodeUpload = "projectNodeUpload";
	// 供应进度维护二级列表
	public static final String materialslistReq = "materialslistReq";// materialslistReq
	// 供应进度维护信息请求接口
	public static final String materialsReceiveReq = "materialsReceiveReq";
	// 供应进度维护上传接口
	public static final String projectReceiveUpload = "projectReceiveUpload";
	// 工作日志维护字段请求接口
	public static final String dairyReceiveReq = "dairyReceiveReq";
	// 工作日志维护字段提交接口
	public static final String dairyReceiveUpload = "dairyReceiveUpload";
	// 现场服务的第一层列表
	public static final String materialsListReq2 = "materialslistReq2";
	// 现场服务申请单列表信息接口
	public static final String servicelistDownload = "servicelistDownload";
	// 现场服务问题反馈单上传
	public static final String newserviceslistupload = "newservieslistupload";// newserviceslistupload
	// 现场服务提问题单信息字段接口
	public static final String newServicelistDownload = "newServicelistDownload";
	// 现场服务联系单信息字段请求接口/现场服务联系单反馈信息字段请求接口/现场服务问题单信息字段接口/现场服务评价信息字段接口
	public static final String serviceDownload = "serviceDownload";
	// 现场服务联系单上传接口/现场服务联系单反馈上传接口/现场服务问题反馈单上传/现场服务完成确认标识上传/现场服务评价单上传
	public static final String serviceRequireUpload = "serviceRequireUpload";
	// 合同结算信息分析数接收接口
	public static final String contructAnlysis = "contructAnlysis";
	// 合同签订情况分析数接收接口
	public static final String contractsign = "contractsign";
	// 物资供应综合分析数接收接口
	public static final String materialsupply = "materialsupply";
	//
	public static final String projectmilestone = "projectmilestone";
	// 项目施工节点信息二级列表请求接口
	public static final String linelistDownload = "linelistDownload";
	// 修改密码
	public static final String userUpload = "userUpload";
	// 特高压项目列表接口
	public static final String ehvProjectList = "ehvProjectList";
	// 铁塔排产计划基础信息请求接口
	public static final String ehvPlanDownload = "ehvPlanDownload";
	// 铁塔排产计划模、铁塔生产进度周报模板下载请求接口
	public static final String ehvTempDownload = "ehvTempDownload";
	// 塔排产计划信息附件上传接口
	public static final String ehvPlantempUpload = "ehvPlantempUpload";
	// 铁塔生产进度周报信息请求接口
	public static final String ehvReportDownload = "ehvReportDownload";
	// 铁塔生产进度周报信息附件上传接口
	public static final String ehvReporttempUpload = "ehvReporttempUpload";
	// 排产计划和生产进度周报筛选条件请求接口
	public static final String ehvplanrep = "ehvplanrep";
	// 铁塔排产计划统计分析数据接收接口
	public static final String ehvPlan = "ehvPlan";
	// 铁塔生产进度周报信息统计数据接收接口
	public static final String ehvReportReq = "ehvReportReq";
	// 在线交流--查询主题列表
	public static final String getSubList = "getSubList";
	// 在线交流--查询主题回复列表
	public static final String getReplayList = "getReplayList";
	// 回复主题接口
	public static final String submitReplay = "submitReplay";
	// 发布主题服务接口
	public static final String buildSub = "buildSub";
	// 查询人员列表
	public static final String getPersionList = "getPersionList";
	// 头像上传接口
	public static final String getHeadImage = "getHeadImage";
	// 获取用户头像接口
	public static final String getHeadImg = "getHeadImg";
	//删除主题 
	public static final String delSubject = "delSubject";
	// 首页查看通知公告未读消息条数
	public static final String getNoRedNum = "getNoRedNum";
	// 通知公告消息列表	
	public static final String getNoticeList = "getNoticeList";
	// 通知公告消息详情	
	public static final String getNoticeDetail = "getNoticeDetail";
	// 位置定位	
	public static final String LocationUpdate = "locationUpdate";


	/******************************************移动收发货接口************************************************/
     // 交接、验收列表数据
	public static final String  receiveList= "receiveList";
	// 根据发货通知标号查询详情信息
	public static final String  getHouseDetail= "getHouseDetail";
	//交接、验收、数据提交接口
	public static final String  pullCommit= "pullCommit";
	//出库预留列表接口
	public static final String  warehouseList= "warehouseList";
	//根据订单号查询出库详情信息
    public  static final String  getWarehouseDetail="getWarehouseDetail";
	// 出库预留信息提交接口
	public  static final String pullwarehourseSubmit="pullwarehourseSubmit";
	//查询物料库存明细信息接口
	 public  static final  String houseQueryWithTable="houseQueryWithTable";

	// 查询工厂列表
	public  static final  String warehouseInfo="warehouseInfo";

	public  static final  String testWSDLOBJ="testObject";
}
