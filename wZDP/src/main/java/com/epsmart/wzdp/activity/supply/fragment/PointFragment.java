package com.epsmart.wzdp.activity.supply.fragment;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.epsmart.wzdp.R;
import com.epsmart.wzdp.activity.MainActivity;
import com.epsmart.wzdp.activity.RequestParamConfig;
import com.epsmart.wzdp.activity.fragment.SupplyFragmemt;
import com.epsmart.wzdp.activity.pagination.PaginationWidget;
import com.epsmart.wzdp.activity.search.QueryDialogListener;
import com.epsmart.wzdp.activity.supply.bean.Field;
import com.epsmart.wzdp.bean.Pagination;
import com.epsmart.wzdp.bean.QueryCondition;
import com.epsmart.wzdp.bean.ViewBuildPon;
import com.epsmart.wzdp.bean.WorkOrder;
import com.epsmart.wzdp.common.Common;
import com.epsmart.wzdp.common.PermissHelp;
import com.epsmart.wzdp.common.RequestXmlHelp;
import com.epsmart.wzdp.http.request.BaseRequest.RequestType;


/*
 * 关键点见证Fragment
 */
public class PointFragment extends SupplyFragmemt {
	//private Activity activity;
	private View view;
	// 初始化分页标签
	private ViewBuildPon viewBuild;

	private String TAG = PointFragment.class.getName();

	@Override
	public void onAttach(Activity activity) {
		//this.activity = activity;
		queryCondition = new QueryCondition();
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.pull_fragment_item, container, false);
		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	/**
	 * 初始化分页控件
	 * 
	 * @param savedInstanceState
	 */
	public void initPage(Bundle savedInstanceState) {
		viewBuild = new ViewBuildPon();
		if (null == paginationWidget)
			paginationWidget = new Pagination();
		paginationWidget.setFootView(activity.getLayoutInflater().inflate(
				R.layout.listview_footer, null));
		paginationWidget.init(activity,
				activity.findViewById(R.id.contentRoot), viewBuild);
		paginationWidget.getHttpModule().setServiceNameSpace(
				RequestParamConfig.serviceNameSpace);
		paginationWidget.getHttpModule().setServiceUrl(
				RequestParamConfig.ServerUrl);
		paginationWidget.setRequestType(RequestType.THRIFT);// TODO
		initPaginationWidget(paginationWidget);
		paginationWidget.loadPaginationData();

	}

	/** 查询条件 */
	protected QueryCondition queryCondition;

	protected void initPaginationWidget(
			final PaginationWidget<WorkOrder> paginationWidget) {
		processQueryCondition(queryCondition);
		paginationWidget.setPageSize(5);
		paginationWidget
				.setPageBodyOnItemClickListener(paginationWidget.new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						if(!PermissHelp.isPermiss("008")){
							PermissHelp.showToast(activity);
							return;
						}
						if (position == 0
								|| paginationWidget.getPageBodyDatas().size()+1 == position) {
							return;
						}
						WorkOrder workOrder = paginationWidget
								.getPageBodyDatas().get(position-1);
						Field Id=workOrder.fields.get("id");
						WitnessFragment	witeness=new WitnessFragment();
						Bundle bundle=new Bundle();
						bundle.putString("reqParam", RequestXmlHelp.getCommonXML(RequestXmlHelp.getReqXML("id", Id.fieldContent)));
						bundle.putString("reqP", RequestXmlHelp.getReqXML("id", Id.fieldContent).toString());
						
						witeness.setArguments(bundle);
						activity.setSmGone();
						Common.replaceRightFragment(activity, witeness,
								false, R.id.content);
					}
				});
		paginationWidget.setServiceName(RequestParamConfig.keyPotReq);
	}

	/**
	 * 设置查询条件
	 * 
	 * @param condition
	 */
	public void processQueryCondition(QueryCondition condition) {

		requestPram.bizId=1004;
		requestPram.methodName=RequestParamConfig.keyPotReq;
		requestPram.password="password";
		requestPram.pluginId=119;
		requestPram.userName=appContext.user.getUid();
      	paginationWidget.requestAction.setReqPram(requestPram);
	}
	{
		listener = new QueryDialogListener() {
			@Override
			public void doQuery(String req) {
				requestPram.param = req;
				paginationWidget.getRequestAction().reset();
				processQueryCondition(null);
				paginationWidget.loadPaginationData();
			}
		};
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		initPage(savedInstanceState);
		activity.setSmShow();
		appContext.wzdpType = WZDPTYPE.POINT;
		search_lay.setVisibility(View.VISIBLE);
		search_lay.setOnClickListener(searchLis);
		search_btn.setOnClickListener(searchLis);
	}
	

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		add_lay = (RelativeLayout) getActivity().getActionBar().getCustomView()
				.findViewById(R.id.add_lay);
		add_btn = (Button) getActivity().getActionBar().getCustomView()
				.findViewById(R.id.add_btn);
		if (PermissHelp.isAdd("028")) {
			add_lay.setVisibility(View.VISIBLE);
			add_lay.setOnClickListener(addOnLister);
			add_btn.setOnClickListener(addOnLister);
		}
		ActionBar actionBar = activity.getActionBar();
		View view = actionBar.getCustomView();
		ImageView title_image = (ImageView) view.findViewById(R.id.title_image);
		title_image.setBackgroundResource(R.drawable.point_cs);
		
	}
	OnClickListener addOnLister = new OnClickListener() {
		@Override
		public void onClick(View v) {
			activity.setSmGone();
			Common.replaceRightFragment(activity, new NewWitnessFG(), false,
					R.id.content);
		}
	};
	@Override
	public void onPause() {
		search_lay.setVisibility(View.INVISIBLE);
		if (add_lay != null)
			add_lay.setVisibility(View.GONE);
		super.onPause();
	}

}