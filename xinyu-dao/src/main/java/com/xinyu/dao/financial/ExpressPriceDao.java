package com.xinyu.dao.financial;

import java.util.List;
import java.util.Map;

import com.xinyu.model.financial.ExpressPrice;
import com.xinyu.model.financial.ExpressPriceMapping;

public interface ExpressPriceDao{

	List<ExpressPrice> findExpressPriceList(Map<String, Object> params);

	int getTotalResult(Map<String, Object> params);

	ExpressPrice findExpressPriceByParam(Map<String, Object> params);

	void save(ExpressPrice expressPrice);

	void update(ExpressPrice expressPrice);

	void insert(ExpressPriceMapping priceMapping);

}
