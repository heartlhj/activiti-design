/**
 *
 * 广州睿智信息科技有限公司, 版权所有 违者必究
 * copyright 2015-2020
 * @date 2015年12月1日 下午4:59:30
 * @author yuxiao
 * @Description: Date 工具集
 * 
 */
package com.lhj.activiti.design.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;


/**
 *
 * @Description: Date 工具集
 * 
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
	
	/**
	 * 获取系统当前时间
	 * 
	 * @return
	 */
	public static Date now() {
		long t0 = System.currentTimeMillis();
		Date now = new Date(t0);
		return now;
	}
	
	/**
	 * 返回指定格式的时间字符串
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String formatDate(Date date, String pattern) {
		String str = null;
		
		if(date != null && StringUtils.isNotBlank(pattern)){
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			str = sdf.format(date);
		}
		
		return str;
	}
	
	/**
	 * 返回默认格式的时间字符串，默认格式为：yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 */
	public static String defaultFormatDate(Date date) {
		String str = null;
		
		if(date != null){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			str = sdf.format(date);
		}
		
		return str;
	}
	
	/**
	 * 比较两个日期大小
	 * <br>
	 * 0：相等、正数date1大、负数date2大
	 * @param date1
	 * @param date2
	 * @return 时间差
	 */
	public static long compare(Date date1, Date date2) {
		if(date1 == null && date2 == null) {
			return 0;
		}
		if(date1 == null) {
			return -1;
		}
		if(date2 == null) {
			return 1;
		}
		return date1.getTime() - date2.getTime();
	}
	
	/**
	 * 两个日期相减得到年龄
	 * @return 年龄
	 */
	public static int getAge(Date birthDay,Date buyDay) throws Exception {
    	int age;
    	long birth=birthDay.getTime();
    	long buy=buyDay.getTime();
    	if(birth>=buy){
    		 age=0;
    	}else{
    	        Calendar cal1 = Calendar.getInstance(); 
    	        cal1.setTime(birthDay);
    	        int yearBirth = cal1.get(Calendar.YEAR);  
    	        int monthBirth = cal1.get(Calendar.MONTH)+1;  
    	        int dayOfMonthBirth = cal1.get(Calendar.DAY_OF_MONTH); 
    	        
    	        Calendar cal2 = Calendar.getInstance(); 
	       	     cal2.setTime(buyDay);
	       	     int yearNow = cal2.get(Calendar.YEAR);  
    	        int monthNow = cal2.get(Calendar.MONTH)+1;  
    	        int dayOfMonthNow = cal2.get(Calendar.DAY_OF_MONTH);
    	        
    	         age = yearNow - yearBirth;  
    	  
    	        if (monthNow <= monthBirth) {  
    	            if (monthNow == monthBirth) {  
    	                if (dayOfMonthNow < dayOfMonthBirth) 
    	                	age--;  
    	            }else{  
    	                age--;  
    	            }  
    	        } 
    	}
    	return age;
    }
	
	public static Date parse(String strDate, String pattern){  
        try {
			return StringUtils.isBlank(strDate) ? null : new SimpleDateFormat(pattern).parse(strDate);
		} catch (Exception e) {
			return now();
		}  
    }  
	

	/**
	 * 
	 * @date 2017年7月13日 下午12:03:03
	 * @author jie
	 * @Description: 递归时间，直到得到不是自定义排除日期的那天
	 * @param middleTime
	 * @param holidays
	 * @param everyDay：一般是1或者-1
	 * @return
	 *
	 */
	private static Date recursionDate(Date middleTime, String holidays, int everyDay){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(middleTime);
//		calendar.add(Calendar.DATE, -1);
		calendar.add(Calendar.DATE, everyDay);
		middleTime = calendar.getTime();
		String middleTimeStr = DateUtils.formatDate(middleTime, "yyyy-MM-dd");
//		System.out.println(middleTimeStr);
//		System.out.println(holidays.contains(middleTimeStr));
		if(holidays.contains(middleTimeStr)){//存在
			middleTime = recursionDate(middleTime, holidays, everyDay);
		}else{
			return middleTime;
		}
		return middleTime;
	}

	/**
	 * 日志转为时间戳
	 * @param paramDate
	 * @return
	 */
	public static Timestamp dateToTimeStamp(Date paramDate)
	{
		if (paramDate == null) {
			return null;
		}
		return new Timestamp(paramDate.getTime());
	}

	/**
	 * 
	 * @date 2018年1月11日 下午5:16:26
	 * @author jie
	 * @Description: 两个时间之差的秒数
	 * 结束时间-开始时间
	 * @param startDay
	 * @param endDay
	 * @return
	 *
	 */
	public static long getSecond(Date startDay, Date endDay){
		long second = 0;
		if(startDay == null || endDay == null){
			return second;
		}
		
		second = (endDay.getTime() - startDay.getTime()) / 1000;
		return second;
	}

}