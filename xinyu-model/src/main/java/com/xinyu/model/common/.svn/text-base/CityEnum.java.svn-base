package com.xinyu.model.common;
/**
 * 城市列表
 * 
 * @author Hou Peiqin
 * 
 */
public enum CityEnum {
	SHANGHAI("上海","SHANGHAI","SH","" , QfangModeEnum.PORT,true,"021",false),
	BEIJING("北京","BEIJING", "BJ","beijing_house"  , QfangModeEnum.PORT,true,"010",false),
	SHENZHEN("深圳", "SHENZHEN","SZ","SHENZHEN" , QfangModeEnum.O2O,false,"0755",false),
	ZHUHAI("珠海", "ZHUHAI","ZH","ZHUHAI" , QfangModeEnum.O2O,false,"0756",false),
	ZHONGSHAN("中山", "ZHONGSHAN","ZS","ZHUHAI" , QfangModeEnum.O2O,false,"0760",false),
	DONGGUAN("东莞", "DONGGUAN","DG","", QfangModeEnum.PORT,false,"0769",false),
	GUANGZHOU("广州","GUANGZHOU","GZ","guangzhou_house", QfangModeEnum.PORT,false,"020",false),
	CHENGDU("成都", "CHENGDU", "CD","", QfangModeEnum.PORT,false,"028",false),
	CHONGQING("重庆", "CHONGQING", "CQ","", QfangModeEnum.PORT,false,"023",false),
	WUHAN("武汉", "WUHAN", "WH","", QfangModeEnum.PORT,false,"027",false),
	SUZHOU("苏州", "SUZHOU", "SZ","suzhou_house", QfangModeEnum.PORT,true,"0512",false),
	NANJING("南京", "NANJING", "NJ","nanjing_house", QfangModeEnum.PORT,true,"025",false),
	ZHENGZHOU("郑州", "ZHENGZHOU", "ZZ","zhengzhou_house", QfangModeEnum.PORT,true,"0371",false),
	HANGZHOU("杭州", "HANGZHOU", "HZ","", QfangModeEnum.PORT,true,"0571",false),
	FOSHAN("佛山", "FOSHAN", "FS","foshan_house", QfangModeEnum.O2O,false,"0757",false),
	XIAMEN("厦门", "XIAMEN", "XM","", QfangModeEnum.PORT,false,"0592",false),
	JINAN("济南","JINAN", "JN","", QfangModeEnum.PORT,false,"0531",false),
	HEFEI("合肥","HEFEI", "HF","", QfangModeEnum.PORT,false,"0551",false),
	QINGDAO("青岛","QINGDAO", "QD","", QfangModeEnum.PORT,false,"0532",false),
	WUXI("无锡","WUXI", "WX","", QfangModeEnum.PORT,false,"0510",false),
	NINGBO("宁波","NINGBO", "NB","", QfangModeEnum.PORT,false,"0574",false)	,
	KUNMING("昆明","KUNMING", "KM","", QfangModeEnum.PORT,false,"0871",false),
	CHANGSHA("长沙","CHANGSHA", "CS","" , QfangModeEnum.PORT,false,"0731",false),
	SHIJIAZHUANG("石家庄","SHIJIAZHUANG", "SJZ","", QfangModeEnum.PORT,false,"0311",false),
	DALIAN("大连","DALIAN", "DL","", QfangModeEnum.PORT,false,"0411",false),
	TIANJIN("天津","TIANJIN", "TJ","", QfangModeEnum.PORT,false,"022",false),
	NANCHANG("南昌","NANCHANG", "NC","", QfangModeEnum.PORT,false,"0791",false),
	KUNSHAN("昆山","KUNSHAN", "KS","",QfangModeEnum.PORT,false,"",false),
	XIANGGANG("香港","XIANGGANG", "XG","", QfangModeEnum.PORT,false,"",false),
	AOMEN("澳门","AOMEN", "AM","",QfangModeEnum.PORT,false,"",false),
	FUZHOU("福州","FUZHOU", "FZ","",QfangModeEnum.PORT,false,"",false),
	NANTONG("南通","NANTONG", "NT","",QfangModeEnum.PORT,false,"",false),
	SANYA("三亚","SANYA", "SY","",QfangModeEnum.PORT,false,"",false),
	HAIKOU("海口","HAIKOU", "HK","",QfangModeEnum.PORT,false,"",false),
	CHANGCHUN("长春","CHANGCHUN", "CC","",QfangModeEnum.PORT,false,"",false),
	SHENYANG("沈阳","SHENYANG", "SYG","",QfangModeEnum.PORT,false,"",false),
	HAERBIN("哈尔滨","HAERBIN", "HEB","",QfangModeEnum.PORT,false,"",false),
	XIAN("西安","XIAN", "XA","",QfangModeEnum.PORT,false,"",false),
	TAIYUAN("太原","TAIYUAN", "TY","",QfangModeEnum.PORT,false,"",false);
	;


	private CityEnum(String alias, String value, String simplePin,String houseSolr,QfangModeEnum mode,boolean isOtherCity,String areaCode,boolean insulate) {
		this.alias = alias;
		this.value = value;
		this.simplePin = simplePin;
		this.houseSolr=houseSolr;
		this.mode = mode;
		this.isOtherCity=isOtherCity;
		this.areaCode=areaCode;
		this.insulate=insulate;
	}

	private String alias;
	private String value;
	private String simplePin;
	private String houseSolr;//使用house索引名称
	private QfangModeEnum mode;//Q房外网类型
	private boolean isOtherCity;//是否是异地城市，需要同步到深圳
	private String areaCode;//区号
	private boolean  insulate;//是否隔离
	
 
	public boolean isInsulate() {
		return insulate;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public boolean isOtherCity() {
		return isOtherCity;
	}

	public QfangModeEnum getMode() {
		return mode;
	}

	public void setMode(QfangModeEnum mode) {
		this.mode = mode;
	}

	public String getHouseSolr() {
		return houseSolr;
	}

	public void setHouseSolr(String houseSolr) {
		this.houseSolr = houseSolr;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getSimplePin() {
		return simplePin;
	}

	public void setSimplePin(String simplePin) {
		this.simplePin = simplePin;
	}

}
