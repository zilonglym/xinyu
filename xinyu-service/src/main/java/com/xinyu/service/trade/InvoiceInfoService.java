package com.xinyu.service.trade;

import java.util.List;
import java.util.Map;

import com.xinyu.model.base.InvoiceInfo;
import com.xinyu.model.base.InvoiceItemDetail;
import com.xinyu.service.common.BaseService;

public interface InvoiceInfoService extends BaseService {

public void insertInvoiceInfo(InvoiceInfo info);
	
	public void updateInvoiceInfo(InvoiceInfo info);
	
	public InvoiceInfo getInvoiceInfoById(String id);
	
	public void insertInvoiceDetail(InvoiceItemDetail detail);
	
	public void insertInvoiceDetailList(List<InvoiceItemDetail> detailList);
	
	public void updateInvoiceDetail(InvoiceItemDetail detail);
	
	public InvoiceItemDetail getInvoiceDetailById(String id);
	
	public List<InvoiceItemDetail>  getInvoiceDetailByList(Map<String,Object> params);
}
