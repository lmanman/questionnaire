package com.visionet.letsdesk.app.common.modules.office;

import com.visionet.letsdesk.app.common.modules.props.PropsKeys;
import com.visionet.letsdesk.app.common.modules.props.PropsUtil;
import com.visionet.letsdesk.app.common.modules.string.StringPool;
import com.visionet.letsdesk.app.common.modules.time.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ExcelUtil {
	
	public static String createExcel(List<Object[]> data,String[] title) throws Exception{
		String filePath = createExcelFilePath();
		FileOutputStream fileos = null;
		try {
			XSSFWorkbook workbook = new XSSFWorkbook();
			
			XSSFSheet sheet = workbook.createSheet();
			writeRow(title, sheet.createRow(0));
			
			writeRows(data, sheet, 1);
			
			
			fileos = new FileOutputStream(filePath);
			workbook.write(fileos);
			
		} catch (Exception e) {
			throw e;
		} finally{
			if(fileos!=null) fileos.close();
		}
		
		return filePath;
	}
	
	private static String createExcelFilePath(){
		String fileName = UUID.randomUUID().toString() + ".xlsx";
		String dateStr = DateUtil.convertToString(new Date(), DateUtil.YMD1);
		
		String path = PropsUtil.getProperty(PropsKeys.UPLOAD_FILE_ROOT_PATH)
				+ StringPool.FORWARD_SLASH + dateStr + StringPool.FORWARD_SLASH	+ "excel";
		
		File parentDir = new File(path);
		if(!parentDir.exists()) parentDir.mkdirs();
		
		return path + StringPool.FORWARD_SLASH + fileName;
	}
	
	private static void writeRows(List<Object[]> data,XSSFSheet sheet,int offset){
		for (int i = 0; i < data.size(); i++) {
			XSSFRow row = sheet.createRow(offset + i);
			writeRow(data.get(i), row);
		}
	}
	
	private static void writeRow(Object[] data,XSSFRow row){
		for (int i = 0; i < data.length; i++) {
			XSSFCell cell = row.createCell(i);
			cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(data[i]==null?"":data[i].toString());
		}
	}
}
