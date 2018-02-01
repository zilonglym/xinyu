package com.graby.store.admin.finance;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.graby.store.entity.MonthProfits;
import com.graby.store.remote.ReportRemote;

/**
 * 公司月利润明细
 * */
@Controller
@RequestMapping(value="monthProfits")
public class MonthProfitsController {
	
	@Autowired
	private ReportRemote monthProfitsRemote;
	
	/**
	 * 公司月利润明细列表
	 * @param model
	 * @return String
	 * */
	@RequestMapping(value="list")
	public String MonthProfitsList(ModelMap model){
		return "finance/monthProfitsList";
	}
	
	/**
	 * 公司月利润明细列表数据填充
	 * @param page
	 * @param rows
	 * @param request
	 * @return map
	 * */
	@RequestMapping(value="listData")
	@ResponseBody
	public Map<String,Object> MonthProfitsListData(@RequestParam(defaultValue="1")int page,@RequestParam(defaultValue="100")int rows,HttpServletRequest request){
		if (rows==10) {
			rows=100;
		}
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String,Object> params=new HashMap<String,Object>();
		String beigainTime=request.getParameter("beigainTime");
		String lastTime=request.getParameter("lastTime");
		params.put("beigainTime", beigainTime);
		params.put("lastTime", lastTime);
		int total=this.monthProfitsRemote.getMonthProfitsTotalResult(params);
		List<MonthProfits> monthProfitsList=this.monthProfitsRemote.findMonthProfits(params, page, rows);
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("total",total);
		resultMap.put("page",page);
		resultMap.put("rows",MonthProfitsListData(monthProfitsList));
		return resultMap;
	}
	
	/**
	 * 公司月利润明细添加
	 * @param model
	 * @return String
	 * */
	@RequestMapping(value="f7Add")
	public String MonthProfitsAdd(ModelMap model){
		return "finance/monthProfitsAdd";
	}
	
	/**
	 * 公司月利润明细编辑
	 * @param model
	 * @param request
	 * @return String
	 * */
	@RequestMapping(value="f7Edit")
	public String MonthProfitsEdit(ModelMap model,HttpServletRequest request){
		String id=request.getParameter("id");
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("id", id);
		MonthProfits monthProfits=this.monthProfitsRemote.findMonthProfitsByParams(params);
		model.put("monthProfits", monthProfits);
		return "finance/monthProfitsEdit";
	}
	
	/**
	 * 公司月利润明细保存
	 * @param request
	 * @return resultMap
	 * @exception JSONException
	 * @exception ParseException
	 * */
	@RequestMapping(value="save")
	@ResponseBody
	public Map<String, Object> MonthProfitsSave(HttpServletRequest request) throws JSONException, ParseException{
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String, Object> resultMap=new HashMap<String, Object>();
		String json=request.getParameter("json");
		JSONObject object=new JSONObject(json);
		String id=object.getString("id");
		String dateTime=object.getString("dateTime");
		String mainIncome=object.getString("mainIncome");
		String mainCost=object.getString("mainCost");
		String deliveryFee=object.getString("deliveryFee");
		String basketFee=object.getString("basketFee");
		String packingFee=object.getString("packingFee");
		String materielFee=object.getString("materielFee");
		String damagedFee=object.getString("damagedFee");
		String financeFee=object.getString("financeFee");
		String propertyFee=object.getString("propertyFee");
		String waterFee=object.getString("waterFee");
		String officeFee=object.getString("officeFee");
		String boardFee=object.getString("boardFee");
		String vehicleFee=object.getString("vehicleFee");
		String assetsFee=object.getString("assetsFee");
		String hospitalityFee=object.getString("hospitalityFee");
		String taxFee=object.getString("taxFee");
		String insuranceFee=object.getString("insuranceFee");
		String wagesFee=object.getString("wagesFee");
		if (id.isEmpty()) {
			MonthProfits monthProfits=new MonthProfits();
			monthProfits.setAssetsFee(Double.valueOf(assetsFee));
			monthProfits.setBasketFee(Double.valueOf(basketFee));
			monthProfits.setBoardFee(Double.valueOf(boardFee));
			monthProfits.setDamagedFee(Double.valueOf(damagedFee));
			monthProfits.setDateTime(sf.parse(dateTime));
			monthProfits.setDeliveryFee(Double.valueOf(deliveryFee));
			monthProfits.setFinanceFee(Double.valueOf(financeFee));
			monthProfits.setHospitalityFee(Double.valueOf(hospitalityFee));
			monthProfits.setInsuranceFee(Double.valueOf(insuranceFee));
			monthProfits.setMainCost(Double.valueOf(mainCost));
			monthProfits.setMainIncome(Double.valueOf(mainIncome));
			monthProfits.setMaterielFee(Double.valueOf(materielFee));
			monthProfits.setOfficeFee(Double.valueOf(officeFee));
			monthProfits.setPackingFee(Double.valueOf(packingFee));
			monthProfits.setPropertyFee(Double.valueOf(propertyFee));
			monthProfits.setTaxFee(Double.valueOf(taxFee));
			monthProfits.setVehicleFee(Double.valueOf(vehicleFee));
			monthProfits.setWagesFee(Double.valueOf(wagesFee));
			monthProfits.setWaterFee(Double.valueOf(waterFee));
			this.monthProfitsRemote.save(monthProfits);
			resultMap.put("ret","insert");
		}else {
			Map<String,Object> params=new HashMap<String, Object>();
			params.put("id",id);
			MonthProfits monthProfits=this.monthProfitsRemote.findMonthProfitsByParams(params);
			monthProfits.setAssetsFee(Double.valueOf(assetsFee));
			monthProfits.setBasketFee(Double.valueOf(basketFee));
			monthProfits.setBoardFee(Double.valueOf(boardFee));
			monthProfits.setDamagedFee(Double.valueOf(damagedFee));
			monthProfits.setDateTime(sf.parse(dateTime));
			monthProfits.setDeliveryFee(Double.valueOf(deliveryFee));
			monthProfits.setFinanceFee(Double.valueOf(financeFee));
			monthProfits.setHospitalityFee(Double.valueOf(hospitalityFee));
			monthProfits.setInsuranceFee(Double.valueOf(insuranceFee));
			monthProfits.setMainCost(Double.valueOf(mainCost));
			monthProfits.setMainIncome(Double.valueOf(mainIncome));
			monthProfits.setMaterielFee(Double.valueOf(materielFee));
			monthProfits.setOfficeFee(Double.valueOf(officeFee));
			monthProfits.setPackingFee(Double.valueOf(packingFee));
			monthProfits.setPropertyFee(Double.valueOf(propertyFee));
			monthProfits.setTaxFee(Double.valueOf(taxFee));
			monthProfits.setVehicleFee(Double.valueOf(vehicleFee));
			monthProfits.setWagesFee(Double.valueOf(wagesFee));
			monthProfits.setWaterFee(Double.valueOf(waterFee));
			this.monthProfitsRemote.update(monthProfits);
			resultMap.put("ret","update");
		}
		return resultMap;
	}
	
	/**
	 * 数据重组
	 * @param list
	 * @return list
	 * */
	private List<Map<String, Object>> MonthProfitsListData(List<MonthProfits> monthProfitsList){
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<Map<String,Object>> resultList=new ArrayList<Map<String,Object>>();
		for(MonthProfits monthProfits:monthProfitsList){
			 Map<String,Object> map=new HashMap<String, Object>();
			 Double mainProfits=monthProfits.getMainIncome()-monthProfits.getMainCost();
			 map.put("mainProfits", mainProfits);
			 Double otherIncome=monthProfits.getDeliveryFee()+monthProfits.getBasketFee();
			 map.put("otherIncome", otherIncome);
			 Double otherCost=monthProfits.getMaterielFee()+monthProfits.getDamagedFee();
			 map.put("otherCost", otherCost);
			 Double totalProfits=mainProfits+otherIncome-otherCost+monthProfits.getPackingFee();
			 map.put("totalProfits", totalProfits);
			 Double manageFee=monthProfits.getPropertyFee()+monthProfits.getWaterFee()+monthProfits.getOfficeFee()+monthProfits.getBoardFee()+monthProfits.getVehicleFee()+monthProfits.getAssetsFee()+monthProfits.getHospitalityFee()+monthProfits.getTaxFee()+monthProfits.getInsuranceFee()+monthProfits.getWagesFee();
			 map.put("manageFee", manageFee);
			 Double profits=totalProfits-monthProfits.getFinanceFee()-manageFee;
			 map.put("profits", profits);
			 map.put("dateTime",sf.format(monthProfits.getDateTime()));
			 map.put("mainIncome", monthProfits.getMainIncome());
			 map.put("mainCost", monthProfits.getMainCost());
			 map.put("deliveryFee", monthProfits.getDeliveryFee());
			 map.put("basketFee", monthProfits.getBasketFee());
			 map.put("packingFee", monthProfits.getPackingFee());
			 map.put("materielFee", monthProfits.getMaterielFee());
			 map.put("damagedFee", monthProfits.getDamagedFee());
			 map.put("financeFee", monthProfits.getFinanceFee());
			 map.put("propertyFee", monthProfits.getPropertyFee());
			 map.put("waterFee", monthProfits.getWaterFee());
			 map.put("officeFee", monthProfits.getOfficeFee());
			 map.put("boardFee", monthProfits.getBoardFee());
			 map.put("vehicleFee", monthProfits.getVehicleFee());
			 map.put("assetsFee", monthProfits.getAssetsFee());
			 map.put("hospitalityFee", monthProfits.getHospitalityFee());
			 map.put("taxFee", monthProfits.getTaxFee());
			 map.put("insuranceFee", monthProfits.getInsuranceFee());
			 map.put("wagesFee", monthProfits.getWagesFee());
			 map.put("id", monthProfits.getId());
			 resultList.add(map);
		}
		return resultList;
	}
}
