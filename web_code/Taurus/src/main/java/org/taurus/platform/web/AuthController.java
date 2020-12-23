package org.taurus.platform.web;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.taurus.common.Code;
import org.taurus.common.result.Result;
import org.taurus.common.util.LoggerUtil;
import org.taurus.entity.SAuthEntity;
import org.taurus.service.SAuthService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("web/auth")
public class AuthController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Resource
    private SAuthService authService;
 
    /**
     * 查询数据
     */
    @ApiOperation(value = "获取权限列表")
    @RequestMapping(method = RequestMethod.GET)
    public Result<List<SAuthEntity>> getList(SAuthEntity authEntity){

    	LoggerUtil.printParam(logger, "authEntity", authEntity);
    	
		QueryWrapper<SAuthEntity> queryWrapper = new QueryWrapper<SAuthEntity>(authEntity);
		List<SAuthEntity> data = authService.list(queryWrapper);
		return new Result<List<SAuthEntity>>(data, true, Code.INTERFACE_ERR_CODE_0);
    }
 
    /**
     * 根据id查询
     */
    @ApiOperation(value = "获取权限信息")
    @RequestMapping(value = "/{authId}", method = RequestMethod.GET)
    public Result<SAuthEntity> getById(@PathVariable("authId")String authId){

    	LoggerUtil.printParam(logger, "authId", authId);
    	
    	SAuthEntity data = authService.getById(authId);
		return new Result<SAuthEntity>(data, true, Code.INTERFACE_ERR_CODE_0);
    }

}
