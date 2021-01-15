package org.taurus.extendEntity;

import org.taurus.entity.SUrlEntity;
import org.taurus.entity.SUrlParamEntity;

public class SUrlParamEntityEx extends SUrlParamEntity {

	private static final long serialVersionUID = 1L;
	
	private SUrlEntity urlEntity;

	public SUrlEntity getUrlEntity() {
		return urlEntity;
	}

	public void setUrlEntity(SUrlEntity urlEntity) {
		this.urlEntity = urlEntity;
	}

	
}
