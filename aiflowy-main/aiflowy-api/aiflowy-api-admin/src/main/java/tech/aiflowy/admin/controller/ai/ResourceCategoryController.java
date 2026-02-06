package tech.aiflowy.admin.controller.ai;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.aiflowy.ai.entity.ResourceCategory;
import tech.aiflowy.ai.service.ResourceCategoryService;
import tech.aiflowy.common.annotation.UsePermission;
import tech.aiflowy.common.web.controller.BaseCurdController;

/**
 * 素材分类
 */
@RestController
@RequestMapping("/api/v1/resourceCategory")
@UsePermission(moduleName = "/api/v1/resource")
public class ResourceCategoryController extends BaseCurdController<ResourceCategoryService, ResourceCategory> {

    public ResourceCategoryController(ResourceCategoryService service) {
        super(service);
    }

}