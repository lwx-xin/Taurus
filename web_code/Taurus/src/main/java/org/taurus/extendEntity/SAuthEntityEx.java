package org.taurus.extendEntity;

import java.util.List;

import org.taurus.entity.SAuthEntity;
import org.taurus.entity.SUrlEntity;

public class SAuthEntityEx extends SAuthEntity {

	private static final long serialVersionUID = 1L;
	
	private List<SUrlEntity> urlList;

	public List<SUrlEntity> getUrlList() {
		return urlList;
	}

	public void setUrlList(List<SUrlEntity> urlList) {
		this.urlList = urlList;
	}
	
}
