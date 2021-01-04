package org.taurus.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 请求权限
 * </p>
 *
 * @author 欣
 * @since 2020-11-09
 */
@TableName("s_auth_url")
public class SAuthUrlEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 请求权限id
	 */
	@TableId("AUTH_URL_ID")
	private String authUrlId;

	/**
	 * 权限id
	 */
	@TableField("AUTH_ID")
	private String authId;

	/**
	 * 请求id
	 */
	@TableField("URL_ID")
	private String urlId;

	/**
	 * 删除标识
	 */
	@TableField("AUTH_URL_DEL_FLG")
	private String authUrlDelFlg;

	/**
	 * 创建时间
	 */
	@TableField("AUTH_URL_CREATE_TIME")
	private LocalDateTime authUrlCreateTime;

	/**
	 * 创建者
	 */
	@TableField("AUTH_URL_CREATE_USER")
	private String authUrlCreateUser;

	/**
	 * 编辑时间
	 */
	@TableField("AUTH_URL_MODIFY_TIME")
	private LocalDateTime authUrlModifyTime;

	/**
	 * 编辑者
	 */
	@TableField("AUTH_URL_MODIFY_USER")
	private String authUrlModifyUser;

	public String getAuthUrlId() {
		return authUrlId;
	}

	public void setAuthUrlId(String authUrlId) {
		this.authUrlId = authUrlId;
	}

	public String getAuthId() {
		return authId;
	}

	public void setAuthId(String authId) {
		this.authId = authId;
	}

	public String getUrlId() {
		return urlId;
	}

	public void setUrlId(String urlId) {
		this.urlId = urlId;
	}

	public String getAuthUrlDelFlg() {
		return authUrlDelFlg;
	}

	public void setAuthUrlDelFlg(String authUrlDelFlg) {
		this.authUrlDelFlg = authUrlDelFlg;
	}

	public LocalDateTime getAuthUrlCreateTime() {
		return authUrlCreateTime;
	}

	public void setAuthUrlCreateTime(LocalDateTime authUrlCreateTime) {
		this.authUrlCreateTime = authUrlCreateTime;
	}

	public String getAuthUrlCreateUser() {
		return authUrlCreateUser;
	}

	public void setAuthUrlCreateUser(String authUrlCreateUser) {
		this.authUrlCreateUser = authUrlCreateUser;
	}

	public LocalDateTime getAuthUrlModifyTime() {
		return authUrlModifyTime;
	}

	public void setAuthUrlModifyTime(LocalDateTime authUrlModifyTime) {
		this.authUrlModifyTime = authUrlModifyTime;
	}

	public String getAuthUrlModifyUser() {
		return authUrlModifyUser;
	}

	public void setAuthUrlModifyUser(String authUrlModifyUser) {
		this.authUrlModifyUser = authUrlModifyUser;
	}

	@Override
	public String toString() {
		return "SAuthUrlEntity{" + "authUrlId=" + authUrlId + ", authId=" + authId + ", urlId=" + urlId
				+ ", authUrlDelFlg=" + authUrlDelFlg + ", authUrlCreateTime=" + authUrlCreateTime
				+ ", authUrlCreateUser=" + authUrlCreateUser + ", authUrlModifyTime=" + authUrlModifyTime
				+ ", authUrlModifyUser=" + authUrlModifyUser + "}";
	}
}
