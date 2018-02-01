package com.graby.store.admin.util;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
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
	
	/**
	 * 替换Emoji表情符号为空
	 * @param source
	 * @param slipStr
	 * @return
	 */

 	 public static String filterEmoji(String source,String slipStr) {
 	        if(StringUtils.isNotBlank(source)){
 	            return source.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", slipStr);
 	        }else{
 	            return source;
 	        
 	    }
 	 }
	 
	 public static String filterEmoji(String source) { 
         if(source != null)
         {
             Pattern emoji = Pattern.compile ("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",Pattern.UNICODE_CASE | Pattern . CASE_INSENSITIVE ) ;
             Matcher emojiMatcher = emoji.matcher(source);
             if ( emojiMatcher.find())
             {
                 source = emojiMatcher.replaceAll("");
                 return source ;
             }
         return source;
        }
        return source; 
     }
	 
	 public static void main(String[] args) {
		System.out.println(ExcelReaderUtil.filterEmoji("🌹21321🌹"));
		System.out.println("11");
	}
}
