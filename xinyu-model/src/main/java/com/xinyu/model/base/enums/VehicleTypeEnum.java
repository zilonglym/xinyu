package com.xinyu.model.base.enums;

/**
 * 
 * @author shark_cj
 * @since  2017-04-25  
 * 
 *  <pre>
 * 	配送车辆类型
 *	NORMAL          0:普通货车，
 * 	FLATCAR         1:平板车，
 *	MOTORVAN 	    2:箱式货车，
 *  REFRIGERATORCAR 3:冷藏车
 *  ELSE            100:其他,测试用;
 *  </pre>
 */
public enum VehicleTypeEnum {
	
	NORMAL("0","普通货车"),
	FLATCAR("1","平板车"),
	MOTORVAN("2","箱式货车"),
	REFRIGERATORCAR("3","冷藏车"),
	ELSE("100","其他,测试用");
	
	private String key;
	
	private String description;
	
	
	public static VehicleTypeEnum getVehicleTypeEnum(String key){
		if(key==null|| key.length()==0){
			return VehicleTypeEnum.getVehicleTypeEnum("100");
		}
		VehicleTypeEnum[] ary=VehicleTypeEnum.values();
		for(VehicleTypeEnum obj:ary){
			if(obj.getKey().equals(key)){
				return obj;
			}
		}
		return VehicleTypeEnum.getVehicleTypeEnum("100");
	}
	
	
	private VehicleTypeEnum(String key,String description){
		this.key=key;
		this.description=description;
	}
	public String getKey() {
		return key;
	}
	public String getDescription() {
		return description;
	}

}
