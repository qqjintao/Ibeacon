package com.ibeacon.util;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;

import com.ibeacon.exception.ExcelParseException;

public class ExcelType {
	public static final int CELL_TYPE_STRING = 1;

	public static final int CELL_TYPE_NUMERIC = 2;

	public static final int CELL_TYPE_DATE = 3;

	public static final int CELL_TYPE_LONG = 4;

	private int cellType;

	private boolean noblank;

	public ExcelType(int cellType, boolean noblank) {
		super();
		this.cellType = cellType;
		this.noblank = noblank;
	}

	public Object getCellValue(HSSFCell cell) throws ExcelParseException {

		if (cell == null) {
			if (this.noblank)
				throw new ExcelParseException();

			return null;
		}

		try {
			switch (cell.getCellType()) {
			case HSSFCell.CELL_TYPE_BOOLEAN:
				if (CELL_TYPE_STRING == this.cellType)
					return new Boolean(cell.getBooleanCellValue()).toString();
				else
					throw new ExcelParseException();
			case HSSFCell.CELL_TYPE_NUMERIC:
				if (CELL_TYPE_STRING == this.cellType)
					return CoreUtils.toDigitalString(new BigDecimal(cell
							.getNumericCellValue()).setScale(8,
							BigDecimal.ROUND_HALF_DOWN));
				else if (CELL_TYPE_NUMERIC == this.cellType)
					return new BigDecimal(cell.getNumericCellValue() + "")
							.setScale(8, BigDecimal.ROUND_HALF_DOWN);
				else if (CELL_TYPE_LONG == this.cellType)
					return new Long(new BigDecimal(cell.getNumericCellValue()
							+ "").longValue());
				else if (CELL_TYPE_DATE == this.cellType)
					return cell.getDateCellValue();
				else
					throw new ExcelParseException();
			case HSSFCell.CELL_TYPE_STRING:
				if (CELL_TYPE_STRING == this.cellType)
					return cell.getRichStringCellValue().getString().trim();
				else if (CELL_TYPE_NUMERIC == this.cellType) {

					String str = org.apache.commons.lang3.StringUtils.remove(cell
							.getRichStringCellValue().getString().trim(), ',');
					if (org.apache.commons.lang3.StringUtils.isNotBlank(str))
						return new BigDecimal(str).setScale(8,
								BigDecimal.ROUND_HALF_DOWN);

					if (this.noblank)
						throw new ExcelParseException();

					return null;

				} else if (CELL_TYPE_LONG == this.cellType) {

					String str = org.apache.commons.lang3.StringUtils.remove(cell
							.getRichStringCellValue().getString().trim(), ',');

					if (org.apache.commons.lang3.StringUtils.isNotBlank(str))
						return new Long(new BigDecimal(str).longValue()); 

					if (this.noblank)
						throw new ExcelParseException();

					return null;

				} else if (CELL_TYPE_DATE == this.cellType) {
					Date d = CoreUtils.parseDate(cell.getRichStringCellValue()
							.getString().trim());
					if (d == null & this.noblank)
						throw new ExcelParseException();
					return d;
				}
				throw new ExcelParseException();
			case HSSFCell.CELL_TYPE_BLANK:
				if (this.noblank)
					throw new ExcelParseException();
				return null;
			case HSSFCell.CELL_TYPE_ERROR:
				throw new ExcelParseException();
			case HSSFCell.CELL_TYPE_FORMULA:
				if (CELL_TYPE_STRING == this.cellType) {
					try {
						return cell.getNumericCellValue() + "";
					} catch (Exception e) {
					}
				} else if (CELL_TYPE_NUMERIC == this.cellType)
					return new BigDecimal(cell.getNumericCellValue()).setScale(
							8, BigDecimal.ROUND_HALF_DOWN);
				else if (CELL_TYPE_DATE == this.cellType)
					return cell.getDateCellValue();
				throw new ExcelParseException();
			default:
				if (CELL_TYPE_DATE == this.cellType)
					return cell.getDateCellValue();
				else
					throw new ExcelParseException();
			}
		} catch (NumberFormatException e) {
			throw new ExcelParseException(e.getMessage());
		} catch (Exception ex) {
			throw new ExcelParseException(ex.getMessage());
		}

	}
}
