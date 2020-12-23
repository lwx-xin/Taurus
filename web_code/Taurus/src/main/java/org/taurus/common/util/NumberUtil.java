package org.taurus.common.util;

import java.math.BigDecimal;

public class NumberUtil {

    /**
     * String --> BigDecimal
     * 
     * @param str
     * @param bigDecimal 字符串等于空时返回的值
     * @return
     */
    public static BigDecimal format(String str, BigDecimal bigDecimal) {
        if (str != null && !"".equals(str) && !"".equals(str.trim())) {
            return new BigDecimal(str);
        }
        return bigDecimal;
    }

    /**
     * String --> Integer
     * 
     * @param str
     * @param integer 字符串等于空时返回的值
     * @return
     */
    public static Integer format(String str, Integer integer) {
        if (str != null && !"".equals(str) && !"".equals(str.trim())) {
            return Integer.parseInt(str);
        }
        return integer;
    }

}
