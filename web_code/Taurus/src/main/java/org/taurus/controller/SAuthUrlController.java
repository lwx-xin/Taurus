package org.taurus.controller;

import org.springframework.web.bind.annotation.*;
import org.taurus.service.SAuthUrlService;
import org.taurus.entity.SAuthUrlEntity;
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
 * 请求权限 前端控制器
 * </p>
 *
 * @author 欣
 * @since 2020-12-11
 * @version v1.0
 */
@Api(tags = {"请求权限"})
@RestController
@RequestMapping("auth_url")
public class SAuthUrlController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Resource
    private SAuthUrlService sAuthUrlService;
 
    /**
     * 查询分页数据
     */
    @ApiOperation(value = "查询分页数据")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Result<List<SAuthUrlEntity>> findListByPage(@RequestBody int pageNum, int pageSize, SAuthUrlEntity sAuthUrlEntity){

    	LoggerUtil.printParam(logger, "pageNum", pageNum);
    	LoggerUtil.printParam(logger, "pageSize", pageSize);
    	LoggerUtil.printParam(logger, "sAuthUrlEntity", sAuthUrlEntity);
    	
        IPage<SAuthUrlEntity> page = new Page<SAuthUrlEntity>(pageNum, pageSize);
        QueryWrapper<SAuthUrlEntity> queryWrapper = new QueryWrapper<SAuthUrlEntity>(sAuthUrlEntity); 
		page = sAuthUrlService.page(page, queryWrapper);
		List<SAuthUrlEntity> data = page.getRecords();
		return new Result<List<SAuthUrlEntity>>(data);
    }
 
 
    /**
     * 根据id查询
     */
    @ApiOperation(value = "根据id查询数据")
    @RequestMapping(value = "/getById")
    public Result<SAuthUrlEntity> getById(@RequestParam("pkid") String pkid){

    	LoggerUtil.printParam(logger, "pkid", pkid);
    	
		SAuthUrlEntity data = sAuthUrlService.getById(pkid);
		return new Result<SAuthUrlEntity>(data);
    }
 
    /**
     * 新增
     */
    @ApiOperation(value = "新增数据")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result<Object> add(@RequestBody SAuthUrlEntity sAuthUrlEntity){

    	LoggerUtil.printParam(logger, "sAuthUrlEntity", sAuthUrlEntity);
    	
		boolean state = sAuthUrlService.save(sAuthUrlEntity);
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
    	
		boolean state = sAuthUrlService.removeById(pkid);
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
    public Result<Object> update(@RequestBody SAuthUrlEntity sAuthUrlEntity){

    	LoggerUtil.printParam(logger, "sAuthUrlEntity", sAuthUrlEntity);
    	
		boolean state = sAuthUrlService.updateById(sAuthUrlEntity);
		if (state) {
			return new Result<Object>(null);
		}
		return new Result<Object>(null, Code.INTERFACE_ERR_CODE_3);
     }
 
}
