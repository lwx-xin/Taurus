package org.taurus.service.impl;

import org.taurus.dao.SAuthUserDao;
import org.taurus.entity.SAuthUserEntity;
import org.taurus.service.SAuthUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户权限 服务实现类
 * </p>
 *
 * @author 欣
 * @since 2020-11-08
 */
@Service
public class SAuthUserServiceImpl extends ServiceImpl<SAuthUserDao, SAuthUserEntity> implements SAuthUserService {

}
