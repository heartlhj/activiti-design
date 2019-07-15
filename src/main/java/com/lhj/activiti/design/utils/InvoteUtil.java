
package com.lhj.activiti.design.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.lhj.activiti.design.dean.DaoSate;
import org.apache.commons.lang3.StringUtils;


/*
 *
 * @Description: 
 * @ param 
 * @return 
 * @author lhj
 * @date 2019/7/11 9:17
 */
public class InvoteUtil {
	
	public static String QryLikeParamCustom(String param){
		
		return StringUtils.isBlank(param)?null:("%"+param+"%");
		
	};
	
	public static String QryParamCustom(String param){
		
		return StringUtils.isBlank(param)?null:(param);
		
	};
	
	
	public static Map<String,String> initMap(){
		Map<String,String> map = new HashMap<>();
		map.put(Constants.STATUS, DaoSate.FAILURE.getCode());
		map.put(Constants.MESSAGE, "操作失败");
		return map;
	}
	
	public static Map<String,Object> initObjectMap(){
		Map<String,Object> map = new HashMap<>();
		map.put(Constants.STATUS, DaoSate.FAILURE.getCode());
		map.put(Constants.MESSAGE, "操作失败");
		return map;
	}
	
	public static Map<String,String> setSuccessMap(Map<String,String> map){
		map.put(Constants.STATUS, DaoSate.SUCCESS.getCode());
		map.put(Constants.MESSAGE,"操作成功");
		return map;
		
	}
	
	/**
	 * 得到当天零点时间
	 * @return
	 */
	
	public static Date getStartDate(){
	    Calendar calendar = Calendar.getInstance();  
       /* calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),  
                0, 0, 0);  */
	    calendar.set(Calendar.HOUR_OF_DAY, 0);
	    calendar.set(Calendar.MINUTE, 0);
	    calendar.set(Calendar.SECOND,0);
	    calendar.set(Calendar.MILLISECOND,0);
        Date beginOfDate = calendar.getTime();
        return beginOfDate;
	}
	
	/**
	 * 得到当天23.59.59
	 * @return
	 */
	
	public  static Date getEndDay(){
	    Calendar calendar = Calendar.getInstance();  
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),  
                23, 59, 59);  
        Date beginOfDate = calendar.getTime();
        return beginOfDate;
	}
	
	/**
	 *获得当月第一天
	 * @return
	 */
	public  static Date getStartMonth(){
	    Calendar calendar = Calendar.getInstance();  
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),calendar.getActualMinimum(Calendar.DAY_OF_MONTH), 0, 0, 0);  
        Date startOfDate = calendar.getTime();
        return startOfDate;
	}
	
	/**
	 *获得当月最后一天
	 * @return
	 */
	public  static Date getEndMonth(){
	    Calendar calendar = Calendar.getInstance();  
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),calendar.getActualMaximum(Calendar.DAY_OF_MONTH), 23, 59, 59);  
        Date beginOfDate = calendar.getTime();
        return beginOfDate;
	}
	
	/**
	 * 
	 * @date 2017年10月28日 下午3:56:06
	 * @author LGK
	 * @Description: 判断是否为空 为空初始化为0
	 * @param bigDecimal
	 * @return
	 *
	 */
	public static BigDecimal isNot(BigDecimal bigDecimal){
		return bigDecimal==null?new BigDecimal(0):bigDecimal;
	}
	/**
	 * @date 2017年11月14日 上午9:57:09
	 * @author LGK
	 * @Description:
	 *
	 */
	public static Object setEmpty(Object obj,Type type) {

		if(type == String.class){
			return obj==null?"":obj;
		}
		if(type == Long.class){
			return obj==null?0L:obj;
		}
		if(type == BigDecimal.class){
			return obj==null?BigDecimal.ZERO:obj;
		}
		if(type == Date.class){
			return obj==null? DateUtils.now():obj;
		}
		return null;
	}
	
	//初始化对象
	public static Object initObj(Object object) throws Exception{
		 Field[] fields = object.getClass().getDeclaredFields();
	        for (Field field : fields) {  
	           field.setAccessible(true);  
	           String name = field.getName();
	           if("serialVersionUID".equals(name)){
	        	   continue;
	           }
	           PropertyDescriptor pd = new PropertyDescriptor(name,object.getClass());
	           Method getMethod= pd.getReadMethod();
	           Object obj = getMethod.invoke(object, null);
	           
	           obj = setEmpty(obj,field.getType());
	           Method setMethod =pd.getWriteMethod();
	           setMethod.invoke(object, obj);
	        }
	        return object;
	}
	
}
