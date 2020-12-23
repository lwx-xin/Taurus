package org.taurus.common.load.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;

@Order(2) // value越小越先加载
@PropertySource(value = { "classpath:taurus.properties" }, encoding = "utf-8")
@Configuration
public class TaurusProperties {

	/**
	 * 系统顶级账号
	 */
	@Value("${system.account.id}")
	private String accountId;
	@Value("${system.account.number}")
	private String accountNumber;
	@Value("${system.account.pwd}")
	private String accountPwd;
	@Value("${system.account.name}")
	private String accountName;
	@Value("${system.account.head}")
	private String accountHead;
	@Value("${system.account.platform}")
	private String accountPlatform;
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getAccountPwd() {
		return accountPwd;
	}
	public void setAccountPwd(String accountPwd) {
		this.accountPwd = accountPwd;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getAccountHead() {
		return accountHead;
	}
	public void setAccountHead(String accountHead) {
		this.accountHead = accountHead;
	}
	public String getAccountPlatform() {
		return accountPlatform;
	}
	public void setAccountPlatform(String accountPlatform) {
		this.accountPlatform = accountPlatform;
	}

	

}
