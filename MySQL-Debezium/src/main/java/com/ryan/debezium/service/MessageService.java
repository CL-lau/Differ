package com.ryan.debezium.service;

import com.ryan.debezium.entity.Message;

public interface MessageService {

    /**
     * 发送消息
     *
     * @param message
     */
    public void sendMessage(Message message);

}
