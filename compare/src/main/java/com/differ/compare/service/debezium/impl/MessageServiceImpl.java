package com.differ.compare.service.debezium.impl;

import com.differ.compare.entity.debezium.Message;
import com.differ.compare.service.debezium.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: lau
 * @time: 2023/11/5 16:09
 */
@Slf4j
@Service
public class MessageServiceImpl implements MessageService {

    @Override
    public void sendMessage(Message message) {
        System.out.println(message);
        /**
         * TODO
         *
         * 增加数据处理的后续
         * 方式一：数据库所在设备单独进行配置发送变更的方式
         * 方式二：differ主动访问数据库通过监控binlog的方式
         * 方式三：differ主动访问数据库的内容
         */
    }
}
