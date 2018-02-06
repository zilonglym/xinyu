package com.xinyu.model.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class InventoryExcelReader {
	
	private Sheet sheet;
	
	public InventoryExcelReader(InputStream in) throws FileNotFoundException, IOException {
		Workbook wb = new HSSFWorkbook(in);
		sheet = wb.getSheetAt(0);
	}
	
	public InventoryExcelReader(File file) throws FileNotFoundException, IOException {
		Workbook wb = new HSSFWorkbook(new FileInputStream(file));
		sheet = wb.getSheetAt(0);
	}
	
	private List<Map<String,Object>> read(Sheet sheet) {
		List<Map<String,Object>> inventory = new ArrayList<Map<String,Object>>();
		int totalRownums = sheet.getPhysicalNumberOfRows();
		for (int i = 1; i < totalRownums; i++) {
			System.err.println("row:"+i);
			Map<String,Object>  map =  new HashMap<String,Object>();
			Row row = sheet.getRow(i);
			Cell userId = row.getCell(0);
			map.put("userId",userId.getStringCellValue());
			
			Cell itemName = row.getCell(1);
			map.put("itemCode", itemName.getStringCellValue());
			
//			map.put("barCode", itemCode.getStringCellValue());
			
		
// 			Cell skuCode = row.getCell(3);
// 			map.put("sku", skuCode.getStringCellValue());
			
			Cell num = row.getCell(2);
			Double numValue = num.getNumericCellValue();
			map.put("num", numValue.longValue());
			
//			Cell centroId = row.getCell(5);
//			Double centroIdValue = centroId.getNumericCellValue();
//			map.put("centroId", centroIdValue.longValue());
			
			inventory.add(map);
		}
		return inventory;
	}
	
	public List<Map<String,Object>> getInventory() {
		return read(sheet);
	}
	
}
