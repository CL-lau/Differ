package com.differ.compare.config;

import com.differ.compare.strategy.Strategy;
import com.differ.compare.strategy.StrategyContext;
import com.differ.compare.strategy.impl.CanalStrategy;
import com.differ.compare.strategy.impl.DebeziumStrategy;
import com.differ.compare.strategy.impl.DirectStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: lau
 * @time: 2023/11/6 10:27
 */
@Configuration
public class StrategyConfig {
    private DebeziumStrategy debeziumStrategy;
    private CanalStrategy canalStrategy;
    private DirectStrategy directStrategy;
    @Autowired
    public void setStrategy(DebeziumStrategy debeziumStrategy, CanalStrategy canalStrategy, DirectStrategy directStrategy){
        this.debeziumStrategy = debeziumStrategy;
        this.canalStrategy = canalStrategy;
        this.directStrategy = directStrategy;
    }
    @Bean
    public StrategyContext strategyContext() {
        Map<String, Strategy> strategies = new HashMap<>();
        strategies.put("debezium", debeziumStrategy);
        strategies.put("canal", canalStrategy);
        strategies.put("direct", directStrategy);
        return new StrategyContext(strategies);
    }
}
