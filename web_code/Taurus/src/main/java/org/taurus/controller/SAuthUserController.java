package org.taurus.controller;

import org.springframework.web.bind.annotation.*;
import org.taurus.service.SAuthUserService;
import org.taurus.entity.SAuthUserEntity;
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
 * 用户权限 前端控制器
 * </p>
 *
 * @author 欣
 * @since 2020-12-11
 * @version v1.0
 */
@Api(tags = {"用户权限"})
@RestController
@RequestMapping("auth_user")
public class SAuthUserController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Resource
    private SAuthUserService sAuthUserService;
 
    /**
     * 查询分页数据
     */
    @ApiOperation(value = "查询分页数据")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Result<List<SAuthUserEntity>> findListByPage(@RequestBody int pageNum, int pageSize, SAuthUserEntity sAuthUserEntity){

    	LoggerUtil.printParam(logger, "pageNum", pageNum);
    	LoggerUtil.printParam(logger, "pageSize", pageSize);
    	LoggerUtil.printParam(logger, "sAuthUserEntity", sAuthUserEntity);
    	
        IPage<SAuthUserEntity> page = new Page<SAuthUserEntity>(pageNum, pageSize);
        QueryWrapper<SAuthUserEntity> queryWrapper = new QueryWrapper<SAuthUserEntity>(sAuthUserEntity); 
		page = sAuthUserService.page(page, queryWrapper);
		List<SAuthUserEntity> data = page.getRecords();
		return new Result<List<SAuthUserEntity>>(data);
    }
 
 
    /**
     * 根据id查询
     */
    @ApiOperation(value = "根据id查询数据")
    @RequestMapping(value = "/getById")
    public Result<SAuthUserEntity> getById(@RequestParam("pkid") String pkid){

    	LoggerUtil.printParam(logger, "pkid", pkid);
    	
		SAuthUserEntity data = sAuthUserService.getById(pkid);
		return new Result<SAuthUserEntity>(data);
    }
 
    /**
     * 新增
     */
    @ApiOperation(value = "新增数据")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result<Object> add(@RequestBody SAuthUserEntity sAuthUserEntity){

    	LoggerUtil.printParam(logger, "sAuthUserEntity", sAuthUserEntity);
    	
		boolean state = sAuthUserService.save(sAuthUserEntity);
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
    	
		boolean state = sAuthUserService.removeById(pkid);
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
    public Result<Object> update(@RequestBody SAuthUserEntity sAuthUserEntity){

    	LoggerUtil.printParam(logger, "sAuthUserEntity", sAuthUserEntity);
    	
		boolean state = sAuthUserService.updateById(sAuthUserEntity);
		if (state) {
			return new Result<Object>(null);
		}
		return new Result<Object>(null, Code.INTERFACE_ERR_CODE_3);
     }
 
}
