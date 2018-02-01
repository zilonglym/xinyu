package com.xinyu.model.order.enums;



/**
 * @author shark_cj
 * @since 2017-04-27
 * <pre>
 *   213 天猫
 *   201 淘宝
 *   214 京东，
 *   202 1688 阿里中文站 ，
 *   203 苏宁在线，
 *   204 亚马逊中国，
 *   205 当当，
 *   208 1号店，
 *   207 唯品会，
 *   209 国美在线，
 *   210 拍拍，
 *   206 易贝ebay，
 *   211 聚美优品，
 *   212 乐蜂网，
 *   215 邮乐，
 *   216 凡客，
 *   217 优购，
 *   218 银泰，
 *   219 易讯，
 *   221 聚尚网，
 *   222 蘑菇街，
 *   223 POS门店，
 *   301 其他 不在范围之内请忽略
 * </pre>
 * 
 */
public enum OrderSourceEnum {
	
	TMALL("213","天猫"),
	TAOBAO("201","淘宝"),
	JD("214","京东"),
	ALIBABA1688("202","1688 阿里中文站"),
	SUNINGONLINE("203","苏宁在线"),
	AMAZONCHINA("204","亚马逊中国"),
	DANGDANG("205","当当"),
	NO1SHOP("208","1号店"),
	VIPSHOP("207","唯品会"),
	GOME("209","国美在线"),
	PAIPAI("210","拍拍"),
	EBAY("206","易贝ebay"),
	JUMEI("211","聚美优品"),
	LAFASO("212","乐蜂网"),
	ULE("215","邮乐"),
	VANCL("216","凡客"),
	EUGOL("217","优购"),
	INTIME("218","银泰"),
	IEVENTS("219","易讯"),
	JUSHANG("221","易讯"),
	MOGUJIE("222","蘑菇街"),
	POS("223","POS门店"),
	ELSE("301","其他"),
	;
	
	public static OrderSourceEnum getOrderSourceEnum(String key){
		if(key==null|| key.length()==0){
			return OrderSourceEnum.getOrderSourceEnum("301");
		}
		OrderSourceEnum[] ary=OrderSourceEnum.values();
		for(OrderSourceEnum obj:ary){
			if(obj.getKey().equals(key)){
				return obj;
			}
		}
		return OrderSourceEnum.getOrderSourceEnum("301");
	}
	
	private String key;
	
	private String description;
	
	private OrderSourceEnum(String key,String description){
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
