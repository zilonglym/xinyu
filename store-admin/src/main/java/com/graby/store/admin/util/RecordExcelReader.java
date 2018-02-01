package com.graby.store.admin.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.graby.store.entity.Record;


/**
 * 物流发货重量表格导入解析
 * */
public class RecordExcelReader {
	
	private Sheet sheet;
	
	public RecordExcelReader(InputStream in) throws FileNotFoundException, IOException {
		Workbook wb = new HSSFWorkbook(in);
		sheet = wb.getSheetAt(0);
	}
	
	public RecordExcelReader(File file) throws FileNotFoundException, IOException {
		Workbook wb = new HSSFWorkbook(new FileInputStream(file));
		sheet = wb.getSheetAt(0);
	}
	
	private List<Record> read(Sheet sheet) throws ParseException {
		List<Record> records = new ArrayList<Record>();
		int totalRownums = sheet.getPhysicalNumberOfRows();
		for (int i = 1; i < totalRownums; i++) {
			Record record=new Record();
			Row row = sheet.getRow(i);
			Cell timeCell = row.getCell(1);
			Cell orderNoCell = row.getCell(3);
			Cell stateCell = row.getCell(4);
			Cell cityCell = row.getCell(5);
			Cell weightCell = row.getCell(6);
			//时间
			record.setCreateTime(timeCell.getDateCellValue());
			//物流单号
			record.setExpressOrderNo(orderNoCell.getStringCellValue());
			//省份
			record.setState(stateCell.getStringCellValue());
			//物流中心
			record.setCenter(cityCell.getStringCellValue());
			//总重量
			record.setWeight(weightCell.getNumericCellValue());
			records.add(record);
		}
		return records;
	}
	
	public List<Record> getRecords() throws ParseException {
		return read(sheet);
	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {
		String path = "C://Users/Administrator/Desktop/新建文件夹/运费模板.xls";
		File file = new File(path);
		RecordExcelReader excel;
		excel = new RecordExcelReader(file);
		List<Record> records = excel.getRecords();
		for (Record record:records) {
			System.out.println(record.getCenter()+":"+record.getExpressOrderNo());
		}
	}
}

