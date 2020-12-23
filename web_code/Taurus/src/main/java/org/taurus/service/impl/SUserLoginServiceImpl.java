package org.taurus.service.impl;

import org.taurus.dao.SUserLoginDao;
import org.taurus.entity.SUserLoginEntity;
import org.taurus.service.SUserLoginService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户登录记录 服务实现类
 * </p>
 *
 * @author 欣
 * @since 2020-11-08
 */
@Service
public class SUserLoginServiceImpl extends ServiceImpl<SUserLoginDao, SUserLoginEntity> implements SUserLoginService {

}
