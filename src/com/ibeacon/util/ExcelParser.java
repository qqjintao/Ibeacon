package com.ibeacon.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.ibeacon.exception.ExcelParseException;
import com.ibeacon.exception.RunException;


public class ExcelParser {

	private ExcelType[] excelTypes;

	private List<List<Object>> results = new ArrayList<List<Object>>();

	private List<Object> errorresults = new ArrayList<Object>();

	private int offset = 1;

	public ExcelParser(ExcelType[] excelTypes) {
		super();
		this.excelTypes = excelTypes;
	}

	@SuppressWarnings({ "deprecation" })
	public void run(InputStream inputstream) {

		POIFSFileSystem fs = null;

		HSSFWorkbook wb = null;

		try {

			fs = new POIFSFileSystem(inputstream);

			wb = new HSSFWorkbook(fs);

		} catch (IOException e) {

			e.printStackTrace();

			throw new ExcelParseException();

		}

		HSSFSheet sheet = wb.getSheetAt(0);

		int lastRow = sheet.getLastRowNum();

		for (int i = this.offset; i <= lastRow; i++) {

			HSSFRow row = sheet.getRow(i);

			if (row == null)
				continue;

			List<Object> cols = new ArrayList<Object>();

			for (short j = 0; j < this.excelTypes.length; j++) {
				try {
					cols.add(this.excelTypes[j].getCellValue(row.getCell(j)));
				} catch (Exception e) {
					if (this.errorresults.size() > 100)
						break;
					this.errorresults.add("行[" + (i + this.offset) + "],列[" + (j + 1)
							+ "]" + e.getMessage());
				}
			}

			this.results.add(cols);
		}

		if (this.results.size() == 0)
			throw new RunException("core.stony.ExcelFixRowsParser.no_row");

	}
	
	@SuppressWarnings({ "deprecation" })
	public void runSheet(InputStream inputstream,int currentSheet) {

		POIFSFileSystem fs = null;

		HSSFWorkbook wb = null;

		try {

			fs = new POIFSFileSystem(inputstream);

			wb = new HSSFWorkbook(fs);

		} catch (IOException e) {

			e.printStackTrace();

			throw new ExcelParseException();

		}

		HSSFSheet sheet = wb.getSheetAt(currentSheet-1);

		int lastRow = sheet.getLastRowNum();

		for (int i = this.offset; i <= lastRow; i++) {

			HSSFRow row = sheet.getRow(i);

			if (row == null)
				continue;

			List<Object> cols = new ArrayList<Object>();

			for (short j = 0; j < this.excelTypes.length; j++) {
				try {
					cols.add(this.excelTypes[j].getCellValue(row.getCell(j)));
				} catch (Exception e) {
					if (this.errorresults.size() > 100)
						break;
					this.errorresults.add("行[" + (i + this.offset) + "],列[" + (j + 1)
							+ "]" + e.getMessage());
				}
			}

			this.results.add(cols);
		}

		if (this.results.size() == 0)
			throw new RunException("core.stony.ExcelFixRowsParser.no_row at the sheet"+currentSheet);

	}
	
	public void run(InputStream inputstream, int off) {
		this.offset = off;
		this.run(inputstream);
	}
	
	public void runSheet(InputStream inputstream, int off,int currentSheet) {
		this.offset = off;
		this.runSheet(inputstream,currentSheet);
	}

	public List<Object> getErrorresults() {
		return this.errorresults;
	}

	public List<List<Object>> getResults() {
		return this.results;
	}

	public static void main(String[] args) {

		InputStream in = null;
		try {
			in = new FileInputStream("d:/pk.XLS");

			ExcelType[] types = new ExcelType[] {
					new ExcelType(ExcelType.CELL_TYPE_LONG, true),
					new ExcelType(ExcelType.CELL_TYPE_STRING, false),
					new ExcelType(ExcelType.CELL_TYPE_NUMERIC, true),
					new ExcelType(ExcelType.CELL_TYPE_LONG, false),
					new ExcelType(ExcelType.CELL_TYPE_STRING, false),
					new ExcelType(ExcelType.CELL_TYPE_STRING, true),
					new ExcelType(ExcelType.CELL_TYPE_NUMERIC, false),
					new ExcelType(ExcelType.CELL_TYPE_NUMERIC, false),
					new ExcelType(ExcelType.CELL_TYPE_LONG, true),
					new ExcelType(ExcelType.CELL_TYPE_STRING, false) };

			ExcelParser parser = new ExcelParser(types);

			parser.run(in);

			List<?> errorresults = parser.getErrorresults();

			for (int i = 0; i < errorresults.size(); i++) {
			}

			List<List<Object>> results = parser.getResults();

			Iterator<List<Object>> it = results.iterator();
			while (it.hasNext()) {
				it.next();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}
}
