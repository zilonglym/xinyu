package com.xinyu.util;

import java.text.DecimalFormat;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;

public class ExcelReaderUtil {
	
	
	public static String  cellValueFormatStr(Cell cell){
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
		return cellValue;
		
	}

}
