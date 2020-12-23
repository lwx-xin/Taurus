package org.taurus.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 系统code列表
 * </p>
 *
 * @author 欣
 * @since 2020-11-09
 */
@TableName("m_code")
public class MCodeEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * code id
     */
    @TableId("CODE_ID")
    private String codeId;

    /**
     * 分组
     */
    @TableField("CODE_GROUP")
    private String codeGroup;

    /**
     * 名称
     */
    @TableField("CODE_NAME")
    private String codeName;

    /**
     * 值
     */
    @TableField("CODE_VALUE")
    private String codeValue;

    /**
     * 顺序
     */
    @TableField("CODE_ORDER")
    private String codeOrder;

    /**
     * 删除标识
     */
    @TableField("CODE_DEL_FLG")
    private String codeDelFlg;

    /**
     * 创建时间
     */
    @TableField("CODE_CREATE_TIME")
    private LocalDateTime codeCreateTime;

    /**
     * 创建者
     */
    @TableField("CODE_CREATE_USER")
    private String codeCreateUser;

    /**
     * 编辑时间
     */
    @TableField("CODE_MODIFY_TIME")
    private LocalDateTime codeModifyTime;

    /**
     * 编辑者
     */
    @TableField("CODE_MODIFY_USER")
    private String codeModifyUser;

    public String getCodeId() {
        return codeId;
    }

    public void setCodeId(String codeId) {
        this.codeId = codeId;
    }
    public String getCodeGroup() {
        return codeGroup;
    }

    public void setCodeGroup(String codeGroup) {
        this.codeGroup = codeGroup;
    }
    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }
    public String getCodeValue() {
        return codeValue;
    }

    public void setCodeValue(String codeValue) {
        this.codeValue = codeValue;
    }
    public String getCodeOrder() {
        return codeOrder;
    }

    public void setCodeOrder(String codeOrder) {
        this.codeOrder = codeOrder;
    }
    public String getCodeDelFlg() {
        return codeDelFlg;
    }

    public void setCodeDelFlg(String codeDelFlg) {
        this.codeDelFlg = codeDelFlg;
    }
    public LocalDateTime getCodeCreateTime() {
        return codeCreateTime;
    }

    public void setCodeCreateTime(LocalDateTime codeCreateTime) {
        this.codeCreateTime = codeCreateTime;
    }
    public String getCodeCreateUser() {
        return codeCreateUser;
    }

    public void setCodeCreateUser(String codeCreateUser) {
        this.codeCreateUser = codeCreateUser;
    }
    public LocalDateTime getCodeModifyTime() {
        return codeModifyTime;
    }

    public void setCodeModifyTime(LocalDateTime codeModifyTime) {
        this.codeModifyTime = codeModifyTime;
    }
    public String getCodeModifyUser() {
        return codeModifyUser;
    }

    public void setCodeModifyUser(String codeModifyUser) {
        this.codeModifyUser = codeModifyUser;
    }

    @Override
    public String toString() {
        return "MCodeEntity{" +
            "codeId=" + codeId +
            ", codeGroup=" + codeGroup +
            ", codeName=" + codeName +
            ", codeValue=" + codeValue +
            ", codeOrder=" + codeOrder +
            ", codeDelFlg=" + codeDelFlg +
            ", codeCreateTime=" + codeCreateTime +
            ", codeCreateUser=" + codeCreateUser +
            ", codeModifyTime=" + codeModifyTime +
            ", codeModifyUser=" + codeModifyUser +
        "}";
    }
}
