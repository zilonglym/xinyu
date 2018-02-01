package com.graby.store.admin.web.item;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.graby.store.admin.util.BaseResource;
import com.graby.store.admin.web.BaseController;
import com.graby.store.entity.Item;
import com.graby.store.entity.User;
import com.graby.store.remote.InventoryRemote;
import com.graby.store.remote.ItemRemote;
import com.graby.store.remote.UserRemote;

/**
 * 商品控制类
 * 
 * @author yangmin
 *
 */
@Controller
@RequestMapping(value = "item")
public class ItemController extends BaseController {
	@Autowired
	private ItemRemote itemRemote;
	@Autowired
	private UserRemote userRemote;
	
	
	@Autowired
	private InventoryRemote  inventoryRemote;

	/**
	 * 商品管理页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "list")
	public String toItemList(ModelMap model) {
		List<User> userList = this.userRemote.findUsers();
		model.put("users", userList);
		return "/storage/itemList";
	}

	/**
	 * 商品列表数据查询
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "listData")
	@ResponseBody
	public Map<String, Object> itemlistData(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "100") int rows, HttpServletRequest request) {
		if (rows == 10) {
			rows = 100;
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		String searchText = request.getParameter("searchText");
		String userId = request.getParameter("userId");
		params.put("searchText", searchText);
		params.put("userId", userId);
		Page<Item> itemPage = this.itemRemote.getItemsByPage(page, rows, params);
		resultMap.put("total", itemPage.getTotalElements());
		resultMap.put("page", page);
		resultMap.put("rows", this.buildItemList(itemPage.getContent()));
		return resultMap;
	}

	/**
	 * @modify 2017-09-05
	 * @author fufangjue
	 * */
	@RequestMapping(value = "generateBarCode")
	@ResponseBody
	public Map<String, Object> generateBarCode() {
		String id = this.getString("id");
		Item item = this.itemRemote.getItem(Long.valueOf(id));
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (item != null && item.getBarCode() != null && item.getBarCode().length()>0) {
			resultMap.put("ret", 0);
			resultMap.put("msg", "");

		} else {
			NumberFormat nf = NumberFormat.getInstance();
			// 设置是否使用分组
			nf.setGroupingUsed(false);
			// 设置最大整数位数
			nf.setMaximumIntegerDigits(4);
			// 设置最小整数位数
			nf.setMinimumIntegerDigits(3);
			String barCode = "29";
			barCode = barCode + nf.format(item.getUserid());
			barCode = barCode + nf.format(item.getId());
			// 66|18|0000|
			//int seq = this.itemRemote.findPageUserItemsCount(item.getUserid());
			//seq++;
			Random rand=new Random();
			int seq=rand.nextInt(999);
			barCode = barCode + nf.format(seq);
			//调用EAN13校验码生成方法
			String newBarCode = this.EAN13Check(barCode);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("itemId", id);
			params.put("barCode", newBarCode);
			this.itemRemote.updateBarCode(params);
			resultMap.put("ret", 1);
		}
		return resultMap;
	}

	/**
	 * 
	 * @modify 2017-09-05
	 * @author fufangjue
	 * 
	 * EAN13根据前12位随机数字生成第13位校验码
	 * 重新跟前12位拼接成13位EAN13的条码
	 * @param string
	 * @return string
	 * */
	private String EAN13Check(String code) {
		int c1=0;int c2=0; 
		for(int i=0;i<12;i+=2){ 
			char c=code.charAt(i);//字符串code中第i个位置上的字符
			int n=c-'0'; 
			c1+=n;//累加奇数位的数字和
			} 
		for(int i=1;i<12;i+=2){ 
			char c=code.charAt(i);//字符串code中第i个位置上的字符
			int n=c-'0'; 
			c2+=n;//累加偶数位的数字和
			} 
		int cc=c1+c2*3;
		int check=cc%10; 
		check=(10-cc%10)%10; 
		code = code + check;
		return code;
	}

	/**
	 * 重构组建Item数据
	 * 
	 * @param list
	 * @return
	 */
	private List<Map<String, Object>> buildItemList(List<Item> list) {
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < list.size(); i++) {
			Item item = list.get(i);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", item.getId());
			map.put("itemCode", item.getCode());
			map.put("itemName", item.getTitle());
			map.put("sku", item.getSku());
			map.put("barCode", item.getBarCode());
			map.put("itemType", item.getItemType());
			map.put("weight", item.getWeight());
			map.put("packageWeight", item.getPackageWeight());
			User user = this.userRemote.getUser(item.getUserid());
			map.put("userName", user.getShopName());
			map.put("operator", item.getId());
			resultList.add(map);
		}
		return resultList;
	}

	/**
	 * 转向至商品条码扫描页面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "barCodef7Page")
	public String toAddbarCode(HttpServletRequest request, ModelMap model) {
		String id = request.getParameter("itemId");
		Item item = this.itemRemote.getItem(Long.valueOf(id));
		model.put("item", item);
		return "/storage/itemf7barCode";
	}

	/**
	 * 修改商品二维码
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "updateBarCode")
	@ResponseBody
	public Map<String, Object> updateBarCode(HttpServletRequest request) {
		String itemId = request.getParameter("itemId");
		String barCode = request.getParameter("barCode");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("itemId", itemId);
		params.put("barCode", barCode);
		this.itemRemote.updateBarCode(params);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("ret", 1);
		return resultMap;
	}

	/**
	 * 跳转至商品打包重量修改页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "tof7PackageWeight")
	public String topackageWeight(HttpServletRequest request, ModelMap model) {
		String id = request.getParameter("itemId");
		Item item = this.itemRemote.getItem(Long.valueOf(id));
		model.put("item", item);
		return "/storage/itemf7packageWeight";
	}

	/**
	 * 修改商品的发货重量
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "submitpackageWeight")
	@ResponseBody
	public Map<String, Object> updatePackageWeight(HttpServletRequest request) {
		String itemId = request.getParameter("itemId");
		String packageWeight = request.getParameter("packageWeight");
		String weight=this.getString("weight");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("itemId", itemId);
		params.put("packageWeight", packageWeight);
		params.put("weight", weight);
		this.itemRemote.updatePackageWeight(params);

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("ret", 1);
		return resultMap;
	}

	/**
	 * 跳转至商品名称修改
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "tof7ItemTitle")
	public String toItemTitle(HttpServletRequest request, ModelMap model) {
		String id = request.getParameter("itemId");
		Item item = this.itemRemote.getItem(Long.valueOf(id));
		model.put("item", item);
		return "/storage/itemf7Title";
	}
	
	
	/**
	 * 修改商品的名称
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "submitItemTitle")
	@ResponseBody
	public Map<String, Object> submitItemTitle(HttpServletRequest request) {
		System.err.println("update item Title");

		System.out.println("update item Title");

		String itemId = request.getParameter("itemId");
		String itemCode = request.getParameter("itemTitle");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("itemId", itemId);
		params.put("itemTitle", itemCode);

		this.itemRemote.updateItemTitle(params);

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("ret", 1);
		return resultMap;
	}

	/**
	 * 跳转至商品库存修改
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "tof7ItemCount")
	public String toItemCount(HttpServletRequest request, ModelMap model) {
		String id = request.getParameter("itemId");
		Item item = this.itemRemote.getItem(Long.valueOf(id));
		model.put("item", item);
		return "/storage/itemf7Count";
	}
	
	/**
	 * 修改商品的名称
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "submitItemCount")
	@ResponseBody
	public Map<String, Object> submitItemCount(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		String itemId = request.getParameter("itemId");
		String itemCount = request.getParameter("itemCount");
		Long count  = 0L;
		try {
			 count  =   Long.valueOf(itemCount.trim());
			
		} catch (Exception e) {
			resultMap.put("ret", 3);
			resultMap.put("msg", "数量转换异常");
			return resultMap;
		}
		System.err.println("update item Count");
		System.err.println("itemId："+itemId);
		System.err.println("itemCount："+itemCount);
		int operId  = BaseResource.getCurrentCentroId();
		try {
			Item item = itemRemote.getItem(Long.valueOf(itemId));
			inventoryRemote.adminAddInventory(1L, item.getUserid(), Long.valueOf(itemId), count, "管理员手动添加库存", ""+operId);
		} catch (Exception e) {
			resultMap.put("ret", 3);
			resultMap.put("msg", e.getMessage());
			return resultMap;
		}
		resultMap.put("ret", 1);
		return resultMap;
	}
	
	
	/**
	 * 跳转至商品打包重量修改页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "tof7ItemCode")
	public String toItemCode(HttpServletRequest request, ModelMap model) {
		String id = request.getParameter("itemId");
		Item item = this.itemRemote.getItem(Long.valueOf(id));
		model.put("item", item);
		return "/storage/itemf7Code";
	}

	/**
	 * 修改商品的发货重量
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "submitItemCode")
	@ResponseBody
	public Map<String, Object> updateItemCode(HttpServletRequest request) {
		String itemId = request.getParameter("itemId");
		String itemCode = request.getParameter("itemCode");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("itemId", itemId);
		params.put("itemCode", itemCode);

		this.itemRemote.updateItemCode(params);

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("ret", 1);
		return resultMap;
	}

	/**
	 * 批量导入商品数据页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "toimportItem")
	public String importItemList(ModelMap model) {
		List<User> userList = this.userRemote.findUsers();
		model.put("userList", userList);
		return "storage/importitem";
	}

	@RequestMapping(value = "submitImportItem", method = RequestMethod.POST)
	public String importItem(@RequestParam(value = "file", required = true) MultipartFile file,
			HttpServletRequest request, ModelMap model) throws FileNotFoundException, IOException {
		String id = request.getParameter("id");
		// String id=this.getString("id");
		Workbook wb = new HSSFWorkbook(file.getInputStream());
		Sheet sheet = wb.getSheetAt(0);
		int r = sheet.getLastRowNum();
		List<Item> itemList = new ArrayList<Item>();
		for (int i = 1; i <= r; i++) {
			System.out.println("row:" + i);
			System.err.println("row:" + i);

			Item item = new Item();
			HSSFRow row = (HSSFRow) sheet.getRow(i);
			String title = row.getCell(1).getStringCellValue();
			String code = row.getCell(2).getStringCellValue();
			String weight = String.valueOf(row.getCell(3).getNumericCellValue());
			if (row.getCell(4) != null) {
				String barCode = row.getCell(4).getStringCellValue();
				item.setBarCode(barCode);
			}
			if (row.getCell(5) != null) {
				String sku = row.getCell(5).getStringCellValue();
				item.setSku(sku);
			}
			item.setTitle(title + " " + code);
			item.setCode(code);
			item.setWeight(Double.valueOf(weight));
			item.setBrandCode("import");// 批量EXCEL导入
			////
			item.setUserid(Long.valueOf(id));
			/////
			itemList.add(item);
		}
		this.itemRemote.saveItemList(itemList);
		return null;
	}
	
	
	/**
	 * 修改商品类型页面
	 * @param model
	 * @param request
	 * @return string
	 * */
	@RequestMapping(value="tof7ItemType")
	public String editItemType(ModelMap model,HttpServletRequest request){
		String id = request.getParameter("id");
		Item item = this.itemRemote.getItem(Long.valueOf(id));
		model.put("item", item);
		return "/storage/itemf7Type";
	}
	
	/**
	 * 修改商品类型保存
	 * @param model
	 * @param request
	 * @return string
	 * */
	@RequestMapping(value="submitItemType")
	@ResponseBody
	public Map<String, Object> saveItemType(ModelMap model,HttpServletRequest request){
		Map<String, Object> retMap = new HashMap<String,Object>();
		String id = request.getParameter("id");
		String itemType = request.getParameter("itemType");
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("id", id);
		params.put("type", itemType);
//		System.err.println(params);
		try {
			this.itemRemote.updateItemType(params);
			retMap.put("ret", "success");
			retMap.put("msg", "商品类型修改成功！");
		} catch (Exception e) {
			e.printStackTrace();
			retMap.put("ret", "fail");
			retMap.put("msg", "商品类型修改失败！"+e.getMessage());			
		}
		return retMap;
	}

}
