package com.xinyu.model.inventory.enums;



/**
 * @author shark_cj
 * @since  2017-04-30
 *
 *<pre>
 *	WMS_GOODS_OVER_FLOW CHECK 仓内多货
 *	WMS_GOODS_LOCK CHECK 仓内少货
 *	WMS_GOODS_OWNER_TRANSFER ADJUST 货权转移
 *	WMS_GOODS_DEFECT ADJUST 临保转残
 *	WMS_GOODS_DAMAGED ADJUST 库内破损
 *	WMS_GOODS_BATCH_ADJUST ADJUST 批次调整
 *  OTHER   其他
 *</pre>
 */
public enum AdjustReasonTypeEnum {
	
	WMS_GOODS_OVER_FLOW("WMS_GOODS_OVER_FLOW","仓内多货"),
	WMS_GOODS_LOCK("WMS_GOODS_LOCK","仓内少货"),
	WMS_GOODS_OWNER_TRANSFER("WMS_GOODS_OWNER_TRANSFER","货权转移"),
	WMS_GOODS_DEFECT("WMS_GOODS_DEFECT","临保转残"),
	WMS_GOODS_DAMAGED("WMS_GOODS_DAMAGED","库内破损"),
	WMS_GOODS_BATCH_ADJUST("WMS_GOODS_BATCH_ADJUST","库内破损"),
	OTHER("OTHER","其他")
	;
	
	public static AdjustReasonTypeEnum getAdjustReasonTypeEnum(String key){
		if(key==null|| key.length()==0){
			return AdjustReasonTypeEnum.getAdjustReasonTypeEnum("OTHER");
		}
		AdjustReasonTypeEnum[] ary=AdjustReasonTypeEnum.values();
		for(AdjustReasonTypeEnum obj:ary){
			if(obj.getKey().equals(key)){
				return obj;
			}
		}
		return AdjustReasonTypeEnum.getAdjustReasonTypeEnum("OTHER");
	}
	
	private String key;
	
	private String description;
	
	private AdjustReasonTypeEnum(String key,String description){
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
