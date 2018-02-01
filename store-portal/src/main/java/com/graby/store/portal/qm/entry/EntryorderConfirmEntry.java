package com.graby.store.portal.qm.entry;

/**
 * 
 * 入库确认单 entry
 * @author shark_cj
 * 
 */
public class EntryorderConfirmEntry {
	
	 public  static String  method  =  "taobao.qimen.entryorder.confirm"; 
	
	/**
	 *  台头名称
	 */
	 public  static String head  =  "entryOrder"; 
	 
	 
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
	  * <entryOrderCode>入库单编码,  string (50) ,  必填 </entryOrderCode>
	  */
	  public  static String entryOrderCode  =  "entryOrderCode";
	  
	  /**
	   * <ownerCode>货主编码,  string (50) </ownerCode> 
	   */
	  public  static String ownerCode  =  "ownerCode";
	  
	  /**
	   *  <warehouseCode>仓库编码,  string (50) </warehouseCode>
	   */
	  public  static String warehouseCode  =  "warehouseCode";
	  
	  /**
	   * <entryOrderId>仓储系统入库单ID, string (50) , 必填</entryOrderId>
	   */
	  public  static String entryOrderId  =  "entryOrderId";
	  
	  /**
	   * <pre>
	   * <entryOrderType>入库单类型 </entryOrderType>  
	   * SCRK=生产入库
	   * LYRK=领用入库
	   * CCRK=残次品入库
	   * CGRK=采购入库 
	   * DBRK=调拨入库 
	   * QTRK=其他入库
	   * <pre>
	   */
	  public  static String entryOrderType  =  "entryOrderType";
	 
	  /**
	   * <outBizCode>外部业务编码, 多次确认时, 每次传入要求唯一,必填</outBizCode> 
	   */
	  public  static String outBizCode  =  "outBizCode";
	  
	   /**
	    * <pre>
	    * <confirmType>支持出入库单多次收货, int,</confirmType>	
	    * 多次收货后确认时	
	    * 0 表示入库单最终状态确认；
	    * 1 表示入库单中间状态确认；
	    * <pre>
	    */
	  public  static String confirmType  =  "confirmType";
	  
	  /**
	   * <pre>
	   * <status>入库单状态, string (50) ,  必填  (只传英文编码) </status> 
	   * NEW-未开始处理,  
	   *  ACCEPT-仓库接单 , 
	   *  PARTFULFILLED-部分收货完成,  
	   *  FULFILLED-收货完成,  
	   *  EXCEPTION-异常,  
	   *  CANCELED-取消,  
	   *  CLOSED-关闭,  
	   *  REJECT-拒单,  
	   *  CANCELEDFAIL-取消失败
	   *  <pre>
	   */
	  public  static String status  =  "status";
	  
	  /**
	   * <pre>
	   * <operateTime>操作时间,  string (19)  </operateTime>
	   *  YYYY-MM-DD HH:MM:SS，
	   *  (当status=FULFILLED, operateTime为入库时间) 
	   *  <pre>
	   */
	  public  static String operateTime  =  "operateTime";
	  
	  /**
	   *  <remark>备注, string (500) </remark>
	   */
	  public  static String remark  =  "remark";
	  
	  
	  //  head ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑     
	  
	  
	  
	  
	  //  detail ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	  
	  /**
	   * <orderLineNo>单据行号，string（50）</orderLineNo>
	   */
	  public static String orderLineNoDetail = "orderLineNo";
	  
	  /**
	   * <itemCode>商品编码, string (50) , 必填</itemCode>
	   */
	  public static String itemCodeDetail = "itemCode";
	  
	  /**
	   *  <itemId>仓储系统商品ID, string (50) , 条件必填</itemId>
	   */
	  public static String itemIdDetail = "itemId";
	  
	  /**
	   *  <itemName>商品名称,  string (200) </itemName>
	   */
	  public static String itemNameDetail = "itemName";
	  
	  /**
	   *  <pre>
	   *  <inventoryType>库存类型，string (50)</inventoryType>
	   *  ZP=正品, 
	   *  CC=残次,
	   *  JS=机损, 
	   *  XS= 箱损，
	   *  默认为ZP, (收到商品总数=正品数+残品数+机损数+箱损数) 
	   *  </pre> 
	   */
	  public static String inventoryTypeDetail = "inventoryType";
	  
	  /**
	   *  <planQty>应收数量, int</planQty>
	   */
	  public static String planQtyDetail = "planQty";
	  
	  /**
	   * <actualQty>实收数量, int，必填</actualQty> 
	   */
	  public static String actualQtyDetail = "actualQty";
	  
	  /**
	   *  <batchCode>批次编码, string (50) </batchCode> 
	   */
	  public static String batchCodeDetail = "batchCode";

	  /**
	   *  <productDate>商品生产日期，string（10）， YYYY-MM-DD</productDate> 
	   */
	  public static String productDateDetail = "productDate";
	  
	  /**
	   *  <expireDate>商品过期日期，string（10），YYYY-MM-DD</expireDate> 
	   */
	  public static String expireDateDetail = "expireDate";
	  
	  /**
	   *  <produceCode>生产批号, string (50) </produceCode>
	   */
	  public static String produceCodeDetail = "produceCode";
	  
	  /**
	   *  <remark>备注, string (500) </remark>
	   */
	  public static String remarkDetail = "remark";
	  
	  //  detail ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
	  
}
