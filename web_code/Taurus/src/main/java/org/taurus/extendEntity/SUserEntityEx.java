package org.taurus.extendEntity;

import java.util.List;

import org.taurus.entity.SUrlEntity;
import org.taurus.entity.SUserEntity;

public class SUserEntityEx extends SUserEntity {

	private static final long serialVersionUID = 1L;
	
	private List<SAuthEntityEx> authList;
	
	private List<SUrlEntity> urlList;
	
	private String headFilePath;

	public List<SAuthEntityEx> getAuthList() {
		return authList;
	}

	public void setAuthList(List<SAuthEntityEx> authList) {
		this.authList = authList;
	}

	public List<SUrlEntity> getUrlList() {
		return urlList;
	}

	public void setUrlList(List<SUrlEntity> urlList) {
		this.urlList = urlList;
	}

	public String getHeadFilePath() {
		return headFilePath;
	}

	public void setHeadFilePath(String headFilePath) {
		this.headFilePath = headFilePath;
	}

}
