package org.taurus.extendEntity;

import java.util.List;

import org.taurus.entity.SUrlEntity;

public class SUrlEntityEx extends SUrlEntity {

	private static final long serialVersionUID = 1L;
	
	private List<SAuthEntityEx> authList;

	public List<SAuthEntityEx> getAuthList() {
		return authList;
	}

	public void setAuthList(List<SAuthEntityEx> authList) {
		this.authList = authList;
	}

}
