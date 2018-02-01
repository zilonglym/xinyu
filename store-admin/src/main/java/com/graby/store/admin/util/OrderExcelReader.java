package com.graby.store.admin.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;


public class OrderExcelReader {
	
	private final Logger logger = Logger.getLogger(OrderExcelReader.class);
	private Sheet sheet;
	
	public OrderExcelReader(InputStream in) throws FileNotFoundException, IOException {
		try {
			Workbook wb = new HSSFWorkbook(in);
			sheet = wb.getSheetAt(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public OrderExcelReader(File file) throws FileNotFoundException, IOException {
		Workbook wb = new HSSFWorkbook(new FileInputStream(file));
		sheet = wb.getSheetAt(0);
	}
	
	private List<Map<String,Object>> read(Sheet sheet)  throws Exception{
		List<Map<String,Object>> order = new ArrayList<Map<String,Object>>();
		int totalRownums = sheet.getPhysicalNumberOfRows();
	
		logger.info("总行数:"+totalRownums);
		try{
			for (int i = 1; i < totalRownums; i++) {
				System.out.println("row:"+i);
				Map<String,Object>  map =  new HashMap<String,Object>();
				Row row = sheet.getRow(i);
				Cell codeId = row.getCell(0);
				
				//String codeIdValue = codeId.getStringCellValue();
				String codeIdValue = ExcelReaderUtil.cellValueFormatStr(codeId);
				map.put("code",codeIdValue);
				
				Cell expressCompanyId = row.getCell(1);
				String expressCompanyValue = ExcelReaderUtil.cellValueFormatStr(expressCompanyId);
				map.put("expressCompany",expressCompanyValue);
				
				Cell userId = row.getCell(2);
				//String userIdValue = userId.getStringCellValue();
				String userIdValue = ExcelReaderUtil.cellValueFormatStr(userId);
				map.put("userId",userIdValue);
				
				
				Cell items = row.getCell(11);
				String itemsValue = ExcelReaderUtil.cellValueFormatStr(items);
				map.put("items", itemsValue);
//				System.out.println("items:"+items);
				
				Cell userName = row.getCell(12);
				String userNameValue = ExcelReaderUtil.cellValueFormatStr(userName);
				map.put("userName", userNameValue);
				
				
				Cell userPhone = row.getCell(14);
				
				//String userPhoneValue = userPhone.getStringCellValue();
				String userPhoneValue = ExcelReaderUtil.cellValueFormatStr(userPhone);
				map.put("userPhone", userPhoneValue);
				
				Cell address = row.getCell(16);
//				System.out.println("address:"+address);
				String addressIdValue  = ExcelReaderUtil.cellValueFormatStr(address);
//				map.put("address", addreStr);
				String[] addrs = addressIdValue.split(" ");
				
				if(addrs.length ==  1  &&  addressIdValue.indexOf("省")!=-1  && addressIdValue.indexOf("市")!=-1 ){
					map.put("state",addressIdValue.substring(0,addressIdValue.indexOf("省")+1));
					map.put("city",addressIdValue.substring(addressIdValue.indexOf("省")+1,addressIdValue.indexOf("市")+1));
					map.put("district","");
					map.put("address",addressIdValue.substring(addressIdValue.indexOf("市")+1));
				}else{
					map.put("state", addrs[0]);
					map.put("city", addrs[1]);
					map.put("district", addrs[2]);
					String addressStr =  "";
					for(int c = 3,csize =addrs.length;c<csize;c++ ){
						addressStr   = addressStr  +addrs[c];
					}
					map.put("address", addressStr);
				}
				order.add(map);
			}
		}catch(Exception e){
			logger.info("导入异常"+e.getMessage());
			throw new Exception(e);
		}
		return order;
	}
	
	public List<Map<String,Object>> getOrder() throws Exception{
		return read(sheet);
	}
	
	
	public static void main(String[] args) throws Exception {
		String path = "C://Users/lenovo/Desktop/韵达二维码.xls";
		File file = new File(path);
		OrderExcelReader excel = new OrderExcelReader(file);
		List<Map<String, Object>> orders = excel.getOrder();
		for (Map<String, Object> map : orders) {
			System.out.println(map.get("userId") );
		}
	}
}
