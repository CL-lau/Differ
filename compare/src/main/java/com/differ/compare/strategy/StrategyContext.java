package com.differ.compare.strategy;

import java.util.Map;

/**
 * @description:
 * @author: lau
 * @time: 2023/11/6 10:25
 */
//@Component
public class StrategyContext {
    private final Map<String, Strategy> strategies;

    public StrategyContext(Map<String, Strategy> strategies) {
        this.strategies = strategies;
    }

    public void executeStrategy(String strategyName) {
        Strategy strategy = strategies.get(strategyName);
        if (strategy != null) {
            strategy.execute();
        } else {
            System.out.println("No strategy found for: " + strategyName);
        }
    }
}
