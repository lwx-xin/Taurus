package org.taurus.controller;

import org.springframework.web.bind.annotation.*;
import org.taurus.service.MCodeService;
import org.taurus.entity.MCodeEntity;
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
 * 系统code列表 前端控制器
 * </p>
 *
 * @author 欣
 * @since 2020-12-11
 * @version v1.0
 */
@Api(tags = {"系统code列表"})
@RestController
@RequestMapping("m_code")
public class MCodeController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Resource
    private MCodeService mCodeService;
 
    /**
     * 查询分页数据
     */
    @ApiOperation(value = "查询分页数据")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Result<List<MCodeEntity>> findListByPage(@RequestBody int pageNum, int pageSize, MCodeEntity mCodeEntity){

    	LoggerUtil.printParam(logger, "pageNum", pageNum);
    	LoggerUtil.printParam(logger, "pageSize", pageSize);
    	LoggerUtil.printParam(logger, "mCodeEntity", mCodeEntity);
    	
        IPage<MCodeEntity> page = new Page<MCodeEntity>(pageNum, pageSize);
        QueryWrapper<MCodeEntity> queryWrapper = new QueryWrapper<MCodeEntity>(mCodeEntity); 
		page = mCodeService.page(page, queryWrapper);
		List<MCodeEntity> data = page.getRecords();
		return new Result<List<MCodeEntity>>(data);
    }
 
 
    /**
     * 根据id查询
     */
    @ApiOperation(value = "根据id查询数据")
    @RequestMapping(value = "/getById")
    public Result<MCodeEntity> getById(@RequestParam("pkid") String pkid){

    	LoggerUtil.printParam(logger, "pkid", pkid);
    	
		MCodeEntity data = mCodeService.getById(pkid);
		return new Result<MCodeEntity>(data);
    }
 
    /**
     * 新增
     */
    @ApiOperation(value = "新增数据")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result<Object> add(@RequestBody MCodeEntity mCodeEntity){

    	LoggerUtil.printParam(logger, "mCodeEntity", mCodeEntity);
    	
		boolean state = mCodeService.save(mCodeEntity);
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
    	
		boolean state = mCodeService.removeById(pkid);
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
    public Result<Object> update(@RequestBody MCodeEntity mCodeEntity){

    	LoggerUtil.printParam(logger, "mCodeEntity", mCodeEntity);
    	
		boolean state = mCodeService.updateById(mCodeEntity);
		if (state) {
			return new Result<Object>(null);
		}
		return new Result<Object>(null, Code.INTERFACE_ERR_CODE_3);
     }
 
}
