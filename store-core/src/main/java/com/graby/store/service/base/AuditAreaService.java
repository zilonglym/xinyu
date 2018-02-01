package com.graby.store.service.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.graby.store.dao.mybatis.AuditAreaDao;
import com.graby.store.dao.mybatis.TradeDao;
import com.graby.store.entity.AuditArea;
import com.graby.store.entity.Trade;
import com.graby.store.util.StringUtils;

@Component
@Transactional
public class AuditAreaService {
	
	private static Logger logger = LoggerFactory.getLogger(AuditAreaService.class);
	
	@Autowired
	private AuditAreaDao auditAreaDao;
	
	
	@Autowired
	private TradeDao tradeDao ;
	
	private  static  List<AuditArea> stateList ; 
	
	
	private  static  List<AuditArea> cityList ; 
	
	public  void  inIt(){
		
		//全境到的省份
		Map<String,Object>  params  = new HashMap<String,Object>();
		params.put("end", true);
		params.put("level", 1);
		stateList  = auditAreaDao.findAuditArea(params);
		
		
		//全境到的市
		params.clear();
		params.put("end", true);
		params.put("level", 2);
		
		cityList= auditAreaDao.findAuditArea(params);
		
	}
	
	public void save(AuditArea auditArea){
		auditAreaDao.save(auditArea);
		
	}
	
	/**
	 * 检测是否在顺风
	 * @param tradeId
	 * @return
	 */
	public Map<String,Object>  checkArea(long tradeId){
		System.out.println("checkArea---开始");
		Trade trade = tradeDao.getTrade(tradeId);
		//返回显示
		Map<String,Object>  retMap  =  new HashMap<String,Object>();
		
		if(stateList==null){
			inIt();
		}
		//匹配省份信息
		String state = trade.getReceiverState();
		if (state.length() == 0) {
			retMap.put("flag", false);
			retMap.put("msg", "该记录没有省份信息");
			return retMap;
		}
		for(AuditArea area:  stateList){ //先判断 每个省  境内 是否 到
			String name  = area.getName();
			if(state.indexOf(name)!=-1){
				String msg  = area.getMsg();
				if("未开通".equals(msg)){
					retMap.put("flag", false);
				}else{
					retMap.put("flag", true);
				}
				retMap.put("msg",name+"【"+msg+"】");
				return retMap;
			}
		}
		
		String city = trade.getReceiverCity();
		if (city.length() == 0) {
			retMap.put("flag", false);
			retMap.put("msg", "该记录没有城市信息");
			return retMap;
		}
		for(AuditArea area:  cityList){ //市区是达到
			String name  = area.getName();
			if(city.indexOf(name)!=-1){
				String msg  = area.getMsg();
				if("未开通".equals(msg)){
					retMap.put("flag", false);
				}else{
					retMap.put("flag", true);
				}
				retMap.put("msg",name+"【"+msg+"】");
				return retMap;
			}
		}
		
		String  district = trade.getReceiverDistrict();
		//参数
		System.out.println("district:"+district);
		if (district.length() == 0) {
			retMap.put("flag", false);
			retMap.put("msg", "该记录没有县区信息");
			return retMap;
		}
		//参数
		Map<String, Object> params = new HashMap<String, Object>();// 区域开始匹配,
		params.put("name", district);
		params.put("level", 3);
		List<AuditArea> areaList = auditAreaDao.findAuditArea(params);
		if(areaList!=null   &&  areaList.size()>0){//配到县区
			AuditArea   auditArea = null;
			if(areaList.size() == 1){  //没有匹配到同名区域
				auditArea = areaList.get(0);
				String msg   =auditArea.getMsg();
				String name  =auditArea.getName();
				if("未开通".equals(msg)){
					retMap.put("flag", false);
				}else if("全境".equals(msg)){
					retMap.put("flag", true);
					retMap.put("msg", district+"【"+msg+"】");
					return retMap;
				}else{
					AuditArea checkAreaLev4 = checkAreaLev4(auditArea, trade);
					if(checkAreaLev4==null){  //区域下没有批到到乡镇信息
						retMap.put("flag", false);
						retMap.put("msg", name+"【"+msg+"】");
						return retMap;
					}
					if("未开通".equals(checkAreaLev4.getMsg())){
						retMap.put("flag", false);
						retMap.put("msg", district+" "+checkAreaLev4.getName()+"【"+checkAreaLev4.getMsg()+"】");
						return retMap;
					}else{
						retMap.put("flag", true);
						retMap.put("msg", district+" "+checkAreaLev4.getName()+"【"+checkAreaLev4.getMsg()+"】");
						return retMap;
					}
				}
				retMap.put("msg", district+" "+name+"【"+msg+"】");
				return retMap;
			}else{   //匹配到同名 区县
				params.clear();
				params.put("name",  city);
				params.put("level", 2);
				System.out.println("city:"+city);
				List<AuditArea> cityList = auditAreaDao.findAuditArea(params);
				System.out.println("cityList:"+cityList.size());
				boolean  flag  =  false;  //标记是城市
				for(int  i  = 0, size   = cityList.size();i<size;i++){
					for(AuditArea  cityArea  :  areaList){
						System.out.println("areaList:"+areaList.size());
						System.out.println(cityList.get(i).getCode()+":"+cityArea.getParentCode());
						if(cityList.get(i).getCode().equals(cityArea.getParentCode())){
							auditArea  = cityArea;
							flag =  true;
							break;
						}
					}
					if(flag){
						break;
					}
				}
				if(flag){
					if(auditArea.getMsg().equals("全境")){
						retMap.put("flag", true);
						retMap.put("msg",city+" "+auditArea.getName()+"【"+auditArea.getMsg()+"】");
						return  retMap;
					}
					AuditArea checkAreaLev4 = checkAreaLev4(auditArea, trade);
					if(checkAreaLev4==null){
						retMap.put("msg",city+" "+ district+"【"+auditArea.getMsg()+"】");
						return  retMap;
					}
					if("未开通".equals(checkAreaLev4.getMsg())){
						retMap.put("flag", false);
					}else{
						retMap.put("flag", true);
					}
					retMap.put("msg",city+" "+ district+"【"+checkAreaLev4.getMsg()+"】");
					return  retMap;
				}
				
			}
		}else{ // 没有匹配到县区信息
			retMap.put("flag", false);
			retMap.put("msg", district+"无法匹配到");
			return retMap;
		}
		retMap.put("flag", false);
		retMap.put("msg", district+"无法匹配到区县记录" );
		return retMap;
	}
	
	//检查区下面所有街道 
	private  AuditArea  checkAreaLev4(AuditArea  auditArea,Trade trade){
		Map<String,Object> params =  new HashMap<String,Object>();
		params.put("parentCode", auditArea.getCode());
		params.put("level", 4);
		List<AuditArea> areas = auditAreaDao.findAuditArea(params);
		String address = trade.getReceiverAddress();
		if(areas!=null)
			for(AuditArea area  : areas){
				String name  = area.getName();
				if(address.indexOf(name)!=-1){
					return area;
				}
			}
		return null;
	}
		
	/**
	 * 顺风批量导入不达到区域
	 * @param params
	 * @return
	 */
	public Map<String,Object> importSfArea(List<Map<String, Object>>  params){
		Map<String,Object> retMap  =  new HashMap<String,Object>();
		String  code = "YUNDA";
		if(params!=null&&  params.size()>0){
			AuditArea  audit   = new AuditArea();
			AuditArea  audit1   = new AuditArea();
			AuditArea  audit2   = new AuditArea();
			int  size  = params.size();
			int  k  =  1;  //多少个省份
			int  c  =  1;  //多少市区
			int  j  =  1;  //多少个区 
			int  n  =  1;  //多少街道
			for(int i = 0 ;i<size;i++  ){
				Map<String, Object>  param   =   params.get(i);
				String n1Temp    = ""+param.get("n1");
				String n2Temp    = ""+param.get("n2");
				String n3Temp    = ""+param.get("n3");
				String n4Temp    = ""+param.get("n4");
				String msg       = ""+param.get("msg");
				
				System.out.println("n1Temp:"+n1Temp);
				
				System.out.println("n2Temp:"+n2Temp);
				
				System.out.println("n3Temp:"+n3Temp);
				
				System.out.println("n4Temp:"+n4Temp);
				
				System.out.println("msg:"+msg);
//				if(i==5){
//					break;
//				}
				
				
				
				if(n2Temp.length()==0 ){  //省
					AuditArea  auditArea   = new AuditArea();
					auditArea.setCode(StringUtils.formatAddZero(3,""+k));
					auditArea.setName(n1Temp);
					auditArea.setLevel(1);
					auditArea.setEnd(false);
					auditArea.setCopmany(code);
					auditArea.setMsg(msg);
					auditAreaDao.save(auditArea);
					audit  =  auditArea;
					k++;
					continue;
				}
				if(n3Temp.length()==0){   //市
					AuditArea  auditArea1   = new AuditArea();
					auditArea1.setCode(audit.getCode()+StringUtils.formatAddZero(5,""+c));
					auditArea1.setName(n2Temp);
					auditArea1.setLevel(2);
					auditArea1.setParentCode(audit.getCode());
					auditArea1.setEnd(false);
					auditArea1.setCopmany(code);
					auditArea1.setMsg(msg);
					auditAreaDao.save(auditArea1);
					audit1 =  auditArea1;
					c++;
					continue;
				}if(n4Temp.length()==0){ //区
					String code2 =  StringUtils.formatAddZero(6,""+j);
					AuditArea  auditArea2   = new AuditArea();
					auditArea2.setCode(audit1.getCode()+code2);
					auditArea2.setName(n3Temp);
					auditArea2.setLevel(3);
					auditArea2.setParentCode(audit1.getCode());
					auditArea2.setEnd(false);
					auditArea2.setCopmany(code);
					auditArea2.setMsg(msg);
					auditAreaDao.save(auditArea2);
					audit2 =  auditArea2;
					j++;
					continue;
				} //街道 
				AuditArea  auditArea3   = new AuditArea();
				String code3 =  StringUtils.formatAddZero(6,""+n);
				auditArea3.setCode(audit2.getCode()+code3);
				auditArea3.setName(n4Temp);
				auditArea3.setLevel(4);
				auditArea3.setEnd(true);
				auditArea3.setParentCode(audit2.getCode());
				auditArea3.setCopmany(code);
				auditArea3.setMsg(msg);
				auditAreaDao.save(auditArea3);
				n++;
				
			}
		}else{
			retMap.put("msg", "无有效数据");
		}
		return retMap;
	}
}
