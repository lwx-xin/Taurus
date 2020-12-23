package org.taurus.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 系统请求
 * </p>
 *
 * @author 欣
 * @since 2020-11-09
 */
@TableName("s_url")
public class SUrlEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 请求id
     */
    @TableId("URL_ID")
    private String urlId;

    /**
     * 请求地址
     */
    @TableField("URL_PATH")
    private String urlPath;

    /**
     * 请求方式(get,post)
     */
    @TableField("URL_TYPE")
    private String urlType;

    /**
     * 请求参数(json格式)
     */
    @TableField("URL_PARAMS")
    private String urlParams;

    /**
     * 系统平台(pc,移动端)
     */
    @TableField("URL_PLATFORM")
    private String urlPlatform;

    /**
     * 备注
     */
    @TableField("URL_REMARKS")
    private String urlRemarks;

    /**
     * 删除标识
     */
    @TableField("URL_DEL_FLG")
    private String urlDelFlg;

    /**
     * 创建时间
     */
    @TableField("URL_CREATE_TIME")
    private LocalDateTime urlCreateTime;

    /**
     * 创建者
     */
    @TableField("URL_CREATE_USER")
    private String urlCreateUser;

    /**
     * 编辑时间
     */
    @TableField("URL_MODIFY_TIME")
    private LocalDateTime urlModifyTime;

    /**
     * 编辑者
     */
    @TableField("URL_MODIFY_USER")
    private String urlModifyUser;

    public String getUrlId() {
        return urlId;
    }

    public void setUrlId(String urlId) {
        this.urlId = urlId;
    }
    public String getUrlPath() {
        return urlPath;
    }

    public void setUrlPath(String urlPath) {
        this.urlPath = urlPath;
    }
    public String getUrlType() {
        return urlType;
    }

    public void setUrlType(String urlType) {
        this.urlType = urlType;
    }
    public String getUrlParams() {
        return urlParams;
    }

    public void setUrlParams(String urlParams) {
        this.urlParams = urlParams;
    }
    public String getUrlPlatform() {
        return urlPlatform;
    }

    public void setUrlPlatform(String urlPlatform) {
        this.urlPlatform = urlPlatform;
    }
    public String getUrlRemarks() {
        return urlRemarks;
    }

    public void setUrlRemarks(String urlRemarks) {
        this.urlRemarks = urlRemarks;
    }
    public String getUrlDelFlg() {
        return urlDelFlg;
    }

    public void setUrlDelFlg(String urlDelFlg) {
        this.urlDelFlg = urlDelFlg;
    }
    public LocalDateTime getUrlCreateTime() {
        return urlCreateTime;
    }

    public void setUrlCreateTime(LocalDateTime urlCreateTime) {
        this.urlCreateTime = urlCreateTime;
    }
    public String getUrlCreateUser() {
        return urlCreateUser;
    }

    public void setUrlCreateUser(String urlCreateUser) {
        this.urlCreateUser = urlCreateUser;
    }
    public LocalDateTime getUrlModifyTime() {
        return urlModifyTime;
    }

    public void setUrlModifyTime(LocalDateTime urlModifyTime) {
        this.urlModifyTime = urlModifyTime;
    }
    public String getUrlModifyUser() {
        return urlModifyUser;
    }

    public void setUrlModifyUser(String urlModifyUser) {
        this.urlModifyUser = urlModifyUser;
    }

    @Override
    public String toString() {
        return "SUrlEntity{" +
            "urlId=" + urlId +
            ", urlPath=" + urlPath +
            ", urlType=" + urlType +
            ", urlParams=" + urlParams +
            ", urlPlatform=" + urlPlatform +
            ", urlRemarks=" + urlRemarks +
            ", urlDelFlg=" + urlDelFlg +
            ", urlCreateTime=" + urlCreateTime +
            ", urlCreateUser=" + urlCreateUser +
            ", urlModifyTime=" + urlModifyTime +
            ", urlModifyUser=" + urlModifyUser +
        "}";
    }
}
