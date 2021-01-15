package org.taurus.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author 欣
 * @since 2021-01-13
 */
@TableName("s_log")
public class SLogEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 日志id
     */
    @TableId("LOG_ID")
    private String logId;

    /**
     * 异常发生时间
     */
    @TableField("LOG_HAPPEN_TIME")
    private LocalDateTime logHappenTime;

    /**
     * 日志文件ID
     */
    @TableField("LOG_FILE")
    private String logFile;

    /**
     * 异常状态(待解决,已解决)
     */
    @TableField("LOG_STATUS")
    private String logStatus;

    /**
     * 触发异常的用户
     */
    @TableField("LOG_USER")
    private String logUser;

    /**
     * 备注
     */
    @TableField("LOG_REMARK")
    private String logRemark;

    /**
     * 引发异常的模块
     */
    @TableField("LOG_MODULE")
    private String logModule;

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }
    public LocalDateTime getLogHappenTime() {
        return logHappenTime;
    }

    public void setLogHappenTime(LocalDateTime logHappenTime) {
        this.logHappenTime = logHappenTime;
    }
    public String getLogFile() {
        return logFile;
    }

    public void setLogFile(String logFile) {
        this.logFile = logFile;
    }
    public String getLogStatus() {
        return logStatus;
    }

    public void setLogStatus(String logStatus) {
        this.logStatus = logStatus;
    }
    public String getLogUser() {
        return logUser;
    }

    public void setLogUser(String logUser) {
        this.logUser = logUser;
    }
    public String getLogRemark() {
        return logRemark;
    }

    public void setLogRemark(String logRemark) {
        this.logRemark = logRemark;
    }
    public String getLogModule() {
        return logModule;
    }

    public void setLogModule(String logModule) {
        this.logModule = logModule;
    }

    @Override
    public String toString() {
        return "SLogEntity{" +
            "logId=" + logId +
            ", logHappenTime=" + logHappenTime +
            ", logFile=" + logFile +
            ", logStatus=" + logStatus +
            ", logUser=" + logUser +
            ", logRemark=" + logRemark +
            ", logModule=" + logModule +
        "}";
    }
}
