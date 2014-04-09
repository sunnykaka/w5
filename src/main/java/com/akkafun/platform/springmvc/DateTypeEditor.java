package com.akkafun.platform.springmvc;

import java.beans.PropertyEditorSupport;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.util.StringUtils;

/**
 * 日期编辑器
 * 
 * 根据日期字符串长度判断是长日期还是短日期。
 * @author liubin
 * @since Feb 14, 2011
 * @version 1.0
 */
public class DateTypeEditor extends PropertyEditorSupport {
	public final DateFormat DF_LONG = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	public final DateFormat DF_SHORT = new SimpleDateFormat("yyyy-MM-dd");
	public final DateFormat DF_HOUR = new SimpleDateFormat("yyyy-MM-dd HH");
	public final DateFormat DF_MINUTE = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	public final DateFormat DF_ONLY_HOUR = new SimpleDateFormat("HH:mm");
	/**
	 * 短类型日期长度
	 */
	public static final int ONLY_HOUR_DATE = 5;
	public static final int SHORT_DATE = 10;
	public static final int HOUR_DATE = 13;
	public static final int MINUTE_DATE = 16;

	public void setAsText(String text) throws IllegalArgumentException {
		text = text.trim();
		if (!StringUtils.hasText(text)) {
			setValue(null);
			return;
		}
		try {
			if (text.length() <= ONLY_HOUR_DATE) {
				setValue(new java.sql.Date(DF_ONLY_HOUR.parse(text).getTime()));
			} else if(text.length() <= SHORT_DATE){
				setValue(new java.sql.Date(DF_SHORT.parse(text).getTime()));
			} else if(text.length() <= HOUR_DATE){
				setValue(new java.sql.Timestamp(DF_HOUR.parse(text).getTime()));
			} else if(text.length() <= MINUTE_DATE){
				setValue(new java.sql.Timestamp(DF_MINUTE.parse(text).getTime()));
			} else {
				setValue(new java.sql.Timestamp(DF_LONG.parse(text).getTime()));
			}
		} catch (ParseException ex) {
			IllegalArgumentException iae = new IllegalArgumentException(
					"Could not parse date: " + ex.getMessage());
			iae.initCause(ex);
			throw iae;
		}
	}

	/**
	 * Format the Date as String, using the specified DateFormat.
	 */
	public String getAsText() {
		Date value = (Date) getValue();
		return (value != null ? DF_LONG.format(value) : "");
	}
}
