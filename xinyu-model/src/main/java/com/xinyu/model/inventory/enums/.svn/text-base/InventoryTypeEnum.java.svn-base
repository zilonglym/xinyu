package com.xinyu.model.inventory.enums;




/**
 * 
 * @author shark_cj
 * @since  2017-04-25
 *  <pre>
 *    商品库存类型
 *    1 可销售库存(正品) 
 *    101 残次 
 *    102 机损 
 *    103 箱损
 *    301 在途库存 
 *    201 冻结库存
 *  </pre>
 */
public enum InventoryTypeEnum {
	
	
	NORMAL("1","可销售库存(正品) "),
	DEFECTIVE("101","残次"),
	MECHANICAL("102","机损"),
	CASES("103","箱损"),
	INTRANSIT("301","在途库存"),
//	BLOCKING("201","冻结库存"),
	BLOCKING("201","待发货库存"),
	ELSE("1000","测试其他"),
	;
	
	public static InventoryTypeEnum getInventoryType(String key){
		if(key==null|| key.length()==0){
			return InventoryTypeEnum.getInventoryType("1000");
		}
		InventoryTypeEnum[] ary=InventoryTypeEnum.values();
		for(InventoryTypeEnum obj:ary){
			if(obj.getKey().equals(key)){
				return obj;
			}
		}
		return InventoryTypeEnum.getInventoryType("1000");
	}
	
	private String key;
	
	private String description;
	
	private InventoryTypeEnum(String key,String description){
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
