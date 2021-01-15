package org.taurus.extendEntity;

import java.util.List;

import org.taurus.entity.SAuthEntity;
import org.taurus.entity.SMenuEntity;
import org.taurus.entity.SUrlEntity;

public class SMenuEntityEx extends SMenuEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 子菜单
	 */
	private List<SMenuEntityEx> childrens;

	/**
	 * 菜单请求
	 */
	private SUrlEntity urlEntity;

	/**
	 * 菜单权限列表
	 */
	private List<SAuthEntity> authList;

	public List<SMenuEntityEx> getChildrens() {
		return childrens;
	}

	public void setChildrens(List<SMenuEntityEx> childrens) {
		this.childrens = childrens;
	}

	public SUrlEntity getUrlEntity() {
		return urlEntity;
	}

	public void setUrlEntity(SUrlEntity urlEntity) {
		this.urlEntity = urlEntity;
	}

	public List<SAuthEntity> getAuthList() {
		return authList;
	}

	public void setAuthList(List<SAuthEntity> authList) {
		this.authList = authList;
	}
}
