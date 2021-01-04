package org.taurus.common.result;

import java.io.Serializable;

import org.taurus.common.code.CheckCode;

/**
 * 令牌验证结果
 * @author 祈
 *
 */
public class TokenVerifyResult implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Object data;
	
	private boolean success;
	
	private String errCode;
	
	private String message;
	
    public TokenVerifyResult(Object data, CheckCode code) {
		super();
		this.data = data;
		this.success = false;
		this.errCode = code.getValue();
		this.message = code.getName();
	}
	
    public TokenVerifyResult(Object data) {
		super();
		this.data = data;
		this.success = true;
		this.errCode = CheckCode.TOKEN_SUCCESS.getValue();
		this.message = CheckCode.TOKEN_SUCCESS.getName();
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
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
        return "TokenVerifyResult{" +
            "data=" + this.data +
            ", success=" + this.success +
            ", errCode=" + this.errCode +
            ", message=" + this.message +
        "}";
    }
}