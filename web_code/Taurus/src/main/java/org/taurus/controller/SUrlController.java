package org.taurus.controller;

import org.springframework.web.bind.annotation.*;
import org.taurus.service.SUrlService;
import org.taurus.entity.SUrlEntity;
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
 * 系统请求 前端控制器
 * </p>
 *
 * @author 欣
 * @since 2020-12-11
 * @version v1.0
 */
@Api(tags = {"系统请求"})
@RestController
@RequestMapping("url")
public class SUrlController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Resource
    private SUrlService sUrlService;
 
    /**
     * 查询分页数据
     */
    @ApiOperation(value = "查询分页数据")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Result<List<SUrlEntity>> findListByPage(@RequestBody int pageNum, int pageSize, SUrlEntity sUrlEntity){

    	LoggerUtil.printParam(logger, "pageNum", pageNum);
    	LoggerUtil.printParam(logger, "pageSize", pageSize);
    	LoggerUtil.printParam(logger, "sUrlEntity", sUrlEntity);
    	
        IPage<SUrlEntity> page = new Page<SUrlEntity>(pageNum, pageSize);
        QueryWrapper<SUrlEntity> queryWrapper = new QueryWrapper<SUrlEntity>(sUrlEntity); 
		page = sUrlService.page(page, queryWrapper);
		List<SUrlEntity> data = page.getRecords();
		return new Result<List<SUrlEntity>>(data);
    }
 
 
    /**
     * 根据id查询
     */
    @ApiOperation(value = "根据id查询数据")
    @RequestMapping(value = "/getById")
    public Result<SUrlEntity> getById(@RequestParam("pkid") String pkid){

    	LoggerUtil.printParam(logger, "pkid", pkid);
    	
		SUrlEntity data = sUrlService.getById(pkid);
		return new Result<SUrlEntity>(data);
    }
 
    /**
     * 新增
     */
    @ApiOperation(value = "新增数据")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result<Object> add(@RequestBody SUrlEntity sUrlEntity){

    	LoggerUtil.printParam(logger, "sUrlEntity", sUrlEntity);
    	
		boolean state = sUrlService.save(sUrlEntity);
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
    	
		boolean state = sUrlService.removeById(pkid);
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
    public Result<Object> update(@RequestBody SUrlEntity sUrlEntity){

    	LoggerUtil.printParam(logger, "sUrlEntity", sUrlEntity);
    	
		boolean state = sUrlService.updateById(sUrlEntity);
		if (state) {
			return new Result<Object>(null);
		}
		return new Result<Object>(null, Code.INTERFACE_ERR_CODE_3);
     }
 
}
