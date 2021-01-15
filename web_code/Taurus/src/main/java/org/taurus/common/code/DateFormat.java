package org.taurus.common.code;

public enum DateFormat {
	
	/**
	 * yyyy/MM/dd HH:mm:ss
	 */
	YYYY_MM_DD_HH_MM_SS("yyyy/MM/dd HH:mm:ss"),
	
	/**
	 * yyyy-MM-dd HH_mm_ss
	 */
	LOG_NAME("yyyy-MM-dd HH_mm_ss");
	
	private String value;
	
	private DateFormat(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
