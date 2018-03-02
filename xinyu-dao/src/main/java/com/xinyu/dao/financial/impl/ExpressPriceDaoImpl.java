package com.xinyu.dao.financial.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xinyu.dao.common.BaseDaoImpl;
import com.xinyu.dao.financial.ExpressPriceDao;
import com.xinyu.model.financial.ExpressPrice;
import com.xinyu.model.financial.ExpressPriceMapping;

@Repository("expressPriceDaoImpl")
public class ExpressPriceDaoImpl extends BaseDaoImpl implements ExpressPriceDao{

	private final String statement = "com.xinyu.dao.financial.ExpressPriceDao.";
	
	@Override
	public List<ExpressPrice> findExpressPriceList(Map<String, Object> params) {
		return (List<ExpressPrice>) super.selectList(this.statement+"findExpressPriceList", params);
	}

	@Override
	public int getTotalResult(Map<String, Object> params) {
		return (Integer) super.selectOne(this.statement+"getTotalResult",params);
	}

	@Override
	public ExpressPrice findExpressPriceByParam(Map<String, Object> params) {
		return (ExpressPrice) super.selectOne(this.statement+"findExpressPriceByParam", params);
	}

	@Override
	public void save(ExpressPrice expressPrice) {
		super.insert(this.statement+"save", expressPrice);
	}

	@Override
	public void update(ExpressPrice expressPrice) {
		super.update(this.statement+"update", expressPrice);
	}

	@Override
	public void insert(ExpressPriceMapping priceMapping) {
		super.insert(this.statement+"insert", priceMapping);
	}

}
