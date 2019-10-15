package com.dyz.filxeservice.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.dyz.filxeservice.common.constant.ServiceConstant;

public class DateHandler {

	public static String getDateString(Date date)
	{
		return getDateString(date, ServiceConstant.DATE_FORMAT);
	}
	
	public static String getDateString(Date date, String strFormat)
	{
		SimpleDateFormat format = new SimpleDateFormat(strFormat);
		String dateString = format.format(date);
		return dateString;
	}
}
