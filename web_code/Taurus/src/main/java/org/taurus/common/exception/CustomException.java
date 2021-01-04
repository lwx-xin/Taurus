package org.taurus.common.exception;

import org.taurus.common.code.ExecptionType;

public class CustomException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private ExecptionType execptionType;
	
	private String message;
	
	private String remark;

	public CustomException(ExecptionType execptionType, String message, String remark) {
		super();
		this.execptionType = execptionType;
		this.message = message;
		this.remark = remark;
	}

	public ExecptionType getExecptionType() {
		return execptionType;
	}

	public void setExecptionType(ExecptionType execptionType) {
		this.execptionType = execptionType;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "CustomException [execptionType=" + execptionType + ", message=" + message + ", remark=" + remark + "]";
	}

}
