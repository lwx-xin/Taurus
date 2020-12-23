package org.taurus.common.token_entity;

import java.util.List;

public class UserToken {
	
	private List<String> authIdList;
	
	private String userId;

	public List<String> getAuthIdList() {
		return authIdList;
	}

	public void setAuthIdList(List<String> authIdList) {
		this.authIdList = authIdList;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
