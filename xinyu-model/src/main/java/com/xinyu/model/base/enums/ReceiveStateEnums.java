package com.xinyu.model.base.enums;

/**
 * 收货省份信息
 * @author yangmin
 *
 */
public enum ReceiveStateEnums {
	  BJ("北京","bjs"),  
	  SH("上海","shs"),  
	  TJ("天津","tjs"),  
	  CQ("重庆","cqs"),  
	  AH("安徽省","ahs"),  
	  FJ("福建省","fjs"),  
	  GS("甘肃省","gss"),  
	  GD("广东省","gds"),  
	  GX("广西壮族自治区","gx"),  
	  GZ("贵州省","gzs"),  
	  HB("河北省","hbs"),  
	  HN("河南省","hns"),  
	  HLJ("黑龙江省","hljs"),  
	  HBS("湖北省","hbs"),  
	  HNS("湖南省","hns"),  
	  JL("吉林省","jls"),  
	  JS("江苏省","jss"),  
	  JX("江西省","jxs"),  
	  LN("辽宁省","lns"),  
	  NMG("内蒙古自治区","nmg"),  
	  NX("宁夏回族自治区","nx"),  
	  QH("青海省","qhs"),  
	  SD("山东省","sds"),  
	  SX("山西省","sxs"),  
	  SXS("陕西省","sxs"),  
	  SC("四川省","scs"),  
	  XZ("西藏自治区","xz"),  
	  XJ("新疆维吾尔自治区","xj"),  
	  YN("云南省","yns"),  
	  ZJ("浙江省","zjs"),  
	  HL("海南省","hls"),  
	  XG("香港","xg"),  
	  TY("台湾","tys")
	;
	private String key;
	private String description;
	
	
	private ReceiveStateEnums(String description,String key){
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
