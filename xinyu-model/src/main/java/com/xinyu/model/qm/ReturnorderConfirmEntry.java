package com.xinyu.model.qm;


/**
 * 退货入库确认
 * @author shark_cj
 *
 */
public class ReturnorderConfirmEntry {

	public static String method = "taobao.qimen.returnorder.confirm";

	/**
	 * 台头名称
	 */
	public static String head = "returnOrder";

	/**
	 * 明细名称
	 */
	public static String details = "orderLines";
	/**
	 * 明细名称
	 */
	public static String detail = "orderLine";
	
	/**
	 * <returnOrderCode>退货单编码,  string (50) ,  必填</returnOrderCode> 
	 */
	public static String returnOrderCode = "returnOrderCode";
	
	/**
	 * <returnOrderId>仓库系统订单编码, string (50) , 条件必填</returnOrderId> 
	 */
	public static String returnOrderId = "returnOrderId";
	
	/**
	 * <warehouseCode>仓库编码,  string (50) </warehouseCode>  
	 */
	public static String warehouseCode = "warehouseCode";
	
	/**
	 * <outBizCode>外部业务编码, 多次确认时, 每次传入要求唯一</outBizCode> 
	 */
	public static String outBizCode = "outBizCode";
	
	/**
	 * <pre>
	 * <orderType>单据类型, string（50）</orderType> 
	 * THRK=退货入库单，HHRK=换货入库 
	 * </pre>
	 */
	public static String orderType = "orderType";
	
	/**
	 *  <orderConfirmTime>确认入库时间, string (19) ,YYYY-MM-DD HH:MM:SS</orderConfirmTime>  
	 */
	public static String orderConfirmTime = "orderConfirmTime";
	
	/**
	 *  <returnReason>退货原因, string (200) </returnReason>  
	 */
	public static String returnReason = "returnReason";
	
	/**
	 * <pre>
	 *  <logisticsCode>物流公司编码, string (50) </logisticsCode>
	 *   SF=顺丰、EMS=标准快递、
	 *   EYB=经济快件、
	 *   ZJS=宅急送、
	 *   YTO=圆通  、
	 *   ZTO=中通 (ZTO) 、
	 *   HTKY=百世汇通、
	 *   UC=优速、STO=申通、
	 *   TTKDEX=天天快递  、
	 *   QFKD=全峰、
	 *   FAST=快捷、
	 *   POSTB=邮政小包  、
	 *   GTO=国通、
	 *   YUNDA=韵达, 
	 *   OTHER=其他,  
	 *   (只传英文编码) 
	 * </pre>
	 */
	public static String logisticsCode = "logisticsCode";
	
	/**
	 *  <logisticsName>物流公司名称, string (200) </logisticsName> 
	 */
	public static String logisticsName = "logisticsName";
	
	/**
	 *  <expressCode>运单号, string (50) </expressCode> 
	 */
	public static String expressCode = "expressCode";
	
	/**
	 *  <remark>备注, string (50) </remark> 
	 */
	public static String remark = "remark";
	
	/**
	 * @author senderInfo
	 */
	public interface senderInfo{
		/**
		 *  <company>公司名称, string (200) </company>  
		 */
		public static String company = "company";
		
		/**
		 * <name>姓名, string (50) , 必填</name>  
		 */
		public static String name = "name";
		
		/**
		 *  <zipCode>邮编, string (50) </zipCode>     
		 */
		public static String zipCode = "zipCode";
		/**
		 *  <tel>固定电话, string (50) </tel>      
		 */
		public static String tel = "tel";
		/**
		 *  <mobile>移动电话, string (50) , 必填</mobile>      
		 */
		public static String mobile = "mobile";
		/**
		 *  <email>电子邮箱, string (50) </email>       
		 */
		public static String email = "email";
		/**
		 *  <countryCode>国家二字码，string（50）</countryCode>      
		 */
		public static String countryCode = "countryCode";
		/**
		 *  <province>省份, string (50) , 必填</province>        
		 */
		public static String province = "province";
		/**
		 *  <city>城市, string (50) , 必填</city>       
		 */
		public static String city = "city";
		/**
		 *  <area>区域, string (50)  </area>       
		 */
		public static String area = "area";
		/**
		 * <town>村镇, string (50) </town>      
		 */
		public static String town = "town";
		/**
		 * <detailAddress>详细地址, string (200) , 必填</detailAddress> 
		 */
		public static String detailAddress = "detailAddress";
	}
	
	/**
	 * @author orderLine
	 */
	public interface orderLine{
		/**
		 * <orderLineNo>单据行号，string（50）</orderLineNo> 
		 */
		public  static String orderLineNo  =  "orderLineNo"; 
		
		/**
		 * <sourceOrderCode>交易平台订单, string (50) </sourceOrderCode>   
		 */
		public  static String sourceOrderCode  =  "sourceOrderCode"; 
		
		/**
		 * <subSourceOrderCode>交易平台子订单编码, string (50) </subSourceOrderCode>    
		 */
		public  static String subSourceOrderCode  =  "subSourceOrderCode"; 
		
		/**
		 * <itemCode>商品编码, string (50) ，必填</itemCode>
		 */
		public  static String itemCode  =  "itemCode"; 
		
		/**
		 * <itemId>商品仓储系统编码, string (50)</itemId>
		 */
		public  static String itemId  =  "itemId"; 
		
		
		/**
		 * <pre>
		 * <inventoryType>库存类型，string (50) </inventoryType>
		 *  ZP=正品
		 *  CC=残次
		 *  JS=机损 
		 *  XS= 箱损
		 *  默认为ZP
		 *  </pre>
		 */
		public  static String inventoryType  =  "inventoryType"; 
		
		/**
		 *  <planQty>应收商品数量, int</planQty> 
		 */
		public  static String planQty  =  "planQty"; 
		
		/**
		 * <actualQty>实收商品数量, int，必填</actualQty> 
		 */
		public  static String actualQty  =  "actualQty"; 

		
		/**
		 * <batchCode>批次编号，string(50)，</batchCode>
		 */
		public  static String batchCode  =  "batchCode"; 
		
		/**
		 * <productDate>生产日期，string(10)，YYYY-MM-DD</productDate>
		 */
		public  static String productDate  =  "productDate"; 
		
		/**
		 * <expireDate>过期日期，string(10)，YYYY-MM-DD </expireDate>
		 */
		public  static String expireDate  =  "expireDate"; 
		
		/**
		 * <produceCode>生产批号，string(50)，</produceCode>
		 */
		public  static String produceCode  =  "produceCode"; 
		
	}
}
