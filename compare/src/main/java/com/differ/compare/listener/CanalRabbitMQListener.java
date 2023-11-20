package com.differ.compare.listener;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.differ.compare.entity.ChangeDto;
import com.differ.compare.entity.canal.CanalMessage;
import com.differ.compare.entity.enumer.ServiceType;
import com.differ.compare.repository.db.mapper.ChangeDtoRepository;
import com.differ.compare.repository.influxdb.InfluxDBRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @description:
 * @author: lau
 * @time: 2023/11/6 16:20
 */

@Component
@Slf4j
@RequiredArgsConstructor
public class CanalRabbitMQListener {
    private ChangeDtoRepository changeDtoMapper;
    private InfluxDBRepository influxDBRepository;

    @Value(value = "${influxdb.bucket}")
    private static String BUCKET_NAME;

    @Value(value = "${influxdb.org}")
    private static String ORG;

    @Autowired
    public void setInject(ChangeDtoRepository changeDtoMapper, InfluxDBRepository influxDBRepository){
        this.changeDtoMapper = changeDtoMapper;
        this.influxDBRepository = influxDBRepository;
    }

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue(value = "canal.queue", durable = "true"),
                    exchange = @Exchange(value = "canal.exchange"),
                    key = "canal.routingKey"
            )
    })
    public void handleOldDataChange(String message) {
        CanalMessage canalMessage = JSONUtil.toBean(message, CanalMessage.class);
        String tableName = canalMessage.getTable();
        log.info("Canal 监听的原始数据库 {} 发生变化；明细：{}", tableName, message);
        List<ChangeDto> changeDtoList = parseCanalMessage(canalMessage, ServiceType.MASTER);
    }

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue(value = "canal.queue_novel", durable = "true"),
                    exchange = @Exchange(value = "canal.exchange_novel"),
                    key = "canal.routingKey_novel"
            )
    })
    public void handleNovelDataChange(String message) {
        CanalMessage canalMessage = JSONUtil.toBean(message, CanalMessage.class);
        String tableName = canalMessage.getTable();
        log.info("Canal 监听的新数据库 {} 发生变化；明细：{}", tableName, message);
        List<ChangeDto> changeDtoList = parseCanalMessage(canalMessage, ServiceType.NOVEL);
    }

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue(value = "canal.queue_backup", durable = "true"),
                    exchange = @Exchange(value = "canal.exchange_backup"),
                    key = "canal.routingKey_backup"
            )
    })
    public void handleBackUpDataChange(String message) {
        CanalMessage canalMessage = JSONUtil.toBean(message, CanalMessage.class);
        String tableName = canalMessage.getTable();
        log.info("Canal 监听的从数据库 {} 发生变化；明细：{}", tableName, message);
        List<ChangeDto> changeDtoList = parseCanalMessage(canalMessage, ServiceType.SLAVE);
    }

    public List<ChangeDto> parseCanalMessage(CanalMessage canalMessage, ServiceType serviceType){
        if (canalMessage == null) {
            return Collections.emptyList();
        }
        List<ChangeDto> changeDtoList = new ArrayList<>();

        Optional<Map<String, Object>> optionalNovelData = Optional.ofNullable(canalMessage.getData())
                .filter(novelList -> !novelList.isEmpty())
                .map(novelList -> parseDataToMap(novelList.get(0).toString()));

        Map<String, Object> novelData = optionalNovelData.orElse(new HashMap<>());

        Optional<Map<String, Object>> optionalOldData = Optional.ofNullable(canalMessage.getOld())
                .filter(oldList -> !oldList.isEmpty())
                .map(oldList -> parseDataToMap(oldList.get(0).toString()));

        Map<String, Object> oldData = optionalOldData.orElse(new HashMap<>());
        System.out.println(canalMessage.getMysqlType());

        Optional<Map<String, Object>> optionalColumnTypes = Optional.ofNullable(canalMessage.getOld())
                .filter(columnType -> !columnType.isEmpty())
                .map(columnType -> parseDataToMap(columnType.get(0).toString()));

        Map<String, Object> columnTypes = optionalColumnTypes.orElse(new HashMap<>());
        String tableName = canalMessage.getTable();
        String database = canalMessage.getDatabase();
        String sqlType = canalMessage.getType();
        Long es = canalMessage.getEs();
        Long ts = canalMessage.getTs();
        Integer columnId = canalMessage.getId();

        syncToInfluxDB(database, tableName, sqlType, columnId, es, ts, novelData, serviceType);

        for (Map.Entry<String, Object> entry: novelData.entrySet()){
            String key = entry.getKey();
            ChangeDto changeDto = new ChangeDto();
            changeDto.setDatabaseName(database);
            changeDto.setColumnId(columnId);
            changeDto.setColumnName(key);
            changeDto.setOldData((String) oldData.get(key));
            changeDto.setNovelData((String) novelData.get(key));
            changeDto.setDataType((String) columnTypes.get(key));
            changeDto.setEs(es);
            changeDto.setTs(ts);
            changeDto.setSqlType(sqlType);
            changeDto.setTableName(tableName);
            changeDto.setServiceType(serviceType);
            this.changeDtoMapper.insert(changeDto);
        }
        return changeDtoList;
    }

    public void syncToInfluxDB(String databaseName, String tableName, String sqlType, Integer columnId, Long es, Long ts,
                               Map<String, Object> novelData, ServiceType serviceType){
        StringBuffer measurementName = new StringBuffer();
        measurementName.append(serviceType.getType())
                .append("_")
                .append(databaseName)
                .append("_")
                .append(tableName);

        novelData.put("es", es);
        novelData.put("columnId", columnId);
        novelData.put("sqlType", sqlType);

        this.influxDBRepository.insert(BUCKET_NAME, ORG, measurementName.toString(), novelData,ts);

        measurementName.append(sqlType);

        this.influxDBRepository.insert(BUCKET_NAME, ORG, measurementName.toString(), novelData, ts);
    }

    public Map<String, Object> parseDataInListToMap(String data) {
        // "[{\"id\":\"27\",\"name\":\"12\",\"sex\":\"44\",\"pwd\":\"44\",\"email\":\"44\"}]"
        // 输入样例
        JSONArray jsonArray = JSON.parseArray(data);

        JSONObject jsonObject = jsonArray.getJSONObject(0);

        Map<String, Object> map = new HashMap<>();
        for (String key : jsonObject.keySet()) {
            map.put(key, jsonObject.get(key));
//            map.put(key, String.valueOf(jsonObject.get(key)));
        }
        return map;
    }

    public Map<String, Object> parseDataToMap(String data){
        JSONObject jsonObject = JSON.parseObject(data);

        Map<String, Object> map = new HashMap<>();
        for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
//            map.put(entry.getKey(), entry.getValue().toString());
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }

    public static void main(String[] args) {
        String jsonArrayStr = "[{\"id\":\"27\",\"name\":\"12\",\"sex\":\"44\",\"pwd\":\"44\",\"email\":\"44\"}]";

        // 解析JSON数组
        JSONArray jsonArray = JSON.parseArray(jsonArrayStr);

        // 如果JSON数组中只有一个元素，你可以直接获取该元素
        JSONObject jsonObject = jsonArray.getJSONObject(0);

        // 转换为Map<String, String>
        Map<String, String> map = new HashMap<>();
        for (String key : jsonObject.keySet()) {
            map.put(key, jsonObject.getString(key));
        }

        // 打印解析后的Map
        System.out.println(map);

        String jsonString = "{\"id\":\"int unsigned\",\"name\":\"varchar(10)\",\"sex\":\"char(6)\",\"pwd\":\"varchar(20)\",\"email\":\"varchar(20)\"}";

        // 使用FastJSON将JSON字符串解析为JSONObject
        jsonObject = JSON.parseObject(jsonString);

        // 转换为Map<String, String>
        map = new HashMap<>();
        for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
            map.put(entry.getKey(), entry.getValue().toString());
        }

        // 打印解析后的Map
        System.out.println(map);
        System.out.println((String) null);
        System.out.println(((String) null) == null);

        System.out.println(map.get("   "));
    }
}
