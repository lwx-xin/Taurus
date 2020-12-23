package org.taurus.controller;

import org.springframework.web.bind.annotation.*;
import org.taurus.service.SAuthMenuService;
import org.taurus.entity.SAuthMenuEntity;
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
 * 菜单权限 前端控制器
 * </p>
 *
 * @author 欣
 * @since 2020-12-11
 * @version v1.0
 */
@Api(tags = {"菜单权限"})
@RestController
@RequestMapping("auth_menu")
public class SAuthMenuController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Resource
    private SAuthMenuService sAuthMenuService;
 
    /**
     * 查询分页数据
     */
    @ApiOperation(value = "查询分页数据")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Result<List<SAuthMenuEntity>> findListByPage(@RequestBody int pageNum, int pageSize, SAuthMenuEntity sAuthMenuEntity){

    	LoggerUtil.printParam(logger, "pageNum", pageNum);
    	LoggerUtil.printParam(logger, "pageSize", pageSize);
    	LoggerUtil.printParam(logger, "sAuthMenuEntity", sAuthMenuEntity);
    	
        IPage<SAuthMenuEntity> page = new Page<SAuthMenuEntity>(pageNum, pageSize);
        QueryWrapper<SAuthMenuEntity> queryWrapper = new QueryWrapper<SAuthMenuEntity>(sAuthMenuEntity); 
		page = sAuthMenuService.page(page, queryWrapper);
		List<SAuthMenuEntity> data = page.getRecords();
		return new Result<List<SAuthMenuEntity>>(data);
    }
 
 
    /**
     * 根据id查询
     */
    @ApiOperation(value = "根据id查询数据")
    @RequestMapping(value = "/getById")
    public Result<SAuthMenuEntity> getById(@RequestParam("pkid") String pkid){

    	LoggerUtil.printParam(logger, "pkid", pkid);
    	
		SAuthMenuEntity data = sAuthMenuService.getById(pkid);
		return new Result<SAuthMenuEntity>(data);
    }
 
    /**
     * 新增
     */
    @ApiOperation(value = "新增数据")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result<Object> add(@RequestBody SAuthMenuEntity sAuthMenuEntity){

    	LoggerUtil.printParam(logger, "sAuthMenuEntity", sAuthMenuEntity);
    	
		boolean state = sAuthMenuService.save(sAuthMenuEntity);
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
    	
		boolean state = sAuthMenuService.removeById(pkid);
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
    public Result<Object> update(@RequestBody SAuthMenuEntity sAuthMenuEntity){

    	LoggerUtil.printParam(logger, "sAuthMenuEntity", sAuthMenuEntity);
    	
		boolean state = sAuthMenuService.updateById(sAuthMenuEntity);
		if (state) {
			return new Result<Object>(null);
		}
		return new Result<Object>(null, Code.INTERFACE_ERR_CODE_3);
     }
 
}
