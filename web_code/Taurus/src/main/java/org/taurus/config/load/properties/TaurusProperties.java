package org.taurus.config.load.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;

@Order(1) // value越小越先加载
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

	/**
	 * 系统顶级管理员权限
	 */
	@Value("${system.admin.auth.id}")
	private String adminAuthId;
	@Value("${system.admin.auth.name}")
	private String adminAuthName;
	@Value("${system.admin.auth.level}")
	private String adminAuthLevel;

	/**
	 * 上传文件根目录
	 */
	@Value("${system.folder.root}")
	private String folderRoot;
	/**
	 * 上传文件根目录虚拟路径
	 */
	@Value("${system.folder.root.virtual}")
	private String folderRootVirtual;

	/**
	 * 系统资源目录
	 */
	@Value("${system.folder.system.name}")
	private String folderSystemName;
	/**
	 * 用户头像目录
	 */
	@Value("${system.folder.headImg.name}")
	private String folderHeadImgName;
	/**
	 * 日志目录
	 */
	@Value("${system.folder.log.name}")
	private String folderLogName;

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

	public String getAdminAuthId() {
		return adminAuthId;
	}

	public void setAdminAuthId(String adminAuthId) {
		this.adminAuthId = adminAuthId;
	}

	public String getAdminAuthName() {
		return adminAuthName;
	}

	public void setAdminAuthName(String adminAuthName) {
		this.adminAuthName = adminAuthName;
	}

	public String getAdminAuthLevel() {
		return adminAuthLevel;
	}

	public void setAdminAuthLevel(String adminAuthLevel) {
		this.adminAuthLevel = adminAuthLevel;
	}

	public String getFolderRoot() {
		return folderRoot;
	}

	public void setFolderRoot(String folderRoot) {
		this.folderRoot = folderRoot;
	}

	public String getFolderRootVirtual() {
		return folderRootVirtual;
	}

	public void setFolderRootVirtual(String folderRootVirtual) {
		this.folderRootVirtual = folderRootVirtual;
	}

	public String getFolderSystemName() {
		return folderSystemName;
	}

	public void setFolderSystemName(String folderSystemName) {
		this.folderSystemName = folderSystemName;
	}

	public String getFolderHeadImgName() {
		return folderHeadImgName;
	}

	public void setFolderHeadImgName(String folderHeadImgName) {
		this.folderHeadImgName = folderHeadImgName;
	}

	public String getFolderLogName() {
		return folderLogName;
	}

	public void setFolderLogName(String folderLogName) {
		this.folderLogName = folderLogName;
	}

}
