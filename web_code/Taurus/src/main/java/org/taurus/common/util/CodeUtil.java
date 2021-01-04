package org.taurus.common.util;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import org.taurus.common.code.Code;
import org.taurus.common.result.CodeElement;

public class CodeUtil {
    
    /**
     * 获取当前分组下的code列表
     * @param groupName
     * @return
     */
    public static List<CodeElement> getByGroup(String group){
        List<CodeElement> codeList = new ArrayList<CodeElement>();
        
        EnumSet<Code> codes = EnumSet.allOf(Code.class);
        if (group == null || "".equals(group) || codes==null || codes.size()==0) {
            return codeList;
        }
        
        for (Code code : codes) {
            if (group.equals(code.getGroup())) {
            	CodeElement element = new CodeElement();
            	element.setName(code.getName());
            	element.setValue(code.getValue());
                codeList.add(element);
            }
        }
        
        return codeList;
    }
    
    /**
     * 获取code的值
     * @param group
     * @param value
     * @return
     */
    public static String getCodeName(String group, String value) {
    	String name = "";
    	
    	if (group==null || "".equals(group) || value==null || "".equals(value)) {
			return name;
		}
    	
    	List<CodeElement> codes = getByGroup(group);
    	if (codes!=null && codes.size()>0) {
			for (CodeElement element : codes) {
				String codeName = element.getName();
				String codeValue = element.getValue();
				if (codeValue.equals(value)) {
					name = codeName;
					break;
				}
			}
		}
    	return name;
    }
    
    /**
     * 获取code的名称
     * @param group
     * @param name
     * @return
     */
    public static String getCodeValue(String group, String name) {
    	String value = "";
    	
    	if (group==null || "".equals(group) || name==null || "".equals(name)) {
			return value;
		}
    	
    	List<CodeElement> codes = getByGroup(group);
    	if (codes!=null && codes.size()>0) {
			for (CodeElement element : codes) {
				String codeName = element.getName();
				String codeValue = element.getValue();
				if (codeName.equals(name)) {
					value = codeValue;
					break;
				}
			}
		}
    	return value;
    }

}
