package tech.aiflowy.ai.service;

import com.mybatisflex.core.service.IService;
import tech.aiflowy.ai.entity.PluginCategory;

import java.math.BigInteger;

/**
 *  服务层。
 *
 * @author Administrator
 * @since 2025-05-21
 */
public interface PluginCategoryService extends IService<PluginCategory> {

    boolean doRemoveCategory(BigInteger id);
}
