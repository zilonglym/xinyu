package com.xinyu.service.trade.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinyu.dao.trade.InvoiceInfoDao;
import com.xinyu.model.base.InvoiceInfo;
import com.xinyu.model.base.InvoiceItemDetail;
import com.xinyu.service.common.BaseServiceImpl;
import com.xinyu.service.trade.InvoiceInfoService;

@Service("invoiceInfoService")
public class InvoiceInfoServiceImpl extends BaseServiceImpl implements InvoiceInfoService {
	
	@Autowired
	private InvoiceInfoDao invoiceInfoDao;
	
	@Override
	public void insertInvoiceInfo(InvoiceInfo info) {
		// TODO Auto-generated method stub
		this.invoiceInfoDao.insertInvoiceInfo(info);
	}

	@Override
	public void updateInvoiceInfo(InvoiceInfo info) {
		// TODO Auto-generated method stub
		this.invoiceInfoDao.updateInvoiceInfo(info);
	}

	@Override
	public InvoiceInfo getInvoiceInfoById(String id) {
		// TODO Auto-generated method stub
		return this.invoiceInfoDao.getInvoiceInfoById(id);
	}

	@Override
	public void insertInvoiceDetail(InvoiceItemDetail detail) {
		// TODO Auto-generated method stub
		this.invoiceInfoDao.insertInvoiceDetail(detail);
	}

	@Override
	public void updateInvoiceDetail(InvoiceItemDetail detail) {
		// TODO Auto-generated method stub
		this.invoiceInfoDao.updateInvoiceDetail(detail);
	}

	@Override
	public InvoiceItemDetail getInvoiceDetailById(String id) {
		// TODO Auto-generated method stub
		return this.invoiceInfoDao.getInvoiceDetailById(id);
	}

	@Override
	public List<InvoiceItemDetail> getInvoiceDetailByList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.invoiceInfoDao.getInvoiceDetailByList(params);
	}

	@Override
	public void insertInvoiceDetailList(List<InvoiceItemDetail> detailList) {
		// TODO Auto-generated method stub
		if(detailList!=null){
			for(InvoiceItemDetail detail:detailList){
				this.insertInvoiceDetail(detail);
			}
		}
	}

}
