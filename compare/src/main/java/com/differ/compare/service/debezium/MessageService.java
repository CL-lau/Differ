package com.differ.compare.service.debezium;

import com.differ.compare.entity.debezium.Message;

public interface MessageService {
    public void sendMessage(Message message);
}
