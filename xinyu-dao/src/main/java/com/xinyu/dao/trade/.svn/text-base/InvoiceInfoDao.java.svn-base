package com.xinyu.dao.trade;

import java.util.List;
import java.util.Map;

import com.xinyu.dao.common.BaseDao;
import com.xinyu.model.base.InvoiceInfo;
import com.xinyu.model.base.InvoiceItemDetail;

public interface InvoiceInfoDao extends BaseDao {

	public void insertInvoiceInfo(InvoiceInfo info);
	
	public void updateInvoiceInfo(InvoiceInfo info);
	
	public InvoiceInfo getInvoiceInfoById(String id);
	
	public void insertInvoiceDetail(InvoiceItemDetail detail);
	
	public void updateInvoiceDetail(InvoiceItemDetail detail);
	
	public InvoiceItemDetail getInvoiceDetailById(String id);
	
	public List<InvoiceItemDetail>  getInvoiceDetailByList(Map<String,Object> params);

	public void deleteInvoiceInfo(String id);
}
