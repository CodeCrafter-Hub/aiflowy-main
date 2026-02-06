package tech.aiflowy.admin.controller.ai;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.aiflowy.ai.entity.BotMessage;
import tech.aiflowy.ai.service.BotMessageService;
import tech.aiflowy.common.annotation.UsePermission;
import tech.aiflowy.common.domain.Result;
import tech.aiflowy.common.satoken.util.SaTokenUtil;
import tech.aiflowy.common.web.controller.BaseCurdController;

/**
 * Bot 消息记录表 控制层。
 *
 * @author michael
 * @since 2024-11-04
 */
@RestController
@RequestMapping("/api/v1/botMessage")
@UsePermission(moduleName = "/api/v1/bot")
public class BotMessageController extends BaseCurdController<BotMessageService, BotMessage> {

    private final BotMessageService botMessageService;

    public BotMessageController(BotMessageService service, BotMessageService botMessageService) {
        super(service);
        this.botMessageService = botMessageService;
    }

    @Override
    protected Result<?> onSaveOrUpdateBefore(BotMessage entity, boolean isSave) {
        entity.setAccountId(SaTokenUtil.getLoginAccount().getId());
        return super.onSaveOrUpdateBefore(entity, isSave);
    }
}
