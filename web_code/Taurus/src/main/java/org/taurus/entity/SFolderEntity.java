package org.taurus.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 文件夹信息
 * </p>
 *
 * @author 欣
 * @since 2020-12-29
 */
@TableName("s_folder")
public class SFolderEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文件夹ID
     */
    @TableId("FOLDER_ID")
    private String folderId;

    /**
     * 文件夹名称
     */
    @TableField("FOLDER_NAME")
    private String folderName;

    /**
     * 父文件夹
     */
    @TableField("FOLDER_PARENT")
    private String folderParent;

    /**
     * 文件夹所有者
     */
    @TableField("FOLDER_OWNER")
    private String folderOwner;

    /**
     * 删除标识
     */
    @TableField("FOLDER_DEL_FLG")
    private String folderDelFlg;

    /**
     * 创建时间
     */
    @TableField("FOLDER_CREATE_TIME")
    private LocalDateTime folderCreateTime;

    /**
     * 创建者
     */
    @TableField("FOLDER_CREATE_USER")
    private String folderCreateUser;

    /**
     * 编辑时间
     */
    @TableField("FOLDER_MODIFY_TIME")
    private LocalDateTime folderModifyTime;

    /**
     * 编辑者
     */
    @TableField("FOLDER_MODIFY_USER")
    private String folderModifyUser;

    public String getFolderId() {
        return folderId;
    }

    public void setFolderId(String folderId) {
        this.folderId = folderId;
    }
    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }
    public String getFolderParent() {
        return folderParent;
    }

    public void setFolderParent(String folderParent) {
        this.folderParent = folderParent;
    }
    public String getFolderOwner() {
        return folderOwner;
    }

    public void setFolderOwner(String folderOwner) {
        this.folderOwner = folderOwner;
    }
    public String getFolderDelFlg() {
        return folderDelFlg;
    }

    public void setFolderDelFlg(String folderDelFlg) {
        this.folderDelFlg = folderDelFlg;
    }
    public LocalDateTime getFolderCreateTime() {
        return folderCreateTime;
    }

    public void setFolderCreateTime(LocalDateTime folderCreateTime) {
        this.folderCreateTime = folderCreateTime;
    }
    public String getFolderCreateUser() {
        return folderCreateUser;
    }

    public void setFolderCreateUser(String folderCreateUser) {
        this.folderCreateUser = folderCreateUser;
    }
    public LocalDateTime getFolderModifyTime() {
        return folderModifyTime;
    }

    public void setFolderModifyTime(LocalDateTime folderModifyTime) {
        this.folderModifyTime = folderModifyTime;
    }
    public String getFolderModifyUser() {
        return folderModifyUser;
    }

    public void setFolderModifyUser(String folderModifyUser) {
        this.folderModifyUser = folderModifyUser;
    }

    @Override
    public String toString() {
        return "SFolderEntity{" +
            "folderId=" + folderId +
            ", folderName=" + folderName +
            ", folderParent=" + folderParent +
            ", folderOwner=" + folderOwner +
            ", folderDelFlg=" + folderDelFlg +
            ", folderCreateTime=" + folderCreateTime +
            ", folderCreateUser=" + folderCreateUser +
            ", folderModifyTime=" + folderModifyTime +
            ", folderModifyUser=" + folderModifyUser +
        "}";
    }
}
