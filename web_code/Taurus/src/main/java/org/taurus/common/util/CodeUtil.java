package org.taurus.common.util;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import org.taurus.common.Code;
import org.taurus.entity.MCodeEntity;

public class CodeUtil {
    
    /**
     * 获取当前分组下的code列表
     * @param groupName
     * @return
     */
    public static List<MCodeEntity> getByGroup(String group){
        List<MCodeEntity> codeList = new ArrayList<MCodeEntity>();
        
        EnumSet<Code> codes = EnumSet.allOf(Code.class);
        if (codes==null || codes.size()==0) {
            return codeList;
        }
        
        for (Code code : codes) {
            if (group.equals(code.getGroup())) {
                MCodeEntity codeEntity = new MCodeEntity();
                codeEntity.setCodeName(code.getName());
                codeEntity.setCodeValue(code.getValue());
                codeList.add(codeEntity);
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
    	
    	List<MCodeEntity> codes = getByGroup(group);
    	if (codes!=null && codes.size()>0) {
			for (MCodeEntity codeEntity : codes) {
				String codeName = codeEntity.getCodeName();
				String codeValue = codeEntity.getCodeValue();
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
    	
    	List<MCodeEntity> codes = getByGroup(group);
    	if (codes!=null && codes.size()>0) {
			for (MCodeEntity codeEntity : codes) {
				String codeName = codeEntity.getCodeName();
				String codeValue = codeEntity.getCodeValue();
				if (codeName.equals(name)) {
					value = codeValue;
					break;
				}
			}
		}
    	return value;
    }

}
