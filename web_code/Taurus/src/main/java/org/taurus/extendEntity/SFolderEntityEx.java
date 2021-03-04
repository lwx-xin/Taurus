package org.taurus.extendEntity;

import java.util.List;

import org.taurus.entity.SFolderEntity;

public class SFolderEntityEx extends SFolderEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 子菜单
	 */
	private List<SFolderEntityEx> childrens;

	public List<SFolderEntityEx> getChildrens() {
		return childrens;
	}

	public void setChildrens(List<SFolderEntityEx> childrens) {
		this.childrens = childrens;
	}

}
