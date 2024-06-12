package com.Asm.Utils;

import java.io.FileOutputStream;
import java.io.IOException;

import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.poi.ss.usermodel.Row;

public class ExcelUtils {
	private HSSFWorkbook workbook;
	private HSSFSheet sheet;

	public ExcelUtils() {
		workbook = new HSSFWorkbook();
		sheet = workbook.createSheet("Danh sách người dùng");
	}

	public void exportToExcel(Map<String, Object[]> data, String filePath) {
		try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
			int rownum = 0;
			for (Map.Entry<String, Object[]> entry : data.entrySet()) {
				Row row = sheet.createRow(rownum++);
				Object[] rowData = entry.getValue();
				int cellnum = 0;
				for (Object obj : rowData) {
					row.createCell(cellnum++).setCellValue(String.valueOf(obj));
				}
			}
			workbook.write(outputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
