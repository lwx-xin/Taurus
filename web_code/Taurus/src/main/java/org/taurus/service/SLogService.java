package org.taurus.service;

import org.springframework.stereotype.Service;
import org.taurus.common.exception.CustomException;
import org.taurus.entity.SLogEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 欣
 * @since 2021-01-13
 * @version v1.0
 */
public interface SLogService extends IService<SLogEntity> {

	/**
	 * 保存日志记录
	 * 
	 * @param exception
	 * @param userId
	 */
	public void saveLogFile(Exception exception, String userId) throws CustomException;

}