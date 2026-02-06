package tech.aiflowy.ai.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import tech.aiflowy.ai.entity.ResourceCategory;
import tech.aiflowy.ai.mapper.ResourceCategoryMapper;
import tech.aiflowy.ai.service.ResourceCategoryService;
import org.springframework.stereotype.Service;

/**
 * 素材分类 服务层实现。
 *
 * @author ArkLight
 * @since 2025-12-24
 */
@Service
public class ResourceCategoryServiceImpl extends ServiceImpl<ResourceCategoryMapper, ResourceCategory>  implements ResourceCategoryService{

}
