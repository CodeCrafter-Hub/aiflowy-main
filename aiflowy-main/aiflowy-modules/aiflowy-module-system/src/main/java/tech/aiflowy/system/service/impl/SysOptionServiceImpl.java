package tech.aiflowy.system.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import tech.aiflowy.system.entity.SysOption;
import tech.aiflowy.system.mapper.SysOptionMapper;
import tech.aiflowy.system.service.SysOptionService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 系统配置信息表。 服务层实现。
 *
 * @author michael
 * @since 2024-03-13
 */
@Service
public class SysOptionServiceImpl extends ServiceImpl<SysOptionMapper, SysOption> implements SysOptionService {

    @Override
    public SysOption getByOptionKey(String key) {
        QueryWrapper w = QueryWrapper.create();
        w.eq(SysOption::getKey, key);
        return getOne(w);
    }
}
