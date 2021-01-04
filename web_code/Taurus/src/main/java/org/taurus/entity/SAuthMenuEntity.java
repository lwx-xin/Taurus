package org.taurus.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 菜单权限
 * </p>
 *
 * @author 欣
 * @since 2020-12-11
 */
@TableName("s_auth_menu")
public class SAuthMenuEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 菜单权限id
	 */
	@TableId("AUTH_MENU_ID")
	private String authMenuId;

	/**
	 * 权限id
	 */
	@TableField("AUTH_ID")
	private String authId;

	/**
	 * 菜单id
	 */
	@TableField("MENU_ID")
	private String menuId;

	/**
	 * 删除标识
	 */
	@TableField("AUTH_MENU_DEL_FLG")
	private String authMenuDelFlg;

	/**
	 * 创建时间
	 */
	@TableField("AUTH_MENU_CREATE_TIME")
	private LocalDateTime authMenuCreateTime;

	/**
	 * 创建者
	 */
	@TableField("AUTH_MENU_CREATE_USER")
	private String authMenuCreateUser;

	/**
	 * 编辑时间
	 */
	@TableField("AUTH_MENU_MODIFY_TIME")
	private LocalDateTime authMenuModifyTime;

	/**
	 * 编辑者
	 */
	@TableField("AUTH_MENU_MODIFY_USER")
	private String authMenuModifyUser;

	public String getAuthMenuId() {
		return authMenuId;
	}

	public void setAuthMenuId(String authMenuId) {
		this.authMenuId = authMenuId;
	}

	public String getAuthId() {
		return authId;
	}

	public void setAuthId(String authId) {
		this.authId = authId;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getAuthMenuDelFlg() {
		return authMenuDelFlg;
	}

	public void setAuthMenuDelFlg(String authMenuDelFlg) {
		this.authMenuDelFlg = authMenuDelFlg;
	}

	public LocalDateTime getAuthMenuCreateTime() {
		return authMenuCreateTime;
	}

	public void setAuthMenuCreateTime(LocalDateTime authMenuCreateTime) {
		this.authMenuCreateTime = authMenuCreateTime;
	}

	public String getAuthMenuCreateUser() {
		return authMenuCreateUser;
	}

	public void setAuthMenuCreateUser(String authMenuCreateUser) {
		this.authMenuCreateUser = authMenuCreateUser;
	}

	public LocalDateTime getAuthMenuModifyTime() {
		return authMenuModifyTime;
	}

	public void setAuthMenuModifyTime(LocalDateTime authMenuModifyTime) {
		this.authMenuModifyTime = authMenuModifyTime;
	}

	public String getAuthMenuModifyUser() {
		return authMenuModifyUser;
	}

	public void setAuthMenuModifyUser(String authMenuModifyUser) {
		this.authMenuModifyUser = authMenuModifyUser;
	}

	@Override
	public String toString() {
		return "SAuthMenuEntity{" + "authMenuId=" + authMenuId + ", authId=" + authId + ", menuId=" + menuId
				+ ", authMenuDelFlg=" + authMenuDelFlg + ", authMenuCreateTime=" + authMenuCreateTime
				+ ", authMenuCreateUser=" + authMenuCreateUser + ", authMenuModifyTime=" + authMenuModifyTime
				+ ", authMenuModifyUser=" + authMenuModifyUser + "}";
	}
}
