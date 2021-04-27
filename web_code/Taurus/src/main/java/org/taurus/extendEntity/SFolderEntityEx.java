package org.taurus.extendEntity;

import java.util.List;

import org.taurus.entity.SFolderEntity;

public class SFolderEntityEx extends SFolderEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 子菜单
	 */
	private List<SFolderEntityEx> childrens;
	private long childrenCount;

	/**
	 * 该目录下的文件
	 */
	private List<SFileEntityEx> childrenFiles;
	private long childrenFileCount;

	public List<SFolderEntityEx> getChildrens() {
		return childrens;
	}

	public void setChildrens(List<SFolderEntityEx> childrens) {
		this.childrens = childrens;
	}

	public List<SFileEntityEx> getChildrenFiles() {
		return childrenFiles;
	}

	public long getChildrenCount() {
		return childrenCount;
	}

	public void setChildrenCount(long childrenCount) {
		this.childrenCount = childrenCount;
	}

	public long getChildrenFileCount() {
		return childrenFileCount;
	}

	public void setChildrenFileCount(long childrenFileCount) {
		this.childrenFileCount = childrenFileCount;
	}

	public void setChildrenFiles(List<SFileEntityEx> childrenFiles) {
		this.childrenFiles = childrenFiles;
	}
}
