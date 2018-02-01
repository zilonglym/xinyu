package com.xinyu.model.qm;



/**
 * 出库确认单 entry
 * @author shark_cj
 * @since  2015 -07 -30
 */
public class StockoutConfirmEntry {
	
	 	public  static String  method  =  "taobao.qimen.stockout.confirm"; 
		
		/**
		 *  台头名称
		 */
		public  static String head  =  "deliveryOrder"; 
		 
		 
		 /**
		  *  明细名称
		  */
		public  static String details  =  "orderLines"; 
		 /**
		  *  明细名称
		  */
		public  static String detail  =  "orderLine";
		
		
		
		 
		 //  head ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
		 /**
		  *  <deliveryOrderCode>出库单号, string (50) , 必填</deliveryOrderCode>  
		  */
		public  static String deliveryOrderCode  =  "deliveryOrderCode";
		
		/**
		 * <deliveryOrderId>仓储系统出库单号, string (50) ，条件必填</deliveryOrderId>
		 */
		public  static String deliveryOrderId  =  "deliveryOrderId";

		/**
		 * <warehouseCode>仓库编码, string (50) </warehouseCode>
		 */
		public  static String warehouseCode  =  "warehouseCode"; 
		
		/**
		 * <pre>
		 * <orderType>出库单类型, string (50) 必填</orderType> 
		 * PTCK=普通出库单（退仓）
		 * DBCK=调拨出库 
		 * QTCK=其他出库，
		 * </pre>
		 */
		public  static String orderType  =  "orderType"; 
		
		/**
		 * <outBizCode>外部业务编码, 消息ID, 用于去重, 一个合作伙伴中要求唯一, 多次确认时, 每次传入要求唯一 ，条件必填，条件为一单需要多次确认时 </outBizCode>  
		 */
		public  static String outBizCode  =  "outBizCode"; 
 
		/**
		 * <pre>
		 * <confirmType>支持出库单多次发货, int，多次发货后确认时</confirmType> 
		 * 0 表示发货单最终状态确认
		 * 1 表示发货单中间状态确认
		 * </pre>
		 */
		public  static String confirmType  =  "confirmType"; 
		
		/**
		 * <logisticsName>物流公司名称, string (200) </logisticsName>
		 */
		public  static String logisticsName  =  "logisticsName"; 
		
		/**
		 *  <expressCode>运单号, string (50) </expressCode> 
		 */
		public  static String expressCode  =  "expressCode"; 

		/**
		 *  <orderConfirmTime>订单完成时间, string (19) , YYYY-MM-DD HH:MM:SS </orderConfirmTime>
		 */
		public  static String orderConfirmTime  =  "orderConfirmTime"; 
		
		
		
		
		 /**
		  *  package
		  */
		public  static String packageNames  =  "packages"; 
		
		 /**
		  *  packages
		  */
		public  static String packageName  =  "package";
		
		
		public interface packages{
			/**
			 * <logisticsName>物流公司名称, string (200) </logisticsName>  
			 */
			public  static String logisticsName  =  "logisticsName";
			
			/**
			 * <expressCode>运单号, string (50) </expressCode>  
			 */
			public  static String expressCode  =  "expressCode";
			
			/**
			 * <packageCode>包裹编号, string (50) </packageCode>  
			 */
			public  static String packageCode  =  "packageCode";
			
			/**
			 *  <length>包裹长度 (厘米) , double (18, 2) </length>   
			 */
			public  static String length  =  "length";
			
			/**
			 *  <width>包裹宽度 (厘米) , double (18, 2) </width>  
			 */
			public  static String width  =  "width";
			
			
			/**
			 *  <height>包裹高度 (厘米) , double (18, 2) </height>  
			 */
			public  static String height  =  "height";
			
			/**
			 *   <weight>包裹重量 (千克) , double (18, 3) </weight>  
			 */
			public  static String weight  =  "weight";
			
			/**
			 *   <volume>包裹重量 (千克) , double (18, 3) </volume>  
			 */
			public  static String volume  =  "volume";
			
		}
		
		public interface packageItems{
			
			/**
			 *  <itemCode>商品编码, string (50) , 必填</itemCode> 
			 */
			public  static String itemCode  =  "itemCode"; 
			
			/**
			 *  <itemId>商品仓储系统编码, string (50)</itemId>  
			 */
			public  static String itemId  =  "itemId"; 
			
			/**
			 *  <quantity>包裹内该商品的数量, int, 必填</quantity>  
			 */
			public  static String quantity  =  "quantity"; 
			
			
		}
		
		public interface packageMaterials{
			 
			/**
			 *  <type>包材型号, string (50) </type>  
			 */
			public  static String type  =  "type"; 
			/**
			 * <quantity>包材的数量, int</quantity> 
			 */
			public  static String quantity  =  "quantity"; 
			
		}
		
		
		public interface onlines{
			/**
			 * <orderLineNo>单据行号，string（50）</orderLineNo> 
			 */
			public  static String orderLineNo  =  "orderLineNo"; 
			
			/**
			 * <itemCode>商品编码, string (50) ，必填</itemCode>
			 */
			public  static String itemCode  =  "itemCode"; 
			
			/**
			 * <itemId>商品仓储系统编码, string (50)</itemId>
			 */
			public  static String itemId  =  "itemId"; 
			
			/**
			 * <itemName>商品名称, string (200) </itemName>
			 */
			public  static String itemName  =  "itemName"; 
			
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
			 * <actualQty>实发商品数量, int，必填</actualQty> 
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
