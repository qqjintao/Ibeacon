package com.ibeacon.util;


import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


public class CoreUtils {

	public static BigDecimal ZERO = new BigDecimal("0");

	public static BigDecimal ONEPERSENT = new BigDecimal("0.01");

	public static BigDecimal ONE = new BigDecimal("1");

	public static BigDecimal VAT017 = new BigDecimal("0.17");

	public static BigDecimal HALF = new BigDecimal("0.5");

	public static BigDecimal ZZ6 = new BigDecimal("0.06");

	public static BigDecimal WAN = new BigDecimal("10000");

	public static BigDecimal HUNDRED = new BigDecimal("100");

	public static Long LONGZERO = new Long(0);

	public static Double DOUBLEZERO = new Double(0);

	public static Long LONGONE = new Long(1);

	public static Long LONGTWO = new Long(2);

	public static Long LONGTHREE = new Long(3);

	public static Long LONG12 = new Long(12);

	public static Long LONG999 = new Long(999);

	public static final String EMPTY = "";

	private static String ISO_DATE_FORMAT = "yyyy-MM-dd";

	private static String ISO_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	private static String ISO_YEARMONTH_FORMAT = "yyyyMM";

	private static String ISO_YEARMONTH_FORMAT2 = "yyyy-MM";

	public static BigDecimal K1 = new BigDecimal("1000");

	public static long CHPAVC = 310278;

	public static Date parseDate(String str) {

		if (StringUtils.isBlank(str))

			return null;

		try {

			return DateUtils.parseDate(str, new String[] { ISO_DATE_FORMAT });

		} catch (ParseException e) {

			return null;

		}

	}

	public static Date parseDate(String str, String format) {

		if (StringUtils.isBlank(str))

			return null;

		try {

			return DateUtils.parseDate(str, new String[] { format });

		} catch (ParseException e) {

			return null;

		}

	}

	public static Date parseTime(String str) {

		if (StringUtils.isBlank(str))

			return null;

		try {

			return DateUtils.parseDate(str, new String[] { ISO_TIME_FORMAT });

		} catch (ParseException e) {

			return null;

		}

	}

	public static String dateToString(Date date, String format) {
		DateFormat dateFormat = new SimpleDateFormat(format);
		if (null == date && null == format) {
			return null;
		}
		return dateFormat.format(date);

	}

	public static String getTwoBitYear(Date val) {
		return formatDate(val).substring(2, 4);
	}

	public static String getFourBitYear(Date val) {
		return formatDate(val).substring(0, 4);
	}

	public static String getFourBitYearMonth(Date val) {
		String tmp = formatDate(val);
		return tmp.substring(2, 4) + tmp.substring(5, 7);
	}

	public static String getSixBitYearMonthDay(Date val) {
		String tmp = formatDate(val);
		return tmp.substring(2, 4) + tmp.substring(5, 7) + tmp.substring(8, 10);
	}

	public static String getEightBitYearMonthDay(Date val) {
		String tmp = formatDate(val);
		return tmp.substring(0, 4) + tmp.substring(5, 7) + tmp.substring(8, 10);
	}

	public static String getSixBitYearMonth(Date val) {
		String tmp = formatDate(val);
		return tmp.substring(0, 4) + tmp.substring(5, 7);
	}

	public static String getTwoBitYearMonth(Date val) {
		String tmp = formatDate(val);
		return tmp.substring(5, 7);
	}

	public static String formatDate(Date val) {

		if (val == null)
			return "";

		return DateFormatUtils.ISO_DATE_FORMAT.format(val);

	}

	public static String formatDate(Date date, String pattern) {
		try {
			return date != null ? DateFormatUtils.format(date, pattern) : null;
		} catch (Exception e) {
			return null;
		}
	}

	public static String formatDate(String dateString, String pattern) {
		return formatDate(parseDate(dateString), pattern);
	}

	public static String formatDatetime(Date val) {

		if (val == null)
			return "";

		return DateFormatUtils.ISO_DATETIME_FORMAT.format(val);

	}
	
	public static String formatDateTime(Date val) {

		if (val == null){
			return "";
		}
		return DateFormatUtils.ISO_DATE_FORMAT.format(val) + " " + DateFormatUtils.ISO_TIME_NO_T_FORMAT.format(val);

	}

	public static String formatChinaDate(Date val) {

		if (val == null)
			return "";

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(val);

		return calendar.get(Calendar.YEAR) + "年"
		+ (calendar.get(Calendar.MONTH) + 1) + "月"
		+ calendar.get(Calendar.DATE) + "日";

	}

	public static int getYear(Date val) {
		if (val == null)
			return 0;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(val);
		return calendar.get(Calendar.YEAR);
	}

	public static int getMonth(Date val) {
		if (val == null)
			return 0;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(val);
		return calendar.get(Calendar.MONTH) + 1;
	}

	public static int getDay(Date val) {
		if (val == null)
			return 0;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(val);
		return calendar.get(Calendar.DATE);
	}

	public static String formatTime(Date val) {

		if (val == null)
			return "";

		return DateFormatUtils.ISO_TIME_FORMAT.format(val);

	}

	public static String formatDateTime(Date val, String pattern) {
		if (val == null)
			return "";

		return DateFormatUtils.format(val, pattern);
	}

	public static String formatString(Object val) {
		if (null == val || "null".equals(val) || "NULL".equals(val))
			return EMPTY;

		if (val instanceof Date)
			return formatDate((Date) val);

		return org.apache.commons.lang3.StringUtils.stripToEmpty(val.toString());

	}

	public static String formatString(Object val, String defaultVal) {
		if (val == null)
			return defaultVal;

		if (val instanceof Date)
			return formatDate((Date) val);

		return org.apache.commons.lang3.StringUtils.stripToEmpty(val.toString());

	}

	public static boolean isDate(String val) {

		Date dval = parseDate(val);

		if (dval == null)
			return false;

		return true;

	}

	public static Date today() {

		return CoreUtils.parseDate(CoreUtils.formatDate(new Date()));

	}

	public static Date nextDate(Date date) {

		Calendar calendar = Calendar.getInstance();

		calendar.setTime(date);

		calendar.add(Calendar.DAY_OF_MONTH, 1);

		return calendar.getTime();

	}

	public static Date nextMonth(Date date) {

		Calendar calendar = Calendar.getInstance();

		calendar.setTime(date);

		calendar.add(Calendar.MONTH, 1);

		return calendar.getTime();

	}

	public static Date nextMonthOneday(Date date, int month, int day) {

		Calendar calendar = Calendar.getInstance();

		calendar.setTime(date);

		calendar.add(Calendar.MONTH, month);

		calendar.set(Calendar.DATE, day);

		return calendar.getTime();

	}

	public static Date nextDate(Date date, int day) {

		Calendar calendar = Calendar.getInstance();

		calendar.setTime(date);

		calendar.add(Calendar.DAY_OF_MONTH, day);

		return calendar.getTime();

	}

	public static Date previousDate(Date date) {

		return nextDate(date, new Long(-1).intValue());

	}

	// 小时差
	public static double hourBetween(Date toDate, Date fromDate) {
		Calendar cDate = Calendar.getInstance();
		Calendar cFromDate = Calendar.getInstance();
		cDate.setTime(toDate);
		cFromDate.setTime(fromDate);
		long cDateMills = cDate.getTimeInMillis();
		long cFromDateMills = cFromDate.getTimeInMillis();
		long subResult = cDateMills - cFromDateMills;

		return (millSecondToHours(subResult));

	}

	// 日期差
	public static Long daysBetween(Date now, Date returnDate) {
		Calendar cNow = Calendar.getInstance();
		Calendar cReturnDate = Calendar.getInstance();
		cNow.setTime(now);
		cReturnDate.setTime(returnDate);
		setTimeToMidnight(cNow);
		setTimeToMidnight(cReturnDate);
		long todayMs = cNow.getTimeInMillis();
		long returnMs = cReturnDate.getTimeInMillis();
		long intervalMs = todayMs - returnMs;

		return new Long(millisecondsToDays(intervalMs));
	}

	// 月差
	public static Long monthsBetween(Date datefrom, Date dateto) {
		if (datefrom == null || dateto == null) {
			return null;
		}
		long days = daysBetween(dateto, datefrom).longValue();
		return new Long(Math.round(days / 30f));
	}

	// 毫秒转换成小时
	private static double millSecondToHours(long subResult) {
		return ((double) subResult / (3600000));
	}

	// 毫秒换算成天数
	private static int millisecondsToDays(long intervalMs) {
		return (int) (intervalMs / (1000 * 86400));
	}

	// 两个日期毫秒差
	private static void setTimeToMidnight(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
	}

	public static final String ZIP_CHARSET = "ISO-8859-1";

	public static String encode64(Object obj) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			OutputStream zos = new GZIPOutputStream(baos);
			ObjectOutputStream oos = new ObjectOutputStream(zos);
			oos.writeObject(obj);
			oos.close();
			zos.close();
			baos.close();
			Base64 base64Codec = new Base64();
			return new String(base64Codec.encode(baos.toByteArray()),
					ZIP_CHARSET);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	public static Object decode64(String s) {
		try {
			Base64 base64Codec = new Base64();
			ByteArrayInputStream decodedStream = new ByteArrayInputStream(
					base64Codec.decode(s.getBytes(ZIP_CHARSET)));
			InputStream unzippedStream = new GZIPInputStream(decodedStream);
			ObjectInputStream ois = new ObjectInputStream(unzippedStream);
			Object obj = ois.readObject();
			ois.close();
			unzippedStream.close();
			decodedStream.close();
			return obj;
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public static String[] splitStringToArray(String str, String key) {
		if (str != null && key != null) {
			return org.apache.commons.lang3.StringUtils.split(str, key);
		}
		return null;
	}

	public static boolean isNumber(String str) {

		try {
			if (NumberUtils.createBigDecimal(str) == null)
				return false;
		} catch (Exception e) {
			return false;
		}

		return true;

	}

	public static boolean isMod(BigDecimal b1, BigDecimal b2) {

		BigDecimal mod = b1.divide(b2, 0, BigDecimal.ROUND_HALF_UP);

		return b1.compareTo(b2.multiply(mod)) == 0;
	}

	public static long getMod(BigDecimal b1, BigDecimal b2) {

		return b1.divide(b2, 0, BigDecimal.ROUND_FLOOR).longValue();

	}

	public static String getNoParameter(Collection<?> nos) {

		StringBuffer sb = new StringBuffer();

		Iterator<?> it = nos.iterator();
		boolean first = true;
		while (it.hasNext()) {

			if (first) {
				sb.append(it.next().toString());
				first = false;
			} else {
				sb.append(",");
				sb.append(it.next().toString());
			}
		}

		return sb.toString();

	}

	public static String getStringParameter(Collection<?> nos) {

		StringBuffer sb = new StringBuffer();

		Iterator<?> it = nos.iterator();
		boolean first = true;
		while (it.hasNext()) {

			if (first) {
				sb.append("'");
				sb.append(it.next().toString());
				sb.append("'");
				first = false;
			} else {
				sb.append(",");
				sb.append("'");
				sb.append(it.next().toString());
				sb.append("'");
			}
		}

		return sb.toString();

	}

	public static String fillString(Long val) {
		if (val == null)
			return "0";

		NumberFormat format = NumberFormat.getNumberInstance(Locale.CHINA);
		((DecimalFormat) format).applyPattern("000");
		return format.format(val);
	}

	private static String fillString1 = "0";

	private static String fillString2 = "00";

	private static String fillString3 = "000";

	private static String fillString4 = "0000";

	private static String fillString5 = "00000";

	private static String fillString6 = "000000";

	private static String fillString7 = "0000000";

	private static String fillString8 = "00000000";

	private static String fillString9 = "000000000";

	public static String fillString(Long val, int numCount) {
		if (val == null)
			return "0";

		String pattern = null;
		switch (numCount) {
		case 1:
			pattern = fillString1;
			break;
		case 2:
			pattern = fillString2;
			break;
		case 3:
			pattern = fillString3;
			break;
		case 4:
			pattern = fillString4;
			break;
		case 5:
			pattern = fillString5;
			break;
		case 6:
			pattern = fillString6;
			break;
		case 7:
			pattern = fillString7;
			break;
		case 8:
			pattern = fillString8;
			break;
		case 9:
			pattern = fillString9;
			break;

		default:
			break;
		}

		NumberFormat format = NumberFormat.getNumberInstance(Locale.CHINA);
		((DecimalFormat) format).applyPattern(pattern);
		return format.format(val);
	}

	public static String toQuantityString(BigDecimal val) {
		if (val == null)
			return "0";

		NumberFormat format = NumberFormat.getNumberInstance(Locale.CHINA);
		((DecimalFormat) format).applyPattern("###,###,###,##0.####");
		return format.format(val.setScale(4, BigDecimal.ROUND_HALF_UP));
	}

	public static String toQuantityString(Long val) {
		if (val == null)
			return "0";

		NumberFormat format = NumberFormat.getNumberInstance(Locale.CHINA);
		((DecimalFormat) format).applyPattern("###,###,###,##0.####");
		return format.format(new BigDecimal(val.longValue()).setScale(4,
				BigDecimal.ROUND_HALF_UP));
	}

	public static String toPriceString(BigDecimal val) {
		if (val == null)
			return "0.00";

		NumberFormat format = NumberFormat.getNumberInstance(Locale.CHINA);
		((DecimalFormat) format).applyPattern("###,###,###,##0.00######");
		return format.format(val.setScale(8, BigDecimal.ROUND_HALF_UP));
	}

	public static String toRateString(BigDecimal val) {
		if (val == null)
			return "0.00";

		NumberFormat format = NumberFormat.getNumberInstance(Locale.CHINA);
		((DecimalFormat) format).applyPattern("###,###,###,##0.#########");
		return format.format(val.setScale(9, BigDecimal.ROUND_HALF_UP));
	}

	public static String toAmountString(BigDecimal val) {
		if (val == null)
			return "0.00";

		NumberFormat format = NumberFormat.getNumberInstance(Locale.CHINA);
		((DecimalFormat) format).applyPattern("###,###,###,##0.00");
		return format.format(val.setScale(2, BigDecimal.ROUND_HALF_UP));
	}

	public static String toAmountString(BigDecimal val, String pattern) {
		if (val == null)
			val = CoreUtils.ZERO;
		NumberFormat format = NumberFormat.getNumberInstance(Locale.CHINA);
		((DecimalFormat) format).applyPattern(pattern);
		return format.format(val.setScale(2, BigDecimal.ROUND_HALF_UP));
	}

	public static String toDigitalString(BigDecimal val) {
		if (val == null)
			return "0.00";

		NumberFormat format = NumberFormat.getNumberInstance(Locale.CHINA);
		((DecimalFormat) format).applyPattern("############.#########");
		return format.format(val);
	}
	
	/**
	 * 格式化数字
	 * @param val
	 * @return
	 */
	public static BigDecimal formatNum(BigDecimal val){
		if (val == null)
			return new BigDecimal("0.00");
		
		DecimalFormat fmt = new DecimalFormat("#.##");
		return new BigDecimal(fmt.format(val));
	}
	
	
	/**
	 * 转换为百分比的值(%)
	 * @param val
	 * @return
	 */
	public static String formatNumPercent(BigDecimal val){
		if (val == null)
			return "0.00%";
		
		DecimalFormat fmt = new DecimalFormat("#.##%");
		return fmt.format(val);
	}
	
	

	/*
	 * if the length of the String --value is greater than max, will return
	 * substring within the maximum length example:
	 * 
	 * effectiveStr("abc",2),return "ab";
	 * 
	 * effectiveStr("abc１",4), return "abc",其中"１"是全角的;
	 * 
	 * effectiveStr("abc１",5),return "abc１",其中"１"是全角的;
	 * 
	 * effectiveStr("abc１全",5), return "abc１";
	 * 
	 * effectiveStr("abc１全",6), return "abc１";
	 * 
	 * effectiveStr("abc１全",7), return "abc１全".
	 */
	public static String lesslengthString(String value, int max) {

		if (value == null)
			return "";

		value = value.trim();

		byte[] bs;
		try {
			bs = value.getBytes("GBK");
		} catch (UnsupportedEncodingException e) {
			return "";
		}

		if (bs.length <= max)
			return value;

		int counterOfDoubleByte = 0;
		for (int i = 0; i < max; i++) {
			if (bs[i] < 0)
				counterOfDoubleByte++;
		}

		try {
			if (counterOfDoubleByte % 2 == 0)
				return new String(bs, 0, max, "GBK");
			else
				return new String(bs, 0, max - 1, "GBK");
		} catch (UnsupportedEncodingException e) {
			return "";
		}

	}

	/*
	 * if the length of the String -- value is greater than max, will return
	 * substring within the maximum length
	 * 
	 * effectiveStr(114.851,16,2), return "114.85"
	 * 
	 * effectiveStr(114.21,16,0), return 114
	 * 
	 * effectiveStr(114.851,16,1), return "114.9"
	 */
	public static String lesslengthString(BigDecimal number, int maxlength,
			int precision) {

		// effectiveStr(0.223,3,2), return "";
		if (maxlength <= (precision + 1)) {
			return "";
		}

		// format
		String format_Str = "";
		String head = "";
		String tail = ".";
		for (int i = 0; i < maxlength - precision - 1; i++) {
			head += "#";
		}
		for (int i = 0; i < precision; i++) {
			tail += "#";

		}
		format_Str = head + tail;
		DecimalFormat format = new DecimalFormat(format_Str);

		// 4舍5入
		String number_Str = number.toString();
		int pointIndex = number_Str.indexOf(".");
		if (number_Str.length() > (pointIndex + precision + 1)) {
			String precision_Str = number_Str
					.charAt(pointIndex + precision + 1) + "";
			BigDecimal base = new BigDecimal("1");
			BigDecimal incre = new BigDecimal("0.1");
			if (precision_Str.equals("5")) {
				for (int i = 0; i < precision + 1; i++) {
					base = base.multiply(incre);
				}
				number = number.add(base);
			}
		}

		return format.format(number);
	}

	/**
	 * 将数字转换成中文的大写货币值
	 * 
	 * @param moneyValue
	 * @return
	 */
	public static String toCapitalMoney1(BigDecimal money) {

		String moneystr = money.setScale(2, BigDecimal.ROUND_HALF_UP)
				.toString();

		int pos = moneystr.indexOf(".");

		String Result = "";
		String capitalLetter = "零壹贰叁肆伍陆柒捌玖";
		String monetaryUnit = "分角圆拾佰仟万拾佰仟亿拾佰仟万拾佰仟亿拾佰仟";
		String tempCapital, tempUnit;

		int integer; // 钱的整数部分
		int point; // 钱的小数部分
		int tempValue; // 钱的每一位的值

		if (pos == -1) {
			integer = Integer.parseInt(moneystr);
			point = 0;
		} else {
			integer = Integer.parseInt(moneystr.substring(0, pos));
			point = Integer.parseInt(moneystr.substring(pos + 1,
					moneystr.length()));
		}

		if (integer == 0 && point == 0)
			return "零圆";

		/*
		 * 货币整数部分操作 1. 依次取得每一位上的值 2. 转换成大写 3. 确定货币单位
		 */

		if (integer > 0) {
			for (int i = 1; integer > 0; i++) {
				tempValue = (integer % 10);
				tempCapital = capitalLetter.substring(tempValue, tempValue + 1);
				tempUnit = monetaryUnit.substring(i + 1, i + 2);
				Result = tempCapital + tempUnit + Result;
				integer = integer / 10;
			}
		}

		/*
		 * 货币小数部分操作
		 */
		if (point > 0) {
			tempValue = (point / 10);
			for (int i = 1; i > -1; i--) {
				tempCapital = capitalLetter.substring(tempValue, tempValue + 1);
				tempUnit = monetaryUnit.substring(i, i + 1);
				Result = Result + tempCapital + tempUnit;
				tempValue = point % 10;
			}
		}

		return Result;
	}

	public static String toCapitalMoney(BigDecimal val) {

		String strNum = val.toString();

		int n, m, k, i, j, q, p, s = 0;
		int length, subLength, pstn;
		String change, output, subInput, input = strNum;
		output = "";

		if (strNum.equals(""))
			return null;
		else {
			length = input.length();
			pstn = input.indexOf('.'); // 小数点的位置

			if (pstn == -1) {
				subLength = length;// 获得小数点前的数字
				subInput = input;
			} else {
				subLength = pstn;
				subInput = input.substring(0, subLength);
			}

			char[] array = new char[4];
			char[] array2 = { '仟', '佰', '拾' };
			char[] array3 = { '亿', '万', '元', '角', '分' };

			n = subLength / 4;// 以千为单位
			m = subLength % 4;

			if (m != 0) {
				for (i = 0; i < (4 - m); i++) {
					subInput = '0' + subInput;// 补充首位的零以便处理
				}
				n = n + 1;
			}
			k = n;

			for (i = 0; i < n; i++) {
				p = 0;
				change = subInput.substring(4 * i, 4 * (i + 1));
				array = change.toCharArray();// 转换成数组处理

				for (j = 0; j < 4; j++) {
					output += formatC(array[j]);// 转换成中文
					if (j < 3) {
						output += array2[j];// 补进单位，当为零是不补（千百十）
					}
					p++;
				}

				if (p != 0)
					output += array3[3 - k];// 补进进制（亿万元分角）
				// 把多余的零去掉

				String[] str = { "零仟", "零佰", "零拾" };
				for (s = 0; s < 3; s++) {
					while (true) {
						q = output.indexOf(str[s]);
						if (q != -1)
							output = output.substring(0, q) + "零"
									+ output.substring(q + str[s].length());
						else
							break;
					}
				}
				while (true) {
					q = output.indexOf("零零");
					if (q != -1)
						output = output.substring(0, q) + "零"
								+ output.substring(q + 2);
					else
						break;
				}
				String[] str1 = { "零亿", "零万", "零元" };
				for (s = 0; s < 3; s++) {
					while (true) {
						q = output.indexOf(str1[s]);
						if (q != -1)
							output = output.substring(0, q)
							+ output.substring(q + 1);
						else
							break;
					}
				}
				k--;
			}

			if (pstn != -1)// 小数部分处理
			{
				for (i = 1; i < length - pstn; i++) {
					if (input.charAt(pstn + i) != '0') {
						output += formatC(input.charAt(pstn + i));
						output += array3[2 + i];
					} else if (i < 2)
						output += "零";
					else
						output += "";
				}
			}
			if (output.substring(0, 1).equals("零"))
				output = output.substring(1);
			if (output.substring(output.length() - 1, output.length()).equals(
					"零"))
				output = output.substring(0, output.length() - 1);
			return output += "整";
		}
	}

	public static String get3Eng(String strNum) {
		String strEng = "";
		String str[] = { "", "ONE", "TWO", "THREE", "FOUR", "FIVE", "SIX",
				"SEVEN", "EIGHT", "NINE" };
		String str1[] = { "TEN", "ELEVEN", "TWELVE", "THIRTEEN", "FOURTEEN",
				"FIFTEEN", "SIXTEEN", "SEVENTEEN", "EIGHTEEN", "NINETEEN" };
		String str2[] = { "TEN", "TWENTY", "THIRTY", "FORTY", "FIFTY", "SIXTY",
				"SEVENTY", "EIGHTY", "NINETY", "HUNDRED" };
		int num = Integer.parseInt(strNum);
		int b = num / 100;
		int t = (num % 100) / 10;
		int g = (num % 100) % 10;
		if (b != 0) {
			strEng = strEng + str[b] + " " + str2[9];
		}

		if (t == 0) {
			if (g != 0) {
				if (b != 0) {
					strEng = strEng + " AND ";
				}
				strEng = strEng + str[g];
			}
		} else if (t == 1) {
			if (b != 0) {
				strEng = strEng + " AND ";
				num = num % 100;
			}
			strEng = strEng + str1[num - 10];
		} else if (t != 1) {
			if (g != 0) {
				if (b != 0) {
					strEng = strEng + " AND ";
				}
				strEng = strEng + str2[t - 1] + "-" + str[g];
			} else {
				if (b != 0) {
					strEng = strEng + " AND ";
				}
				strEng = strEng + str2[t - 1] + str[g];
			}
		}
		return strEng;
	}

	public static String getCent(String strNum) {
		String strEng = "";
		String str[] = { "", "ONE", "TWO", "THREE", "FOUR", "FIVE", "SIX",
				"SEVEN", "EIGHT", "NINE" };
		String str1[] = { "TEN", "ELEVEN", "TWELVE", "THIRTEEN", "FOURTEEN",
				"FIFTEEN", "SIXTEEN", "SEVENTEEN", "EIGHTEEN", "NINETEEN" };
		String str2[] = { "TEN", "TWENTY", "THIRTY", "FORTY", "FIFTY", "SIXTY",
				"SEVENTY", "EIGHTY", "NINETY", "HUNDRED" };
		String str3[] = { "CENTS", "", "DOLLARS", "", "HUNDRED", "THOUSAND",
				"", "", "MILLION", "", "", "BILLION", "", "" };
		if (strNum.equals(""))
			return null;
		else {
			int length = strNum.length();
			if (length != 3) {
				return "输入的位数错误！";
			}
			int cent = Integer.parseInt(strNum.substring(1, 3));
			if (cent == 0) {
				return strEng;
			}
			if (cent < 10) {
				strEng = str3[0] + " " + strEng + str[cent];
			} else if (cent >= 10 && cent <= 19) {
				strEng = str3[0] + " " + strEng + str1[cent - 10];
			} else if (cent > 19) {
				int jiao = cent / 10;
				int fen = cent % 10;
				if (fen != 0) {
					strEng = str3[0] + " " + strEng + str2[jiao - 1] + "-"
							+ str[fen];
				} else {
					strEng = str3[0] + " " + strEng + str2[jiao - 1] + str[fen];
				}
			}
			return strEng;
		}
	}

	public static String getEngmoney(BigDecimal val) {

		String strNum = val.toString();

		String strNumber = "";
		String str3[] = { "CENTS", "", "DOLLARS", "", "HUNDRED", "THOUSAND",
				"", "", "MILLION", "", "", "BILLION", "", "" };
		String strEng = "";
		strNumber = strNum;
		int pointbz = strNumber.indexOf(".");
		if (pointbz < 0) {
			strNumber = strNumber + ".00";
		} else if (pointbz > 0) {
			int k = strNum.length() - pointbz;
			if (k == 2) {
				strNumber = strNumber + "0";
			} else if (k == 1) {
				strNumber = strNumber + "00";
			}
		}
		int length = strNumber.length();
		if (length > 16) {
			return "您输入的值过大系统无法处理！";
		}
		String strb = "";
		String strm = "";
		String strq = "";
		String stry = "";
		String strf = "";
		// 得到分
		if (length == 3) {
			strf = getCent(strNumber);
			strEng = strEng + strf;
		} else if (length > 3 && length < 7) {
			stry = get3Eng(strNumber.substring(0, length - 3));
			strf = getCent(strNumber.substring(length - 3, length));
			strEng = strEng + stry + " " + str3[2];
			if (!strf.equals("")) {
				strEng = strEng + " AND " + strf;
			}
		} else if (length > 6 && length < 10) {
			strq = get3Eng(strNumber.substring(0, length - 6));
			stry = get3Eng(strNumber.substring(length - 6, length - 3));
			strf = getCent(strNumber.substring(length - 3, length));
			strEng = strEng + strq + " " + str3[5];
			if (stry.equals("")) {
				strEng = strEng + " " + stry;
			} else {
				strEng = strEng + " " + stry + " " + str3[2];
			}
			if (!strf.equals("")) {
				strEng = strEng + " AND " + strf;
			}
		} else if (length > 9 && length < 13) {
			strm = get3Eng(strNumber.substring(0, length - 9));
			strq = get3Eng(strNumber.substring(length - 9, length - 6));
			stry = get3Eng(strNumber.substring(length - 6, length - 3));
			strf = getCent(strNumber.substring(length - 3, length));
			strEng = strEng + strm + " " + str3[8];
			if (!strq.equals("")) {
				strEng = strEng + " " + strq + " " + str3[5];
			}
			if (!stry.equals("")) {
				strEng = strEng + " " + stry + " " + str3[2];
			} else {
				strEng = strEng + " " + str3[2];
			}
			if (!strf.equals("")) {
				strEng = strEng + " AND " + strf;
			}
		} else if (length > 12 && length < 16) {
			strb = get3Eng(strNumber.substring(0, length - 12));
			strm = get3Eng(strNumber.substring(length - 12, length - 9));
			strq = get3Eng(strNumber.substring(length - 9, length - 6));
			stry = get3Eng(strNumber.substring(length - 6, length - 3));
			strf = getCent(strNumber.substring(length - 3, length));
			strEng = strEng + strb + " " + str3[11];
			if (!strm.equals("")) {
				strEng = strEng + " " + strm + " " + str3[8];
			}
			if (!strq.equals("")) {
				strEng = strEng + " " + strq + " " + str3[5];
			}
			if (!stry.equals("")) {
				strEng = strEng + " " + stry + " " + str3[2];
			} else {
				strEng = strEng + " " + str3[2];
			}
			if (!strf.equals("")) {
				strEng = strEng + " AND " + strf;
			}
		}

		return strEng + " ONLY";
	}

	public static String formatC(char x) {
		String a = "";
		switch (x) {
		case '0':
			a = "零";
			break;
		case '1':
			a = "壹";
			break;
		case '2':
			a = "贰";
			break;
		case '3':
			a = "叁";
			break;
		case '4':
			a = "肆";
			break;
		case '5':
			a = "伍";
			break;
		case '6':
			a = "陆";
			break;
		case '7':
			a = "柒";
			break;
		case '8':
			a = "捌";
			break;
		case '9':
			a = "玖";
			break;
		}
		return a;
	}

	


	
	public static void setCellValue(HSSFCell cell, boolean ishk, Object val) {

		//cell.setEncoding(HSSFCell.ENCODING_UTF_16);

		if (val instanceof Number) {
			cell.setCellValue(((Number) val).doubleValue());

		} else if (val instanceof Date) {
			cell.setCellValue((Date) val);

		} else {
			//cell.setEncoding(HSSFCell.ENCODING_UTF_16);

			if (ishk) {
				cell.setCellValue(EncodingTranslate.convertString(
						CoreUtils.formatString(val), Encoding.GB2312,
						Encoding.BIG5));
			} else {
				cell.setCellValue(CoreUtils.formatString(val));
			}

		}

	}

	public static void setCellValueApplyFont(HSSFCell cell, boolean ishk,
			String val, int startIndex, int endIndex, HSSFFont font1,
			HSSFFont font2) {

		//cell.setEncoding(HSSFCell.ENCODING_UTF_16);

		if (ishk) {

			HSSFRichTextString str = new HSSFRichTextString(
					EncodingTranslate.convertString(
							CoreUtils.formatString(val),Encoding.GB2312,
							Encoding.BIG5));
			str.applyFont(startIndex, endIndex, font1);
			str.applyFont(endIndex + 1, CoreUtils.formatString(val).length(),
					font2);
			cell.setCellValue(str);

		} else {

			HSSFRichTextString str = new HSSFRichTextString(
					CoreUtils.formatString(val));
			str.applyFont(startIndex, endIndex, font1);
			str.applyFont(endIndex + 1, CoreUtils.formatString(val).length(),
					font2);
			cell.setCellValue(str);

		}

	}

	public static String numberToEnglish(String number) {
		String strNum = "";

		String[] number2parts;
		number2parts = number.split("\\.");

		String intStr = null;
		String fraStr = null;

		if (number2parts.length == 1) {
			intStr = number2parts[0];
			fraStr = "";
		} else {
			intStr = number2parts[0];
			fraStr = number2parts[1];
		}

		int intStrLen = intStr.length();
		int fraStrLen = fraStr.length();

		if (intStrLen > 13) {
			return "The Number is Too Large.....";
		}
		if (fraStrLen > 2) {
			return "The Fraction Part is Too Long.....";
		}
		char[] intChar = intStr.toCharArray();
		char[] fraChar = fraStr.toCharArray();

		for (int j = intStrLen, i = 0; j >= 1; j = j - 3, i++) {
			String tmbt = "";
			if (i == 1) {
				tmbt = "thousand";
			} else if (i == 2) {
				tmbt = "million";
			} else if (i == 3) {
				tmbt = "billion";
			} else if (i == 4) {
				tmbt = "trillion";
			}
			if (j == 1) {
				String num3toeng = num3ToEng("00"
						+ String.valueOf(intChar[j - 1]));
				if (!num3toeng.equals("")) {
					strNum = num3toeng + tmbt + " " + strNum;
				}

			} else if (j == 2) {
				String num3toeng = num3ToEng("0"
						+ String.valueOf(intChar[j - 2])
						+ String.valueOf(intChar[j - 1]));
				if (!num3toeng.equals("")) {
					strNum = num3toeng + tmbt + " " + strNum;
				}

			} else if (j >= 3) {
				String num3toeng = num3ToEng(String.valueOf(intChar[j - 3])
						+ String.valueOf(intChar[j - 2])
						+ String.valueOf(intChar[j - 1]));
				if (!num3toeng.equals("")) {
					strNum = num3toeng + tmbt + " " + strNum;
				}
			}
		}
		String fraction = "";
		if (fraStrLen > 0 && !fraStr.equals("0") && !fraStr.equals("00")) {
			fraction += "and cents ";

			if (fraStrLen == 1)
				fraction += numToEng(String.valueOf(fraChar[0]) + "0") + " ";
			else if (fraStrLen == 2 && fraChar[0] == '1')
				fraction += numToEng(fraStr) + " ";
			else {
				fraction += numToEng(String.valueOf(fraChar[0]) + "0") + " ";
				fraction += numToEng(String.valueOf(fraChar[1])) + " ";
			}
		}
		return (strNum + fraction).toLowerCase() + "only";
	}

	public static String num3ToEng(String str) {
		StringBuffer eng = new StringBuffer("");
		char[] chars = str.toCharArray();
		boolean bl = false;
		String and = "";// the "and" behind the hundred
		if (chars[0] != '0') {
			bl = true;
			eng.append(numToEng(String.valueOf(chars[0])) + " "
					+ numToEng("100") + " ");
		}
		if (chars[1] == '1') {
			if (bl) {
				and = "and ";
			}
			eng.append(and
					+ numToEng(String.valueOf(chars[1])
							+ String.valueOf(chars[2])) + " ");
		} else if (chars[1] == '0') {
			if (bl) {
				and = "and ";
			}
			if (chars[2] != '0') {
				eng.append(and + numToEng(String.valueOf(chars[2])) + " ");
			}
		} else {
			if (bl) {
				and = "and ";
			}
			if (chars[2] == '0') {
				eng.append(and + numToEng(String.valueOf(chars[1]) + "0") + " ");
			} else {
				eng.append(and + numToEng(String.valueOf(chars[1]) + "0") + "-"
						+ numToEng(String.valueOf(chars[2])) + " ");
			}
		}

		return eng.toString();
	}

	public static String numToEng(String num) {
		Map<String, String> map = new HashMap<String, String>();

		map.put("0", "zero");
		map.put("1", "one");
		map.put("2", "two");
		map.put("3", "three");
		map.put("4", "four");
		map.put("5", "five");
		map.put("6", "six");
		map.put("7", "seven");
		map.put("8", "eight");
		map.put("9", "nine");
		map.put("10", "ten");
		map.put("11", "eleven");
		map.put("12", "twelve");
		map.put("13", "thirteen");
		map.put("14", "fourteen");
		map.put("15", "fifteen");
		map.put("16", "sixteen");
		map.put("17", "seventeen");
		map.put("18", "eighteen");
		map.put("19", "nineteen");
		map.put("20", "twenty");
		map.put("30", "thirty");
		map.put("40", "forty");
		map.put("50", "fifty");
		map.put("60", "sixty");
		map.put("70", "seventy");
		map.put("80", "eighty");
		map.put("90", "ninety");
		map.put("100", "hundred");
		map.put("130", "thousand");
		map.put("160", "million");
		map.put("190", "billion");
		map.put("1120", "trillion");

		return map.get(num);
	}

	public static String listToString(List<?> l) {
		StringBuffer buf = new StringBuffer();

		Iterator<?> i = l.iterator();
		boolean hasNext = i.hasNext();
		while (hasNext) {
			Object o = i.next();
			buf.append(String.valueOf(o));
			hasNext = i.hasNext();
			if (hasNext)
				buf.append(", ");
		}

		return buf.toString();
	}

	public static String getWBR(String str) {
		if (StringUtils.isBlank(str))
			return "";

		StringBuffer buff = new StringBuffer();
		char[] chars = str.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			buff.append(chars[i]);
			if (chars[i] == ',' || chars[i] == ';' || chars[i] == '\\'
					|| chars[i] == '/')
				buff.append("<WBR>");
		}
		return buff.toString();
	}

	/**//*
	 * 由HSSFWorkbook生成Excel文档
	 */
	public static void generateExcel(HSSFWorkbook wb, String saveFile) {
		try {// 新建一输出文件流
			FileOutputStream fOut = new FileOutputStream(saveFile);
			// 把相应的Excel 工作簿存盘
			wb.write(fOut);
			fOut.flush();
			// 操作结束，关闭文件
			fOut.close();
		} catch (Exception e) {
			e.printStackTrace(); // To change body of catch statement use
			// File |
			// Settings | File Templates.
		}
	}

	// 删除多于的Sheet
	public static HSSFWorkbook deleteSheet(HSSFWorkbook wb) {

		int totalSheets = wb.getNumberOfSheets() - 1;
		while (0 <= totalSheets) {
			if (wb.getSheetName(totalSheets).lastIndexOf("Sheet") == 0) {
				wb.removeSheetAt(totalSheets);
				totalSheets--;
			} else {
				totalSheets--;
			}
		}
		return wb;
	}

	public static Date previousMonth(Date date) {

		Calendar calendar = Calendar.getInstance();

		calendar.setTime(date);

		calendar.add(Calendar.MONTH, new Long(-1).intValue());

		return calendar.getTime();

	}

	// 返回指定精度的数字
	public static BigDecimal getbitNum(int scale, BigDecimal num) {

		if (num == null)
			num = ZERO;

		return dropNoUseBit(num.setScale(scale, BigDecimal.ROUND_HALF_UP));

	}

	// 去掉小数后面无用的位，如：29.3300，则返回29.23
	public static BigDecimal dropNoUseBit(BigDecimal num) {

		String numstr_1 = "";
		String numstr_2 = "";

		String numstr = num.toString();
		String[] numarray = numstr.split("\\.");
		if (numarray != null) {
			numstr_1 = numarray[0];
			numstr_2 = numarray[1];

			numstr_2 = dropLastZero(numstr_2);

		}

		return new BigDecimal(numstr_1 + "." + numstr_2);
	}

	// 去掉字符串末尾的0，如2300，则返回23
	public static String dropLastZero(String numstr) {
		if (numstr.length() > 0
				&& "0".equals(numstr.charAt(numstr.length() - 1) + "")) {
			numstr = numstr.substring(0, numstr.length() - 1);
			numstr = dropLastZero(numstr);
		}
		return numstr;
	}

	// 半角全角转换 以及首字母去掉空格
	public static String trim(String s) {
		if (s == null) {
			return null;
		}
		// 去首尾空格，不管是全角半角：
		s = s.replaceAll("(^[ |　]*|[ |　]*$)", "");
		s = s.replaceAll("　", "");

		return s;
	}

	/**
	 * 半角转全角
	 * 
	 * @param QJstr
	 * @return
	 */
	public static String BQchange(String QJstr) {
		String outStr = "";
		String Tstr = "";
		byte[] b = null;

		for (int i = 0; i < QJstr.length(); i++) {
			try {
				Tstr = QJstr.substring(i, i + 1);
				b = Tstr.getBytes("unicode");
			} catch (java.io.UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			if (b[3] != -1) {
				b[2] = (byte) (b[2] - 32);
				b[3] = -1;
				try {
					outStr = outStr + new String(b, "unicode");
				} catch (java.io.UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			} else
				outStr = outStr + Tstr;
		}

		return outStr;
	}

	/**
	 * 全角转半角
	 * 
	 * @param QJstr
	 * @return
	 */
	/*
	 * public static String QBchange(String QJstr) { String outStr = ""; String
	 * Tstr = ""; byte[] b = null;
	 * 
	 * for (int i = 0; i < QJstr.length(); i++) { try { Tstr =
	 * QJstr.substring(i, i + 1); b = Tstr.getBytes("unicode"); } catch
	 * (java.io.UnsupportedEncodingException e) { e.printStackTrace(); }
	 * 
	 * if (b[3] == -1) { b[2] = (byte) (b[2] + 32); b[3] = 0; try { outStr =
	 * outStr + new String(b, "unicode"); } catch
	 * (java.io.UnsupportedEncodingException e) { e.printStackTrace(); } } else
	 * outStr = outStr + Tstr; }
	 * 
	 * return outStr; }
	 */

	public static String QBchange(String QJstr) {
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < QJstr.length(); i++) {
			char c = QJstr.charAt(i);

			if (c >= 65281 && c < 65373)
				sb.append((char) (c - 65248));
			else
				sb.append(QJstr.charAt(i));
		}

		return sb.toString();
	}

	// 多级代理取真实ip
	public static String getIpAddr(HttpServletRequest request) {
		String strClientIp = request.getHeader("x-forwarded-for");
		request.getRemoteHost();
		// log.info("All the IP address string is: " + strClientIp);
		if (strClientIp == null || strClientIp.length() == 0
				|| "unknown".equalsIgnoreCase(strClientIp)) {
			strClientIp = request.getRemoteAddr();
		} else {
			String ipList[] = null;
			ipList = strClientIp.split(","); // 拆分字符串，可直接用String.plit方法
			String strIp = new String();
			for (int index = 0; index < ipList.length; index++) {
				strIp = ipList[index];
				if (!("unknown".equalsIgnoreCase(strIp))) {
					strClientIp = strIp;
					break;
				}
			}
		}

		return strClientIp;
	}

	/**
	 * 通过反谢机制获取类属性<br>
	 * 返回结果为键(属性名称)值(属性内容)对字符串内容<br>
	 * 字符串内容如： [ name = 张三; ...... ] <br>
	 * 
	 * @param bean
	 * @return String
	 */
	public static String getBeanPropertys(Object bean) {
		StringBuffer sb = new StringBuffer();
		sb.append("[ ");
		if (bean != null) {
			PropertyDescriptor propertyDescriptor;
			PropertyDescriptor[] propertyDescriptors = PropertyUtils
					.getPropertyDescriptors(bean);
			String propertyName = null;
			Object propertyValue = null;
			for (int i = 0; i < propertyDescriptors.length; i++) {
				try {
					propertyDescriptor = propertyDescriptors[i];
					propertyName = propertyDescriptor.getName();
					propertyValue = PropertyUtils.getProperty(bean,
							propertyName);
					sb.append(propertyName);
					sb.append(" = ");
					sb.append(propertyValue);
					sb.append(";");
				} catch (Exception ex) {
					sb.append("ERROR");
				}
			}
		}
		sb.append(" ]");
		return sb.toString();
	}

	/**
	 * 取javabean中的属性的值
	 * 
	 * @deprecated
	 */
	// public static String getBeanType(Object condition) {
	// Method metd = null;
	// String fdname = null;
	// Class clazz = condition.getClass();
	//
	// Field[] fds = clazz.getDeclaredFields();// 获取他的字段数组
	//
	// StringBuffer parameter = new StringBuffer();
	// try {
	// // for (Field field : fds) {// 遍历该数组
	// for (int i = 0; i < fds.length; i++) {
	// Field field = fds[i];
	// fdname = field.getName();// 得到字段名，
	// if (!"serialVersionUID".equals(fdname)
	// && !"beaAddedFields".equals(fdname)) {
	// // 根据字段名找到对应的get方法 null表示无参数
	// metd = clazz.getMethod("get" + change(fdname), new Class[0]);
	// // 比较是否在字段数组中存在name字段，如果不存在短路，如果存在继续判断该字段的get方法是否存在，同时存在继续执行
	// if (metd != null) {
	// java.lang.Object[] name = metd.invoke(condition, new Class[0]);//
	// 调用该字段的get方法
	// if (!"".equals(CoreUtils.formatString(name)))
	// parameter.append(fdname + ":"
	// + CoreUtils.formatString(name) + ";");
	// }
	// }
	//
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return parameter.toString();
	// }

	// 将属性的第一个字母换成大写
	@Deprecated
	public static String change(String src) {
		if (src != null) {
			StringBuffer sb = new StringBuffer(src);
			sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
			return sb.toString();
		} else {
			return null;
		}
	}

	// 时间差
	public static Date getTime(Date date, int dateNum) {
		long one_hour = 60 * 60 * 1000L;
		Date nextDate = new Date();
		nextDate.setTime(date.getTime() - dateNum * 24 * one_hour);
		return nextDate;
	}

	/**
	 * 获取系统根目录绝对路径 add by mching 2009-04-23
	 * 
	 * @param request
	 * @return String
	 */
	public static String getSystemResourceMapped(HttpServletRequest request) {
		return request.getSession().getServletContext().getRealPath("/");
	}

	/**
	 * 校验字符串是否是手机号码 add by mching 2009-06-04
	 * 
	 * @param number
	 * @return booelan
	 */
	public static boolean isMobileNumber(String number) {
		boolean isMobile = false;

		if (StringUtils.isBlank(number))
			return isMobile;

		number = number.trim();

		// 去除国际码(如号码：+8613800138000)
		if (number.length() == 14 && number.startsWith("+86"))
			number = number.substring(3, number.length()).trim();
		
		if (number.length() == 12 && number.startsWith("+"))
			number = number.substring(1, number.length()).trim();

		// 号码长度是否满足手机位数
		if (number.length() != 11)
			return isMobile;

		// 手机号码前三位规则列表
		String[] rules = { "134", "135", "136", "137", "138", "139", "159",
				"158", "150", "157", "151", "188", "189", "130", "131", "132",
				"133", "153", "156", "180", "152", "183", "184", "186", "187", "147", "155", "181", "182", "185","170","177" };

		// 匹配规则
		for (int i = 0; i < rules.length; i++) {
			if (number.startsWith(rules[i])) {
				isMobile = true;
				break;
			}
		}
		//如果已通过上面的判断,继续下面的判断
		if (isMobile) {
			for (int j=0; j < number.length(); j++) {
				if(!(java.lang.Character.isDigit(number.charAt(j)))){
					isMobile = false;
					if (!isMobile) {
						break;
					}
				}

			}
		}

		return isMobile;
	}
	
	
	/**
	 * 校验字符串是否是手机号码新方法 zheng.cao
	 * 
	 * @param number
	 * @return booelan
	 */
	public static boolean isMobileNumberNEW(String number) {
		if (StringUtils.isBlank(number)) return false;

		number = number.trim();
		String numberStr=number.replaceAll("\\+86", "").replaceAll("\\+", "");
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");  
		  
	    Matcher m = p.matcher(numberStr);  
		  
		return m.matches();  
		
	}
	
	/**
	 * 校验字符串是否是固话号码 zheng.cao
	 * 027-2234567-1
	 * 0755-88888888-111
	 * @param number
	 * @return booelan
	 */
	public static boolean istelephoneNumberNEW(String number) {
		if (StringUtils.isBlank(number)) return false;
		number = number.trim();
		Pattern p = Pattern.compile("^([0-9]{3,4}\\-)?([2-9][0-9]{6,7})(\\-[0-9]{1,4})?$");  
		  
	    Matcher m = p.matcher(number);  
		  
		return m.matches();  
		
	}

	/**
	 * 去除字符串中的空格、回车、换行符、制表符
	 * @param str
	 * @return
	 */
	public static String replaceBlank(String str){
		if(str==null || "".equals(str)){
			return "";
		}
		Pattern p = Pattern.compile("\\s*|\t|\r|\n");
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	} 
	
	public static boolean isEmail(String str) {
		if (StringUtils.isEmpty(str)) {
			return false;
		}
		int pos;

		String[] mailArra = str.split(",");
		String mail = "";
		for (int i=0; i< mailArra.length ;i++) {
			mail = mailArra[i]; 
			if ((pos = mail.indexOf("@")) == -1) {
				return false;
			} else if ((pos = mail.indexOf("@", pos + 1)) != -1) {
				return false;
			}
		}

		return true;
	}

	/**
	 * HTML 标签特殊字符过滤,用于防止JS代码功击 将替找符号如：<b><</b> <b>></b> <b>"</b>
	 * 
	 * HTML 标签特殊字符过滤,用于防止JS代码功击 将替找符号如：<b><</b> <b>></b> <b>"</b> <b>,</b>
	 * <b>|</b>
	 * 
	 * @param value
	 * @return String
	 */
	public static String encodeHTML(String value) {
		if (value == null) {
			return "";
		}
		char content[] = new char[value.length()];
		value.getChars(0, value.length(), content, 0);
		StringBuffer result = new StringBuffer();
		// 过滤"(34)<(60)>(62),(44)|(124)符号
		for (int i = 0; i < content.length; i++) {
			if (content[i] == 34) {
				result.append("&quot;");
			} else if (content[i] == 60) {
				result.append("&#60;");
			} else if (content[i] == 62) {
				result.append("&#62;");
			} else if (content[i] == 44) {
				result.append("&#44;");
			} else if (content[i] == 124) {
				result.append("&#124;");
			} else {
				result.append(content[i]);
			}
		}
		return result.toString();
	}

	/**
	 * Long 类型转换，失败结果为NULL add by mching 2009-06-05
	 * 
	 * @param partno
	 * @return Long
	 */
	public static Long parseLong(String partno) {
		try {
			return StringUtils.isBlank(partno) ? null : new Long(partno);
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * 将String转换成BigDecimal
	 * 
	 * @param value
	 * @return
	 */
	public static BigDecimal parseBigDecimal(String value) {
		try {
			return StringUtils.isBlank(value) ? new BigDecimal(0) : new BigDecimal(value);
		} catch (Exception ex) {
			return null;
		}
	}

	public static Double parseDouble(String value) {
		try {
			return StringUtils.isBlank(value) ? null : new Double(value);
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * 将字符串值劈开成字符串数组<br>
	 * <b>value</b>为空或是空字符串将返回一个空数组<br>
	 * <b>regex</b>为一个正则表达式字符劈开条件[为空时将默认按 <b>逗号</b> 劈开]<br>
	 * <br>
	 * 示例如下：<br>
	 * String[] values = CoreUtils.toArray("A,B,C,D,E,F,G", ",");<br>
	 * values 值为 [A, B, C, D, E, F, G]<br>
	 * 
	 * String[] values = CoreUtils.toArray("A|B|C|D|E|F|G", "\\|");<br>
	 * values 值为 [A, B, C, D, E, F, G]<br>
	 * 
	 * @param value
	 *            值
	 * @param regex
	 *            正则表达式
	 * @return String[]
	 */
	public static String[] toArray(String value, String regex) {
		String[] values;
		if (StringUtils.isBlank(value)) {
			values = new String[0];
		} else {
			values = value.split(StringUtils.isBlank(regex) ? "," : regex);
		}
		return values;
	}

	/**
	 * 判断某个字符串中是否包含另一个字符串
	 * 
	 * @param str
	 * @param subString
	 * @return
	 */
	public static boolean isIncludeSubString(String str, String subString) {
		boolean result = false;
		if (str == null || subString == null) {
			return false;
		}
		int strLength = str.length();
		int subStrLength = subString.length();
		String tmpStr = null;
		for (int i = 0; i < strLength; i++) {
			if (strLength - i < subStrLength) {
				return false;
			}
			tmpStr = str.substring(i, subStrLength + i);
			if (tmpStr.endsWith(subString)) {
				return true;
			}
		}
		return result;
	}

	public static String getAge(Date birthday) {
		if (birthday == null) {
			return "";
		}
		int today = Integer
				.parseInt(CoreUtils.getFourBitYear(CoreUtils.today()));

		int birthedyear = Integer.parseInt(CoreUtils.getFourBitYear(birthday));
		String age = String.valueOf(today - birthedyear);
		return age;
	}

	public static String getLongToString(Long val) {
		if (val == null) {
			return "";
		}
		return val.toString();
	}

	/**
	 * 
	 * @param str
	 *            原字符串
	 * @param fixedLength
	 *            固定列
	 * @param fillingChar
	 *            填充字符
	 * @return
	 */
	public static String padLeft(String str, int fixedLength, char fillingChar) {
		if (str == null) {
			str = "";
			int space = fixedLength;
			while (space-- != 0) {
				str = fillingChar + str;
			}
			return str;
		}
		if (str.length() < fixedLength) {
			int space = fixedLength - str.length();
			while (space-- != 0) {
				str = fillingChar + str;
			}
		}
		return str;
	}

	/**
	 * 生成指定长度的随机序列
	 * 
	 * @param length
	 * @return
	 */
	public static String getRandomString(int length) {
		String letterchar = "1234567890abcdefghijklmnopqrstuvwxyz";
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			Random random = new Random();
			int t = random.nextInt(letterchar.length());
			sb.append(letterchar.charAt(t));
			try {
				Thread.sleep(15);
			} catch (InterruptedException e) {
			}
		}
		return sb.toString();
	}

	/**
	 * 生产md5
	 * 
	 * @param str
	 * @return
	 */
	public static String md5(String str) {

		byte[] defaultBytes = str.getBytes();
		try {
			MessageDigest algorithm = MessageDigest.getInstance("MD5");
			algorithm.reset();
			algorithm.update(defaultBytes);
			byte messageDigest[] = algorithm.digest();

			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < messageDigest.length; i++) {
				hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
			}
			str = hexString.toString();
		} catch (NoSuchAlgorithmException nsae) {

		}

		return str;

	}

	public static String getDayOfWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int weekno = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		String[] weeks = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };

		return weeks[weekno];
	}

	/**
	 * P2币种转换成OA币种
	 * 
	 * @param currency
	 * @return
	 */
	public static String P2CurrencyOA(String currency) {
		currency = StringUtils.trim(currency);
		if ("RMB".equals(currency)) {// 人民币
			currency = "CNY";
		} else if ("JPN".equals(currency)) {// 日元
			currency = "JPY";
		} else if ("ENG".equals(currency)) {// 英镑
			currency = "GBP";
		} else if ("RIN".equals(currency)) {// 马来西亚林吉特
			currency = "MYR";
		} else if ("RUP".equals(currency)) {// 印度卢比
			currency = "INR";
		} else if ("NTD".equals(currency)) {// 台湾元/新台币
			currency = "TWD";
		}

		return currency;
	}

	/**
	 * 取上个年月(yyyyMM)
	 * 
	 * @param val
	 * @return
	 */
	public static String getLastYearMonth(String val) {
		if (StringUtils.isBlank(val)) {
			return null;
		} else {
			try {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(CoreUtils.parseDate(val, ISO_YEARMONTH_FORMAT));
				calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
				Date date = calendar.getTime();
				return CoreUtils.formatDate(date, ISO_YEARMONTH_FORMAT);
			} catch (Exception e) {
				return null;
			}

		}
	}

	/**
	 * 取下个年月(yyyyMM)
	 * 
	 * @param val
	 * @return
	 */
	public static String getNextYearMonth(String val) {
		if (StringUtils.isBlank(val)) {
			return null;
		} else {
			try {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(CoreUtils.parseDate(val, ISO_YEARMONTH_FORMAT));
				calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
				Date date = calendar.getTime();
				return CoreUtils.formatDate(date, ISO_YEARMONTH_FORMAT);
			} catch (Exception e) {
				return null;
			}

		}
	}

	/**
	 * 取得下个月第一天
	 * 
	 * @param val
	 * @return String yyyy-mm-dd
	 */
	public static String getNextFirstDay(Date val) {
		if (val == null) {
			return null;
		} else {
			try {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(val);
				calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
				Date date = calendar.getTime();
				return CoreUtils.formatDate(date, ISO_YEARMONTH_FORMAT2)
						+ "-01";
			} catch (Exception e) {
				return null;
			}

		}
	}

	/**
	 * 取得上个月第一天
	 * 
	 * @param val
	 * @return String yyyy-mm-dd
	 */
	public static String getPreFirstDay(Date val) {
		if (val == null) {
			return null;
		} else {
			try {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(val);
				calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
				Date date = calendar.getTime();
				return CoreUtils.formatDate(date, ISO_YEARMONTH_FORMAT2)
						+ "-01";
			} catch (Exception e) {
				return null;
			}

		}
	}

	/** 
     * 获取某年第一天日期 
     * @param year 年份 
     * @return Date 
     */  
    public static Date getYearFirst(int year){  
        Calendar calendar = Calendar.getInstance();  
        calendar.clear();  
        calendar.set(Calendar.YEAR, year);  
        Date currYearFirst = calendar.getTime();  
        return currYearFirst;  
    }
    
    /** 
     * 获取某年最后一天日期 
     * @param year 年份 
     * @return Date 
     */  
    public static Date getYearLast(int year){  
        Calendar calendar = Calendar.getInstance();  
        calendar.clear();  
        calendar.set(Calendar.YEAR, year);  
        calendar.roll(Calendar.DAY_OF_YEAR, -1);  
        Date currYearLast = calendar.getTime();  
          
        return currYearLast;  
    }

	/**
	 * 取得去年的日期，即年份减去一,同时取得该月的第一日
	 * 
	 * @param val
	 * @return String yyyy-mm-dd
	 */
	public static String getLastYear(Date val) {
		if (val == null) {
			return null;
		} else {
			try {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(val);
				calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 1);
				Date date = calendar.getTime();
				return CoreUtils.formatDate(date, ISO_YEARMONTH_FORMAT2)
						+ "-01";
			} catch (Exception e) {
				return null;
			}

		}
	}

	/**
	 * 取得每个月第一天
	 * 
	 * @param val
	 * @return String yyyy-mm-dd
	 */
	public static String getMonthFirstDay(Date val) {
		if (val == null) {
			return null;
		} else {
			try {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(val);
				calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
				Date date = calendar.getTime();
				return CoreUtils.formatDate(date, ISO_YEARMONTH_FORMAT2)
						+ "-01";
			} catch (Exception e) {
				return null;
			}

		}
	}	
	
	/**
     * 返回指定日期的月的第一天
     *
     * @param year
     * @param month
     * @return
     */
    public static Date getFirstDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(Calendar.YEAR),
                     calendar.get(Calendar.MONTH), 1);
        return calendar.getTime();
    }


	 /**
     * 返回指定日期的月的最后一天
     *
     * @param year
     * @param month
     * @return
     */
    public static Date getLastDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(Calendar.YEAR),
                     calendar.get(Calendar.MONTH), 1);
        calendar.roll(Calendar.DATE, -1);
        return calendar.getTime();
    }

	/**
	 * 取得所传入日期下一个月的上一日
	 * 
	 * @param val
	 * @return String yyyy-mm-dd
	 */
	public static String getMonthPreDay(Date val) {
		if (val == null) {
			return null;
		} else {
			try {
				Date nextMonth = CoreUtils.nextMonth(val);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(nextMonth);
				calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);
				Date date = calendar.getTime();
				return CoreUtils.formatDate(date, ISO_YEARMONTH_FORMAT2);
			} catch (Exception e) {
				return null;
			}

		}
	}	

	/**
	 * 获得下周星期一的日期
	 * @param val
	 * @return String yyyy-mm-dd
	 */
	@SuppressWarnings("static-access")
	public static String getNextMonday(int count) {

		Calendar strDate = Calendar.getInstance();
		strDate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

		strDate.add(strDate.DATE, count);

		// System.out.println(strDate.getTime());
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.set(strDate.get(Calendar.YEAR),
				strDate.get(Calendar.MONTH), strDate.get(Calendar.DATE));
		Date monday = currentDate.getTime();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String preMonday = df.format(monday);
		return preMonday;
	}

	/**
	 *  获得下周星期五的日期
	 * @param val
	 * @return String yyyy-mm-dd
	 */
	@SuppressWarnings("static-access")
	public static String getNextFriday(int count) {
		// weeks++;
		GregorianCalendar currentDate = new GregorianCalendar();
		Calendar strDate = Calendar.getInstance();
		strDate.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
		strDate.add(strDate.DATE, count);
		//System.out.println("==" + strDate.getTime());
		currentDate.set(strDate.get(Calendar.YEAR),
				strDate.get(Calendar.MONTH), strDate.get(Calendar.DATE));
		//currentDate.add(GregorianCalendar.DATE, Calendar.DAY_OF_WEEK);
		Date monday = currentDate.getTime();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String preMonday = df.format(monday);
		return preMonday;
	}

	/**
	 * 计算两个日期相差几天
	 * @param fromDate yyyy-MM-dd
	 * @param toDate yyyy-MM-dd
	 * @return
	 */
	public static Long dateDiff(String fromDate, String toDate) {
		long days = 0;
		Date from = CoreUtils.parseDate(fromDate);
		Date to = CoreUtils.parseDate(toDate);
		days =  Math.abs((to.getTime() - from.getTime())
				/ (24 * 60 * 60 * 1000)) + 1;
		return days;
	}

	/**
	 *将20123转换成201203格式
	 * 
	 * @return
	 */
	public static String addZero(String val){
		String str2;
		if(val.length()<6){
			str2 = val.substring(0, 4)+"0"+val.substring(4, 5);
		}else
			str2 = val;

		return str2;
	}
	public static String formatXmlString(Object notStandardXml) {
		if (notStandardXml == null)
			return EMPTY;

		String str = notStandardXml.toString();
		if(org.apache.commons.lang3.StringUtils.isNotBlank(str)) {
			if(str.indexOf("&amp;") > -1 || str.indexOf("&lt;") > -1 || str.indexOf("&gt;") > -1 || str.indexOf("&apos;") > -1 || str.indexOf("&quot;") > -1) {
				return str;				
			} else {
				return str.replace("&", "&amp;");
			}
		}
		return str;
	}

	public static BigDecimal getBigDecimal(BigDecimal val) {
		if (val != null) {
			return val;
		} else {
			return BigDecimal.ZERO;
		}

	}
	public static Long getLong(Long val) {
		if (val != null) {
			return val;
		} else {
			return 0L;
		}
	}
	public static String getString(String val) {
		if (val != null) {
			return val;
		} else {
			return "";
		}
	}
	public static Date getDate(Date val) {
		if (val != null) {
			return val;
		} else {
			return new Date();
		}
	}
	public static String parser2XmlText(Object notStandardXml) {
		if (notStandardXml == null)
			return EMPTY;

		return notStandardXml.toString().replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\'", "&apos;").replace("\"", "&quot;");
	}

	/**
	 *如果传递 进来时一个null值，则返回一个大数类型的0
	 * 
	 * 此方法减法也可以用，除法和乘法不可用
	 * @return
	 */
	public static BigDecimal returnZeroIfAddEqualNull(BigDecimal val){
		if(val == null){
			return ZERO;
		}
		else 
			return val;
	}
	/**
	 * 
	 * @param str  传递进来一个字符串
	 * @return  如果字符串为空 返回0
	 */
	public static BigDecimal returnZeroIfAddEqualNull(String str){
		str = str.trim().replaceAll(" ", "");
		if(StringUtils.isEmpty(str)){
			return BigDecimal.valueOf(0);
		}
		else {
			return new BigDecimal(str);
		}
	}
	public static String getExceptionContext(Exception ex) {

		StringBuffer context = new StringBuffer();
		StackTraceElement[] sts = ex.getStackTrace();
		context.append(ex.getMessage() + "\r\n");
		for (StackTraceElement ste : sts) {
			context.append(ste.getClassName() + " " + ste.getMethodName() + " "
					+ ste.getLineNumber() + "\r\n");
		}

		return context.toString();

	}

	public static boolean isNetFileAvailable(String netFileUrl)
	{
		InputStream   netFileInputStream =null;
		try{
			URL   url   =   new   URL(netFileUrl);   
			URLConnection   urlConn   =   url.openConnection();   
			netFileInputStream   =   urlConn.getInputStream(); 
		}catch (IOException e)
		{
			return false;
		}
		if(null!=netFileInputStream)
		{
			return true;
		}else
		{
			return false;
		}
	}


	/**
	 * 
	 * @param c 字符
	 * @return 如果c为0到9之间的数字，则返回true,否则返回false
	 * 
	 */
	public static boolean isDigit(char c){
		if (c >= '0' && c <= '9')
			return true;
		return false;
	}
	public static boolean isDigit(String str){ 
		Pattern pattern = Pattern.compile("[0-9]*"); 
		return pattern.matcher(str).matches(); 
		}
	/**
	 * 
	 * @param c 字符
	 * @return 如果c为标示符允许的字符，则返回true,否则返回false;
	 */
	public static boolean isAlphia(char c){
		if (c >= '0' && c <= '9')
			return true;
		else if (c >= 'a' && c <= 'z')
			return true;
		else if (c >= 'A' && c <= 'Z')
			return true;
		else if (c == '.' || c == '_' || c == '-')
			return true;
		return false;
	}
	public static String getOperatorDesc(String oper){
		if (">".equals(oper)) return "大于";
		if (">=".equals(oper)) return "大于等于";
		if ("==".equals(oper)) return "等于";
		if ("<=".equals(oper)) return "小于等于";
		if ("<".equals(oper)) return "小于";
		if ("!=".equals(oper)) return "不等于";
		if ("&&".equals(oper)) return "且";
		if ("||".equals(oper)) return "或";
		return "";
	}


	public static List<String> yearFromList(int fromYear){
		List<String> result = new ArrayList<String>();
		int thisYear = CoreUtils.getYear(new Date());
		int startYear;
		int endYear;
		if (thisYear > fromYear) {
			startYear = fromYear;
			endYear = thisYear;
		} else {
			startYear = thisYear;
			endYear = fromYear;
		}
		if (startYear != endYear) {
			for (int i = endYear; i >= startYear; i--) {
				result.add(String.valueOf(i));
			}

		}else {
			result.add(String.valueOf(thisYear));
		}
		return result;
	}
	public static List<String> monthList() {
		List<String> result = new ArrayList<String>();
		for (int i=1; i<=12; i++) {
			if (i <10) {
				result.add("0" + String.valueOf(i));
			} else {
				result.add(String.valueOf(i));
			}
		}
		return result;
	}

	public static final String NOTEQUAL = "!=";
	public static final String EQUAL = "==";

	public static final String MORETHANEQUAL = ">=";
	public static final String MORETHAN = ">";

	public static final String LESSTHAN = "<";
	public static final String LESSTHANEQUAL = "<=";
	public static final String PARENT = "~";		// 27523 ~ 14081 27523是14081的子节点吗？(只支持组织机构)
	public static final String NOTPARENT = "!~";	// 27523 ~ 14081 27523非14081的子节点吗？(只支持组织机构)
	public static final String IN = "IN";
	public static final String NOTIN = "NOTIN";
	

	//	public static final String IN = "IN";
	//	public static final String NOTIN = "NOTIN";

	
	
	
	/**
	 * 获取指定日期月份的日期数
	 * @param yyyy-MM-ss格式日期
	 * @return 
	 */
	public static int getDayCount(String dateTime) {
		  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		  /*GregorianCalendar 是 Calendar 的一个具体子类，
		   * 提供了世界上大多数国家/地区使用的标准日历系统。*/
		  Calendar calendar = new GregorianCalendar();   
		  try {
		   /*使用给定的 Date 设置此 Calendar 的时间*/
		   calendar.setTime(sdf.parse(dateTime));
		  } catch (ParseException e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
		  }   
		  /*返回此日历字段可能具有的最大值。DAY_OF_MONTH 用于指示一个月的某天*/
		  int day = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		  return day;
	}
	
	//进行SQL注入过滤
	public static String TransactSQLInjection(String str) {
          return str.replaceAll(".*([';]+| or |(--)+).*", " ");
    }
	
	public static String getLocalLastIp() {
		String ipaddress = "";
		try {
			ipaddress = InetAddress.getLocalHost().toString();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return org.apache.commons.lang3.StringUtils.substringAfterLast(ipaddress, ".");
	}
	public static boolean isNULL(String str){
		return str==null || "".equals(str);
	}
	
	/**
	 * 日期加一天,用于查询
	 */
	@SuppressWarnings("unused")
	public static Date endTime(Date endTime ){
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		if(null != endTime){
			Calendar cad = Calendar.getInstance();
			cad.setTime(endTime);
			cad.add(Calendar.DATE, 1);
			endTime = cad.getTime();
		}
		return endTime;
	}
	
	/**
	 * 两个日期的月份差
	 * @param fromDate起始日期 A
	 * @param toDate结束日期  B
	 * @return 如果1表示相等，2表示A大于B，3表示A小于B
	 */
	public static int getCompareYearMonth(Date fromDate, Date toDate) {
		Calendar c = Calendar.getInstance();
		c.setTime(fromDate);
		int fromYear = c.get(Calendar.YEAR);
		int fromMonth = c.get(Calendar.MONTH) + 1;
		c.setTime(toDate);
		int toYear = c.get(Calendar.YEAR);
		int toMonth = c.get(Calendar.MONTH) + 1;
		int monthCount = 0;//如果1表示相等，2表示A大于B，3表示A小于B
		if (fromYear  == toYear) {
			 if(fromMonth == toMonth){
				 monthCount =1;
			 }else if(fromMonth > toMonth){
				 monthCount =2;
			 }else if(fromMonth < toMonth){
				 monthCount =3;
			 }
		} else if (fromYear  > toYear) {
			monthCount =2;
		} else if (fromYear < toYear){
			monthCount = 3;
		}
		return monthCount;
	}
	
	/**
	 * 设置当前参数月份加1并把日期设置1日
	 * @param  date 
	 * @return Date
	 */
	public static Date setMonthandDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DATE, 1);
		return calendar.getTime();
	}
}
