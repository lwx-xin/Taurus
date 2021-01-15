package org.taurus.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 系统菜单
 * </p>
 *
 * @author 欣
 * @since 2021-01-13
 */
@TableName("s_menu")
public class SMenuEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 菜单id
	 */
	@TableId("MENU_ID")
	private String menuId;

	/**
	 * 文本
	 */
	@TableField("MENU_TEXT")
	private String menuText;

	/**
	 * 父菜单
	 */
	@TableField("MENU_PARENT")
	private String menuParent;

	/**
	 * 顺序
	 */
	@TableField("MENU_ORDER")
	private Integer menuOrder;

	/**
	 * 点击菜单发出的请求(请求id)
	 */
	@TableField("MENU_URL")
	private String menuUrl;

	/**
	 * 是否为菜单组
	 */
	@TableField("MENU_GROUP")
	private String menuGroup;

	/**
	 * 菜单图标
	 */
	@TableField("MENU_ICON")
	private String menuIcon;

	/**
	 * 删除标识
	 */
	@TableField("MENU_DEL_FLG")
	private String menuDelFlg;

	/**
	 * 创建时间
	 */
	@TableField("MENU_CREATE_TIME")
	private LocalDateTime menuCreateTime;

	/**
	 * 创建者
	 */
	@TableField("MENU_CREATE_USER")
	private String menuCreateUser;

	/**
	 * 编辑时间
	 */
	@TableField("MENU_MODIFY_TIME")
	private LocalDateTime menuModifyTime;

	/**
	 * 编辑者
	 */
	@TableField("MENU_MODIFY_USER")
	private String menuModifyUser;

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getMenuText() {
		return menuText;
	}

	public void setMenuText(String menuText) {
		this.menuText = menuText;
	}

	public String getMenuParent() {
		return menuParent;
	}

	public void setMenuParent(String menuParent) {
		this.menuParent = menuParent;
	}

	public Integer getMenuOrder() {
		return menuOrder;
	}

	public void setMenuOrder(Integer menuOrder) {
		this.menuOrder = menuOrder;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public String getMenuGroup() {
		return menuGroup;
	}

	public void setMenuGroup(String menuGroup) {
		this.menuGroup = menuGroup;
	}

	public String getMenuIcon() {
		return menuIcon;
	}

	public void setMenuIcon(String menuIcon) {
		this.menuIcon = menuIcon;
	}

	public String getMenuDelFlg() {
		return menuDelFlg;
	}

	public void setMenuDelFlg(String menuDelFlg) {
		this.menuDelFlg = menuDelFlg;
	}

	public LocalDateTime getMenuCreateTime() {
		return menuCreateTime;
	}

	public void setMenuCreateTime(LocalDateTime menuCreateTime) {
		this.menuCreateTime = menuCreateTime;
	}

	public String getMenuCreateUser() {
		return menuCreateUser;
	}

	public void setMenuCreateUser(String menuCreateUser) {
		this.menuCreateUser = menuCreateUser;
	}

	public LocalDateTime getMenuModifyTime() {
		return menuModifyTime;
	}

	public void setMenuModifyTime(LocalDateTime menuModifyTime) {
		this.menuModifyTime = menuModifyTime;
	}

	public String getMenuModifyUser() {
		return menuModifyUser;
	}

	public void setMenuModifyUser(String menuModifyUser) {
		this.menuModifyUser = menuModifyUser;
	}

	@Override
	public String toString() {
		return "SMenuEntity{" + "menuId=" + menuId + ", menuText=" + menuText + ", menuParent=" + menuParent
				+ ", menuOrder=" + menuOrder + ", menuUrl=" + menuUrl + ", menuGroup=" + menuGroup + ", menuIcon="
				+ menuIcon + ", menuDelFlg=" + menuDelFlg + ", menuCreateTime=" + menuCreateTime + ", menuCreateUser="
				+ menuCreateUser + ", menuModifyTime=" + menuModifyTime + ", menuModifyUser=" + menuModifyUser + "}";
	}
}
