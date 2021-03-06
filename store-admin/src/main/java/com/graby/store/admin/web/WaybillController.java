package com.graby.store.admin.web;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.graby.store.admin.util.BaseResource;
import com.graby.store.base.StoreConstants;
import com.graby.store.entity.Centro;
import com.graby.store.entity.ShipOrder;
import com.graby.store.entity.SystemItem;
import com.graby.store.entity.SystemOperatorRecord;
import com.graby.store.entity.Trade;
import com.graby.store.entity.User;
import com.graby.store.entity.enums.OperatorModel;
import com.graby.store.entity.enums.OrderStatusEnums;
import com.graby.store.remote.CentroRemote;
import com.graby.store.remote.ShipOrderRemote;
import com.graby.store.remote.SystemItemRemote;
import com.graby.store.remote.SystemOperatorRecordRemote;
import com.graby.store.remote.TradeRemote;
import com.graby.store.remote.UserRemote;
import com.graby.store.remote.WayBillRemote;
import com.taobao.api.internal.parser.json.JsonConverter;

/**
 * 面单打印入口控制类，调用菜鸟与顺丰的入口
 * @author yangmin
 *
 */
@Controller
@RequestMapping(value = "waybill")
public class WaybillController extends BaseController{
	
	private static Logger logger = LoggerFactory.getLogger(WaybillController.class);

	@Autowired
	private TradeRemote tradeRemote;

	@Autowired
	private ShipOrderRemote shipOrderRemote;

	@Autowired
	private CentroRemote centroRemote;

	@Autowired
	private UserRemote userRemote;
	
	@Autowired
	private WayBillRemote wayBillRemote;
	
	@Autowired
	private SystemOperatorRecordRemote recordRemote;
	
	@Autowired
	private SystemItemRemote  systemItemRemote;
	
	private Map<String,Object> templateMap=null;
	
	/**
	 * 发货特殊处理的商家，黑白电脑椅
	 */
	private int otherUser=55;
	/**
	 * rs转向菜鸟云打页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="c_print")
	public String test(ModelMap model){
		String ids=this.getString("ids");
		String cpCode=this.getString("cpCode");
		String batchCode=this.getString("batchCode");
		String type=this.getString("type");
		model.put("ids", ids);
		model.put("cpCode", cpCode);
		model.put("batchCode", batchCode);
		model.put("type", type);
		if(StringUtils.isNotBlank(cpCode) && cpCode.equals("SF")){
			return "waybill/sf_ajax";
		}else{
			return "waybill/c_cainiao";
		}
	}
	
	@RequestMapping(value="getTemplates")
	@ResponseBody
	public Map<String,Object> getCainiaoTemplates()  throws Exception {
		Map<String,Object> resultMap=new HashMap<String, Object>();
		String templates=this.wayBillRemote.getCainiaoTemplates();
		resultMap.put("ret", 1);
		resultMap.put("templates", templates);
		logger.debug("templates:"+templates);
		return resultMap;
	}
	
	/**
	 * 刷新URL
	 * @return
	 */
	@RequestMapping(value="refresh")
	@ResponseBody
	public Map<String,Object> refersh(){
		Map<String,Object> resultMap=new HashMap<String, Object>();
		this.refreshTemplate();
		resultMap.put("ret", 1);
		resultMap.put("msg", "刷新成功");
		return resultMap;
	}
	
	private void refreshTemplate(){
		if(this.templateMap==null){
			this.templateMap=new HashMap<String, Object>();
		}
		
		List<SystemItem>  sysItem =this.systemItemRemote.findSystemItemByType("template");
		for(int i=0;i<sysItem.size();i++){
			SystemItem obj=sysItem.get(i);
			this.templateMap.put(obj.getDescription(), obj.getValue());
		}
	}
	
	/**
	 * 菜鸟新接口取订单号
	 * @return
	 */
	@RequestMapping(value="c_getPrintData")
	@ResponseBody
	public Map<String,Object> cPrint(){
		String ids=this.getString("ids");
		String cpCode=this.getString("cp_code");
		String batchCode=this.getString("batchCode");
		Map<String,Object> resultMap=new HashMap<String, Object>();
		if(this.templateMap==null){
			this.refreshTemplate();
		}
		batchCode=String.valueOf(this.getbatchCode());
		String url=(String)this.templateMap.get(cpCode);
		Map<String,Object> map=this.wayBillRemote.getCainiaoBill(ids, cpCode, url, batchCode);
		String msg=(String)map.get("msg");
		if(!StringUtils.isEmpty(msg) ){
			resultMap.put("msg", msg);
			resultMap.put("ret", 0);
		}else{
			resultMap.put("ret", 1);
		}
		resultMap.put("ids", map.get("ids"));
		resultMap.put("batchCode", batchCode);
		return resultMap;
	}

	/**
	 * 取云打印数据
	 * @return
	 */
	@RequestMapping(value="c_printData")
	@ResponseBody
	public Map<String,Object> getCPrintData(){
		String batchCode=String.valueOf(this.getbatchCode());
		if(this.templateMap==null){
			this.refreshTemplate();
		}
		if(StringUtils.isEmpty(batchCode)){
			batchCode=String.valueOf(this.getbatchCode());
		}
		String waybillTemplateURL = "";
	    String   customAreaURL    = "";
	    String ids=this.getString("ids");
	    String type=this.getString("type");
		Map<String,Object> resultMap=new HashMap<String, Object>();
		
		JSONArray documents=new JSONArray();
		System.err.println(ids);
		String[] ary=ids.split(",");
		StringBuffer description=new StringBuffer();
		int orderIndex=0;
		int index=0;
		int printIndex=0;
		for(int i=0;i<ary.length;i++){
			ShipOrder order=this.shipOrderRemote.getShipOrder(Long.valueOf(ary[i]));
			index++;
			waybillTemplateURL = order.getTemplateURL();
			if (StringUtils.isEmpty(waybillTemplateURL)) {
				waybillTemplateURL=(String)this.templateMap.get(order.getExpressCompany());
			}
			
			customAreaURL=(String)this.templateMap.get("AREA");//自定义区域
			
			Centro centro=this.centroRemote.getCentroById(order.getCentroId().intValue());
			
			User user=this.userRemote.getUser(order.getCreateUser().getId());
			
			JSONObject doc=new JSONObject();
			
			JSONArray contents=new JSONArray(); //内容数据，一个内容一个运单
			
			if(StringUtils.isNotBlank( order.getExpressOrderno())){
				doc.put("documentID", order.getExpressOrderno());
			}else{
				continue;
			}
			
			//运单主区域
			List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
			
			JSONObject dataJson=new JSONObject();
			
			JSONObject address=new JSONObject();
			address.put("city", order.getReceiverCity());
			address.put("detail", order.getReceiverAddress());
			address.put("district", order.getReceiverDistrict());
			address.put("province", order.getReceiverState());
			address.put("town", "");
			
			JSONObject recipient=new JSONObject();
			recipient.put("address", address);
			recipient.put("mobile", (order.getReceiverMobile()==null?order.getReceiverPhone():order.getReceiverMobile()));
			recipient.put("name", order.getReceiverName());
			recipient.put("phone", (order.getReceiverPhone()==null?order.getReceiverMobile():order.getReceiverPhone()));
			
			/**
			 * 统计信息
			 */
			if(StringUtils.isEmpty(type) && order!=null && order.getStatus().equals(OrderStatusEnums.WMS_PRINT)){
				description.append(i+1)
					.append("--")
					.append(order.getExpressOrderno())
					.append("--")
					.append(order.getReceiverName())
					.append("--")
					.append(order.getReceiverMobile()==null?order.getReceiverPhone():order.getReceiverMobile())
					.append("<br>");
				orderIndex++;
				continue;
			}
			/**
			 * 统计信息
			 */
			dataJson.put("recipient", recipient);//收件人信息处理
			
			JSONObject routingInfo=new JSONObject();
			
			JSONObject consolidation=new JSONObject();//集包地信息
			consolidation.put("name", order.getSellerMobile());
			consolidation.put("code", order.getSellerPhone());
			routingInfo.put("consolidation", consolidation);
			
			JSONObject origin=new JSONObject();//发件网点名称
			origin.put("code", order.getExpressCompany());
			routingInfo.put("origin", origin);
			
			JSONObject sortation=new JSONObject();//大头笔信息
			sortation.put("name", order.getOrderFlag());
			routingInfo.put("sortation", sortation);
			routingInfo.put("routeCode", StringUtils.isNotBlank(order.getRouteCode())?order.getRouteCode():"");
			dataJson.put("routingInfo", routingInfo);
			
			JSONObject sender=new JSONObject();//发件人信息
			
			JSONObject senderAddress=new JSONObject();
			
			/**
			 * modify 2017-09-06
			 * order.getSellerMessage()为空报错，已纠正
			 * */
			if (order.getSellerMessage()!=null&&order.getSellerMessage().indexOf("环球捕手")!=-1) {
				senderAddress.put("city","");
				senderAddress.put("detail", "环球捕手");
				senderAddress.put("district", "");//区
				senderAddress.put("province", "");
				sender.put("address", senderAddress);
				sender.put("mobile", "");
				sender.put("name", "环球捕手4001603602");
				//sender.put("phone", user.getCode()); 
			}else if(order.getSellerMessage()!=null&&order.getSellerMessage().indexOf("脉宝云店")!=-1){
				senderAddress.put("city","");
				senderAddress.put("detail", "脉宝云店");
				senderAddress.put("district", "");//区
				senderAddress.put("province", "");
				sender.put("address", senderAddress);
				sender.put("mobile","");
				sender.put("name", "脉宝云店4001116789");
				//sender.put("phone", user.getCode());
				
			}else if ("74".equals(order.getCreateUser().getId())) {
				senderAddress.put("city","");
				senderAddress.put("detail", "江西省赣州市信丰县赣南脐橙基地（多仓发货）");
				senderAddress.put("district", "");//区
				senderAddress.put("province", "");
				sender.put("address", senderAddress);
				sender.put("mobile", user.getCode());
				sender.put("name", user.getShopName());
			}else {
				senderAddress.put("city",centro.getCity());
				senderAddress.put("detail", "湖南省湘潭市岳塘区双马镇金钢人防");
				senderAddress.put("district", "");//区
				senderAddress.put("province", centro.getProvince());
				sender.put("address", senderAddress);
				sender.put("mobile", user.getCode());
				sender.put("name", user.getShopName());
				//sender.put("phone", user.getCode());
			}
			//sender.put("phone", user.getCode());
			dataJson.put("sender", sender);
			
			JSONObject shippingOption=new JSONObject();
			shippingOption.put("code", "COD");
			
			JSONObject services=new JSONObject();
			
			JSONObject other=new JSONObject();
			other.put("value", order.getExpressCode());
			services.put("SVC-COD", other);
			shippingOption.put("title", order.getExpressCode());//右上角标题
			shippingOption.put("services", services);
			dataJson.put("shippingOption", shippingOption);
			dataJson.put("waybillCode", order.getExpressOrderno());
			
			//运单备注区域
			JSONObject remarkJson=new JSONObject();
			remarkJson.put("templateURL", customAreaURL);
			
			JSONObject data=new JSONObject();
			data.put("item_name", order.getItems());
			remarkJson.put("data", data);
		//	contents.put(dataJson);
			
			JSONObject jsonMap=new JSONObject();
			jsonMap.put("templateURL", waybillTemplateURL);
			jsonMap.put("signature", UUID.randomUUID());
			jsonMap.put("data", dataJson);

			contents.put(jsonMap);
			contents.put(remarkJson);
			doc.put("contents", contents);
			documents.put(doc);
			printIndex++;
			//写订单云打日志
			this.insertOrderPrintRecord(order,batchCode,type);
			if(StringUtils.isEmpty(type)){
				Map<String,Object> params=new HashMap<String, Object>();
				params.put("status", OrderStatusEnums.WMS_PRINT);
				params.put("printBatch", batchCode);
				params.put("id", order.getId());
				this.shipOrderRemote.updateShipOrder(params);
			}
		}

		resultMap.put("ret", "1");
		String result=documents.toString();
		resultMap.put("results", result);
		resultMap.put("description", description.toString());//打印的人员信息
		resultMap.put("orderIndex", orderIndex);//重复打印的数量
		resultMap.put("index", index);//此批单据数量
		resultMap.put("printIndex", printIndex);////打印的数量
		return resultMap;
	}
	
	/**
	 * modify 2017-09-15
	 * 面单云打日志
	 * @param order
	 */
	private void insertOrderPrintRecord(ShipOrder order,String batchCode,String type) {
		SystemOperatorRecord printRecord = new SystemOperatorRecord();
		printRecord.setIpaddr(order.getExpressOrderno());
		String accountId = String.valueOf(BaseResource.getCurrentUser().getId());
		printRecord.setOperator(Integer.valueOf(accountId));
		if(StringUtils.isNotEmpty(type) && type.equals("again")){
			printRecord.setOperatorModel(OperatorModel.TRADE_PRINT_AGAIN);
		}else{
			printRecord.setOperatorModel(OperatorModel.TRADE_PRINT);
		}
		printRecord.setStatus(batchCode);
		printRecord.setTime(new Date());
		String userId = String.valueOf(order.getCreateUser().getId());
		printRecord.setUser(Integer.parseInt(userId));
		String description = "store面单云打:"+String.valueOf(order.getId())+"|"+order.getExpressCompany()+"|"+order.getExpressOrderno();
		printRecord.setDescription(description);
		this.recordRemote.insert(printRecord);
	}

	/**
	 * 顺丰电子面单打印异步调用的实现
	 * @return
	 */
	@RequestMapping(value="sfAjax")
	public String sfAjax(ModelMap model){
		
		String idsObj=this.getString("ids");
		String cpCode=this.getString("cpCode");
		String type=this.getString("type","");
		model.put("ids", idsObj);
		
		model.put("cpCode", cpCode);
		
		model.put("batchCode", this.getbatchCode());
		model.put("type", type);
		return "waybill/sf_ajax";
	}
	
	
	/**
	 * 异步批量取菜鸟面单号,返回具体的打印数据
	 * 
	 */
	@RequestMapping(value = "getPrintAjax")
	@ResponseBody
	public Map<String, Object> getPrintAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		String idsObj =(String) request.getParameter("ids");
		String batchCode=(String)request.getParameter("batchCode");
		Date date=new Date();
		//String cp_code = (String)request.getParameter("cpCode");
		Map<String,Object> resultMap=new HashMap<String, Object>();
		/**
		 * 调用接口去取订单号，这里应该考虑一个问题。如果有一个订单取不到订单号，应该整批都不能进行打印。并且还需要给出具体的提示
		 * 修改为批量的取订单，用事务完成这一功能。
		 */
	
		if (idsObj != null) {
			String[] ids = idsObj.split(",");
			try{
				resultMap=this.wayBillRemote.getSFBillNo(ids,batchCode);
			}catch(Exception e ){
				/**
				 * 异常，描述出这批异常单据的具体情况。写出来
				 */
				Trade trade=this.tradeRemote.getTrade(Long.valueOf(ids[0])); 
				//Trade trade_end=this.tradeRemote.getTrade(Long.valueOf(ids[ids.length]));
				//resultMap.put("msg",trade.getReceiverName()+"-"+trade_end.getReceiverName()+"没有取得运单号!["+ids+"]");
				resultMap.put("code", 500);
				e.printStackTrace();
				
			}
		}
		if(resultMap!=null && String.valueOf(resultMap.get("code")).equals("200") ){
			/**
			 * 取完订单号，查询构建数据进行打印
			 */
			List<Map<String, String>> tradeList = new ArrayList<Map<String,String>>();
//			buildSfPrintData(idsObj.split(","), tradeList);
//			resultMap.put("data", tradeList);
			resultMap.put("code",200);
			logger.debug("面单打印:此批单据取号所花时间:"+(new Date().getTime()-date.getTime()));
		}else{
			resultMap.put("code", 500);
		}
		return resultMap;
	}
	
	/**
	 * 顺丰电子面单打印
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 * @throws IOException
	 */
	@RequestMapping(value = "sfpreview")
	public String sfpreview(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception, IOException {
		Object idsObj = request.getParameter("ids");
		String type=request.getParameter("type");
		String batchCode=String.valueOf(this.getbatchCode());
		if (idsObj != null) {
			String[] ids = ("" + idsObj).split(",");
			List<Map<String, String>> tradeList = new ArrayList<Map<String,String>>();
			JSONArray array = buildSfPrintData(ids, tradeList,batchCode,null);
			model.addAttribute("tradeList", tradeList);
			model.addAttribute("data", array);
			
		}
		if(type!=null && type.equals("print")){
			return "waybill/sf_new";
		}else{
			return "waybill/sf_isv";
		}
	}

	
	/**
	 * 取顺丰电子面单的打印数据
	 * @return
	 */
	@RequestMapping(value="getSfPrint")
	@ResponseBody
	public Map<String,Object> getSfPrint(){
		String idsObj =this.getString("ids");
		String type=this.getString("type");
		String batchCode=String.valueOf(this.getbatchCode());
		List<Map<String, String>> tradeList = new ArrayList<Map<String,String>>();
		Map<String,Object> resultMap=new HashMap<String, Object>();
		buildSfPrintData(idsObj.split(","), tradeList,batchCode,type);
		resultMap.put("data", tradeList);
		resultMap.put("code", "200");
		logger.error("顺丰面单打印："+resultMap);
		return resultMap;
	}


	private JSONArray buildSfPrintData(String[] ids, List<Map<String, String>> tradeList,String batchCode,String type) {
		JSONArray array=new JSONArray();
		for (int i = 0, size = ids.length; i < size; i++) {
//			Trade trade = tradeRemote.getTrade(new Long(ids[i]));
			ShipOrder sendShipOrder = shipOrderRemote.getShipOrder(new Long(ids[i]));
			Trade trade = tradeRemote.getTrade(sendShipOrder.getTradeId());
			if(sendShipOrder.getExpressOrderno()==null || sendShipOrder.getExpressOrderno().length()==0){
				//判断，如果订单号为空，则不输出打前台
				continue;
			}
			logger.error("buildSfPrintData:"+ids);
			System.err.println("buildSfPrintData:"+ids);
			String name;
			Centro centro = centroRemote.getCentroById(Integer.valueOf(""+trade.getCentro().getId()));
			User user = userRemote.getUser(sendShipOrder.getCreateUser().getId());
			String address = trade.getReceiverState() + "," + trade.getReceiverCity() + ","
					+ trade.getReceiverDistrict();
			String address1 = trade.getReceiverState() + trade.getReceiverCity() +
					trade.getReceiverDistrict()+trade.getReceiverAddress();
			if(user!=null && user.getId().intValue()==otherUser){
				name = trade.getReceiverName();
			}else{
			 name = trade.getReceiverName() + " "
					+ (trade.getBuyerNick() == null ? "" : trade.getBuyerNick());
			}
			String phone = trade.getReceiverMobile() + " "
					+ (trade.getReceiverPhone() == null ? "" : trade.getReceiverPhone());
			
			Map<String, String> map = new HashMap<String, String>();
			//JSONObject json=new JSONObject();
			
		    //上方运单号 条形码
//		   	    LODOP.ADD_PRINT_BARCODE(65,80,161,50,"128C","444 835 934 440");
			map.put("sf_orderno", sendShipOrder.getExpressOrderno());
			
			map.put("sf_selforderno", sendShipOrder.getOrderno());
		    //目的地
			map.put("sf_destcode", sendShipOrder.getSellerPhone());
			
			//DecimalFormat df=new DecimalFormat("######0.00");
			//map.put("weight", sendShipOrder.getTotalWeight()==null?"0":df.format(sendShipOrder.getTotalWeight())+"KG");
			
		    //收货地址
			map.put("sf_destaddress", address);
			 //详细地址
			map.put("sf_destdetailaddress", address1);
		    
		    //收货人  和 收货电话号码
			map.put("sf_destname", name+" "+phone);
			
			SimpleDateFormat smf  = new SimpleDateFormat("MM月dd日");
			String format = smf.format(new Date());
			
			map.put("sf_date", format);
		    //代收货款
		    //卡号
		    //运费
		    //费用合计
			//寄件信息
//			if(user!=null && user.getId().intValue()==otherUser){
//				String  caddres =  centro.getProvince()+centro.getCity()+centro.getArea();
//				String detailAddress  = centro.getSfAddress().replaceAll(caddres, "");
//				if(sendShipOrder.getItems().indexOf("AF")!=-1){
//					map.put("sf_sellername", " 傲风AutoFull   4000929959" );
//				}else{
//					map.put("sf_sellername", user.getShopName()+" "+(user.getCode()!=null ?user.getCode():"") );
//				}
//				map.put("sf_selleraddress", "");
//				map.put("sf_sellerdetailaddress", "北京");
//				  //目的地
//				map.put("sf_sellercode", sendShipOrder.getSellerMobile());
//			}else 
			if(StringUtils.isNotBlank(sendShipOrder.getSellerMessage()) && sendShipOrder.getSellerMessage().indexOf("环球捕手")!=-1){
				map.put("sf_sellername", "环球捕手4001603602" );
				map.put("sf_selleraddress", "");
				map.put("sf_sellerdetailaddress", "环球捕手");
				map.put("sf_sellercode", sendShipOrder.getSellerMobile());
			}else if(StringUtils.isNotBlank(sendShipOrder.getSellerMessage()) && (sendShipOrder.getSellerMessage()).indexOf("脉宝云店")!=-1){
				map.put("sf_sellername", "脉宝云店4001116789" );
				map.put("sf_selleraddress", "");
				map.put("sf_sellerdetailaddress", "脉宝云店");
				map.put("sf_sellercode", sendShipOrder.getSellerMobile());
			}else if ("74".equals(sendShipOrder.getCreateUser().getId())) {
				map.put("sf_sellername", user.getShopName() );
				map.put("sf_selleraddress", "");
				map.put("sf_sellerdetailaddress", "江西省赣州市信丰县赣南脐橙基地（多仓发货）");
				map.put("sf_sellercode", user.getCode());
			}else{
				String  caddres =  centro.getProvince()+centro.getCity()+centro.getArea();
				String detailAddress  = centro.getSfAddress().replaceAll(caddres, "");
				map.put("sf_sellername", user.getShopName()+" "+(user.getCode()!=null?user.getCode():""));
//				System.err.println("user:"+user.getShopName());
//				map.put("sf_sellername", user.getShopName());
				map.put("sf_selleraddress", caddres);
				map.put("sf_sellerdetailaddress", "湖南省湘潭市岳塘区双马镇金钢人防");
				  //目的地
				map.put("sf_sellercode", sendShipOrder.getSellerMobile());
//				System.err.println("sf_sellername:"+map.get("sf_sellername"));
			}
		    //下收方信息
			if (sendShipOrder.getCreateUser().getId()!=28) {
				map.put("sf_selleritems", sendShipOrder.getItems());
			}else {
				map.put("sf_selleritems", sendShipOrder.getItems()+"备注："+(sendShipOrder.getSellerMemo()!=null ?sendShipOrder.getSellerMemo():"" )+(sendShipOrder.getSellerMessage()!=null ?sendShipOrder.getSellerMessage():"" ));
			}
		    map.put("userId", user.getId().toString());
		    //原寄地CODE
			tradeList.add(map);
			array.put(map);
			
			if(StringUtils.isEmpty(type)){
				Map<String,Object> params=new HashMap<String, Object>();
				params.put("status", OrderStatusEnums.WMS_PRINT);
				params.put("printBatch", batchCode);
				params.put("id", sendShipOrder.getId());
				this.shipOrderRemote.updateShipOrder(params);
			}
			/**
			 * 写顺丰打印的操作日志
			 */
			SystemOperatorRecord record=new SystemOperatorRecord();
			record.setOperatorModel(OperatorModel.TRADE_PRINT);
			record.setDescription("CP|"+sendShipOrder.getId()+"|"+sendShipOrder.getPrintBatch()+"|"+sendShipOrder.getExpressCompany()+"|"+sendShipOrder.getExpressOrderno()+"|"+sendShipOrder.getReceiverMobile());
			record.setOperator(BaseResource.getCurrentUser().getId().intValue());
			record.setTime(new Date());
			record.setUser(user.getId().intValue());
			record.setStatus(sendShipOrder.getId().toString());
			this.recordRemote.insert(record);
		}
		return array;
	}
	
	/**
	 * 快递打印(预览打印)
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 * @throws IOException
	 */
	@RequestMapping(value = "preview")
	public String preview(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception, IOException {
		Object idsObj = request.getParameter("ids");
		String type=request.getParameter("type");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		boolean  tradeBatchFlag  =  false;
		
		if (idsObj != null) {
			String[] ids = ("" + idsObj).split(",");
			List<Map<String, String>> tradeList = null;
			for (int i = 0, size = ids.length; i < size; i++) {
				Trade trade = tradeRemote.getTrade(new Long(ids[i]));
				ShipOrder sendShipOrder = shipOrderRemote.getSendShipOrderByTradeId(trade.getId());
				if(i==0  && "SF".equals(sendShipOrder.getExpressCompany())){
					if(type==null || type.trim().length()==0){
						return "redirect:sfpreview?ids="+idsObj;
					}else{
						return "redirect:sfpreview?type=print&ids="+idsObj;
					}
				}
					
				
				if(!tradeBatchFlag && sendShipOrder.getTradeBatchId()!=null){
					tradeBatchFlag  = true;
				}
				Centro centro = centroRemote.getCentroById(Integer.valueOf("" + trade.getCentro().getId()));

				User user = userRemote.getUser(trade.getUser().getId());
				String address = trade.getReceiverState() + " " + trade.getReceiverCity() + " "
						+ trade.getReceiverDistrict() + " " + trade.getReceiverAddress();
				address=this.getHtml(address);
				
				String buyNick=(trade.getBuyerNick()==null?"":("("+trade.getBuyerNick()+")"));
				String name = trade.getReceiverName()+buyNick;
				
				String phone = trade.getReceiverMobile() + " "
						+ (trade.getReceiverPhone() == null ? "" : trade.getReceiverPhone());
				
				DecimalFormat df = new DecimalFormat("######0.00");   
				Double weight = (sendShipOrder.getTotalWeight()==null?0:sendShipOrder.getTotalWeight());
				
				String goodsInfoItems=sendShipOrder.getItems();
				if (sendShipOrder.getCreateUser().getId()==28) {
					String meno=(sendShipOrder.getSellerMemo()!=null ?sendShipOrder.getSellerMemo():"" )+(sendShipOrder.getSellerMessage()!=null ?sendShipOrder.getSellerMessage():"" );
					goodsInfoItems=goodsInfoItems+" 备注："+(meno.replaceAll(" ", "").replaceAll("[\\t\\n\\r]", ""));
				}
				if (i == 0) {
					model.addAttribute("user_id", userRemote.getUser(trade.getUser().getId()).getTbUserId());
					model.addAttribute("app_key", StoreConstants.rookie_appkey);

					model.addAttribute("cp_code", sendShipOrder.getExpressCompany()); // 选择快递末班公司
					//EMS 的EYB是经济快递，其它都为标准快递
					if(sendShipOrder.getExpressCompany().equals("EYB")){
						model.addAttribute("ali_waybill_product_type", "经济快递");// 单据类型
						model.addAttribute("ali_waybill_package_center_name", "6283   "+sendShipOrder.getSellerMobile());// 集散地名称
					}else{
						if(sendShipOrder.getExpressCode()!=null && sendShipOrder.getExpressCode().length()>0){
							if(sendShipOrder.getExpressCompany().equals("YUNDA") && sendShipOrder.getReceiverState()!=null && sendShipOrder.getReceiverState().indexOf("湖南")!=-1){
								model.addAttribute("ali_waybill_product_type", "06 省内");// 单据类型
							}else{
								model.addAttribute("ali_waybill_product_type", sendShipOrder.getExpressCode()+" 汽运");// 单据类型
							}
						}else{
							if (user.getId()==18) {
								model.addAttribute("ali_waybill_product_type", "凯盈汽运");// 单据类型
							}else{
								model.addAttribute("ali_waybill_product_type", "汽运");// 单据类型
							}			
						}
						model.addAttribute("ali_waybill_package_center_name", sendShipOrder.getSellerMobile());// 集散地名称
					}
					model.addAttribute("ali_waybill_package_center_code", sendShipOrder.getSellerPhone());//集散地代码（卖家电话）
					model.addAttribute("ali_waybill_waybill_code", sendShipOrder.getExpressOrderno());//快递代码（物流单号）
					model.addAttribute("ali_waybill_short_address", sendShipOrder.getOrderFlag());
					
					model.addAttribute("ali_waybill_package_center_code", sendShipOrder.getSellerPhone());
					model.addAttribute("ali_waybill_waybill_code", sendShipOrder.getExpressOrderno());
					// model.addAttribute("ali_waybill_cod_amount",
					// "FKFS=到付;");// 服务
					model.addAttribute("ali_waybill_consignee_name", name);//收件人姓名
					model.addAttribute("ali_waybill_consignee_phone", phone);//收件人手机
					model.addAttribute("ali_waybill_consignee_address", address.replaceAll("/", "").replaceAll("\"", "").replaceAll("\\\\","").replaceAll("\'", ""));// 收件人地址
					//model.addAttribute("ali_waybill_consignee_address", address);// 收件人地址
					if(StringUtils.isNotBlank(sendShipOrder.getSellerMessage()) && sendShipOrder.getSellerMessage().indexOf("环球捕手")!=-1){
						model.addAttribute("ali_waybill_send_name", "环球捕手4001603602");//发件人姓名（店铺名）
						model.addAttribute("ali_waybill_send_phone", " ");//发件人手机（客户编码）
						model.addAttribute("ali_waybill_shipping_address", "环球捕手");//发件地址（仓库地址）
					}else if(StringUtils.isNotBlank(sendShipOrder.getSellerMessage()) && (sendShipOrder.getSellerMessage()).indexOf("脉宝云店")!=-1){
						model.addAttribute("ali_waybill_send_name", "脉宝云店4001116789" );
						model.addAttribute("ali_waybill_send_phone", "");
						model.addAttribute("ali_waybill_shipping_address", "脉宝云店");
//						model.addAttribute("sf_sellercode", sendShipOrder.getSellerMobile());
					}else if("74".equals(sendShipOrder.getCreateUser().getId())){
						model.addAttribute("ali_waybill_send_name", user.getShopName());//发件人姓名（店铺名）
						model.addAttribute("ali_waybill_send_phone", user.getCode());//发件人手机（客户编码）
						model.addAttribute("ali_waybill_shipping_address", "江西省赣州市信丰县赣南脐橙基地（多仓发货） "+sdf.format(new Date())+"[允许先验收后签收]");//发件地址（仓库地址）
					}else{
						model.addAttribute("ali_waybill_send_name", user.getShopName());//发件人姓名（店铺名）
						model.addAttribute("ali_waybill_send_phone", user.getCode());//发件人手机（客户编码）
						model.addAttribute("ali_waybill_shipping_address", "湖南省湘潭市岳塘区双马镇金钢人防  "+sdf.format(new Date())+"[允许先验收后签收]");//发件地址（仓库地址）
					}
					/**
					 * 十记不需要打印重量
					 */
					Long userId = sendShipOrder.getCreateUser().getId();
					if (userId!=44&&userId!=14) {
						model.addAttribute("goodsInfos",df.format(weight)+"kg "+"["+(i+1)+"]"+goodsInfoItems +"    汽运");
					}else {
						model.addAttribute("goodsInfos","["+(i+1)+"]"+goodsInfoItems +"    汽运");
					}
					
				} else {
					if (tradeList == null) {
						tradeList = new ArrayList<Map<String, String>>();
					}
					Map<String, String> map = new HashMap<String, String>();
					//EMS 的EYB是经济快递，其它都为标准快递
					if(sendShipOrder.getExpressCompany().equals("EYB")){
						map.put("ali_waybill_product_type", "经济快递");// 单据类型
						map.put("ali_waybill_package_center_name", "6283   "+sendShipOrder.getSellerMobile());// 集散地名称
					}else{
						if(sendShipOrder.getExpressCode()!=null && sendShipOrder.getExpressCode().length()>0){
							if(sendShipOrder.getExpressCompany().equals("YUNDA") &&  sendShipOrder.getReceiverState()!=null && sendShipOrder.getReceiverState().indexOf("湖南")!=-1){
								map.put("ali_waybill_product_type","06 省内");// 单据类型
							}else{
								map.put("ali_waybill_product_type", sendShipOrder.getExpressCode()+" 汽运");// 单据类型
							}
						}else{
							if (user.getId()==18) {
								model.addAttribute("ali_waybill_product_type", "凯盈汽运");// 单据类型
							}else{
								model.addAttribute("ali_waybill_product_type", "汽运");// 单据类型
							}		
						}
						map.put("ali_waybill_package_center_name", sendShipOrder.getSellerMobile());// 集散地名称
					}
					
					//map.put("ali_waybill_product_type", "标准快递");// 单据类型
					map.put("ali_waybill_short_address", sendShipOrder.getOrderFlag());
				//	map.put("ali_waybill_package_center_name", sendShipOrder.getSellerMobile());// 集散地名称
					map.put("ali_waybill_package_center_code", sendShipOrder.getSellerPhone());
					map.put("ali_waybill_waybill_code", "" + sendShipOrder.getExpressOrderno());
					// map.put("ali_waybill_cod_amount", "FKFS=到付;");// 服务
					map.put("ali_waybill_consignee_name", name);
					map.put("ali_waybill_consignee_phone", phone);
					map.put("ali_waybill_consignee_address", address.replaceAll("/", "").replaceAll("\"", "").replaceAll("\\\\","").replaceAll("\'", ""));// 收件人地址
					//map.put("ali_waybill_consignee_address", address);// 收件人地址
					if(StringUtils.isNotBlank(sendShipOrder.getSellerMessage()) && sendShipOrder.getSellerMessage().indexOf("环球捕手")!=-1){
						map.put("ali_waybill_send_name", "环球捕手4001603602");//发件人姓名（店铺名）
						map.put("ali_waybill_send_phone", " ");//发件人手机（客户编码）
						map.put("ali_waybill_shipping_address", "环球捕手");//发件地址（仓库地址）
					}else if(StringUtils.isNotBlank(sendShipOrder.getSellerMessage()) && (sendShipOrder.getSellerMessage()).indexOf("脉宝云店")!=-1){
						map.put("ali_waybill_send_name", "脉宝云店4001116789" );
						map.put("ali_waybill_send_phone", "");
						map.put("ali_waybill_shipping_address", "脉宝云店");
					}else if("74".equals(sendShipOrder.getCreateUser().getId())){
						model.addAttribute("ali_waybill_send_name", user.getShopName());//发件人姓名（店铺名）
						model.addAttribute("ali_waybill_send_phone", user.getCode());//发件人手机（客户编码）
						model.addAttribute("ali_waybill_shipping_address", "江西省赣州市信丰县赣南脐橙基地（多仓发货） "+sdf.format(new Date())+"[允许先验收后签收]");//发件地址（仓库地址）
					}else{
						map.put("ali_waybill_send_name", user.getShopName());//发件人姓名（店铺名）
						map.put("ali_waybill_send_phone", user.getCode());//发件人手机（客户编码）
						map.put("ali_waybill_shipping_address", "湖南省湘潭市岳塘区双马镇金钢人防  "+sdf.format(new Date())+"[允许先验收后签收]");//发件地址（仓库地址）
					}
					/**
					 * 十记不需要打印重量
					 */
					Long userId = sendShipOrder.getCreateUser().getId();
					if (userId!=44&&userId!=14) {
						map.put("goodsInfos",df.format(weight)+"kg "+"["+(i+1)+"]"+goodsInfoItems +"    汽运");
					}else {
						map.put("goodsInfos","["+(i+1)+"]"+goodsInfoItems +"    汽运");
					}
					
					tradeList.add(map);
				}

				/**
				 * 添加打印日志
				 */
				SystemOperatorRecord record=new SystemOperatorRecord();
				record.setOperatorModel(OperatorModel.TRADE_PRINT);
				record.setDescription("CP"+sendShipOrder.getId()+"|"+sendShipOrder.getPrintBatch()+"|"+sendShipOrder.getExpressCompany()+"|"+sendShipOrder.getExpressOrderno()+"|"+sendShipOrder.getReceiverMobile());
				record.setOperator(BaseResource.getCurrentUser().getId().intValue());
				record.setTime(new Date());
				record.setUser(user.getId().intValue());
				record.setStatus(sendShipOrder.getId().toString());
				this.recordRemote.insert(record);
				
			}

			if (tradeList != null) {
				if(tradeBatchFlag){ //更新 批次
					
				}
				model.addAttribute("tradeList", tradeList);
			}
		}
		if(type==null || type.trim().length()==0){
			return "waybill/yunda_isvPreview";
		}else{
			return "waybill/cainiao_new";
		}
		
	}

	/**
	 * 获取菜鸟面单号(预览打印)
	 * Map key tradeId 订单ID orderNo 面单号
	 */
	@RequestMapping(value = "ajax/getPrint")
	@ResponseBody
	public Map<String, Object> getPrint(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Object idsObj = request.getParameter("ids");
		String cp_code = "" + request.getParameter("cp_code");
		//取批次号
		long batch=this.getbatchCode();
		List<Map<String, String>> retList = new ArrayList<Map<String, String>>();
		if (idsObj != null) {
			String[] ids = ("" + idsObj).split(",");
			for (int i = 0, size = ids.length; i < size; i++) {
				Trade trade = tradeRemote.getTrade(new Long(ids[i]));
				if (!"SF".equals(cp_code)) {
					/**
					 * 2017-03-09
					 * 菜鸟打印加入打印批次号的更新 batch
					 */
					Map<String, String> getMap = wayBillRemote.billGet(trade, cp_code,String.valueOf(batch));
					if(!"getMap".equals("200")){
						retList.add(getMap);
					}
				} else {
					try{
						retList.add(wayBillRemote.billSfGet(trade,String.valueOf(batch)));
					}catch(Exception e){
						e.printStackTrace();
						logger.debug(e.getMessage());
					}
				}
			}
		}
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("retList", retList);
		logger.debug("取订单号:"+retList);
		return retMap;
	}

	/**
	 * 取消电子面单
	 */
	@RequestMapping(value = "ajax/cancel")
	@ResponseBody
	public Map<String, Object> cancel(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception, IOException {
		Object idsObj = request.getParameter("ids");
		List<Map<String, String>> retList = new ArrayList<Map<String, String>>();
		if (idsObj != null) {
			String[] ids = ("" + idsObj).split(",");
			for (int i = 0, size = ids.length; i < size; i++) {
				Trade trade = tradeRemote.getTrade(new Long(ids[i]));
				retList.add(wayBillRemote.billCancel(trade));
			}
		}
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("retList", retList);
		System.err.println("订单取消:"+retList);
		return retMap;
	}
	/**
	 * 查询快递单状态
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="queryDetail")
	public Map<String,Object> queryDetail(HttpServletRequest request,ModelMap model){
		String code=request.getParameter("code");
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.putAll(wayBillRemote.queryDetail(code));
		resultMap.put("ret", 1);
		return resultMap;
	}
	
	@RequestMapping(value="getPrintbatchCode")
	@ResponseBody
	public   Map<String,Object> getPrintbatchCode(){
		Map<String,Object> resultMap=new HashMap<String, Object>();
		long batchCode=getbatchCode();				
		resultMap.put("batchCode", batchCode);
		resultMap.put("code", 200);
		return resultMap;
	}
	
	private synchronized long getbatchCode(){
		long batchCode=new Date().getTime();
		return batchCode;
	}
	/**
	 * 格式化地址
	 * @param str
	 * @return
	 */
	public String getHtml(String str){
		if(str!=null && str.length()>0){
			str=str.replaceAll("(\r\n|\r|\n|\n\r)", "");
			if(str.indexOf("\"")>0){
				str=str.replaceAll("\"", "");
			}
			if(str.indexOf("'")>0){
				str=str.replaceAll("'", "");
			}
			return str;
		}
		return null;
	}
}
