package org.taurus.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 请求参数
 * </p>
 *
 * @author 欣
 * @since 2021-01-12
 */
@TableName("s_url_param")
public class SUrlParamEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 请求参数id
	 */
	@TableId("URL_PARAM_ID")
	private String urlParamId;

	/**
	 * 请求id
	 */
	@TableField("URL_ID")
	private String urlId;

	/**
	 * 参数名
	 */
	@TableField("URL_PARAM_NAME")
	private String urlParamName;

	/**
	 * 参数值，正则表达式
	 */
	@TableField("URL_PARAM_VALUE")
	private String urlParamValue;

	/**
	 * 是否为必须参数
	 */
	@TableField("URL_PARAM_REQUIRED")
	private String urlParamRequired;

	/**
	 * 是否允许为空值
	 */
	@TableField("URL_PARAM_NULL")
	private String urlParamNull;

	/**
	 * 参数备注
	 */
	@TableField("URL_PARAM_REMARK")
	private String urlParamRemark;

	/**
	 * 参数异常时候的提示
	 */
	@TableField("URL_PARAM_ERR_HINT")
	private String urlParamErrHint;

	/**
	 * 删除标识
	 */
	@TableField("URL_PARAM_DEL_FLG")
	private String urlParamDelFlg;

	/**
	 * 创建时间
	 */
	@TableField("URL_PARAM_CREATE_TIME")
	private LocalDateTime urlParamCreateTime;

	/**
	 * 创建者
	 */
	@TableField("URL_PARAM_CREATE_USER")
	private String urlParamCreateUser;

	/**
	 * 编辑事件
	 */
	@TableField("URL_PARAM_MODIFY_TIME")
	private LocalDateTime urlParamModifyTime;

	/**
	 * 编辑者
	 */
	@TableField("URL_PARAM_MODIFY_USER")
	private String urlParamModifyUser;

	public String getUrlParamId() {
		return urlParamId;
	}

	public void setUrlParamId(String urlParamId) {
		this.urlParamId = urlParamId;
	}

	public String getUrlId() {
		return urlId;
	}

	public void setUrlId(String urlId) {
		this.urlId = urlId;
	}

	public String getUrlParamName() {
		return urlParamName;
	}

	public void setUrlParamName(String urlParamName) {
		this.urlParamName = urlParamName;
	}

	public String getUrlParamValue() {
		return urlParamValue;
	}

	public void setUrlParamValue(String urlParamValue) {
		this.urlParamValue = urlParamValue;
	}

	public String getUrlParamRequired() {
		return urlParamRequired;
	}

	public void setUrlParamRequired(String urlParamRequired) {
		this.urlParamRequired = urlParamRequired;
	}

	public String getUrlParamNull() {
		return urlParamNull;
	}

	public void setUrlParamNull(String urlParamNull) {
		this.urlParamNull = urlParamNull;
	}

	public String getUrlParamRemark() {
		return urlParamRemark;
	}

	public void setUrlParamRemark(String urlParamRemark) {
		this.urlParamRemark = urlParamRemark;
	}

	public String getUrlParamErrHint() {
		return urlParamErrHint;
	}

	public void setUrlParamErrHint(String urlParamErrHint) {
		this.urlParamErrHint = urlParamErrHint;
	}

	public String getUrlParamDelFlg() {
		return urlParamDelFlg;
	}

	public void setUrlParamDelFlg(String urlParamDelFlg) {
		this.urlParamDelFlg = urlParamDelFlg;
	}

	public LocalDateTime getUrlParamCreateTime() {
		return urlParamCreateTime;
	}

	public void setUrlParamCreateTime(LocalDateTime urlParamCreateTime) {
		this.urlParamCreateTime = urlParamCreateTime;
	}

	public String getUrlParamCreateUser() {
		return urlParamCreateUser;
	}

	public void setUrlParamCreateUser(String urlParamCreateUser) {
		this.urlParamCreateUser = urlParamCreateUser;
	}

	public LocalDateTime getUrlParamModifyTime() {
		return urlParamModifyTime;
	}

	public void setUrlParamModifyTime(LocalDateTime urlParamModifyTime) {
		this.urlParamModifyTime = urlParamModifyTime;
	}

	public String getUrlParamModifyUser() {
		return urlParamModifyUser;
	}

	public void setUrlParamModifyUser(String urlParamModifyUser) {
		this.urlParamModifyUser = urlParamModifyUser;
	}

	@Override
	public String toString() {
		return "SUrlParamEntity{" + "urlParamId=" + urlParamId + ", urlId=" + urlId + ", urlParamName=" + urlParamName
				+ ", urlParamValue=" + urlParamValue + ", urlParamRequired=" + urlParamRequired + ", urlParamNull="
				+ urlParamNull + ", urlParamRemark=" + urlParamRemark + ", urlParamErrHint=" + urlParamErrHint
				+ ", urlParamDelFlg=" + urlParamDelFlg + ", urlParamCreateTime=" + urlParamCreateTime
				+ ", urlParamCreateUser=" + urlParamCreateUser + ", urlParamModifyTime=" + urlParamModifyTime
				+ ", urlParamModifyUser=" + urlParamModifyUser + "}";
	}
}
