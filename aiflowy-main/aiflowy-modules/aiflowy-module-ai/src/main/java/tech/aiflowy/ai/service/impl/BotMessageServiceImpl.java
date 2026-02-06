package tech.aiflowy.ai.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import tech.aiflowy.ai.entity.BotMessage;
import tech.aiflowy.ai.mapper.BotMessageMapper;
import tech.aiflowy.ai.service.BotMessageService;

/**
 * Bot 消息记录表 服务层实现。
 *
 * @author michael
 * @since 2024-11-04
 */
@Service
public class BotMessageServiceImpl extends ServiceImpl<BotMessageMapper, BotMessage> implements BotMessageService {

}
