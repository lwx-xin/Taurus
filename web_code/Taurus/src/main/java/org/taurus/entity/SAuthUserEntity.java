package org.taurus.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 用户权限
 * </p>
 *
 * @author 欣
 * @since 2020-11-09
 */
@TableName("s_auth_user")
public class SAuthUserEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 用户权限id
	 */
	@TableId("AUTH_USER_ID")
	private String authUserId;

	/**
	 * 权限id
	 */
	@TableField("AUTH_ID")
	private String authId;

	/**
	 * 用户id
	 */
	@TableField("USER_ID")
	private String userId;

	/**
	 * 删除标识
	 */
	@TableField("AUTH_USER_DEL_FLG")
	private String authUserDelFlg;

	/**
	 * 创建时间
	 */
	@TableField("AUTH_USER_CREATE_TIME")
	private LocalDateTime authUserCreateTime;

	/**
	 * 创建者
	 */
	@TableField("AUTH_USER_CREATE_USER")
	private String authUserCreateUser;

	/**
	 * 编辑时间
	 */
	@TableField("AUTH_USER_MODIFY_TIME")
	private LocalDateTime authUserModifyTime;

	/**
	 * 编辑者
	 */
	@TableField("AUTH_USER_MODIFY_USER")
	private String authUserModifyUser;

	public String getAuthUserId() {
		return authUserId;
	}

	public void setAuthUserId(String authUserId) {
		this.authUserId = authUserId;
	}

	public String getAuthId() {
		return authId;
	}

	public void setAuthId(String authId) {
		this.authId = authId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAuthUserDelFlg() {
		return authUserDelFlg;
	}

	public void setAuthUserDelFlg(String authUserDelFlg) {
		this.authUserDelFlg = authUserDelFlg;
	}

	public LocalDateTime getAuthUserCreateTime() {
		return authUserCreateTime;
	}

	public void setAuthUserCreateTime(LocalDateTime authUserCreateTime) {
		this.authUserCreateTime = authUserCreateTime;
	}

	public String getAuthUserCreateUser() {
		return authUserCreateUser;
	}

	public void setAuthUserCreateUser(String authUserCreateUser) {
		this.authUserCreateUser = authUserCreateUser;
	}

	public LocalDateTime getAuthUserModifyTime() {
		return authUserModifyTime;
	}

	public void setAuthUserModifyTime(LocalDateTime authUserModifyTime) {
		this.authUserModifyTime = authUserModifyTime;
	}

	public String getAuthUserModifyUser() {
		return authUserModifyUser;
	}

	public void setAuthUserModifyUser(String authUserModifyUser) {
		this.authUserModifyUser = authUserModifyUser;
	}

	@Override
	public String toString() {
		return "SAuthUserEntity{" + "authUserId=" + authUserId + ", authId=" + authId + ", userId=" + userId
				+ ", authUserDelFlg=" + authUserDelFlg + ", authUserCreateTime=" + authUserCreateTime
				+ ", authUserCreateUser=" + authUserCreateUser + ", authUserModifyTime=" + authUserModifyTime
				+ ", authUserModifyUser=" + authUserModifyUser + "}";
	}
}
