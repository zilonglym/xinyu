package com.graby.store.entity.qm;


/**
 * 仓库盘点通知
 * @author shark_cj
 */
public class InventoryReportEntry {
	
	/**
	 * <totalPage>总页数, int,必填</totalPage>
	 */
	public static String totalPage = "totalPage";

	/**
	 * <currentPage>当前页, 从1开始,int， 必填</currentPage> 
	 */
	public static String currentPage = "currentPage";
	
	/**
	 * <pageSize>每页记录的条数, int,  必填</pageSize>  
	 */
	public static String pageSize = "pageSize";
	
	/**
	 * <warehouseCode>仓库编码, string (50) ,  必填</warehouseCode>
	 */
	public static String warehouseCode = "warehouseCode";
	
	/**
	 * <checkOrderId>仓储系统的盘点单编码, string（50），必填</checkOrderId>
	 */
	public static String checkOrderId = "checkOrderId";
	
	/**
	 *  <ownerCode>货主编码, string (50) ,  必填</ownerCode>
	 */
	public static String ownerCode = "ownerCode";  
	
	/**
	 *  <checkTime>盘点时间，string(19)，string (19) , YYYY-MM-DD HH:MM:SS </checkTime>
	 */
	public static String checkTime = "checkTime";  
	
	/**
	 *  <outBizCode>外部业务编码, 一个合作伙伴中要求唯一多次确认时, 每次传入要求唯一,  必填</outBizCode> 
	 */
	public static String outBizCode = "outBizCode";  
	
	/**
	 *  <remark>备注, string (500)</remark>
	 */
	public static String remark = "remark";  
	
	public interface item{
		
		/**
		 *  <itemCode>商品编码, string (50) , 必填</itemCode>  
		 */
		public static String itemCode = "itemCode";  
		
		/**
		 * <inventoryType>库存类型，string (50)   </inventoryType>
		 */
		public static String inventoryType = "inventoryType";
		
		/**
		 * <quantity>盘盈盘亏商品变化量，int，必填，盘盈为正数，盘亏为负数</quantity>  
		 */
		public static String quantity = "quantity";
		
		/**
		 * <snCode>商品序列号</snCode>
		 */
		public  static String snCode  =  "snCode"; 
		
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
		/**
		 * <remark>生产批号，string(50)，</remark>
		 */
		public  static String remark  =  "remark"; 
	}

}
