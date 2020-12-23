package org.taurus.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.taurus.entity.SAuthEntity;

import com.baomidou.mybatisplus.extension.service.IService;
 
/**
 * <p>
 * 系统权限 服务类
 * </p>
 *
 * @author 欣
 * @since 2020-11-08
 * @version v1.0
 */
@Service
public interface SAuthService extends IService<SAuthEntity> {
	
	/**
	 * 获取用户的权限ID
	 * @param userId
	 * @return
	 */
	List<String> getAuthByUserId(String userId);

}