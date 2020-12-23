package ${package.Controller};

import org.springframework.web.bind.annotation.*;
import ${package.Service}.${table.serviceName};
import ${package.Entity}.${entity};
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
<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>
 
/**
 * <p>
 * ${table.comment!} 前端控制器
 * </p>
 *
 * @author ${author}
 * @since ${date}
 * @version v1.0
 */
<#if restControllerStyle>
@Api(tags = {"${table.comment!}"})
@RestController
<#else>
@Controller
</#if>
@RequestMapping("${(table.name)?replace('s_','')}")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
    <#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
    <#else>
public class ${table.controllerName} {

    </#if>
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Resource
    private ${table.serviceName} ${(table.serviceName)?uncap_first};
 
    /**
     * 查询分页数据
     */
    @ApiOperation(value = "查询分页数据")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Result<List<${entity}>> findListByPage(@RequestBody int pageNum, int pageSize, ${entity} ${entity?uncap_first}){

    	LoggerUtil.printParam(logger, "pageNum", pageNum);
    	LoggerUtil.printParam(logger, "pageSize", pageSize);
    	LoggerUtil.printParam(logger, "${entity?uncap_first}", ${entity?uncap_first});
    	
        IPage<${entity}> page = new Page<${entity}>(pageNum, pageSize);
        QueryWrapper<${entity}> queryWrapper = new QueryWrapper<${entity}>(${entity?uncap_first}); 
		page = ${(table.serviceName)?uncap_first}.page(page, queryWrapper);
		List<${entity}> data = page.getRecords();
		return new Result<List<${entity}>>(data);
    }
 
 
    /**
     * 根据id查询
     */
    @ApiOperation(value = "根据id查询数据")
    @RequestMapping(value = "/getById")
    public Result<${entity}> getById(@RequestParam("pkid") String pkid){

    	LoggerUtil.printParam(logger, "pkid", pkid);
    	
		${entity} data = ${(table.serviceName)?uncap_first}.getById(pkid);
		return new Result<${entity}>(data);
    }
 
    /**
     * 新增
     */
    @ApiOperation(value = "新增数据")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result<Object> add(@RequestBody ${entity} ${entity?uncap_first}){

    	LoggerUtil.printParam(logger, "${entity?uncap_first}", ${entity?uncap_first});
    	
		boolean state = ${(table.serviceName)?uncap_first}.save(${entity?uncap_first});
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
    	
		boolean state = ${(table.serviceName)?uncap_first}.removeById(pkid);
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
    public Result<Object> update(@RequestBody ${entity} ${entity?uncap_first}){

    	LoggerUtil.printParam(logger, "${entity?uncap_first}", ${entity?uncap_first});
    	
		boolean state = ${(table.serviceName)?uncap_first}.updateById(${entity?uncap_first});
		if (state) {
			return new Result<Object>(null);
		}
		return new Result<Object>(null, Code.INTERFACE_ERR_CODE_3);
     }
 
}
</#if>