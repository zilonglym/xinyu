package com.xinyu.service.financial;

import java.util.List;
import java.util.Map;

import com.xinyu.model.financial.ExpressPrice;
import com.xinyu.model.trade.ShipOrder;
import com.xinyu.model.trade.TmsOrder;
import com.xinyu.service.common.BaseService;

public interface ExpressPriceService extends BaseService{

	List<ExpressPrice> findExpressPriceList(Map<String, Object> params);

	int getTotalResult(Map<String, Object> params);

	ExpressPrice findExpressPriceByParam(Map<String, Object> params);

	void save(ExpressPrice expressPrice);

	void update(ExpressPrice expressPrice);
	
	/**
	 * 根据TmsOrder计算运费
	 * @param order
	 * @return code
	 */
	Map<String, Object> priceCalculate(TmsOrder order);

}
