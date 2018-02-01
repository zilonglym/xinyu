package com.xinyu.util;

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
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;


public class SfAreaExcelReader {
	private Sheet sheet;
	
	public SfAreaExcelReader(InputStream in) throws FileNotFoundException, IOException {
		Workbook wb = new HSSFWorkbook(in);
		sheet = wb.getSheetAt(0);
	}
	
	public SfAreaExcelReader(File file) throws FileNotFoundException, IOException {
		Workbook wb = new HSSFWorkbook(new FileInputStream(file));
		sheet = wb.getSheetAt(0);
	}
	
	
	
	private List<Map<String,Object>> read(Sheet sheet) {
		List<Map<String,Object>> areas = new ArrayList<Map<String,Object>>();
		int totalRownums = sheet.getPhysicalNumberOfRows();
		for (int i = 1; i < totalRownums; i++) {
			
			Map<String,Object>  map =  new HashMap<String,Object>();
			Row row = sheet.getRow(i);
			//信息
			String msg  =  ExcelReaderUtil.cellValueFormatStr(row.getCell(6));
			
			//乡镇街道
			String n4  = ExcelReaderUtil.cellValueFormatStr(row.getCell(5));
			String n3  = ExcelReaderUtil.cellValueFormatStr(row.getCell(4));
			String n2  = ExcelReaderUtil.cellValueFormatStr(row.getCell(3));
			String n1  = ExcelReaderUtil.cellValueFormatStr(row.getCell(2));
			map.put("n4",n4);
			map.put("msg",msg);
			
			//城市代码
			map.put("code",ExcelReaderUtil.cellValueFormatStr(row.getCell(1)));
			
			//省
			map.put("n1",n1);
			
			//市
			map.put("n2",n2);
			
			//区县
			map.put("n3",n3);
			
			
			
			areas.add(map);
		}
		return areas;
	}
	
	public List<Map<String,Object>> getAreas() {
		return read(sheet);
	}
	
	
}
