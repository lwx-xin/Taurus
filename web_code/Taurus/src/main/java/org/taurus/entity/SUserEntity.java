package org.taurus.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 系统用户
 * </p>
 *
 * @author 欣
 * @since 2020-11-09
 */
@TableName("s_user")
public class SUserEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @TableId("USER_ID")
    private String userId;

    /**
     * 账号
     */
    @TableField("USER_NUMBER")
    private String userNumber;

    /**
     * 密码
     */
    @TableField("USER_PWD")
    private String userPwd;

    /**
     * 昵称
     */
    @TableField("USER_NAME")
    private String userName;

    /**
     * 头像(文件id)
     */
    @TableField("USER_HEAD")
    private String userHead;

    /**
     * 账号能登陆的平台(pc,移动端)
     */
    @TableField("USER_PLATFORM")
    private String userPlatform;

    /**
     * QQ
     */
    @TableField("USER_QQ")
    private String userQq;

    /**
     * 邮箱
     */
    @TableField("USER_EMAIL")
    private String userEmail;

    /**
     * 删除标识
     */
    @TableField("USER_DEL_FLG")
    private String userDelFlg;

    /**
     * 创建时间
     */
    @TableField("USER_CREATE_TIME")
    private LocalDateTime userCreateTime;

    /**
     * 创建者
     */
    @TableField("USER_CREATE_USER")
    private String userCreateUser;

    /**
     * 编辑时间
     */
    @TableField("USER_MODIFY_TIME")
    private LocalDateTime userModifyTime;

    /**
     * 编辑者
     */
    @TableField("USER_MODIFY_USER")
    private String userModifyUser;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }
    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserHead() {
        return userHead;
    }

    public void setUserHead(String userHead) {
        this.userHead = userHead;
    }
    public String getUserPlatform() {
        return userPlatform;
    }

    public void setUserPlatform(String userPlatform) {
        this.userPlatform = userPlatform;
    }
    public String getUserQq() {
        return userQq;
    }

    public void setUserQq(String userQq) {
        this.userQq = userQq;
    }
    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    public String getUserDelFlg() {
        return userDelFlg;
    }

    public void setUserDelFlg(String userDelFlg) {
        this.userDelFlg = userDelFlg;
    }
    public LocalDateTime getUserCreateTime() {
        return userCreateTime;
    }

    public void setUserCreateTime(LocalDateTime userCreateTime) {
        this.userCreateTime = userCreateTime;
    }
    public String getUserCreateUser() {
        return userCreateUser;
    }

    public void setUserCreateUser(String userCreateUser) {
        this.userCreateUser = userCreateUser;
    }
    public LocalDateTime getUserModifyTime() {
        return userModifyTime;
    }

    public void setUserModifyTime(LocalDateTime userModifyTime) {
        this.userModifyTime = userModifyTime;
    }
    public String getUserModifyUser() {
        return userModifyUser;
    }

    public void setUserModifyUser(String userModifyUser) {
        this.userModifyUser = userModifyUser;
    }

    @Override
    public String toString() {
        return "SUserEntity{" +
            "userId=" + userId +
            ", userNumber=" + userNumber +
            ", userPwd=" + userPwd +
            ", userName=" + userName +
            ", userHead=" + userHead +
            ", userPlatform=" + userPlatform +
            ", userQq=" + userQq +
            ", userEmail=" + userEmail +
            ", userDelFlg=" + userDelFlg +
            ", userCreateTime=" + userCreateTime +
            ", userCreateUser=" + userCreateUser +
            ", userModifyTime=" + userModifyTime +
            ", userModifyUser=" + userModifyUser +
        "}";
    }
}
