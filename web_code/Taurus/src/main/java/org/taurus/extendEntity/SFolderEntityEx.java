package org.taurus.extendEntity;

import java.util.List;

import org.taurus.entity.SFolderEntity;

public class SFolderEntityEx extends SFolderEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 子菜单
	 */
	private List<SFolderEntityEx> childrens;

	/**
	 * 该目录下的文件
	 */
	private List<SFileEntityEx> childrenFiles;

	public List<SFolderEntityEx> getChildrens() {
		return childrens;
	}

	public void setChildrens(List<SFolderEntityEx> childrens) {
		this.childrens = childrens;
	}

	public List<SFileEntityEx> getChildrenFiles() {
		return childrenFiles;
	}

	public void setChildrenFiles(List<SFileEntityEx> childrenFiles) {
		this.childrenFiles = childrenFiles;
	}
}
