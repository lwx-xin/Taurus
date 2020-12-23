package org.taurus.common.util;

import java.util.Collection;

public class ListUtil {
	
	public static boolean isEmpty(Collection<?> list){
		boolean returnValue = false;
		if (list==null||list.size()==0) {
			returnValue = true;
		}else{
			returnValue = false;
		}
		return returnValue;
	}
	
	public static boolean isNotEmpty(Collection<?> list){
		return !isEmpty(list);
	}
	
	public static boolean isEmpty(Object[] array){
		boolean returnValue = false;
		if (array==null||array.length==0) {
			returnValue = true;
		}else{
			returnValue = false;
		}
		return returnValue;
	} 
	
	public static boolean isNotEmpty(Object[] array){
		return !isEmpty(array);
	}

}
