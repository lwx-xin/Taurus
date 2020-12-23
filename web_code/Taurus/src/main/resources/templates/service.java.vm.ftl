package ${package.Service};

import org.springframework.stereotype.Service;
import ${package.Entity}.${entity};
import com.baomidou.mybatisplus.extension.service.IService;
 
/**
 * <p>
 * ${table.comment!} 服务类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 * @version v1.0
 */
@Service
public interface ${table.serviceName} extends IService<${entity}> {

}