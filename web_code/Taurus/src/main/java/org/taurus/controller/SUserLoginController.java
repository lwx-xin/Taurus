package org.taurus.controller;

import org.springframework.web.bind.annotation.*;
import org.taurus.service.SUserLoginService;
import org.taurus.entity.SUserLoginEntity;
import org.taurus.common.Code;
import org.taurus.common.result.Result;
import org.taurus.common.util.LoggerUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.web.bind.annotation.RestController;
 
/**
 * <p>
 * 用户登录记录 前端控制器
 * </p>
 *
 * @author 欣
 * @since 2020-12-11
 * @version v1.0
 */
@Api(tags = {"用户登录记录"})
@RestController
@RequestMapping("user_login")
public class SUserLoginController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Resource
    private SUserLoginService sUserLoginService;
 
    /**
     * 查询分页数据
     */
    @ApiOperation(value = "查询分页数据")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Result<List<SUserLoginEntity>> findListByPage(@RequestBody int pageNum, int pageSize, SUserLoginEntity sUserLoginEntity){

    	LoggerUtil.printParam(logger, "pageNum", pageNum);
    	LoggerUtil.printParam(logger, "pageSize", pageSize);
    	LoggerUtil.printParam(logger, "sUserLoginEntity", sUserLoginEntity);
    	
        IPage<SUserLoginEntity> page = new Page<SUserLoginEntity>(pageNum, pageSize);
        QueryWrapper<SUserLoginEntity> queryWrapper = new QueryWrapper<SUserLoginEntity>(sUserLoginEntity); 
		page = sUserLoginService.page(page, queryWrapper);
		List<SUserLoginEntity> data = page.getRecords();
		return new Result<List<SUserLoginEntity>>(data);
    }
 
 
    /**
     * 根据id查询
     */
    @ApiOperation(value = "根据id查询数据")
    @RequestMapping(value = "/getById")
    public Result<SUserLoginEntity> getById(@RequestParam("pkid") String pkid){

    	LoggerUtil.printParam(logger, "pkid", pkid);
    	
		SUserLoginEntity data = sUserLoginService.getById(pkid);
		return new Result<SUserLoginEntity>(data);
    }
 
    /**
     * 新增
     */
    @ApiOperation(value = "新增数据")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result<Object> add(@RequestBody SUserLoginEntity sUserLoginEntity){

    	LoggerUtil.printParam(logger, "sUserLoginEntity", sUserLoginEntity);
    	
		boolean state = sUserLoginService.save(sUserLoginEntity);
		if (state) {
			return new Result<Object>(null);
		}
		return new Result<Object>(null, Code.INTERFACE_ERR_CODE_3);
    }
 
    /**
     * 删除
     */
    @ApiOperation(value = "删除数据")
    @RequestMapping(value = "/del")
    public Result<Object> delete(@RequestParam("pkid") String pkid){

    	LoggerUtil.printParam(logger, "pkid", pkid);
    	
		boolean state = sUserLoginService.removeById(pkid);
		if (state) {
			return new Result<Object>(null);
		}
		return new Result<Object>(null, Code.INTERFACE_ERR_CODE_3);
    }
 
    /**
     * 修改
     */
    @ApiOperation(value = "更新数据")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result<Object> update(@RequestBody SUserLoginEntity sUserLoginEntity){

    	LoggerUtil.printParam(logger, "sUserLoginEntity", sUserLoginEntity);
    	
		boolean state = sUserLoginService.updateById(sUserLoginEntity);
		if (state) {
			return new Result<Object>(null);
		}
		return new Result<Object>(null, Code.INTERFACE_ERR_CODE_3);
     }
 
}
