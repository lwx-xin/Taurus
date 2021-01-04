package org.taurus.common.result;

import java.io.Serializable;

import org.taurus.common.code.CheckCode;

public class Result<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private T data;
	
	private boolean status;
	
	private String errCode;
	
	private String message;
	
	public Result(T data, boolean status, CheckCode code) {
		super();
		this.data = data;
		this.status = status;
		this.errCode = code.getValue();
		this.message = code.getName();
	}
	
	public Result(T data, boolean status, String errCode, String message) {
		super();
		this.data = data;
		this.status = status;
		this.errCode = errCode;
		this.message = message;
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
