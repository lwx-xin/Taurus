package org.taurus.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.taurus.entity.SMenuEntity;
import org.taurus.extendEntity.SMenuEntityEx;

import com.baomidou.mybatisplus.extension.service.IService;
 
/**
 * <p>
 * 系统菜单 服务类
 * </p>
 *
 * @author 欣
 * @since 2020-11-08
 * @version v1.0
 */
@Service
public interface SMenuService extends IService<SMenuEntity> {
	
	/**
	 * 获取用户能显示的菜单
	 * @param userId
	 * @return
	 */
	public List<SMenuEntityEx> getMenuListByUser(String userId);

}