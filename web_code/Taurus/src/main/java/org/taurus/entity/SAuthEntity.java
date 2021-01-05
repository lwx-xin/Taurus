package org.taurus.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 系统权限
 * </p>
 *
 * @author 欣
 * @since 2020-11-09
 */
@TableName("s_auth")
public class SAuthEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 权限id
	 */
	@TableId("AUTH_ID")
	private String authId;

	/**
	 * 权限名称
	 */
	@TableField("AUTH_NAME")
	private String authName;

	/**
	 * 权限级别
	 */
	@TableField("AUTH_LEVEL")
	private String authLevel;

	/**
	 * 删除标识
	 */
	@TableField("AUTH_DEL_FLG")
	private String authDelFlg;

	/**
	 * 创建时间
	 */
	@TableField("AUTH_CREATE_TIME")
	private LocalDateTime authCreateTime;

	/**
	 * 创建者
	 */
	@TableField("AUTH_CREATE_USER")
	private String authCreateUser;

	/**
	 * 编辑时间
	 */
	@TableField("AUTH_MODIFY_TIME")
	private LocalDateTime authModifyTime;

	/**
	 * 编辑者
	 */
	@TableField("AUTH_MODIFY_USER")
	private String authModifyUser;

	public String getAuthId() {
		return authId;
	}

	public void setAuthId(String authId) {
		this.authId = authId;
	}

	public String getAuthName() {
		return authName;
	}

	public void setAuthName(String authName) {
		this.authName = authName;
	}

	public String getAuthLevel() {
		return authLevel;
	}

	public void setAuthLevel(String authLevel) {
		this.authLevel = authLevel;
	}

	public String getAuthDelFlg() {
		return authDelFlg;
	}

	public void setAuthDelFlg(String authDelFlg) {
		this.authDelFlg = authDelFlg;
	}

	public LocalDateTime getAuthCreateTime() {
		return authCreateTime;
	}

	public void setAuthCreateTime(LocalDateTime authCreateTime) {
		this.authCreateTime = authCreateTime;
	}

	public String getAuthCreateUser() {
		return authCreateUser;
	}

	public void setAuthCreateUser(String authCreateUser) {
		this.authCreateUser = authCreateUser;
	}

	public LocalDateTime getAuthModifyTime() {
		return authModifyTime;
	}

	public void setAuthModifyTime(LocalDateTime authModifyTime) {
		this.authModifyTime = authModifyTime;
	}

	public String getAuthModifyUser() {
		return authModifyUser;
	}

	public void setAuthModifyUser(String authModifyUser) {
		this.authModifyUser = authModifyUser;
	}

	@Override
	public String toString() {
		return "SAuthEntity{" + "authId=" + authId + ", authName=" + authName + ", authLevel=" + authLevel
				+ ", authDelFlg=" + authDelFlg + ", authCreateTime=" + authCreateTime + ", authCreateUser="
				+ authCreateUser + ", authModifyTime=" + authModifyTime + ", authModifyUser=" + authModifyUser + "}";
	}
}
