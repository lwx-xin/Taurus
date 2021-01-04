package org.taurus.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 用户登录记录
 * </p>
 *
 * @author 欣
 * @since 2020-11-09
 */
@TableName("s_user_login")
public class SUserLoginEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId("LOGIN_ID")
	private String loginId;

	/**
	 * 用户id
	 */
	@TableField("LOGIN_USER")
	private String loginUser;

	/**
	 * 登录时间
	 */
	@TableField("LOGIN_TIME")
	private LocalDateTime loginTime;

	/**
	 * 登录地点
	 */
	@TableField("LOGIN_ADDRESS")
	private String loginAddress;

	/**
	 * 登录平台(PC端,移动端)
	 */
	@TableField("LOGIN_PLATFORM")
	private String loginPlatform;

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getLoginUser() {
		return loginUser;
	}

	public void setLoginUser(String loginUser) {
		this.loginUser = loginUser;
	}

	public LocalDateTime getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(LocalDateTime loginTime) {
		this.loginTime = loginTime;
	}

	public String getLoginAddress() {
		return loginAddress;
	}

	public void setLoginAddress(String loginAddress) {
		this.loginAddress = loginAddress;
	}

	public String getLoginPlatform() {
		return loginPlatform;
	}

	public void setLoginPlatform(String loginPlatform) {
		this.loginPlatform = loginPlatform;
	}

	@Override
	public String toString() {
		return "SUserLoginEntity{" + "loginId=" + loginId + ", loginUser=" + loginUser + ", loginTime=" + loginTime
				+ ", loginAddress=" + loginAddress + ", loginPlatform=" + loginPlatform + "}";
	}
}
