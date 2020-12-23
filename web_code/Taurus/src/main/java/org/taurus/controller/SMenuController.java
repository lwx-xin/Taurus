package org.taurus.controller;

import org.springframework.web.bind.annotation.*;
import org.taurus.service.SMenuService;
import org.taurus.entity.SMenuEntity;
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
 * 系统菜单 前端控制器
 * </p>
 *
 * @author 欣
 * @since 2020-12-11
 * @version v1.0
 */
@Api(tags = {"系统菜单"})
@RestController
@RequestMapping("menu")
public class SMenuController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Resource
    private SMenuService sMenuService;
 
    /**
     * 查询分页数据
     */
    @ApiOperation(value = "查询分页数据")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Result<List<SMenuEntity>> findListByPage(@RequestBody int pageNum, int pageSize, SMenuEntity sMenuEntity){

    	LoggerUtil.printParam(logger, "pageNum", pageNum);
    	LoggerUtil.printParam(logger, "pageSize", pageSize);
    	LoggerUtil.printParam(logger, "sMenuEntity", sMenuEntity);
    	
        IPage<SMenuEntity> page = new Page<SMenuEntity>(pageNum, pageSize);
        QueryWrapper<SMenuEntity> queryWrapper = new QueryWrapper<SMenuEntity>(sMenuEntity); 
		page = sMenuService.page(page, queryWrapper);
		List<SMenuEntity> data = page.getRecords();
		return new Result<List<SMenuEntity>>(data);
    }
 
 
    /**
     * 根据id查询
     */
    @ApiOperation(value = "根据id查询数据")
    @RequestMapping(value = "/getById")
    public Result<SMenuEntity> getById(@RequestParam("pkid") String pkid){

    	LoggerUtil.printParam(logger, "pkid", pkid);
    	
		SMenuEntity data = sMenuService.getById(pkid);
		return new Result<SMenuEntity>(data);
    }
 
    /**
     * 新增
     */
    @ApiOperation(value = "新增数据")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result<Object> add(@RequestBody SMenuEntity sMenuEntity){

    	LoggerUtil.printParam(logger, "sMenuEntity", sMenuEntity);
    	
		boolean state = sMenuService.save(sMenuEntity);
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
    	
		boolean state = sMenuService.removeById(pkid);
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
    public Result<Object> update(@RequestBody SMenuEntity sMenuEntity){

    	LoggerUtil.printParam(logger, "sMenuEntity", sMenuEntity);
    	
		boolean state = sMenuService.updateById(sMenuEntity);
		if (state) {
			return new Result<Object>(null);
		}
		return new Result<Object>(null, Code.INTERFACE_ERR_CODE_3);
     }
 
}
