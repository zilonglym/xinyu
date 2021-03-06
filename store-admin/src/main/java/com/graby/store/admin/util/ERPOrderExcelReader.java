package com.graby.store.admin.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream.PutField;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.web.bind.annotation.RequestMapping;

import com.graby.store.util.StringUtils;


public class ERPOrderExcelReader {
	private Sheet sheet;
	
	public ERPOrderExcelReader(InputStream in) throws FileNotFoundException, IOException {
		try {
			Workbook wb = new HSSFWorkbook(in);
			sheet = wb.getSheetAt(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public ERPOrderExcelReader(File file) throws FileNotFoundException, IOException {
		Workbook wb = new HSSFWorkbook(new FileInputStream(file));
		sheet = wb.getSheetAt(0);
	}
	
	private String  cellValueFormatStr(Cell cell){
		String cellValue = "";
		if (null != cell) {
			switch (cell.getCellType()) {
			case HSSFCell.CELL_TYPE_NUMERIC: // 数字
				DecimalFormat df = new DecimalFormat("0");
				cellValue = df.format(cell.getNumericCellValue()).trim();
				break;
			case HSSFCell.CELL_TYPE_STRING: // 字符串
				cellValue  =  cell.getStringCellValue().trim();
				break;
			case HSSFCell.CELL_TYPE_BLANK: // 空值
				cellValue  = "";
				break;
			default:
				cellValue  = "";
				break;
			}
		}
		System.out.println("cellValue:"+cellValue);
		return cellValue;
		
	}
	
	/**
	 * 富润系统 导出 解析结构
	 * @param sheet
	 * @return
	 */
	private Map<String,Object> readWangQuBao(Sheet sheet) {
		Map<String, Object> retMap = new HashMap<String, Object>(); 
		List<Map<String,Object>> order = new ArrayList<Map<String,Object>>();
		int totalRownums = sheet.getPhysicalNumberOfRows();
		System.out.println("totalRownums:"+totalRownums);
		for (int i = 1; i < totalRownums; i++) {
			Map<String,Object>  map =  new HashMap<String,Object>();
			
			
			Row row = sheet.getRow(i);
			if(row==null){
				break;
			}
			// 1.网上订单号
//			Cell tbNumberId = row.getCell(0);
//			String tbNumberIdValue = tbNumberId.getStringCellValue();
			map.put("tbNumber",ExcelReaderUtil.cellValueFormatStr(row.getCell(0)));
			// 2.物流公司	  //快递公司
			
//			Cell expressId = row.getCell(1);
//			String expressIdValue = expressId.getStringCellValue();
			map.put("express",ExcelReaderUtil.cellValueFormatStr(row.getCell(1)));
			// 3.物流单号	
			
//			Cell expressOrdernoId = row.getCell(2);
			String expressOrdernoIdValue = ExcelReaderUtil.cellValueFormatStr(row.getCell(2));
			map.put("expressOrderno",expressOrdernoIdValue);
			// 4.收货人	
			
//			Cell userId = row.getCell(3);
//			String userIdValue = userId.getStringCellValue();
			map.put("userName",ExcelReaderUtil.cellValueFormatStr(row.getCell(3)));
			// 5.收货人手机
			
//			Cell phoneId = row.getCell(4);
//			String phoneIdValue = phoneId.getStringCellValue();
			map.put("userPhone",ExcelReaderUtil.cellValueFormatStr(row.getCell(4)));
			// 6.收货地址
//			Cell addressId = row.getCell(5);
			String addressIdValue = cellValueFormatStr(row.getCell(5));
			
			try {
				
			String[] addrs = addressIdValue.split(" ");
			System.out.println("row:"+i);
			if(addrs.length ==  1  &&  addressIdValue.indexOf("省")!=-1  && addressIdValue.indexOf("市")!=-1 ){
				map.put("state",addressIdValue.substring(0,addressIdValue.indexOf("省")+1));
				map.put("city",addressIdValue.substring(addressIdValue.indexOf("省")+1,addressIdValue.indexOf("市")+1));
				map.put("district","");
				map.put("address",addressIdValue.substring(addressIdValue.indexOf("市")+1));
			}else if (addrs.length ==  1  &&  addressIdValue.indexOf("自治区")!=-1  && addressIdValue.indexOf("市")!=-1){
				map.put("state",addressIdValue.substring(0,addressIdValue.indexOf("自治区")+3));
				map.put("city",addressIdValue.substring(addressIdValue.indexOf("自治区")+3,addressIdValue.indexOf("市")+1));
				map.put("district","");
				map.put("address",addressIdValue.substring(addressIdValue.indexOf("市")+1));
			}else if (addrs.length ==  1  &&  addressIdValue.indexOf("自治州")!=-1  && addressIdValue.indexOf("省")!=-1){
				map.put("state",addressIdValue.substring(0,addressIdValue.indexOf("省")+1));
				map.put("city",addressIdValue.substring(addressIdValue.indexOf("省")+1,addressIdValue.indexOf("自治州")+3));
				map.put("district","");
				map.put("address",addressIdValue.substring(addressIdValue.indexOf("自治州")+3));
			}else  if(addrs.length == 3){
				map.put("state",addrs[0]);
				map.put("city",addrs[1]);
				map.put("district","");
				map.put("address",addrs[2]);
			}else  if(addrs.length == 2){
				String startStr = addrs[0];
				map.put("state",startStr.substring(0,startStr.indexOf("省")+1));
				map.put("city",startStr.substring(startStr.indexOf("省")+1,startStr.indexOf("市")+1));
				map.put("district","");
				map.put("address",startStr.substring(addressIdValue.indexOf("市")+1)+addrs[1]);
			}else{
				map.put("state",addrs[0]);
				map.put("city",addrs[1]);
				map.put("district",addrs[2]);
				map.put("address",addrs[3]);
			}
			map.put("addressDetail",addressIdValue);
			} catch (Exception e) {
				String msg = "第"+(i+1)+"行地址异常！";
				retMap.put("msg", msg);
				retMap.put("code", "500");
				return retMap;
						
			}
			//7.本地商品名称	
//			Cell itemId = row.getCell(6);	   
			String itemStr = cellValueFormatStr(row.getCell(6));
			for(int j = i+1 ;  j < totalRownums;j++ ){
				Row row2 = sheet.getRow(j);
//				Cell expressOrdernoId2 = row2.getCell(2);
				String expressOrdernoIdValue2 = cellValueFormatStr(row2.getCell(2));
				if(!expressOrdernoIdValue.equals(expressOrdernoIdValue2)){
					break;
				}
				i =  i+1;
				Cell itemId2 = row2.getCell(6);	 
				itemStr  =   itemStr+";"  + itemId2.getStringCellValue();
				
			}
			System.out.println(itemStr);
			String[] itemCodes = itemStr.split(";");
			map.put("items",itemStr);
			map.put("itemCode",itemCodes);
			
//			Cell itemCountId = row.getCell(8);
			String itemCountIdValue =  cellValueFormatStr(row.getCell(8));
			if( "".equals(itemCountIdValue)  ||  "null".equals(itemCountIdValue)){
				String[] itemCounts  = new String[itemCodes.length];
				for(int j =0, jsize  = itemCodes.length ; j<jsize ;  j++){
					itemCounts[j] = "1";
				}
				map.put("itemCount",itemCounts);
			}else{
				String[] itemCounts  = new String[itemCodes.length];
				for(int j =0, jsize  = itemCodes.length ; j<jsize ;  j++){
					itemCounts[j] = itemCountIdValue;
				}
				map.put("itemCount",itemCounts);
			}
			//8.买家昵称
//			Cell buyerNameId = row.getCell(7);
			String buyerNameIdValue = cellValueFormatStr(row.getCell(7));
			map.put("buyerName",buyerNameIdValue);
			
			order.add(map);
		}
		retMap.put("list", order);
		retMap.put("code", "200");
		return retMap;
	}
	
	
	
	/**
	 * 富润系统 导出 解析结构
	 * @param sheet
	 * @return
	 * @throws Exception 
	 */
	private Map<String,Object> readFunRun(Sheet sheet){
		
		Map<String,Object>  retMap = new HashMap<String, Object>();  
		
		List<Map<String,Object>> order = new ArrayList<Map<String,Object>>();
		int totalRownums = sheet.getPhysicalNumberOfRows();
		System.out.println("totalRownums:"+totalRownums);
		for (int i = 1; i < totalRownums; i++) {
			System.out.println("row:"+(i+1));
			System.err.println("row:"+(i+1));
			Map<String,Object>  map =  new HashMap<String,Object>();
			Row row = sheet.getRow(i);
			System.err.println("row:"+i+":"+row);
			if(this.isRowEmpty(row)){
				break;
			}
			// 1.网上订单号
			
//			Cell tbNumberId = row.getCell(0);
//			String tbNumberIdValue = tbNumberId.getStringCellValue();
			map.put("tbNumber",cellValueFormatStr(row.getCell(0)));
			
			// 2.物流公司	  //快递公司
//			Cell expressId = row.getCell(1);
//			String expressIdValue = expressId.getStringCellValue();
			map.put("express",cellValueFormatStr(row.getCell(1)));
			
			// 3.物流单号	
//			Cell expressOrdernoId = row.getCell(2);
			String expressOrdernoIdValue = cellValueFormatStr(row.getCell(2));
			map.put("expressOrderno",expressOrdernoIdValue);
			// 4.收货人	
//			Cell userId = row.getCell(3);
//			String userIdValue = userId.getStringCellValue();
			String userName = cellValueFormatStr(row.getCell(3));
			map.put("userName",ExcelReaderUtil.filterEmoji(userName));
			// 5.收货人手机
			
//			Cell phoneId = row.getCell(4);
//			String phoneIdValue = phoneId.getStringCellValue();
			map.put("userPhone",cellValueFormatStr(row.getCell(4)));
			
			// 6.收货地址
//			Cell addressId = row.getCell(5);
			String addressIdValue =cellValueFormatStr(row.getCell(5));	
			
			try {
			String[] addrs  = addressIdValue.split(" ");
			String[] addrs1  = addressIdValue.split("\\|");
			System.out.println("addressIdValue:"+addressIdValue);
			System.out.println("addrs.length:"+addrs.length);
			System.out.println("addrs1.length:"+addrs1.length);
			if(addrs.length ==  1  &&  addressIdValue.indexOf("省")!=-1  && addressIdValue.indexOf("市")!=-1&& addrs1.length<=3 ){
				map.put("state",addressIdValue.substring(0,addressIdValue.indexOf("省")+1));
				map.put("city",addressIdValue.substring(addressIdValue.indexOf("省")+1,addressIdValue.indexOf("市")+1));
				map.put("district","");
				map.put("address",addressIdValue.substring(addressIdValue.indexOf("市")+1));
			}else if(addrs1.length ==5){
				map.put("state",addrs1[1]);
				map.put("city",addrs1[2]);
				map.put("district",addrs1[3]);
				map.put("address",addrs1[4]);
			}else if(addrs1.length ==4){
				map.put("state",addrs1[1]);
				map.put("city",addrs1[2]);
				map.put("district","");
				map.put("address",addrs1[3]);
			}else  if(addrs.length == 4){
				map.put("state",addrs[1]);
				map.put("city",addrs[2]);
				map.put("district","");
				map.put("address",addrs[3]);
			}
			else{
				map.put("state", addrs[1]);
				map.put("city", addrs[2]);
				map.put("district", addrs[3]);
//				map.put("address", addrs[4]);
				String addressStr =  "";
				for(int c = 4,csize =addrs.length;c<csize;c++ ){
					addressStr   = addressStr  +addrs[c];
				}
				map.put("address", addressStr);
			}
			
			map.put("addressDetail",ExcelReaderUtil.filterEmoji(addressIdValue));
			} catch (Exception e) {
				String msg = "第"+(i+1)+"行地址异常！";
				retMap.put("code", "500");
				retMap.put("msg", msg);
			    return retMap;
			}
			//7.本地商品名称	  解析
//			Cell itemId = row.getCell(6);
			String itemStr = cellValueFormatStr(row.getCell(6));
			map.put("items",itemStr);
			String[] itemsStr  =   itemStr.split(";");
			int  itemSize  =  itemsStr.length  ;
			String[] items  =   new String[itemSize] ;
			String[] itemsCount =   new String[itemSize] ;
			for(int  j  = 0; j<itemSize   ;  j++){
				String  itemStr1   =   itemsStr[j] ;
				String  itemCode =  "";
				String  itemCount =  "";
				if(itemStr1.lastIndexOf('(')!=-1  && itemStr1.lastIndexOf(')')!=-1){
					itemCode = itemStr1.substring(0,itemStr1.lastIndexOf('('));
					itemCount=  itemStr1.substring(itemStr1.lastIndexOf('(')+1,itemStr1.lastIndexOf(')'));
				}else{
					itemCode  =  itemStr1  ;
				}
				System.err.println("erpItemCount:"+itemCount);
				itemCount  = StringUtils.startNum(itemCount);
				try {
					Long itemCountTemp =Long.valueOf(itemCount);
				} catch (Exception e) {
//					e.printStackTrace();
					itemCount  =  "1";
				}
				items[j]  =  itemCode  ;
				itemsCount[j]  =  itemCount  ;
			}
			map.put("itemCode", items);
			map.put("itemCount", itemsCount);
			
			//8.买家昵称
//			Cell buyerNameId = row.getCell(7);
//			String buyerNameIdValue = buyerNameId.getStringCellValue();
			
			String buyerName = cellValueFormatStr(row.getCell(7));
			map.put("buyerName",ExcelReaderUtil.filterEmoji(buyerName));
			
			
			Cell barCodeId = row.getCell(9);
			if(barCodeId!=null  ){
				String barCodeIdValue = barCodeId.getStringCellValue();
				System.out.println("barCodeIdValue:"+barCodeIdValue);
				String[] barCodesStr = barCodeIdValue.split(";"); 
				int  codeSize  =   barCodesStr.length;
				String[]  barCodes   =  new String[codeSize];
				String[]  barCodesCount   =  new String[codeSize];
				for(int  j  = 0  ;j<codeSize  ; j++){
					String  barCodeStr  =  barCodesStr[j];
					String barCode  = barCodeStr.substring(0,barCodeStr.lastIndexOf('('));
					String barCodeCount = barCodeStr.substring(barCodeStr.lastIndexOf('(')+1,barCodeStr.lastIndexOf(')'));
					try {
						Long barCodeCountTemp =Long.valueOf(barCodeCount);
					} catch (Exception e) {
						barCodeCount  =  "1";
					}
					barCodes[j]  =  barCode  ;
					barCodesCount[j]  =  barCodeCount  ;
				}
				map.put("barCode",barCodes);
				map.put("itemCount", barCodesCount);
			}
			order.add(map);
		}
		System.out.println(order);
		retMap.put("code", "200");
		retMap.put("list", order);
		return retMap;
	}
	
	/**
	 * 校验是否空白行
	 * @param row
	 * @return
	 */
	public static boolean isRowEmpty(Row row) {
		for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
			Cell cell = row.getCell(c);
			if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK)
				return false;
			}
		return true;
	}
	
	/**
	 * @return
	 * @throws Exception 
	 */
	public Map<String,Object> getOrder(String ERPType){
		if(ERPEnums.FURUN.getKey().equals(ERPType)){
			return readFunRun(sheet);
		}
		if(ERPEnums.WANGQUBAO.getKey().equals(ERPType)){
			return readWangQuBao(sheet);
		}
		return null;
	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		String str=ExcelReaderUtil.filterEmoji("", "");
		System.err.println(str);
//		}
	}
}
