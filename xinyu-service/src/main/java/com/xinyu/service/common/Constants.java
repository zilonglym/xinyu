package com.xinyu.service.common;

/**
 * 
 * @author yangmin
 * 2017年4月23日
 *
 */
public class Constants {
	/**
	 * 菜鸟参数配置 开始
	 */
	/*
	public final static String cainiao_appKey="SANDBOX340142";
    public final static String cainiao_secretKey = "wHN6aN909F3452R9Ar6oF0leh58kpG7u";
    public final static String cainiao_url = "http://pac.tbsandbox.com/gateway/pac_message_receiver.do?session_type=debug";
    public final static String cainiao_fromCode="Tran_Store_13333333";
    */
    
    public final static String cainiao_appKey="198831";
//	 k813674P4EQ1P7HB6274021TV46PYJ3e
   public final static String cainiao_secretKey = "lMZ61yM1R28T5C2Tp18Cu5fqpx5A913P";
   public final static String cainiao_url = "http://pac.partner.taobao.com/gateway/pac_message_receiver.do";
   public final static String cainiao_fromCode="TZC001"; //仓库编码
    
    /**
     * 菜鸟默认操作帐号
     */
    public final static String cainiao_account="cainiao";
    /**
     * 奇门默认操作帐号
     */
    public final static String qimeng_account="qimeng";
    /**
     * 菜鸟参数配置结束
     */
    
    /**
     * 菜鸟接口列表
     */
    /**
     * 用户订购仓储服务通知（主动模式，WMS_SUBSCRIPTION_NOTIFY，4PL仓必选！3PL仓非必选） 
     */
    public final static String WMS_SUBSCRIPTION_NOTIFY="WMS_SUBSCRIPTION_NOTIFY";  //
    /**
     *OK    商品信息通知（主动模式，WMS_SKU_INFO_NOTIFY，必选！）   OK
     */
    public final static String WMS_SKU_INFO_NOTIFY="WMS_SKU_INFO_NOTIFY";
    /**
     * OK   WMS查询订单商品信息（被动模式，WMS_ITEM_QUERY，4PL仓必选！3PL仓非必选）
     */
    public final static String WMS_ITEM_QUERY="WMS_ITEM_QUERY";
    /**
     * OK 销售订单发货通知接口（主动模式，WMS_CONSIGN_ORDER_NOTIFY，必选！） OK
     */
    public final static String WMS_CONSIGN_ORDER_NOTIFY="WMS_CONSIGN_ORDER_NOTIFY";
    /**
     * OK   入库通知单接口（主动模式，WMS_STOCK_IN_ORDER_NOTIFY，必选！）                                   
     */
    public final static String WMS_STOCK_IN_ORDER_NOTIFY="WMS_STOCK_IN_ORDER_NOTIFY";
    /**
     * OK  出库通知单接口（主动模式，WMS_STOCK_OUT_ORDER_NOTIFY，必选！）
     */
    public final static String WMS_STOCK_OUT_ORDER_NOTIFY="WMS_STOCK_OUT_ORDER_NOTIFY";
    /**
     * OK  单据取消通知接口（主动模式，WMS_ORDER_CANCEL_NOTIFY，必选！）
     */
    public final static String WMS_ORDER_CANCEL_NOTIFY="WMS_ORDER_CANCEL_NOTIFY";
    /**
     * 单据状态回传接口（被动模式，WMS_ORDER_STATUS_UPLOAD，必选！）
     */
    public final static String WMS_ORDER_STATUS_UPLOAD="WMS_ORDER_STATUS_UPLOAD";
    /**
     * 盘点单接口（被动模式，WMS_INVENTORY_COUNT，必选！）
     */
    public final static String WMS_INVENTORY_COUNT="WMS_INVENTORY_COUNT";
    /**
     * 发货订单确认接口（被动模式，WMS_CONSIGN_ORDER_CONFIRM，必选！）
     */
    public final static String WMS_CONSIGN_ORDER_CONFIRM="WMS_CONSIGN_ORDER_CONFIRM";
    /**
     * 入库订单确认接口（被动模式，WMS_STOCK_IN_ORDER_CONFIRM，必选！）
     */
    public final static String WMS_STOCK_IN_ORDER_CONFIRM="WMS_STOCK_IN_ORDER_CONFIRM";
    /**
     * 出库订单确认接口（被动模式，WMS_STOCK_OUT_ORDER_CONFIRM，必选！）
     */
    public final static String WMS_STOCK_OUT_ORDER_CONFIRM="WMS_STOCK_OUT_ORDER_CONFIRM";
    /**
     * 3.3.37 转残单确认接口（被动模式，WMS_DEFECTIVE_TRANSFER_CONFIRM，必选！）
     */
    public final static String WMS_DEFECTIVE_TRANSFER_CONFIRM="WMS_DEFECTIVE_TRANSFER_CONFIRM";
    

}
