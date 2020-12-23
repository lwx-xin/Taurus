package org.taurus.common.result;

import java.io.Serializable;

import org.taurus.common.Code;

public class Result<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private T data;
	
	private boolean status;
	
	private String errCode;
	
	private String message;
	
    public Result(T data, Code errCode) {
		super();
		this.data = data;
		this.status = false;
		this.errCode = errCode.getValue();
		this.message = errCode.getName();
	}
	
    public Result(T data, Boolean status, Code errCode) {
		super();
		this.data = data;
		this.status = status;
		this.errCode = errCode.getValue();
		this.message = errCode.getName();
	}
	
    public Result(Boolean status, Code errCode) {
		super();
		this.data = null;
		this.status = status;
		this.errCode = errCode.getValue();
		this.message = errCode.getName();
	}
	
    public Result(T data) {
		super();
		this.data = data;
		this.status = true;
		this.errCode = Code.INTERFACE_ERR_CODE_0.getValue();
		this.message = Code.INTERFACE_ERR_CODE_0.getName();
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
    public String toString() {
        return "Result{" +
            "data=" + this.data +
            ", status=" + this.status +
            ", errCode=" + this.errCode +
            ", message=" + this.message +
        "}";
    }
}
