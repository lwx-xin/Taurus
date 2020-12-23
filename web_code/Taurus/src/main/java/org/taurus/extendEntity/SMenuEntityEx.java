package org.taurus.extendEntity;

import java.util.List;

import org.taurus.entity.SMenuEntity;

public class SMenuEntityEx extends SMenuEntity {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 子菜单
	 */
	private List<SMenuEntityEx> childrens;

	public List<SMenuEntityEx> getChildrens() {
		return childrens;
	}

	public void setChildrens(List<SMenuEntityEx> childrens) {
		this.childrens = childrens;
	}

}
