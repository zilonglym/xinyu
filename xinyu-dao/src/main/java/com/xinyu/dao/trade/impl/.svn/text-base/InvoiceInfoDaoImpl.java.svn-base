package com.xinyu.dao.trade.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xinyu.dao.common.BaseDaoImpl;
import com.xinyu.dao.trade.InvoiceInfoDao;
import com.xinyu.model.base.InvoiceInfo;
import com.xinyu.model.base.InvoiceItemDetail;

@Repository("invoiceInfoDao")
public class InvoiceInfoDaoImpl extends BaseDaoImpl implements InvoiceInfoDao {

	public final String statement="com.xinyu.dao.invoiceInfoDao.";
	public final String statementDetail="com.xinyu.dao.invoiceItemDetailDao.";
	
	@Override
	public void insertInvoiceInfo(InvoiceInfo info) {
		// TODO Auto-generated method stub
		this.insert(statement+"insertInvoiceInfo", info);
	}

	@Override
	public void updateInvoiceInfo(InvoiceInfo info) {
		// TODO Auto-generated method stub
		this.update(statement+"updateInvoiceInfo", info);
	}

	@Override
	public InvoiceInfo getInvoiceInfoById(String id) {
		return (InvoiceInfo) this.selectOne(statement+"getInvoiceInfoById", id);
	}

	@Override
	public void insertInvoiceDetail(InvoiceItemDetail detail) {
		this.insert(statementDetail+"insertInvoiceItemDetail", detail);
	}

	@Override
	public void updateInvoiceDetail(InvoiceItemDetail detail) {
		this.update(statementDetail+"updateInvoiceItemDetail", detail);
	}

	@Override
	public InvoiceItemDetail getInvoiceDetailById(String id) {
		return (InvoiceItemDetail) this.selectOne(statementDetail+"getInvoiceItemDetailById", id);
	}

	@Override
	public List<InvoiceItemDetail> getInvoiceDetailByList(Map<String, Object> params) {
		return (List<InvoiceItemDetail>) this.selectList(statementDetail+"getInvoiceItemDetailByList", params);
	}

	@Override
	public void deleteInvoiceInfo(String id) {
		// TODO Auto-generated method stub
		this.delete(this.statement+"deleteInvoiceInfo", id);
	}

}
